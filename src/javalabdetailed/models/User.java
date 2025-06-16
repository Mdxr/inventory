/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalabdetailed.models;

/**
 * @author Mudasser Khan
 */
public class User {
    private int id;
    private String name;
    private String email;
    private char[] password;
    private boolean isVerified;
    
    public User(){
        this.id = 0;
        this.name = "";
        this.email = "";
    }
    public User(String name, String email, char[] password){
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
    public char[] getPassword(){
        return password;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public void setVerfied(boolean status){
        this.isVerified = status;
    }
    public boolean getVerified(){
        return isVerified;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(char[] password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    
}

