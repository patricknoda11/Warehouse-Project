package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private static final String WAREHOUSE_TEST_NAME = "Test Warehouse";
    private static final String TEST_CUSTOMER_NAME_ONE = "NDS";
    private static final String TEST_CUSTOMER_NAME_TWO = "The Company";
    // Test Order values:
    private static final String TEST_ORDER_CONTENT_ONE = "Content1";
    private static final String TEST_ORDER_CONTENT_TWO = "Content2";
    private static final LocalDate TEST_ORDER_IMPORT_DATE_ONE = LocalDate.of(2021, 1 ,21);
    private static final LocalDate TEST_ORDER_IMPORT_DATE_TWO = LocalDate.of(2021, 2 ,21);
    private static final String TEST_ORDER_INVOICE_NUMBER_ONE = "Invoice Number1";
    private static final String TEST_ORDER_INVOICE_NUMBER_TWO = "Invoice Number2";
    private static final int TEST_ORDER_QUANTITY_ONE = 50;
    private static final int TEST_ORDER_QUANTITY_TWO = 100;
    private static final String TEST_ORDER_STORAGE_LOCATION_ONE = "Test Location1";
    private static final String TEST_ORDER_STORAGE_LOCATION_TWO = "Test Location2";
    private static final String TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE = "EXPORT Invoice Number1";
    private static final String TEST_ORDER_EXPORT_INVOICE_NUMBER_TWO = "EXPORT Invoice Number2";

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
    public void testAddCustomerOneCustomer() {
        Customer referenceCustomer = new Customer(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            // pass
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            fail("This should not run");
        }

        // add customer should add 1 new customer with the given customer name
        Set<Customer> testWarehouseCustomerSet = this.testWarehouse.getCustomerSet();
        assertEquals(1, testWarehouseCustomerSet.size());
        assertTrue(testWarehouseCustomerSet.contains(referenceCustomer));
    }

    @Test
    public void testAddCustomerMultipleCustomers() {
        // Customer object equivalence dependant on name field --> use reference customers to check addCustomer
        Customer referenceCustomerOne = new Customer(TEST_CUSTOMER_NAME_ONE);
        Customer referenceCustomerTwo = new Customer(TEST_CUSTOMER_NAME_TWO);

        try {
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_TWO);
            // pass
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            fail("This should not run");
        }

        // should contain 2 customers with given names
        Set<Customer> testWarehouseCustomerSet = this.testWarehouse.getCustomerSet();
        assertEquals(2, testWarehouseCustomerSet.size());
        assertTrue(testWarehouseCustomerSet.contains(referenceCustomerOne));
        assertTrue(testWarehouseCustomerSet.contains(referenceCustomerTwo));
    }

    @Test
    public void testAddCustomerCustomerAlreadyExistsException() {
        // Customer object equivalence dependant on name field --> use reference customers to check addCustomer
        Customer referenceCustomerOne = new Customer(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            fail("This should not run");
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            // pass
        }

        // only one customer with the given name should be added
        Set<Customer> testWarehouseCustomerSet = this.testWarehouse.getCustomerSet();
        assertEquals(1, testWarehouseCustomerSet.size());
        assertTrue(testWarehouseCustomerSet.contains(referenceCustomerOne));
    }

    // MODIFIES: this
    // EFFECTS: a helper that adds a customer to warehouse
    public void addCustomerToWarehouse(String customerName) {
        try {
            this.testWarehouse.addCustomer(TEST_CUSTOMER_NAME_ONE);
            // pass
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportProductOnce() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);

            // confirm that an order of given detail was added to the correct customer:
            Customer customer = this.testWarehouse.getCustomerSet().stream().iterator().next();
            assertEquals(1, customer.getActiveOrderSize());
            assertEquals(new Order(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE),
                    customer.getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE));
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderAlreadyExistsException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportProductMultipleTimes() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO);

            // confirm that an order of given detail was added to the correct customer:
            Customer customer = this.testWarehouse.getCustomerSet().stream().iterator().next();
            assertEquals(2, customer.getActiveOrderSize());
            assertEquals(new Order(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                            TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE),
                    customer.getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE));
            assertEquals(new Order(TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                            TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO),
                    customer.getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_TWO));
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderAlreadyExistsException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportProductCustomerDoesNotExist() {
        try {
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            fail("This should not run");
        } catch (CustomerDoesNotExistException e) {
            // pass
        } catch (OrderAlreadyExistsException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportProductOrderAlreadyExists() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testWarehouse.importProduct(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            fail("This should not run");
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderAlreadyExistsException e) {
            // pass
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    // MODIFIES: this
    // EFFECTS: a helper that adds Orders to warehouse
    public void addOrdersToWarehouse(String customerName) {
        try {
            this.testWarehouse.importProduct(customerName, TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testWarehouse.importProduct(customerName, TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO);
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderAlreadyExistsException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testExportOrderPartialRemoval() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);
        addOrdersToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.exportOrder(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE - 10, TEST_ORDER_IMPORT_DATE_ONE.plusDays(10),
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        } catch (ParseException e) {
            fail("This should not run");
        }

        // confirm changes
        Customer customer = this.testWarehouse.getCustomerSet().stream().iterator().next();
        assertEquals(2, customer.getActiveOrderSize());
        assertEquals(1, customer.getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE).getExports().size());
    }

    @Test
    public void testExportOrderMultipleTimes() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);
        addOrdersToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.exportOrder(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE - 40, TEST_ORDER_IMPORT_DATE_ONE.plusDays(10),
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);
            this.testWarehouse.exportOrder(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE - 40, TEST_ORDER_IMPORT_DATE_ONE.plusDays(11),
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_TWO);
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        } catch (ParseException e) {
            fail("This should not run");
        }

        // confirm changes
        Customer customer = this.testWarehouse.getCustomerSet().stream().iterator().next();
        assertEquals(2, customer.getActiveOrderSize());
        assertEquals(2, customer.getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE).getExports().size());
    }

    @Test
    public void testExportOrderCustomerDoesNotExist() {
        try {
            this.testWarehouse.exportOrder(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE - 40, TEST_ORDER_IMPORT_DATE_ONE.plusDays(10),
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);
            fail("This should not run");
        } catch (CustomerDoesNotExistException e) {
            // pass
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        } catch (ParseException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testRecordMonthlyChargeOnce() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);
        addOrdersToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.recordMonthlyCharge(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE, TEST_ORDER_IMPORT_DATE_ONE.plusDays(30), TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);

            // confirm monthly charge label correctly added to order
            Order order = this.testWarehouse.getCustomerSet().stream().iterator().next().
                    getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE);
            assertEquals(1, order.getMonthlyChargeLabels().size());
            assertTrue(order.getMonthlyChargeLabels().contains(new MonthlyChargeLabel(TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(30))));
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testRecordMonthlyChargeMultipleTimes() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);
        addOrdersToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.recordMonthlyCharge(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE, TEST_ORDER_IMPORT_DATE_ONE.plusDays(30), TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);
            this.testWarehouse.recordMonthlyCharge(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_IMPORT_DATE_TWO, TEST_ORDER_IMPORT_DATE_TWO.plusDays(30), TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_TWO);

            // confirm monthly charge label correctly added to order
            Order order = this.testWarehouse.getCustomerSet().stream().iterator().next().
                    getActiveOrders().get(TEST_ORDER_INVOICE_NUMBER_ONE);
            assertEquals(2, order.getMonthlyChargeLabels().size());
            assertTrue(order.getMonthlyChargeLabels().contains(new MonthlyChargeLabel(TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_IMPORT_DATE_TWO.plusDays(30))));
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testRecordMonthlyChargeCustomerDoesNotExist() {
        try {
            this.testWarehouse.recordMonthlyCharge(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE, TEST_ORDER_IMPORT_DATE_ONE.plusDays(30), TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_EXPORT_INVOICE_NUMBER_ONE);
            fail("This should not run");
        } catch (CustomerDoesNotExistException e) {
            // pass
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testDeleteCustomer() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.deleteCustomer(TEST_CUSTOMER_NAME_ONE);
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        }

        assertEquals(0, this.testWarehouse.getCustomerSet().size());
    }

    @Test
    public void testDeleteCustomerCustomerDoesNotExist() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.deleteCustomer(TEST_CUSTOMER_NAME_TWO);
            fail("This should not run");
        } catch (CustomerDoesNotExistException e) {
            // pass
        }

        assertEquals(1, this.testWarehouse.getCustomerSet().size());
    }

    @Test
    public void testDeleteCustomerOrder() {
        // setup
        addCustomerToWarehouse(TEST_CUSTOMER_NAME_ONE);
        addOrdersToWarehouse(TEST_CUSTOMER_NAME_ONE);

        try {
            this.testWarehouse.deleteCustomerOrder(TEST_CUSTOMER_NAME_ONE, TEST_ORDER_INVOICE_NUMBER_ONE);
        } catch (CustomerDoesNotExistException e) {
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        }


        // confirm deletion of order from customer
        Customer customer = this.testWarehouse.getCustomerSet().stream().iterator().next();
        assertEquals(1, customer.getActiveOrderSize());
        assertFalse(customer.getActiveOrders().containsKey(TEST_ORDER_INVOICE_NUMBER_ONE));
    }
}
