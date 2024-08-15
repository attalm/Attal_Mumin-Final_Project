import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *Program: TaskListApp
 *
 * A task list management application using a GUI.
 * Allows users to navigate through tasks, add new tasks,
 * and save them to a file.
 *
 * Author: Attal
 * Date: 15/08/2024
 * Purpose: To manage and maintain a list of tasks with deadlines and completion status.
 * Version: 1.0
 */
public class TaskListApp extends JFrame {

    // List to store tasks
    private ArrayList<Task> tasks;
    // Index to track the current task displayed
    private int currentTaskIndex = 0;

    // UI Components
    private JTextField taskIdField, taskNameField, taskDeadlineField;
    private JCheckBox taskCompleteCheckBox;
    private JButton forwardButton, backButton, saveTaskButton, saveListButton, newTaskButton;

    /**
     * Constructor to initialize the task list and set up the UI.
     */
    public TaskListApp() {
        tasks = loadTasks(); // Load existing tasks from file
        setupUI(); // Set up the user interface
        updateTaskDetails(); // Display the first task
    }

    /**
     * Sets up the user interface components and layout.
     */
    private void setupUI() {
        // Set window title and properties
        setTitle("Task List Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // BorderLayout with gaps
        setResizable(false);

        // Set look and feel for consistency across platforms
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Top section: Task ID and navigation buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        taskIdField = new JTextField(5);
        taskIdField.setEditable(false);
        taskIdField.setFont(new Font("Arial", Font.BOLD, 14));

        forwardButton = new JButton("Next >");
        backButton = new JButton("< Previous");

        forwardButton.setBackground(new Color(173, 216, 230));
        backButton.setBackground(new Color(173, 216, 230));

        // Set up action listeners for navigation buttons
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTaskIndex < tasks.size() - 1) {
                    currentTaskIndex++;
                    updateTaskDetails();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTaskIndex > 0) {
                    currentTaskIndex--;
                    updateTaskDetails();
                }
            }
        });

        // Add components to the top panel
        topPanel.add(new JLabel("Task ID:"));
        topPanel.add(taskIdField);
        topPanel.add(backButton);
        topPanel.add(forwardButton);

        // Bottom section: Task details and action buttons
        JPanel bottomPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        taskNameField = new JTextField(20);
        taskDeadlineField = new JTextField(20);
        taskCompleteCheckBox = new JCheckBox("Complete");

        taskNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        taskDeadlineField.setFont(new Font("Arial", Font.PLAIN, 14));
        taskCompleteCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to the bottom panel
        bottomPanel.add(new JLabel("Task Name:"));
        bottomPanel.add(taskNameField);
        bottomPanel.add(new JLabel("Deadline (dd/MM/yyyy):"));
        bottomPanel.add(taskDeadlineField);
        bottomPanel.add(new JLabel("Complete:"));
        bottomPanel.add(taskCompleteCheckBox);

        // Initialize action buttons with colors and fonts
        saveTaskButton = new JButton("Save Task");
        newTaskButton = new JButton("New Task");
        saveListButton = new JButton("Save List");

        saveTaskButton.setBackground(new Color(144, 238, 144));
        newTaskButton.setBackground(new Color(255, 228, 181));
        saveListButton.setBackground(new Color(173, 216, 230));

        saveTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        newTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveListButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Set up action listeners for action buttons
        saveTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurrentTask();
            }
        });

        newTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTask();
            }
        });

        saveListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasksToFile();
            }
        });

        // Add buttons to the bottom panel
        bottomPanel.add(saveTaskButton);
        bottomPanel.add(newTaskButton);
        bottomPanel.add(saveListButton);

        // Add panels to the main frame
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        // Set background color and finalize frame
        getContentPane().setBackground(new Color(240, 248, 255));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Updates the UI fields with the current task details.
     */
    private void updateTaskDetails() {
        if (tasks.isEmpty()) {
            taskIdField.setText("");
            taskNameField.setText("");
            taskDeadlineField.setText("");
            taskCompleteCheckBox.setSelected(false);
            forwardButton.setEnabled(false);
            backButton.setEnabled(false);
        } else {
            Task task = tasks.get(currentTaskIndex);
            taskIdField.setText(String.valueOf(task.getTaskId()));
            taskNameField.setText(task.getName());
            taskDeadlineField.setText(new SimpleDateFormat("dd/MM/yyyy").format(task.getDeadline()));
            taskCompleteCheckBox.setSelected(task.isComplete());
            forwardButton.setEnabled(currentTaskIndex < tasks.size() - 1);
            backButton.setEnabled(currentTaskIndex > 0);
        }
    }

    /**
     * Saves the current task's details from the UI to the task list.
     */
    private void saveCurrentTask() {
        if (!tasks.isEmpty()) {
            Task task = tasks.get(currentTaskIndex);
            task.setName(taskNameField.getText());
            try {
                Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(taskDeadlineField.getText());
                task.setDeadline(deadline);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use dd/MM/yyyy.");
                return;
            }
            task.setComplete(taskCompleteCheckBox.isSelected());
        }
    }

    /**
     * Adds a new task to the task list.
     */
    private void addNewTask() {
        try {
            String name = JOptionPane.showInputDialog("Enter Task Name:");
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Task name cannot be empty.");
                return;
            }

            String deadlineStr = JOptionPane.showInputDialog("Enter Deadline (dd/MM/yyyy):");
            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(deadlineStr);
            Task newTask = new Task(name, deadline);
            tasks.add(newTask);
            currentTaskIndex = tasks.size() - 1;
            updateTaskDetails();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use dd/MM/yyyy.");
        }
    }

    /**
     * Saves the task list to a file.
     */
    private void saveTasksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
            oos.writeObject(tasks);
            JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
        }
    }

    /**
     * Loads tasks from a file into the task list.
     *
     * @return ArrayList of Task objects
     */
    private ArrayList<Task> loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tasks.dat"))) {
            return (ArrayList<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    /**
     * The main method to start the TaskListApp.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskListApp();
            }
        });
    }
}

