package com.myniprojects.jetdiary.ui.main

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.lesson.LessonBody
import com.myniprojects.jetdiary.ui.student.StudentBody
import com.myniprojects.jetdiary.utils.Constants.LESSON_SCREEN_ROUTE
import com.myniprojects.jetdiary.utils.Constants.STUDENT_SCREEN_ROUTE
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun App(
    viewModel: MainViewModel
)
{
    val navController = rememberNavController()

    val baseTitle = "" // stringResource(id = R.string.app_name)
    val (title, setTitle) = remember { mutableStateOf(baseTitle) }

    val (canPop, setCanPop) = remember { mutableStateOf(false) }

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    navController.addOnDestinationChangedListener { controller, _, _ ->
        setCanPop(controller.previousBackStackEntry != null)
    }

    // check navigation state and navigate
    if (viewModel.navigateToStudents.value)
    {
        navController.navigate(route = STUDENT_SCREEN_ROUTE)
        viewModel.studentsNavigated()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {

                    if (canPop)
                    {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(asset = Icons.Outlined.ArrowBack)
                        }
                    }
                    else
                    {
                        IconButton(onClick = {
                            scaffoldState.drawerState.open()
                        }) {
                            Icon(asset = Icons.Outlined.Menu)
                        }
                    }
                },
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent()
        },
        drawerShape = CutCornerShape(
            bottomRight = dimensionResource(id = R.dimen.nav_drawer_cut),
            topRight = dimensionResource(id = R.dimen.nav_drawer_cut)
        ),
        bodyContent = {
            AppBody(
                viewModel = viewModel,
                navController = navController,
                setTitle = setTitle
            )
        }
    )
}


@ExperimentalCoroutinesApi
@Composable
fun AppBody(
    viewModel: MainViewModel,
    navController: NavHostController,
    setTitle: (String) -> Unit,
)
{
    NavHost(
        navController,
        startDestination = LESSON_SCREEN_ROUTE
    ) {
        composable(route = LESSON_SCREEN_ROUTE) {
            LessonBody(
                viewModel = viewModel,
                setTitle = setTitle
            )
        }
        composable(
            route = STUDENT_SCREEN_ROUTE
        ) {
            StudentBody(
                viewModel = viewModel,
                setTitle = setTitle
            )
        }
    }
}


@Composable
fun DrawerContent()
{

    Text(text = "Drawer")

}