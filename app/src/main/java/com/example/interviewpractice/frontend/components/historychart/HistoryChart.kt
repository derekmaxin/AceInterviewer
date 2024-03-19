package com.example.interviewpractice.frontend.components.historychart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(utcDate: LocalDate): Int {
    when (utcDate.dayOfWeek) {
        DayOfWeek.SUNDAY -> return 0
        DayOfWeek.MONDAY -> return 1
        DayOfWeek.TUESDAY -> return 2
        DayOfWeek.WEDNESDAY -> return 3
        DayOfWeek.THURSDAY -> return 4
        DayOfWeek.FRIDAY -> return 5
        DayOfWeek.SATURDAY -> return 6
        else -> { return -1 }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun HistoryChart() {

    var calendar = mutableListOf<Pair<LocalDate, Int>>()
    val oneYearAgo = LocalDate.now().minusDays(365)
    for (date in 0..365) {
        calendar.add(Pair(LocalDate.now().minusDays(date.toLong()), Random.nextInt(5)))
    }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .height(112.dp)
            .padding(8.dp)
    ) {
        for (spaces in 0..getDayOfWeek(oneYearAgo)) {
            item() {
                Spacer( modifier = Modifier.size(12.dp))
            }
        }

        for (day in calendar) {
            item() {
                Card(
                    modifier = Modifier.size(12.dp),

                    colors = CardDefaults.cardColors( containerColor =
                        when(day.second) {
                            0 -> Color(0xffd5cade)
                            in 1..2 -> Color(0xffc3a9d9)
                            in 3..4 -> Color(0xffa46ad4)
                            else -> Color(0xff8f34d9)
                        }
                    )


                ) {}

            }
        }
    }
}