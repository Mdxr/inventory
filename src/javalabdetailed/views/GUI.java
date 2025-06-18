package javalabdetailed.views;

import javalabdetailed.controllers.Insights;
import javalabdetailed.models.Supplier;
import javalabdetailed.models.User;
import javalabdetailed.models.HistoryLog;
import javalabdetailed.models.Product;
import javalabdetailed.models.ProductSupplierPair;
import javalabdetailed.controllers.ReportsManager;
import javalabdetailed.controllers.ProductManager;
import javalabdetailed.controllers.UserManager;
import javalabdetailed.controllers.Reorder;
import javalabdetailed.controllers.SupplierManager;
import javalabdetailed.controllers.HistoryManager;
import javalabdetailed.controllers.StocksMonitoring;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import Styles.Styles;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.Map;
import javalabdetailed.Main;

public class GUI {
    private static boolean editMode;
    private static boolean isSuper;
    private static String admin;

    public static void register(JFrame auth, JFrame app) {
        UserManager authManager = new UserManager();

        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        auth.setSize(1920, 1080);
        app.setSize(1920, 1080);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            auth.setVisible(true);
        });
        JPanel registerForm = new JPanel();

        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();

        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerForm.add(logoLabel);
        auth.add(registerForm);
        registerForm.setLayout(new GridLayout(18, 1));
        registerForm.setBorder(BorderFactory.createEmptyBorder(130, 500, 0, 500));

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
        JPasswordField password = new JPasswordField();
        JLabel passErr = new JLabel();
        registerForm.add(rl3);
        registerForm.add(password);
        registerForm.add(passErr);

        //confirm password
        JLabel rl4 = new JLabel("Confirm Password : ");
        JPasswordField cPassword = new JPasswordField();
        JLabel cPassErr = new JLabel();
        registerForm.add(rl4);
        registerForm.add(cPassword);
        registerForm.add(cPassErr);

        //submit button
        JButton rBtn = new JButton("Register");
        registerForm.add(rBtn);

        // making array of all error labels, for combined styling and more
        ArrayList<JLabel> errors = new ArrayList<>();
        errors.add(nameErr);
        errors.add(emailErr);
        errors.add(passErr);
        errors.add(cPassErr);
        for (JLabel l : errors) {
            l.setForeground(Color.decode("#f26878"));
            l.setFont(new Font("Arial", Font.PLAIN, 11));
        }

        ArrayList<JTextField> fields = new ArrayList<>();
        fields.add(name);
        fields.add(email);
        fields.add(password);
        fields.add(cPassword);

        // register user setup
        rBtn.addActionListener(e -> {
            //check name
            if (name.getText().isEmpty()) {
                nameErr.setText("Name is Required!");
            } else {
                nameErr.setText("");
            }

            //check email
            if (email.getText().isEmpty()) {
                emailErr.setText("Email is Required!");
            } else {
                emailErr.setText("");
            }

            //check pass
            if (new String(password.getPassword()).isEmpty()) {
                passErr.setText("Password is Required!");
            } else if (password.getPassword().length < 8) {
                passErr.setText("Password should be atleast 8 characters long!");
            } else {
                passErr.setText("");
            }

            // check confrim password
            if (new String(cPassword.getPassword()).isEmpty() || !new String(cPassword.getPassword()).equals(new String(password.getPassword()))) {
                cPassErr.setText("Confirm Password!");
            } else {
                cPassErr.setText("");
            }

            boolean clean = true;
            for (JLabel l : errors) {
                if (!l.getText().isEmpty()) {
                    clean = false;
                    break;
                }
            }
            if (clean) {
                User user = new User(name.getText(), email.getText(), password.getPassword());
                String result = authManager.register(user);
                if (!result.equals("success")) {
                    emailErr.setText(result);
                } else {
                    for (JTextField field : fields) {
                        field.setText("");
                    }
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
            login(auth, app);
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

//        styles.setButtonsStyling(btns);
//        styles.setBackgroundsStyling(bgs);
//        styles.setLabelStyling(labels);
//        styles.setTextFieldsStyling(fields);
//        styles.setTitleStyling(l1);
    }

    public static void login(JFrame auth, JFrame app) {
        UserManager authManager = new UserManager();

        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        auth.setSize(1920, 1080);
        app.setSize(1920, 1080);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();

        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        auth.setVisible(true);
        JPanel registerForm = new JPanel();
        registerForm.add(logoLabel);
        auth.add(registerForm);
        registerForm.setLayout(new GridLayout(14, 1));
        registerForm.setBorder(BorderFactory.createEmptyBorder(220, 500, 0, 500));

        //email
        JLabel rl2 = new JLabel("Email : ");
        JTextField email = new JTextField("admin@super");
        JLabel emailErr = new JLabel();
        registerForm.add(rl2);
        registerForm.add(email);
        registerForm.add(emailErr);

        //password
        JLabel rl3 = new JLabel("Password : ");
        JPasswordField password = new JPasswordField("SUPERADMIN");
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

        // login user setup
        rBtn.addActionListener(e -> {
            //check email
            if (email.getText().isEmpty()) {
                emailErr.setText("Email is Required!");
            } else {
                emailErr.setText("");
            }

            //check pass
            if (password.getPassword().length == 0) {
                passErr.setText("Password is Required!");
            } else {
                passErr.setText("");
            }

            boolean clean = true;
            for (JLabel l : errors) {
                if (!l.getText().isEmpty()) {
                    clean = false;
                    break;
                }
            }
            if (clean) {
                User user = new User();
                user.setEmail(email.getText());
                user.setPassword(password.getPassword());
                String result = authManager.login(user);
                if (!result.contains("success")) {
                    if (result.contains("Password")) {
                        passErr.setText(result);
                    } else if (result.contains("unverified")) {
                        emailErr.setText("Admin has not been verified");
                    } else {
                        emailErr.setText(result);
                    }
                } else {
                    if (result.contains("super")) {
                        isSuper = true;
                    } else {
                        isSuper = false;
                    }
                    admin = email.getText();
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

    public static void inventory(JFrame app) {
        JTabbedPane pane = new JTabbedPane();
        JPanel entryForm = new JPanel();
        JPanel table = new JPanel();
        JPanel adminPanel = new JPanel();
        JPanel historyPanel = new JPanel();

        ImageIcon entryIcon = new ImageIcon(Main.class.getResource("/Media/square-pen.png"));
        ImageIcon monitorIcon = new ImageIcon(Main.class.getResource("/Media/square-activity.png"));
        ImageIcon adminIcon = new ImageIcon(Main.class.getResource("/Media/fullscreen.png"));
        ImageIcon historyIcon = new ImageIcon(Main.class.getResource("/Media/history.png"));

        pane.addTab("Product Entry", entryIcon, entryForm);
        pane.addTab("Monitor Stocks", monitorIcon, table);
        pane.addTab("History Logs", historyIcon, historyPanel);
        if (isSuper) {
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
        notificationHandler(app, new ProductSupplierPair(new Product(), new Supplier()));
        createMonthButtonsPanel(pane);
        adminPanel(adminPanel, pane, app);
        historyPanel(historyPanel, pane);
    }

    public static DefaultTableModel historyTableModel;

    public static void historyPanel(JPanel panel, JTabbedPane pane) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();

        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        String[] columnNames = {"ID", "Product", "Supplier", "Operation", "Dated"};

        historyTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(historyTableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        panel.add(container);
        updateHistory();
    }

    public static void updateHistory() {
        HistoryManager manager = new HistoryManager();
        historyTableModel.setRowCount(0);
        ArrayList<HistoryLog> logs = manager.fetchLogs();

        for (HistoryLog log : logs) {
            historyTableModel.addRow(new Object[]{log.getID(), log.getProduct().getName(), log.getSupplier().getName(), log.getOperation(), log.getDate()});
        }
    }

    public static void adminPanel(JPanel adminPanel, JTabbedPane pane, JFrame frame) {
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
        JPanel rightTop = new JPanel();
        JPanel rightBottom = new JPanel();
        right.add(rightTop);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(rightBottom);
        right.setBackground(Color.decode("#3c3f41"));

        left.setBackground(Color.decode("#46494b"));
        rightTop.setBackground(Color.decode("#46494b"));
        rightBottom.setBackground(Color.decode("#46494b"));
        left.setBorder(new FlatRoundBorder());
        rightTop.setBorder(new FlatRoundBorder());
        rightBottom.setBorder(new FlatRoundBorder());

        JLabel leftTitle = new JLabel("Manage Stocks");
        JLabel rightTitle = new JLabel("Manage Users");
        JLabel rightBTitle = new JLabel("Insights");
        leftTitle.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightTitle.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        leftTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightBTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightBTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightBTitle.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));

        left.add(leftTitle);
        rightTop.add(rightTitle);
        rightBottom.add(rightBTitle);

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        rightTop.setLayout(new BoxLayout(rightTop, BoxLayout.Y_AXIS));
        rightBottom.setLayout(new BoxLayout(rightBottom, BoxLayout.Y_AXIS));

        container.add(left);
        container.add(right);
        adminPanel.add(container);
        manageStocks(left, pane, leftTitle, frame);
        manageUsers(rightTop, rightTitle);
        insights(rightBottom);
    }

    public static void insights(JPanel insights) {
        Insights insightsManager = new Insights();
        JPanel container = new JPanel();
        insights.setMaximumSize(new Dimension(Short.MAX_VALUE, insights.getPreferredSize().height));
        container.setLayout(new GridLayout(2, 2, 20, 20));
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        container.setBackground(Color.decode("#46494b"));
        JLabel stocksLabel = new JLabel("Total Stocks");
        JLabel amountLabel = new JLabel("Total Amount");
        JLabel suppliersLabel = new JLabel("Total Suppliers");
        JLabel productsLabel = new JLabel("Total Products");

        stocksLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        suppliersLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        productsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        stocksLabel.setForeground(Color.decode("#8c8c8c"));
        amountLabel.setForeground(Color.decode("#8c8c8c"));
        suppliersLabel.setForeground(Color.decode("#8c8c8c"));
        productsLabel.setForeground(Color.decode("#8c8c8c"));

        JLabel stocksValue = new JLabel("1200");
        JLabel amountValue = new JLabel("123092");
        JLabel suppliersValue = new JLabel("9");
        JLabel productsValue = new JLabel("12");

        stocksValue.setFont(new Font("Monospaced", Font.BOLD, 56));
        amountValue.setFont(new Font("Monospaced", Font.BOLD, 56));
        suppliersValue.setFont(new Font("Monospaced", Font.BOLD, 56));
        productsValue.setFont(new Font("Monospaced", Font.BOLD, 56));

        stocksValue.setText(String.valueOf(insightsManager.getStocks()));
        productsValue.setText(String.valueOf(insightsManager.getProducts()));
        suppliersValue.setText(String.valueOf(insightsManager.getSuppliers()));
        amountValue.setText(String.valueOf(insightsManager.getTotalAmount()));

        JPanel stocks = new JPanel();
        JPanel amount = new JPanel();
        JPanel suppliers = new JPanel();
        JPanel products = new JPanel();

        stocks.setLayout(new BoxLayout(stocks, BoxLayout.Y_AXIS));
        amount.setLayout(new BoxLayout(amount, BoxLayout.Y_AXIS));
        products.setLayout(new BoxLayout(products, BoxLayout.Y_AXIS));
        suppliers.setLayout(new BoxLayout(suppliers, BoxLayout.Y_AXIS));

        stocks.setBackground(Color.decode("#46494b"));
        products.setBackground(Color.decode("#46494b"));
        suppliers.setBackground(Color.decode("#46494b"));
        amount.setBackground(Color.decode("#46494b"));

        stocks.add(stocksLabel);
        stocks.add(stocksValue);

        amount.add(amountLabel);
        amount.add(amountValue);

        suppliers.add(suppliersLabel);
        suppliers.add(suppliersValue);

        products.add(productsLabel);
        products.add(productsValue);

        container.add(products);
        container.add(amount);
        container.add(suppliers);
        container.add(stocks);
        insights.add(container);
    }

    public static void manageStocks(JPanel container, JTabbedPane pane, JLabel title, JFrame frame) {
        ProductManager manager = new ProductManager();
        SupplierManager supplierManager = new SupplierManager();
        HistoryManager historyManager = new HistoryManager();

        StocksMonitoring monitor = new StocksMonitoring();
        JPanel records = new JPanel();
        records.setBorder(BorderFactory.createEmptyBorder());
        container.removeAll();
        container.add(title);
        JScrollPane scrollPane = new JScrollPane(records);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        records.setLayout(new BoxLayout(records, BoxLayout.Y_AXIS));
        records.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList<ProductSupplierPair> products = monitor.loadProducts();
        ImageIcon reorderIconImage = new ImageIcon(Main.class.getResource("/Media/rotate-cw.png"));
        ImageIcon deleteIconImage = new ImageIcon(Main.class.getResource("/Media/trash-2.png"));
        records.setBackground(Color.decode("#46494b"));

        for (ProductSupplierPair product : products) {
            JPanel record = new JPanel();
            record.setLayout(new BorderLayout());
            JLabel details = new JLabel(product.getProduct().getName().toUpperCase() + " BY " + product.getSupplier().getName().toUpperCase());
            details.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            JPanel actionButtons = new JPanel();
            JButton reorder = new JButton("");
            JButton delete = new JButton("");
            JLabel reorderLabel = new JLabel("reorder");
            reorderLabel.setBorder(BorderFactory.createEmptyBorder(0, 23, 0, 0));
            reorderLabel.setForeground(Color.DARK_GRAY);
            reorder.add(reorderLabel);
            reorder.setCursor(new Cursor(Cursor.HAND_CURSOR));
            delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
            actionButtons.add(reorder);
            actionButtons.add(delete);
            actionButtons.setOpaque(false);
            actionButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
            record.add(details, BorderLayout.WEST);
            record.add(actionButtons, BorderLayout.EAST);
            delete.setMargin(new Insets(6, 6, 6, 6));

            JLabel trashIcon = new JLabel(deleteIconImage);

            JLabel rotateIcon = new JLabel(reorderIconImage);

            reorder.add(rotateIcon);
            delete.setForeground(Color.DARK_GRAY);
            delete.add(trashIcon);
            reorder.setMargin(new Insets(6, 7, 6, 7));
            reorder.setBackground(Color.decode("#73d187"));
            delete.setBackground(Color.decode("#f26878"));

            reorder.addActionListener(e -> {
                notificationHandler(frame, product);
            });
            delete.addActionListener(e -> {
                manager.deleteProduct(product.getProduct().getID());
                product.getSupplier().setID(supplierManager.getSupplierID(product.getSupplier().getName(), product.getSupplier().getEmail()));
                product.getProduct().setID(manager.getProductID(product.getProduct().getName(), product.getSupplier().getID()));
                supplierManager.deleteSupplier(product.getSupplier());

                historyManager.addLog(new HistoryLog(0, "Deleted", product.getProduct(), product.getSupplier(), ""));

                manageStocks(container, pane, title, frame);
                populateTable(0);
                updateHistory();
            });
            record.setMaximumSize(new Dimension(Short.MAX_VALUE, record.getPreferredSize().height));
            record.setBorder(new FlatRoundBorder());

            records.add(record);
            records.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        container.add(scrollPane);

    }

    public static void manageUsers(JPanel container, JLabel title) {
        UserManager manager = new UserManager();
        JPanel records = new JPanel();
        records.setBorder(BorderFactory.createEmptyBorder());
        container.removeAll();
        container.add(title);
        JScrollPane scrollPane = new JScrollPane(records);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        records.setLayout(new BoxLayout(records, BoxLayout.Y_AXIS));
        records.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        records.setBackground(Color.decode("#46494b"));

        ArrayList<User> users = manager.fetchUsers();

        ImageIcon deleteIconImage = new ImageIcon(Main.class.getResource("/Media/trash-2.png"));
        ImageIcon approveIconImage = new ImageIcon(Main.class.getResource("/Media/circle-check.png"));
        ImageIcon provokeIconImage = new ImageIcon(Main.class.getResource("/Media/circle.png"));

        for (User user : users) {
            if (!user.getEmail().contains("super")) {
                JPanel record = new JPanel();
                record.setLayout(new BorderLayout());
                JLabel details = new JLabel(user.getName() + " | " + user.getEmail());
                details.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                JPanel actionButtons = new JPanel();

                if (user.getVerified()) {
                    JButton refuseBtn = new JButton("");
                    JLabel refuseLabel = new JLabel("Revoke");
                    refuseLabel.setForeground(Color.DARK_GRAY);
                    refuseLabel.setBorder(BorderFactory.createEmptyBorder(0, 21, 0, 0));
                    refuseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    refuseBtn.setMargin(new Insets(6, 10, 6, 10));
                    refuseBtn.add(new JLabel(provokeIconImage));
                    refuseBtn.add(refuseLabel);
                    actionButtons.add(refuseBtn);

                    refuseBtn.setBackground(Color.decode("#f26878"));

                    refuseBtn.addActionListener(e -> {
                        manager.provokeUser(user.getID());
                        manageUsers(container, title);
                    });

                } else {
                    JButton approveBtn = new JButton("");
                    JLabel approveLabel = new JLabel("Approve");
                    approveLabel.setForeground(Color.DARK_GRAY);
                    approveLabel.setBorder(BorderFactory.createEmptyBorder(0, 21, 0, 0));
                    approveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    approveBtn.setBackground(Color.decode("#73d187"));
                    approveBtn.add(new JLabel(approveIconImage));
                    approveBtn.add(approveLabel);
                    approveBtn.setMargin(new Insets(6, 10, 6, 10));
                    actionButtons.add(approveBtn);

                    approveBtn.addActionListener(e -> {
                        manager.approveUser(user.getID());
                        manageUsers(container, title);
                    });
                }

                JButton delete = new JButton("");
                delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                delete.add(new JLabel(deleteIconImage));
                delete.setBackground(Color.decode("#f26878"));

                actionButtons.add(delete);
                actionButtons.setOpaque(false);
                actionButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
                record.add(details, BorderLayout.WEST);
                record.add(actionButtons, BorderLayout.EAST);
                delete.setMargin(new Insets(6, 7, 6, 7));
                delete.addActionListener(e -> {
                    manager.deleteUser(user.getID());
                    manageUsers(container, title);
                });
                record.setMaximumSize(new Dimension(Short.MAX_VALUE, record.getPreferredSize().height));
                record.setBorder(new FlatRoundBorder());
                records.add(record);
                records.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        container.add(scrollPane);
    }

    public static void addProduct(JPanel entryForm, JButton editBtn, JButton addBtn) {
        ProductManager productManager = new ProductManager();
        SupplierManager supplierManager = new SupplierManager();
        HistoryManager historyManager = new HistoryManager();

        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));

        left.setLayout(new GridLayout(15, 1));
        right.setLayout(new GridLayout(15, 1));

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
//        dSIcon.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 0));

        JButton addProductButton = new JButton();       
        JLabel addLabel = new JLabel("Add Product");    

        addProductButton.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        addLabel.setForeground(Color.DARK_GRAY);
        
        addProductButton.add(dSIcon);
        addProductButton.add(addLabel);

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
            try {
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

                for (JLabel err : errors) {
                    if (!err.getText().isEmpty()) {
                        clean = false;
                        break;
                    }
                }
                if (clean) {
                    Supplier supplier = new Supplier(0, sName.getText(), sEmail.getText());
                    Product product = new Product(0, pName.getText(), pCateg.getText(), parseInt(pQty.getText()), parseDouble(pPrice.getText()));

                    String result = productManager.addProduct(product, supplier);

                    supplier.setID(supplierManager.getSupplierID(supplier.getName(), supplier.getEmail()));
                    product.setID(productManager.getProductID(product.getName(), supplier.getID()));
                    if (result.contains("success")) {
                        pName.setText("");
                        pCateg.setText("");
                        pQty.setText("");
                        pPrice.setText("");
                        sName.setText("");
                        sEmail.setText("");
                        status.setText(product.getName() + " Added Successfully ");
                        status.setForeground(Color.decode("#73d187"));
                        historyManager.addLog(new HistoryLog(0, "Added", product, supplier, ""));
                        populateTable(0);
                        updateHistory();
                    } else {
                        status.setText("Error Occured adding " + product.getName());
                        status.setForeground(Color.decode("#f26878"));
                    }
                    if (result.contains("updated")) {
                        status.setText(product.getName() + " Updated SuccessFully ");
                        status.setForeground(Color.decode("#73d187"));
                        for (JLabel error : errors) {
                            error.setText("");
                        }
                        historyManager.addLog(new HistoryLog(0, "Updated", product, supplier, ""));
                        populateTable(0);
                        updateHistory();
                    }
                }

            } catch (NumberFormatException ne) {
                status.setText("Invalid Data types");
                status.setForeground(Color.decode("#f26878"));
            }

        });

        editBtn.addActionListener(e -> {
            if (!editMode) {
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
        styles.setButtonsStyling(btns);
        addProductButton.setBackground(Color.decode("#73d187"));
        addProductButton.setForeground(Color.DARK_GRAY);

    }

    public static void editProduct(JPanel entryForm, JButton addBtn, JButton editBtn) {
        ProductManager productManager = new ProductManager();
        HistoryManager historyManager = new HistoryManager();
        SupplierManager supplierManager = new SupplierManager();
        JPanel form = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        form.setLayout(new GridLayout(1, 2, 20, 0));

        left.setLayout(new GridLayout(15, 1));
        right.setLayout(new GridLayout(15, 1));

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

        JButton saveProductButton = new JButton();
        JButton loadProductButton = new JButton();

        loadProductButton.add(lIcon);
        JLabel loadLabel = new JLabel("Load Product");
        loadProductButton.add(loadLabel);

        saveProductButton.add(sIcon);
        JLabel saveLabel = new JLabel("Save Product");

        saveProductButton.add(saveLabel);
        
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
        btns.setLayout(new GridLayout(1, 2, 10, 0));
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
            if (pID.getText().isEmpty()) {
                pIDErr.setText("ID is required!");
            } else {
                pIDErr.setText("");
                ProductSupplierPair pair = productManager.loadProduct(parseInt(pID.getText()));
                if (pair != null) {
                    saveProductButton.setEnabled(true);
                    pName.setText(pair.getProduct().getName());
                    pCateg.setText(pair.getProduct().getCategory());
                    pQty.setText(String.valueOf(pair.getProduct().getQuantity()));
                    pPrice.setText(String.valueOf(pair.getProduct().getPrice()));
                    sName.setText(pair.getSupplier().getName());
                    sEmail.setText(pair.getSupplier().getEmail());
                    for (JLabel err : errors) {
                        err.setText("");
                    }
                    status.setText("Loaded successfully");
                    saveProductButton.remove(saveLabel);
                    saveProductButton.remove(sIcon);
                    saveProductButton.add(dSIcon);
                    saveProductButton.add(saveLabel);
                    saveLabel.setForeground(Color.DARK_GRAY);
                    status.setForeground(Color.decode("#73d187"));

                    saveProductButton.addActionListener(f -> {
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
                        if (pID.getText().isEmpty()) {
                            pIDErr.setText("ID is required!");
                        } else {
                            pIDErr.setText("");
                        }

                        boolean clean = true;

                        for (JLabel err : errors) {
                            if (!err.getText().isEmpty()) {
                                clean = false;
                                break;
                            }
                        }
                        if (clean) {
                            Product product = new Product(parseInt(pID.getText()), pName.getText(), pCateg.getText(), parseInt(pQty.getText()), parseDouble(pPrice.getText()));
                            Supplier supplier = new Supplier(0, sName.getText(), sEmail.getText());

                            supplier.setID(supplierManager.getSupplierID(supplier.getName(), supplier.getEmail()));
                            product.setID(productManager.getProductID(product.getName(), supplier.getID()));

                            String result = productManager.updateProduct(product, supplier);
                            if (result.contains("success")) {
                                status.setText("Updated Successfully!");
                                historyManager.addLog(new HistoryLog(0, "Updated", product, supplier, ""));
                                for (JTextField tF : inputs) {
                                    tF.setText("");
                                }

                                populateTable(0);
                                updateHistory();
                            } else {
                                status.setText("Error Occurred!");
                            }
                        }
                        if (status.getText().contains("Updated Successfully!")) {
                            for (JLabel error : errors) {
                                error.setText("");
                            }
                        }
                    });

                } else {
                    pIDErr.setText("Product Doesn't Exist");
                    saveProductButton.setEnabled(false);
                    saveProductButton.remove(dSIcon);
                    saveProductButton.remove(saveLabel);
                    saveProductButton.add(sIcon);
                    saveProductButton.add(saveLabel);
                    saveLabel.setForeground(Color.WHITE);
                    
                    for (JTextField tF : inputs) {
                        tF.setText("");
                    }
                }
            }
        });

        addBtn.addActionListener(e -> {
            if (editMode) {
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

        for (JLabel error : errors) {
            error.setForeground(Color.decode("#f26878"));
            error.setFont(new Font("Arial", Font.PLAIN, 11));
        }

    }

    public static DefaultTableModel stockTableModel;

    public static JTable stockTable;

    public static void monitorStocks(JPanel tab) {
        tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
        tab.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));

        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);

        ImageIcon sortByAlphaImage = new ImageIcon(Main.class.getResource("/Media/arrow-down-a-z.png"));
        ImageIcon sortByQtyImage = new ImageIcon(Main.class.getResource("/Media/arrow-up-0-1.png"));
        ImageIcon sortByDateImage = new ImageIcon(Main.class.getResource("/Media/calendar-arrow-down.png"));
        ImageIcon sortByPriceImage = new ImageIcon(Main.class.getResource("/Media/dollar-sign.png"));

        JLabel sortByQtyIcon = new JLabel(sortByQtyImage);
        JLabel sortByAlphaIcon = new JLabel(sortByAlphaImage);
        JLabel sortByDateIcon = new JLabel(sortByDateImage);
        JLabel sortByPriceIcon = new JLabel(sortByPriceImage);

        JPanel filtersContainer = new JPanel();
        JButton sortByNameBtn = new JButton();
        JButton sortByQtyBtn = new JButton();
        JButton sortByDateBtn = new JButton();
        JButton sortByPriceBtn = new JButton();

        sortByNameBtn.add(sortByAlphaIcon);
        sortByQtyBtn.add(sortByQtyIcon);
        sortByDateBtn.add(sortByDateIcon);
        sortByPriceBtn.add(sortByPriceIcon);

        JLabel byName = new JLabel("sort by name");
        JLabel byQty = new JLabel("sort by quantity");
        JLabel byDate = new JLabel("sort by date");
        JLabel byPrice = new JLabel("sort by price");

        sortByNameBtn.add(byName);
        sortByQtyBtn.add(byQty);
        sortByDateBtn.add(byDate);
        sortByPriceBtn.add(byPrice);

        filtersContainer.add(sortByNameBtn);
        filtersContainer.add(sortByQtyBtn);
        filtersContainer.add(sortByDateBtn);
        filtersContainer.add(sortByPriceBtn);

        JLabel title = new JLabel(logoIcon);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        tab.add(title);

        String[] columnNames = {"ID", "Name", "Category", "Quantity", "Price", "TotalAmount", "SupplierID", "SupplierName", "SupplierEmail"};

        stockTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        populateTable(0);

        sortByQtyBtn.addActionListener(e -> populateTable(1));
        sortByNameBtn.addActionListener(e -> populateTable(2));
        sortByPriceBtn.addActionListener(e -> populateTable(3));
        sortByDateBtn.addActionListener(e -> populateTable(0));

        stockTable = new JTable(stockTableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);

        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);
        tab.add(container);

        ReportsManager reportsManager = new ReportsManager();
        JPanel reportActions = new JPanel();

        JLabel pdfLabel = new JLabel("generate pdf report");
        JLabel csvLabel = new JLabel("generate csv report");

        JButton generateReportBtn = new JButton();
        JButton generateCSVReportBtn = new JButton();

        ImageIcon pdfIcon = new ImageIcon(Main.class.getResource("/Media/pdf-icon.png"));
        JLabel pdfIconL = new JLabel(pdfIcon);
        generateReportBtn.add(pdfIconL);

        ImageIcon csvIcon = new ImageIcon(Main.class.getResource("/Media/csv.png"));
        JLabel csvIconL = new JLabel(csvIcon);
        generateCSVReportBtn.add(csvIconL);

        generateReportBtn.add(pdfLabel);
        generateCSVReportBtn.add(csvLabel);

        generateReportBtn.addActionListener(e -> reportsManager.generateStockReport(stockTable, admin));
        generateCSVReportBtn.addActionListener(e -> reportsManager.generateStockReportCSV(stockTable, admin));

        generateReportBtn.setBackground(Color.decode("#a3453e"));
        generateCSVReportBtn.setBackground(Color.decode("#3ea35c"));
        csvLabel.setForeground(Color.DARK_GRAY);

        reportActions.add(generateReportBtn);
        reportActions.add(generateCSVReportBtn);

        Styles styles = new Styles();
        ArrayList<JButton> fBtns = new ArrayList<>();
        fBtns.add(sortByQtyBtn);
        fBtns.add(sortByNameBtn);
        fBtns.add(sortByDateBtn);
        fBtns.add(sortByPriceBtn);
        fBtns.add(generateReportBtn);
        fBtns.add(generateCSVReportBtn);
        styles.setButtonsStyling(fBtns);

        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        footer.add(filtersContainer, BorderLayout.WEST);
        footer.add(reportActions, BorderLayout.EAST);
        footer.setMaximumSize(new Dimension(Short.MAX_VALUE, footer.getPreferredSize().height));

        tab.add(footer);
    }

    public static void createMonthButtonsPanel(JTabbedPane pane) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));

    ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
    Image logoImage = logo.getImage();
    Image resizedLogo = logoImage.getScaledInstance(320, 50, Image.SCALE_SMOOTH);
    JLabel title = new JLabel(new ImageIcon(resizedLogo));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(title);

    JPanel reportsContainer = new JPanel();
    reportsContainer.setLayout(new BoxLayout(reportsContainer, BoxLayout.Y_AXIS));
    reportsContainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 300, 10, 300), BorderFactory.createCompoundBorder(new FlatRoundBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10))));

    JScrollPane scrollPane = new JScrollPane(reportsContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBorder(null);
    Map<String, ArrayList<ProductSupplierPair>> monthMap = ReportsManager.fetchMonthlyProductSupplierPairs();
    
    JLabel mTitle = new JLabel("Monthly Reports");
    mTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    mTitle.setFont(new Font("Arial", Font.BOLD, 16));
    mTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    reportsContainer.add(mTitle);
    for (String month : monthMap.keySet()) {
        JPanel record = new JPanel(new BorderLayout());
        record.setBorder(new FlatRoundBorder());
//        record.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10),new FlatRoundBorder()));
        record.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        record.setBackground(Color.decode("#46494b"));

        JLabel monthTitle = new JLabel(month);
        monthTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        monthTitle.setFont(new Font("Arial", Font.BOLD, 12));
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.setOpaque(false);

        JButton generateReportBtn = new JButton();
        JButton generateCSVReportBtn = new JButton();

        ImageIcon pdfIcon = new ImageIcon(Main.class.getResource("/Media/pdf-icon.png"));
        generateReportBtn.setIcon(pdfIcon);
        generateReportBtn.setText("Generate PDF");

        ImageIcon csvIcon = new ImageIcon(Main.class.getResource("/Media/csv.png"));
        generateCSVReportBtn.setIcon(csvIcon);
        generateCSVReportBtn.setText("Generate CSV");
        generateCSVReportBtn.setForeground(Color.DARK_GRAY);
        
        generateReportBtn.setBackground(Color.decode("#a3453e"));
        generateCSVReportBtn.setBackground(Color.decode("#3ea35c"));

        generateReportBtn.addActionListener(e -> {
            ArrayList<ProductSupplierPair> list = monthMap.get(month);
            if (list != null && !list.isEmpty()) {
                ReportsManager.generateReportForMonth(month, list, admin);
            } else {
                JOptionPane.showMessageDialog(null, "No data for " + month);
            }
        });

        generateCSVReportBtn.addActionListener(e -> {
            ArrayList<ProductSupplierPair> list = monthMap.get(month);
            if (list != null && !list.isEmpty()) {
                ReportsManager.generateCSVReportForMonth(month, list, admin);
            } else {
                JOptionPane.showMessageDialog(null, "No data for " + month);
            }
        });
        Styles styles = new Styles();
        ArrayList<JButton> jBtns = new ArrayList<>();
        jBtns.add(generateReportBtn);
        jBtns.add(generateCSVReportBtn);
        styles.setButtonsStyling(jBtns);
        btns.add(generateReportBtn);
        btns.add(generateCSVReportBtn);

        record.add(monthTitle, BorderLayout.WEST);
        record.add(btns, BorderLayout.EAST);

        reportsContainer.add(Box.createVerticalStrut(5));
        reportsContainer.add(record);
    }

    panel.add(scrollPane);
    
    ImageIcon reportsIcon = new ImageIcon(Main.class.getResource("/Media/reports.png"));

    pane.addTab("Monthly Reports", reportsIcon, panel);
}


    public static void populateTable(int sortIndex) {
        StocksMonitoring monitor = new StocksMonitoring();
        stockTableModel.setRowCount(0);
        ArrayList<ProductSupplierPair> products = monitor.loadProducts();

        switch (sortIndex) {
            case 1:
                products.sort(Comparator.comparingInt(p -> p.getProduct().getQuantity()));
                break;
            case 2:
                products.sort(Comparator.comparing(p -> p.getProduct().getName().toLowerCase()));
                break;
            case 3:
                products.sort(Comparator.comparingDouble(p -> p.getProduct().getPrice()));
                break;
            default:
                break;
        }

        for (ProductSupplierPair product : products) {
            stockTableModel.addRow(new Object[]{
                product.getProduct().getID(),
                product.getProduct().getName(),
                product.getProduct().getCategory(),
                product.getProduct().getQuantity(),
                product.getProduct().getPrice(),
                product.getTotalAmount(),
                product.getSupplier().getID(),
                product.getSupplier().getName(),
                product.getSupplier().getEmail()
            });
        }
    }

    public static void notificationHandler(JFrame app, ProductSupplierPair p) {
        JDialog notificationPopUp = new JDialog(app, "Reorder Products");
        notificationPopUp.setLocationRelativeTo(null);
        notificationPopUp.setSize(600, 300);
        JButton cancel = new JButton("CANCEL");
        cancel.setFont(new Font("Arial", Font.BOLD, 12));
        cancel.setBackground(Color.decode("#f26878"));
        cancel.setForeground(Color.DARK_GRAY);

        cancel.setMargin(new Insets(7, 7, 7, 7));

        ImageIcon cancelIconImage = new ImageIcon(Main.class.getResource("/Media/circle.png"));
        JLabel cancelIcon = new JLabel(cancelIconImage);
        cancelIcon.setBorder(BorderFactory.createEmptyBorder(0, 230, 0, 0));
        cancel.add(cancelIcon);
        cancel.addActionListener(e -> {
            notificationPopUp.dispose();
        });

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        notificationPopUp.add(container);
        renderReorderProducts(container, notificationPopUp, cancel, p);
    }

    public static void renderReorderProducts(JPanel container, JDialog popUp, JButton cancel, ProductSupplierPair p) {
        HistoryManager historyManager = new HistoryManager();
        container.removeAll();
        ImageIcon logo = new ImageIcon(Main.class.getResource("/Media/logo.png"));
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(192, 30, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        boolean isFound = false;
        container.add(logoLabel);
        Reorder reorder = new Reorder();
        ArrayList<ProductSupplierPair> products = reorder.loadProducts();
        for (ProductSupplierPair product : products) {
            if (product.getProduct().getID() == p.getProduct().getID()) {
                product.setProduct(p.getProduct());
                isFound = true;
                break;
            }
        }
        if (!isFound && !p.getProduct().getName().isEmpty()) {
            products.add(p);
        }
        ImageIcon darkSaveIcon = new ImageIcon(Main.class.getResource("/Media/saveDark.png"));
        if (!products.isEmpty()) {
            container.setLayout(new GridLayout(0, 1, 10, 10));
            for (ProductSupplierPair product : products) {
                JLabel dSIcon = new JLabel(darkSaveIcon);
                dSIcon.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
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
                        product.getProduct().setQuantity(product.getProduct().getQuantity() + parseInt(reorderQty.getText()));
                        renderReorderProducts(container, popUp, cancel, new ProductSupplierPair(new Product(), new Supplier()));
                        populateTable(0);
                        historyManager.addLog(new HistoryLog(product.getProduct().getID(), "Reordered", product.getProduct(), product.getSupplier(), ""));
                        updateHistory();
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
