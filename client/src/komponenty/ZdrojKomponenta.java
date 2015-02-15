package komponenty;

import client.ClientUtils;
import data.ZdrojAkce;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class ZdrojKomponenta extends JPanel implements Serializable {
    JLabel zdrojLabel;
    JLabel zdrojVykon;
    JButton zdrojPlus;
    JButton zdrojMinus;
    JCheckBox zdrojZapnout;
    String sysName;
    JLabel spotrebaPaliva;

    public ZdrojKomponenta() {
        super();
        zdrojLabel = new JLabel();
        zdrojVykon = new JLabel();
        spotrebaPaliva = new JLabel();
        zdrojPlus = new JButton();
        zdrojMinus = new JButton();
        zdrojZapnout = new JCheckBox();
        setBorder(new TitledBorder("EnergieSource"));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(300, 50);

        //---- zdrojLabel ----
        zdrojLabel.setText("Výkon");
        zdrojVykon.setSize(100, 30);
        zdrojVykon.setMinimumSize(new Dimension(100, 30));
        zdrojVykon.setPreferredSize(new Dimension(100, 30));
        add(zdrojLabel);

        //---- zdrojVykon ----
        zdrojVykon.setText("0/0");
        zdrojVykon.setSize(150, 30);
        zdrojVykon.setMinimumSize(new Dimension(50, 30));
        zdrojVykon.setPreferredSize(new Dimension(50, 30));
        add(zdrojVykon);

        //---- zdrojPlus ----
        zdrojPlus.setText("+");
        add(zdrojPlus);
        zdrojPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientUtils.sendData(new ZdrojAkce(ZdrojAkce.Akce.PLUS, sysName));
            }
        });
        //---- zdrojMinus ----
        zdrojMinus.setText("-");
        add(zdrojMinus);
        zdrojMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientUtils.sendData(new ZdrojAkce(ZdrojAkce.Akce.MINUS, sysName));
            }
        });
        //---- zdrojZapnout ----
        zdrojZapnout.setText("Zapnout");
        add(zdrojZapnout);

        zdrojZapnout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zdrojZapnout.isSelected()) {
                    ClientUtils.sendData(new ZdrojAkce(ZdrojAkce.Akce.ZAPNOUT, sysName));
                } else {
                    ClientUtils.sendData(new ZdrojAkce(ZdrojAkce.Akce.VYPNOUT, sysName));
                }
            }
        });
        //---- spotrebaPaliva -----
        spotrebaPaliva.setText("0 paliva/sec");
        spotrebaPaliva.setSize(150, 30);
        spotrebaPaliva.setMinimumSize(new Dimension(150, 30));
        spotrebaPaliva.setPreferredSize(new Dimension(150, 30));
        add(spotrebaPaliva);
    }

    public void setNazev(String jmeno) {
        ((TitledBorder) getBorder()).setTitle(jmeno);
        repaint();
    }

    public void setMaxVykon(long maxVykon) {
        zdrojLabel.setText("Výkon (max " + maxVykon + ")");
    }

    public void setVykon(String vykon) {
        zdrojVykon.setText(vykon);
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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

    public void setSpotrebaPaliva(long spotreba) {
        spotrebaPaliva.setText(spotreba + " paliva/sec");
    }
}
