
package javalabdetailed;

import DB.DBManager;
import java.sql.*;
/**
 *
 * @author mudasser
 */
public class ProductManager {
    public String addProduct(Product product, Supplier supplier){
        SupplierManager sManager = new SupplierManager();
        sManager.addSupplier(supplier);
        String sql = "SELECT * FROM suppliers WHERE email=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement sStmt = conn.prepareStatement(sql)){
            sStmt.setString(1, supplier.getEmail());
            ResultSet rs = sStmt.executeQuery();
            if(rs.next()){
                supplier.setID(rs.getInt("id"));
                sql = "SELECT * FROM products WHERE name=? AND supplier_id=?";
                try(PreparedStatement pStmt = conn.prepareStatement(sql)){
                    pStmt.setString(1, product.getName());
                    pStmt.setInt(2, supplier.getID());
                    
                    rs = pStmt.executeQuery();
                    if(rs.next()){
                        product.setID(rs.getInt("id"));
                        sql = "UPDATE products SET quantity=?, total_amount=? WHERE id=?";
                        try(PreparedStatement addStmt = conn.prepareStatement(sql)){
                            addStmt.setInt(1, rs.getInt("quantity") + product.getQuantity());
                            addStmt.setDouble(2, rs.getDouble("total_amount") + (product.getQuantity() * product.getPrice()));
                            addStmt.setInt(3, product.getID());
                            
                            addStmt.execute();
                            return "updated";
                        } catch (SQLException e){
                            return "Error Updating product " + e;
                        }
                    } else {
                        sql = "INSERT INTO products(name, category, quantity, supplier_id, price, total_amount) VALUES (?,?,?,?,?,?)";
                        try(PreparedStatement addStmt = conn.prepareStatement(sql)){
                            addStmt.setString(1, product.getName());
                            addStmt.setString(2, product.getCategory());
                            addStmt.setInt(3, product.getQuantity());
                            addStmt.setInt(4, supplier.getID());
                            addStmt.setDouble(5, product.getPrice());
                            addStmt.setDouble(6, product.getQuantity() * product.getPrice());
                        
                            addStmt.execute();
                            return "success";
                        } catch (SQLException e){
                            return "Error Creating product record" + e;
                        }
                    }
                } catch (SQLException e){
                    return "Error fetching product " + e;
                }
            }
            
        } catch (SQLException e){
            return "Error fetching supplier (pManager) " + e;
        }

       return "Error Performing this operation!";
    }
    
    public ProductSupplierPair loadProduct(int id){
        String sql = "SELECT * FROM products WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                Product product = new Product(id, rs.getString("name"), rs.getString("category"), rs.getInt("quantity"), rs.getDouble("price"));
                sql = "SELECT * FROM suppliers WHERE id=?";
                try(PreparedStatement sStmt = conn.prepareStatement(sql)){
                    int supplier_id = rs.getInt("supplier_id");
                    sStmt.setInt(1, supplier_id);
                    
                    rs = sStmt.executeQuery();
                    if(rs.next()){
                        Supplier supplier = new Supplier(supplier_id, rs.getString("name"), rs.getString("email"));
                        ProductSupplierPair pair = new ProductSupplierPair(product, supplier);
                        return pair;
                    } else {
                        System.out.println("Supplier doesn't exist!");
                    }
                } catch (SQLException e){
                    System.out.println("Error fetching supplierr " + e);
                }
            } else {
                return null;
            }
            
        } catch (SQLException e){
            System.out.println("Error fetching product " + e);
        }
        return null;
    }
    
    public String updateProduct(Product product, Supplier supplier){
        SupplierManager sManager = new SupplierManager();
        sManager.addSupplier(supplier);
        String sql = "SELECT * FROM suppliers WHERE email=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement sStmt = conn.prepareStatement(sql)){
            sStmt.setString(1, supplier.getEmail());
            ResultSet rs = sStmt.executeQuery();
            if(rs.next()){
                supplier.setID(rs.getInt("id"));
                sql = "UPDATE products SET name=?,category=?,quantity=?,supplier_id=?,price=?,total_amount=? WHERE id=?";
                try(PreparedStatement uStmt = conn.prepareStatement(sql)){
                    uStmt.setString(1, product.getName());
                    uStmt.setString(2, product.getCategory());
                    uStmt.setInt(3, product.getQuantity());
                    uStmt.setInt(4, supplier.getID());
                    uStmt.setDouble(5, product.getPrice());
                    uStmt.setDouble(6, product.getQuantity() * product.getPrice());
                    uStmt.setInt(7, product.getID());
                    
                    uStmt.execute();
                    return "success updating";
                } catch (SQLException e){
                    return "Error updating Product! " + e;
                }
            } else {
                return "Couldn't be processed!";
            }
        } catch (SQLException e){
            return "Error Fetching Supplier " + e;
        }
    }
    public void deleteProduct(int id){
        String sql = "DELETE FROM products WHERE id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Error deleting record " + e);
        }
    }
    
    public int getProductID(String name, int supplierID){
        String sql = "SELECT * FROM products WHERE name=? AND supplier_id=?";
        try(Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setInt(2, supplierID);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id") : 0;
        } catch (SQLException e){
            System.out.println("Error deleting record " + e);
            return 0;
        }
    }
    
}
