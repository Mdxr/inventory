/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

import DB.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
 
public class UserManager {
    public String register(User user) {
        String sql = "SELECT * FROM users WHERE email=?";
        try(Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return "Admin Already Registered!";
            } else {
                sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, user.getName());
                    stmt.setString(2, user.getEmail());

                    String hashedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

                    stmt.setString(3, hashedPwd);
                    stmt.executeUpdate();

                    return "success";

                } catch (SQLException e) {
                    return "Error adding new Admin!" + e;
                }
            }
            
        } catch (SQLException e){
            return "Error Adding new Admin!" + e;
        }
    }
    
    public String login(User user){
        String sql = "SELECT * FROM users WHERE email=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                if(BCrypt.checkpw(user.getPassword(), rs.getString("password"))){
                    return "success";
                } else {
                    return "Password is invalid!";
                }
            } else {
                return "Admin doesn't exist!";
            }
            
        } catch (SQLException e){
            return "Error Logging in!" + e;
        }
    }

}
