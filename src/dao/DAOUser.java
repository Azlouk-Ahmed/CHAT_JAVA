package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.Conversation;
import models.User;
import utilitaire.Utilitaire;

public class DAOUser implements IDAO<User> {
	private Connection connection;
	public  DAOUser()
	{
		
		connection =  Utilitaire.seConnecter("connectionPar.properties");

	}
	
	public User getUserByEmailAndPassword(String email, String password) {
		User user = new User();

        try {
            String sql = "select * from user_table where EMAIL = ? and PASSWORD = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1 , email);
			statement.setString(2 , password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateCreated(resultSet.getDate("date_created"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {System.out.println (e.getMessage());}
        return user;
	}	
    @Override
    public User findByID(int id) {
        User user = new User();

        try {
            String sql = "select * from user_table where user_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1 , id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateCreated(resultSet.getDate("date_created"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {System.out.println (e.getMessage());}
        return user;
    }

	@Override
	public ArrayList<User> findAll() {
		ArrayList <User> list = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM user_table";
			PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateCreated(resultSet.getDate("date_created"));
                list.add(user);
			}
			resultSet.close();
            statement.close();
		}catch (SQLException e) {System.out.println (e.getMessage());}
		return list;
	}
	public ArrayList<User> findAllExcept(int id) {
		ArrayList <User> list = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM user_table where user_id <>"+id;
			PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateCreated(resultSet.getDate("date_created"));
                list.add(user);
			}
			resultSet.close();
            statement.close();
		}catch (SQLException e) {System.out.println (e.getMessage());}
		return list;
	}
	
	@Override
	public boolean insert(User o) {
		try
		{
	    String sql = "INSERT INTO User_Table (user_id, username, password, email, date_created) values (?, ?, ?, ?, ?)";
	    PreparedStatement s = connection.prepareStatement(sql);
	    s.setLong(1, o.getUserId());
        s.setString(2, o.getUsername());
        s.setString(3, o.getPassword());
        s.setString(4, o.getEmail());
        s.setDate(5, new java.sql.Date(o.getDateCreated().getTime()));
        int rowsInsert = s.executeUpdate();
        s.close();
        if(rowsInsert>0)
			return true;
        }
		catch (SQLException e)
		
		{
			System.out.println (e.getMessage());
		}
		return false;
	}
	public User findByUsername(String username) {
        User user = null;
        try {
            String sql = "SELECT * FROM user_table WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
	public ArrayList<User> searchUsers(String searchTerm) {
	    ArrayList<User> users = new ArrayList<>();
	    try {
	        String sql = "SELECT * FROM user_table WHERE username LIKE ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, "%" + searchTerm + "%");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            User user = new User();
	            user.setUserId(resultSet.getInt("user_id"));
	            user.setUsername(resultSet.getString("username"));
	            // Set other user properties if needed
	            users.add(user);
	        }
	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return users;
	}
	public void deleteUserByName(String username) {
        try {
            String sql = "DELETE FROM user_table WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
            System.out.println("User deleted successfully: " + username);
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
	
	public int maxId() {
        int max = 0;
        try {
            String sql = "SELECT MAX(USER_ID) AS max_id FROM user_table";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                max = resultSet.getInt("max_id");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return max;
    }
}