package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse testWarehouse;
    private Package testPackage1;
    private Package testPackage2;
    private Package testPackage3;
    private Package testPackage4;
    private List<List<Package>> testInventory;
    private List<Package> testLargeSizedPackages;
    private List<Package> testMediumSizedPackages;
    private List<Package> testSmallSizedPackages;
    private List<Package> testExportHistory;
    private List<Package> testImportHistory;

    @BeforeEach
    public void setUp() {
        testWarehouse = new Warehouse();
        testInventory = testWarehouse.getInventory();
        testLargeSizedPackages = testWarehouse.getLargeSizedPackages();
        testMediumSizedPackages = testWarehouse.getMediumSizedPackages();
        testSmallSizedPackages = testWarehouse.getSmallSizedPackages();
        testExportHistory = testWarehouse.getExportHistory();
        testImportHistory = testWarehouse.getImportHistory();

        testPackage1 = new Package("Miranda Williams", "98213 36 ave, Toronto, Canada, VW2 1S7",
                "7072134567", "Garden Tools", "Medium");
        testPackage2 = new Package("Benjamin Cruz", "10890 42 ave, Delta, Canada, L12 26K",
                "7781899002", "Canned Salmon", "Large");
        testPackage3 = new Package("Adam Chimney", "12879 108 st, Burnaby, Canada, C56 2A1",
                "6045991426", "Facial Products", "Small");
        testPackage4 = new Package("Camryn Miho", "17212 10 st, Kamloops, Canada, B28 1S2",
                "6612139008", "Packaging Materials", "Medium");
    }

    @Test
    public void testWarehouseConstructor() {
        assertEquals(3, testInventory.size());
        assertTrue(testInventory.contains(testLargeSizedPackages));
        assertTrue(testInventory.contains(testMediumSizedPackages));
        assertTrue(testInventory.contains(testSmallSizedPackages));

        assertEquals(0, testLargeSizedPackages.size());
        assertEquals(0, testMediumSizedPackages.size());
        assertEquals(0, testSmallSizedPackages.size());

        assertEquals(0, testExportHistory.size());
        assertEquals(0, testImportHistory.size());
    }

    @Test
    public void testImportPackageOnePackage() {
        assertFalse(testPackage1.getIsInWarehouse());
        testWarehouse.importPackage(testPackage1);

        assertEquals(1, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testMediumSizedPackages.size());
        assertTrue(testMediumSizedPackages.contains(testPackage1));
        assertEquals(Collections.emptyList(), testLargeSizedPackages);
        assertEquals(Collections.emptyList(), testSmallSizedPackages);
        assertEquals(1, testImportHistory.size());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testPackage1.getIsInWarehouse());
    }

    @Test
    public void testImportPackageMultiplePackagesIntoSameSizeSection() {
        assertFalse(testPackage1.getIsInWarehouse());
        assertFalse(testPackage4.getIsInWarehouse());
        testWarehouse.importPackage(testPackage1);
        testWarehouse.importPackage(testPackage4);

        assertEquals(2, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(2, testMediumSizedPackages.size());
        assertTrue(testMediumSizedPackages.contains(testPackage1));
        assertTrue(testMediumSizedPackages.contains(testPackage4));
        assertEquals(Collections.emptyList(), testLargeSizedPackages);
        assertEquals(Collections.emptyList(), testSmallSizedPackages);
        assertEquals(2, testImportHistory.size());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage4));
        assertTrue(testPackage1.getIsInWarehouse());
        assertTrue(testPackage4.getIsInWarehouse());
    }

    @Test
    public void testImportPackageMultiplePackagesIntoDifferentSizeSections() {
        assertFalse(testPackage2.getIsInWarehouse());
        assertFalse(testPackage1.getIsInWarehouse());
        assertFalse(testPackage3.getIsInWarehouse());

        testWarehouse.importPackage(testPackage2);

        assertEquals(1, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testLargeSizedPackages.size());
        assertTrue(testLargeSizedPackages.contains(testPackage2));
        assertEquals(Collections.emptyList(), testMediumSizedPackages);
        assertEquals(Collections.emptyList(), testSmallSizedPackages);
        assertEquals(1, testImportHistory.size());
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testPackage2.getIsInWarehouse());

        testWarehouse.importPackage(testPackage1);

        assertEquals(2, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testLargeSizedPackages.size());
        assertEquals(1, testMediumSizedPackages.size());
        assertTrue(testLargeSizedPackages.contains(testPackage2));
        assertTrue(testMediumSizedPackages.contains(testPackage1));
        assertEquals(Collections.emptyList(), testSmallSizedPackages);
        assertEquals(2, testImportHistory.size());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testPackage1.getIsInWarehouse());

        testWarehouse.importPackage(testPackage3);

        assertEquals(3, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testLargeSizedPackages.size());
        assertEquals(1, testMediumSizedPackages.size());
        assertEquals(1, testSmallSizedPackages.size());
        assertTrue(testLargeSizedPackages.contains(testPackage2));
        assertTrue(testMediumSizedPackages.contains(testPackage1));
        assertTrue(testSmallSizedPackages.contains(testPackage3));
        assertEquals(3, testImportHistory.size());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testImportHistory.contains(testPackage3));
        assertEquals(0, testExportHistory.size());
        assertTrue(testPackage3.getIsInWarehouse());

    }

    @Test
    public void testExportPackageOnePackage() {
        testWarehouse.importPackage(testPackage1);
        assertTrue(testPackage1.getIsInWarehouse());
        assertFalse(testPackage1.getHasBeenExportedFromWarehouse());
        testWarehouse.exportPackage(testPackage1);

        assertEquals(0, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testWarehouse.getImportHistorySize());
        assertEquals(1, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage1));
        assertFalse(testPackage1.getIsInWarehouse());
        assertTrue(testPackage1.getHasBeenExportedFromWarehouse());
    }

    @Test
    public void testExportPackageMultiplePackagesFromDifferentSizeSection() {
        testWarehouse.importPackage(testPackage1);
        testWarehouse.importPackage(testPackage2);
        testWarehouse.importPackage(testPackage3);
        assertTrue(testPackage1.getIsInWarehouse());
        assertTrue(testPackage2.getIsInWarehouse());
        assertTrue(testPackage3.getIsInWarehouse());
        assertFalse(testPackage1.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage2.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage3.getHasBeenExportedFromWarehouse());


        testWarehouse.exportPackage(testPackage1);

        assertEquals(2, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testLargeSizedPackages.size());
        assertEquals(0, testMediumSizedPackages.size());
        assertEquals(1, testSmallSizedPackages.size());
        assertTrue(testLargeSizedPackages.contains(testPackage2));
        assertTrue(testSmallSizedPackages.contains(testPackage3));
        assertEquals(3, testWarehouse.getImportHistorySize());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testImportHistory.contains(testPackage3));
        assertEquals(1, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertTrue(testPackage1.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage1.getIsInWarehouse());

        testWarehouse.exportPackage(testPackage3);

        assertEquals(1, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(1, testLargeSizedPackages.size());
        assertEquals(0, testMediumSizedPackages.size());
        assertEquals(0, testSmallSizedPackages.size());
        assertTrue(testLargeSizedPackages.contains(testPackage2));
        assertEquals(3, testWarehouse.getImportHistorySize());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testImportHistory.contains(testPackage3));
        assertEquals(2, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertTrue(testExportHistory.contains(testPackage3));
        assertTrue(testPackage3.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage3.getIsInWarehouse());

        testWarehouse.exportPackage(testPackage2);

        assertEquals(0, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(0, testLargeSizedPackages.size());
        assertEquals(0, testMediumSizedPackages.size());
        assertEquals(0, testSmallSizedPackages.size());
        assertEquals(3, testWarehouse.getImportHistorySize());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage2));
        assertTrue(testImportHistory.contains(testPackage3));
        assertEquals(3, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertTrue(testExportHistory.contains(testPackage2));
        assertTrue(testExportHistory.contains(testPackage3));
        assertTrue(testPackage2.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage2.getIsInWarehouse());
    }

    @Test
    public void testExportPackageMultiplePackagesFromSameSection() {
        testWarehouse.importPackage(testPackage1);
        testWarehouse.importPackage(testPackage4);
        assertTrue(testPackage1.getIsInWarehouse());
        assertTrue(testPackage4.getIsInWarehouse());
        assertFalse(testPackage1.getHasBeenExportedFromWarehouse());
        assertFalse(testPackage4.getHasBeenExportedFromWarehouse());


        testWarehouse.exportPackage(testPackage1);

        assertEquals(1, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(0, testLargeSizedPackages.size());
        assertEquals(1, testMediumSizedPackages.size());
        assertEquals(0, testSmallSizedPackages.size());
        assertTrue(testMediumSizedPackages.contains(testPackage4));
        assertEquals(2, testWarehouse.getImportHistorySize());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage4));
        assertEquals(1, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertFalse(testPackage1.getIsInWarehouse());
        assertTrue(testPackage1.getHasBeenExportedFromWarehouse());

        testWarehouse.exportPackage(testPackage4);

        assertEquals(0, testWarehouse.getTotalNumberOfPackagesInInventory());
        assertEquals(0, testLargeSizedPackages.size());
        assertEquals(0, testMediumSizedPackages.size());
        assertEquals(0, testSmallSizedPackages.size());
        assertEquals(2, testWarehouse.getImportHistorySize());
        assertTrue(testImportHistory.contains(testPackage1));
        assertTrue(testImportHistory.contains(testPackage4));
        assertEquals(2, testWarehouse.getExportHistorySize());
        assertTrue(testExportHistory.contains(testPackage1));
        assertTrue(testExportHistory.contains(testPackage4));
        assertFalse(testPackage4.getIsInWarehouse());
        assertTrue(testPackage4.getHasBeenExportedFromWarehouse());
    }

}
