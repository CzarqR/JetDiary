package com.myniprojects.jetdiary.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.repo.LessonRepo
import com.myniprojects.jetdiary.repo.MarkRepo
import com.myniprojects.jetdiary.repo.StudentRepo
import com.myniprojects.jetdiary.ui.common.EditListState
import com.myniprojects.jetdiary.ui.lesson.LessonRow
import com.myniprojects.jetdiary.ui.mark.MarkRow
import com.myniprojects.jetdiary.ui.student.StudentRow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo,
    private val studentRepo: StudentRepo,
    private val markRepo: MarkRepo,
) : ViewModel()
{
    init
    {
        viewModelScope.launch {
            lessonRepo.mockData()
            studentRepo.mockData()
            markRepo.mockData()
        }
    }


    // region lesson

    private val _selectedLesson: MutableStateFlow<Lesson?> = MutableStateFlow(null)

    private val studentsInLesson: Flow<List<Student>> = _selectedLesson.flatMapLatest { lesson ->

        if (lesson != null)
        {
            studentRepo.getStudentsInLesson(lesson.lessonId).map { lws ->
                lws.students
            }
        }
        else
        {
            flowOf()
        }
    }

    private val lessonListState = EditListState(
        flowList = lessonRepo.lessons,
        onSave = {
            insertLesson(it)
        },
        onDelete = {
            viewModelScope.launch {
                lessonRepo.deleteLesson(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
            _selectedLesson.value = it
            _navigateToStudents.value = true
        },
        generateNewItem = {

            val id = runBlocking {
                lessonRepo.insertLesson(Lesson())
            }

            return@EditListState getLesson(id)
        },
        {
            return@EditListState it.copy(
                name = it.name.trim()
            )
        }
    )

    val lessonRow = LessonRow(lessonListState)

    private fun insertLesson(lesson: Lesson)
    {
        viewModelScope.launch {
            lessonRepo.insertLesson(lesson)
        }
    }

    private fun getLesson(id: Long): Lesson = runBlocking {
        lessonRepo.getLesson(id)
    }

    private val _navigateToStudents: MutableState<Boolean> = mutableStateOf(false)
    val navigateToStudents: State<Boolean> = _navigateToStudents

    fun studentsNavigated()
    {
        _navigateToStudents.value = false
    }


    // endregion

    // region students

    private val _selectedStudent: MutableStateFlow<Student?> = MutableStateFlow(null)

    private val studentsMarks: Flow<List<MarkAssigned>> = _selectedStudent.flatMapLatest { student ->

        val lesson = _selectedLesson.value

        if (student != null && lesson != null)
        {
            markRepo.getMarks(lessonId = lesson.lessonId, studentId = student.studentId)
        }
        else
        {
            flowOf()
        }
    }


    private val studentListState = EditListState(
        flowList = studentsInLesson,
        onSave = {
            insertStudent(it)
        },
        onDelete = {
            viewModelScope.launch {
                studentRepo.deleteLesson(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
            _selectedStudent.value = it
            _navigateToMarks.value = true
        },
        generateNewItem = {

            val id = runBlocking {
                studentRepo.insertLesson(Student())
            }

            return@EditListState getStudent(id)
        },
        {
            return@EditListState it.copy(
                name = it.name.trim(),
                surname = it.surname.trim()
            )
        }
    )


    val studentRow = StudentRow(studentListState)

    private fun insertStudent(student: Student)
    {
        viewModelScope.launch {
            studentRepo.insertLesson(student)
        }
    }

    private fun getStudent(id: Long): Student = runBlocking {
        studentRepo.getLesson(id)
    }

    private val _navigateToMarks: MutableState<Boolean> = mutableStateOf(false)
    val navigateToMarks: State<Boolean> = _navigateToMarks

    fun marksNavigated()
    {
        _navigateToMarks.value = false
    }

    // endregion


    // region marks


    private val markListState = EditListState(
        flowList = studentsMarks,
        onSave = {
        },
        onDelete = {
            viewModelScope.launch {

            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
        },
        generateNewItem = {

            return@EditListState MarkAssigned(0, 0, Mark.FIVE)
        },
        {
            return@EditListState it
        }
    )

    val markRow = MarkRow(markListState)


    // endregion
}