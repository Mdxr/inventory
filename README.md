![](src/Media/logo.png)
<p align="center">
    An Inventory Tracking Desktop Application
</p>

## Features
#### Regular User
- Product Entry
- Monitor Stocks
    - Sort by Name, Date, Price, Quantity
- History Logs
    - Logs for Adding, Updating, Deleting and Reordering Products
- Reorder Notification
- Report Generation
    - Full stock (PDF / CSV)
    - Monthly (PDF / CSV)

#### Admin (Admin Panel Extended to Regular User)
- Manage Users
    - Allow/Revoke Access
    - Manage Stocks
- Manage Stocks
    - Delete Products
    - Reorder Products

## Get Started

#### -> Cloning
Open Terminal and Run the Following Command

    git clone https://github.com/Mdxr/inventory
And then

    cd inventory

Open this project in your IDE (NetBeans, InteliJ IDEA are Recommened)

#### -> Setting Up
#### JDBC Connection
- Change the Username, Password and Database Name in the DBManager Class inside DB Package
- Import the tables from `crud.sql` file present in repo's root directory into your sql Database
#### Setting up libraries
All Required Jar Files are Present in the `libs` directory. Include them all in the project's Libraries

#### Finishing Up
- Run the project and Register a new Account
- Login as Admin to Approve the new Account
    - Admin Credentials
        - Email : admin@super
        - Password : SUPERADMIN
- Thats it!.



