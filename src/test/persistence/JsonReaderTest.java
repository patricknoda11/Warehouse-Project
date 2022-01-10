package persistence;

import model.Customer;
import model.Order;
import model.Warehouse;
import model.exceptions.CorruptFileException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonReaderTest {
    private static final String TEST_CUSTOMER_NAME_ONE = "test customer 1";
    private static final String TEST_CUSTOMER_NAME_TWO = "test customer 2";
    private static final String TEST_ORDER_INVOICE_NUMBER_ONE = "111111";
    private static final String TEST_ORDER_INVOICE_NUMBER_TWO = "222222";

    private static final File TEST_FILE_EMPTY_INVENTORY = new File("./data/testJsonReaderEmptyInventory.json");
    private static final File TEST_FILE_ONE_ORDER = new File("./data/testJsonReaderOneOrder.json");
    private static final File TEST_FILE_MULTIPLE_ORDER = new File("./data/testJsonReaderMultipleOrders.json");

    private Warehouse testWarehouse;

    @BeforeEach
    public void setUp() {
        this.testWarehouse = new Warehouse();
    }

    @Test
    public void testGetJsonRepresentationNonExistentFile() {
        try {
            JsonReader testJsonReader = new JsonReader(new File("./data/NoSuchFileExist"));
            JSONObject jsonObject = testJsonReader.getJsonRepresentation();
            this.testWarehouse.convertJsonObjectToWarehouse(jsonObject);
            fail("This should not run");
        } catch (IOException | CorruptFileException e) {
            // test passes if exception is thrown"
        }
    }

    @Test
    public void testGetJsonRepresentationEmptyInventory() {
        try {
            JsonReader jsonReader = new JsonReader(TEST_FILE_EMPTY_INVENTORY);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Set<Customer> customerSet = this.testWarehouse.getCustomerSet();
            assertEquals(2, customerSet.size());
            assertTrue(customerSet.contains(new Customer(TEST_CUSTOMER_NAME_ONE)));
            assertTrue(customerSet.contains(new Customer(TEST_CUSTOMER_NAME_TWO)));
        } catch (IOException | CorruptFileException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testGetJsonRepresentationOneOrder() {
        try {
            JsonReader jsonReader = new JsonReader(TEST_FILE_ONE_ORDER);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Customer customerOne = this.testWarehouse.getCustomerSet().iterator().next();
            assertEquals(1, customerOne.getActiveOrderSize());
            assertEquals(0, customerOne.getCompleteOrderSize());
            assertTrue(customerOne.getActiveOrders().containsKey(TEST_ORDER_INVOICE_NUMBER_ONE));
        } catch (IOException | CorruptFileException e) {
            fail("This should not run");
        }
    }


    @Test
    public void testGetJsonRepresentationMultipleOrders() {
        try {
            JsonReader jsonReader = new JsonReader(TEST_FILE_MULTIPLE_ORDER);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Customer customerOne = this.testWarehouse.getCustomerSet().iterator().next();
            Map<String, Order> activeOrder = customerOne.getActiveOrders();
            assertEquals(2, customerOne.getActiveOrderSize());
            assertEquals(0, customerOne.getCompleteOrderSize());
            assertTrue(activeOrder.containsKey(TEST_ORDER_INVOICE_NUMBER_ONE));
            assertTrue(activeOrder.containsKey(TEST_ORDER_INVOICE_NUMBER_TWO));
        } catch (IOException | CorruptFileException e) {
            fail("This should not run");
        }
    }
}
