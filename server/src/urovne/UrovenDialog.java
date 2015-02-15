/*
 * Created by JFormDesigner on Sun Jan 30 23:27:58 CET 2011
 */

package urovne;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zdenek Vyskocil
 */
public class UrovenDialog extends JDialog {
    private Urovne uroveneList;

    public UrovenDialog(Frame owner, Urovne urovenList) {
        super(owner);
        this.uroveneList = urovenList;
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        if (urovenTable.isEditing()) {
            urovenTable.getCellEditor().stopCellEditing();
        }
        this.setVisible(false);
        uroveneList = null;
        this.dispose();
    }

    private void addButtonActionPerformed(ActionEvent e) {
        uroveneList.createNewElement();
    }

    private void removeButtonActionPerformed(ActionEvent e) {
        if (urovenTable.getSelectedRow() > -1) {
            uroveneList.removeElement(urovenTable.getSelectedRow());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        toolBar1 = new JToolBar();
        addButton = new JButton();
        removeButton = new JButton();
        scrollPane2 = new JScrollPane();
        urovenTable = new JTable();
        buttonBar = new JPanel();
        okButton = new JButton();
        UrovneListModel = this.uroveneList.getList();

        //======== this ========
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== toolBar1 ========
                {
                    toolBar1.setFloatable(false);

                    //---- addButton ----
                    addButton.setText("Pridat dal\u0161\u00ed");
                    addButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            addButtonActionPerformed(e);
                        }
                    });
                    toolBar1.add(addButton);

                    //---- removeButton ----
                    removeButton.setText("Odebrat");
                    removeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            removeButtonActionPerformed(e);
                        }
                    });
                    toolBar1.add(removeButton);
                }
                contentPanel.add(toolBar1, BorderLayout.PAGE_START);

                //======== scrollPane2 ========
                {

                    //---- urovenTable ----
                    urovenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane2.setViewportView(urovenTable);
                }
                contentPanel.add(scrollPane2, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                    UrovneListModel, urovenTable);
            binding.addColumnBinding(BeanProperty.create("nazev"))
                    .setColumnName("Nazev")
                    .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("spotreba"))
                    .setColumnName("Spotreba")
                    .setColumnClass(Integer.class);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JToolBar toolBar1;
    private JButton addButton;
    private JButton removeButton;
    private JScrollPane scrollPane2;
    private JTable urovenTable;
    private JPanel buttonBar;
    private JButton okButton;
    private ObservableList<urovne.Uroven> UrovneListModel;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
