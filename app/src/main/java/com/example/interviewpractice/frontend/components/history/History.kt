package com.example.interviewpractice.frontend.components.history

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.viewreviewscores.ViewReviewScores
import com.example.interviewpractice.frontend.views.review.ReviewViewViewModel
import java.util.Calendar
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

@Composable
fun listSelectionDropdown(
    startingValue: String,
    optionsList: List<String>,
    onUpdate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(startingValue)}

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },

    ) {

        for (item in optionsList) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onUpdate(item)
                    selected = item
                },
                text = { Text(item) }
            )
        }
    }

    OutlinedButton(
        modifier = modifier,
        onClick = { expanded = true }
    ) {
        Text(selected)
        Icon( Icons.Outlined.ExpandMore, contentDescription = null )
    }
}


fun stringContainsLetter(input: String): Boolean {
    val regex = Regex("[a-zA-Z]")
    return regex.containsMatchIn(input)
}
@Composable
fun History(viewModel: HistoryViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ) {
            Spacer(Modifier.weight(1f))
            listSelectionDropdown(
                startingValue = viewModel.selectedMonth,
                optionsList = getMonthsAsStringList(),
                onUpdate = { viewModel.selectedMonth = it },
                modifier = Modifier
                    .height(50.dp)
            )

            OutlinedTextField (
                value = viewModel.selectedYear,
                onValueChange = {
                    if (!stringContainsLetter(it) && it.length <= 4) {
                        viewModel.selectedYear = it
                    }
                    Log.d("CHANGE", "View model changed to $it") },
                label = { Text("Year") },
                maxLines = 1,
                modifier = Modifier
                    .width(100.dp)
                    .height(58.dp)
            )
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        for (vrsVM in viewModel.historyData) {
            ViewReviewScores(vrsVM)
        }
    }
}