package com.myniprojects.jetdiary.vm

import android.text.format.DateUtils
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
import com.myniprojects.jetdiary.ui.studenteditor.StudentRow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*


@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val lessonRepo: LessonRepo,
    private val studentRepo: StudentRepo,
    private val markRepo: MarkRepo,
) : ViewModel()
{
    init
    {
        //add fake data
//        viewModelScope.launch {
//            lessonRepo.mockData()
//            studentRepo.mockData()
//            markRepo.mockData()
//        }

    }


    // region lesson

    private val _selectedLesson: MutableStateFlow<Lesson?> = MutableStateFlow(null)
    val selectedLesson: StateFlow<Lesson?> = _selectedLesson

    val studentsInLesson: Flow<List<Student>> = _selectedLesson.flatMapLatest { lesson ->

        Timber.d("New income")

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
        update = {
            updateLesson(it)
        },
        insert = {
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
            return@EditListState Lesson()
        },
        {
            return@EditListState it.copy(
                name = it.name.trim()
            )
        }
    )

    private fun updateLesson(lesson: Lesson)
    {
        viewModelScope.launch {
            lessonRepo.updateLesson(lesson)
        }
    }

    private fun insertLesson(lesson: Lesson)
    {
        viewModelScope.launch {
            lessonRepo.insertLesson(lesson)
        }
    }

    val lessonRow = LessonRow(lessonListState)


    private val _navigateToStudents: MutableState<Boolean> = mutableStateOf(false)
    val navigateToStudents: State<Boolean> = _navigateToStudents

    fun studentsNavigated()
    {
        _navigateToStudents.value = false
    }


    // endregion

    // region students editor

    private val allStudents = studentRepo.students


    private val studentsInLessonEditor = allStudents.combine(
        studentsInLesson
    ) { all, isIn ->
        val l = mutableListOf<Pair<Student, Boolean>>()
        all.forEach {
            l.add(it to isIn.contains(it))
        }
        l
    }


    private val studentListState = EditListState<Pair<Student, Boolean>>(
        flowList = studentsInLessonEditor,
        update = {
            updateStudent(it.first)
        },
        insert = {
            viewModelScope.launch {
                insertStudent(it.first)
            }
        },
        onDelete = {
            viewModelScope.launch {
                studentRepo.deleteStudent(it.first)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
            Timber.d("Insert")
            viewModelScope.launch {
                if (it.second) // add
                {
                    val lesson = _selectedLesson.value
                    if (lesson != null)
                    {
                        Timber.d("Not null")

                        studentRepo.assignStudentToLesson(
                            lessonId = lesson.lessonId,
                            studentId = it.first.studentId
                        )
                    }
                }
                else // remove
                {
                    val lesson = _selectedLesson.value
                    if (lesson != null)
                    {
                        Timber.d("Not null")

                        studentRepo.deleteStudentToLesson(
                            lessonId = lesson.lessonId,
                            studentId = it.first.studentId
                        )
                    }
                }

            }
        },
        generateNewItem = {
            return@EditListState Student() to false
        },
        {
            return@EditListState it.copy(
                first = it.first.copy(
                    name = it.first.name.trim(),
                    surname = it.first.surname.trim()
                )
            )
        }
    )


    val studentEditorRow = StudentRow(studentListState)

    private fun insertStudent(student: Student)
    {
        viewModelScope.launch {
            studentRepo.insertStudent(student)
        }
    }

    private fun updateStudent(student: Student)
    {
        viewModelScope.launch {
            studentRepo.updateStudent(student)
        }
    }


    // endregion

    //region students


    private val _selectedStudent: MutableStateFlow<Student?> = MutableStateFlow(null)
    val selectedStudent: StateFlow<Student?> = (_selectedStudent)

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

    private val _navigateToStudentEditor: MutableState<Boolean> = mutableStateOf(false)
    val navigateToStudentEditor: State<Boolean> = _navigateToStudentEditor

    fun navigateToStudentEditor()
    {
        _navigateToStudentEditor.value = true
    }

    fun studentEditorNavigated()
    {
        _navigateToStudentEditor.value = false
    }

    private val _navigateToMarks: MutableState<Boolean> = mutableStateOf(false)
    val navigateToMarks: State<Boolean> = _navigateToMarks

    fun navigateToMarks(student: Student)
    {
        _selectedStudent.value = student
        _navigateToMarks.value = true
    }

    fun marksNavigated()
    {
        _navigateToMarks.value = false
    }

    // endregion

    // region marks


    private val markListState = EditListState(
        flowList = studentsMarks,
        update = {
            updateMark(it)
        },
        insert = {
            insertMark(it)
        },
        onDelete = {
            viewModelScope.launch {
                markRepo.deleteMark(it)
            }
        },
        clickItem = {
            Timber.d("Item clicked $it")
        },
        generateNewItem = {

            val studentId = _selectedStudent.value?.studentId
            val lessonId = _selectedLesson.value?.lessonId

            if (lessonId != null && studentId != null)
            {
                return@EditListState MarkAssigned(
                    lessonId = lessonId,
                    studentId = studentId,
                    mark = Mark.FIVE
                )
            }
            else
            {
                throw Exception("Student or lesson unselected")
            }
        },
        formatItem = {
            return@EditListState it
        }
    )

    val markRow = MarkRow(markListState)


    private fun insertMark(markAssigned: MarkAssigned)
    {
        viewModelScope.launch {
            markRepo.insertMark(markAssigned)
        }
    }

    private fun updateMark(markAssigned: MarkAssigned)
    {
        viewModelScope.launch {
            markRepo.updateMark(markAssigned)
        }
    }

    // endregion


    // region stats

    val todayMarks: Flow<Long> = markRepo.getAllMarks().flatMapLatest {

        var i = 0L

        it.forEach { mark ->
            if (DateUtils.isToday(mark.date.time))
                i++
        }

        flowOf(i)
    }

    val allMarksCount = markRepo.getMarksCount()
    val lessonsCount = lessonRepo.getLessonsCount()
    val studentsCount = studentRepo.getStudentsCount()

    // endregion
}