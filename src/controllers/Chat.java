package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dao.DAOConversation;
import dao.DAOMessage;
import dao.DAOUser;
import models.*;
import views.CombinedView;

import views.Login;
import views.SignUpView;


public class Chat  implements ActionListener{
	 static CombinedView fr1;
	 static Login fr2;
	 static SignUpView fr3;
	 private User user = new User();
	 private User user2;
	 Message msg = new Message();
	 DAOUser du = new DAOUser();
	 DAOConversation dc = new DAOConversation();
	 DAOMessage dm = new DAOMessage();
	 int chatID = dm.maxId()+1;
	 int convID = dc.maxId()+1;
	 int userID = du.maxId()+1;
	 private Conversation conv = new Conversation();
	 User connectedUser = new User();
	 public Chat(JFrame  f) {
		 if (f instanceof CombinedView)
            fr1 = (CombinedView) f;
        if (f instanceof Login)
            fr2 = (Login) f;
        if (f instanceof SignUpView)
            fr3 = (SignUpView) f;
        if (fr1 != null) {
            user = fr1.user;
        } else {
        	System.out.println("No user connected");
        }
	 }
	 public void actionPerformed(ActionEvent arg0) {
		 JButton b = (JButton)arg0.getSource();
			if (b.getText()=="Send")
			{
				if (fr1.selectedUser != null) {
					if(dc.hadConversation(fr1.selectedUser.getUserId(), user.getUserId())) {
						System.out.println("here is the conv id : "+dc.getConvId(fr1.selectedUser.getUserId(), user.getUserId()));
						msg.setConversationId(dc.getConvId(fr1.selectedUser.getUserId(), user.getUserId()));
			            msg.setMessageContent(fr1.messageTextField.getText());
			            msg.setMessageId(chatID);
			            msg.setSenderId(user.getUserId());
			            msg.setDateSent(Calendar.getInstance().getTime());
			            System.out.println("msg insert: " + dm.insert(msg));
			            chatID++;
			            ArrayList<UserMessage> list = dm.findConvBetweenTwo(fr1.selectedUser.getUserId(), user.getUserId());
			            fr1.messageTextField.setText("");
			            fr1.chatTextArea.setText("");
			            for (UserMessage message : list) {
			                fr1.chatTextArea.append(
			                        message.getName() + " : " + message.getMessage() + " at: " + message.getDateSent() + " \n");
			            }
					}else {
						System.out.println("hey "+user.getUsername()+" you didnt have a conv yet with "+fr1.selectedUser.getUsername()+" let's insert a conv :");
						conv.setConversationId(convID);
			        	conv.setDateCreated(Calendar.getInstance().getTime());
			        	conv.setUserId1(fr1.selectedUser.getUserId());
			        	conv.setUserId2(user.getUserId());
			        	convID++;
			        	boolean convInserted = dc.insert(conv);
			        	if(convInserted) {
			        		System.out.println("conv inserted succefully , go and chat ; ");
			        		msg.setConversationId(dc.getConvId(fr1.selectedUser.getUserId(), user.getUserId()));
				            msg.setMessageContent(fr1.messageTextField.getText());
				            msg.setMessageId(chatID);
				            msg.setSenderId(user.getUserId());
				            msg.setDateSent(Calendar.getInstance().getTime());
				            System.out.println("msg inserted afterr inserting conversation : " + dm.insert(msg));
				            chatID++;
				            fr1.chatTextArea.setText("");
				            fr1.messageTextField.setText("");
				            ArrayList<UserMessage> list = dm.findConvBetweenTwo(fr1.selectedUser.getUserId(), user.getUserId());
				            for (UserMessage message : list) {
				                fr1.chatTextArea.append(
				                        message.getName() + " : " + message.getMessage() + " at: " + message.getDateSent() + " \n");
				            }
			        	}else {
			        		System.out.println("failed to insert conv");
			        	}
					}     
		        } else {
		            System.out.println("No user selected");
		        }
			}else if(b.getText()=="Login") {
				DAOUser du = new DAOUser();
				String email = fr2.emailField.getText();
				String pw = fr2.passwordField.getText();
				connectedUser = du.getUserByEmailAndPassword(email, pw);
				if(connectedUser.getUserId() != 0) {
					System.out.println("user found : "+connectedUser);
					new CombinedView(connectedUser);
					fr2.dispose();
					
				}else {
					System.out.println("no user found ! ");
					JOptionPane.showMessageDialog(fr2, "no user found !", "errror", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}else if (b.getText()=="Search") {
				
				String searchTerm = fr1.searchField.getText();
				
				Collection<User> foundUsers = du.searchUsers(searchTerm);
				System.out.println(foundUsers.size());
				fr1.conversationListModel.clear();
		        for (User foundUser : foundUsers) {
		            fr1.conversationListModel.addElement(foundUser.getUsername());  
		        }
			}else if(b.getText()=="Sign Up") {
				String username = fr3.usernameField.getText();
                String email = fr3.emailField.getText();
                String password = new String(fr3.passwordField.getPassword());
                String confirmPassword = new String(fr3.confirmPasswordField.getPassword());

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(fr3, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(fr3, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User registeredUser = new User();
                    registeredUser.setEmail(email);
                    registeredUser.setPassword(password);
                    registeredUser.setUserId(userID);
                    registeredUser.setUsername(username);
                    registeredUser.setDateCreated(Calendar.getInstance().getTime());
                    if(du.insert(registeredUser)) {
                    	userID++;
                    	JOptionPane.showMessageDialog(fr3, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    	fr3.dispose();
                    	new Login();
                    }else {
                    	JOptionPane.showMessageDialog(fr3, "User not registerd", "errror", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                }

			}else if(b.getText()=="Sign Up Now") {
				fr2.dispose();
				new SignUpView();
			}else if(b.getText()=="Login Now") {
				fr3.dispose();
				new Login();
			}else if(b.getText()=="Logout") {
				fr1.dispose();
				new Login();
			}
	 }
}
