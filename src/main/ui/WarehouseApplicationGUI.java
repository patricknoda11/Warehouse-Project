package ui;

import model.Package;
import model.Warehouse;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.operations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

// Warehouse management application
public class WarehouseApplicationGUI extends JFrame implements ActionListener {
    public static final String SOURCE_FILE_1 = "./data/warehouseInventoryFile1.json"; // delete later
    public static final String SOURCE_FILE_2 = "./data/warehouseInventoryFile2.json"; // delete later
    public static final String SOURCE_FILE_3 = "./data/warehouseInventoryFile3.json"; // delete later
    private static final int GUI_WIDTH = 1300;
    private static final int GUI_HEIGHT = 1000;
    private static final Color GUI_BACKGROUND_COLOUR = new Color(184, 216, 232);
    private static final int TIMER_REFRESH = 1000;

    private Warehouse myWarehouse;
    private Scanner userInput;
    private JsonReader jsonReader;       // delete later --> moved to Load class!!
    private JsonWriter jsonWriter;       // delete later --> moved to Save class!!
    private int newPackageIDCount;       // keeps track of package id for new import packages to prevent duplicate id
    private boolean isFinished;

    private JTextArea currentInventory;
    private JLabel communicatorText;

    // EFFECTS: instantiates WarehouseApplication
    public WarehouseApplicationGUI() {
        initializeApplication();
        initializeGUI();
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
        newPackageIDCount = 1;
    }

    private void initializeGUI() {
        setTitle("Warehouse Management");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(GUI_WIDTH,GUI_HEIGHT);
        setLocationRelativeTo(null); // sets the location of the window to the center of screen

        JPanel mainUpperPanel = new JPanel();
        JPanel mainMiddlePanel = new JPanel();
        JPanel mainBottomPanel = new JPanel();

        mainUpperPanel.setBackground(GUI_BACKGROUND_COLOUR);
        mainMiddlePanel.setLayout(new GridLayout(0,3));
        add(mainUpperPanel, BorderLayout.NORTH);
        add(mainMiddlePanel, BorderLayout.CENTER);
        add(mainBottomPanel, BorderLayout.SOUTH);

        communicatorText = new JLabel();
        communicatorText.setBackground(GUI_BACKGROUND_COLOUR);
        mainBottomPanel.add(communicatorText);

        addWelcomeMenu(mainUpperPanel);
        addMainMenuOptions(mainMiddlePanel);
        setVisible(true);
    }

