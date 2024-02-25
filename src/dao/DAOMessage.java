package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.Conversation;
import models.Message;
import models.User;
import models.UserMessage;
import utilitaire.Utilitaire;

public class DAOMessage implements IDAO<Message> {
	private Connection connection;
	public  DAOMessage()
	{
		
		connection =  Utilitaire.seConnecter("connectionPar.properties");

	}
    @Override
    public Message findByID(int id) {
        Message message = new Message();

        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            ResultSet rs = Utilitaire.OuvrirReq(sql);
            PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1 , id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                message = new Message();
                message.setMessageId(rs.getLong("message_id"));
                message.setMessageContent(rs.getString("MESSAGE_CONTENT"));
                message.setSenderId(rs.getLong("sender_id"));
                message.setConversationId(rs.getLong("conversation_id"));
                message.setDateSent(rs.getDate("DATE_SENT"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }
    
    public int maxId() {
        int max = 0;
        try {
            String sql = "SELECT MAX(message_id) AS max_id FROM message";
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
    public ArrayList<Message> findAll() {
        ArrayList<Message> list = new ArrayList<Message>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	Message message = new Message();
                message.setMessageId(resultSet.getLong("message_id"));
                message.setMessageContent(resultSet.getString("MESSAGE_CONTENT"));
                message.setSenderId(resultSet.getLong("sender_id"));
                message.setConversationId(resultSet.getLong("conversation_id"));
                message.setDateSent(resultSet.getDate("DATE_SENT"));
                list.add(message);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    
    public ArrayList<UserMessage> findConvBetweenTwo(int id1, int id2) {
        ArrayList<UserMessage> list = new ArrayList<UserMessage>();
        try {
            String sql = "SELECT m.date_sent, u.username, m.message_content " +
                         "FROM message m " +
                         "JOIN user_table u ON u.user_id = m.sender_id " +
                         "JOIN conversation c ON m.conversation_id = c.conversation_id " +
                         "WHERE (c.user_id1 = ? AND c.user_id2 = ?) OR (c.user_id1 = ? AND c.user_id2 = ?) " +
                         "ORDER BY m.insert_timestamp DESC";
            System.out.println(sql);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
            statement.setInt(3, id2);
            statement.setInt(4, id1);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserMessage message = new UserMessage();
                message.setMessage(resultSet.getString("message_content"));
                message.setName(resultSet.getString("username"));
                message.setDateSent(resultSet.getDate("date_sent"));
                list.add(message);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

 
    @Override
    public boolean insert(Message message) {
        try {
            String sql = "INSERT INTO Message (message_id, conversation_id, sender_id, message_content, date_sent,insert_timestamp) VALUES (?,?,?,?,?, CURRENT_TIMESTAMP)";
            //        + message.getMessageId() + ", "
             //       + message.getConversationId() + ", "
             //       + message.getSenderId() + ", "
            //        + "'" + message.getMessageContent() + "', "
            //        + "TO_TIMESTAMP('" + new java.sql.Timestamp(message.getDateSent().getTime()) + "', 'YYYY-MM-DD HH24:MI:SS.FF'))";
            System.out.println(sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,message.getMessageId());
            statement.setLong(2,message.getConversationId());
            statement.setLong(3, message.getSenderId());
            statement.setString(4, message.getMessageContent());
            statement.setDate(5, new java.sql.Date(message.getDateSent().getTime()));
            int rowsInsert = statement.executeUpdate();
            statement.close();
            if(rowsInsert>0)
				return true;
	        }catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
    }
}
