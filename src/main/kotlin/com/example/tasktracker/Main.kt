package com.example.tasktracker

import java.io.File

data class Task(
    val description: String,
    var isDone: Boolean = false,
)

val tasksFile = File("tasks.txt")
val tasks = mutableListOf<Task>()

fun main() {
    loadTasks()
    while (true) {
        println("---- Task Tracker ---")
        println("1. Add task")
        println("2. View tasks")
        println("3. Mark task as done")
        println("4. Exit")
        print("Choose an option: ")

        when (readLine()?.trim()) {
            "1" -> addTask()
            "2" -> viewTasks()
            "3" -> markTaskDone()
            "4" -> {
                saveTasks()
                println("Goodbye!")
                return
            }
            else -> println("Invalid option, try again.")
        }
    }
}

fun addTask() {
    print("Enter task description: ")
    val description = readLine()?.trim()
    if (!description.isNullOrEmpty()) {
        tasks.add(Task(description))
        println("Task added.")
    } else {
        println("Task description cannot be empty.")
    }
}

fun viewTasks() {
    if (tasks.isEmpty()) {
        println("No tasks to show.")
    } else {
        println("Tasks:")
        tasks.forEachIndexed { index, task ->
            val status = if (task.isDone) "Done" else "Not done"
            println("${index + 1}. ${task.description} [$status]")
        }
    }
}

fun markTaskDone() {
    print("Enter the number of the task to mark as done: ")
    val input = readLine()?.trim()
    val index = input?.toIntOrNull()
    if (index != null && index in 1..tasks.size) {
        tasks[index - 1].isDone = true
        println("Task marked as done.")
    } else {
        println("Invalid task number.")
    }
}

fun loadTasks() {
    if (tasksFile.exists()) {
        tasksFile.readLines().forEach { line ->
            val parts = line.split("|")
            if (parts.size == 2) {
                val description = parts[0]
                val isDone = parts[1].toBoolean()
                tasks.add(Task(description, isDone))
            }
        }
    }
}

fun saveTasks() {
    tasksFile.printWriter().use { out ->
        tasks.forEach { task ->
            out.println("${task.description}|${task.isDone}")
        }
    }
}
