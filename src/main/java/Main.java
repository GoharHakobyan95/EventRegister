import command.Commands;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.EventType;
import model.User;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main implements Commands {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            Commands.printCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    run = false;
                    break;
                case ADD_EVENT:
                    addEvent();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case SHOW_EVENTS:
                    showEvents();
                    break;
                case SHOW_USERS:
                    showUsers();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }


    private static void addEvent() {
        System.out.println("please input event's name");
        String name = scanner.nextLine();

        System.out.println("please input place's name");
        String place = scanner.nextLine();

        try {
            System.out.println("please input 'true' if event online, else 'false'");
            boolean isOnline = Boolean.parseBoolean(scanner.nextLine());

            System.out.println("please input  price");
            String priceStr = scanner.nextLine();
            double price = Double.parseDouble(priceStr);

            System.out.println("Please input event's type " + EventType.CONFERENCE + " or " + EventType.WEBINAR);
            EventType eventType = EventType.valueOf(scanner.nextLine());

            eventManager.addEvent(new Event(name, place, isOnline, price, eventType));
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Please try again! (input valid number or event's valid type). ");
        }
    }

    private static void addUser() {
        System.out.println("please input user's name");
        String name = scanner.nextLine();

        System.out.println("please input user's surname");
        String surname = scanner.nextLine();

        System.out.println("please input user's email");
        String email = scanner.nextLine();

        try {
            System.out.println("please input event Id");
            String idStr = scanner.nextLine();
            int id = Integer.parseInt(idStr);

            userManager.addUser(new User(name, surname, email, id));

        } catch (NumberFormatException e) {
            System.out.println("Please input valid number! ");
        }
    }

    private static void showEvents() {
        List<Event> allEvents = eventManager.getAllEvents();
        for (Event events : allEvents) {
            System.out.println(events);
        }
    }

    private static void showUsers() {
        List<User> allUsers = userManager.getAllUsers();
        for (User users : allUsers) {
            System.out.println(users);
        }
    }
}


