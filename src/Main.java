import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoListManager {
    private List<Todo> todos;
    private Scanner scanner;
    private String fileName = "todos.txt";
    public static void main(String[] args) {
        TodoListManager todoListManager = new TodoListManager();
        todoListManager.showMenu();
    }

    public TodoListManager() {
        todos = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadTodos();
    }

    private void loadTodos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String title = data[0];
                LocalDate until = LocalDate.parse(data[1]);
                boolean done = Boolean.parseBoolean(data[2]);
                todos.add(new Todo(title, until));
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            // Ignore exceptions for now, new file will be created if it doesn't exist
        }
    }

    private void saveTodos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Todo todo : todos) {
                writer.write(todo.getTitle() + "," + todo.getUntil() + "," + todo.isDone() + "\n");
            }
        } catch (IOException e) {
            // Ignore exceptions for now
        }
    }

    public void showMenu() {
        while (true) {
            System.out.println("Welcome!");
            displayTodos();
            System.out.println("1. Create TODO");
            System.out.println("2. Edit TODO");
            System.out.println("3. Finish TODO");
            System.out.println("4. Delete TODO");
            System.out.println("5. Exit");
            System.out.print("Input: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    createTodo();
                    break;
                case 2:
                    editTodo();
                    break;
                case 3:
                    finishTodo();
                    break;
                case 4:
                    deleteTodo();
                    break;
                case 5:
                    saveTodos();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }
    }

    private void displayTodos() {
        if (todos.isEmpty()) {
            System.out.println("You have no more TODOs left!!!");
        } else {
            int count = 0;
            for (Todo todo : todos) {
                if (!todo.isDone()) {
                    System.out.println((++count) + ". " + todo);
                }
            }
            if (count == 0) {
                System.out.println("You have no more TODOs left!!!");
            } else {
                System.out.println("You have " + count + " TODOs left.");
            }
        }
    }

    private void createTodo() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Until (yyyy-mm-dd): ");
        String untilStr = scanner.nextLine();
        LocalDate until = LocalDate.parse(untilStr);

        todos.add(new Todo(title, until));
        saveTodos();
        System.out.println("Saved!!!");
    }

    private void editTodo() {
        System.out.print("Edit TODO number: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (index < 1 || index > todos.size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        Todo todo = todos.get(index - 1);
        System.out.print("Title (" + todo.getTitle() + "): ");
        String title = scanner.nextLine();

        if (!title.isEmpty()) {
            todo.setTitle(title);
        }

        System.out.print("Until (" + todo.getUntil() + "): ");
        String untilStr = scanner.nextLine();

        if (!untilStr.isEmpty()) {
            LocalDate until = LocalDate.parse(untilStr);
            todo.setUntil(until);
        }

        saveTodos();
        System.out.println("Saved!!!");
    }

    private void finishTodo() {
        System.out.print("Finish TODO number: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (index < 1 || index > todos.size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        Todo todo = todos.get(index - 1);
        todo.markAsDone();
        saveTodos();
        System.out.println("Todo marked as Done!");
    }

    private void deleteTodo() {
        System.out.print("Delete TODO number: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (index < 1 || index > todos.size()) {
            System.out.println("Invalid TODO number.");
            return;
        }

        todos.remove(index - 1);
        saveTodos();
        System.out.println("Todo deleted!");
    }


}

