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
                "Large");
    }

    @Test
    public void testPackageConstructor() {
        assertEquals("Adam Brown", testPackage.getOwnerName());
        assertEquals("12345 67ave, Vancouver, Canada, ABC 123", testPackage.getOwnerAddress());
        assertEquals("6041234567", testPackage.getOwnerPhoneNumber());
        assertEquals("Stationary items", testPackage.getProductContent());
        assertEquals("Large", testPackage.getSize());
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
        assertEquals(null, testPackage.getDateImportedIntoWarehouse());
        testPackage.setDateAndTimeImportedIntoWarehouse("10 Feb, 2021 14:20:12");
        assertEquals("10 Feb, 2021 14:20:12", testPackage.getDateImportedIntoWarehouse());
    }

    @Test
    public void testSetDateExportedFromWarehouse() {
        assertEquals(null, testPackage.getDateExportedFromWarehouse());
        testPackage.setDateAndTimeExportedFromWarehouse("11 Feb, 2021 15:22:21");
        assertEquals("11 Feb, 2021 15:22:21", testPackage.getDateExportedFromWarehouse());
    }

    @Test
    public void testSetAddressExportedTo() {
        assertEquals(null, testPackage.getAddressExportedTo());
        testPackage.setAddressExportedTo("22222 33ave, Calgary, Canada, FGH 456");
        assertEquals("22222 33ave, Calgary, Canada, FGH 456", testPackage.getAddressExportedTo());
    }





}
