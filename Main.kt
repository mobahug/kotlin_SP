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
        println("\n--- Task Tracker ---")
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
    val desc = readLine()?.trim()
    if (!desc.isNullOrBlank()) {
        tasks.add(Task(desc))
        println("Task added!")
    }
}

fun viewTasks() {
    if (tasks.isEmpty()) {
        println("No tasks yet.")
    } else {
        tasks.forEachIndexed { i, task ->
            val status = if (task.isDone) "✓" else "✗"
            println("${i + 1}. [$status] ${task.description}")
        }
    }
}

fun markTaskDone() {
    viewTasks()
    print("Enter task number to mark as done: ")
    val index = readLine()?.toIntOrNull()?.minus(1)
    if (index != null && index in tasks.indices) {
        tasks[index].isDone = true
        println("Task marked as done!")
    } else {
        println("Invalid task number.")
    }
}

fun saveTasks() {
    tasksFile.writeText(tasks.joinToString("\n") { "${it.isDone};${it.description}" })
}

fun loadTasks() {
    if (tasksFile.exists()) {
        tasksFile.readLines().forEach { line ->
            val parts = line.split(";")
            if (parts.size == 2) {
                tasks.add(Task(parts[1], parts[0].toBoolean()))
            }
        }
    }
}
