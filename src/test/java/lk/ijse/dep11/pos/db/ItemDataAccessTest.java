package lk.ijse.dep11.pos.db;

import lk.ijse.dep11.pos.tm.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ItemDataAccessTest {

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
    void getAllItems() throws SQLException {
        ItemDataAccess.saveItem(new Item("1234", "keyboard", 10, new BigDecimal("1250.00")));
        ItemDataAccess.saveItem(new Item("5678", "mouse", 5, new BigDecimal("1250.00")));
        assertTrue(ItemDataAccess.getAllItems().size() >= 2);
    }

    @Test
    void saveItem() throws SQLException {
        assertDoesNotThrow(()->{
            ItemDataAccess.saveItem(new Item("123456", "Keyboard", 10, new BigDecimal("1250.00")));
            ItemDataAccess.saveItem(new Item("456789", "Mouse", 10, new BigDecimal("1250.00")));
        });
        assertThrows(SQLException.class, ()->
                ItemDataAccess.saveItem(new Item("123456", "Keyboard", 10, new BigDecimal("1250.00"))));
    }

    @Test
    void updateItem() throws SQLException {
        ItemDataAccess.saveItem(new Item("123456", "Keyboard", 10, new BigDecimal("1250.00")));
        assertDoesNotThrow(()-> ItemDataAccess.updateItem(new Item("123456", "HP keybord", 10, new BigDecimal("1250.00"))));
    }

    @Test
    void deleteItem() throws SQLException {
        ItemDataAccess.saveItem(new Item("123456", "Keyboard", 10, new BigDecimal("1250.00")));
        int count = ItemDataAccess.getAllItems().size();
        assertDoesNotThrow(()-> ItemDataAccess.deleteItem("123456"));
        assertEquals(count - 1, ItemDataAccess.getAllItems().size());

    }


    @Test
    void existsItem() throws SQLException {
        assertDoesNotThrow(()-> assertFalse(ItemDataAccess.existsItem("Crazy Item")));
        ItemDataAccess.saveItem(new Item("123456", "Keyboard", 10, new BigDecimal("1250.00")));
        assertTrue(ItemDataAccess.existsItem("123456"));
    }
}