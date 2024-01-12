package lk.ijse.dep11.pos.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonConnectionDataSourceTest {

    @Test
    void getInstance() {
        SingletonConnectionDataSource instance1 = SingletonConnectionDataSource.getInstance();
        SingletonConnectionDataSource instance2 = SingletonConnectionDataSource.getInstance();
        SingletonConnectionDataSource instance3 = SingletonConnectionDataSource.getInstance();
        assertEquals(instance1, instance2);
        assertEquals(instance2, instance3);
    }

    @Test
    void getConnection() {
        assertDoesNotThrow(SingletonConnectionDataSource.getInstance()::getConnection);
    }

    @Test
    void generateSchema() {
        assertDoesNotThrow(() ->{
            SingletonConnectionDataSource.getInstance().getConnection().createStatement()
                    .executeQuery("SELECT * FROM customer, item, \"order\", order_item");
        });
    }
}