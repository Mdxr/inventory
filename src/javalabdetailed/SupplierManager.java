/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;
import DB.DBManager;
import java.sql.*;
/**
 *
 * @author mudasser
 */
public class SupplierManager {
    public String addSupplier(Supplier supplier){
        String sql = "SELECT * FROM suppliers WHERE email=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, supplier.getEmail());
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                sql = "INSERT INTO suppliers(name, email) VALUES (?,?)";
                try(PreparedStatement pstmt = conn.prepareStatement(sql)){
                    pstmt.setString(1, supplier.getName());
                    pstmt.setString(2, supplier.getEmail());
                    pstmt.execute();
                    
                    return "success";
                } catch (SQLException e){
                    return "Error Adding supplier " + e;
                }
            } else {
                return "Supplier already exists!";
            }
            
        } catch (SQLException e){
            return "Error Fetching Supplier (sManager)" + e;
        }
    }
    
    public int getSupplierID(String name, String email){
        String sql = "SELECT * FROM suppliers WHERE name=? AND email=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setString(2, email);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        } catch (SQLException e){
            System.err.println("Error fetching supplier " + e);
            return 0;
        }
    }
    public void deleteSupplier(Supplier supplier){
        int count = 0;
        String sql = "SELECT * FROM products WHERE supplier_id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, supplier.getID());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                count++;
            }
            if(count < 1){
                sql = "DELETE FROM suppliers WHERE id=?";
                try(PreparedStatement sStmt = conn.prepareStatement(sql)){
                    sStmt.setInt(1, supplier.getID());
                    
                    sStmt.execute();
                } catch (SQLException e){
                    System.out.println("Error Deleting supplier " + e);
                }
            }
        } catch (SQLException e){
            System.out.println("Error Fetching products " + e);
        }
    }
}
