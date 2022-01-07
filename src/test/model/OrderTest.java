package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Order tests
 */
public class OrderTest {
    // Test Order fields/values:
    private static final String TEST_ORDER_CONTENT = "Content";
    private static final String TEST_ORDER_INVOICE_NUMBER = "123456-a";
    private static final String TEST_ORDER_STORAGE_LOCATION = "AL Warehouse";
    private static final LocalDate TEST_ORDER_IMPORT_DATE = LocalDate.of(2021, 1, 21);
    private static final int TEST_ORDER_QUANTITY = 50;
    // Test Values below:
    private static final int TEST_QUANTITY_ONE = 10;
    private static final int TEST_QUANTITY_TWO = 20;
    private static final int TEST_QUANTITY_THREE = 100;
    private static final int TEST_QUANTITY_NEGATIVE = -10;
    private static final int TEST_QUANTITY_ZERO = 0;
    private static final String TEST_INVOICE_NUMBER_ONE = "111111";
    private static final String TEST_INVOICE_NUMBER_TWO = "222222";
    private static final LocalDate TEST_DATE_ONE = LocalDate.of(2021, 3, 21);
    private static final LocalDate TEST_DATE_TWO = LocalDate.of(2021, 4, 21);
    private static final LocalDate TEST_DATE_THREE = LocalDate.of(2021, 8, 21);
    private static final LocalDate TEST_DATE_FOUR = LocalDate.of(2021, 9, 21);
    private static final LocalDate TEST_DATE_FIVE = LocalDate.of(2015, 3, 21);
    private static final LocalDate TEST_DATE_SIX = LocalDate.of(2015, 4, 21);
    private static final LocalDate TEST_DATE_SEVEN = LocalDate.now().plusDays(5);
    private static final LocalDate TEST_DATE_EIGHT = LocalDate.now().plusDays(35);
    private static final LocalDate TEST_DATE_NINE = LocalDate.of(2021, 8, 25);

    private Order testOrder;

