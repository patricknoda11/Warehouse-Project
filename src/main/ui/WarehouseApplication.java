package ui;

import model.Package;
import model.Warehouse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Warehouse management application
public class WarehouseApplication {

    private Warehouse myWarehouse;
    private Scanner userInput;
    private boolean isFinished;
    private List<Package> packagesAvailableToImport;

    // EFFECTS: instantiates WarehouseApplication
    public WarehouseApplication() {
        initializeApplication();
        runApplicationMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: creates warehouse and scanner instance
    private void initializeApplication() {
        myWarehouse = new Warehouse();
        userInput = new Scanner(System.in);
        isFinished = false;
        packagesAvailableToImport = new ArrayList<>();
        Package package1 = new Package("Timothy Grimes", "12126 26 st, Vancouver, Canada, V51L1A",
                "604 912 8091", "Cardboard boxes", "Large", "1");
        Package package2 = new Package("Anthony Vega", "31782 adams st, Calgary, Canada, Q12O8P",
                "604 312 9910", "Canned food", "Large", "2");
        Package package3 = new Package("Alexia Anderson", "77039 138 st, Prince Rupert, Canada, B1A76V",
                "778 776 4397", "Styrofoam boxes", "Medium", "3");
        Package package4 = new Package("Emily Willcott", "43219 sesame st, Surrey, Canada, V7S1L1",
                "7079895555", "Plastic bottles", "Small", "4");
        Package package5 = new Package("Luwawu Cabarot", "00939 albert ave, Burnaby, Canada BNR6D2",
                "7781907789", "Utilities", "Small", "5");
        packagesAvailableToImport.add(package1);
        packagesAvailableToImport.add(package2);
        packagesAvailableToImport.add(package3);
        packagesAvailableToImport.add(package4);
        packagesAvailableToImport.add(package5);
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

    // EFFECTS: prints options that are available for user and the
    //          corresponding inputs required to access each option
    private void displayOptions() {
        System.out.println("\nMain menu: ");
        System.out.println("\tTo add a package to your inventory - type \"add\" ");
        System.out.println("\tTo remove a package from your inventory - type \"remove\"");
        System.out.println("\tTo view current inventory - type \"view\"");
        System.out.println("\tTo view a history of packages imported/exported - type \"history\"");
        System.out.println("\tTo exit - type \"exit\"\n");
    }

    // EFFECTS: processes user input and directs to desired operation
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


    // MODIFIES: this, package
    // EFFECTS: creates new package and imports package into warehouse inventory
    private void doPackageImport() {
        int packagesInInventory = this.myWarehouse.getNumberPackagesInInventory();
        if (packagesInInventory < Warehouse.MAX_WAREHOUSE_CAPACITY) {
            Package packageToImport = choosePackageToImport();
            this.myWarehouse.importPackage(packageToImport);
            System.out.println("\nPackage " + packageToImport.getPackageID()
                    + " has been stored in the inventory");
            this.packagesAvailableToImport.remove(packageToImport);
            System.out.println("The warehouse inventory now has: "
                    + this.myWarehouse.getNumberPackagesInInventory()
                    + " item(s) \n");
        } else {
            System.out.println("\nWarehouse inventory is full... Cannot import package\n");
        }
    }

    // EFFECTS: returns package that the user has chosen
    private Package choosePackageToImport() {
        String inputValue;
        displayPackagesAvailableToImport();
        while (true) {
            inputValue = userInput.next();
            for (Package p : packagesAvailableToImport) {
                String packageIdentification = p.getPackageID();
                if (inputValue.equals(packageIdentification)) {
                    return p;
                }
            }
            System.out.println("\nInvalid input please try again");
        }
    }

    // EFFECTS: prints the packages that are currently available to import
    private void displayPackagesAvailableToImport() {
        int numberOfPackagesAvailableToImport = packagesAvailableToImport.size();
        if (numberOfPackagesAvailableToImport == 0) {
            System.out.println("There are no packages currently available to import");
            System.out.println("Returning to main menu...\n");
            runApplicationMainMenu();
        } else {
            for (Package p : packagesAvailableToImport) {
                System.out.println();
                System.out.println(p);
            }
            System.out.println("\nPlease type in package ID that you would like to import");
        }
    }

    // MODIFIES: this, package
    // EFFECTS: exports package from warehouse inventory
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
    //           Ex: 12345_67ave_Surrey_Canada_v241Q8
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

    // EFFECTS: prints history options that the user can perform
    private void displayHistoryOptions() {
        System.out.println("\nView History Options: ");
        System.out.println("\tTo view import history - type \"import\"");
        System.out.println("\tTo view export history - type \"export\"");
        System.out.println("\tTo return to the main menu - type \"return\"\n");
    }

    // EFFECTS: processes user input and directs to desired operation
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
    // EFFECTS: signals to warehouse application to end application
    private void exitApplication() {
        System.out.println("\nGoodbye!");
        this.isFinished = true;
    }
}
