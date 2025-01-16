package com.macc.androidkotlin.todoapp

import com.macc.androidkotlin.todoapp.TaskCategory

data class Task (val name:String, val category: TaskCategory, var isSelected:Boolean = false)