package manager;


import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private Connection connection;

    public UserManager() {
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addUser(User user) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("Insert into user (name,surname,email,event_id) Values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getSurname());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setInt(4, user.getEventId());
            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
            System.out.println(user);
            System.out.println("User was added successfully! ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            List<User> users = new LinkedList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setEventId(resultSet.getInt("event_id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}

