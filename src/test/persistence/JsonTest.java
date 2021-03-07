package persistence;

import model.Package;
import model.Warehouse;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkPackageDetails(Package aPackage,
                                       String packageID,
                                       String ownerName,
                                       String content,
                                       String size,
                                       String ownerPhoneNumber,
                                       String ownerAddress,
                                       String dateImportedIntoWarehouse,
                                       String dateExportedFromWarehouse,
                                       String addressExportedFromWarehouse,
                                       boolean hasBeenExportedFromWarehouse,
                                       boolean isInWarehouse) {
        assertEquals(packageID, aPackage.getPackageID());
        assertEquals(ownerName, aPackage.getOwnerName());
        assertEquals(content, aPackage.getContent());
        assertEquals(size, aPackage.getSize());
        assertEquals(ownerPhoneNumber, aPackage.getOwnerPhoneNumber());
        assertEquals(ownerAddress, aPackage.getOwnerAddress());
        assertEquals(dateImportedIntoWarehouse, aPackage.getDateImportedIntoWarehouse());
        assertEquals(dateExportedFromWarehouse, aPackage.getDateExportedFromWarehouse());
        assertEquals(addressExportedFromWarehouse, aPackage.getAddressExportedTo());
        assertEquals(hasBeenExportedFromWarehouse, aPackage.getHasBeenExportedFromWarehouse());
        assertEquals(isInWarehouse, aPackage.getIsInWarehouse());
    }


}
