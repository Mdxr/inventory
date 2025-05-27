package javalabdetailed;

import javax.swing.*;
import java.awt.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import DB.DBManager;
import Styles.Colors;
import Styles.Styles;
import java.sql.*;
import java.util.Comparator;

public class Main {
    public static boolean editMode;

    public static void main(String[] args) {
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
        auth.setVisible(true);
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
        Styles styles = new Styles();
        
        
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
        
        styles.setButtonsStyling(btns);
        styles.setBackgroundsStyling(bgs);
        styles.setLabelStyling(labels);
        styles.setTextFieldsStyling(fields);
        styles.setTitleStyling(l1);
        
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
        JTextField email = new JTextField("kmudasser44@gmail.com");
        JLabel emailErr = new JLabel();
        registerForm.add(rl2);
        registerForm.add(email);
        registerForm.add(emailErr);
        
        //password
        JLabel rl3 = new JLabel("Password : ");
        JTextField password = new JTextField("RESSADUM");
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
                if(!result.equals("success")){
                    if(result.contains("Password")){
                        passErr.setText(result);
                    } else {
                        emailErr.setText(result);
                    }
                } else {
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
        Styles styles = new Styles();
        styles.setButtonsStyling(btns);
        
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
        
        styles.setButtonsStyling(btns);
        styles.setBackgroundsStyling(bgs);
        styles.setLabelStyling(labels);
        styles.setTextFieldsStyling(fields);
        styles.setTitleStyling(l1);
        
         
    }
    
    public static void inventory(JFrame app){
        JTabbedPane pane = new JTabbedPane();
        JPanel entryForm = new JPanel();
        JPanel table = new JPanel();
        pane.addTab("Entry", entryForm);
        pane.addTab("Monitor Stocks", table);
        app.add(pane);
        
        JPanel btnPanel = new JPanel();
        JLabel l1 = new JLabel("Product Entry");
        JButton addBtn = new JButton("Add New Product");
        JButton editBtn = new JButton("Edit Existing Product");
        addBtn.setEnabled(false);
        l1.setFont(new Font("Arial", Font.BOLD, 21));
        entryForm.add(l1);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        entryForm.add(btnPanel);
        
        addProduct(entryForm, editBtn, addBtn, l1);
        monitorStocks(table);
        
        Styles styles = new Styles();
        ArrayList<JPanel> bgs = new ArrayList<>();
        bgs.add(entryForm);
        bgs.add(btnPanel);
        Colors colors = new Colors();
//        pane.setBackground();
        
        ArrayList<JButton> btns = new ArrayList<>();
        btns.add(editBtn);
        btns.add(addBtn);
        
        styles.setBackgroundsStyling(bgs);
        styles.setButtonsStyling(btns);

}
    
    public static void addProduct(JPanel entryForm, JButton editBtn, JButton addBtn, JLabel title){
        ProductManager productManager = new ProductManager();
        SupplierManager supplierManager = new SupplierManager();
        title.setText("Product Entry : Addition Mode");
        
        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));
        
        left.setLayout(new GridLayout(15,1));
        right.setLayout(new GridLayout(15,1));
        
        form.add(left);
        form.add(right);
        
        
        JLabel pNameLabel = new JLabel("Product Name : ");
        JTextField pName = new JTextField(50);
        JLabel pNameErr = new JLabel();
        JLabel pCategLabel = new JLabel("Product Category : ");
        JTextField pCateg = new JTextField(50);
        JLabel pCategErr = new JLabel();
        JLabel pQtyLabel = new JLabel("Product Quantity : ");
        JTextField pQty = new JTextField(50);
        JLabel pQtyErr = new JLabel();
        JLabel pPriceLabel = new JLabel("Product Price : ");
        JTextField pPrice = new JTextField(50);
        JLabel pPriceErr = new JLabel();
        
        
        JLabel sNameLabel = new JLabel("Supplier Name : ");
        JTextField sName = new JTextField(50);
        JLabel sNameErr = new JLabel();
        JLabel sEmailLabel = new JLabel("Supplier Email : ");
        JTextField sEmail = new JTextField(50);
        JLabel sEmailErr = new JLabel();
        
