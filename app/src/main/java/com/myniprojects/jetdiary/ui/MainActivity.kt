package com.myniprojects.jetdiary.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.myniprojects.jetdiary.ui.lesson.LessonScreen
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.vm.LessonViewModel
import com.myniprojects.jetdiary.vm.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val lessonViewModel: LessonViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // turn on light mode to test
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //delegate.applyDayNight()

        setContent {
            AppTheme {
                Surface {
                    LessonScreen(
                        lessonViewModel = lessonViewModel
                    )

//                    StudentScreen(
//                        studentViewModel
//                    )

                }
            }
        }
    }
}
