import command.EventCommands;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.EventType;
import model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main implements EventCommands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static UserManager userManager = new UserManager();
    private static EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            EventCommands.printCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
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
        System.out.println("please input event's name, place, isOnline, price, eventType " + EventType.CONFERENCE +
                " or " + EventType.WEBINAR + ", " + "event_date");
        try {
            String eventDataStr = SCANNER.nextLine();
            String[] eventData = eventDataStr.split(",");
            Event event = Event.builder()
                    .name(eventData[0])
                    .place(eventData[1])
                    .isOnline(Boolean.valueOf(eventData[2]))
                    .price(Double.parseDouble(eventData[3]))
                    .type(EventType.valueOf(eventData[4]))
                    .eventDate(sdf.parse(eventData[5]))
                    .build();
            eventManager.addEvent(event);
            System.out.println("Event has been successfully added");
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Please try again! (input valid number or event's valid type). ");
        } catch (ParseException e) {
            System.out.println("Invalid Date format.");
        }
    }

    private static void addUser() {
        showEvents();
        System.out.println("Please choose event Id");
        int eventId = Integer.parseInt(SCANNER.nextLine());
        Event event = eventManager.getById(eventId);
        if (event == null) {
            System.out.println("Please choose correct Event's id. ");
        } else {
            System.out.println("please input user's name,surname,email,event's Id");
            String userDataStr = SCANNER.nextLine();
            String[] userData = userDataStr.split(",");
            User user = User.builder()
                    .name(userData[0])
                    .surname(userData[1])
                    .email(userData[3])
                    .event(event)
                    .build();
            userManager.addUser(user);
            System.out.println("User has been successfully added.");

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


