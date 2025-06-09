/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

/**
 *
 * @author mudasser
 */
public class Product {
    protected int id;
    protected String name;
    protected String category;
    protected int quantity;
    protected double price;
    
    public Product(){
        this.name = "";
        this.id = 0;
        this.category = "";
        this.price = 0.0;
        this.quantity = 0;
    }
    public Product(int id, String name, String category, int quantity, double price){
        this.name = name;
        this.id = id;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    public void setID(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String c){
        this.category = c;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setQuantity(int qty){
        this.quantity = qty;
    }
    
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }

}
