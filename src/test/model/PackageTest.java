package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackageTest {
    private Package testPackage;

    @BeforeEach
    public void setUp() {
        testPackage = new Package("Adam Brown",
                "12345 67ave, Vancouver, Canada, ABC 123",
                "6041234567",
                "Stationary items",
                "Large",
                "123");
    }

    @Test
    public void testPackageConstructor() {
        assertEquals("Adam Brown", testPackage.getOwnerName());
        assertEquals("12345 67ave, Vancouver, Canada, ABC 123", testPackage.getOwnerAddress());
        assertEquals("6041234567", testPackage.getOwnerPhoneNumber());
        assertEquals("Stationary items", testPackage.getContent());
        assertEquals("Large", testPackage.getSize());
        assertEquals("has not been imported yet", testPackage.getDateImportedIntoWarehouse());
        assertEquals("has not been exported yet", testPackage.getDateExportedFromWarehouse());
        assertEquals("has not been exported yet", testPackage.getAddressExportedTo());
        assertEquals("123", testPackage.getPackageID());
        assertFalse(testPackage.getIsInWarehouse());
        assertFalse(testPackage.getHasBeenExportedFromWarehouse());
    }

    @Test
    public void testUpdateOwnerPhoneNumber() {
        assertEquals("6041234567", testPackage.getOwnerPhoneNumber());
        testPackage.updateOwnerPhoneNumber("6041112233");
        assertEquals("6041112233", testPackage.getOwnerPhoneNumber());
    }

    @Test
    public void testUpdateOwnerAddress() {
        assertEquals("12345 67ave, Vancouver, Canada, ABC 123", testPackage.getOwnerAddress());
        testPackage.updateOwnerAddress("11111 22ave, Vancouver, Canada, DEF 456");
        assertEquals("11111 22ave, Vancouver, Canada, DEF 456", testPackage.getOwnerAddress());
    }

    @Test
    public void testSetDateImportedIntoWarehouse() {
        assertEquals("has not been imported yet", testPackage.getDateImportedIntoWarehouse());
        testPackage.setDateAndTimeImportedIntoWarehouse("10 Feb, 2021 14:20:12");
        assertEquals("10 Feb, 2021 14:20:12", testPackage.getDateImportedIntoWarehouse());
    }

    @Test
    public void testSetDateExportedFromWarehouse() {
        assertEquals("has not been exported yet", testPackage.getDateExportedFromWarehouse());
        testPackage.setDateAndTimeExportedFromWarehouse("11 Feb, 2021 15:22:21");
        assertEquals("11 Feb, 2021 15:22:21", testPackage.getDateExportedFromWarehouse());
    }

    @Test
    public void testSetAddressExportedTo() {
        assertEquals("has not been exported yet", testPackage.getAddressExportedTo());
        testPackage.setAddressExportedTo("22222 33ave, Calgary, Canada, FGH 456");
        assertEquals("22222 33ave, Calgary, Canada, FGH 456", testPackage.getAddressExportedTo());
    }

    @Test
    public void testToString() {
        assertEquals("Package Owner: Adam Brown"
                + "\n Package ID: 123"
                + "\n Owner Phone Number: 6041234567"
                + "\n Owner Address: 12345 67ave, Vancouver, Canada, ABC 123"
                + "\n Package Content: Stationary items"
                + "\n Package Size: Large"
                + "\n Date Imported: has not been imported yet"
                + "\n Date Exported: has not been exported yet"
                + "\n Address Exported To: has not been exported yet", testPackage.toString());
    }
}