        JButton addProductButton = new JButton("Add Product");

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
        entryForm.setBorder(BorderFactory.createEmptyBorder(50, 500, 0, 500));
        
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
                    status.setForeground(Color.green);
                } else {
                    status.setText("Error Occured adding " + product.getName());
                    status.setForeground(Color.red);
                }
                if(result.contains("updated")){
                    status.setText(product.getName() + " Updated SuccessFully");
                }
            }

        });
        
        editBtn.addActionListener(e -> {
            if(!editMode){
                form.setVisible(false);
                editProduct(entryForm, addBtn, editBtn, title);
                editMode = true;
                addBtn.setEnabled(true);
                editBtn.setEnabled(false);
            }
        });
        
        Styles styles = new Styles();
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
        
        styles.setBackgroundsStyling(bgs);
        styles.setTextFieldsStyling(fields);
        styles.setButtonsStyling(btns);
        styles.setLabelStyling(labels);
        styles.setTitleStyling(title);
        
        
    }
    
    public static void editProduct(JPanel entryForm, JButton addBtn, JButton editBtn, JLabel title){
        ProductManager productManager = new ProductManager();
        title.setText("Product Entry : Edit Mode");
        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));
        
        left.setLayout(new GridLayout(15,1));
        right.setLayout(new GridLayout(15,1));
        
        form.add(left);
        form.add(right);
        
        
        JLabel pNameLabel = new JLabel("Product Name : ");
        JTextField pName = new JTextField(50);
        JLabel pNameErr = new JLabel();
        JLabel pCategLabel = new JLabel("Product Category : ");
        JTextField pCateg = new JTextField(50);
        JLabel pCategErr = new JLabel();
        JLabel pQtyLabel = new JLabel("Product Quantity : ");
        JTextField pQty = new JTextField(50);
        JLabel pQtyErr = new JLabel();
        JLabel pPriceLabel = new JLabel("Product Price : ");
        JTextField pPrice = new JTextField(50);
        JLabel pPriceErr = new JLabel();
        
        JLabel pIDLabel = new JLabel("Product ID : ");
        JTextField pID = new JTextField(50);
        JLabel pIDErr = new JLabel();
        JLabel sNameLabel = new JLabel("Supplier Name : ");
        JTextField sName = new JTextField(50);
        JLabel sNameErr = new JLabel();
        JLabel sEmailLabel = new JLabel("Supplier Email : ");
        JTextField sEmail = new JTextField(50);
        JLabel sEmailErr = new JLabel();
        
        JButton saveProductButton = new JButton("Save Product");
        JButton loadProductButton = new JButton("Load Product");
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
        entryForm.setBorder(BorderFactory.createEmptyBorder(50, 500, 0, 500));
        
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
                            } else {
                                status.setText("Error Occurred!");
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
                addProduct(entryForm, editBtn, addBtn, title);
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
        
        Styles styles = new Styles();
        styles.setBackgroundsStyling(bgs);
        styles.setTextFieldsStyling(inputs);
        styles.setButtonsStyling(allBtns);
        styles.setLabelStyling(labels);
        styles.setTitleStyling(title);
        
    }
    
    public static DefaultTableModel stockTableModel; // Accessible model for external updates

    public static void monitorStocks(JPanel tab) {
        StocksMonitoring monitor = new StocksMonitoring();
        tab.setLayout(new BorderLayout());
        tab.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JLabel title = new JLabel("Monitor Stocks", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 21));
        JButton refresh = new JButton("refresh");
        tab.add(title, BorderLayout.NORTH);
        tab.add(refresh, BorderLayout.SOUTH);
        
        

        String[] columnNames = {"ID", "Name", "Category", "Quantity", "Price", "TotalAmount", "Supplier ID", "Supplier Name", "Supplier Email"};

        stockTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        refresh.addActionListener(e -> {
            stockTableModel.setRowCount(0);
            ArrayList<ProductSupplierPair> products = monitor.loadProducts();
            products.sort(Comparator.comparingInt(p -> p.getProduct().getQuantity()));
            for(ProductSupplierPair product: products){
                System.out.println(product.getProduct().getName() + " " + product.getSupplier().getEmail());
                stockTableModel.addRow(new Object[]{product.getProduct().getID(), product.getProduct().getName(), product.getProduct().getCategory(), product.getProduct().getQuantity(), product.getProduct().getPrice() , product.getTotalAmount(), product.getSupplier().getID() ,product.getSupplier().getName(), product.getSupplier().getEmail()});
            }
        });
        
       
        JTable table = new JTable(stockTableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        tab.add(container, BorderLayout.CENTER);
    }
}
