package org.liying.jdbc;

import org.liying.jdbc.ShoppingPlatformJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO implemented by the underlying code for the first time(JDBC)
public class ShoppingPlatformJDBCDao {

    // dealer1 not dealer (depends which db you migrate your table to)
    static final String DBURL = "jdbc:postgresql://localhost:5431/dealer2";
    static final String USER = "admin";
    static final String PASS = "password";

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<ShoppingPlatformJDBC> getShoppingPlatforms() {

        List<ShoppingPlatformJDBC> shoppingPlatforms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
//            Requires using the DriverManager.getConnection() method
//            to create a Connection object,
//            which represents a physical connection with a database server.
            logger.debug("open connection...");

            conn = DriverManager.getConnection(DBURL, USER, PASS);
            //STEP 3: Execute a query
            logger.info("create statement...");
            stmt = conn.createStatement();
            String sql;

            // the table name : shoppingPlatforms not shoppingPlatform
            sql = "SELECT * FROM shopping_platforms";
            try {
                rs = stmt.executeQuery(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //STEP 4: Extract data from result set
            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //Retrieve by column name
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String website = rs.getString("website");
                String shippingMethod = rs.getString("shipping_method");

                //Fill the object
                ShoppingPlatformJDBC shoppingPlatform = new ShoppingPlatformJDBC();

                shoppingPlatform.setId(id);
                shoppingPlatform.setName(name);
                shoppingPlatform.setWebsite(website);
                shoppingPlatform.setShippingMethod(shippingMethod);

                shoppingPlatforms.add(shoppingPlatform);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("error", e);
        } finally {
            //STEP 6: finally block used to close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return shoppingPlatforms;
    }
    public static void main(String[] args){
        ShoppingPlatformJDBCDao shoppingPlatformJDBCDao = new ShoppingPlatformJDBCDao();
        System.out.println(shoppingPlatformJDBCDao.getShoppingPlatforms());
        System.out.println(shoppingPlatformJDBCDao.getShoppingPlatforms().size());
    }
}


