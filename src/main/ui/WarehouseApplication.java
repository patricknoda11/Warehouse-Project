package ui;

import model.Package;
import model.Warehouse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

// Warehouse management application
public class WarehouseApplication {
    private Warehouse myWarehouse;
    private Scanner userInput;
    private int newPackageIDCount;       // keeps track of package id for new import packages to prevent duplicate id
    private boolean isFinished;


    // EFFECTS: instantiates WarehouseApplication
    public WarehouseApplication() {
        initializeApplication();
        runApplicationMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: creates warehouse and scanner instance
    //          sets the warehouse's is finished status to false (used to terminate application)
    //          sets package id count to 0
    private void initializeApplication() {
        myWarehouse = new Warehouse("My Warehouse");
        userInput = new Scanner(System.in);
        isFinished = false;
        newPackageIDCount = 0;
    }

    // EFFECTS: runs warehouse application's main menu
    public void runApplicationMainMenu() {
        String inputValue;
        welcomeUser();

        while (!isFinished) {
            displayOptions();
            inputValue = userInput.next();
            inputValue = inputValue.toLowerCase();
            processMainMenuOptionsInput(inputValue);
        }
    }

    // EFFECTS: welcome the user into application and indicates current date
    private void welcomeUser() {
        DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String currentDate = newDateFormat.format(LocalDate.now());
        int timeOfDay = LocalTime.now().getHour();

        if (0 <= timeOfDay && timeOfDay < 12) {
            System.out.println("Good Morning, today is " + currentDate);
        } else if (12 <= timeOfDay && timeOfDay < 18) {
            System.out.println("Good Afternoon, today is " + currentDate);
        } else {
            System.out.println("Good Evening, today is " + currentDate);
        }
    }

    // EFFECTS: prints options that are available for user in main menu
    //          and the inputs required to access each option
    private void displayOptions() {
        System.out.println("\nMain menu: ");
        System.out.println("\tTo add a package to your inventory - type \"add\" ");
        System.out.println("\tTo remove a package from your inventory - type \"remove\"");
        System.out.println("\tTo view current inventory - type \"view\"");
        System.out.println("\tTo view a history of packages imported/exported - type \"history\"");
        System.out.println("\tTo exit - type \"exit\"\n");
    }

    // EFFECTS: processes user input and directs user to desired operation
    private void processMainMenuOptionsInput(String inputValue) {
        switch (inputValue) {
            case "add":
                doPackageImport();
                break;
            case "remove":
                doPackageExport();
                break;
            case "view":
                printWarehouseInventory();
                break;
            case "history":
                runApplicationViewHistoryMenu();
                break;
            case "exit":
                exitApplication();
                break;
            default:
                System.out.println("Invalid Entry Please try again\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: if the packages currently in warehouse inventory is less than the warehouse's maximum capacity
    //          it facilitates import operation
    //          otherwise it lets the user know that the inventory is already full
    private void doPackageImport() {
        int packagesInInventory = this.myWarehouse.getNumberPackagesInInventory();
        if (packagesInInventory < Warehouse.MAX_WAREHOUSE_CAPACITY) {
            Package packageToImport = createPackageToImport();
            this.myWarehouse.importPackage(packageToImport);
            System.out.println("\nPackage " + packageToImport.getPackageID()
                    + " has been stored in the inventory");
            System.out.println("The warehouse inventory now has: "
                    + this.myWarehouse.getNumberPackagesInInventory()
                    + " item(s) \n");
        } else {
            System.out.println("\nWarehouse inventory is full... Cannot import package\n");
        }
    }

    // EFFECTS: returns a new package that the user would like to import
    private Package createPackageToImport() {
        System.out.println("Creating new package to import. \nPlease add details regarding package below\n");
        return new Package(addOwnerNameToImportPackage(),
                addOwnerAddressToImportPackage(),
                addOwnerPhoneNumberToImportPackage(),
                addPackageContentForImportPackage(),
                addSizeInfoForImportPackage(),
                addPackageIDForImportPackage());
    }

    // REQUIRES: spaces must be replaced with underscore
    //           Ex. Patrick_Williams
    // EFFECTS: returns owner name to be added to import package
    private String addOwnerNameToImportPackage() {
        System.out.println("Please type in package owner Name");
        return userInput.next();
    }

    // REQUIRES: spaces must be replaced with underscore
    //           Ex. 12345_69b_ave_Vancouver_Canada_V3X78A
    // EFFECTS: returns owner address that will be added to import package
    private String addOwnerAddressToImportPackage() {
        System.out.println("Please type in package owner address");
        return userInput.next();
    }

    // REQUIRES: spaces must be replaced with underscore
    // EFFECTS: returns owner phone number that will be added to import package
    private String addOwnerPhoneNumberToImportPackage() {
        System.out.println("Please type in package owner phone number");
        return userInput.next();
    }

    // REQUIRES: spaces must be replaced with underscore
    // EFFECTS: returns package content info that will be added to import package
    private String addPackageContentForImportPackage() {
        System.out.println("Please type in package content");
        return userInput.next();
    }

    // REQUIRES: spaces must be replaced with underscore
    // EFFECTS: returns size info that will be added to import package
    private String addSizeInfoForImportPackage() {
        System.out.println("Please type in package size");
        System.out.println("Size should be either: large, medium or small");
        System.out.println("If size does not meet above criteria, the package will be stored in large section");
        String inputValue = userInput.next();
        return inputValue.toLowerCase();
    }

    // EFFECTS: returns package id as a String that will be added to import package
    private String addPackageIDForImportPackage() {
        this.newPackageIDCount++;
        return String.valueOf(this.newPackageIDCount);
    }

    // MODIFIES: this
    // EFFECTS: if the packages currently in warehouse inventory is 0
    //          it notifies user
    //          otherwise, it facilitates export by having the user indicate the package he/she
    //          would like to export and the address he/she would like the package exported to
    private void doPackageExport() {
        int packagesInInventory = this.myWarehouse.getNumberPackagesInInventory();
        if (packagesInInventory == 0) {
            System.out.println("\nWarehouse inventory has no packages to export\n");
        } else {
            Package packageToExport = choosePackageToExport();
            String packageDestination = indicatePackageDestination();
            this.myWarehouse.exportPackage(packageToExport, packageDestination);
            System.out.println("The package has been successfully shipped!");
            System.out.println("Package destination: "
                    + packageToExport.getAddressExportedTo());
            System.out.println("The warehouse inventory now has: "
                    + this.myWarehouse.getNumberPackagesInInventory() + " items \n");
        }
    }

    // EFFECTS: chooses package to export given user input
    private Package choosePackageToExport() {
        String inputValue;
        List<Package> availablePackagesToExport = this.myWarehouse.getAllPackagesAvailableInInventory();
        printWarehouseInventory();
        while (true) {
            System.out.println("Please type in package ID that you would like to export");
            inputValue = userInput.next();
            for (Package p : availablePackagesToExport) {
                String packageID = p.getPackageID();
                if (packageID.equals(inputValue)) {
                    return p;
                }
            }
            System.out.println("\nInvalid input please try again");
        }
    }

    // REQUIRES: the address indicated by the user must be in the format: address_city_country_PostalCode
    //           spaces must be replaced with underscore
    //           Ex: 12345_67ave_Surrey_Canada_V241Q8
    // EFFECTS: returns address that the user would like to export package to
    private String indicatePackageDestination() {
        String inputValue;
        System.out.println("\nPlease type the address that you would like the package to be shipped to");
        System.out.println("\tall spaces must be replaced with underscore");
        System.out.println("\taddress should be typed in the following format: address_city_country_PostalCode");
        System.out.println("\tEx: 12345_67ave_Surrey_Canada_V7B1L9\n");
        inputValue = userInput.next();
        return inputValue;
    }

    // EFFECTS: prints the packages currently stored in warehouse inventory
    private void printWarehouseInventory() {
        List<Package> largeSizedPackagesInventory = this.myWarehouse.getLargeSizedPackages();
        List<Package> mediumSizedPackagesInventory = this.myWarehouse.getMediumSizedPackages();
        List<Package> smallSizedPackagesInventory = this.myWarehouse.getSmallSizedPackages();
        if (this.myWarehouse.getNumberPackagesInInventory() == 0) {
            System.out.println("There are currently no packages in your inventory "
                    + "\nreturning back to main menu\n");
        } else {
            for (Package p : largeSizedPackagesInventory) {
                System.out.println();
                System.out.println(p);
            }
            for (Package p : mediumSizedPackagesInventory) {
                System.out.println();
                System.out.println(p);
            }
            for (Package p : smallSizedPackagesInventory) {
                System.out.println();
                System.out.println(p);
            }
        }
    }

    // EFFECTS: runs view history menu
    private void runApplicationViewHistoryMenu() {
        String inputValue;

        while (!isFinished) {
            displayHistoryOptions();
            inputValue = userInput.next();
            processHistoryOptionsInput(inputValue);
        }
    }

    // EFFECTS: prints view history menu options that the user can perform
    private void displayHistoryOptions() {
        System.out.println("\nView History Options: ");
        System.out.println("\tTo view import history - type \"import\"");
        System.out.println("\tTo view export history - type \"export\"");
        System.out.println("\tTo return to the main menu - type \"return\"\n");
    }

    // EFFECTS: processes user input and directs to desired operation in view history menu
    private void processHistoryOptionsInput(String inputValue) {
        switch (inputValue) {
            case "import":
                printImportHistory();
                break;
            case "export":
                printExportHistory();
                break;
            case "return":
                runApplicationMainMenu();
                break;
            default:
                System.out.println("Invalid entry, please try again \n");
        }
    }

    // EFFECTS: prints the history of all packages that have been imported
    private void printImportHistory() {
        List<Package> importHistory = myWarehouse.getImportHistory();
        if (importHistory.size() == 0) {
            System.out.println("There are no records of package imports"
                    + "\nreturning back to View History Options");
        } else {
            for (Package p : importHistory) {
                System.out.println();
                System.out.println(p);
            }
        }
    }

    // EFFECTS: prints the history of all packages that have been exported
    private void printExportHistory() {
        List<Package> exportHistory = myWarehouse.getExportHistory();
        if (exportHistory.size() == 0) {
            System.out.println("There are no records of package exports"
                    + "\nreturning back to View History Options");
        } else {
            for (Package p : exportHistory) {
                System.out.println();
                System.out.println(p);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves changes made to Warehouse inventory on file
    private void saveWarehouseInventoryToFile() {
        // stub
    }

    // EFFECTS: loads Warehouse inventory from file
    private void loadWarehouseInventoryFromFile() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: signals to warehouse application to terminate application
    private void exitApplication() {
        System.out.println("\nGoodbye!");
        this.isFinished = true;
    }
}
