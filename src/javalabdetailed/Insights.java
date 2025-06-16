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
public class Insights {
    public int getProducts(){
        int products = 0;
        String sql = "SELECT * FROM products";
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                products++;
            }
            
        } catch (SQLException e){
            System.out.println("Error fetching Products " + e);
        }
        return products;
    }
    public int getSuppliers(){
        int suppliers = 0;
        String sql = "SELECT * FROM suppliers";
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                suppliers++;
            }
            
        } catch (SQLException e){
            System.out.println("Error fetching suppliers " + e);
        }
        return suppliers;
    }
    public int getStocks(){
        int stocks = 0;
        String sql = "SELECT * FROM products";
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                stocks += rs.getInt("quantity");
            }
            
        } catch (SQLException e){
            System.out.println("Error fetching Products " + e);
        }
        return stocks;
    }
    public double getTotalAmount(){
        double amount = 0;
        String sql = "SELECT * FROM products";
        try(Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                amount += rs.getInt("total_amount");
            }
            
        } catch (SQLException e){
            System.out.println("Error fetching Products " + e);
        }
        return amount;
    }
}
