package views;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controllers.Chat;
import dao.DAOMessage;
import dao.DAOUser;
import models.User;
import models.UserMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CombinedView extends JFrame {
    public JTextField searchField = new JTextField();
    private JList<String> conversationList;
    public JTextArea chatTextArea;
    public JTextField messageTextField;
    private JButton sendButton;
    private JButton searchButton;
    private JButton logoutButton; 
    private int selectedUserId;
    public User selectedUser;
    public User user;
    Collection<User> users;
    public DefaultListModel<String> conversationListModel;

    public CombinedView(User user1) {
        super("Chat Application");
        setTitle("Chat Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        DAOUser userDAO = new DAOUser();
        user = user1;
        System.out.println("the connected user is: " + user.getUsername());
        DAOMessage messageDAO = new DAOMessage();
        users = userDAO.findAllExcept(user.getUserId());

        conversationListModel = new DefaultListModel<>();
        for (User user : users) {
            conversationListModel.addElement(user.getUsername());
        }

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete User");
        popupMenu.add(deleteMenuItem);
        conversationList = new JList<>(conversationListModel);
        conversationList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedUsername = conversationList.getSelectedValue();
                    if (selectedUsername != null) {
                        selectedUser = userDAO.findByUsername(selectedUsername);
                        selectedUserId = selectedUser.getUserId();
                        ArrayList<UserMessage> list = messageDAO.findConvBetweenTwo(selectedUserId, user.getUserId());

                        chatTextArea.setText("");

                        if (list.isEmpty()) {
                            chatTextArea.append("No messages yet, send a message to " + selectedUser.getUsername());
                        } else {
                            for (UserMessage message : list) {
                                chatTextArea.append(message.getName() + " : " + message.getMessage()
                                        + " at: " + message.getDateSent() + "\n");
                            }
                        }
                    }
                }
            }
        });
        conversationList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = conversationList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        conversationList.setSelectedIndex(index);
                        popupMenu.show(conversationList, e.getX(), e.getY());
                    }
                }
            }
        });
        deleteMenuItem.addActionListener(e -> {
            String selectedUser = conversationList.getSelectedValue();
            if (selectedUser != null) {
                userDAO.deleteUserByName(selectedUser);
                users = userDAO.findAllExcept(user.getUserId());
                conversationListModel.clear();
                for (User user : users) {
                    conversationListModel.addElement(user.getUsername());
                }
            }
        });
        JScrollPane conversationScrollPane = new JScrollPane(conversationList);

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPanel, conversationScrollPane);
        leftSplitPane.setDividerLocation(40);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        messageTextField = new JTextField();
        sendButton = new JButton("Send");
        messageTextField = new JTextField();
        messageTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        chatTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(messageTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new Chat(this));
        sendButton.addActionListener(new Chat(this));
        searchButton.addActionListener(new Chat(this));

        searchButton.setBackground(new Color(135, 206, 250)); 
        sendButton.setBackground(new Color(135, 206, 250)); 
        logoutButton.setBackground(new Color(255, 165, 0)); 

        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.add(logoutButton, BorderLayout.CENTER);
        inputPanel.add(logoutPanel, BorderLayout.WEST);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane, chatPanel);
        mainSplitPane.setDividerLocation(200);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(mainSplitPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }
}
