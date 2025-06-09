/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

import DB.DBManager;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author mudasser
 */
public class HistoryManager {
    public void addLog(HistoryLog log){
        String sql = "INSERT INTO history_logs(product_name, supplier_name, product_id, operation) VALUES (?,?,?,?)";
        
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, log.getProduct().getName());
            stmt.setString(2, log.getSupplier().getName());
            stmt.setInt(3, log.getProduct().getID());
            stmt.setString(4, log.getOperation());
            
            stmt.execute();
            
        } catch (SQLException e){
            System.err.println("Error Logging event " + e);
        }
    }
    
    public ArrayList<HistoryLog> fetchLogs(){
        ArrayList<HistoryLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM history_logs";
        
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
           
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                logs.add(new HistoryLog(rs.getInt("id"), rs.getString("operation") ,new Product(0, rs.getString("product_name"), "", 0, 0), new Supplier(0, rs.getString("supplier_name"), ""), rs.getString("at")));
            }
            return logs;
        } catch (SQLException e){
            System.err.println("Error Logging event " + e);
            return null;
        }
    }
    
}
