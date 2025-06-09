/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

import java.util.Date;

/**
 *
 * @author mudasser
 */
public class HistoryLog{
    private int id;
    private String operation;
    private Product product;
    private Supplier supplier;
    private String date;
    
    public HistoryLog(int id, String op, Product product, Supplier supplier, String date){
        this.id = id;
        this.operation = op;
        this.supplier = supplier;
        this.product = product;
        this.date = date;
    }
    
    public void setID(int id){
        this.id = id;
    }
    public void setOperation(String op){
        this.operation = op;
    }
    public void setProduct(Product product){
        this.product = product;
    }
    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
    }
    public void setDate(String date){
        this.date = date;
    }
    
    public int getID(){
        return id;
    }
    public String getOperation(){
        return operation;
    }
    public Product getProduct(){
        return product;
    }
    public Supplier getSupplier(){
        return supplier;
    }
    public String getDate(){
        return date;
    }
}
