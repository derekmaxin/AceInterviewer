package com.example.interviewpractice.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.*
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun RegisterScreen() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Register",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
            }
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Username *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Password *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Confirm Password *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Email *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            BirthdaySelect()
            FieldsOfInterest()
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Register",
                    style = TextStyle(
                        fontSize = 26.sp,
                        color = Color.White,
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("* Required",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                )
            )
        }
    }
}

@Composable
fun FieldsOfInterest(){
    val options = listOf("Biology", "English", "Chemistry", "Art", "Computer Science", "Math"
    , "Finance", "Physics", "Business")
    var selectedOptions by remember { mutableStateOf(setOf<String>()) }

    Column {
        Text(
            text = "Select up to three interests:",
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
            )
        )
        val rows = options.chunked(3)
        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { option ->
                    val isSelected = selectedOptions.contains(option)
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                if (selectedOptions.size < 3) {
                                    selectedOptions = selectedOptions.plus(option)
                                }
                            } else {
                                selectedOptions = selectedOptions.minus(option)
                            }
                        }
                    )
                    Text(
                        text = option,
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdaySelect() {
    var selectedYear by remember { mutableIntStateOf(YearMonth.now().year) }
    var selectedMonth by remember { mutableIntStateOf(YearMonth.now().monthValue) }
    var selectedDay by remember { mutableIntStateOf(LocalDate.now().dayOfMonth) }

    val years = (1900..YearMonth.now().year).toList()
    val months = (1..12).toList()
    val daysInMonth = remember(selectedYear, selectedMonth) {
        YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
    }
    val days = (1..daysInMonth).toList()

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Birthday",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            YearDropdown(
                years = years,
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            MonthDropdown(
                months = months,
                selectedMonth = selectedMonth,
                onMonthSelected = { selectedMonth = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            DayDropdown(
                days = days,
                selectedDay = selectedDay,
                onDaySelected = { selectedDay = it }
            )
        }
        //Text("Selected Date: $selectedYear-$selectedMonth-$selectedDay")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun YearDropdown(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    Column {
        Text("Year")
        var expanded by remember { mutableStateOf(false) }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    },
                    modifier = Modifier.selectable(selected = year == selectedYear) {},
                    text = { Text(year.toString()) }
                )
            }
        }
        Button(
            onClick = { expanded = true },
        ) {
            Text(text = selectedYear.toString())
        }
    }
}

@Composable
fun MonthDropdown(
    months: List<Int>,
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit
) {
    Column {
        Text("Month")
        var expanded by remember { mutableStateOf(false) }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    onClick = {
                        onMonthSelected(month)
                        expanded = false
                    },
                    modifier = Modifier.selectable(selected = month == selectedMonth) {},
                    text = { Text(month.toString()) }
                )
            }
        }
        Button(
            onClick = { expanded = true },
        ) {
            Text(text = selectedMonth.toString())
        }
    }
}

@Composable
fun DayDropdown(
    days: List<Int>,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    Column {
        Text("Day")
        var expanded by remember { mutableStateOf(false) }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            days.forEach { day ->
                DropdownMenuItem(
                    onClick = {
                        onDaySelected(day)
                        expanded = false
                    },
                    modifier = Modifier.selectable(selected = day == selectedDay) {},
                    text = { Text(day.toString()) }
                )
            }
        }
        Button(
            onClick = { expanded = true },
        ) {
            Text(text = selectedDay.toString())
        }
    }
}
