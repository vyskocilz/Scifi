package log;

import ini.ClientProperty;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Pupupaj
 * Date: 15.1.11
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class Log {
    private static boolean enable = false;
    private static DefaultListModel listModel;


    public static void init() {
        enable = ClientProperty.getPropertyAsBoolean(ClientProperty.CLIENT_LOG);
        if (enable) {
            listModel = new DefaultListModel();
            LogDialog dialog = new LogDialog();
            dialog.getList1().setModel(listModel);
            dialog.getLogDialog().setVisible(true);
        }
    }

    public static void log(Object text) {
        if (enable) {
            if (listModel.getSize() > 100) {
                listModel.remove(0);
            }
            listModel.addElement(text);
        }
        System.out.println(text);
    }


    public static void error(Exception e) {
        log("CHYBA>>>" + e.getMessage());
    }
}
