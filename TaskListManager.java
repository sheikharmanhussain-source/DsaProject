import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Task {
    private int id;
    private String title;
    private int priority;

    public Task(int id, String title, int priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    private String getPriorityLabel() {
        switch (priority) {
            case 1: return "High";
            case 2: return "Medium";
            case 3: return "Low";
            default: return "Normal";
        }
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %-25s | Priority: %s", id, title, getPriorityLabel());
    }
}

public class TaskListManager {
    private static ArrayList<Task> pendingTasks = new ArrayList<>();
    private static LinkedList<Task> completedTasks = new LinkedList<>();
    private static int taskCounter = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        System.out.println("==========================================");
        System.out.println("        SIMPLE TASK LIST MANAGER          ");
        System.out.println("==========================================");

        do {
            printMenu();
            choice = getValidInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewPendingTasks();
                    break;
                case 3:
                    completeTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    sortTasksByPriority();
                    break;
                case 6:
                    sortTasksAlphabetically();
                    break;
                case 7:
                    viewCompletedTasks();
                    break;
                case 8:
                    System.out.println("Exiting Task List Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 8.");
            }
        } while (choice != 8);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add New Task");
        System.out.println("2. View Pending Tasks (ArrayList)");
        System.out.println("3. Mark Task as Completed (Move to LinkedList)");
        System.out.println("4. Delete a Pending Task");
        System.out.println("5. Sort Pending Tasks by Priority");
        System.out.println("6. Sort Pending Tasks Alphabetically");
        System.out.println("7. View Completed Tasks History (LinkedList)");
        System.out.println("8. Exit");
    }

    private static void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Task title cannot be empty.");
            return;
        }

        int priority;
        while (true) {
            priority = getValidInt("Enter priority (1 = High, 2 = Medium, 3 = Low): ");
            if (priority >= 1 && priority <= 3) break;
            System.out.println("Please enter 1, 2, or 3.");
        }

        Task newTask = new Task(taskCounter++, title, priority);
        pendingTasks.add(newTask);
        System.out.println("Task added successfully!");
    }

    private static void viewPendingTasks() {
        System.out.println("\n--- PENDING TASKS ---");
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks.");
            return;
        }
        for (Task t : pendingTasks) {
            System.out.println(t);
        }
    }

    private static void completeTask() {
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks to complete.");
            return;
        }
        viewPendingTasks();
        int id = getValidInt("Enter Task ID to mark as completed: ");

        for (int i = 0; i < pendingTasks.size(); i++) {
            if (pendingTasks.get(i).getId() == id) {
                Task completed = pendingTasks.remove(i);
                completedTasks.addFirst(completed);
                System.out.println("Marked as completed: " + completed.getTitle());
                return;
            }
        }
        System.out.println("Task ID not found.");
    }

    private static void deleteTask() {
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks to delete.");
            return;
        }
        viewPendingTasks();
        int id = getValidInt("Enter Task ID to delete: ");

        for (int i = 0; i < pendingTasks.size(); i++) {
            if (pendingTasks.get(i).getId() == id) {
                Task removed = pendingTasks.remove(i);
                System.out.println("Deleted task: " + removed.getTitle());
                return;
            }
        }
        System.out.println("Task ID not found.");
    }

    private static void sortTasksByPriority() {
        int n = pendingTasks.size();
        if (n < 2) {
            System.out.println("Not enough tasks to sort.");
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pendingTasks.get(j).getPriority() > pendingTasks.get(j + 1).getPriority()) {
                    Task temp = pendingTasks.get(j);
                    pendingTasks.set(j, pendingTasks.get(j + 1));
                    pendingTasks.set(j + 1, temp);
                }
            }
        }
        System.out.println("Tasks sorted by Priority (High to Low)!");
        viewPendingTasks();
    }

    private static void sortTasksAlphabetically() {
        int n = pendingTasks.size();
        if (n < 2) {
            System.out.println("Not enough tasks to sort.");
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pendingTasks.get(j).getTitle().compareToIgnoreCase(pendingTasks.get(j + 1).getTitle()) > 0) {
                    Task temp = pendingTasks.get(j);
                    pendingTasks.set(j, pendingTasks.get(j + 1));
                    pendingTasks.set(j + 1, temp);
                }
            }
        }
        System.out.println("Tasks sorted Alphabetically!");
        viewPendingTasks();
    }

    private static void viewCompletedTasks() {
        System.out.println("\n--- COMPLETED TASKS (Most Recent First) ---");
        if (completedTasks.isEmpty()) {
            System.out.println("No completed tasks yet.");
            return;
        }
        for (Task t : completedTasks) {
            System.out.println(t);
        }
    }

    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}