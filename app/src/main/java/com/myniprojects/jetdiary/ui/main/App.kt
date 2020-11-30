package com.myniprojects.jetdiary.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myniprojects.jetdiary.ui.lesson.LessonBody
import com.myniprojects.jetdiary.ui.student.StudentBody
import com.myniprojects.jetdiary.vm.MainViewModel


@Composable
fun App(
    viewModel: MainViewModel
)
{
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "App title") },
                navigationIcon = {

                    /*
                    Check if navController back stack has more
                    than one element. If so show BackButton.
                    Clicking on that button will move back
                     */

                    val canMoveBack = true

                    if (canMoveBack)
                    {
                        IconButton(onClick = {
                            // Move back
                            navController.popBackStack()
                        }) {
                            Icon(asset = Icons.Outlined.ArrowBack)
                        }
                    }
                    else
                    {
                        IconButton(onClick = {
                            // show NavDrawer
                        }) {
                            Icon(asset = Icons.Outlined.Menu)
                        }
                    }
                },
            )
        },
        bodyContent = {
            AppBody(
                viewModel = viewModel,
                navController = navController
            )
        }
    )
}


@Composable
fun AppBody(
    viewModel: MainViewModel,
    navController: NavHostController
)
{
    NavHost(
        navController,
        startDestination = "lessonScreen"
    ) {
        composable("lessonScreen") {
            LessonBody(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = "studentScreen"
        ) {
            StudentBody(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}