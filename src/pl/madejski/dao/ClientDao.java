package pl.madejski.dao;

import pl.madejski.model.*;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientDao {

	private List<Client> clients;
	
	public ClientDao() {
		clients = new ArrayList<>();
		init();
	}
	
	public void init() {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			//Connection conn = ds.getConnection();
			Connection conn=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","Misia123");  
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT client_id, client_name, client_surname FROM client");
			while (rs.next()) {
				clients.add(new Client(
						Long.parseLong(rs.getString("client_id")),
						rs.getString("client_name"),
						rs.getString("client_surname")
				));
			}
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Client> findAll() {
		return clients;
	}
	
	public Client findByClientId(Long ClientId) {
		ListIterator<Client> iterator = clients.listIterator();
		
		 while (iterator.hasNext()) {
			 if(iterator.next().getClientId()==ClientId)
				 return iterator.next();   
		 }
		 return null;
	}
	
	public void save(Client client) {
		// TODO: init JDBC connection once/move create somewhere else
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			//Connection conn = ds.getConnection();
			Connection conn=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","Misia123");  
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO products (productname) VALUES (?)");
			
			
			pstmt.setLong(1,	client.getClientId());
			
			if(pstmt.executeUpdate() != 1) { // shouldn't insert more than 1 row
				pstmt.close();
				conn.close();
				throw new Exception("Wyst¹pi³ b³¹d podczas dodawania produktu!");
			}
			
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
