/*
 * Created by JFormDesigner on Mon Jan 10 00:15:22 CET 2011
 */

package main;

import client.ClientUtils;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import converter.LongToTimeStringConverter;
import data.*;
import ini.ClientProperty;
import log.Log;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Zdenek Vyskocil
 */
public class ClientApplication extends JFrame {

    public static ClientApplication dialog;

    public static void main(String[] args) {
        Log.init();
        JFrame.setDefaultLookAndFeelDecorated(true);
        dialog = new ClientApplication();
        dialog.setExtendedState(Frame.MAXIMIZED_BOTH);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        dialog.setVisible(true);
    }


    public ClientApplication() {
        initComponents();
        ZdrojeModel.setStrojovnaPanel(panelZdroje);
        ZdrojeModel.setMotoryPanel(panelMotory);
        ZdrojeModel.setZbranePanel(panelZbrane);
        ZdrojeModel.setStityPanel(panelStity);
        ZdrojeModel.setSceneryPanel(panelScanery);
        java.util.List<String> clientTypes = ClientProperty.getClientType();

        panelMustek.setVisible(clientTypes.contains(ClientType.MUSTEK));
        panelStrojovna.setVisible(clientTypes.contains(ClientType.STROJOVNA));

        mainPanel.repaint();
    }

    public void updatePalivo(PalivoData data) {
        palivoAktualniSpotreba.setText(Long.toString(data.getPalivoSpotreba()));
        palivoZasoba.setText(Long.toString(data.getPalivoCelkem()));
        palivoCasCelkem.setText(toTimeStringConverter.convertForward(data.getPalivoTime()));
    }


    private static LongToTimeStringConverter toTimeStringConverter = new LongToTimeStringConverter();

    public void updateSpotreba(SpotrebaData data) {
        vykonCelkem.setText(data.getCelkovyVykon().toString());
        spotrebaCelkem.setText(data.getCelkovaSpotreba().toString());

        zbraneSnizit.setEnabled(data.isZbraneMinusEnebla());
        zbraneZvisit.setEnabled(data.isZbranePlusEnebla());
        spotrebaZbrane.setText(data.getZbraneSpotreba().toString());
        vykonZbrane.setText(data.getZbraneVykon().toString());

        stitySnizit.setEnabled(data.isStityMinusEnebla());
        stityZvisit.setEnabled(data.isStityPlusEnebla());
        spotrebaStity.setText(data.getStitySpotreba().toString());
        vykonStity.setText(data.getStityVykon().toString());

        motorySnizit.setEnabled(data.isMotoryMinusEnebla());
        motoryZvisit.setEnabled(data.isMotoryPlusEnebla());
        spotrebaMotory.setText(data.getMotorySpotreba().toString());
        vykonMotory.setText(data.getMotoryVykon().toString());

        scanerySnizit.setEnabled(data.isScaneryMinusEnebla());
        scaneryZvisit.setEnabled(data.isScaneryPlusEnebla());
        spotrebaScanery.setText(data.getScanerySpotreba().toString());
        vykonScanery.setText(data.getScaneryVykon().toString());

    }

    private void thisWindowOpened(WindowEvent e) {
        ClientUtils.start();
    }

    private void thisWindowClosing(WindowEvent e) {
        String pass = JOptionPane.showInputDialog(this, "Zadejte heslo k ukončení", "Konec?", JOptionPane.QUESTION_MESSAGE);
        if (ClientProperty.getClosePassword().equals(pass)) {
            ClientUtils.stop();
            System.exit(0);
        }
    }

    private void chatButtonActionPerformed(ActionEvent e) {
        if (chatEdit.getText().trim().length() > 0) {
            Chat.sendText(chatEdit.getText().trim());
            chatEdit.setText("");
        }
    }

