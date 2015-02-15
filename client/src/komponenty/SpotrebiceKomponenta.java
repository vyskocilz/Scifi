package komponenty;

import client.ClientUtils;
import data.SpotrebicAkce;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class SpotrebiceKomponenta extends JPanel implements Serializable {
    JLabel urovenLabel;
    JButton zdrojPlus;
    JButton zdrojMinus;
    JCheckBox zdrojZapnout;
    String sysName;
    String type;

    public SpotrebiceKomponenta() {
        super();
        urovenLabel = new JLabel();
        zdrojPlus = new JButton();
        zdrojMinus = new JButton();
        zdrojZapnout = new JCheckBox();
        setBorder(new TitledBorder("EnergieSource"));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(300, 50);

        //---- urovenLabel ----
        urovenLabel.setText("Vykon");
        urovenLabel.setSize(50, 30);
        urovenLabel.setMinimumSize(new Dimension(50, 30));
        urovenLabel.setPreferredSize(new Dimension(50, 30));
        add(urovenLabel);

        //---- zdrojPlus ----
        zdrojPlus.setText("+");
        add(zdrojPlus);

        //---- zdrojMinus ----
        zdrojMinus.setText("-");
        add(zdrojMinus);

        //---- zdrojZapnout ----
        zdrojZapnout.setText("zapnout");
        add(zdrojZapnout);

        zdrojZapnout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zdrojZapnout.isSelected()) {
                    ClientUtils.sendData(new SpotrebicAkce(sysName, type, SpotrebicAkce.Akce.ZAPNOUT));
                } else {
                    ClientUtils.sendData(new SpotrebicAkce(sysName, type, SpotrebicAkce.Akce.VYPNOUT));
                }
            }
        });

        zdrojPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientUtils.sendData(new SpotrebicAkce(sysName, type, SpotrebicAkce.Akce.PLUS));
            }
        });
        zdrojMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientUtils.sendData(new SpotrebicAkce(sysName, type, SpotrebicAkce.Akce.MINUS));
            }
        });
    }

    public void setNazev(String jmeno) {
        ((TitledBorder) getBorder()).setTitle(jmeno);
        repaint();
    }

    public void setUroven(String vykon) {
        urovenLabel.setText(vykon);
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        zdrojMinus.setEnabled(enabled);
        zdrojPlus.setEnabled(enabled);
        zdrojZapnout.setEnabled(enabled);
    }

    public void setZapnuto(boolean zapnuto) {
        zdrojZapnout.setSelected(zapnuto);
        if (zapnuto) {
            zdrojZapnout.setText("Zapnuto");
        } else {
            zdrojZapnout.setText("Vypnuto");
        }
    }

    public void setPlusMinusEnabled(boolean plusEnabled, boolean minusEnabled) {
        if (isEnabled()) {
            zdrojPlus.setEnabled(plusEnabled);
            zdrojMinus.setEnabled(minusEnabled);
        }
    }
}
