package com.example.guru_2

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
)
{
    fun isCompleted() = completedDate != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.baseline_check_circle_outline_24 else R.drawable.baseline_radio_button_unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
}