import java.io.File

/**
 * Represents a single task. Each task has a textual description and a completion status.
 */
data class Task(
    val description: String,
    var isDone: Boolean = false,
)

/**
 * Handles loading, saving and in-memory management of tasks.
 * Encapsulates file I/O and business logic to separate concerns from the CLI in `main`.
 */
class TaskManager(
    private val tasksFile: File = File("tasks.txt"),
) {
    private val tasks: MutableList<Task> = mutableListOf()

    init {
        load() // automatically populate task list on creation
    }

    /**
     * Adds a new task with the given description.
     */
    fun addTask(description: String) {
        if (description.isNotBlank()) {
            tasks.add(Task(description.trim()))
            println("Task added!")
        }
    }

    /**
     * Displays all tasks along with their completion status.
     */
    fun listTasks() {
        if (tasks.isEmpty()) {
            println("No tasks yet.")
        } else {
            tasks.forEachIndexed { index, task ->
                val status = if (task.isDone) "✓" else "✗"
                println("${index + 1}. [$status] ${task.description}")
            }
        }
    }

    /**
     * Marks the task at the specified (1‑based) position as done.
     */
    fun markDone(position: Int) {
        val index = position - 1
        if (index in tasks.indices) {
            tasks[index].isDone = true
            println("Task marked as done!")
        } else {
            println("Invalid task number.")
        }
    }

    /**
     * Persist tasks to disk. Stores each task on its own line using a semi‑colon to
     * separate status and description.
     */
    fun save() {
        runCatching {
            tasksFile.writeText(tasks.joinToString("\n") { "${it.isDone};${it.description}" })
        }.onFailure { err ->
            println("Failed to save tasks: ${err.message}")
        }
    }

    /**
     * Load tasks from disk if the file exists. Ignores malformed lines.
     */
    private fun load() {
        if (tasksFile.exists()) {
            runCatching {
                tasksFile.readLines().forEach { line ->
                    val parts = line.split(";")
                    if (parts.size == 2) {
                        val done = parts[0].toBoolean()
                        val desc = parts[1]
                        tasks.add(Task(desc, done))
                    }
                }
            }.onFailure { err ->
                println("Failed to load tasks: ${err.message}")
            }
        }
    }
}

/**
 * Entry point for the CLI application. Presents a simple menu and delegates
 * actions to a [TaskManager] instance. Loops until the user chooses to exit.
 */
fun main() {
    val manager = TaskManager()

    while (true) {
        println("\n--- Task Tracker ---")
        println("1. Add task")
        println("2. View tasks")
        println("3. Mark task as done")
        println("4. Exit")
        print("Choose an option: ")

        when (readLine()?.trim()) {
            "1" -> {
                print("Enter task description: ")
                val desc = readLine()?.trim()
                if (!desc.isNullOrBlank()) {
                    manager.addTask(desc)
                }
            }
            "2" -> manager.listTasks()
            "3" -> {
                manager.listTasks()
                print("Enter task number to mark as done: ")
                val num = readLine()?.toIntOrNull()
                if (num != null) {
                    manager.markDone(num)
                } else {
                    println("Invalid task number.")
                }
            }
            "4" -> {
                manager.save()
                println("Goodbye!")
                return
            }
            else -> println("Invalid option, try again.")
        }
    }
}