    @BeforeEach
    public void setUp() {
        try {
            this.testOrder = new Order(TEST_ORDER_CONTENT, TEST_ORDER_IMPORT_DATE,
                    TEST_ORDER_INVOICE_NUMBER, TEST_ORDER_QUANTITY, TEST_ORDER_STORAGE_LOCATION);
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testOrderConstructor() {
        assertEquals(TEST_ORDER_CONTENT, this.testOrder.getContent());
        assertEquals(TEST_ORDER_INVOICE_NUMBER, this.testOrder.getInvoiceNumber());
        assertEquals(TEST_ORDER_STORAGE_LOCATION, this.testOrder.getStorageLocation());
        assertEquals(TEST_ORDER_IMPORT_DATE, this.testOrder.getImportDate());
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getOriginalQuantity());
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        assertEquals(0, this.testOrder.getExports().size());
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testOrderConstructorQuantityNegative() {
        try {
            this.testOrder = new Order(TEST_ORDER_CONTENT, TEST_ORDER_IMPORT_DATE,
                    TEST_ORDER_INVOICE_NUMBER, TEST_QUANTITY_NEGATIVE, TEST_ORDER_STORAGE_LOCATION);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            // pass
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testOrderConstructorQuantityZero() {
        try {
            this.testOrder = new Order(TEST_ORDER_CONTENT, TEST_ORDER_IMPORT_DATE,
                    TEST_ORDER_INVOICE_NUMBER, TEST_QUANTITY_ZERO, TEST_ORDER_STORAGE_LOCATION);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            // pass
        } catch (InvalidImportDateException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testOrderConstructorInvalidImportDate() {
        try {
            this.testOrder = new Order(TEST_ORDER_CONTENT, TEST_DATE_SEVEN,
                    TEST_ORDER_INVOICE_NUMBER, TEST_ORDER_QUANTITY, TEST_ORDER_STORAGE_LOCATION);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (InvalidImportDateException e) {
            // pass
        }
    }

    @Test
    public void testRemoveOnce() {
        try {
            this.testOrder.remove(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE);
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

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY - TEST_QUANTITY_ONE, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(1, exports.size());
        assertTrue(exports.contains(new ExportLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE)));
    }

    @Test
    public void testRemoveMultipleRemovals() {
        try {
            this.testOrder.remove(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE);
            this.testOrder.remove(TEST_QUANTITY_TWO, TEST_INVOICE_NUMBER_TWO, TEST_DATE_TWO);
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

        List<ExportLabel> exports = this.testOrder.getExports();;
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY - TEST_QUANTITY_ONE - TEST_QUANTITY_TWO,
                this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(2, exports.size());
        assertTrue(exports.contains(new ExportLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE)));
        assertTrue(exports.contains(new ExportLabel(TEST_QUANTITY_TWO, TEST_INVOICE_NUMBER_TWO, TEST_DATE_TWO)));
    }

    @Test
    public void testRemoveNegativeRemovalQuantity() {
        try {
            this.testOrder.remove(TEST_QUANTITY_NEGATIVE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            // pass
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        }

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(0, exports.size());
    }

    @Test
    public void testRemoveRemovalQuantityZero() {
        try {
            this.testOrder.remove(TEST_QUANTITY_ZERO, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            // pass
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        }

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(0, exports.size());
    }

    @Test
    public void testRemoveRemovalQuantityExceedsAvailability() {
        try {
            this.testOrder.remove(TEST_QUANTITY_THREE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_ONE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            // pass
        } catch (RemovalQuantityExceedsAvailabilityException e) {
            fail("This should not run");
        } catch (InvalidExportDateException e) {
            fail("This should not run");
        }

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(0, exports.size());
    }


    @Test
    public void testRemoveRemovalDatePriorToImportDate() {
        try {
            this.testOrder.remove(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_FIVE);
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
            // pass
        }

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(0, exports.size());
    }


    @Test
    public void testRemoveFutureRemovalDate() {
        try {
            this.testOrder.remove(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE, TEST_DATE_SEVEN);
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
            // pass
        }

        List<ExportLabel> exports = this.testOrder.getExports();
        // confirm correct quantity remains
        assertEquals(TEST_ORDER_QUANTITY, this.testOrder.getCurrentQuantity());
        // confirm export label has been correctly generated and added for removal event
        assertEquals(0, exports.size());
    }

    @Test
    public void testAddMonthlyChargeLabelOnce() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO);

            List<MonthlyChargeLabel> monthlyChargeLabels = this.testOrder.getMonthlyChargeLabels();
            // confirm that correct number of monthly charge label(s) with the correct details have been added
            assertEquals(1, monthlyChargeLabels.size());
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO)));
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        }  catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }
    }

    @Test
    public void testAddMonthlyChargeLabelMultipleTimes() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO);
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_TWO, TEST_INVOICE_NUMBER_TWO,
                    TEST_DATE_THREE, TEST_DATE_FOUR);

            List<MonthlyChargeLabel> monthlyChargeLabels = this.testOrder.getMonthlyChargeLabels();
            // confirm that correct number of monthly charge label(s) with the correct details have been added
            assertEquals(2, monthlyChargeLabels.size());
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO)));
            assertTrue(monthlyChargeLabels.contains(new MonthlyChargeLabel(TEST_QUANTITY_TWO, TEST_INVOICE_NUMBER_TWO,
                    TEST_DATE_THREE, TEST_DATE_FOUR)));
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
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
    public void testAddMonthlyChargeLabelNegativeQuantity() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_NEGATIVE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            // pass
        } catch (QuantityZeroException e) {
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

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testAddMonthlyChargeLabelZeroQuantity() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ZERO, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            // pass
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testAddMonthlyChargeLabelQuantityExceedsOriginalQuantity() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_THREE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_ONE, TEST_DATE_TWO);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            // pass
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testAddMonthlyChargeLabelInvalidStartDate() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_FIVE, TEST_DATE_SIX);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            // pass
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testAddMonthlyChargeLabelInvalidEndDate() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_SEVEN, TEST_DATE_EIGHT);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            // pass
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }

    @Test
    public void testAddMonthlyChargeLabelInvalidStartEndDateRange() {
        try {
            this.testOrder.addMonthlyChargeLabel(TEST_QUANTITY_ONE, TEST_INVOICE_NUMBER_ONE,
                    TEST_DATE_THREE, TEST_DATE_NINE);
            fail("This should not run");
        } catch (QuantityNegativeException e) {
            fail("This should not run");
        } catch (QuantityZeroException e) {
            fail("This should not run");
        } catch (QuantityExceedsMaxQuantityException e) {
            fail("This should not run");
        } catch (InvalidStartDateException e) {
            fail("This should not run");
        } catch (InvalidEndDateException e) {
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            // pass
        }

        // confirm that no monthly charge label has been added
        assertEquals(0, this.testOrder.getMonthlyChargeLabels().size());
    }
}




