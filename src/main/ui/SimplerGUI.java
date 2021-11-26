package ui;


import model.Collection;
import model.Event;
import model.EventLog;
import model.Memory;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

//this class references code from
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TreeDemoProject/src/components/TreeDemo.java

//this class references code from
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

//this class references code from
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextFieldDemoProject/src/components/TextFieldDemo.java



public class SimplerGUI extends JFrame {

    private static boolean useSystemLookAndFeel = false;
    private static Collection january = new Collection("january");


    //EFFECTS:
    public static class MonthsTree extends JPanel implements TreeSelectionListener {


        private JEditorPane pane;
        DefaultMutableTreeNode title = new DefaultMutableTreeNode("Songs");
        private JTree tree;
        private boolean playWithLineStyle = false;
        private String lineStyle = "Horizontal";
        private static final String addString = "Add";
        private static final String saveString = "Save";
        private static final String loadString = "Load";
        private JTextField songName;

        //EFFECTS: creates JPane displaying the list of songs
        public MonthsTree() {
            super(new GridLayout(1, 0));
            DefaultMutableTreeNode month = new DefaultMutableTreeNode("Current Songs");
            title.add(month);
            tree = new JTree(title);
            tree.setFont(new Font("Arial", Font.PLAIN, 30));
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.addTreeSelectionListener(this);

            JScrollPane monthsView = new JScrollPane(tree);

            monthsView.setMinimumSize(new Dimension(200, 200));

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setTopComponent(monthsView);
            splitPane.setBottomComponent(buttonPane());
            add(splitPane);
        }

        //EFFECTS: creates the button allowing users to add songs
        public JButton addButton() {
            JButton addButton = new JButton(addString);
            SimplerGUI.MonthsTree.AddListener addListener = new SimplerGUI.MonthsTree.AddListener(addButton);
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
            saveButton.addActionListener(new SimplerGUI.MonthsTree.SaveListener(saveButton));
            saveButton.setFont(new Font("Arial", Font.PLAIN, 30));

            JButton loadButton = new JButton(loadString);
            loadButton.setActionCommand(loadString);
            loadButton.addActionListener(new SimplerGUI.MonthsTree.LoadListener());
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

        //EFFECTS:
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        }


        private class AddListener implements ActionListener, DocumentListener, TreeSelectionListener {
            private boolean alreadyEnabled = false;
            private JButton button;


            //EFFECTS: adds a listener for the add button
            private AddListener(JButton button) {
                this.button = button;
            }

            //EFFECTS: saves input as memory and adds it into the tree
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                String name = songName.getText();
                node.add(new DefaultMutableTreeNode(name));

                songName.requestFocusInWindow();
                songName.setText("");
                january.addMemory(new Memory(name));
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

            //EFFECTS: creates a popup displaying an error message
            public Popup errorPop() {
                JFrame f = new JFrame("error");
                JLabel l = new JLabel("error");
                f.setSize(100, 100);
                PopupFactory pf = new PopupFactory();
                JPanel p1 = new JPanel();
                p1.setBackground(Color.blue);
                p1.add(l);
                Popup p = pf.getPopup(f, p1, 180, 100);
                return p;
            }

            @Override
            public void valueChanged(TreeSelectionEvent e) {

            }
        }

        private class SaveListener implements ActionListener {
            private boolean alreadyEnabled = false;
            private JButton button;

            private static final String IMAGE = "./data/images/pic.JPG";

            private static final String JSON_STORE = "./data/data/januarymonth.json";

            private JsonWriter janWriter =  new JsonWriter(JSON_STORE);

            private SaveListener(JButton button) {

                this.button = button;
            }

            //EFFECTS: If save button is clicked, current state is saved
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJanuary();

            }

            //EFFECTS: saves the memory into the json file
            //MODIFIES: januarymonth.json
            private void saveJanuary() {
                try {
                    janWriter.open();
                    janWriter.write(january);
                    janWriter.close();
                    savedPop().setVisible(true);
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            //EFFECTS: creates a popup displaying a message for when the data is saved succesfully
            public JDialog savedPop() {
                JPanel p1 = new JPanel();
                ImageIcon i = new ImageIcon(IMAGE);
                p1.add(new JLabel(i));
                JDialog p = new JDialog();
                p.add(p1);
                p.setMinimumSize(new Dimension(900, 1500));
                return p;
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
            private static final String IMAGE = "./data/images/image.jpg";

            private static final String JSON_STORE = "./data/data/januarymonth.json";
            private JsonReader janReader =  new JsonReader(JSON_STORE);

            //EFFECTS: if load button is clicked, load data onto application
            @Override
            public void actionPerformed(ActionEvent e) {
                loadJanuary();

            }

            // EFFECTS: loads the memory from the json file and adds all the memories into a tree in a new popup
            //MODIFIES: january, tree
            private void loadJanuary() {
                try {
                    january = janReader.read();
                    System.out.println("Loaded");
                    DefaultMutableTreeNode previous = new DefaultMutableTreeNode("Previously Added");

                    tree = new JTree(previous);
                    for (Memory m :january) {
                        previous.add(new DefaultMutableTreeNode(m.getSongName()));
                    }
                    previousPop(tree).setVisible(true);
                    System.out.println("tree done");

                } catch (IOException e) {
                    errorPop().show();
                }

            }

            //EFFECTS: creates popup displaying the songs added in previous sessions
            public JDialog previousPop(JTree t) {
                t.setFont(new Font("Arial", Font.PLAIN, 25));
                JLabel l = new JLabel("Previously Added");
                JPanel p1 = new JPanel();
                ImageIcon i = new ImageIcon(IMAGE);
                p1.add(new JLabel(i));
                JScrollPane sp = new JScrollPane(t);
                p1.add(sp);
                JDialog p = new JDialog();
                p.add(p1);
                p.setMinimumSize(new Dimension(1000, 700));
                return p;
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
                Popup p = pf.getPopup(f, p1, 180, 100);
                return p;
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

        frame.add(new SimplerGUI.MonthsTree());

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


