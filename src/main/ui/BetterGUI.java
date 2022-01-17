package ui;

import model.Collection;
import model.Event;
import model.EventLog;
import model.Memory;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BetterGUI {

    private static boolean useSystemLookAndFeel = false;
    private static Collection january = new Collection("january");
    private static Collection february = new Collection("february");
    private static Collection march = new Collection("march");
    private static Collection april = new Collection("april");
    private static Collection mayc = new Collection("may");
    private static Collection june = new Collection("june");
    private static Collection july = new Collection("july");
    private static Collection august = new Collection("august");
    private static Collection september = new Collection("september");
    private static Collection october = new Collection("october");
    private static Collection november = new Collection("november");
    private static Collection december = new Collection("december");


    //EFFECTS:
    public static class MonthsTree extends JPanel {


        protected JTree tree;
        protected DefaultMutableTreeNode rootNode;
        protected DefaultTreeModel treeModel;
        private static final String addString = "Add";
        private static final String saveString = "Save";
        private static final String loadString = "Load";
        private JTextField songName;
        DefaultMutableTreeNode jan = new DefaultMutableTreeNode("January");
        DefaultMutableTreeNode feb = new DefaultMutableTreeNode("February");
        DefaultMutableTreeNode mar = new DefaultMutableTreeNode("March");
        DefaultMutableTreeNode apr = new DefaultMutableTreeNode("April");
        DefaultMutableTreeNode may = new DefaultMutableTreeNode("May");
        DefaultMutableTreeNode jun = new DefaultMutableTreeNode("June");
        DefaultMutableTreeNode jul = new DefaultMutableTreeNode("July");
        DefaultMutableTreeNode aug = new DefaultMutableTreeNode("August");
        DefaultMutableTreeNode sep = new DefaultMutableTreeNode("September");
        DefaultMutableTreeNode oct = new DefaultMutableTreeNode("October");
        DefaultMutableTreeNode nov = new DefaultMutableTreeNode("November");
        DefaultMutableTreeNode dec = new DefaultMutableTreeNode("December");

        //EFFECTS: creates JPane displaying the list of songs
        public MonthsTree() {
            super(new GridLayout(1, 0));
            rootNode = new DefaultMutableTreeNode("Months");
            treeModel = new DefaultTreeModel(rootNode);
            theTree(rootNode);

            tree = new JTree(treeModel);
            tree.setFont(new Font("Arial", Font.PLAIN, 30));
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            JScrollPane monthsView = new JScrollPane(tree);
            monthsView.setMinimumSize(new Dimension(200, 200));
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setTopComponent(monthsView);
            splitPane.setBottomComponent(buttonPane());
            add(splitPane);

        }

        public DefaultMutableTreeNode theTree(DefaultMutableTreeNode dmtn) {
            dmtn.add(jan);
            dmtn.add(feb);
            dmtn.add(mar);
            dmtn.add(apr);
            dmtn.add(may);
            dmtn.add(jun);
            dmtn.add(jul);
            dmtn.add(aug);
            dmtn.add(sep);
            dmtn.add(oct);
            dmtn.add(nov);
            dmtn.add(dec);
            return dmtn;
        }

        //EFFECTS: creates the button allowing users to add songs
        public JButton addButton() {
            JButton addButton = new JButton(addString);
            BetterGUI.MonthsTree.AddListener addListener = new BetterGUI.MonthsTree.AddListener(addButton);
            addButton.setActionCommand(addString);
            addButton.addActionListener(addListener);
            addButton.setFont(new Font("Arial", Font.PLAIN, 30));
            addButton.setEnabled(false);

            songName = new JTextField(30);
            songName.setFont(new Font("Arial", Font.PLAIN, 25));
            songName.addActionListener(addListener);
            songName.getDocument().addDocumentListener(addListener);
            return addButton;
        }

        //EFFECTS: creates the panel of buttons
        public JPanel buttonPane() {
            JButton saveButton = new JButton(saveString);
            saveButton.setActionCommand(saveString);
            saveButton.addActionListener(new BetterGUI.MonthsTree.SaveListener(saveButton));
            saveButton.setFont(new Font("Arial", Font.PLAIN, 30));

            JButton loadButton = new JButton(loadString);
            loadButton.setActionCommand(loadString);
            loadButton.addActionListener(new BetterGUI.MonthsTree.LoadListener());
            loadButton.setFont(new Font("Arial", Font.PLAIN, 30));

            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
            buttonPane.add(addButton());
            buttonPane.add(songName);
            buttonPane.add(saveButton);
            buttonPane.add(loadButton);
            buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

            return buttonPane;
        }


        private class AddListener implements ActionListener, DocumentListener {
            private boolean alreadyEnabled = false;
            private JButton button;


            //EFFECTS: adds a listener for the add button
            private AddListener(JButton button) {
                this.button = button;
            }

            //EFFECTS: saves input as memory and adds it into the tree
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = songName.getText();
                addObject(name);

                songName.requestFocusInWindow();
                songName.setText("");
                addToMonth(name);
            }

            @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
            public void addToMonth(String name) {
                Memory mem = new Memory(name, "");
                String month = tree.getSelectionPath().getLastPathComponent().toString();
                if (month.equals("January")) {
                    january.addMemory(mem);
                } else if (month.equals("February")) {
                    february.addMemory(mem);
                } else if (month.equals("March")) {
                    march.addMemory(mem);
                } else if (month.equals("April")) {
                    april.addMemory(mem);
                } else if (month.equals("May")) {
                    mayc.addMemory(mem);
                } else if (month.equals("June")) {
                    june.addMemory(mem);
                } else if (month.equals("July")) {
                    july.addMemory(mem);
                } else if (month.equals("August")) {
                    august.addMemory(mem);
                } else if (month.equals("September")) {
                    september.addMemory(mem);
                } else if (month.equals("October")) {
                    october.addMemory(mem);
                } else if (month.equals("November")) {
                    november.addMemory(mem);
                } else if (month.equals("December")) {
                    december.addMemory(mem);
                }
            }

            public DefaultMutableTreeNode addObject(Object child) {
                DefaultMutableTreeNode parentNode = null;
                TreePath parentPath = tree.getSelectionPath();

                if (parentPath == null) {
                    parentNode = null;
                } else {
                    parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
                }

                return addObject(parentNode, child, true);

            }

            public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                                    Object child,
                                                    boolean shouldBeVisible) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
                treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

                if (shouldBeVisible) {
                    tree.scrollPathToVisible(new TreePath(childNode.getPath()));
                }
                return childNode;
            }


            //EFFECTS: enables the button
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            //EFFECTS: handles the text entered into the add text box
            @Override
            public void removeUpdate(DocumentEvent e) {
                handleEmptyTextField(e);
            }

            //EFFECTS: if text field is empty, button is disabled
            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!handleEmptyTextField(e)) {
                    enableButton();
                }

            }


            //EFFECTS: enables the button
            //MODIFIES: button
            private void enableButton() {
                if (!alreadyEnabled) {
                    button.setEnabled(true);
                }
            }

            //EFFECTS: If the text field is empty, button is disabled
            //MODIFIES: button
            private boolean handleEmptyTextField(DocumentEvent e) {
                if (e.getDocument().getLength() <= 0) {
                    button.setEnabled(false);
                    alreadyEnabled = false;
                    return true;
                }
                return false;
            }
        }


        private class SaveListener implements ActionListener {
            private boolean alreadyEnabled = false;
            private JButton button;
            private static final String IMAGE = "./data/images/pic.JPG";
            private static final String JAN = "./data/data/januarymonth.json";
            private JsonWriter janWriter =  new JsonWriter(JAN);
            private static final String FEB = "./data/data/february.json";
            private JsonWriter febWriter = new JsonWriter(FEB);
            private static final String MAR = "./data/data/march.json";
            private JsonWriter marWriter = new JsonWriter(MAR);
            private static final String APR = "./data/data/april.json";
            private JsonWriter aprWriter = new JsonWriter(APR);
            private static final String MAY = "./data/data/may.json";
            private JsonWriter mayWriter = new JsonWriter(MAY);
            private static final String JUN = "./data/data/june.json";
            private JsonWriter junWriter = new JsonWriter(JUN);
            private static final String JUL = "./data/data/july.json";
            private JsonWriter julWriter = new JsonWriter(JUL);
            private static final String AUG = "./data/data/august.json";
            private JsonWriter augWriter = new JsonWriter(AUG);
            private static final String SEP = "./data/data/september.json";
            private JsonWriter sepWriter = new JsonWriter(SEP);
            private static final String OCT = "./data/data/october.json";
            private JsonWriter octWriter = new JsonWriter(OCT);
            private static final String NOV = "./data/data/november.json";
            private JsonWriter novWriter = new JsonWriter(NOV);
            private static final String DEC = "./data/data/december.json";
            private JsonWriter decWriter = new JsonWriter(DEC);


            private SaveListener(JButton button) {
                this.button = button;
            }

            //EFFECTS: If save button is clicked, current state is saved
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMonth(january, janWriter);
                saveMonth(february, febWriter);
                saveMonth(march, marWriter);
                saveMonth(april, aprWriter);
                saveMonth(mayc, mayWriter);
                saveMonth(june, junWriter);
                saveMonth(july, julWriter);
                saveMonth(august, augWriter);
                saveMonth(september, sepWriter);
                saveMonth(october, octWriter);
                saveMonth(november, novWriter);
                saveMonth(december, decWriter);

            }

            private void saveMonth(Collection month, JsonWriter writer) {
                try {
                    writer.open();
                    writer.write(month);
                    writer.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }
            }


            //EFFECTS: creates popup displaying error message
            public Popup errorPop() {
                JFrame f = new JFrame("error");
                JLabel l = new JLabel("error");
                f.setSize(100, 100);
                PopupFactory pf = new PopupFactory();
                JPanel p1 = new JPanel();
                p1.add(l);
                Popup p = pf.getPopup(f, p1, 180, 100);
                return p;
            }
        }



        private class LoadListener implements ActionListener {

            private static final String JAN = "./data/data/januarymonth.json";
            private JsonReader janReader =  new JsonReader(JAN);
            private static final String FEB = "./data/data/february.json";
            private JsonReader febReader = new JsonReader(FEB);
            private static final String MAR = "./data/data/march.json";
            private JsonReader marReader = new JsonReader(MAR);
            private static final String APR = "./data/data/april.json";
            private JsonReader aprReader = new JsonReader(APR);
            private static final String MAY = "./data/data/may.json";
            private JsonReader mayReader = new JsonReader(MAY);
            private static final String JUN = "./data/data/june.json";
            private JsonReader junReader = new JsonReader(JUN);
            private static final String JUL = "./data/data/july.json";
            private JsonReader julReader = new JsonReader(JUL);
            private static final String AUG = "./data/data/august.json";
            private JsonReader augReader = new JsonReader(AUG);
            private static final String SEP = "./data/data/september.json";
            private JsonReader sepReader = new JsonReader(SEP);
            private static final String OCT = "./data/data/october.json";
            private JsonReader octReader = new JsonReader(OCT);
            private static final String NOV = "./data/data/november.json";
            private JsonReader novReader = new JsonReader(NOV);
            private static final String DEC = "./data/data/december.json";
            private JsonReader decReader = new JsonReader(DEC);

            //EFFECTS: if load button is clicked, load data onto application
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMonth(january, janReader, jan);
                loadMonth(february, febReader, feb);
                loadMonth(march, marReader, mar);
                loadMonth(mayc, mayReader, may);
                loadMonth(april, aprReader, apr);
                loadMonth(june, junReader, jun);
                loadMonth(july, julReader, jul);
                loadMonth(august, augReader, aug);
                loadMonth(september, sepReader, sep);
                loadMonth(october, octReader, oct);
                loadMonth(november, novReader, nov);
                loadMonth(december, decReader, dec);

            }

            private void loadMonth(Collection month, JsonReader reader, DefaultMutableTreeNode node) {
                try {
                    month = reader.read();
                    System.out.println("Loaded");
                    for (Memory m : month) {
                        node.add(new DefaultMutableTreeNode(m.getSongName()));
                    }

                } catch (IOException e) {
                    errorPop().show();
                }
            }


            //EFFECTS: creates popup displaying error message
            public Popup errorPop() {
                JFrame f = new JFrame("error");
                JLabel l = new JLabel("error");
                f.setSize(100, 100);
                PopupFactory pf = new PopupFactory();
                JPanel p1 = new JPanel();
                p1.setBackground(Color.blue);
                p1.add(l);
                return pf.getPopup(f, p1, 180, 100);
            }
        }

    }




        //EFFECTS: creates the GUI
    private static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
        JFrame frame = new JFrame("Months Tree");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString());
                }
                System.exit(0);
            }
        });

        frame.add(new BetterGUI.MonthsTree());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    createAndShowGUI();
                }
        });
    }
}


