import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GUI_wybormiejsc_code extends JFrame implements ActionListener {

    String idFilmu;
    List<JRadioButton> radioButtonList;
    List<String> wykupioneMiejsca;

    public GUI_wybormiejsc_code(String idFilmu) {
        JFrame frame = new JFrame();
        frame.setTitle("Wybór miejsc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(true);
        this.idFilmu = idFilmu;

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel panelRadioButtons = new JPanel(new GridLayout(3, 3, 50, 50));
        JPanel panelWykupione = new JPanel(new GridLayout(2, 1, 10, 10));
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();
        JButton cofnijButton = new JButton();
        JButton kupBiletButton = new JButton();
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        Dimension d1 = new Dimension(20, 100);
        scrollPane.setPreferredSize(d1);

        cofnijButton.addActionListener(this);
        cofnijButton.setActionCommand("cofnij");
        kupBiletButton.addActionListener(this);
        kupBiletButton.setActionCommand("kup");


        radioButtonList = new ArrayList<>();

        c.insets = new Insets(5, 50, 5, 50);


//        // label1
//        label1.setText("Proszę wybrać miejsce:");
//        c.gridx = 0;
//        c.gridy = 0;
//        c.ipadx = 10;
//        c.ipady = 10;
//        panel.add(label1,c);

        //cofnijButton
        cofnijButton.setText("Cofnij");
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 27;
        c.ipady = 10;
        panel.add(cofnijButton, c);

//        // empty label
//        JLabel temp = new JLabel();
//        temp.setText("");
//        c.gridx = 1;
//        c.gridy = 5;
//        c.ipadx = 10;
//        c.ipady = 200;
//        panel.add(temp,c);

        //kupButton
        kupBiletButton.setText("Kup bilet");
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        panel.add(kupBiletButton, c);

        // juz wykupione panel
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;
        panel.add(panelWykupione, c);

        // label2
        label2.setText("Już wykupione");
        panelWykupione.add(label2);

        // scrollpane
        panelWykupione.add(scrollPane);

        // label3 (ekran)
        label3.setText("EKRAN");
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        c.gridwidth = 3;
        panel.add(label3, c);

        // label4 (-----)
        label4.setText("--------------------------------------------------------------------");
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        c.gridwidth = 3;
        panel.add(label4, c);

        // radioPanel
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        panel.add(panelRadioButtons, c);

        // seats
        int seatNum = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                seatNum++;
                String fs = String.format("seat %s", Integer.toString(seatNum));
                JRadioButton seat = new JRadioButton();
                radioButtonList.add(seat);
                seat.setText(fs);
                seat.setForeground(Color.green);
                panelRadioButtons.add(seat);
            }
        }

        frame.add(panel);
//        frame.pack();
        frame.setVisible(true);


        wykupioneMiejsca = FileOps.getLine("zakupioneMiejsca.csv", idFilmu);
        List<Integer> listaNumerowZajetychMiejsc = new ArrayList<Integer>();

        for (int i=1; i<wykupioneMiejsca.size(); i++) {
            String seatName = wykupioneMiejsca.get(i);
            int seatNumber =  Character.getNumericValue(seatName.charAt(seatName.length() - 1));
            listaNumerowZajetychMiejsc.add(seatNumber);
        }

        for (int i : listaNumerowZajetychMiejsc) {
            radioButtonList.get(i-1).setForeground(Color.RED);
        }

        if (!wykupioneMiejsca.isEmpty()) {
            Iterator<String> iter = wykupioneMiejsca.iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (!s.contains("seat")) iter.remove();
            }

            String[] wykupioneMiejscaArr = wykupioneMiejsca.toArray(new String[wykupioneMiejsca.size()]);

            String[][] wykupioneMiejsca1 = new String[wykupioneMiejsca.size()][2];
            for (int i = 0; i < wykupioneMiejsca.size(); i++) {
                wykupioneMiejsca1[i][0] = wykupioneMiejscaArr[i];
                wykupioneMiejsca1[i][1] = "-";
            }

            String[] colNames = {"Nr miejsca", "-"};
            DefaultTableModel tabela = new DefaultTableModel(wykupioneMiejsca1, colNames);
            table.setModel(tabela);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = ((JButton) e.getSource()).getActionCommand();

        switch (actionCommand) {
            case "cofnij":
                GUI_wybormiejsc_code.super.setVisible(false);
                GUI1 gui11 = new GUI1();
                break;
            case "kup":
                boolean seatSelected = false;
                List<String> listaMiejsc = new ArrayList<>();
                listaMiejsc.add(idFilmu);

                for (int i=0; i< radioButtonList.size(); i++) {
                    JRadioButton seat = radioButtonList.get(i);
                    if (seat.isSelected()) {
                        seatSelected = true;
                        listaMiejsc.add("seat" + Integer.toString(i+1));
                    }
                }
                if (seatSelected) {
                    List<String> commonElements = new ArrayList<>(wykupioneMiejsca);
                    commonElements.retainAll(listaMiejsc);

                    if (commonElements.isEmpty()) {
                        FileOps.writeToFile("zakupioneMiejsca.csv", listaMiejsc);
                        JOptionPane.showMessageDialog(null, "Zakupiono miejsca", "Zakup pomyślny", JOptionPane.INFORMATION_MESSAGE);
                        GUI_wybormiejsc_code.super.setVisible(false);
                        GUI1 gui1 = new GUI1();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Wybrane miejsca są zajęte", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać miejsce", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}
