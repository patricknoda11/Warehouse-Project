package persistence;

import model.Warehouse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testJsonReaderConstructor() {
        JsonReader testJsonReader =
                new JsonReader("./data/testJsonReaderEmptyInventory.json");
        assertEquals("./data/testJsonReaderEmptyInventory.json", testJsonReader.getSaveLocation());
    }

    @Test
    public void testRetrieveSavedWarehouseDataNonExistentFile() {
        try {
            JsonReader testJsonReader =
                    new JsonReader("./data/NoSuchFileExist");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            fail("Exception was expected");
        } catch (IOException e) {
            // test passes if exception is thrown"
        }
    }

    @Test
    public void testRetrieveSavedWarehouseDataEmptyInventory() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderEmptyInventory.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(0, testWarehouse.getNumberPackagesInInventory());
            assertEquals(0, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(0, testWarehouse.getExportEvent().getExportHistory().size());
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }

    @Test
    public void testRetrieveSavedWarehouseDataOneItemInLargeSection() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderOneItemInILargeSection.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(1, testWarehouse.getNumberPackagesInInventory());
            assertEquals(3, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(2, testWarehouse.getExportEvent().getExportHistory().size());
            // check if package organized into correct size section
            assertEquals(1, testWarehouse.getLargeSizedPackages().size());
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }


    @Test
    public void testRetrieveSavedWarehouseDataOneItemInMediumSection() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderOneItemInMediumSection.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(1, testWarehouse.getNumberPackagesInInventory());
            assertEquals(3, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(2, testWarehouse.getExportEvent().getExportHistory().size());
            // check if package organized into correct size section
            assertEquals(1, testWarehouse.getMediumSizedPackages().size());
            // check if package details in import history correctly read and loaded
            checkImportHistoryPackageDetails(testWarehouse);
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }

    // EFFECTS: helper method to check import history package details
    private void checkImportHistoryPackageDetails(Warehouse testWarehouse) {
        checkPackageDetails(testWarehouse.getImportEvent().getImportHistory().get(0),
                "1", "testName", "dog food", "large", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "1 Jan, 2021 15:56:32",
                "3 Mar 2021, 16:00:27","testAddress",
                true, false);
        checkPackageDetails(testWarehouse.getImportEvent().getImportHistory().get(1),
                "2", "testName", "bottles", "large", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "1 Jan, 2021 15:56:32",
                "3 Mar 2021, 16:00:27","testAddress",
                true, false);
        checkPackageDetails(testWarehouse.getImportEvent().getImportHistory().get(2),
                "3", "testName", "cans", "medium", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "8 Feb, 2021 15:56:32",
                "has not been exported yet","has not been exported yet",
                false, true);
    }

    @Test
    public void testRetrieveSavedWarehouseDataOneItemInSmallSection() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderOneItemInSmallSection.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(1, testWarehouse.getNumberPackagesInInventory());
            assertEquals(3, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(2, testWarehouse.getExportEvent().getExportHistory().size());
            // check if package organized into correct size section
            assertEquals(1, testWarehouse.getSmallSizedPackages().size());
            // check if package details in export history correctly read and loaded
            checkExportHistoryPackageDetails(testWarehouse);
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }

    // EFFECTS: helper method to check export history package details
    private void checkExportHistoryPackageDetails(Warehouse testWarehouse) {
        checkPackageDetails(testWarehouse.getImportEvent().getImportHistory().get(0),
                "1", "testName", "dog food", "large", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "1 Jan, 2021 15:56:32",
                "3 Mar 2021, 16:00:27","testAddress",
                true, false);
        checkPackageDetails(testWarehouse.getImportEvent().getImportHistory().get(1),
                "2", "testName", "bottles", "large", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "1 Jan, 2021 15:56:32",
                "3 Mar 2021, 16:00:27","testAddress",
                true, false);
    }

    @Test
    public void testRetrieveSavedWarehouseDataOneItemInEachSizeSection() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderOneItemInEachSizeSection.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(3, testWarehouse.getNumberPackagesInInventory());
            assertEquals(5, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(2, testWarehouse.getExportEvent().getExportHistory().size());
            // check if package organized into correct size section
            assertEquals(1, testWarehouse.getLargeSizedPackages().size());
            assertEquals(1, testWarehouse.getMediumSizedPackages().size());
            assertEquals(1, testWarehouse.getSmallSizedPackages().size());
            // check if package details in inventory correctly read and loaded
            checkInventoryPackageDetails(testWarehouse);
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }

    // EFFECTS: helper method to check inventory package details
    private void checkInventoryPackageDetails(Warehouse testWarehouse) {
        checkPackageDetails(testWarehouse.getAllPackagesAvailableInInventory().get(1),
                "3", "testName", "cans", "medium", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "8 Feb, 2021 15:56:32",
                "has not been exported yet","has not been exported yet",
                false, true);
        checkPackageDetails(testWarehouse.getAllPackagesAvailableInInventory().get(2),
                "4", "testName", "card board boxes", "small", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "20 Feb, 2021 15:56:32",
                "has not been exported yet","has not been exported yet",
                false, true);
        checkPackageDetails(testWarehouse.getAllPackagesAvailableInInventory().get(0),
                "5", "testName", "coca Cola", "large", "6045009091",
                "1234567_23rd_ave_Vancouver_Canada_V1ALQ3", "27 Feb, 2021 15:56:32",
                "has not been exported yet","has not been exported yet",
                false, true);
    }

    @Test
    public void testRetrieveSavedWarehouseDataMultipleItemsInEachSizeSection() {
        try {
            // setup
            JsonReader testJsonReader =
                    new JsonReader("./data/testJsonReaderMultipleItemsInEachSizeSection.json");
            Warehouse testWarehouse = testJsonReader.retrieveSavedWarehouseData();
            // check if data read from source file correctly
            assertEquals("My Warehouse", testWarehouse.getWarehouseName());
            assertEquals(6, testWarehouse.getNumberPackagesInInventory());
            assertEquals(8, testWarehouse.getImportEvent().getImportHistory().size());
            assertEquals(2, testWarehouse.getExportEvent().getExportHistory().size());
            // check if package organized into correct size section
            assertEquals(2, testWarehouse.getLargeSizedPackages().size());
            assertEquals(2, testWarehouse.getMediumSizedPackages().size());
            assertEquals(2, testWarehouse.getSmallSizedPackages().size());
        } catch (IOException e) {
            fail("Exception should not pass");
        }
    }
}
