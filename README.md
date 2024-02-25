## Java Chat App with Oracle Database

This MVC project implements a simple chat application using Java, Oracle Database, and JDBC.

<img src="https://www.gif-maniac.com/gifs/50/49799.gif" width="100px" />

**Project Structure:**

- `src/`: Contains Java source code for the application logic, including:

        - `dao`: oracle commands to create the needed operations for the chat app

        - `controllers`: handles the interaction between the user and the app

        - `utilitaire`: connection to database (im using hr database you can change it to your desired db)

        - `views`: folder representing the views in client-side

  
- `ConnectionPar.properties`: Configuration file for the Oracle database connection (username, password, URL).
  
- `presentation-script bd/`:
  
        - `dataBaseScript.txt`: Script to create necessary tables in the Oracle database.
  
        - `presentation.docs`: docs presentation showcasing the project and its features.
  
        - `diagramme de classe.png`: Class diagram illustrating the application's components and relationships.
  
- `ojdbc6.jar`:driver for connecting to Oracle Database (ensure compatibility with your chosen Oracle version).

**Dependencies:**

- Java Development Kit (JDK)
- Oracle Database server and client libraries

**Getting Started:**

1. **Install the Oracle Database server and client libraries:** Follow Oracle's installation instructions for your environment.
2. **Set up the database:**
    - Create a database user with appropriate permissions.
    - Execute the `create_tables.sql` script in the `presentation-script bd/` folder to create the necessary tables.
    - (Optional) Execute the insert lines script to insert some sample data (useful for testing).
3. **Configure the database connection:**
    - Open `db.properties` and update the following properties:
        - `db.url`: Oracle database URL (e.g., `jdbc:oracle:thin:@localhost:1521/your_database_name`)
        - `db.username`: Database username
        - `db.password`: Database password
4. **Compile and run the application:**
    - Open a terminal in the project directory.
    - Compile the main class (`com.example.chat.Server` or `com.example.chat.Client`) using `javac -cp .:jdbc/* *.java`.

**Additional Notes:**

- You can customize the application's behavior and features by modifying the Java source code.
- Consider implementing security measures (e.g., authentication, authorization) based on your requirements.
- Refer to the class diagram and presentation for a deeper understanding of the project.
- This is a basic example, and you may need to expand it based on your specific needs and functionalities.

**Note:** This README file doesn't contain actual code snippets for the Java classes or SQL scripts. You need to implement those based on your specific functionality and database design.
