package ui;

import model.Package;
import model.Warehouse;
import ui.operations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Warehouse management application
public class WarehouseApplication extends JFrame implements ActionListener {
    private static final int GUI_WIDTH = 1300;
    private static final int GUI_HEIGHT = 1000;
    private static final Color GUI_BACKGROUND_COLOUR = new Color(184, 216, 232);
    private static final int TIMER_REFRESH = 1000;

    private Warehouse myWarehouse;
    private JTextArea currentInventory;
    private JLabel communicatorText;

    // EFFECTS: instantiates WarehouseApplication
    public WarehouseApplication() {
        initializeApplication();
        initializeGUI();
    }

    // MODIFIES: this
    // EFFECTS: creates warehouse and scanner instance
    //          sets the warehouse's is finished status to false (used to terminate application)
    //          sets package id count to 0
    private void initializeApplication() {
        myWarehouse = new Warehouse("My Warehouse");
    }

    // MODIFIES: this
    // EFFECTS: initializes the warehouse GUI by creating JFrame window and implementing its features
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

        addWelcomeStatement(mainUpperPanel);
        addMainMenuOptions(mainMiddlePanel);
        setVisible(true);
    }

    // MODIFIES: this, mainUpperPanel
    // EFFECTS: generates a welcome statement on the main JFrame window which changes depending on time of day
    private void addWelcomeStatement(JPanel mainUpperPanel) {
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

    // MODIFIES: this, mainMiddlePanel
    // EFFECTS: adds containers that will house main menu buttons/options to the main JFrame window
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

    // MODIFIES: this
    // EFFECTS: constructs a display which shows the current inventory's packages' details
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

    // MODIFIES: this, leftPanelOptions, middlePanelOptions
    // EFFECTS: adds buttons to panel and implements its functionality
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

    // EFFECTS: directs the button pressed on main JFrame window to its operation
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionEvent = e.getActionCommand();
        Toolkit.getDefaultToolkit().beep();
        switch (actionEvent) {
            case ("Import"):
                new ImportEvent(this, this.myWarehouse, communicatorText).generateImportPackageDialog();
                break;
            case ("Export"):
                new ExportEvent(this, this.myWarehouse, communicatorText).generateExportPackageDialog();
                break;
            case ("View History"):
                new ViewHistoryEvent(this, this.myWarehouse).generateViewHistoryDialog();
                break;
            case ("Save Changes"):
                new SaveEvent(this, this.myWarehouse, communicatorText).generateSaveInventoryDialog();
                break;
            case ("Load Changes"):
                new LoadEvent(this, this.myWarehouse, communicatorText).generateLoadInventoryDialog();
                break;
            default: break;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the current inventory display
    public void updateCurrentInventoryDisplay() {
        List<Package> largeSizedPackagesInventory = this.myWarehouse.getLargeSizedPackages();
        List<Package> mediumSizedPackagesInventory = this.myWarehouse.getMediumSizedPackages();
        List<Package> smallSizedPackagesInventory = this.myWarehouse.getSmallSizedPackages();
        if (this.myWarehouse.getNumberPackagesInInventory() == 0) {
            currentInventory.setText("There are no items currently stored in the inventory");
        } else {
            addCurrentInventoryToDisplay(largeSizedPackagesInventory,
                    mediumSizedPackagesInventory,
                    smallSizedPackagesInventory);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds current inventory's package details to main JFrameWindow display
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

    // MODIFIES: this
    // EFFECTS: reloads warehouse details from file
    public void reloadMyWarehouseFromFile(Warehouse warehouse) {
        this.myWarehouse = warehouse;
    }

}
