package org.liying.jdbc;

import org.liying.jdbc.OrderJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderJDBCDao {
    // dealer1 not dealer (depends which db you migrate your table)
    static final String DBURL = "jdbc:postgresql://localhost:5431/dealer2";
    static final String USER = "admin";
    static final String PASS = "password";

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<OrderJDBC> getOrders() {

        List<OrderJDBC> orders = new ArrayList();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
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
                Long totalAmount = rs.getLong("total_amount");
                String paymentMethod = rs.getString("payment_method");
                Long consumerId = rs.getLong("consumer_id");

                //Fill the object
                OrderJDBC order = new OrderJDBC();

                order.setId(id);
                order.setTotalAmount(totalAmount);
                order.setPaymentMethod(paymentMethod);
               // order.setConsumerId(consumerId);

                orders.add(order);
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
        return orders;
    }

}

