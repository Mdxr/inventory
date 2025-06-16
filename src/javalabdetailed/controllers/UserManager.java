/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed.controllers;

import DB.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javalabdetailed.models.User;
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

                    String hashedPwd = BCrypt.hashpw(new String(user.getPassword()), BCrypt.gensalt());

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
                if(BCrypt.checkpw(new String(user.getPassword()), rs.getString("password"))){
                    if(rs.getBoolean("verified")){
                        if(rs.getString("email").contains("super")){
                            return "success (super)";
                        }
                        return "success (basic)";
                    }
                    return "unverified";
                    
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
    
    public ArrayList<User> fetchUsers(){
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                User tempUser = new User(rs.getString("name"), rs.getString("email"), rs.getString("password").toCharArray());
                tempUser.setVerfied(rs.getBoolean("verified"));
                tempUser.setID(rs.getInt("id"));
                users.add(tempUser);
            }
        } catch (SQLException e){
            System.out.println("Error fetching users " + e);
        }
        return users;
    }
    
    public void approveUser(int id){
        String sql = "UPDATE users SET verified=1 WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Error approving admin " + e);
        }
    }
    
    public void provokeUser(int id){
        String sql = "UPDATE users SET verified=0 WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Error provoking admin " + e);
        }
    }
    
    public void deleteUser(int id){
        String sql = "DELETE FROM users WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Error deleting admin " + e);
        }
    }
    
    

}
