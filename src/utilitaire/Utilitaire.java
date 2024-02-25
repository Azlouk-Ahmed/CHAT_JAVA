	package utilitaire;
	import java.io.FileInputStream;
	import java.sql.*;
	import java.util.Properties;

	public class Utilitaire {
		private static Connection cn= null;
		public static Connection seConnecter ( String fproperties)
		{  
			try {	
			if (cn==null) {
			Properties p = new Properties();
			FileInputStream f = new FileInputStream(fproperties);
			p.load(f);
			String driver, url, login, pwd;
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			login=p.getProperty("login");
			pwd= p.getProperty("pwd");
			Class.forName(driver);  //Chargement du driver
			cn=DriverManager.getConnection(url, login, pwd);   //Ouverture d'une connexion
			System.out.println ("Connexion e  tablie");
			
			}
		}
		catch (Exception e)
		{	
			
			
			
			
			
			System.out.println ("Echec de connexion");
			System.out.println (e.getMessage());
			
		}
			return cn;	

		}	
		
		public static ResultSet OuvrirReq(String req)
		{   ResultSet res = null;
			try
		    {   if (cn!=null) {
			    Statement  state = cn.createStatement();
			    res = state.executeQuery(req);   
		    	}
		    else
		    	System.out.println ("Connexion non initialis�e");
		    }
		    catch (SQLException e)
		    {
		    	System.out.println (e.getMessage());
		    }
			finally {
				return res;
			}
		}
		public static Connection getConnection()
		{
			return cn;
		}
		public static void seDeconnecter()
		{
			try {
				cn.close();
			}
			 catch (SQLException e)
		    {
		    	System.out.println (e.getMessage());
		    }
		}
	}

