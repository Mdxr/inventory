/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed;

/**
 * @author Mudasser Khan
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    
    public User(){
        this.id = 0;
        this.name = "";
        this.email = "";
        this.password = "";
    }
    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public void setID(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    
}