    private void motoryZvisitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.PLUS, SpotrebaAkce.Typ.MOTORY));
    }

    private void motorySnizitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.MINUS, SpotrebaAkce.Typ.MOTORY));
    }

    private void scaneryZvisitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.PLUS, SpotrebaAkce.Typ.SCANERY));
    }

    private void scanerySnizitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.MINUS, SpotrebaAkce.Typ.SCANERY));
    }

    private void zbraneZvisitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.PLUS, SpotrebaAkce.Typ.ZBRANE));
    }

    private void zbraneSnizitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.MINUS, SpotrebaAkce.Typ.ZBRANE));
    }

    private void stityZvisitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.PLUS, SpotrebaAkce.Typ.STITY));
    }

    private void stitySnizitActionPerformed(ActionEvent e) {
        ClientUtils.sendData(new SpotrebaAkce(SpotrebaAkce.Akce.MINUS, SpotrebaAkce.Typ.STITY));
    }

    public JPanel getChatPanel() {
        return chatPanel;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        mainPanel = new JTabbedPane();
        panelMustek = new JPanel();
        panelMotory = new JPanel();
        panelZbrane = new JPanel();
        panelStity = new JPanel();
        panelScanery = new JPanel();
        panelStrojovna = new JPanel();
        panelZdrojeWarper = new JPanel();
        scrollPane1 = new JScrollPane();
        panelZdroje = new JPanel();
        panelSpotrebaPravo = new JPanel();
        panelSpotreba = new JPanel();
        spotrebaPanel = new JPanel();
        label6 = new JLabel();
        label7 = new JLabel();
        label1 = new JLabel();
        vykonCelkem = new JTextField();
        spotrebaCelkem = new JTextField();
        label3 = new JLabel();
        vykonMotory = new JTextField();
        spotrebaMotory = new JTextField();
        motoryZvisit = new JButton();
        motorySnizit = new JButton();
        label4 = new JLabel();
        vykonScanery = new JTextField();
        spotrebaScanery = new JTextField();
        scaneryZvisit = new JButton();
        scanerySnizit = new JButton();
        label5 = new JLabel();
        vykonZbrane = new JTextField();
        spotrebaZbrane = new JTextField();
        zbraneZvisit = new JButton();
        zbraneSnizit = new JButton();
        label8 = new JLabel();
        vykonStity = new JTextField();
        spotrebaStity = new JTextField();
        stityZvisit = new JButton();
        stitySnizit = new JButton();
        panelSpotreba2 = new JPanel();
        spotrebaPanel2 = new JPanel();
        label2 = new JLabel();
        palivoZasoba = new JTextField();
        palivoCasCelkem = new JTextField();
        palivoAktualniSpotreba = new JTextField();
        label9 = new JLabel();
        chatPanel = new JPanel();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        list1 = new JList();
        chatEditPanel = new JPanel();
        chatEdit = new JTextField();
        chatButton = new JButton();
        chatListModel = Chat.getChatListModel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== mainPanel ========
        {

            //======== panelMustek ========
            {
                panelMustek.setVisible(false);
                panelMustek.setLayout(new GridLayout(2, 2));

                //======== panelMotory ========
                {
                    panelMotory.setBorder(new TitledBorder("Motory"));
                    panelMotory.setLayout(new GridLayout(5, 4));
                }
                panelMustek.add(panelMotory);

                //======== panelZbrane ========
                {
                    panelZbrane.setBorder(new TitledBorder("Zbran\u011b"));
                    panelZbrane.setLayout(new GridLayout(5, 4));
                }
                panelMustek.add(panelZbrane);

                //======== panelStity ========
                {
                    panelStity.setBorder(new TitledBorder("\u0160t\u00edty"));
                    panelStity.setLayout(new GridLayout(5, 4));
                }
                panelMustek.add(panelStity);

                //======== panelScanery ========
                {
                    panelScanery.setBorder(new TitledBorder("Ostatn\u00ed"));
                    panelScanery.setLayout(new GridLayout(5, 4));
                }
                panelMustek.add(panelScanery);
            }
            mainPanel.addTab("M\u016fstek", panelMustek);

            //======== panelStrojovna ========
            {
                panelStrojovna.setBorder(null);
                panelStrojovna.setLayout(new BorderLayout());

                //======== panelZdrojeWarper ========
                {
                    panelZdrojeWarper.setBorder(new TitledBorder("Zdroje"));
                    panelZdrojeWarper.setLayout(new BoxLayout(panelZdrojeWarper, BoxLayout.X_AXIS));

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setBorder(null);

                        //======== panelZdroje ========
                        {
                            panelZdroje.setLayout(new VerticalLayout());
                        }
                        scrollPane1.setViewportView(panelZdroje);
                    }
                    panelZdrojeWarper.add(scrollPane1);
                }
                panelStrojovna.add(panelZdrojeWarper, BorderLayout.CENTER);

                //======== panelSpotrebaPravo ========
                {
                    panelSpotrebaPravo.setLayout(new BorderLayout());

                    //======== panelSpotreba ========
                    {
                        panelSpotreba.setBorder(new TitledBorder("Sm\u011br energie"));
                        panelSpotreba.setLayout(new BorderLayout());

                        //======== spotrebaPanel ========
                        {
                            spotrebaPanel.setBorder(null);
                            spotrebaPanel.setLayout(new FormLayout(
                                "6*(default, $lcgap), default",
                                "fill:default, 5*($lgap, default)"));

                            //---- label6 ----
                            label6.setText("V\u00fdkon");
                            label6.setHorizontalAlignment(SwingConstants.CENTER);
                            spotrebaPanel.add(label6, CC.xy(3, 1));

                            //---- label7 ----
                            label7.setText("Spot\u0159eba");
                            label7.setHorizontalAlignment(SwingConstants.CENTER);
                            spotrebaPanel.add(label7, CC.xy(7, 1));

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

                            //---- label4 ----
                            label4.setText("Ostatni");
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

                            //---- label5 ----
                            label5.setText("Zbran\u011b");
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

                            //---- label8 ----
                            label8.setText("\u0160t\u00edty");
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
                        }
                        panelSpotreba.add(spotrebaPanel, BorderLayout.CENTER);
                    }
                    panelSpotrebaPravo.add(panelSpotreba, BorderLayout.PAGE_START);

                    //======== panelSpotreba2 ========
                    {
                        panelSpotreba2.setBorder(new TitledBorder("Palivo"));
                        panelSpotreba2.setLayout(new BorderLayout());

                        //======== spotrebaPanel2 ========
                        {
                            spotrebaPanel2.setBorder(null);
                            spotrebaPanel2.setLayout(new FormLayout(
                                "default, $lcgap, 79dlu, $lcgap, 75dlu",
                                "2*($lgap, default)"));

                            //---- label2 ----
                            label2.setText("Z\u00e1soba");
                            label2.setLabelFor(palivoZasoba);
                            label2.setHorizontalAlignment(SwingConstants.CENTER);
                            label2.setFocusable(false);
                            spotrebaPanel2.add(label2, CC.xy(1, 2));

                            //---- palivoZasoba ----
                            palivoZasoba.setEditable(false);
                            palivoZasoba.setColumns(5);
                            spotrebaPanel2.add(palivoZasoba, CC.xy(3, 2));

                            //---- palivoCasCelkem ----
                            palivoCasCelkem.setEditable(false);
                            palivoCasCelkem.setColumns(5);
                            spotrebaPanel2.add(palivoCasCelkem, CC.xy(5, 2));

                            //---- palivoAktualniSpotreba ----
                            palivoAktualniSpotreba.setEditable(false);
                            palivoAktualniSpotreba.setColumns(5);
                            spotrebaPanel2.add(palivoAktualniSpotreba, CC.xy(3, 4));

                            //---- label9 ----
                            label9.setText("Aktualn\u00ed spot\u0159eba");
                            label9.setLabelFor(palivoAktualniSpotreba);
                            label9.setHorizontalAlignment(SwingConstants.CENTER);
                            label9.setFocusable(false);
                            spotrebaPanel2.add(label9, CC.xy(1, 4));
                        }
                        panelSpotreba2.add(spotrebaPanel2, BorderLayout.CENTER);
                    }
                    panelSpotrebaPravo.add(panelSpotreba2, BorderLayout.CENTER);
                }
                panelStrojovna.add(panelSpotrebaPravo, BorderLayout.EAST);
            }
            mainPanel.addTab("Strojovna", panelStrojovna);
        }
        contentPane.add(mainPanel, BorderLayout.CENTER);

        //======== chatPanel ========
        {
            chatPanel.setBorder(new TitledBorder("Komunik\u00e1tor"));
            chatPanel.setLayout(new BorderLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(list1);
                }
                panel2.add(scrollPane2, BorderLayout.CENTER);

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
        contentPane.add(chatPanel, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_ONCE,
            chatListModel, list1));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTabbedPane mainPanel;
    private JPanel panelMustek;
    private JPanel panelMotory;
    private JPanel panelZbrane;
    private JPanel panelStity;
    private JPanel panelScanery;
    private JPanel panelStrojovna;
    private JPanel panelZdrojeWarper;
    private JScrollPane scrollPane1;
    private JPanel panelZdroje;
    private JPanel panelSpotrebaPravo;
    private JPanel panelSpotreba;
    private JPanel spotrebaPanel;
    private JLabel label6;
    private JLabel label7;
    private JLabel label1;
    private JTextField vykonCelkem;
    private JTextField spotrebaCelkem;
    private JLabel label3;
    private JTextField vykonMotory;
    private JTextField spotrebaMotory;
    private JButton motoryZvisit;
    private JButton motorySnizit;
    private JLabel label4;
    private JTextField vykonScanery;
    private JTextField spotrebaScanery;
    private JButton scaneryZvisit;
    private JButton scanerySnizit;
    private JLabel label5;
    private JTextField vykonZbrane;
    private JTextField spotrebaZbrane;
    private JButton zbraneZvisit;
    private JButton zbraneSnizit;
    private JLabel label8;
    private JTextField vykonStity;
    private JTextField spotrebaStity;
    private JButton stityZvisit;
    private JButton stitySnizit;
    private JPanel panelSpotreba2;
    private JPanel spotrebaPanel2;
    private JLabel label2;
    private JTextField palivoZasoba;
    private JTextField palivoCasCelkem;
    private JTextField palivoAktualniSpotreba;
    private JLabel label9;
    private JPanel chatPanel;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JList list1;
    private JPanel chatEditPanel;
    private JTextField chatEdit;
    private JButton chatButton;
    private ObservableList<java.lang.String> chatListModel;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
