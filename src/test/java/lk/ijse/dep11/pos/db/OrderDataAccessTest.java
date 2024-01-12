package lk.ijse.dep11.pos.db;

import lk.ijse.dep11.pos.tm.Customer;
import lk.ijse.dep11.pos.tm.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderDataAccessTest {

    @BeforeEach
    void setUp() throws SQLException {
        SingletonConnectionDataSource.getInstance().getConnection().setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        SingletonConnectionDataSource.getInstance().getConnection().rollback();
        SingletonConnectionDataSource.getInstance().getConnection().setAutoCommit(true);
    }

    @Test
    void getLastOrderId() throws SQLException {
        ResultSet rst = SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                .executeQuery("SELECT COUNT(id) FROM \"order\"");
        rst.next();
        int count = rst.getInt(1);
        if(count == 0){
            assertNull(OrderDataAccess.getLastOrderId());

            CustomerDataAccess.saveCustomer(new Customer("ABC", "Crazy","Panadura"));
            SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                    .executeUpdate("INSERT INTO \"order\" (id, customer_id) VALUES ('111111', 'ABC')");
            assertNotNull(OrderDataAccess.getLastOrderId());
        }else {
            assertNotNull(OrderDataAccess.getLastOrderId());
        }

    }

    @Test
    void existsOrderByCustomerId() throws SQLException {
        assertDoesNotThrow(() -> assertFalse(OrderDataAccess.existsOrderByCustomerId("ABC")));
        CustomerDataAccess.saveCustomer(new Customer("ABC", "Crazy", "Panadura"));
        SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                .executeUpdate("INSERT INTO \"order\" (id, customer_id) VALUES ('11111', 'ABC')");
        assertDoesNotThrow(() -> assertTrue(OrderDataAccess.existsOrderByCustomerId("ABC")));
    }

    @Test
    void existsOrderByItemCode() throws SQLException {
        assertDoesNotThrow(() -> assertFalse(OrderDataAccess.existsOrderByItemCode("Crazy item code")));

        ItemDataAccess.saveItem(new Item("IT111111", "Crazy Item", 5, new BigDecimal("1234")));
        CustomerDataAccess.saveCustomer(new Customer("ABC", "Crazy","Panadura"));
        SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                .executeUpdate("INSERT INTO \"order\" (id, customer_id) VALUES ('11111', 'ABC')");
        SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                .executeUpdate("INSERT INTO order_item (order_id, item_code, qty, unit_price) VALUES ('11111', 'IT111111', 2, 1230.00)");
        assertTrue(OrderDataAccess.existsOrderByItemCode("IT111111"));

    }
}