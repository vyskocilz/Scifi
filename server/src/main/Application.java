/*
 * Created by JFormDesigner on Sat Jan 08 22:22:25 CET 2011
 */

package main;

import chat.Chat;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.FormLayout;
import converter.LongToTimeStringConverter;
import converter.ZdrojeConverter;
import data.ClientType;
import ini.Theme;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.*;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import server.ServerUtils;
import urovne.UrovenDialog;
import validator.PositiveValidator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import static javax.swing.JOptionPane.YES_OPTION;

/**
 * @author Zdenek Vyskocil
 */
public class Application extends JFrame {

    public static ClientData clientData = new ClientData();
    final JFileChooser fc;


    public static void main(String[] args) {
        Theme.init();
        clientData.init();
        Application dialog = new Application();
        dialog.setVisible(true);
    }

    public Application() {
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(false);
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String ex = getExtension(f);
                return ex != null && "scifi".equalsIgnoreCase(ex);
            }

            @Override
            public String getDescription() {
                return "Scifi soubory (*.scifi)";
            }
        });
        initComponents();
        engineSourceBindingGroup.addBindingListener(serverController.getEnergieSourceServerController().getBindingListener());
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private void thisWindowClosing(WindowEvent e) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu ukončit server?", "Konec?", JOptionPane.YES_NO_OPTION)) {
            ServerUtils.stopServer();
            System.exit(0);
        }
    }

    private void startServerButtonActionPerformed(ActionEvent e) {
        startServerButton.setEnabled(false);
        ServerUtils.startServer((Integer) serverPortField.getValue());
        stopServerButton.setEnabled(true);
    }

    private void stopServerButtonActionPerformed(ActionEvent e) {
        stopServerButton.setEnabled(false);
        ServerUtils.stopServer();
        startServerButton.setEnabled(true);
    }

    private void button1ActionPerformed(ActionEvent e) {
        spotrebaData.addPalivo((Long) addPalivoSpiner.getValue());
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void chatButtonActionPerformed(ActionEvent e) {
        if (chatEdit.getText().trim().length() > 0) {
            Chat.writeText(ClientType.POCITAC, chatEdit.getText().trim());
            chatEdit.setText("");
        }
    }

    private void zdrojPridatActionPerformed(ActionEvent e) {
        serverController.getEnergieSourceServerController().addNew();
    }

    private void zdrojOdebratActionPerformed(ActionEvent e) {
        serverController.getEnergieSourceServerController();
    }

    private void ulozitActionPerformed(ActionEvent e) {
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setDialogTitle("Uložit nastavení");
        int returnVal = fc.showDialog(this, "Uložit");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getAbsolutePath();
            String ex = getExtension(fc.getSelectedFile());
            if (!"scifi".equalsIgnoreCase(ex)) {
                fileName += ".scifi";
            }
            clientData.save(fileName);
        }
    }

    private void nahratActionPerformed(ActionEvent e) {
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setDialogTitle("Nahrát nastavení");
        int returnVal = fc.showDialog(this, "Nahrát");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String ex = getExtension(fc.getSelectedFile());
            if ("scifi".equalsIgnoreCase(ex)) {
                clientData.load(fc.getSelectedFile().getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(null,
                        "Vložte soubor s příponou '.scifi'!", "Špatný soubor",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void stityUrovneActionPerformed(ActionEvent e) {
        if (stityTable.getSelectedRow() > -1) {
            UrovenDialog dialog = new UrovenDialog(this, stityListModel.get(stityTable.getSelectedRow()).getUrovne());
            dialog.setVisible(true);
        }
    }

    private void zvysStitButtonActionPerformed(ActionEvent e) {
        clientData.getStity().zvysUrove(stityTable.getSelectedRow());
    }

    private void snizStitUrovenActionPerformed(ActionEvent e) {
        clientData.getStity().snizUroven(stityTable.getSelectedRow());
    }

    private void smazatStitButtonActionPerformed(ActionEvent e) {
        clientData.getStity().removeElement(stityTable.getSelectedRow());
    }

    private void pridatStitButtonActionPerformed(ActionEvent e) {
        clientData.getStity().createNewElement();
    }

    private void motoryZvisitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().zvisitMotory();
    }

    private void motorySnizitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().snizitMotory();
    }

    private void scaneryZvisitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().zvisitScenery();
    }

    private void scanerySnizitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().snizitScenery();
    }

    private void zbraneZvisitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().zvisitZbrane();
    }

    private void zbraneSnizitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().snizitZbrane();
    }

    private void stityZvisitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().zvisitStity();
    }

    private void stitySnizitActionPerformed(ActionEvent e) {
        clientData.getSpotreba().snizitStity();
    }

    private void zvysScanerButtonActionPerformed(ActionEvent e) {
        clientData.getScanery().zvysUrove(scaneryTable.getSelectedRow());
    }

    private void zvysMotorButtonActionPerformed(ActionEvent e) {
        clientData.getMotory().zvysUrove(motoryTable.getSelectedRow());
    }

    private void zvysZbranButtonActionPerformed(ActionEvent e) {
        clientData.getZbrane().zvysUrove(zbraneTable.getSelectedRow());
    }

    private void snizScanerUrovenActionPerformed(ActionEvent e) {
        clientData.getScanery().snizUroven(scaneryTable.getSelectedRow());
    }

    private void snizMotorUrovenActionPerformed(ActionEvent e) {
        clientData.getMotory().snizUroven(motoryTable.getSelectedRow());
    }

    private void snizZbranUrovenActionPerformed(ActionEvent e) {
        clientData.getZbrane().snizUroven(zbraneTable.getSelectedRow());
    }

    private void scanerUrovneActionPerformed(ActionEvent e) {
        if (scaneryTable.getSelectedRow() > -1) {
            UrovenDialog dialog = new UrovenDialog(this, scaneryListModel.get(scaneryTable.getSelectedRow()).getUrovne());
            dialog.setVisible(true);
        }
    }

    private void motorUrovneActionPerformed(ActionEvent e) {
        if (motoryTable.getSelectedRow() > -1) {
            UrovenDialog dialog = new UrovenDialog(this, motoryListModel.get(motoryTable.getSelectedRow()).getUrovne());
            dialog.setVisible(true);
        }
    }

    private void zbranUrovneActionPerformed(ActionEvent e) {
        if (zbraneTable.getSelectedRow() > -1) {
            UrovenDialog dialog = new UrovenDialog(this, zbraneListModel.get(zbraneTable.getSelectedRow()).getUrovne());
            dialog.setVisible(true);
        }
    }

    private void pridatScanerButton2ActionPerformed(ActionEvent e) {
        clientData.getScanery().createNewElement();
    }

    private void pridatZbranButtonActionPerformed(ActionEvent e) {
        clientData.getZbrane().createNewElement();
    }

    private void pridatMotorButtonActionPerformed(ActionEvent e) {
        clientData.getMotory().createNewElement();
    }

    private void smazatScanerButton2ActionPerformed(ActionEvent e) {
        clientData.getScanery().removeElement(scaneryTable.getSelectedRow());
    }

    private void smazatMotorButtonActionPerformed(ActionEvent e) {
        clientData.getMotory().removeElement(motoryTable.getSelectedRow());
    }

    private void smazatZbranButtonActionPerformed(ActionEvent e) {
        clientData.getZbrane().removeElement(zbraneTable.getSelectedRow());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
        tabbedPane1 = new JTabbedPane();
        stityPanel = new JPanel();
        stityToolbar = new JToolBar();
        zvysStitButton = new JButton();
        snizStitUroven = new JButton();
        stityUrovne = new JButton();
        pridatStitButton = new JButton();
        smazatStitButton = new JButton();
        scrollPane1 = new JScrollPane();
        stityTable = new JTable();
        scaneryPanel = new JPanel();
        stityToolbar2 = new JToolBar();
        zvysScanerButton = new JButton();
        snizScanerUroven = new JButton();
        scanerUrovne = new JButton();
        pridatScanerButton2 = new JButton();
        smazatScanerButton2 = new JButton();
        scrollPane2 = new JScrollPane();
        scaneryTable = new JTable();
        motoryPanel = new JPanel();
        stityToolbar4 = new JToolBar();
        zvysMotorButton = new JButton();
        snizMotorUroven = new JButton();
        motorUrovne = new JButton();
        pridatMotorButton = new JButton();
        smazatMotorButton = new JButton();
        scrollPane4 = new JScrollPane();
        motoryTable = new JTable();
        zbranePanel = new JPanel();
        stityToolbar3 = new JToolBar();
        zvysZbranButton = new JButton();
        snizZbranUroven = new JButton();
        zbranUrovne = new JButton();
        pridatZbranButton = new JButton();
        smazatZbranButton = new JButton();
        scrollPane3 = new JScrollPane();
        zbraneTable = new JTable();
        zdrojePanel = new JPanel();
        zdrojeToolbar = new JToolBar();
        zdrojPridat = new JButton();
        zdrojOdebrat = new JButton();
        zdrojeScrollPanel = new JScrollPane();
        zdrojeTable = new JTable();
        spotrebaMainPanel = new JPanel();
        spotrebaPanel = new JPanel();
        label6 = new JLabel();
        label7 = new JLabel();
        label2 = new JLabel();
        label1 = new JLabel();
        vykonCelkem = new JTextField();
        spotrebaCelkem = new JTextField();
        label3 = new JLabel();
        vykonMotory = new JTextField();
        spotrebaMotory = new JTextField();
        motoryZvisit = new JButton();
        motorySnizit = new JButton();
        checkBox2 = new JCheckBox();
        label4 = new JLabel();
        vykonScanery = new JTextField();
        spotrebaScanery = new JTextField();
        scaneryZvisit = new JButton();
        scanerySnizit = new JButton();
        checkBox3 = new JCheckBox();
        label5 = new JLabel();
        vykonZbrane = new JTextField();
        spotrebaZbrane = new JTextField();
        zbraneZvisit = new JButton();
        zbraneSnizit = new JButton();
        checkBox4 = new JCheckBox();
        label8 = new JLabel();
        vykonStity = new JTextField();
        spotrebaStity = new JTextField();
        stityZvisit = new JButton();
        stitySnizit = new JButton();
        checkBox5 = new JCheckBox();
        label15 = new JLabel();
        vykonSpotrebaEnable = new JCheckBox();
        palivoPanel = new JPanel();
        label12 = new JLabel();
        palivoCelkem = new JTextField();
        palivoCasCelkem = new JTextField();
        label13 = new JLabel();
        palivoSpotreba = new JTextField();
        label14 = new JLabel();
        palivoTimerEnabled = new JCheckBox();
        label9 = new JLabel();
        addPalivoSpiner = new JSpinner();
        addPalivoButton = new JButton();
        serverPanel = new JPanel();
        clientPanel = new JPanel();
        clinetScrollPane = new JScrollPane();
        clientTable = new JTable();
        serverPropertiesPanel = new JPanel();
        mainToolbar = new JToolBar();
        startServerButton = new JButton();
        stopServerButton = new JButton();
        zdrojeUlozit = new JButton();
        zdrojeNahrat = new JButton();
        serverFormPanel = new JPanel();
        label10 = new JLabel();
        serverPortField = new JSpinner();
        label11 = compFactory.createLabel("Ip adresa");
        serverIpAdressField = new JTextField();
        serverIpAdressField.setText(ServerUtils.getIPAddress());
        chatPanel = new JPanel();
        panel2 = new JPanel();
        chatListPanel = new JScrollPane();
        chatList = new JList();
        chatEditPanel = new JPanel();
        chatEdit = new JTextField();
        chatButton = new JButton();
        clientsListModel = ServerUtils.getServerClients();
        chatListModel = Chat.getChatListModel();
        zdrojeListModel = clientData.getZdroje().getList();
        stityListModel = clientData.getStity().getList();
        spotrebaData = clientData.getSpotreba();
        scaneryListModel = clientData.getScanery().getList();
        zbraneListModel = clientData.getZbrane().getList();
        motoryListModel = clientData.getMotory().getList();
        longToTimeStringConverter1 = new LongToTimeStringConverter();
        serverController = new ServerController();
        zdrojeConverter1 = new ZdrojeConverter();
        positiveValidator = new PositiveValidator();

        //======== this ========
        setTitle("Server");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {

            //======== stityPanel ========
            {
                stityPanel.setBorder(new TitledBorder("\u0160t\u00edty"));
                stityPanel.setLayout(new BorderLayout());

                //======== stityToolbar ========
                {
                    stityToolbar.setFloatable(false);

                    //---- zvysStitButton ----
                    zvysStitButton.setText("+ uroven");
                    zvysStitButton.setMinimumSize(new Dimension(100, 25));
                    zvysStitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zvysStitButtonActionPerformed(e);
                        }
                    });
                    stityToolbar.add(zvysStitButton);

                    //---- snizStitUroven ----
                    snizStitUroven.setText("- uroven");
                    snizStitUroven.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            snizStitUrovenActionPerformed(e);
                        }
                    });
                    stityToolbar.add(snizStitUroven);

                    //---- stityUrovne ----
                    stityUrovne.setText("Urovne");
                    stityUrovne.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stityUrovneActionPerformed(e);
                        }
                    });
                    stityToolbar.add(stityUrovne);

                    //---- pridatStitButton ----
                    pridatStitButton.setText("Pridat");
                    pridatStitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pridatStitButtonActionPerformed(e);
                        }
                    });
                    stityToolbar.add(pridatStitButton);

                    //---- smazatStitButton ----
                    smazatStitButton.setText("Smazat");
                    smazatStitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            smazatStitButtonActionPerformed(e);
                        }
                    });
                    stityToolbar.add(smazatStitButton);
                }
                stityPanel.add(stityToolbar, BorderLayout.PAGE_START);

                //======== scrollPane1 ========
                {

                    //---- stityTable ----
                    stityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane1.setViewportView(stityTable);
                }
                stityPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("\u0160t\u00edty", stityPanel);


            //======== scaneryPanel ========
            {
                scaneryPanel.setBorder(new TitledBorder("Ruzne"));
                scaneryPanel.setLayout(new BorderLayout());

                //======== stityToolbar2 ========
                {
                    stityToolbar2.setFloatable(false);

                    //---- zvysScanerButton ----
                    zvysScanerButton.setText("+ uroven");
                    zvysScanerButton.setMinimumSize(new Dimension(100, 25));
                    zvysScanerButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zvysScanerButtonActionPerformed(e);
                        }
                    });
                    stityToolbar2.add(zvysScanerButton);

                    //---- snizScanerUroven ----
                    snizScanerUroven.setText("- uroven");
                    snizScanerUroven.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            snizScanerUrovenActionPerformed(e);
                        }
                    });
                    stityToolbar2.add(snizScanerUroven);

                    //---- scanerUrovne ----
                    scanerUrovne.setText("Urovne");
                    scanerUrovne.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            scanerUrovneActionPerformed(e);
                        }
                    });
                    stityToolbar2.add(scanerUrovne);

                    //---- pridatScanerButton2 ----
                    pridatScanerButton2.setText("Pridat");
                    pridatScanerButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pridatScanerButton2ActionPerformed(e);
                        }
                    });
                    stityToolbar2.add(pridatScanerButton2);

                    //---- smazatScanerButton2 ----
                    smazatScanerButton2.setText("Smazat");
                    smazatScanerButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            smazatScanerButton2ActionPerformed(e);
                        }
                    });
                    stityToolbar2.add(smazatScanerButton2);
                }
                scaneryPanel.add(stityToolbar2, BorderLayout.PAGE_START);

                //======== scrollPane2 ========
                {

                    //---- scaneryTable ----
                    scaneryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane2.setViewportView(scaneryTable);
                }
                scaneryPanel.add(scrollPane2, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Scanery", scaneryPanel);


            //======== motoryPanel ========
            {
                motoryPanel.setBorder(new TitledBorder("Motory"));
                motoryPanel.setLayout(new BorderLayout());

                //======== stityToolbar4 ========
                {
                    stityToolbar4.setFloatable(false);

                    //---- zvysMotorButton ----
                    zvysMotorButton.setText("+ uroven");
                    zvysMotorButton.setMinimumSize(new Dimension(100, 25));
                    zvysMotorButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zvysMotorButtonActionPerformed(e);
                        }
                    });
                    stityToolbar4.add(zvysMotorButton);

                    //---- snizMotorUroven ----
                    snizMotorUroven.setText("- uroven");
                    snizMotorUroven.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            snizMotorUrovenActionPerformed(e);
                        }
                    });
                    stityToolbar4.add(snizMotorUroven);

                    //---- motorUrovne ----
                    motorUrovne.setText("Urovne");
                    motorUrovne.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            motorUrovneActionPerformed(e);
                        }
                    });
                    stityToolbar4.add(motorUrovne);

                    //---- pridatMotorButton ----
                    pridatMotorButton.setText("Pridat");
                    pridatMotorButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pridatMotorButtonActionPerformed(e);
                        }
                    });
                    stityToolbar4.add(pridatMotorButton);

                    //---- smazatMotorButton ----
                    smazatMotorButton.setText("Smazat");
                    smazatMotorButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            smazatMotorButtonActionPerformed(e);
                        }
                    });
                    stityToolbar4.add(smazatMotorButton);
                }
                motoryPanel.add(stityToolbar4, BorderLayout.PAGE_START);

                //======== scrollPane4 ========
                {

                    //---- motoryTable ----
                    motoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane4.setViewportView(motoryTable);
                }
                motoryPanel.add(scrollPane4, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Motory", motoryPanel);


            //======== zbranePanel ========
            {
                zbranePanel.setBorder(new TitledBorder("Zbran\u011b"));
                zbranePanel.setLayout(new BorderLayout());

                //======== stityToolbar3 ========
                {
                    stityToolbar3.setFloatable(false);

                    //---- zvysZbranButton ----
                    zvysZbranButton.setText("+ uroven");
                    zvysZbranButton.setMinimumSize(new Dimension(100, 25));
                    zvysZbranButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zvysZbranButtonActionPerformed(e);
                        }
                    });
                    stityToolbar3.add(zvysZbranButton);

                    //---- snizZbranUroven ----
                    snizZbranUroven.setText("- uroven");
                    snizZbranUroven.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            snizZbranUrovenActionPerformed(e);
                        }
                    });
                    stityToolbar3.add(snizZbranUroven);

                    //---- zbranUrovne ----
                    zbranUrovne.setText("Urovne");
                    zbranUrovne.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zbranUrovneActionPerformed(e);
                        }
                    });
                    stityToolbar3.add(zbranUrovne);

                    //---- pridatZbranButton ----
                    pridatZbranButton.setText("Pridat");
                    pridatZbranButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pridatZbranButtonActionPerformed(e);
                        }
                    });
                    stityToolbar3.add(pridatZbranButton);

                    //---- smazatZbranButton ----
                    smazatZbranButton.setText("Smazat");
                    smazatZbranButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            smazatZbranButtonActionPerformed(e);
                        }
                    });
                    stityToolbar3.add(smazatZbranButton);
                }
                zbranePanel.add(stityToolbar3, BorderLayout.PAGE_START);

                //======== scrollPane3 ========
                {

                    //---- zbraneTable ----
                    zbraneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane3.setViewportView(zbraneTable);
                }
                zbranePanel.add(scrollPane3, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Zbran\u011b", zbranePanel);


            //======== zdrojePanel ========
            {
                zdrojePanel.setBorder(new TitledBorder("Zdroje"));
                zdrojePanel.setLayout(new BorderLayout());

                //======== zdrojeToolbar ========
                {
                    zdrojeToolbar.setFloatable(false);

                    //---- zdrojPridat ----
                    zdrojPridat.setText("Pridat");
                    zdrojPridat.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zdrojPridatActionPerformed(e);
                        }
                    });
                    zdrojeToolbar.add(zdrojPridat);

                    //---- zdrojOdebrat ----
                    zdrojOdebrat.setText("Smazat");
                    zdrojOdebrat.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zdrojOdebratActionPerformed(e);
                        }
                    });
                    zdrojeToolbar.add(zdrojOdebrat);
                }
                zdrojePanel.add(zdrojeToolbar, BorderLayout.NORTH);

                //======== zdrojeScrollPanel ========
                {

                    //---- zdrojeTable ----
                    zdrojeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    zdrojeScrollPanel.setViewportView(zdrojeTable);
                }
                zdrojePanel.add(zdrojeScrollPanel, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Zdroje", zdrojePanel);


            //======== spotrebaMainPanel ========
            {
                spotrebaMainPanel.setLayout(new BorderLayout());

                //======== spotrebaPanel ========
                {
                    spotrebaPanel.setBorder(new TitledBorder("Spotreba"));
                    spotrebaPanel.setLayout(new FormLayout(
                            "6*(default, $lcgap), default",
                            "fill:default, 6*($lgap, default)"));

                    //---- label6 ----
                    label6.setText("Vykon");
                    label6.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label6, CC.xy(3, 1));

                    //---- label7 ----
                    label7.setText("Spotreba");
                    label7.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label7, CC.xy(7, 1));

                    //---- label2 ----
                    label2.setText("Ovladani");
                    spotrebaPanel.add(label2, CC.xy(13, 1));

                    //---- label1 ----
                    label1.setText("Celkem");
                    label1.setLabelFor(vykonCelkem);
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setFocusable(false);
                    spotrebaPanel.add(label1, CC.xy(1, 3));

                    //---- vykonCelkem ----
                    vykonCelkem.setEditable(false);
                    vykonCelkem.setColumns(5);
                    spotrebaPanel.add(vykonCelkem, CC.xy(3, 3));

                    //---- spotrebaCelkem ----
                    spotrebaCelkem.setEditable(false);
                    spotrebaCelkem.setColumns(5);
                    spotrebaPanel.add(spotrebaCelkem, CC.xy(7, 3));

                    //---- label3 ----
                    label3.setText("Motory");
                    label3.setLabelFor(vykonMotory);
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label3, CC.xy(1, 5));

                    //---- vykonMotory ----
                    vykonMotory.setColumns(5);
                    vykonMotory.setEditable(false);
                    spotrebaPanel.add(vykonMotory, CC.xy(3, 5));

                    //---- spotrebaMotory ----
                    spotrebaMotory.setEditable(false);
                    spotrebaMotory.setColumns(5);
                    spotrebaPanel.add(spotrebaMotory, CC.xy(7, 5));

                    //---- motoryZvisit ----
                    motoryZvisit.setText("+");
                    motoryZvisit.setEnabled(false);
                    motoryZvisit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            motoryZvisitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(motoryZvisit, CC.xy(9, 5));

                    //---- motorySnizit ----
                    motorySnizit.setText("-");
                    motorySnizit.setEnabled(false);
                    motorySnizit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            motorySnizitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(motorySnizit, CC.xy(11, 5));
                    spotrebaPanel.add(checkBox2, CC.xy(13, 5));

                    //---- label4 ----
                    label4.setText("Ruzne");
                    label4.setLabelFor(vykonScanery);
                    label4.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label4, CC.xy(1, 7));

                    //---- vykonScanery ----
                    vykonScanery.setColumns(5);
                    vykonScanery.setEditable(false);
                    spotrebaPanel.add(vykonScanery, CC.xy(3, 7));

                    //---- spotrebaScanery ----
                    spotrebaScanery.setEditable(false);
                    spotrebaPanel.add(spotrebaScanery, CC.xy(7, 7));

                    //---- scaneryZvisit ----
                    scaneryZvisit.setText("+");
                    scaneryZvisit.setEnabled(false);
                    scaneryZvisit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            scaneryZvisitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(scaneryZvisit, CC.xy(9, 7));

                    //---- scanerySnizit ----
                    scanerySnizit.setText("-");
                    scanerySnizit.setEnabled(false);
                    scanerySnizit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            scanerySnizitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(scanerySnizit, CC.xy(11, 7));
                    spotrebaPanel.add(checkBox3, CC.xy(13, 7));

                    //---- label5 ----
                    label5.setText("Zbrane");
                    label5.setLabelFor(vykonZbrane);
                    label5.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label5, CC.xy(1, 9));

                    //---- vykonZbrane ----
                    vykonZbrane.setColumns(5);
                    vykonZbrane.setEditable(false);
                    spotrebaPanel.add(vykonZbrane, CC.xy(3, 9));

                    //---- spotrebaZbrane ----
                    spotrebaZbrane.setEditable(false);
                    spotrebaPanel.add(spotrebaZbrane, CC.xy(7, 9));

                    //---- zbraneZvisit ----
                    zbraneZvisit.setText("+");
                    zbraneZvisit.setEnabled(false);
                    zbraneZvisit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zbraneZvisitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(zbraneZvisit, CC.xy(9, 9));

                    //---- zbraneSnizit ----
                    zbraneSnizit.setText("-");
                    zbraneSnizit.setEnabled(false);
                    zbraneSnizit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            zbraneSnizitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(zbraneSnizit, CC.xy(11, 9));
                    spotrebaPanel.add(checkBox4, CC.xy(13, 9));

                    //---- label8 ----
                    label8.setText("Stity");
                    label8.setLabelFor(vykonStity);
                    label8.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label8, CC.xy(1, 11));

                    //---- vykonStity ----
                    vykonStity.setColumns(5);
                    vykonStity.setEditable(false);
                    spotrebaPanel.add(vykonStity, CC.xy(3, 11));

                    //---- spotrebaStity ----
                    spotrebaStity.setEditable(false);
                    spotrebaPanel.add(spotrebaStity, CC.xy(7, 11));

                    //---- stityZvisit ----
                    stityZvisit.setText("+");
                    stityZvisit.setEnabled(false);
                    stityZvisit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stityZvisitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(stityZvisit, CC.xy(9, 11));

                    //---- stitySnizit ----
                    stitySnizit.setText("-");
                    stitySnizit.setEnabled(false);
                    stitySnizit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stitySnizitActionPerformed(e);
                        }
                    });
                    spotrebaPanel.add(stitySnizit, CC.xy(11, 11));
                    spotrebaPanel.add(checkBox5, CC.xy(13, 11));

                    //---- label15 ----
                    label15.setText("Po\u010d\u00edt\u00e1n\u00ed spot\u0159eby");
                    label15.setHorizontalAlignment(SwingConstants.CENTER);
                    spotrebaPanel.add(label15, CC.xy(1, 13));
                    spotrebaPanel.add(vykonSpotrebaEnable, CC.xy(3, 13));
                }
                spotrebaMainPanel.add(spotrebaPanel, BorderLayout.WEST);

                //======== palivoPanel ========
                {
                    palivoPanel.setBorder(new TitledBorder("Palivo"));
                    palivoPanel.setLayout(new FormLayout(
                            "default, $lcgap, 53dlu, $lcgap, 64dlu",
                            "fill:default, 6*($lgap, default)"));

                    //---- label12 ----
                    label12.setText("Celkem paliva");
                    label12.setLabelFor(palivoCelkem);
                    label12.setHorizontalAlignment(SwingConstants.CENTER);
                    label12.setFocusable(false);
                    palivoPanel.add(label12, CC.xy(1, 3));

                    //---- palivoCelkem ----
                    palivoCelkem.setEditable(false);
                    palivoCelkem.setColumns(5);
                    palivoPanel.add(palivoCelkem, CC.xy(3, 3));

                    //---- palivoCasCelkem ----
                    palivoCasCelkem.setEditable(false);
                    palivoCasCelkem.setColumns(5);
                    palivoPanel.add(palivoCasCelkem, CC.xy(5, 3));

                    //---- label13 ----
                    label13.setText("Spot\u0159eba za sekundu");
                    label13.setLabelFor(palivoSpotreba);
                    label13.setHorizontalAlignment(SwingConstants.CENTER);
                    palivoPanel.add(label13, CC.xy(1, 5));

                    //---- palivoSpotreba ----
                    palivoSpotreba.setEditable(false);
                    palivoSpotreba.setColumns(5);
                    palivoPanel.add(palivoSpotreba, CC.xy(3, 5));

                    //---- label14 ----
                    label14.setText("Po\u010d\u00edt\u00e1n\u00ed spot\u0159eby");
                    label14.setHorizontalAlignment(SwingConstants.CENTER);
                    palivoPanel.add(label14, CC.xy(1, 7));
                    palivoPanel.add(palivoTimerEnabled, CC.xy(3, 7));

                    //---- label9 ----
                    label9.setText("P\u0159idat palivo");
                    label9.setHorizontalAlignment(SwingConstants.CENTER);
                    palivoPanel.add(label9, CC.xy(1, 9));

                    //---- addPalivoSpiner ----
                    addPalivoSpiner.setModel(new SpinnerNumberModel(0L, null, null, 1L));
                    palivoPanel.add(addPalivoSpiner, CC.xy(3, 9));

                    //---- addPalivoButton ----
                    addPalivoButton.setText("P\u0159idat");
                    addPalivoButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            button1ActionPerformed(e);
                        }
                    });
                    palivoPanel.add(addPalivoButton, CC.xy(5, 9));
                }
                spotrebaMainPanel.add(palivoPanel, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Spot\u0159eba", spotrebaMainPanel);


            //======== serverPanel ========
            {
                serverPanel.setLayout(new BorderLayout());

                //======== clientPanel ========
                {
                    clientPanel.setBorder(new TitledBorder("Klienti"));
                    clientPanel.setLayout(new BorderLayout());

                    //======== clinetScrollPane ========
                    {

                        //---- clientTable ----
                        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        clinetScrollPane.setViewportView(clientTable);
                    }
                    clientPanel.add(clinetScrollPane, BorderLayout.CENTER);
                }
                serverPanel.add(clientPanel, BorderLayout.CENTER);

                //======== serverPropertiesPanel ========
                {
                    serverPropertiesPanel.setBorder(new TitledBorder("Nastaveni"));
                    serverPropertiesPanel.setLayout(new BorderLayout());

                    //======== mainToolbar ========
                    {
                        mainToolbar.setFloatable(false);

                        //---- startServerButton ----
                        startServerButton.setText("Spustit server");
                        startServerButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                startServerButtonActionPerformed(e);
                            }
                        });
                        mainToolbar.add(startServerButton);

                        //---- stopServerButton ----
                        stopServerButton.setText("Zastavit server");
                        stopServerButton.setEnabled(false);
                        stopServerButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                stopServerButtonActionPerformed(e);
                            }
                        });
                        mainToolbar.add(stopServerButton);

                        //---- zdrojeUlozit ----
                        zdrojeUlozit.setText("Ulozit nastaveni");
                        zdrojeUlozit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ulozitActionPerformed(e);
                            }
                        });
                        mainToolbar.add(zdrojeUlozit);

                        //---- zdrojeNahrat ----
                        zdrojeNahrat.setText("Nahrat nastaveni");
                        zdrojeNahrat.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                nahratActionPerformed(e);
                            }
                        });
                        mainToolbar.add(zdrojeNahrat);
                    }
                    serverPropertiesPanel.add(mainToolbar, BorderLayout.PAGE_START);

                    //======== serverFormPanel ========
                    {
                        serverFormPanel.setLayout(new FormLayout(
                                "35dlu, $lcgap, 81dlu, $lcgap, default",
                                "2*(default, $lgap), default"));

                        //---- label10 ----
                        label10.setText("Port");
                        serverFormPanel.add(label10, CC.xy(1, 1));

                        //---- serverPortField ----
                        serverPortField.setModel(new SpinnerNumberModel(1234, 1000, 20000, 1));
                        serverFormPanel.add(serverPortField, CC.xy(3, 1));
                        serverFormPanel.add(label11, CC.xy(1, 3));

                        //---- serverIpAdressField ----
                        serverIpAdressField.setEditable(false);
                        serverFormPanel.add(serverIpAdressField, CC.xy(3, 3));
                    }
                    serverPropertiesPanel.add(serverFormPanel, BorderLayout.CENTER);
                }
                serverPanel.add(serverPropertiesPanel, BorderLayout.EAST);
            }
            tabbedPane1.addTab("Server", serverPanel);


            //======== chatPanel ========
            {
                chatPanel.setBorder(new TitledBorder("Chat"));
                chatPanel.setLayout(new BorderLayout());

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout());

                    //======== chatListPanel ========
                    {

                        //---- chatList ----
                        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        chatList.setEnabled(false);
                        chatListPanel.setViewportView(chatList);
                    }
                    panel2.add(chatListPanel, BorderLayout.CENTER);

                    //======== chatEditPanel ========
                    {
                        chatEditPanel.setLayout(new BoxLayout(chatEditPanel, BoxLayout.X_AXIS));
                        chatEditPanel.add(chatEdit);

                        //---- chatButton ----
                        chatButton.setText("Odeslat");
                        chatButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                chatButtonActionPerformed(e);
                            }
                        });
                        chatEditPanel.add(chatButton);
                    }
                    panel2.add(chatEditPanel, BorderLayout.NORTH);
                }
                chatPanel.add(panel2, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("Chat", chatPanel);

        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        setSize(1080, 600);
        setLocationRelativeTo(null);

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    clientsListModel, clientTable);
            binding.setEditable(false);
            binding.addColumnBinding(BeanProperty.create("clientName"))
                    .setColumnName("Klient")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("clientIp"))
                    .setColumnName("Ip adrresa")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("connected"))
                    .setColumnName("Connected")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("port"))
                    .setColumnName("Port")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
            binding.bind();
        }
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ,
                chatListModel, chatList));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                chatListModel, ELProperty.create("${length}"),
                chatList, BeanProperty.create("selectedIndex")));
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    stityListModel, stityTable);
            binding.setEditable(false);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("Nazev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.nazev"))
                    .setColumnName("Uroven")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.spotreba"))
                    .setColumnName("Spotreba")
                    .setColumnClass(Integer.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("ovladatelnost"))
                    .setColumnName("Ovladatelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("viditelnost"))
                    .setColumnName("Viditelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("zapnut"))
                    .setColumnName("Zapnut")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("priorita"))
                    .setColumnName("Priorita")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
        }
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                    spotrebaData, BeanProperty.create("celkovyVykon"),
                    vykonCelkem, BeanProperty.create("text"));
            bindingGroup.addBinding(binding);
            binding.bind();
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("motoryVykon"),
                vykonMotory, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("scaneryVykon"),
                vykonScanery, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("zbraneVykon"),
                vykonZbrane, BeanProperty.create("text")));
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                    spotrebaData, BeanProperty.create("celkovaSpotreba"),
                    spotrebaCelkem, BeanProperty.create("text"));
            binding.setTargetNullValue(0);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("stityVykon"),
                vykonStity, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("stitySpotreba"),
                spotrebaStity, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("zbraneSpotreba"),
                spotrebaZbrane, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("scanerySpotreba"),
                spotrebaScanery, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("motorySpotreba"),
                spotrebaMotory, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enablePlus"),
                motoryZvisit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enablePlus"),
                scaneryZvisit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enablePlus"),
                zbraneZvisit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enablePlus"),
                stityZvisit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableMinusMotory"),
                motorySnizit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableMinusScanery"),
                scanerySnizit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableMinusZbrane"),
                zbraneSnizit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableMinusStity"),
                stitySnizit, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableMotory"),
                checkBox2, BeanProperty.create("selected")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableScanery"),
                checkBox3, BeanProperty.create("selected")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableZbrane"),
                checkBox4, BeanProperty.create("selected")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("enableStity"),
                checkBox5, BeanProperty.create("selected")));
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    scaneryListModel, scaneryTable);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("Nazev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.nazev"))
                    .setColumnName("Uroven")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.spotreba"))
                    .setColumnName("Spotreba")
                    .setColumnClass(Integer.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("ovladatelnost"))
                    .setColumnName("Ovladatelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("viditelnost"))
                    .setColumnName("Viditelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("zapnut"))
                    .setColumnName("Zapnut")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("priorita"))
                    .setColumnName("Priorita")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
        }
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    zbraneListModel, zbraneTable);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("Nazev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.nazev"))
                    .setColumnName("Uroven")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.spotreba"))
                    .setColumnName("Spotreba")
                    .setColumnClass(Integer.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("ovladatelnost"))
                    .setColumnName("Ovladatelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("viditelnost"))
                    .setColumnName("Viditelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("zapnut"))
                    .setColumnName("Zapnut")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("priorita"))
                    .setColumnName("Priorita")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
        }
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    motoryListModel, motoryTable);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("Nazev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.nazev"))
                    .setColumnName("Uroven")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("aktualniUroven.spotreba"))
                    .setColumnName("Spotreba")
                    .setColumnClass(Integer.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("ovladatelnost"))
                    .setColumnName("Ovladatelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("viditelnost"))
                    .setColumnName("Viditelnost")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("zapnut"))
                    .setColumnName("Zapnut")
                    .setColumnClass(Boolean.class);
            binding.addColumnBinding(BeanProperty.create("priorita"))
                    .setColumnName("Priorita")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                stityUrovne, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                pridatStitButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                smazatStitButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                scanerUrovne, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                pridatScanerButton2, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                smazatScanerButton2, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                zbranUrovne, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                pridatZbranButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                smazatZbranButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                motorUrovne, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                pridatMotorButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                smazatMotorButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                startServerButton, BeanProperty.create("enabled"),
                zdrojOdebrat, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                spotrebaData, BeanProperty.create("timerSpotrebaEnable"),
                vykonSpotrebaEnable, BeanProperty.create("selected")));
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                    serverController, BeanProperty.create("energieSourceServerController.recalculateFuel"),
                    palivoTimerEnabled, BeanProperty.create("selected"));
            binding.setTargetNullValue(false);
            bindingGroup.addBinding(binding);
        }
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                    spotrebaData, BeanProperty.create("palivoCelkem"),
                    palivoCelkem, BeanProperty.create("text"));
            binding.setTargetNullValue(0L);
            bindingGroup.addBinding(binding);
        }
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                    spotrebaData, BeanProperty.create("palivoSpotreba"),
                    palivoSpotreba, BeanProperty.create("text"));
            binding.setTargetNullValue(0L);
            bindingGroup.addBinding(binding);
        }
        {
            Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ,
                    spotrebaData, BeanProperty.create("palivoTime"),
                    palivoCasCelkem, BeanProperty.create("text"));
            binding.setConverter(longToTimeStringConverter1);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
                startServerButton, BeanProperty.create("enabled"),
                serverPortField, BeanProperty.create("enabled")));
        bindingGroup.bind();
        engineSourceBindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ,
                    serverController, (BeanProperty) BeanProperty.create("energieSourceServerController.list"), zdrojeTable, "engineSourceBindingGroup");
            binding.setEditable(false);
            binding.addColumnBinding(BeanProperty.create("name"))
                    .setColumnName("Name")
                    .setColumnClass(String.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("enable"))
                    .setColumnName("Enable")
                    .setColumnClass(Boolean.class)
                    .setEditable(false);
            JTableBinding.ColumnBinding columnBinding = binding.addColumnBinding(BeanProperty.create("fuelUsage"))
                    .setColumnName("Fuel Usage")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            columnBinding = binding.addColumnBinding(BeanProperty.create("fuelUsageActual"))
                    .setColumnName("Fuel Usage Actual")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            columnBinding = binding.addColumnBinding(BeanProperty.create("powerActual"))
                    .setColumnName("Power Actual")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            binding.addColumnBinding(BeanProperty.create("powerNeeded"))
                    .setColumnName("Power Needed")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding = binding.addColumnBinding(BeanProperty.create("powerAddSpeed"))
                    .setColumnName("Power Add Speed")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            columnBinding = binding.addColumnBinding(BeanProperty.create("powerMax"))
                    .setColumnName("Power Max")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            columnBinding = binding.addColumnBinding(BeanProperty.create("powerMin"))
                    .setColumnName("Power Min")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            columnBinding = binding.addColumnBinding(BeanProperty.create("priority"))
                    .setColumnName("Priority")
                    .setColumnClass(Long.class)
                    .setEditable(false);
            columnBinding.setTargetNullValue(0L);
            binding.addColumnBinding(BeanProperty.create("visible"))
                    .setColumnName("Visible")
                    .setColumnClass(Boolean.class)
                    .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("editable"))
                    .setColumnName("Editable")
                    .setColumnClass(Boolean.class)
                    .setEditable(false);
            engineSourceBindingGroup.addBinding(binding);
        }
        engineSourceBindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTabbedPane tabbedPane1;
    private JPanel stityPanel;
    private JToolBar stityToolbar;
    private JButton zvysStitButton;
    private JButton snizStitUroven;
    private JButton stityUrovne;
    private JButton pridatStitButton;
    private JButton smazatStitButton;
    private JScrollPane scrollPane1;
    private JTable stityTable;
    private JPanel scaneryPanel;
    private JToolBar stityToolbar2;
    private JButton zvysScanerButton;
    private JButton snizScanerUroven;
    private JButton scanerUrovne;
    private JButton pridatScanerButton2;
    private JButton smazatScanerButton2;
    private JScrollPane scrollPane2;
    private JTable scaneryTable;
    private JPanel motoryPanel;
    private JToolBar stityToolbar4;
    private JButton zvysMotorButton;
    private JButton snizMotorUroven;
    private JButton motorUrovne;
    private JButton pridatMotorButton;
    private JButton smazatMotorButton;
    private JScrollPane scrollPane4;
    private JTable motoryTable;
    private JPanel zbranePanel;
    private JToolBar stityToolbar3;
    private JButton zvysZbranButton;
    private JButton snizZbranUroven;
    private JButton zbranUrovne;
    private JButton pridatZbranButton;
    private JButton smazatZbranButton;
    private JScrollPane scrollPane3;
    private JTable zbraneTable;
    private JPanel zdrojePanel;
    private JToolBar zdrojeToolbar;
    private JButton zdrojPridat;
    private JButton zdrojOdebrat;
    private JScrollPane zdrojeScrollPanel;
    private JTable zdrojeTable;
    private JPanel spotrebaMainPanel;
    private JPanel spotrebaPanel;
    private JLabel label6;
    private JLabel label7;
    private JLabel label2;
    private JLabel label1;
    private JTextField vykonCelkem;
    private JTextField spotrebaCelkem;
    private JLabel label3;
    private JTextField vykonMotory;
    private JTextField spotrebaMotory;
    private JButton motoryZvisit;
    private JButton motorySnizit;
    private JCheckBox checkBox2;
    private JLabel label4;
    private JTextField vykonScanery;
    private JTextField spotrebaScanery;
    private JButton scaneryZvisit;
    private JButton scanerySnizit;
    private JCheckBox checkBox3;
    private JLabel label5;
    private JTextField vykonZbrane;
    private JTextField spotrebaZbrane;
    private JButton zbraneZvisit;
    private JButton zbraneSnizit;
    private JCheckBox checkBox4;
    private JLabel label8;
    private JTextField vykonStity;
    private JTextField spotrebaStity;
    private JButton stityZvisit;
    private JButton stitySnizit;
    private JCheckBox checkBox5;
    private JLabel label15;
    private JCheckBox vykonSpotrebaEnable;
    private JPanel palivoPanel;
    private JLabel label12;
    private JTextField palivoCelkem;
    private JTextField palivoCasCelkem;
    private JLabel label13;
    private JTextField palivoSpotreba;
    private JLabel label14;
    private JCheckBox palivoTimerEnabled;
    private JLabel label9;
    private JSpinner addPalivoSpiner;
    private JButton addPalivoButton;
    private JPanel serverPanel;
    private JPanel clientPanel;
    private JScrollPane clinetScrollPane;
    private JTable clientTable;
    private JPanel serverPropertiesPanel;
    private JToolBar mainToolbar;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JButton zdrojeUlozit;
    private JButton zdrojeNahrat;
    private JPanel serverFormPanel;
    private JLabel label10;
    private JSpinner serverPortField;
    private JLabel label11;
    private JTextField serverIpAdressField;
    private JPanel chatPanel;
    private JPanel panel2;
    private JScrollPane chatListPanel;
    private JList chatList;
    private JPanel chatEditPanel;
    private JTextField chatEdit;
    private JButton chatButton;
    private ObservableList<server.ServerClient> clientsListModel;
    private ObservableList<java.lang.String> chatListModel;
    private ObservableList<zdroje.Zdroj> zdrojeListModel;
    private ObservableList<spotrebice.Stit> stityListModel;
    private Spotreba spotrebaData;
    private ObservableList<spotrebice.Scaner> scaneryListModel;
    private ObservableList<spotrebice.Zbran> zbraneListModel;
    private ObservableList<spotrebice.Motor> motoryListModel;
    private LongToTimeStringConverter longToTimeStringConverter1;
    private ServerController serverController;
    private ZdrojeConverter zdrojeConverter1;
    private PositiveValidator positiveValidator;
    private BindingGroup bindingGroup;
    private BindingGroup engineSourceBindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
