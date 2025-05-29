package javalabdetailed;

import javax.swing.*;
import java.awt.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import DB.DBManager;
import Styles.Styles;
import java.sql.*;
import java.util.Comparator;
import com.formdev.flatlaf.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static boolean editMode;
    public static boolean isSuper;

    public static void main(String[] args) {
        try {
            // Set FlatLaf Dark theme
            UIManager.put("Component.focusColor", Color.decode("#73d187"));
            UIManager.put("Component.outlineColor", Color.decode("#4CAF93"));
            UIManager.put("TabbedPane.underlineColor", Color.decode("#73d187"));
            UIManager.put("Component.arc", 10); // default is 5
            UIManager.put("Button.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10); // text fields, password fields, etc.
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
    
    public static void register(JFrame auth, JFrame app){
        UserManager authManager = new UserManager();
        
        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        auth.setSize(1920,1080);
        app.setSize(1920,1080);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            auth.setVisible(true);
        });
        JPanel registerForm = new JPanel();
        JLabel l1 = new JLabel("Register");
        l1.setFont(new Font("Arial", Font.BOLD, 21));
        registerForm.add(l1);
        auth.add(registerForm);
        registerForm.setLayout(new GridLayout(20,1));
        registerForm.setBorder(BorderFactory.createEmptyBorder(80,500,0,500));
        
        // full name
        JLabel rl1 = new JLabel("Full Name : ");
        JTextField name = new JTextField();
        JLabel nameErr = new JLabel();
        registerForm.add(rl1);
        registerForm.add(name);
        registerForm.add(nameErr);
        
        //email
        JLabel rl2 = new JLabel("Email : ");
        JTextField email = new JTextField();
        JLabel emailErr = new JLabel();
        registerForm.add(rl2);
        registerForm.add(email);
        registerForm.add(emailErr);
        
        //password
        JLabel rl3 = new JLabel("Password : ");
        JTextField password = new JTextField();
        JLabel passErr = new JLabel();
        registerForm.add(rl3);
        registerForm.add(password);
        registerForm.add(passErr);
        
        //confirm password
        JLabel rl4 = new JLabel("Confirm Password : ");
        JTextField cPassword = new JTextField();
        JLabel cPassErr = new JLabel();
        registerForm.add(rl4);
        registerForm.add(cPassword);
        registerForm.add(cPassErr);
        
        //auth key
        JLabel rl5 = new JLabel("Authentication Key : ");
        JTextField authKey = new JTextField();
        JLabel keyErr = new JLabel();
        registerForm.add(rl5);
        registerForm.add(authKey);
        registerForm.add(keyErr);
        
        //submit button
        JButton rBtn = new JButton("Register");
        registerForm.add(rBtn);
        
        // making array of all error labels, for combined styling and more
        ArrayList<JLabel> errors = new ArrayList<>();
        errors.add(nameErr);
        errors.add(emailErr);
        errors.add(passErr);
        errors.add(cPassErr);
        errors.add(keyErr);
        for (JLabel l : errors) {
            l.setForeground(Color.decode("#f26878"));
            l.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        
        // register user setup
        rBtn.addActionListener(e -> {
            //check name
            if(name.getText().isEmpty()){
                nameErr.setText("Name is Required!");
            } else {
                nameErr.setText("");
            }
            
            //check email
            if(email.getText().isEmpty()){
                emailErr.setText("Email is Required!");
            } else {
                emailErr.setText("");
            }
            
            //check pass
            if(password.getText().isEmpty()){
                passErr.setText("Password is Required!");
            } else if(password.getText().length() < 8){
                passErr.setText("Passwors should be atleast 8 characters long!");
            } else {
                passErr.setText("");
            }
            
            // check confrim password
            if(cPassword.getText().isEmpty() || !cPassword.getText().equals(password.getText())){
                cPassErr.setText("Confirm Password!");
            } else {
                cPassErr.setText("");
            }
            
            //check key
            if(authKey.getText().isEmpty()){
                keyErr.setText("Key is Required!");
            } else if(!authKey.getText().equals(LocalDate.now().toString())){
                keyErr.setText("Invalid Authentication Key!");
            } else {
                keyErr.setText("");
            }
            
            boolean clean = true;
            for(JLabel l : errors){
                if(!l.getText().isEmpty()){
                    clean = false;
                    break;
                }
            }
            if(clean){
                User user = new User(name.getText(), email.getText(), password.getText());
                String result = authManager.register(user);
                if(!result.equals("success")){
                    emailErr.setText(result);
                }
            }
 
        });
        
        //swith to login
        JPanel switchToLogin = new JPanel();
        JLabel alt = new JLabel("Already Registered?");
        JButton altB = new JButton("Login");
        altB.setContentAreaFilled(false);
        altB.setBorderPainted(false);
        altB.setForeground(Color.decode("#68b9f2"));
        altB.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchToLogin.add(alt);
        switchToLogin.add(altB);    
        registerForm.add(switchToLogin);
        
        altB.addActionListener(e -> {
            registerForm.setVisible(false);
            login(auth,app);
        });
        
        ArrayList<JButton> btns = new ArrayList<>();
        btns.add(rBtn);
//        Styles styles = new Styles();
        
        
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(switchToLogin);
        bgs.add(registerForm);
        
        
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(alt);
        labels.add(rl1);
        labels.add(rl3);
        labels.add(rl2);
        labels.add(rl4);
        labels.add(rl5);
        
        
        ArrayList<JTextField> fields = new ArrayList<>();
        fields.add(name);
        fields.add(cPassword);
        fields.add(authKey);
        fields.add(email);
        fields.add(password);
        
//        styles.setButtonsStyling(btns);
//        styles.setBackgroundsStyling(bgs);
//        styles.setLabelStyling(labels);
//        styles.setTextFieldsStyling(fields);
//        styles.setTitleStyling(l1);
        
    }
    public static void login(JFrame auth, JFrame app){
        UserManager authManager = new UserManager();
        
        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        auth.setSize(1920,1080);
        app.setSize(1920,1080);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        auth.setVisible(true);
        JPanel registerForm = new JPanel();
        JLabel l1 = new JLabel("Login");
        l1.setFont(new Font("Arial", Font.BOLD, 21));
        registerForm.add(l1);
        auth.add(registerForm);
        registerForm.setLayout(new GridLayout(16,1));
        registerForm.setBorder(BorderFactory.createEmptyBorder(200,500,0,500));
        
        //email
        JLabel rl2 = new JLabel("Email : ");
        JTextField email = new JTextField("admin@super");
        JLabel emailErr = new JLabel();
        registerForm.add(rl2);
        registerForm.add(email);
        registerForm.add(emailErr);
        
        //password
        JLabel rl3 = new JLabel("Password : ");
        JTextField password = new JTextField("SUPERADMIN");
        JLabel passErr = new JLabel();
        registerForm.add(rl3);
        registerForm.add(password);
        registerForm.add(passErr);
        
        //submit button
        JButton rBtn = new JButton("Login");
        registerForm.add(rBtn);
        
        // making array of all error labels, for combined styling and more
        ArrayList<JLabel> errors = new ArrayList<>();
        errors.add(emailErr);
        errors.add(passErr);
        for (JLabel l : errors) {
            l.setForeground(Color.decode("#f26878"));
            l.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        
        // register user setup
        rBtn.addActionListener(e -> {
            //check email
            if(email.getText().isEmpty()){
                emailErr.setText("Email is Required!");
            } else {
                emailErr.setText("");
            }
            
            //check pass
            if(password.getText().isEmpty()){
                passErr.setText("Password is Required!");
            } else {
                passErr.setText("");
            }
            
            
            boolean clean = true;
            for(JLabel l : errors){
                if(!l.getText().isEmpty()){
                    clean = false;
                    break;
                }
            }
            if(clean){
                User user = new User();
                user.setEmail(email.getText());
                user.setPassword(password.getText());
                String result = authManager.login(user);
                if(!result.contains("success")){
                    if(result.contains("Password")){
                        passErr.setText(result);
                    } else {
                        emailErr.setText(result);
                    }
                } else {
                    if(result.contains("super")){
                        isSuper = true;
                    } else {
                        isSuper = false;
                    }
                    
                    auth.setVisible(false);
                    inventory(app);
                    app.setVisible(true);
                }
            }
                        
        });
        
        //swith to login
        JPanel switchToLogin = new JPanel();
        JLabel alt = new JLabel("Not Registered Yet?");
        JButton altB = new JButton("Register");
        altB.setContentAreaFilled(false);
        altB.setBorderPainted(false);
        altB.setForeground(Color.decode("#68b9f2"));
        altB.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchToLogin.add(alt);
        switchToLogin.add(altB);
        registerForm.add(switchToLogin);
        
        altB.addActionListener(e -> {
            registerForm.setVisible(false);
            register(auth, app);
        });
        
        ArrayList<JButton> btns = new ArrayList<>();
        btns.add(rBtn);
//        Styles styles = new Styles();
//        styles.setButtonsStyling(btns);
        
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(switchToLogin);
        bgs.add(registerForm);
        
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(alt);
        labels.add(rl3);
        labels.add(rl2);
        
        
        ArrayList<JTextField> fields = new ArrayList<>();
        fields.add(email);
        fields.add(password);
        
//        styles.setButtonsStyling(btns);
//        styles.setBackgroundsStyling(bgs);
//        styles.setLabelStyling(labels);
//        styles.setTextFieldsStyling(fields);
//        styles.setTitleStyling(l1);
        
         
    }
    
    public static void inventory(JFrame app){
        JTabbedPane pane = new JTabbedPane();
        JPanel entryForm = new JPanel();
        JPanel table = new JPanel();
        JPanel adminPanel = new JPanel();
        
        ImageIcon entryIcon = new ImageIcon(Main.class.getResource("/Media/square-pen.png"));
        ImageIcon monitorIcon = new ImageIcon(Main.class.getResource("/Media/square-activity.png"));
        ImageIcon adminIcon = new ImageIcon(Main.class.getResource("/Media/fullscreen.png"));
        pane.addTab("Entry",entryIcon, entryForm);
        pane.addTab("Monitor Stocks",monitorIcon, table);
        if(isSuper){
            pane.addTab("Admin Panel", adminIcon, adminPanel);
        }
        app.add(pane);
        
        
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add New Product");
        JButton editBtn = new JButton("Edit Existing Product");
        addBtn.setEnabled(false);
        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        
        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        
        entryForm.add(logoLabel);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        entryForm.add(btnPanel);
        
        addProduct(entryForm, editBtn, addBtn);
        monitorStocks(table);
        
//        Styles styles = new Styles();
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(entryForm);
        bgs.add(btnPanel);
//        Colors colors = new Colors();
//        pane.setBackground();
//        
        ArrayList<JButton> btns = new ArrayList<>();
        btns.add(editBtn);
        btns.add(addBtn);
        
//        styles.setBackgroundsStyling(bgs);
//        styles.setButtonsStyling(btns);
        notificationHandler(app);
        adminPanel(adminPanel);
    }
    
    public static void adminPanel(JPanel adminPanel){
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        
        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(logoLabel);
        
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2, 20, 20));
        
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        
        left.setBackground(Color.decode("#46494b"));
        right.setBackground(Color.decode("#46494b"));
        left.setBorder(new FlatRoundBorder());
        right.setBorder(new FlatRoundBorder());
        
        JLabel leftTitle = new JLabel("Manage Stocks");
        JLabel rightTitle = new JLabel("Manage Users");
        leftTitle.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightTitle.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        leftTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        
        left.add(leftTitle);
        right.add(rightTitle);
        
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        
        container.add(left);
        container.add(right);
        adminPanel.add(container);
        manageStocks(left);
    }
    
    public static void manageStocks(JPanel container){
        JPanel records = new JPanel();
        records.setLayout(new BoxLayout(records, BoxLayout.Y_AXIS));
        records.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
        records.setBackground(Color.decode("#46494b"));
        JPanel record = new JPanel();
        record.setLayout(new BorderLayout());
        JLabel details = new JLabel("E5540 by DELL");
        details.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        JPanel actionButtons = new JPanel();
        JButton reorder = new JButton("Reorder");
        JButton delete = new JButton("Delete");
        actionButtons.add(reorder);
        actionButtons.add(delete);
        actionButtons.setOpaque(false);
        record.add(details, BorderLayout.WEST);
        record.add(actionButtons, BorderLayout.EAST);
        delete.setMargin(new Insets(5,10,5,10));
        reorder.setMargin(new Insets(5,10,5,10));
        record.setMaximumSize(new Dimension(Short.MAX_VALUE, record.getPreferredSize().height));
        record.setBorder(new FlatRoundBorder());
        records.add(record);
        container.add(records);
        
    }
    
    public static void addProduct(JPanel entryForm, JButton editBtn, JButton addBtn){
        ProductManager productManager = new ProductManager();
        SupplierManager supplierManager = new SupplierManager();

        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));
        
        left.setLayout(new GridLayout(15,1));
        right.setLayout(new GridLayout(15,1));
        
        form.add(left);
        form.add(right);
        
        
        JLabel pNameLabel = new JLabel("Product Name : ");
        JTextField pName = new JTextField(30);
        JLabel pNameErr = new JLabel();
        JLabel pCategLabel = new JLabel("Product Category : ");
        JTextField pCateg = new JTextField(30);
        JLabel pCategErr = new JLabel();
        JLabel pQtyLabel = new JLabel("Product Quantity : ");
        JTextField pQty = new JTextField(30);
        JLabel pQtyErr = new JLabel();
        JLabel pPriceLabel = new JLabel("Product Price : ");
        JTextField pPrice = new JTextField(30);
        JLabel pPriceErr = new JLabel();
        
        
        JLabel sNameLabel = new JLabel("Supplier Name : ");
        JTextField sName = new JTextField(30);
        JLabel sNameErr = new JLabel();
        JLabel sEmailLabel = new JLabel("Supplier Email : ");
        JTextField sEmail = new JTextField(30);
        JLabel sEmailErr = new JLabel();
        
        ImageIcon darkSaveIcon = new ImageIcon(Main.class.getResource("/Media/saveDark.png"));
        JLabel dSIcon = new JLabel(darkSaveIcon);
        dSIcon.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 0));
        
        JButton addProductButton = new JButton("Add Product");
        addProductButton.add(dSIcon);

        left.add(pNameLabel);
        left.add(pName);
        left.add(pNameErr);
        left.add(pCategLabel);
        left.add(pCateg);
        left.add(pCategErr);
        left.add(pQtyLabel);
        left.add(pQty);
        left.add(pQtyErr);
        left.add(pPriceLabel);
        left.add(pPrice);
        left.add(pPriceErr);
        
        right.add(sNameLabel);
        right.add(sName);
        right.add(sNameErr);
        right.add(sEmailLabel);
        right.add(sEmail);
        right.add(sEmailErr);

        entryForm.setLayout(new FlowLayout());
        entryForm.setBorder(BorderFactory.createEmptyBorder(70, 500, 0, 500));
        
        entryForm.add(form);
        right.add(new JLabel(""));
        right.add(addProductButton);
        JLabel status = new JLabel("");
        status.setFont(new Font("Arial", Font.PLAIN, 11));
        right.add(status);
        
        ArrayList<JLabel> errors = new ArrayList<>();
        errors.add(pNameErr);
        errors.add(pQtyErr);
        errors.add(pPriceErr);
        errors.add(pCategErr);
        errors.add(sNameErr);
        errors.add(sEmailErr);

        for (JLabel l : errors) {
            l.setForeground(Color.decode("#f26878"));
            l.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        
        addProductButton.addActionListener(e -> {
            try{
            if (pName.getText().isEmpty()) {
                pNameErr.setText("Name is Required!");
            } else {
                pNameErr.setText("");
            }
            if (pPrice.getText().isEmpty()) {
                pPriceErr.setText("Price is Required!");
            } else {
                pPriceErr.setText("");
            }
            if (pCateg.getText().isEmpty()) {
                pCategErr.setText("Category is Required!");
            } else {
                pCategErr.setText("");
            }
            if (pQty.getText().isEmpty()) {
                pQtyErr.setText("Quantity is Required!");
            } else {
                pQtyErr.setText("");
            }
            if (sName.getText().isEmpty()) {
                sNameErr.setText("Supplier Name is Required!");
            } else {
                sNameErr.setText("");
            }
            if (sEmail.getText().isEmpty()) {
                sEmailErr.setText("Supplier Email is Required!");
            } else {
                sEmailErr.setText("");
            }
            
            boolean clean = true;
            
            for(JLabel err: errors){
                if(!err.getText().isEmpty()){
                    clean = false;
                    break;
                }
            }
            if(clean){
                Supplier supplier = new Supplier(0, sName.getText(), sEmail.getText());
                Product product = new Product(0, pName.getText(), pCateg.getText(), parseInt(pQty.getText()), parseDouble(pPrice.getText()));
                
                String result = productManager.addProduct(product, supplier);
                if(result.contains("success")){
                    pName.setText("");
                    pCateg.setText("");
                    pQty.setText("");
                    pPrice.setText("");
                    sName.setText("");
                    sEmail.setText("");
                    status.setText(product.getName() + " Added Successfully");
                    status.setForeground(Color.decode("#73d187"));
                    populateTable();
                } else {
                    status.setText("Error Occured adding " + product.getName());
                    status.setForeground(Color.decode("#f26878"));
                }
                if(result.contains("updated")){
                    status.setText(product.getName() + " Updated SuccessFully");
                    status.setForeground(Color.decode("#73d187"));
                    for(JLabel error: errors){
                        error.setText("");
                    }
                }
            }
            
            } catch (NumberFormatException ne){
                status.setText("Invalid Data types");
                status.setForeground(Color.decode("#f26878"));
            }

        });
        
        editBtn.addActionListener(e -> {
            if(!editMode){
                form.setVisible(false);
                editProduct(entryForm, addBtn, editBtn);
                editMode = true;
                addBtn.setEnabled(true);
                editBtn.setEnabled(false);
            }
        });
        
//        Styles styles = new Styles();
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(form);
        bgs.add(left);
        bgs.add(right);
        
        ArrayList<JButton> btns = new ArrayList<>();
        btns.add(editBtn);
        btns.add(addBtn);
        btns.add(addProductButton);
        
        ArrayList<JTextField> fields = new ArrayList<>();
        fields.add(sName);
        fields.add(pName);
        fields.add(pCateg);
        fields.add(pPrice);
        fields.add(pQty);
        fields.add(sEmail);
        
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(pNameLabel);
        labels.add(pCategLabel);
        labels.add(pPriceLabel);
        labels.add(pQtyLabel);
        labels.add(sNameLabel);
        labels.add(sEmailLabel);
        Styles styles = new Styles();
//        styles.setBackgroundsStyling(bgs);
//        styles.setTextFieldsStyling(fields);
        styles.setButtonsStyling(btns);
//        styles.setLabelStyling(labels);
//        styles.setTitleStyling(title);

    addProductButton.setBackground(Color.decode("#73d187"));
    addProductButton.setForeground(Color.DARK_GRAY);
        
    }
    
    public static void editProduct(JPanel entryForm, JButton addBtn, JButton editBtn){
        ProductManager productManager = new ProductManager();
        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));
        
        left.setLayout(new GridLayout(15,1));
        right.setLayout(new GridLayout(15,1));
        
        form.add(left);
        form.add(right);
        
        
        JLabel pNameLabel = new JLabel("Product Name : ");
        JTextField pName = new JTextField(30);
        JLabel pNameErr = new JLabel();
        JLabel pCategLabel = new JLabel("Product Category : ");
        JTextField pCateg = new JTextField(30);
        JLabel pCategErr = new JLabel();
        JLabel pQtyLabel = new JLabel("Product Quantity : ");
        JTextField pQty = new JTextField(30);
        JLabel pQtyErr = new JLabel();
        JLabel pPriceLabel = new JLabel("Product Price : ");
        JTextField pPrice = new JTextField(30);
        JLabel pPriceErr = new JLabel();
        
        JLabel pIDLabel = new JLabel("Product ID : ");
        JTextField pID = new JTextField(30);
        JLabel pIDErr = new JLabel();
        JLabel sNameLabel = new JLabel("Supplier Name : ");
        JTextField sName = new JTextField(30);
        JLabel sNameErr = new JLabel();
        JLabel sEmailLabel = new JLabel("Supplier Email : ");
        JTextField sEmail = new JTextField(30);
        JLabel sEmailErr = new JLabel();
        
        ImageIcon loadIcon = new ImageIcon(Main.class.getResource("/Media/load.png"));
        JLabel lIcon = new JLabel(loadIcon);
        
        ImageIcon saveIcon = new ImageIcon(Main.class.getResource("/Media/save.png"));
        JLabel sIcon = new JLabel(saveIcon);
        
        ImageIcon darkSaveIcon = new ImageIcon(Main.class.getResource("/Media/saveDark.png"));
        JLabel dSIcon = new JLabel(darkSaveIcon);

        JButton saveProductButton = new JButton("Save Product");
        JButton loadProductButton = new JButton("Load Product");
        
        lIcon.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 0));
        loadProductButton.add(lIcon);
        
        sIcon.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 0));
        dSIcon.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 0));
        saveProductButton.add(sIcon);

        saveProductButton.setEnabled(false);
        
        JPanel btns = new JPanel();
        btns.add(loadProductButton);
        btns.add(saveProductButton);

        left.add(pNameLabel);
        left.add(pName);
        left.add(pNameErr);
        left.add(pCategLabel);
        left.add(pCateg);
        left.add(pCategErr);
        left.add(pQtyLabel);
        left.add(pQty);
        left.add(pQtyErr);
        left.add(pPriceLabel);
        left.add(pPrice);
        left.add(pPriceErr);
        
        right.add(pIDLabel);
        right.add(pID);
        right.add(pIDErr);
        right.add(sNameLabel);
        right.add(sName);
        right.add(sNameErr);
        right.add(sEmailLabel);
        right.add(sEmail);
        right.add(sEmailErr);
        
        entryForm.setLayout(new FlowLayout());
        entryForm.setBorder(BorderFactory.createEmptyBorder(70, 500, 0, 500));
        
        entryForm.add(form);
        right.add(new JLabel(""));
        btns.setLayout(new GridLayout(1,2, 10, 0));
        right.add(btns);
        JLabel status = new JLabel("");
        right.add(status);
        
        ArrayList<JTextField> inputs = new ArrayList<>();
        inputs.add(pID);
        inputs.add(pName);
        inputs.add(pCateg);
        inputs.add(pQty);
        inputs.add(pPrice);
        inputs.add(sName);
        inputs.add(sEmail);
        
        ArrayList<JLabel> errors = new ArrayList<>();
        errors.add(pNameErr);
        errors.add(pQtyErr);
        errors.add(pPriceErr);
        errors.add(pCategErr);
        errors.add(sNameErr);
        errors.add(sEmailErr);
        errors.add(pIDErr);
        
        loadProductButton.addActionListener(e -> {
            if(pID.getText().isEmpty()){
                pIDErr.setText("ID is required!");
            } else {
                
                pIDErr.setText("");
                ProductSupplierPair pair = productManager.loadProduct(parseInt(pID.getText()));
                if(pair != null){
                    saveProductButton.setEnabled(true);
                    pName.setText(pair.getProduct().getName());
                    pCateg.setText(pair.getProduct().getCategory());
                    pQty.setText(String.valueOf(pair.getProduct().getQuantity()));
                    pPrice.setText(String.valueOf(pair.getProduct().getPrice()));
                    sName.setText(pair.getSupplier().getName());
                    sEmail.setText(pair.getSupplier().getEmail());
                    for(JLabel err : errors){
                        err.setText("");
                    }
                    status.setText("Loaded successfully");
                    saveProductButton.remove(sIcon);
                    saveProductButton.add(dSIcon);
                    status.setForeground(Color.decode("#73d187"));
                    
                    saveProductButton.addActionListener(f -> {
                        if(pName.getText().isEmpty()){
                            pNameErr.setText("Name is Required!");
                        } else {
                            pNameErr.setText("");
                        }
                        if(pPrice.getText().isEmpty()){
                            pPriceErr.setText("Price is Required!");
                        } else {
                            pPriceErr.setText("");
                        }
                        if(pCateg.getText().isEmpty()){
                            pCategErr.setText("Category is Required!");
                        } else {
                            pCategErr.setText("");
                        }
                        if(pQty.getText().isEmpty()){
                            pQtyErr.setText("Quantity is Required!");
                        } else {
                            pQtyErr.setText("");
                        }
                        if(sName.getText().isEmpty()){
                            sNameErr.setText("Supplier Name is Required!");
                        } else {
                            sNameErr.setText("");
                        }
                        if(sEmail.getText().isEmpty()){
                            sEmailErr.setText("Supplier Email is Required!");
                        } else {
                            sEmailErr.setText("");
                        }
                        if(pID.getText().isEmpty()){
                            pIDErr.setText("ID is required!");
                        } else {
                            pIDErr.setText("");
                        }

                        boolean clean = true;

                        for(JLabel err: errors){
                            if(!err.getText().isEmpty()){
                                clean = false;
                                break;
                            }
                        }
                        if(clean){
                            Product product = new Product(parseInt(pID.getText()), pName.getText(), pCateg.getText(), parseInt(pQty.getText()), parseDouble(pPrice.getText()));
                            Supplier supplier = new Supplier(0, sName.getText(), sEmail.getText());
                            
                            String result = productManager.updateProduct(product, supplier);
                            if(result.contains("success")){
                                status.setText("Updated Successfully!");
                                for(JTextField tF: inputs){
                                    tF.setText("");
                                }

                                populateTable();
                            } else {
                                status.setText("Error Occurred!");
                            }
                            
                            
                        }
                        if(status.getText().contains("Updated Successfully!")){
                                for(JLabel error: errors){
                                    error.setText("");
                                }
                        }
                });
                    
                } else {
                    pIDErr.setText("Product Doesn't Exist");
                    saveProductButton.setEnabled(false);
                    for(JTextField tF: inputs){
                        tF.setText("");
                    }
                }
            }
        });

        addBtn.addActionListener(e -> {
            if(editMode){
                form.setVisible(false);
                addProduct(entryForm, editBtn, addBtn);
                editMode = false;
                editBtn.setEnabled(true);
                addBtn.setEnabled(false);
            }
        });
        
        ArrayList<JButton> allBtns = new ArrayList<>();
        allBtns.add(editBtn);
        allBtns.add(addBtn);
        allBtns.add(loadProductButton);
        allBtns.add(saveProductButton);
        
        Styles styles = new Styles();
        styles.setButtonsStyling(allBtns);
        
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(left);
        bgs.add(right);
        bgs.add(form);
        bgs.add(btns);
        
        
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(pIDLabel);
        labels.add(pNameLabel);
        labels.add(pCategLabel);
        labels.add(pPriceLabel);
        labels.add(pQtyLabel);
        labels.add(sNameLabel);
        labels.add(sEmailLabel);
        
