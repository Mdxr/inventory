/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Styles;

import java.awt.Cursor;
import java.awt.Font;
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
            btn.setBackground(colors.grey);
            btn.setForeground(colors.lightWhite);
            btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colors.borderWhite), BorderFactory.createEmptyBorder(5,10,5,10)));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if(btn.isEnabled()){
                        btn.setBackground(colors.lightGrey);
                        btn.setForeground(colors.lightWhite);
                    }
                }

                public void mouseExited(MouseEvent e) {
                    btn.setBackground(colors.grey);
                    btn.setForeground(colors.lightWhite);
                }
            });

        }
    }
    public void setBackgroundsStyling(ArrayList<JPanel> panels){
        for(JPanel panel : panels){
            panel.setBackground(colors.darkGrey);
        }
    }
    public void setTextFieldsStyling(ArrayList<JTextField> fields){
        for(JTextField field : fields){
            field.setBackground(colors.lightBlack);
            field.setForeground(colors.lightGrey);
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
