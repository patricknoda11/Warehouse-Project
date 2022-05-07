package persistence;

import model.Customer;
import model.Order;
import model.Warehouse;
import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String TEST_CUSTOMER_NAME_ONE = "test customer 1";
    private static final String TEST_CUSTOMER_NAME_TWO = "test customer 2";
    private static final String TEST_ORDER_CONTENT_ONE = "test product 1";
    private static final LocalDate TEST_ORDER_IMPORT_DATE_ONE = LocalDate.of(2022, 1,1);
    private static final String TEST_ORDER_INVOICE_NUMBER_ONE = "111111";
    private static final int TEST_ORDER_QUANTITY_ONE = 20;
    private static final String TEST_ORDER_STORAGE_LOCATION_ONE = "test location 1";
    private static final String TEST_ORDER_CONTENT_TWO = "test product 2";
    private static final LocalDate TEST_ORDER_IMPORT_DATE_TWO = LocalDate.of(2021, 2,2);
    private static final String TEST_ORDER_INVOICE_NUMBER_TWO = "222222";
    private static final int TEST_ORDER_QUANTITY_TWO = 40;
    private static final String TEST_ORDER_STORAGE_LOCATION_TWO= "test location 2";

    private static final File TEST_FILE_EMPTY_INVENTORY = new File("./data/testJsonWriterEmptyInventory.json");
    private static final File TEST_FILE_ONE_ORDER = new File("./data/testJsonWriterOneOrder.json");
    private static final File TEST_FILE_MULTIPLE_ORDER = new File("./data/testJsonWriterMultipleOrders.json");

    private Warehouse testWarehouse;

    @BeforeEach
    public void setUp() {
        this.testWarehouse = new Warehouse();
    }

    @Test
    public void testWarehouseConstructor() {
        assertEquals(0, this.testWarehouse.getCustomerSet().size());
    }

    @Test
    public void testSaveToFileMultipleCustomers() {
        // clear previous data that was written to file
        TEST_FILE_EMPTY_INVENTORY.delete();

        try {
            // add customers to warehouse
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_TWO);

            // write to file warehouse state
            JsonWriter jsonWriter = new JsonWriter(TEST_FILE_EMPTY_INVENTORY, this.testWarehouse.convertToJsonObject());
            jsonWriter.saveToFile();

            // confirm changes
            this.testWarehouse = new Warehouse();
            JsonReader jsonReader = new JsonReader(TEST_FILE_EMPTY_INVENTORY);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Set<Customer> customerSet = this.testWarehouse.getCustomerSet();
            assertEquals(2, customerSet.size());
            assertTrue(customerSet.contains(new Customer(TEST_CUSTOMER_NAME_ONE)));
            assertTrue(customerSet.contains(new Customer(TEST_CUSTOMER_NAME_TWO)));
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            fail("This should not run");
        } catch (FileNotFoundException e) {
            fail("This should not run");
        } catch (IOException e) {
            fail("This should not run");
        } catch (CorruptFileException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testSaveToFileOneOrder() {
        // clear previous data that was written to file
        TEST_FILE_ONE_ORDER.delete();

        try {
            // add customers to warehouse
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);

            // write to file warehouse state
            JsonWriter jsonWriter = new JsonWriter(TEST_FILE_ONE_ORDER, this.testWarehouse.convertToJsonObject());
            jsonWriter.saveToFile();

            // confirm changes
            this.testWarehouse = new Warehouse();
            JsonReader jsonReader = new JsonReader(TEST_FILE_ONE_ORDER);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Customer customerOne = this.testWarehouse.getCustomerSet().iterator().next();
            assertEquals(1, customerOne.getActiveOrderSize());
            assertEquals(0, customerOne.getCompleteOrderSize());
            assertTrue(customerOne.getActiveOrders().containsKey(TEST_ORDER_INVOICE_NUMBER_ONE));
        } catch (CustomerAlreadyExistsException | IOException | CustomerDoesNotExistException
                | OrderAlreadyExistsException | QuantityNegativeException | QuantityZeroException
                | InvalidImportDateException | CorruptFileException | InvalidCustomerNameException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testSaveToFileMultipleOrders() {
        // clear previous data that was written to file
        TEST_FILE_MULTIPLE_ORDER.delete();

        try {
            // add customers to warehouse (setup)
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO);

            // write to file warehouse state
            JsonWriter jsonWriter = new JsonWriter(TEST_FILE_MULTIPLE_ORDER, this.testWarehouse.convertToJsonObject());
            jsonWriter.saveToFile();

            // confirm changes
            this.testWarehouse = new Warehouse();
            JsonReader jsonReader = new JsonReader(TEST_FILE_MULTIPLE_ORDER);
            this.testWarehouse.convertJsonObjectToWarehouse(jsonReader.getJsonRepresentation());
            Customer customerOne = this.testWarehouse.getCustomerSet().iterator().next();
            Map<String, Order> activeOrder = customerOne.getActiveOrders();
            assertEquals(2, customerOne.getActiveOrderSize());
            assertEquals(0, customerOne.getCompleteOrderSize());
            assertTrue(activeOrder.containsKey(TEST_ORDER_INVOICE_NUMBER_ONE));
            assertTrue(activeOrder.containsKey(TEST_ORDER_INVOICE_NUMBER_TWO));
        } catch (CustomerAlreadyExistsException | IOException | CustomerDoesNotExistException
                | OrderAlreadyExistsException | QuantityNegativeException | QuantityZeroException
                | InvalidImportDateException | CorruptFileException | InvalidCustomerNameException e) {
            fail("This should not run");
        }
    }
}
