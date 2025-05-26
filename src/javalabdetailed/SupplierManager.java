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
}
