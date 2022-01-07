package ui;

import model.Package;
import model.Warehouse;
import ui.components.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// Warehouse management application
public class WarehouseApplication extends JFrame implements ActionListener, Observer {
    private static final int GUI_WIDTH = 1300;
    private static final int GUI_HEIGHT = 1000;
    private static final Color GUI_BACKGROUND_COLOUR = new Color(250, 230, 190);
    private static final int TIMER_REFRESH = 1000;

    private Warehouse myWarehouse = new Warehouse("My Warehouse");
    private JTextArea currentInventory;
    private JLabel communicatorText;

    // EFFECTS: instantiates WarehouseApplication
    public WarehouseApplication() {
        initializeFrame();
    }

    // MODIFIES: this
    // EFFECTS: initializes the warehouse GUI by setting up JFrame settings and adding features to JFrame
    private void initializeFrame() {
        // initialize general Frame Settings
        setupGeneralFrameSettings();

        // add Components to Frame
        setupFrameComponents();

        // sets frame to visible
        setVisible(true);
    }

    // EFFECTS: setup general Frame settings
    private void setupGeneralFrameSettings() {
        setTitle("Warehouse Management");
        setLayout(new BorderLayout());
        setUpFrameIcon();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(GUI_WIDTH,GUI_HEIGHT);
        setLocationRelativeTo(null); // sets the location of the window to the center of screen
    }


    // EFFECTS: sets up icon if it is possible
    private void setUpFrameIcon() {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("./data/warehouse.png"));
            setIconImage(icon);
        } catch (IOException e) {
            // if Icon cannot be set up it doesn't matter :)
        }
    }

    // EFFECTS: sets up Frame components
    private void setupFrameComponents() {
        // create top, middle and bottom JPanels for the main JFrame
        JPanel mainUpperPanel = new JPanel();
        JPanel mainLeftPanel = new JPanel();
        JPanel mainMiddlePanel = new JPanel();
        JPanel mainBottomPanel = new JPanel();

        // setup the JPanels
        setupMainUpperPanel(mainUpperPanel);
        setupMainLeftPanel(mainLeftPanel);
        setupMainMiddlePanel(mainMiddlePanel);
        setupMainBottomPanel(mainBottomPanel);
        // add JPanels to JFrame
        add(mainUpperPanel, BorderLayout.NORTH);
        add(mainLeftPanel, BorderLayout.WEST);
        add(mainMiddlePanel, BorderLayout.CENTER);
        add(mainBottomPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this, mainUpperPanel
    // EFFECTS: generates a welcome statement on the main JFrame window which changes depending on time of day
    private void setupMainUpperPanel(JPanel mainUpperPanel) {
        mainUpperPanel.setBackground(GUI_BACKGROUND_COLOUR);
        JLabel welcomeStatement = new JLabel();
        mainUpperPanel.add(welcomeStatement);
        DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm");

        Timer timer = new Timer(TIMER_REFRESH, ae -> {
            String currentDateAndTime = newDateFormat.format(LocalDateTime.now());
            int timeOfDay = LocalTime.now().getHour();

            if (0 <= timeOfDay && timeOfDay < 12) {
                welcomeStatement.setText("Good Morning, Today is " + currentDateAndTime + " AM");
            } else if (12 <= timeOfDay && timeOfDay < 18) {
                welcomeStatement.setText("Good Afternoon, Today is " + currentDateAndTime + " PM");
            } else {
                welcomeStatement.setText("Good Evening, Today is " + currentDateAndTime + " PM");
            }
        });

        timer.start();
    }

    private void setupMainLeftPanel(JPanel mainLeftPanel) {
        mainLeftPanel.setLayout(new GridLayout(5,1));
        mainLeftPanel.setBackground(GUI_BACKGROUND_COLOUR);
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

        mainLeftPanel.add(importButton);
        mainLeftPanel.add(exportButton);
        mainLeftPanel.add(viewHistoryButton);
        mainLeftPanel.add(saveButton);
        mainLeftPanel.add(loadButton);
    }

    // MODIFIES: this, mainMiddlePanel
    // EFFECTS: adds containers that will house main menu buttons/options to the main JFrame window
    private void setupMainMiddlePanel(JPanel mainMiddlePanel) {
        mainMiddlePanel.setLayout(new GridLayout(1,1));
        this.currentInventory = new JTextArea("There are no items currently stored in the inventory");
        this.currentInventory.setEditable(false);
        this.currentInventory.setLineWrap(true);
        this.currentInventory.setWrapStyleWord(true);

        JScrollPane currentInventoryDisplay = new JScrollPane(this.currentInventory);
        currentInventoryDisplay.createHorizontalScrollBar();
        currentInventoryDisplay.createVerticalScrollBar();
        currentInventoryDisplay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        currentInventoryDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainMiddlePanel.add(currentInventoryDisplay);
    }

    private void setupMainBottomPanel(JPanel mainBottomPanel) {
        mainBottomPanel.setBackground(GUI_BACKGROUND_COLOUR);
        this.communicatorText = new JLabel();
        mainBottomPanel.add(this.communicatorText);
    }


    // EFFECTS: directs the button pressed on main JFrame window to its operation
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionEvent = e.getActionCommand();

        switch (actionEvent) {
            case ("Import"):
                new ImportDialog(this, this.communicatorText);
                break;
            case ("Export"):
//                new ExportDialog(this, this.communicatorText).generateExportPackageDialog();
                break;
            case ("View History"):
//                new ViewHistoryDialog(this).generateViewHistoryDialog();
                break;
            case ("Save Changes"):
//                new SaveDialog(this, communicatorText).generateSaveInventoryDialog();
                break;
            case ("Load Changes"):
//                new LoadDialog(this, communicatorText).generateLoadInventoryDialog();
                break;
            default: break;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the current inventory display
    public void updateCurrentInventoryDisplay() {
//        List<Package> largeSizedPackagesInventory = this.myWarehouse.getLargeSizedPackages();
//        List<Package> mediumSizedPackagesInventory = this.myWarehouse.getMediumSizedPackages();
//        List<Package> smallSizedPackagesInventory = this.myWarehouse.getSmallSizedPackages();
//        if (this.myWarehouse.getNumberPackagesInInventory() == 0) {
//            currentInventory.setText("There are no items currently stored in the inventory");
//        } else {
//            addCurrentInventoryToDisplay(largeSizedPackagesInventory,
//                    mediumSizedPackagesInventory,
//                    smallSizedPackagesInventory);
//        }
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

    public Warehouse getWarehouse() {
        return this.myWarehouse;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
