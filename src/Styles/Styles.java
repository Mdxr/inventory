/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Styles;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author mudasser
 */
public class Styles {
    Colors colors = new Colors();
    public void setButtonsStyling(ArrayList<JButton> btns){
        for(JButton btn: btns){
            btn.setMargin(new Insets(7,10,7,10));
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    public void setTextFieldsStyling(ArrayList<JTextField> fields){
        for(JTextField field : fields){
//            field.setBackground(colors.lightBlack);
//            field.setForeground(colors.lightGrey);
            field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colors.borderWhite), BorderFactory.createEmptyBorder(5,10,5,10)));
        }
    }
    public void setLabelStyling(ArrayList<JLabel> labels){
        for(JLabel label : labels){
            label.setForeground(colors.lightGrey);
        }
    }
    public void setTitleStyling(JLabel title){
        title.setForeground(colors.lightWhite);
        title.setFont(new Font("Arial", Font.BOLD, 21));
    }
}
