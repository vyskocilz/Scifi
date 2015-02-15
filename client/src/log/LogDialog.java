/*
 * Created by JFormDesigner on Sat Jan 15 22:28:33 CET 2011
 */

package log;

import javax.swing.*;
import java.awt.*;

/**
 * @author Zdenek Vyskocil
 */
public class LogDialog {
    public LogDialog() {
        initComponents();
    }

    public JFrame getLogDialog() {
        return LogDialog;
    }

    public JList getList1() {
        return list1;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        LogDialog = new JFrame();
        scrollPane1 = new JScrollPane();
        list1 = new JList();

        //======== LogDialog ========
        {
            LogDialog.setAlwaysOnTop(true);
            LogDialog.setTitle("Log");
            Container LogDialogContentPane = LogDialog.getContentPane();
            LogDialogContentPane.setLayout(new BorderLayout());

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(list1);
            }
            LogDialogContentPane.add(scrollPane1, BorderLayout.CENTER);
            LogDialog.pack();
            LogDialog.setLocationRelativeTo(LogDialog.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JFrame LogDialog;
    private JScrollPane scrollPane1;
    private JList list1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
