package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PackageTest {
    private Package testPackage;

    @BeforeEach
    public void setUp() {
        testPackage = new Package("Adam Brown",
                "12345 67ave, Vancouver, Canada, ABC 123",
                "6041234567",
                "Stationary items");
    }

    @Test
    public void testPackageConstructor() {
        assertEquals("Adam Brown", testPackage.getOwnerName());
        assertEquals("12345 67ave, Vancouver, Canada, ABC 123", testPackage.getOwnerAddress());
        assertEquals("6041234567", testPackage.getOwnerPhoneNumber());
        assertEquals("Stationary items", testPackage.getProductContained());
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
        testPackage.setDateImportedIntoWarehouse("February/14/2021");
        assertEquals("February/14/2021", testPackage.getDateImportedIntoWarehouse());
    }

    @Test
    public void testSetDateExportedFromWarehouse() {
        assertEquals(null, testPackage.getDateExportedFromWarehouse());
        testPackage.setDateExportedFromWarehouse("February/15/2021");
        assertEquals("February/15/2021", testPackage.getDateExportedFromWarehouse());
    }

    @Test
    public void testSetAddressExportedTo() {
        assertEquals(null, testPackage.getAddressExportedTo());
        testPackage.setAddressExportedTo("22222 33ave, Calgary, Canada, FGH 456");
        assertEquals("22222 33ave, Calgary, Canada, FGH 456", testPackage.getAddressExportedTo());
    }





}
