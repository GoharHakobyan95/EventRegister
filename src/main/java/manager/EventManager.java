package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EventManager {
    private Connection connection;

    public EventManager() {
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(Event event) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("Insert into event (name,place,is_online,price,event_type) Values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, event.getName());
            prepareStatement.setString(2, event.getPlace());
            prepareStatement.setBoolean(3, event.isOnline());
            prepareStatement.setDouble(4, event.getPrice());
            prepareStatement.setString(5, event.getType().name());
            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                event.setId(id);
            }
            System.out.println("Event was added successfully! ");
            System.out.println(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Event> getAllEvents() {
        List<Event> events = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM event");
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setName(resultSet.getString("name"));
                event.setPlace(resultSet.getString("place"));
                event.setOnline(resultSet.getBoolean("is_online"));
                event.setPrice(resultSet.getDouble("price"));
                event.setType(EventType.valueOf(resultSet.getString("event_type")));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}