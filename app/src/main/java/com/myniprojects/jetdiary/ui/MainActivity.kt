package com.myniprojects.jetdiary.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.myniprojects.jetdiary.ui.composes.LessonScreen
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.vm.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val lessonViewModel: LessonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface {
                    LessonScreen(
                        lessonViewModel = lessonViewModel
                    )
                }
            }
        }
    }
}
