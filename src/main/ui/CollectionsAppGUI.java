package ui;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import model.Collection;
import model.Memory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionsAppGUI extends JFrame {



    private static boolean useSystemLookAndFeel = false;

    public static class MonthsTree extends JPanel implements TreeSelectionListener {

        private Collection january = new Collection("january");
        private Collection february = new Collection("february");
        private Collection march = new Collection("march");
        private Collection april = new Collection("april");
        private Collection may = new Collection("may");
        private Collection june = new Collection("june");
        private Collection july = new Collection("july");
        private Collection august = new Collection("august");
        private Collection september = new Collection("september");
        private Collection october = new Collection("october");
        private Collection november = new Collection("november");
        private Collection december = new Collection("december");

        private Set<Collection> months;

        private JEditorPane pane;
        private JTree tree;
        private boolean playWithLineStyle = false;
        private String lineStyle = "Horizontal";
        private static final String addString = "Add";
        private static final String saveString = "Save";
        private static final String loadString = "Load";
        private LinkedList<String> allMonths;
        private JTextField songName;


        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        private MonthsTree() {
            super(new GridLayout(1, 0));
            DefaultMutableTreeNode title = new DefaultMutableTreeNode("Months");
            createNodes(title);
            tree = new JTree(title);
            tree.setFont(new Font("Arial", Font.PLAIN, 30));
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.addTreeSelectionListener(this);

            JScrollPane monthsView = new JScrollPane(tree);

            monthsView.setMinimumSize(new Dimension(200, 200));

            JButton addButton = new JButton(addString);
            AddListener addListener = new AddListener(addButton);
            addButton.setActionCommand(addString);
            addButton.addActionListener(addListener);
            addButton.setFont(new Font("Arial", Font.PLAIN, 30));
            addButton.setEnabled(false);

            songName = new JTextField(30);
            songName.addActionListener(addListener);
            songName.getDocument().addDocumentListener(addListener);

            JButton saveButton = new JButton(saveString);
            saveButton.setActionCommand(saveString);
            saveButton.addActionListener(new SaveListener(saveButton));
            saveButton.setFont(new Font("Arial", Font.PLAIN, 30));

            JButton loadButton = new JButton(loadString);
            loadButton.setActionCommand(loadString);
            loadButton.addActionListener(new LoadListener());
            loadButton.setFont(new Font("Arial", Font.PLAIN, 30));

            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
            buttonPane.add(addButton);
            buttonPane.add(songName);

            buttonPane.add(saveButton);
            buttonPane.add(loadButton);
            buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setTopComponent(monthsView);
            splitPane.setBottomComponent(buttonPane);
            add(splitPane);
        }

        private Set<Collection> allMonths() {
            months = new HashSet<>();
            months.add(january);
            months.add(february);
            months.add(march);
            months.add(april);
            months.add(may);
            months.add(june);
            months.add(july);
            months.add(august);
            months.add(september);
            months.add(october);
            months.add(november);
            months.add(december);

            return months;
        }


        private class AddListener implements ActionListener, DocumentListener, TreeSelectionListener {
            private boolean alreadyEnabled = false;
            private JButton button;
            private boolean isTreeSelected = false;

            private AddListener(JButton button) {
                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                String name = songName.getText();
                node.add(new DefaultMutableTreeNode(name));

                songName.requestFocusInWindow();
                songName.setText("");
                updateCollection(name, node.toString());
            }

            public void updateCollection(String s, String n) {
                Memory m = new Memory(s);
                for (Collection c: months) {
                    if (c.getName() == n) {
                        c.addMemory(m);
                    }
                }
            }


            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleEmptyTextField(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!handleEmptyTextField(e)) {
                    enableButton();
                }

            }

            private void enableButton() {
                if (!alreadyEnabled) {
                    button.setEnabled(true);
                }
            }

            private boolean handleEmptyTextField(DocumentEvent e) {
                if (e.getDocument().getLength() <= 0) {
                    button.setEnabled(false);
                    alreadyEnabled = false;
                    return true;
                }
                return false;
            }

            @Override
            public void valueChanged(TreeSelectionEvent e) {
            }

        }

        private class SaveListener implements ActionListener {
            private boolean alreadyEnabled = false;
            private JButton button;

            private static final String JSON_STORE = "./data/data/januarymonth.json";
            private static final String FJSON_STORE = "./data/data/februarymonth.json";
            private static final String MJSON_STORE = "./data/data/marchmonth.json";
            private static final String AJSON_STORE = "./data/data/aprilmonth.json";
            private static final String MAJSON_STORE = "./data/data/maymonth.json";
            private static final String JUJSON_STORE = "./data/data/junemonth.json";
            private static final String JULJSON_STORE = "./data/data/julymonth.json";
            private static final String AUGJSON_STORE = "./data/data/augustmonth.json";
            private static final String SJSON_STORE = "./data/data/september.json";
            private static final String OJSON_STORE = "./data/data/october.json";
            private static final String NJSON_STORE = "./data/data/november.json";
            private static final String DJSON_STORE = "./data/data/december.json";

            private JsonReader janReader = new JsonReader(JSON_STORE);
            private JsonWriter janWriter =  new JsonWriter(JSON_STORE);
            private JsonReader febReader = new JsonReader(FJSON_STORE);
            private JsonWriter febWriter = new JsonWriter(FJSON_STORE);
            private JsonReader marReader = new JsonReader(MJSON_STORE);
            private JsonWriter marWriter = new JsonWriter(MJSON_STORE);
            private JsonReader aprReader = new JsonReader(AJSON_STORE);
            private JsonWriter aprWriter = new JsonWriter(AJSON_STORE);
            private JsonReader mayReader = new JsonReader(MJSON_STORE);
            private JsonWriter mayWriter = new JsonWriter(MJSON_STORE);
            private JsonReader junReader = new JsonReader(JUJSON_STORE);
            private JsonWriter junWriter = new JsonWriter(JUJSON_STORE);
            private JsonReader julReader = new JsonReader(JULJSON_STORE);
            private JsonWriter julWriter = new JsonWriter(JULJSON_STORE);
            private JsonReader augReader = new JsonReader(AUGJSON_STORE);
            private JsonWriter augWriter = new JsonWriter(AUGJSON_STORE);
            private JsonReader septReader = new JsonReader(SJSON_STORE);
            private JsonWriter septWriter = new JsonWriter(SJSON_STORE);
            private JsonReader octReader = new JsonReader(OJSON_STORE);
            private JsonWriter octWriter = new JsonWriter(OJSON_STORE);
            private JsonReader novReader = new JsonReader(NJSON_STORE);
            private JsonWriter novWriter = new JsonWriter(NJSON_STORE);
            private JsonReader decReader = new JsonReader(DJSON_STORE);
            private JsonWriter decWriter = new JsonWriter(DJSON_STORE);

            private SaveListener(JButton button) {

                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent e) {

            }

            private void saveJanuary() {
                try {
                    janWriter.open();
                    janWriter.write(january);
                    janWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveFebruary() {
                try {
                    febWriter.open();
                    febWriter.write(february);
                    febWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveMarch() {
                try {
                    marWriter.open();
                    marWriter.write(march);
                    marWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveApril() {
                try {
                    aprWriter.open();
                    aprWriter.write(april);
                    aprWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveMay() {
                try {
                    mayWriter.open();
                    mayWriter.write(may);
                    mayWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveJune() {
                try {
                    junWriter.open();
                    junWriter.write(june);
                    junWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveJuly() {
                try {
                    julWriter.open();
                    julWriter.write(july);
                    julWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveAugust() {
                try {
                    augWriter.open();
                    augWriter.write(august);
                    augWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveSeptember() {
                try {
                    septWriter.open();
                    septWriter.write(september);
                    septWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveOctober() {
                try {
                    octWriter.open();
                    octWriter.write(october);
                    octWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveNovember() {
                try {
                    novWriter.open();
                    novWriter.write(november);
                    novWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }

            }

            public void saveDecember() {
                try {
                    decWriter.open();
                    decWriter.write(december);
                    decWriter.close();
                } catch (FileNotFoundException e) {
                    errorPop().show();
                }
            }

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


        public class LoadListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }



        private void createNodes(DefaultMutableTreeNode title) {
            DefaultMutableTreeNode month = null;
            month = new DefaultMutableTreeNode("January");
            title.add(month);
            month = new DefaultMutableTreeNode("February");
            title.add(month);
            month = new DefaultMutableTreeNode("March");
            title.add(month);
            month = new DefaultMutableTreeNode("April");
            title.add(month);
            month = new DefaultMutableTreeNode("May");
            title.add(month);
            month = new DefaultMutableTreeNode("June");
            title.add(month);
            month = new DefaultMutableTreeNode("July");
            title.add(month);
            month = new DefaultMutableTreeNode("August");
            title.add(month);
            month = new DefaultMutableTreeNode("September");
            title.add(month);
            month = new DefaultMutableTreeNode("October");
            title.add(month);
            month = new DefaultMutableTreeNode("November");
            title.add(month);
            month = new DefaultMutableTreeNode("December");
            title.add(month);
        }

        @Override
        public void valueChanged(TreeSelectionEvent e) {

        }

        private static void createAndShowGUI() {
            if (useSystemLookAndFeel) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.err.println("Couldn't use system look and feel.");
                }
            }
            JFrame frame = new JFrame("Months Tree");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new MonthsTree());

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



}
