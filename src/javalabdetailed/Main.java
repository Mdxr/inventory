package javalabdetailed;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.*;
import static javalabdetailed.GUI.register;

public class Main {
    public static boolean editMode;
    public static boolean isSuper;

    public static void main(String[] args) {
        try {
            UIManager.put("Component.focusColor", Color.decode("#73d187"));
            UIManager.put("Component.outlineColor", Color.decode("#4CAF93"));
            UIManager.put("TabbedPane.underlineColor", Color.decode("#73d187"));
            UIManager.put("Component.arc", 10);
            UIManager.put("Button.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("CheckBox.arc", 10);
            UIManager.put("TabbedPane.tabArc", 10);

            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame auth = new JFrame("Auth");
        JFrame app = new JFrame("App");
        register(auth, app);
    }
}