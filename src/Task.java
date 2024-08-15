import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task
 *
 * A simple class representing a task with a name, deadline, and completion status.
 *
 * Author: [Your Name]
 * Date: [Current Date]
 * Version: 1.0
 */
class Task implements Serializable {
    private static int taskCounter = 1;
    private int taskId;
    private String name;
    private Date deadline;
    private boolean complete;

    /**
     * Constructor to create a new Task object.
     *
     * @param name     The name of the task
     * @param deadline The deadline of the task
     */
    public Task(String name, Date deadline) {
        this.taskId = taskCounter++;
        this.name = name;
        this.deadline = deadline;
        this.complete = false;
    }

    /**
     * Gets the task ID.
     *
     * @return The task ID
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Gets the task name.
     *
     * @return The task name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the task name.
     *
     * @param name The new task name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the task deadline.
     *
     * @return The task deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the task deadline.
     *
     * @param deadline The new task deadline
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Checks if the task is complete.
     *
     * @return True if the task is complete, false otherwise
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Sets the task completion status.
     *
     * @param complete The new completion status
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Overrides the toString() method to provide a string representation of the task.
     *
     * @return A string representation of the task
     */
    @Override
    public String toString() {
        return "Task ID: " + taskId + "\nTask Name: " + name + "\nDeadline: " + new SimpleDateFormat("dd/MM/yyyy").format(deadline) + "\nComplete: " + complete;
    }
}
