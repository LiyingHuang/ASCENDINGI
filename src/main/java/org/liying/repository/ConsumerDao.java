package org.liying.repository;
import org.liying.model.Consumer;
import org.liying.model.ShoppingPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ConsumerDao {
    static final String DBURL = "jdbc:postgresql://localhost:5431/dealer2";
    static final String USER = "admin";
    static final String PASS = "password";
    private Logger logger = LoggerFactory.getLogger(getClass());
    public List<Consumer> getConsumers() {
        List<Consumer> consumers = new ArrayList();
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
            sql = "SELECT * FROM consumers";
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
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Long shippingPlatformI =rs.getLong("shopping_platform_id ");
                //Fill the object
                Consumer consumer = new Consumer();
                consumer.setId(id);
                consumer.setName(name);
                consumer.setAddress(address);
                consumer.setPhone(phone);
                consumer.setShippingPlatformID(shippingPlatformI);
                consumers.add(consumer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            logger.error("error", e);
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
        return consumers;
    }
    public static void main(String[] args){
        ConsumerDao consumerJDBCDao = new ConsumerDao();
        System.out.println(consumerJDBCDao.getConsumers());
        System.out.println(consumerJDBCDao.getConsumers().size());
    }
}