//        Styles styles = new Styles();
//        styles.setBackgroundsStyling(bgs);
//        styles.setTextFieldsStyling(inputs);
//        styles.setButtonsStyling(allBtns);
//        styles.setLabelStyling(labels);
//        styles.setTitleStyling(title);

    saveProductButton.setBackground(Color.decode("#73d187"));
    saveProductButton.setForeground(Color.DARK_GRAY);
    
    for(JLabel error: errors){
            error.setForeground(Color.decode("#f26878"));
            error.setFont(new Font("Arial", Font.PLAIN, 11));
    }
        
    }
    
    public static DefaultTableModel stockTableModel; 

    public static void monitorStocks(JPanel tab) {
        tab.setLayout(new BorderLayout());
        tab.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));
        
        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);

        JLabel title = new JLabel(logoIcon, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 21));
        tab.add(title, BorderLayout.NORTH);
        
        

        String[] columnNames = {"ID", "Name", "Category", "Quantity", "Price", "TotalAmount", "Supplier ID", "Supplier Name", "Supplier Email"};

        stockTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        populateTable();
       
        JTable table = new JTable(stockTableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        tab.add(container, BorderLayout.CENTER);
    }
    
    public static void populateTable(){
        StocksMonitoring monitor = new StocksMonitoring();
        stockTableModel.setRowCount(0);
        ArrayList<ProductSupplierPair> products = monitor.loadProducts();
        products.sort(Comparator.comparingInt(p -> p.getProduct().getQuantity()));
        for (ProductSupplierPair product : products) {
            stockTableModel.addRow(new Object[]{product.getProduct().getID(), product.getProduct().getName(), product.getProduct().getCategory(), product.getProduct().getQuantity(), product.getProduct().getPrice(), product.getTotalAmount(), product.getSupplier().getID(), product.getSupplier().getName(), product.getSupplier().getEmail()});
        }
    }
    public static DefaultTableModel reorderTableModel;

    public static void notificationHandler(JFrame app){
        JDialog notificationPopUp = new JDialog(app, "Reorder Products");
        notificationPopUp.setLocationRelativeTo(null);
        notificationPopUp.setSize(600, 300);    
        JButton cancel = new JButton("CANCEL");
        cancel.setFont(new Font("Arial", Font.BOLD, 12));
        cancel.setBackground(Color.decode("#f26878"));
        cancel.setForeground(Color.DARK_GRAY);
        
        cancel.setMargin(new Insets(7,7,7,7));
        
        ImageIcon cancelIconImage = new ImageIcon(Main.class.getResource("/Media/circle.png"));
        JLabel cancelIcon = new JLabel(cancelIconImage);
        cancelIcon.setBorder(BorderFactory.createEmptyBorder(0,230,0,0));
        cancel.add(cancelIcon);
        cancel.addActionListener(e -> {
            notificationPopUp.dispose();
        });
        
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        
        
        
        notificationPopUp.add(container);
        renderReorderProducts(container, notificationPopUp, cancel);
    }
    
    public static void renderReorderProducts(JPanel container, JDialog popUp, JButton cancel) {
        container.removeAll();
        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(192, 30, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        
        container.add(logoLabel);
        Reorder reorder = new Reorder();
        ArrayList<ProductSupplierPair> products = reorder.loadProducts();
        ImageIcon darkSaveIcon = new ImageIcon(Main.class.getResource("/Media/saveDark.png"));
        if (!products.isEmpty()) {
            container.setLayout(new GridLayout(0, 1, 10, 10));
            for (ProductSupplierPair product : products) {
                JLabel dSIcon = new JLabel(darkSaveIcon);
                dSIcon.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(1, 4, 10, 0));
                panel.add(new JLabel(product.getProduct().getName()));
                JLabel qtylabel = new JLabel("Quantity (Present : " + product.getProduct().getQuantity() + ")", SwingConstants.RIGHT);
                qtylabel.setFont(new Font("Arial", Font.BOLD, 12));
                panel.add(qtylabel);
                JTextField reorderQty = new JTextField(5);
                JButton reorderBtn = new JButton("Reorder");
                reorderBtn.add(dSIcon);
                reorderBtn.setBackground(Color.decode("#73d187"));
                reorderBtn.setForeground(Color.DARK_GRAY);
                reorderBtn.setFont(new Font("Arial", Font.BOLD, 12));
                panel.add(qtylabel);
                panel.add(reorderQty);
                panel.add(reorderBtn);
                container.add(panel);
                reorderBtn.addActionListener(e -> {
                    if (!reorderQty.getText().isEmpty()) {
                        reorder.ReorderProduct(product.getProduct(), parseInt(reorderQty.getText()));
                        renderReorderProducts(container, popUp, cancel);
                        populateTable();
                    }
                    if (products.isEmpty()) {
                        popUp.setVisible(false);
                        popUp.dispose();
                    } else {
                        popUp.pack();
                    }
                });
            }
            container.add(cancel); 
            popUp.setVisible(true);
        } else {
            popUp.setVisible(false);
        }
        popUp.pack();
    }
}
