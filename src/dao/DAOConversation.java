package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Conversation;
import utilitaire.Utilitaire;

public class DAOConversation implements IDAO<Conversation> {
	private Connection connection;
	public  DAOConversation()
	{
		
		connection =  Utilitaire.seConnecter("connectionPar.properties");

	}
	public int maxId() {
        int max = 0;
        try {
            String sql = "SELECT MAX(CONVERSATION_ID) AS max_id FROM conversation";
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
    @Override
    public Conversation findByID(int id) {
        Conversation conversation = new Conversation();

        try {
            String sql = "SELECT * FROM Conversation WHERE conversation_id = ?" ;
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1 , id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                conversation.setConversationId(resultSet.getLong("conversation_id"));
                conversation.setUserId1(resultSet.getLong("user_id1"));
                conversation.setUserId2(resultSet.getLong("user_id2"));
                conversation.setDateCreated(resultSet.getDate("date_created"));
                
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversation;
    }

    @Override
    public ArrayList<Conversation> findAll() {
        ArrayList<Conversation> conversations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Conversation";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Conversation conversation = new Conversation();
                conversation.setConversationId(resultSet.getLong("conversation_id"));
                conversation.setUserId1(resultSet.getLong("user_id1"));
                conversation.setUserId2(resultSet.getLong("user_id2"));
                conversation.setDateCreated(resultSet.getDate("date_created"));

                conversations.add(conversation);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conversations;
    }


    @Override
    public boolean insert(Conversation conversation) {

        try {
        	String sql = "INSERT INTO Conversation (conversation_id, user_id1, user_id2, date_created) VALUES (?,?,?,?)";
        	PreparedStatement statement = connection.prepareStatement(sql);
        	statement.setLong(1,conversation.getConversationId());
            statement.setLong(2, conversation.getUserId1());
            statement.setLong(3, conversation.getUserId2());
            java.util.Date dateCreated = conversation.getDateCreated();
            java.sql.Date sqlDateCreated = new java.sql.Date(dateCreated.getTime());
            statement.setDate(4, sqlDateCreated);
        	int rowsInsert = statement.executeUpdate();
            statement.close();
            if(rowsInsert>0)
				return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean hadConversation(int id1, int id2) {
    	boolean test = false;
    	try {
            String sql = "SELECT * FROM Conversation WHERE user_id1 = ? and user_id2= ?" ;
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1 , id1);
			statement.setInt(2 , id2);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                test = true;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return test;
    }
    
    public int getConvId(int id1, int id2) {
    	int id = 0;
    	try {
            String sql = "SELECT * FROM Conversation WHERE user_id1 = ? and user_id2= ?" ;
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1 , id1);
			statement.setInt(2 , id2);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                id = resultSet.getInt("CONVERSATION_ID");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return id;
    }

}
