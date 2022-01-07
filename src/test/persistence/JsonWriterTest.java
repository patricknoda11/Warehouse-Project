//package persistence;
//
//import model.Package;
//import model.Warehouse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class JsonWriterTest extends JsonTest {
//    private Warehouse updatedWarehouse;
//    private Package testPackage1;
//    private Package testPackage2;
//    private Package testPackage3;
//    private Package testPackage4;
//    private Package testPackage5;
//    private Package testPackage6;
//
//
//    @BeforeEach
//    public void setUp() {
//        resetJsonWriterSampleData();
//        updatedWarehouse = new Warehouse("Shoreline Warehousing");
//        testPackage1 = new Package("Miranda Williams", "98213 36 ave, Toronto, Canada, VW2 1S7",
//                "7072134567", "Garden Tools", "medium", "9");
//        testPackage2 = new Package("Benjamin Cruz", "10890 42 ave, Delta, Canada, L12 26K",
//                "7781899002", "Canned Salmon", "large", "10");
//        testPackage3 = new Package("Adam Chimney", "12879 108 st, Burnaby, Canada, C56 2A1",
//                "6045991426", "Facial Products", "small", "11");
//        testPackage4 = new Package("Camryn Miho", "17212 10 st, Kamloops, Canada, B28 1S2",
//                "6612139008", "Packaging Materials", "medium", "12");
//        testPackage5 = new Package("Austin Chimes", "22222 22 st, Surrey, Canada, L21 R23",
//                "6045932113", "Cosmetics", "small", "13");
//        testPackage6 = new Package("Meagan Rellington", "11111 10 st, Burnaby, Canada, L12 R23",
//                "778 219 9008", "Nutritional supplements", "large", "14");
//    }
//
//    // EFFECTS: resets ./data/TestJsonWriterEmptyInventory.json; ./data/TestJsonWriterMultipleItemsInEachSizeSection();
//    //          and ./data/TestJsonWriterONeItemInEachSizeSection.json files to original state after each test method
//    private void resetJsonWriterSampleData() {
//        resetTestJsonWriterEmptyInventory();
//        resetTestJsonWriterMultipleItemsInEachSizeSection();
//        resetTestJsonWriterOneItemInEachSizeSection();
//    }
//    // EFFECTS: resets ./data/testJsonWriterEmptyInventory.json by replacing content with reset copy
//    private void resetTestJsonWriterEmptyInventory() {
//        try {
//            JsonWriter oldWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            JsonReader reset =
//                    new JsonReader("./data/testJsonWriterResetEmptyInventory.json");
//            new Warehouse("My Warehouse");
//            Warehouse resetJsonWriterMultipleItemsInEachSizeSection;
//            resetJsonWriterMultipleItemsInEachSizeSection = reset.retrieveSavedWarehouseData();
//            oldWriter.saveToFile(resetJsonWriterMultipleItemsInEachSizeSection);
//        } catch (IOException e) {
//            fail();
//        }
//    }
//    // EFFECTS: resets ./data/testJsonWriterMultipleItemsInEachSizeSection.json by replacing content with reset copy
//    public void resetTestJsonWriterMultipleItemsInEachSizeSection() {
//        try {
//            JsonWriter oldWriter =
//                    new JsonWriter("./data/testJsonWriterMultipleItemsInEachSizeSection.json");
//            JsonReader reset =
//                    new JsonReader("./data/testJsonWriterResetMultipleItemsInEachSizeSection.json");
//            new Warehouse("My Warehouse");
//            Warehouse resetJsonWriterMultipleItemsInEachSizeSection;
//            resetJsonWriterMultipleItemsInEachSizeSection = reset.retrieveSavedWarehouseData();
//            oldWriter.saveToFile(resetJsonWriterMultipleItemsInEachSizeSection);
//        } catch (IOException e) {
//            fail();
//        }
//    }
//
//    // EFFECTS: resets ./data/testJsonWriterItemInEachSizeSection.json by replacing content with reset copy
//    public void resetTestJsonWriterOneItemInEachSizeSection() {
//        try {
//            JsonWriter oldWriter =
//                    new JsonWriter("./data/testJsonWriterOneItemInEachSizeSection.json");
//            JsonReader reset =
//                    new JsonReader("./data/testJsonWriterResetOneItemInEachSizeSection.json");
//            new Warehouse("My Warehouse");
//            Warehouse resetJsonWriterMultipleItemsInEachSizeSection;
//            resetJsonWriterMultipleItemsInEachSizeSection = reset.retrieveSavedWarehouseData();
//            oldWriter.saveToFile(resetJsonWriterMultipleItemsInEachSizeSection);
//        } catch (IOException e) {
//            fail();
//        }
//    }
//
//    @Test
//    public void testJsonWriterConstructor() {
//        JsonWriter testJsonWriter =
//                new JsonWriter("./data/testJsonWriterOneItemInEachSizeSection.json");
//        assertEquals("./data/testJsonWriterOneItemInEachSizeSection.json", testJsonWriter.getSaveLocation());
//    }
//
//
//    @Test
//    public void testSaveToFileInvalidFile() {
//        try {
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/Thi\0sFileDoesNotExist");
//            testJsonWriter.saveToFile(updatedWarehouse);
//            fail("The above line should throw error since the source file does not exist");
//        } catch (IOException e) {
//            // test passes!
//        }
//    }
//
//    @Test
//    public void testSaveToFileEmptyInventory() {
//        try {
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            Warehouse initialWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            // check initial warehouse values in ./data/testJsonWriterEmptyInventory.json
//            assertEquals("My Warehouse", initialWarehouse.getWarehouseName());
//            assertEquals(0, initialWarehouse.getNumberPackagesInInventory());
//            assertEquals(0, initialWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, initialWarehouse.getExportEvent().getExportHistory().size());
//            // save warehouse with updated name
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals("Shoreline Warehousing", retrievedWarehouse.getWarehouseName());
//            assertEquals(0, retrievedWarehouse.getNumberPackagesInInventory());
//            assertEquals(0, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileOneItemInLargeSection() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            updatedWarehouse.importProduct(testPackage2);
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(1, retrievedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(1, retrievedWarehouse.getLargeSizedPackages().size());
//            // check if history updated properly
//            assertEquals(1, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileOneItemInMediumSection() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            updatedWarehouse.importProduct(testPackage1);
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(1, retrievedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(1, retrievedWarehouse.getMediumSizedPackages().size());
//            // check if history updated properly
//            assertEquals(1, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileOneItemInSmallSection() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            updatedWarehouse.importProduct(testPackage3);
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(1, retrievedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(1, retrievedWarehouse.getSmallSizedPackages().size());
//            // check if history updated properly
//            assertEquals(1, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileOneItemInEachSizeSection() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            updatedWarehouse.importProduct(testPackage1);
//            updatedWarehouse.importProduct(testPackage2);
//            updatedWarehouse.importProduct(testPackage3);
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(3, retrievedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(1, retrievedWarehouse.getLargeSizedPackages().size());
//            assertEquals(1, retrievedWarehouse.getMediumSizedPackages().size());
//            assertEquals(1, retrievedWarehouse.getSmallSizedPackages().size());
//            // check if history updated properly
//            assertEquals(3, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileMultipleItemsInEachSizeSection() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterEmptyInventory.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterEmptyInventory.json");
//            updatedWarehouse.importProduct(testPackage1);
//            updatedWarehouse.importProduct(testPackage2);
//            updatedWarehouse.importProduct(testPackage3);
//            updatedWarehouse.importProduct(testPackage4);
//            updatedWarehouse.importProduct(testPackage5);
//            updatedWarehouse.importProduct(testPackage6);
//            testJsonWriter.saveToFile(updatedWarehouse);
//            // confirm save to file
//            Warehouse retrievedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(6, retrievedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(2, retrievedWarehouse.getLargeSizedPackages().size());
//            assertEquals(2, retrievedWarehouse.getMediumSizedPackages().size());
//            assertEquals(2, retrievedWarehouse.getSmallSizedPackages().size());
//            // check if history updated properly
//            assertEquals(6, retrievedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(0, retrievedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileAdditionOfGoodsToExistingInventory() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterMultipleItemsInEachSizeSection.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterMultipleItemsInEachSizeSection.json");
//            Warehouse initialWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            // check initial warehouse values in ./data/testJsonWriterMultipleItemsInEachSizeSection.json
//            assertEquals("My Warehouse", initialWarehouse.getWarehouseName());
//            assertEquals(6, initialWarehouse.getNumberPackagesInInventory());
//            assertEquals(8, initialWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(2, initialWarehouse.getExportEvent().getExportHistory().size());
//            // add goods to existing inventory
//            initialWarehouse.importProduct(testPackage1);
//            initialWarehouse.importProduct(testPackage2);
//            initialWarehouse.importProduct(testPackage3);
//            // save updates
//            testJsonWriter.saveToFile(initialWarehouse);
//            // retrieve saved data and confirm update has been recorded
//            Warehouse updatedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(9, updatedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(3, updatedWarehouse.getLargeSizedPackages().size());
//            assertEquals(3, updatedWarehouse.getMediumSizedPackages().size());
//            assertEquals(3, updatedWarehouse.getSmallSizedPackages().size());
//            // check if history updated properly
//            assertEquals(11, updatedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(2, updatedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//
//    @Test
//    public void testSaveToFileRemovalOfGoodsFromExistingInventory() {
//        try {
//            // setup
//            JsonReader testJsonReader =
//                    new JsonReader("./data/testJsonWriterMultipleItemsInEachSizeSection.json");
//            JsonWriter testJsonWriter =
//                    new JsonWriter("./data/testJsonWriterMultipleItemsInEachSizeSection.json");
//            Warehouse initialWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            initialWarehouse.importProduct(testPackage1);
//            initialWarehouse.importProduct(testPackage2);
//            initialWarehouse.importProduct(testPackage3);
//            initialWarehouse.exportProduct(testPackage1, "testAddress1");
//            initialWarehouse.exportProduct(testPackage2, "testAddress2");
//            // save export changes
//            testJsonWriter.saveToFile(initialWarehouse);
//            // retrieve updated warehouse
//            updatedWarehouse = testJsonReader.retrieveSavedWarehouseData();
//            assertEquals(7, updatedWarehouse.getNumberPackagesInInventory());
//            // check if packages correctly organized into sections
//            assertEquals(2, updatedWarehouse.getLargeSizedPackages().size());
//            assertEquals(2, updatedWarehouse.getMediumSizedPackages().size());
//            assertEquals(3, updatedWarehouse.getSmallSizedPackages().size());
//            // check if history updated properly
//            assertEquals(11, updatedWarehouse.getImportEvent().getImportHistory().size());
//            assertEquals(4, updatedWarehouse.getExportEvent().getExportHistory().size());
//        } catch (IOException e) {
//            fail("Exception should not be thrown!");
//        }
//    }
//}
