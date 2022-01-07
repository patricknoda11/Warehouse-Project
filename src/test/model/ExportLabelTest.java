package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ExportLabel tests
 */
public class ExportLabelTest {
    private static final int TEST_EXPORT_QUANTITY = 10;
    private static final String TEST_EXPORT_INVOICE_NUMBER = "000000";
    private static final LocalDate TEST_EXPORT_DATE = LocalDate.of(2021, 12, 25);

    private ExportLabel testExportLabel;

    @BeforeEach
    public void setUp() {
        this.testExportLabel = new ExportLabel(TEST_EXPORT_QUANTITY, TEST_EXPORT_INVOICE_NUMBER, TEST_EXPORT_DATE);
    }

    @Test
    public void testExportLabelConstructor() {
        assertEquals(TEST_EXPORT_QUANTITY, this.testExportLabel.getQuantity());
        assertEquals(TEST_EXPORT_INVOICE_NUMBER, this.testExportLabel.getInvoiceNumber());
        assertEquals(TEST_EXPORT_DATE, this.testExportLabel.getExportDate());
    }
}
