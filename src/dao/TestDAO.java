package dao;

import java.sql.Date;
import java.util.*;

import models.Conversation;
import models.Message;
import models.User;
import models.UserMessage;
import utilitaire.Utilitaire;

public class TestDAO {

	public static void main(String[] args) {


		DAOUser userDAO = new DAOUser();
        User user1 = new User();
        user1.setUserId(6);
        user1.setUsername("syrine ayeb");
        user1.setPassword("0000");
        user1.setEmail("syrine@example.com");
        user1.setDateCreated(Calendar.getInstance().getTime());

        boolean inserted = userDAO.insert(user1);
        System.out.println("User insert: " + inserted);

        ArrayList<User> allUsers = userDAO.findAll();
        System.out.println("All Users:");
        for (User user : allUsers) {
           System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Date Created: " + user.getDateCreated());
            System.out.println("-------------------------");
        }
		DAOConversation dc = new DAOConversation();
        Conversation c = new Conversation();
        c.setConversationId(8594);
        c.setDateCreated(Calendar.getInstance().getTime());
        c.setUserId1(2);
        c.setUserId2(18);
        System.out.println(dc.insert(c));
        ArrayList<Conversation> allcnv2 = dc.findAll();
        System.out.println("All Users:");
        for (Conversation user : allcnv2) {
            System.out.println(user);
            System.out.println("-------------------------");
        }
        System.out.println(dc.findByID(11));
        Message message = new Message();
        message.setMessageId(1235);
        message.setConversationId(1);
        message.setSenderId(2);
        message.setMessageContent("Hello, how are you?");
        message.setDateSent(Calendar.getInstance().getTime());
        DAOMessage daoMessage = new DAOMessage();
        boolean insertResult = daoMessage.insert(message);
        if (insertResult) {
            System.out.println("Message inserted successfully.");
        } else {
            System.out.println("Failed to insert message.");
        }
        List<Message> messages = daoMessage.findAll();

        for (Message msg : messages) {
            System.out.println("Message ID: " + message.getMessageId());
            System.out.println("Conversation ID: " + message.getConversationId());
            System.out.println("Sender ID: " + message.getSenderId());
            System.out.println("Message Text: " + message.getMessageContent());
            System.out.println("Message Date: " + message.getDateSent());
            System.out.println("------------------------");
        }
        System.out.println(daoMessage.maxId());
        if(dc.hadConversation(2, 19)) {
        	System.out.println("here is the conv id : "+dc.getConvId(2, 18));
        }else {
        	System.out.println("non conv yet ");
        }
        System.out.println(userDAO.maxId());
	}

}
