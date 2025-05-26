/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

/**
 *
 * @author mudasser
 */
public class Supplier {
    private int id;
    private String name;
    private String email;
    
    public Supplier(){
        this.name = "";
        this.id = 0;
        this.email = "";
    }
    public Supplier(int id, String name, String email){
        this.name = name;
        this.id = id;
        this.email = email;
    }
    
    public void setID(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
}
