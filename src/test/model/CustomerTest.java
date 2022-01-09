package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Customer tests
 */
public class CustomerTest {
    // Test Customer initial values:
    private static final String TEST_CUSTOMER_NAME = "TEST";
    // Test Order fields/values:
    private static final String TEST_ORDER_CONTENT_ONE = "Content1";
    private static final String TEST_ORDER_INVOICE_NUMBER_ONE = "123456-a";
    private static final String TEST_ORDER_STORAGE_LOCATION_ONE = "AL Warehouse1";
    private static final LocalDate TEST_ORDER_IMPORT_DATE_ONE = LocalDate.of(2021, 1, 21);
    private static final int TEST_ORDER_QUANTITY_ONE = 50;
    private static final String TEST_ORDER_CONTENT_TWO = "Content2";
    private static final String TEST_ORDER_INVOICE_NUMBER_TWO = "123456-b";
    private static final String TEST_ORDER_STORAGE_LOCATION_TWO = "AL Warehouse2";
    private static final LocalDate TEST_ORDER_IMPORT_DATE_TWO = LocalDate.of(2021, 2, 22);
    private static final int TEST_ORDER_QUANTITY_TWO = 100;
    // Additional test values:
    private static final int TEST_QUANTITY_NEGATIVE = -10;
    private static final int TEST_QUANTITY_ZERO = 0;
    private static final LocalDate TEST_DATE_INVALID_IMPORT_DATE = LocalDate.now().plusDays(5);
    private static final LocalDate TEST_DATE_ONE = LocalDate.of(2021, 2,21);
    private static final LocalDate TEST_DATE_TWO = LocalDate.of(2021, 3,22);
    private static final LocalDate TEST_DATE_THREE = LocalDate.of(2021, 3,23);
    private static final String TEST_INVALID_INVOICE_NUMBER = "abcdefg";
    private static final String TEST_INVOICE_NUMBER_ONE = "222222";
    private static final String TEST_INVOICE_NUMBER_TWO = "111111";


    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        this.testCustomer = new Customer(TEST_CUSTOMER_NAME);
    }

    @Test
    public void testCustomerConstructor() {
        assertEquals(TEST_CUSTOMER_NAME, this.testCustomer.getName());
        assertEquals(0, this.testCustomer.getActiveOrders().size());
        assertEquals(0, this.testCustomer.getCompleteOrders().size());
    }

    @Test
    public void testImportOrderOneOrder() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);

            // confirm that Order was correctly added into activeOrders with the right key (Invoice Number)
            Map<String, Order> activeOrders = this.testCustomer.getActiveOrders();
            assertEquals(1, activeOrders.size());
            assertEquals(new Order(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE), activeOrders.get(TEST_ORDER_INVOICE_NUMBER_ONE));
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportOrderMultipleOrders() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO);

            // confirm that Order was correctly added into activeOrders with the right key (Invoice Number)
            Map<String, Order> activeOrders = this.testCustomer.getActiveOrders();
            assertEquals(2, activeOrders.size());
            assertEquals(new Order(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE, TEST_ORDER_INVOICE_NUMBER_ONE,
                    TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE),
                    activeOrders.get(TEST_ORDER_INVOICE_NUMBER_ONE));
            assertEquals(new Order(TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                            TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO),
                    activeOrders.get(TEST_ORDER_INVOICE_NUMBER_TWO));
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testImportOrderNegativeQuantity() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_QUANTITY_NEGATIVE, TEST_ORDER_STORAGE_LOCATION_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            // pass
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }

        // confirm that no changes were made
        assertEquals(0, this.testCustomer.getActiveOrders().size());
    }

    @Test
    public void testImportOrderQuantityZero() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_QUANTITY_ZERO, TEST_ORDER_STORAGE_LOCATION_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            // pass
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }

        // confirm that no changes were made
        assertEquals(0, this.testCustomer.getActiveOrders().size());
    }

    @Test
    public void testImportOrderInvalidImportDate() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_DATE_INVALID_IMPORT_DATE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            // pass
        }

        // confirm that no changes were made
        assertEquals(0, this.testCustomer.getActiveOrders().size());
    }

    // MODIFIES: this
    // EFFECTS: helper that sets up test customer for removal
    private void addOrdersToCustomer() {
        try {
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE, TEST_ORDER_STORAGE_LOCATION_ONE);
            this.testCustomer.importOrder(TEST_ORDER_CONTENT_TWO, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_ORDER_INVOICE_NUMBER_TWO, TEST_ORDER_QUANTITY_TWO, TEST_ORDER_STORAGE_LOCATION_TWO);

        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testRemoveFromOrderOnePartialRemoval() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.removeFromOrder(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE - 30,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(10), TEST_INVOICE_NUMBER_ONE);

            // confirm that correct changes were made to the right Order
            Map<String, Order> activeOrders = this.testCustomer.getActiveOrders();
            assertEquals(TEST_ORDER_QUANTITY_ONE - (TEST_ORDER_QUANTITY_ONE - 30),
                    activeOrders.get(TEST_ORDER_INVOICE_NUMBER_ONE).getCurrentQuantity());
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
        }
    }


    @Test
    public void testRemoveFromOrderMultiplePartialRemoval() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.removeFromOrder(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE - 30,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(10), TEST_INVOICE_NUMBER_ONE);
            this.testCustomer.removeFromOrder(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE - 40,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(11), TEST_INVOICE_NUMBER_TWO);

            // confirm that correct changes were made to the right Order
            Map<String, Order> activeOrders = this.testCustomer.getActiveOrders();
            assertEquals(TEST_ORDER_QUANTITY_ONE
                            - (TEST_ORDER_QUANTITY_ONE - 30) - (TEST_ORDER_QUANTITY_ONE - 40),
                    activeOrders.get(TEST_ORDER_INVOICE_NUMBER_ONE).getCurrentQuantity());
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
        }
    }

    @Test
    public void testRemoveFromOrderCompleteRemoval() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.removeFromOrder(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(10), TEST_INVOICE_NUMBER_ONE);

            // confirm that correct changes were made to the right Order
            Map<String, Order> activeOrders = this.testCustomer.getActiveOrders();
            assertEquals(1, this.testCustomer.getActiveOrderSize());
            assertEquals(1, this.testCustomer.getCompleteOrderSize());
            assertEquals(TEST_ORDER_INVOICE_NUMBER_ONE,
                    this.testCustomer.getCompleteOrders().get(0).getInvoiceNumber());
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
        }
    }

    @Test
    public void testRemoveFromOrderOrderDoesNotExist() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.removeFromOrder(TEST_INVALID_INVOICE_NUMBER, TEST_ORDER_QUANTITY_ONE,
                    TEST_ORDER_IMPORT_DATE_ONE.plusDays(10), TEST_INVOICE_NUMBER_ONE);
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            // pass
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
        }

        // check that no changes were made
        assertEquals(2, this.testCustomer.getActiveOrderSize());
        assertEquals(0, this.testCustomer.getCompleteOrderSize());
    }

    @Test
    public void testDeleteOrderOnce() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.deleteOrder(TEST_ORDER_INVOICE_NUMBER_ONE);
        } catch (QuantityNegativeException e){
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        }

        // check that one Order has been removed from Customer
        assertEquals(1, this.testCustomer.getActiveOrderSize());
        assertEquals(0, this.testCustomer.getCompleteOrderSize());
    }

    @Test
    public void testDeleteOrderMultipleTimes() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.deleteOrder(TEST_ORDER_INVOICE_NUMBER_ONE);
            this.testCustomer.deleteOrder(TEST_ORDER_INVOICE_NUMBER_TWO);
        } catch (QuantityNegativeException e){
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            fail("This should not run");
        }

        // check that one Order has been removed from Customer
        assertEquals(0, this.testCustomer.getActiveOrderSize());
        assertEquals(0, this.testCustomer.getCompleteOrderSize());
    }

    @Test
    public void testRecordMonthlyChargeOnce() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.recordMonthlyCharge(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_DATE_ONE, TEST_ORDER_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE);

            // confirm monthly charge label correctly added
            List<Label> monthlyChargeLabels = this.testCustomer.getActiveOrders().
                    get(TEST_ORDER_INVOICE_NUMBER_ONE).getMonthlyChargeLabels();
            assertEquals(1, monthlyChargeLabels.size());
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_ORDER_QUANTITY_ONE,
                    TEST_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_ONE,TEST_DATE_ONE)));
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
        addOrdersToCustomer();

        try {
            this.testCustomer.recordMonthlyCharge(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_DATE_ONE, TEST_ORDER_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE);
            this.testCustomer.recordMonthlyCharge(TEST_ORDER_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_TWO,
                    TEST_DATE_TWO, TEST_ORDER_QUANTITY_ONE, TEST_INVOICE_NUMBER_TWO);

            // confirm monthly charge label correctly added
            List<Label> monthlyChargeLabels = this.testCustomer.getActiveOrders().
                    get(TEST_ORDER_INVOICE_NUMBER_ONE).getMonthlyChargeLabels();
            assertEquals(2, monthlyChargeLabels.size());
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_ORDER_QUANTITY_ONE,
                    TEST_INVOICE_NUMBER_ONE, TEST_ORDER_IMPORT_DATE_ONE,TEST_DATE_ONE)));
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_ORDER_QUANTITY_ONE,
                    TEST_INVOICE_NUMBER_TWO, TEST_ORDER_IMPORT_DATE_TWO,TEST_DATE_TWO)));
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
    public void testRecordMonthlyChargeOrderDoesNotExist() {
        // setup
        addOrdersToCustomer();

        try {
            this.testCustomer.recordMonthlyCharge(TEST_INVALID_INVOICE_NUMBER, TEST_ORDER_IMPORT_DATE_ONE,
                    TEST_DATE_ONE, TEST_ORDER_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE);
            fail("This should not run");
        } catch (OrderDoesNotExistException e) {
            // pass
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
}