    private void addWelcomeMenu(JPanel mainUpperPanel) {
        JLabel welcomeStatement = new JLabel();
        mainUpperPanel.add(welcomeStatement);
        DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm");

        Timer timer = new Timer(TIMER_REFRESH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String currentDateAndTime = newDateFormat.format(LocalDateTime.now());
                int timeOfDay = LocalTime.now().getHour();

                if (0 <= timeOfDay && timeOfDay < 12) {
                    welcomeStatement.setText("Good Morning, today is " + currentDateAndTime + " AM");
                } else if (12 <= timeOfDay && timeOfDay < 18) {
                    welcomeStatement.setText("Good Afternoon, today is " + currentDateAndTime + "PM");
                } else {
                    welcomeStatement.setText("Good Evening, today is " + currentDateAndTime + " PM");
                }
            }
        });

        timer.start();
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
        System.out.println("\tTo save changes to warehouse inventory - type \"save\"");
        System.out.println("\tTo load a previously saved warehouse inventory - type \"load\"");
        System.out.println("\tTo exit - type \"exit\"\n");
    }

    private void addMainMenuOptions(JPanel mainMiddlePanel) {
        JPanel importExportPanel = new JPanel();
        JPanel historySaveLoadPanel = new JPanel();
        JScrollPane currentInventoryDisplay = setupCurrentInventoryDisplay();
        importExportPanel.setBackground(GUI_BACKGROUND_COLOUR);
        historySaveLoadPanel.setBackground(GUI_BACKGROUND_COLOUR);

        importExportPanel.setLayout(new GridLayout(2, 0));
        historySaveLoadPanel.setLayout(new GridLayout(3,0));

        setUpMainMenuButtons(importExportPanel, historySaveLoadPanel);

        mainMiddlePanel.add(importExportPanel);
        mainMiddlePanel.add(historySaveLoadPanel);
        mainMiddlePanel.add(currentInventoryDisplay);
    }

    private JScrollPane setupCurrentInventoryDisplay() {
        currentInventory = new JTextArea("There are no items currently stored in the inventory");
        currentInventory.setEditable(false);
        currentInventory.setLineWrap(true);
        currentInventory.setWrapStyleWord(true);

        JScrollPane currentInventoryDisplay = new JScrollPane(currentInventory);
        currentInventoryDisplay.createHorizontalScrollBar();
        currentInventoryDisplay.createVerticalScrollBar();
        currentInventoryDisplay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        currentInventoryDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return currentInventoryDisplay;
    }

    private void setUpMainMenuButtons(JPanel leftPanelOptions, JPanel middlePanelOptions) {
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");
        JButton viewHistoryButton = new JButton("View History");
        JButton saveButton = new JButton("Save Changes");
        JButton loadButton = new JButton("Load Changes");

        importButton.addActionListener(this);
        exportButton.addActionListener(this);
        viewHistoryButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

        leftPanelOptions.add(importButton);
        leftPanelOptions.add(exportButton);
        middlePanelOptions.add(viewHistoryButton);
        middlePanelOptions.add(saveButton);
        middlePanelOptions.add(loadButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionEvent = e.getActionCommand();
        Toolkit.getDefaultToolkit().beep();
        switch (actionEvent) {
            case ("Import"):
                importPackageDialog();
                break;
            case ("Export"):
                exportPackageDialog();
                break;
            case ("View History"):
                viewHistoryDialog();
                break;
            case ("Save Changes"):
                saveInventoryDialog();
                break;
            case ("Load Changes"):
                loadInventoryDialog();
                break;
            default: break;
        }
    }

    // EFFECTS: processes user input and directs user to desired operation
    private void processMainMenuOptionsInput(String inputValue) {
        if (inputValue.equals("add")) {
            doPackageImport();
        } else if (inputValue.equals("remove")) {
            doPackageExport();
        } else if (inputValue.equals("view")) {
            printWarehouseInventory();
        } else if (inputValue.equals("history")) {
            runApplicationViewHistoryMenu();
        } else if (inputValue.equals("save")) {
            saveWarehouseInventoryToFile();
        } else if (inputValue.equals("load")) {
            loadWarehouseInventoryFromFile();
        } else if (inputValue.equals("exit")) {
            exitApplication();
        } else {
            System.out.println("Invalid Entry Please Try Again\n");
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

    private void importPackageDialog() {
        int numberOfPackagesInInventoryBeforeImport = this.myWarehouse.getNumberPackagesInInventory();
        if (numberOfPackagesInInventoryBeforeImport < Warehouse.MAX_WAREHOUSE_CAPACITY) {
            JDialog importDialog = new JDialog(this, "Import Package");
            ImportEvent newImportEvent = new ImportEvent(this, this.myWarehouse, importDialog, communicatorText);
            importDialog.setLayout(new GridLayout(6,2));
            newImportEvent.implementFunctionality();
            importDialog.setSize(750, 300);
            importDialog.setLocationRelativeTo(null);
            importDialog.setVisible(true);
        } else {
            communicatorText.setText("\nWarehouse inventory is full... Cannot import package\n");
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

    private void exportPackageDialog() {
        int packagesInInventory = this.myWarehouse.getNumberPackagesInInventory();
        if (packagesInInventory == 0) {
            communicatorText.setText("Warehouse inventory has no packages to export");
        } else {
            JDialog exportDialog = new JDialog(this, "Export Package");
            ExportEvent newExportEvent = new ExportEvent(this, this.myWarehouse, exportDialog, communicatorText);
            exportDialog.setLayout(new GridLayout(3,2));
            newExportEvent.implementFunctionality();
            exportDialog.setSize(750, 150);
            exportDialog.setLocationRelativeTo(null);
            exportDialog.setVisible(true);
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
            System.out.println("There are currently no packages in your inventory"
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

    public void updateCurrentInventoryDisplay() {
        List<Package> largeSizedPackagesInventory = this.myWarehouse.getLargeSizedPackages();
        List<Package> mediumSizedPackagesInventory = this.myWarehouse.getMediumSizedPackages();
        List<Package> smallSizedPackagesInventory = this.myWarehouse.getSmallSizedPackages();
        resetCurrentInventoryDisplay();
        if (this.myWarehouse.getNumberPackagesInInventory() == 0) {
            currentInventory.setText("There are no items currently stored in the inventory");
        } else {
            addCurrentInventoryToDisplay(largeSizedPackagesInventory,
                    mediumSizedPackagesInventory,
                    smallSizedPackagesInventory);
        }
    }

    private void addCurrentInventoryToDisplay(List<Package> largeSizedPackagesInventory,
                                              List<Package> mediumSizedPackagesInventory,
                                              List<Package> smallSizedPackagesInventory) {
        currentInventory.setText("Large Sized Packages: \n");
        currentInventory.append("-------------------------------------\n");
        for (Package p : largeSizedPackagesInventory) {
            currentInventory.append(p.toString());
            currentInventory.append("\n\n");
        }
        currentInventory.append("\n\nMedium Sized Packages: \n");
        currentInventory.append("-------------------------------------\n");
        for (Package p : mediumSizedPackagesInventory) {
            currentInventory.append(p.toString());
            currentInventory.append("\n\n");
        }
        currentInventory.append("\n\nSmall Sized Packages: \n");
        currentInventory.append("-------------------------------------\n");
        for (Package p : smallSizedPackagesInventory) {
            currentInventory.append(p.toString());
            currentInventory.append("\n\n");
        }
    }

    private void resetCurrentInventoryDisplay() {
        currentInventory.removeAll();
        currentInventory.setEditable(false);
        currentInventory.setLineWrap(true);
    }

    private void viewHistoryDialog() {
        JDialog viewHistoryDialog = new JDialog(this, "View Transaction History");
        ViewHistoryEvent newViewHistoryEvent = new ViewHistoryEvent(this.myWarehouse, viewHistoryDialog);
        viewHistoryDialog.setLayout(new GridLayout(1,2));
        newViewHistoryEvent.organizeViewHistoryDialogContent();
        viewHistoryDialog.setSize(800, 700);
        viewHistoryDialog.setLocationRelativeTo(null);
        viewHistoryDialog.setVisible(true);
    }

    // EFFECTS: runs view history menu
    private void runApplicationViewHistoryMenu() {
        String inputValue;
        boolean keepOperating = true;

        while (keepOperating) {
            displayHistoryOptions();
            inputValue = userInput.next();
            keepOperating = processHistoryOptionsInput(inputValue);
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
    //          returns true if invalid input, otherwise returns false
    private boolean processHistoryOptionsInput(String inputValue) {
        switch (inputValue.toLowerCase()) {
            case "import":
                printImportHistory();
                return false;
            case "export":
                printExportHistory();
                return false;
            case "return":
                return false;
            default:
                System.out.println("Invalid entry, please try again \n");
                return true;
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
        String inputValue;
        boolean keepOperating = true;

        while (keepOperating) {
            displaySaveOptions();
            inputValue = userInput.next();
            inputValue = inputValue.toLowerCase();
            keepOperating = processSaveOptionsInput(inputValue);
        }
    }

    private void saveInventoryDialog() {
        JDialog saveDialog = new JDialog(this, "Save Inventory");
        SaveEvent saveEventFunction = new SaveEvent(this, this.myWarehouse, saveDialog, communicatorText);
        saveDialog.setLayout(new BorderLayout());
        saveEventFunction.implementFunctionality();
        saveDialog.setSize(400, 200);
        saveDialog.setLocationRelativeTo(null);
        saveDialog.setVisible(true);
    }

    // EFFECTS: prints save menu options user can perform
    private void displaySaveOptions() {
        System.out.println("\nPlease select a file you would like to save changes to: ");
        System.out.println("\tTo save changes to warehouseInventoryFile1 - type \"1\"");
        System.out.println("\tTo save changes to warehouseInventoryFile2 - type \"2\"");
        System.out.println("\tTo save changes to warehouseInventoryFile3 - type \"3\"");
        System.out.println("\tTo return to the main menu - type \"return\"\n");
    }

    // EFFECTS: processes user input and directs to desired operation in save menu
    private boolean processSaveOptionsInput(String inputValue) {
        try {
            if (inputValue.equals("1")) {
                jsonWriter = new JsonWriter(SOURCE_FILE_1);
                processSaveSequence(SOURCE_FILE_1);
                return false;
            } else if (inputValue.equals("2")) {
                jsonWriter = new JsonWriter(SOURCE_FILE_2);
                processSaveSequence(SOURCE_FILE_2);
                return false;
            } else if (inputValue.equals("3")) {
                jsonWriter = new JsonWriter(SOURCE_FILE_3);
                processSaveSequence(SOURCE_FILE_3);
                return false;
            } else if (inputValue.equals("return")) {
                return false;
            } else {
                System.out.println("Invalid entry, please try again \n");
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save to specified source file...");
            return false;
        }
    }

    // EFFECTS: helper method for processSaveOptionsInput:
    //              decides whether to rename warehouse
    //              saves warehouse to file
    //              prints confirmation letter
    private void processSaveSequence(String sourceFile) throws FileNotFoundException {
        nameWarehouse();
        jsonWriter.saveToFile(myWarehouse);
        System.out.println("Warehouse inventory has been saved to " + sourceFile);
    }

    // MODIFIES: this
    // EFFECTS: gives myWarehouse a name
    private void nameWarehouse() {
        String inputValue;
        boolean keepOperating = true;

        while (keepOperating) {
            displayNameWarehouseOptions();
            inputValue = userInput.next();
            keepOperating = processNameWarehouseInput(inputValue);
        }
    }

    // EFFECTS: prints name warehouse options that the user can perform
    private void displayNameWarehouseOptions() {
        System.out.println("\nThe current name of your warehouse is: " + myWarehouse.getWarehouseName());
        System.out.println("Would you like to change names?");
        System.out.println("\tTo keep current warehouse name - type \"keep\"");
        System.out.println("\tTo change warehouse name - type \"change\"");
    }

    // MODIFIES: this
    // EFFECTS: processes user input and directs to desired operation in name warehouse menu
    private boolean processNameWarehouseInput(String inputValue) {
        String input;
        switch (inputValue.toLowerCase()) {
            case "keep":
                System.out.println("The current name: " + myWarehouse.getWarehouseName() + "has been kept");
                return false;
            case "change":
                System.out.println("Please type in new warehouse name");
                System.out.println("All spaces must be replaced with underscore");
                System.out.println("Ex. My_Warehouse");
                input = userInput.next();
                myWarehouse.setWarehouseName(input);
                System.out.println("New Warehouse name has been set to: " + myWarehouse.getWarehouseName());
                return false;
            default:
                System.out.println("Invalid entry, please try again \n");
                return true;
        }
    }

    // EFFECTS: loads Warehouse inventory from file
    private void loadWarehouseInventoryFromFile() {
        String inputValue;
        boolean keepOperating = true;

        while (keepOperating) {
            displayLoadOptions();
            inputValue = userInput.next();
            inputValue = inputValue.toLowerCase();
            keepOperating = processLoadOptionsInput(inputValue);
        }
    }

    private void loadInventoryDialog() {
        JDialog loadDialog = new JDialog(this, "Load Inventory");
        LoadEvent loadEventFunction = new LoadEvent(this, this.myWarehouse, loadDialog, communicatorText);
        loadDialog.setLayout(new BorderLayout());
        loadEventFunction.implementFunctionality();
        loadDialog.setSize(400, 250);
        loadDialog.setLocationRelativeTo(null);
        loadDialog.setVisible(true);
    }

    public void reloadMyWarehouseFromFile(Warehouse warehouse) {
        this.myWarehouse = warehouse;
    }

    // EFFECTS: prints load menu options user can perform
    private void displayLoadOptions() {
        System.out.println("\nPlease select a file you would like to load warehouse inventory from: ");
        System.out.println("\tTo load inventory from warehouseInventoryFile1 - type \"1\"");
        System.out.println("\tTo load inventory from warehouseInventoryFile2 - type \"2\"");
        System.out.println("\tTo load inventory from warehouseInventoryFile3 - type \"3\"");
        System.out.println("\tTo return to the main menu - type \"return\"\n");
    }

    // EFFECTS: processes user input and directs to desired operation in load options menu
    private boolean processLoadOptionsInput(String inputValue) {
        try {
            if (inputValue.equals("1")) {
                jsonReader = new JsonReader(SOURCE_FILE_1);
                processLoadSequence(SOURCE_FILE_1);
                return false;
            } else if (inputValue.equals("2")) {
                jsonReader = new JsonReader(SOURCE_FILE_2);
                processLoadSequence(SOURCE_FILE_2);
                return false;
            } else if (inputValue.equals("3")) {
                jsonReader = new JsonReader(SOURCE_FILE_3);
                processLoadSequence(SOURCE_FILE_3);
                return false;
            } else if (inputValue.equals("return")) {
                return false;
            } else {
                System.out.println("Invalid entry, please try again \n");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Cannot read from specified source file...");
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads warehouse from file and prints confirmation statement
    private void processLoadSequence(String sourceFile) throws IOException {
        myWarehouse = jsonReader.retrieveSavedWarehouseData();
        System.out.println("Warehouse inventory previously saved in " + sourceFile + " has been loaded");
    }

    // MODIFIES: this
    // EFFECTS: signals to warehouse application to terminate application
    private void exitApplication() {
        System.out.println("\nGoodbye!");
        this.isFinished = true;
    }
}
