package com.example.interviewpractice.frontend.views.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.controller.AuthController
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import java.time.YearMonth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.types.Tag


@Composable
//@Preview
fun RegisterScreen(am: AuthModel, c: AuthController) {
    val vm: RegisterViewModel = viewModel()
    vm.addModel(am)
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
                value = vm.username,
                onValueChange = { vm.username = it },
                label = { Text("Username *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = vm.password,
                onValueChange = { vm.password = it},
                label = { Text("Password *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = vm.passwordConfirm,
                onValueChange = {vm.passwordConfirm = it },
                label = { Text("Confirm Password *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(fontSize = 28.sp)

            )
            TextField(
                value = vm.email,
                onValueChange = { vm.email = it },
                label = { Text("Email *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            BirthdaySelect(vm)
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            FieldsOfInterest(vm)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    c.verifyRegister(
                        username = vm.username,
                        password = vm.password,
                        email = vm.email,
                        foi = vm.selectedOptions,
                        birthday = vm.getSelectedBirthday(),
                    )
                },
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

//TODO: Move data to viewModel
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FieldsOfInterest(vm: RegisterViewModel){
    val options = listOf(
        Tag.BIOLOGY, Tag.ENGLISH, Tag.CHEMISTRY, Tag.CS, Tag.MATH, Tag.FINANCE, Tag.PHYSICS, Tag.BUSINESS
    )
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
                    val isSelected = vm.selectedOptions.contains(option)
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                if (vm.selectedOptions.size < 3) {
                                    vm.selectedOptions = vm.selectedOptions.plus(option)
                                }
                            } else {
                                vm.selectedOptions = vm.selectedOptions.minus(option)
                            }
                        }
                    )
                    Text(
                        text = option.toString(),
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdaySelect(vm: RegisterViewModel) {

    val years = (1900..YearMonth.now().year).toList()
    val months = (1..12).toList()
    val daysInMonth = remember(vm.selectedYear, vm.selectedMonth) {
        YearMonth.of(vm.selectedYear, vm.selectedMonth).lengthOfMonth()
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
                selectedYear = vm.selectedYear,
                onYearSelected = { vm.selectedYear = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            MonthDropdown(
                months = months,
                selectedMonth = vm.selectedMonth,
                onMonthSelected = { vm.selectedMonth = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            DayDropdown(
                days = days,
                selectedDay = vm.selectedDay,
                onDaySelected = { vm.selectedDay = it }
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
