/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed.models;

/**
 *
 * @author mudasser
 */
public class ProductSupplierPair {
    private Product product;
    private Supplier supplier;
    private double totalAmount;

    public ProductSupplierPair(Product product, Supplier supplier) {
        this.product = product;
        this.supplier = supplier;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }
    
    public double getTotalAmount(){
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }
    public void setProduct(Product p){
        this.product = p;
    }
}
