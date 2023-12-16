package lk.ijse.dep11.pos.db;

import lk.ijse.dep11.pos.tm.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDataAccessTest {

    @Test
    void sqlSyntax() {
        assertDoesNotThrow(()-> Class.forName("lk.ijse.dep11.pos.db.CustomerDataAccess"));
    }

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
    void getAllCustomer() throws SQLException {
        CustomerDataAccess.saveCustomer(new Customer("ABC", "Kasun Sampath", "Galle"));
        CustomerDataAccess.saveCustomer(new Customer("EDF", "Nilan Dinusha", "Matara"));
        assertDoesNotThrow(() -> {
            List<Customer> customerList = CustomerDataAccess.getAllCustomer();
            assertTrue(customerList.size() >= 2);
        });

    }

    @Test
    void saveCustomer() {
        assertDoesNotThrow(()-> {
            CustomerDataAccess.saveCustomer(new Customer("ABC", "Kasun Sampath", "Galle"));
            CustomerDataAccess.saveCustomer(new Customer("EDF", "Nilan Dinusha", "Matara"));
        });
        assertThrows(SQLException.class, ()-> CustomerDataAccess
                .saveCustomer(new Customer("ABC", "Madushan Chamara", "Panadura")));
    }

    @Test
    void updateCustomer() throws SQLException {
        CustomerDataAccess.saveCustomer(new Customer("ABC", "Kasun Sampath", "Galle"));
        assertDoesNotThrow(()-> CustomerDataAccess.updateCustomer(new Customer("ABC", "Ruwan Sampath", "Galle")));

    }

    @Test
    void deleteCustomer() throws SQLException {
        CustomerDataAccess.saveCustomer(new Customer("ABC", "Kasun Sampath", "Galle"));
        int size = CustomerDataAccess.getAllCustomer().size();
        assertDoesNotThrow(()-> {
            CustomerDataAccess.deleteCustomer("ABC");
            assertEquals(size - 1, CustomerDataAccess.getAllCustomer().size());
        });

    }

    @Test
    void getLastCustomerId() throws SQLException {
        String lastCustomerId = CustomerDataAccess.getLastCustomerId();
        if(CustomerDataAccess.getAllCustomer().isEmpty()){
            assertNull(lastCustomerId);
        }else {
            CustomerDataAccess.saveCustomer(new Customer("ABC", "Kasun Sampath", "galle"));
            lastCustomerId = CustomerDataAccess.getLastCustomerId();
            assertNotNull(lastCustomerId);
        }
    }
}