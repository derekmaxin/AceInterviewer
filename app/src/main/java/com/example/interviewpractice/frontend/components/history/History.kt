package com.example.interviewpractice.frontend.components.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.viewreviewscores.ViewReviewScores
import com.example.interviewpractice.model.MainModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getMonthsAsStringList(): List<String> {
    val calendar = Calendar.getInstance()
    val monthNames = mutableListOf<String>()

    for (i in 0 until 12) {
        calendar.set(Calendar.MONTH, i)
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        monthNames.add(monthName)
    }

    return monthNames
}

fun getDateRangeFromMonth(month: Int, year: Int): Pair<Date, Date> {
    val firstDay = Calendar.getInstance()
    firstDay.set(Calendar.MONTH, month)
    firstDay.set(Calendar.YEAR, year)
    firstDay.set(Calendar.DATE, 1)

    val lastDay = Calendar.getInstance()
    lastDay.set(Calendar.MONTH, month)
    lastDay.set(Calendar.YEAR, year)
    lastDay.set(Calendar.DATE, lastDay.getActualMaximum(Calendar.DATE))

    return Pair(firstDay.time, lastDay.time)
}

fun onVMUpdate(hc: HistoryController) {

}

@Composable
fun listSelectionDropdown(
    startingIndex: Int,
    optionsList: List<String>,
    onUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(startingIndex)}

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },

    ) {

        for (index in optionsList.indices) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onUpdate(index)
                    selectedIndex = index
                },
                text = { Text(optionsList[index]) }
            )
        }
    }

    OutlinedButton(
        modifier = modifier,
        onClick = { expanded = true }
    ) {
        Text(optionsList[selectedIndex])
        Icon( Icons.Outlined.ExpandMore, contentDescription = null )
    }
}

fun stringContainsLetter(input: String): Boolean {
    val regex = Regex("[a-zA-Z]")
    return regex.containsMatchIn(input)
}
@Composable
fun History(viewModel: HistoryViewModel, hc: HistoryController, mm: MainModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            listSelectionDropdown(
                startingIndex = viewModel.selectedMonth,
                optionsList = getMonthsAsStringList(),
                onUpdate =
                {
                    viewModel.selectedMonth = it
                    val dateRange = getDateRangeFromMonth(viewModel.selectedMonth, viewModel.selectedYear.toInt())
                    hc.getUserHistoryDataByDate(dateRange.first, dateRange.second)
                },
                modifier = Modifier
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField (
                value = viewModel.selectedYear,
                onValueChange = {
                    if (!stringContainsLetter(it) && it.length <= 4) {
                        viewModel.selectedYear = it
                        val dateRange = getDateRangeFromMonth(viewModel.selectedMonth, viewModel.selectedYear.toInt())
                        hc.getUserHistoryDataByDate(dateRange.first, dateRange.second)
                    } },
                label = { Text("Year") },
                maxLines = 1,
                modifier = Modifier
                    .width(100.dp)
                    .height(58.dp)
            )
        }
        if (viewModel.localLoading) {
            Loader()
        }
        else {
            for (history in viewModel.historyChartData) {
                ViewReviewScores(history, mm)
            }
        }

    }
}