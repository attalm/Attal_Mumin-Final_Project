import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * Task
 *
 * A simple class that reads the task and writes it down in a layered format putting it into a txt file
 *
 * Author: Attal
 * Date: 15/08/2024
 * Version: 1.0
 */
public class TaskListReader {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tasks.dat"))) {
            // Deserialize the ArrayList of Task objects
            ArrayList<Task> tasks = (ArrayList<Task>) ois.readObject();

            // Display each task's details
            for (Task task : tasks) {
                System.out.println(task);
                System.out.println("-------------------------");
            }

            // Optionally, write the tasks to a text file
            try (PrintWriter writer = new PrintWriter(new FileWriter("tasks_output.txt"))) {
                for (Task task : tasks) {
                    writer.println(task);
                    writer.println("-------------------------");
                }
                System.out.println("Task details saved to tasks_output.txt");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

