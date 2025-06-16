/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed.controllers;

import DB.DBManager;
import java.sql.*;
import java.util.ArrayList;
import javalabdetailed.models.Product;
import javalabdetailed.models.ProductSupplierPair;
import javalabdetailed.models.Supplier;

/**
 *
 * @author mudasser
 */
public class Reorder {
    public ArrayList<ProductSupplierPair> loadProducts(){
        String sql = "SELECT * FROM products WHERE quantity<20";
        ArrayList<ProductSupplierPair> pairArray = new ArrayList<>();
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();
                
                while(rs.next()){
                    Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("category"), rs.getInt("quantity"), rs.getDouble("price"));
                    Supplier supplier = new Supplier();
                    supplier.setID(rs.getInt("supplier_id"));
                    double totalAmount = rs.getDouble("total_amount");
                    
                    sql = "SELECT * FROM suppliers WHERE id=?";
                    try(PreparedStatement nStmt = conn.prepareStatement(sql)){
                        nStmt.setInt(1, supplier.getID());
                        ResultSet nrs = nStmt.executeQuery();
                        if(nrs.next()){
                            supplier.setName(nrs.getString("name"));
                            supplier.setEmail(nrs.getString("email"));

                            ProductSupplierPair pair = new ProductSupplierPair(product, supplier);
                            pair.setTotalAmount(totalAmount);
                            pairArray.add(pair);
 
                        }
                    } catch (SQLException e){
                        System.out.println("Error Fetching Supplier " + e);
                    }
                }
            
        } catch (SQLException e){
             System.out.println("Error Fetching Product " + e);
        }
        return pairArray;
    }
    public void ReorderProduct(Product product, int qty){
        String sql = "UPDATE products SET quantity=?,total_amount=? WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, qty + product.getQuantity());
            stmt.setDouble(2, (product.getQuantity() * product.getPrice()) + (qty * product.getPrice()));
            stmt.setInt(3, product.getID());
            
            stmt.execute();
       
        } catch (SQLException e){
            System.out.println("Error Updating product " + e);
        }
    }

}
