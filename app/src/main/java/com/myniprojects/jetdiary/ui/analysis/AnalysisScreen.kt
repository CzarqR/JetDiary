package com.myniprojects.jetdiary.ui.analysis

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.myniprojects.jetdiary.R
import com.myniprojects.jetdiary.db.lesson.Lesson
import com.myniprojects.jetdiary.db.mark.Mark
import com.myniprojects.jetdiary.db.mark.MarkAssigned
import com.myniprojects.jetdiary.db.student.Student
import com.myniprojects.jetdiary.ui.common.ItemRowBase
import com.myniprojects.jetdiary.ui.theme.AppTheme
import com.myniprojects.jetdiary.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun AnalysisScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    setTitle: (String) -> Unit,
)
{
    setTitle(stringResource(id = R.string.analysis))

    val x = viewModel.markLessonStudent.collectAsState(initial = listOf())

    LazyColumnFor(
        modifier = modifier
            .fillMaxSize(),
        items = x.value.sortedBy {
            it.third.name
        }
    ) { summaryItem ->
        Timber.d(summaryItem.toString())
        CombinedItem(data = summaryItem)
    }
}

@ExperimentalAnimationApi
@Composable
fun CombinedItem(
    data: Triple<MarkAssigned, Student, Lesson>,
    modifier: Modifier = Modifier
)
{
    Timber.d(data.toString())
    ItemRowBase(
        modifier = modifier
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.default_padding_small))
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
            )
            {
                Text(
                    text = stringResource(
                        id = R.string.student_format,
                        data.second.surname,
                        data.second.name
                    ),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.default_padding_tiny))
                )
                Text(
                    text = data.third.name,
                    style = MaterialTheme.typography.h5
                )
            }

            Text(
                text = data.first.mark.markText,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.default_padding_small),
                        start = dimensionResource(id = R.dimen.default_padding_small)
                    )

            )

        }


    }
}


@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun CombinedItemPrev()
{

    AppTheme {
        CombinedItem(
            data = Triple(
                MarkAssigned(1, 1, Mark.FOUR_HALF),
                Student("Leo", "Messi"),
                Lesson("Database SQL")
            )
        )
    }
}