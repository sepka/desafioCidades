package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	Connection conectar = null;
    
	public  Connection conectar() throws SQLException {
     	    
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conectar = DriverManager.getConnection("jdbc:mysql://localhost/cidades","root","");
	
                  
		} catch (Exception e) {
                    
                }
                
		
		return conectar;
		
		
	}
	
	public void fecha() throws SQLException {
		conectar.close();
		
	}
	
}