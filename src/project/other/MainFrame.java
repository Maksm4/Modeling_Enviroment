package project.other;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class MainFrame {
    private JFrame mainFrame;
    private JList<String> leftList;
    private JList<String> rightList;
    private JTable table;

    private JPanel leftPanelMain;

    public MainFrame() {
        initializeMainFrame();
        configureLeftPanelMain();
        configureLeftPanel();
        configureRightPanel();
        configureRightPanelMain();
        configureRunModelButton();
        mainFrame.setVisible(true);
        mainFrame.pack();
    }

    private void initializeMainFrame() {
        this.mainFrame = new JFrame();
        this.mainFrame.setPreferredSize(new Dimension(1000, 500));
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLayout(new BorderLayout());
    }

    private void configureLeftPanelMain() {
        this.leftPanelMain = new JPanel();
        this.leftPanelMain.setLayout(new BorderLayout());
        this.leftPanelMain.setPreferredSize(new Dimension(300, 500));
        mainFrame.add(this.leftPanelMain, BorderLayout.WEST);
    }

    private void configureLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(140, 400));
        this.leftList = createJList(getModelNames());
        this.leftList.setPreferredSize(new Dimension(140, 400));
        leftPanel.add(this.leftList);
        this.leftPanelMain.add(leftPanel, BorderLayout.WEST);
    }

    private void configureRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(140, 400));
        this.rightList = createJList(getFileNames());
        this.rightList.setPreferredSize(new Dimension(140, 400));
        rightPanel.add(this.rightList);
        this.leftPanelMain.add(rightPanel, BorderLayout.EAST);
    }

    private String[] getModelNames() {
        String modelPath = "src/project/other/Models";
        File dirModel = new File(modelPath);
        File[] models = dirModel.listFiles();
        String[] modelNames = new String[models.length];

        for (int i = 0; i < models.length; i++) {
            if (!models[i].getName().equals("AbstractModel.java")) {
                String tmp = models[i].getName();
                int indexOfDot = tmp.indexOf(".");
                modelNames[i] = tmp.substring(0, indexOfDot);
            }
        }
        return modelNames;
    }

    private JList<String> createJList(String[] items) {
        JList<String> list = new JList<>(items);
        list.setPreferredSize(new Dimension(140, 400));
        return list;
    }

    private String[] getFileNames() {
        String dataPath = "C:\\Users\\Modeling\\data\\";
        File dir = new File(dataPath);
        File[] dataFiles = dir.listFiles();
        String[] fileNames = new String[dataFiles.length];

        for (int i = 0; i < dataFiles.length; i++) {
            fileNames[i] = dataFiles[i].getName();
        }
        return fileNames;
    }

    private void configureRightPanelMain() {
        JPanel rightPanelMain = new JPanel();
        rightPanelMain.setLayout(new BorderLayout());
        rightPanelMain.setPreferredSize(new Dimension(650, 400));
        this.table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        this.table.setDefaultRenderer(Object.class, new ModelCellRenderer());
        this.table.setModel(tableModel);
        this.table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.table);
        rightPanelMain.add(scrollPane, BorderLayout.CENTER);
        this.mainFrame.add(rightPanelMain, BorderLayout.EAST);
    }

    private void configureRunModelButton() {
        JButton runModelButton = new JButton();
        runModelButton.setPreferredSize(new Dimension(200, 70));
        runModelButton.setText("RUN MODEL");
        runModelButton.addActionListener(e -> {
            String chosenModel = this.leftList.getSelectedValue();
            String chosenData = this.rightList.getSelectedValue();
            String dataFilePath = "C:\\Users\\Modeling\\data\\" + chosenData;

            try {
                Controller controller = new Controller("project.other.Models." + chosenModel);
                controller.readDataFrom(dataFilePath).runModel();
                String dataToPopulate = controller.getResultsAsTsv();
                updateTable((DefaultTableModel) this.table.getModel(), dataToPopulate);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(300, 100));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(runModelButton);
        buttonPanel.add(Box.createHorizontalGlue());
        this.mainFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateTable(DefaultTableModel tableModel, String data) {

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        String[] lines = data.split("\n");
        String[] columns1 = lines[0].split("\\t");

        tableModel.setColumnIdentifiers(columns1);


        for (int i = 1; i < lines.length; i++) {
            String[] row = lines[i].split("\\t");
            tableModel.addRow(row);
        }


    }


}
