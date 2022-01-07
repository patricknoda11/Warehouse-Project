package model;

import model.exceptions.InvalidMonthRangeException;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * MonthlyChargeLabel tests
 */
public class MonthlyChargeLabelTest {
    private static final int TEST_MONTHLY_CHARGE_QUANTITY = 10;
    private static final String TEST_MONTHLY_CHARGE_INVOICE_NUMBER = "000000";
    private static final LocalDate TEST_MONTHLY_CHARGE_INITIAL_DATE = LocalDate.of(2021, 12, 25);
    private static final LocalDate TEST_MONTHLY_CHARGE_END_DATE = LocalDate.of(2022,1,25);
    private static final LocalDate TEST_MONTHLY_CHARGE_INVALID_END_DATE = LocalDate.of(2022,1,1);

    private MonthlyChargeLabel testMonthlyChargeLabel;

    @Test
    public void testExportLabelConstructorCorrectMonthRange() {
        try {
            this.testMonthlyChargeLabel = new MonthlyChargeLabel(TEST_MONTHLY_CHARGE_QUANTITY,
                    TEST_MONTHLY_CHARGE_INVOICE_NUMBER, TEST_MONTHLY_CHARGE_INITIAL_DATE, TEST_MONTHLY_CHARGE_END_DATE);
        } catch (InvalidMonthRangeException e) {
            fail("This should not run");
        }
        assertEquals(TEST_MONTHLY_CHARGE_QUANTITY, this.testMonthlyChargeLabel.getQuantity());
        assertEquals(TEST_MONTHLY_CHARGE_INVOICE_NUMBER, this.testMonthlyChargeLabel.getInvoiceNumber());
        assertEquals(TEST_MONTHLY_CHARGE_INITIAL_DATE, this.testMonthlyChargeLabel.getInitialDate());
        assertEquals(TEST_MONTHLY_CHARGE_END_DATE, this.testMonthlyChargeLabel.getEndDate());
    }

    @Test
    public void testExportLabelConstructorInvalidMonthRange() {
        try {
            this.testMonthlyChargeLabel = new MonthlyChargeLabel(TEST_MONTHLY_CHARGE_QUANTITY,
                    TEST_MONTHLY_CHARGE_INVOICE_NUMBER, TEST_MONTHLY_CHARGE_INITIAL_DATE,
                    TEST_MONTHLY_CHARGE_INVALID_END_DATE);
            fail("This should not run");
        } catch (InvalidMonthRangeException e) {
            // pass
        }
    }
}
