package com.myniprojects.jetdiary.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.ui.lesson.LessonBody
import com.myniprojects.jetdiary.ui.mark.MarksBody
import com.myniprojects.jetdiary.ui.studenteditor.StudentBody
import com.myniprojects.jetdiary.ui.studentlesson.StudentLessonBody
import com.myniprojects.jetdiary.utils.Constants.LESSON_SCREEN_ROUTE
import com.myniprojects.jetdiary.utils.Constants.MARKS_SCREEN_ROUTE
import com.myniprojects.jetdiary.utils.Constants.STUDENT_EDITOR_SCREEN_ROUTE
import com.myniprojects.jetdiary.utils.Constants.STUDENT_LESSON_SCREEN_ROUTE
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

    navController.addOnDestinationChangedListener { controller, _, _ ->
        setCanPop(controller.previousBackStackEntry != null)
    }

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // check navigation state and navigate
    when
    {
        viewModel.navigateToStudents.value ->
        {
            navController.navigate(route = STUDENT_LESSON_SCREEN_ROUTE)
            viewModel.studentsNavigated()
        }
        viewModel.navigateToMarks.value ->
        {
            navController.navigate(route = MARKS_SCREEN_ROUTE)
            viewModel.marksNavigated()
        }
        viewModel.navigateToStudentEditor.value ->
        {
            navController.navigate(route = STUDENT_EDITOR_SCREEN_ROUTE)
            viewModel.studentEditorNavigated()
        }
    }



    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text(text = title) },
                backgroundColor = MaterialTheme.colors.primary,
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
        bodyContent = {
            AppBody(
                viewModel = viewModel,
                navController = navController,
                setTitle = setTitle
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(mainViewModel = viewModel)
        },
        drawerShape = CutCornerShape(
            bottomRight = dimensionResource(id = R.dimen.nav_drawer_cut),
            topRight = dimensionResource(id = R.dimen.nav_drawer_cut)
        ),
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
            route = STUDENT_LESSON_SCREEN_ROUTE
        ) {
            StudentLessonBody(
                viewModel = viewModel,
                setTitle = setTitle
            )
        }

        composable(
            route = STUDENT_EDITOR_SCREEN_ROUTE
        ) {
            StudentBody(
                viewModel = viewModel,
                setTitle = setTitle
            )
        }

        composable(
            route = MARKS_SCREEN_ROUTE
        ) {
            MarksBody(
                viewModel = viewModel,
                setTitle = setTitle
            )
        }
    }
}


@ExperimentalCoroutinesApi
@Composable
fun DrawerContent(
    mainViewModel: MainViewModel
)
{
    val today = mainViewModel.todayMarks.collectAsState(initial = null)
    val allMarksCount = mainViewModel.allMarksCount.collectAsState(initial = null)
    val studentsCount = mainViewModel.studentsCount.collectAsState(initial = null)
    val lessonsCount = mainViewModel.lessonsCount.collectAsState(initial = null)

    ScrollableColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.default_padding_medium))
    ) {

        Image(
            modifier = Modifier
                .preferredHeight(dimensionResource(id = R.dimen.drawer_icon)),
            asset = imageResource(id = R.drawable.icon)
        )

        Divider(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.default_padding_small))
        )

        StatText(stringId = R.string.today_marks_format, arg = today)
        StatText(stringId = R.string.all_marks_count_format, arg = allMarksCount)
        StatText(stringId = R.string.students_count_format, arg = studentsCount)
        StatText(stringId = R.string.lesson_count_format, arg = lessonsCount)

    }
}

@Composable
fun <T> StatText(
    @StringRes stringId: Int,
    arg: State<T?>
)
{
    Text(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.default_padding_small)),
        text = stringResource(
            stringId,
            arg.value?.toString() ?: "…"
        ),
        style = MaterialTheme.typography.body1
    )
}