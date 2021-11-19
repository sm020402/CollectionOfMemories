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
import javax.swing.text.DefaultTextUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SimplerGUI extends JFrame {

    private static boolean useSystemLookAndFeel = false;

    public static class MonthsTree extends JPanel implements TreeSelectionListener {

        private Collection january = new Collection("january");

        private JEditorPane pane;
        DefaultMutableTreeNode title = new DefaultMutableTreeNode("Months");
        private JTree tree;
        private boolean playWithLineStyle = false;
        private String lineStyle = "Horizontal";
        private static final String addString = "Add";
        private static final String saveString = "Save";
        private static final String loadString = "Load";
        private JTextField songName;

        public MonthsTree() {
            super(new GridLayout(1, 0));
            DefaultMutableTreeNode month = new DefaultMutableTreeNode("January");
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

        public JButton addButton() {
            JButton addButton = new JButton(addString);
            SimplerGUI.MonthsTree.AddListener addListener = new SimplerGUI.MonthsTree.AddListener(addButton);
            addButton.setActionCommand(addString);
            addButton.addActionListener(addListener);
            addButton.setFont(new Font("Arial", Font.PLAIN, 30));
            addButton.setEnabled(false);

            songName = new JTextField(30);
            songName.addActionListener(addListener);
            songName.getDocument().addDocumentListener(addListener);
            return addButton;
        }

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

        @Override
        public void valueChanged(TreeSelectionEvent e) {

        }

        private class AddListener implements ActionListener, DocumentListener, TreeSelectionListener {
            private boolean alreadyEnabled = false;
            private JButton button;

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
                january.addMemory(new Memory(name));
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

        public class SaveListener implements ActionListener {
            private boolean alreadyEnabled = false;
            private JButton button;

            private static final String JSON_STORE = "./data/data/januarymonth.json";

            private JsonWriter janWriter =  new JsonWriter(JSON_STORE);

            private SaveListener(JButton button) {

                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                saveJanuary();

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

            private static final String JSON_STORE = "./data/data/januarymonth.json";
            private JsonReader janReader =  new JsonReader(JSON_STORE);

            @Override
            public void actionPerformed(ActionEvent e) {
                loadJanuary();

            }

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

            public JDialog previousPop(JTree t) {

                JFrame f = new JFrame("Previously Added");
                JLabel l = new JLabel("Previously Added");
                f.setSize(300, 300);

                JPanel p1 = new JPanel();
                p1.setBackground(Color.blue);
                p1.add(l);
                JScrollPane sp = new JScrollPane(t);
                p1.add(sp);
                JDialog p = new JDialog();
                p.add(p1);
                return p;
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


