package com.myniprojects.jetdiary.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // turn on light mode to test
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()

        setContent {
            AppTheme {
                Surface {
                    App(
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}
