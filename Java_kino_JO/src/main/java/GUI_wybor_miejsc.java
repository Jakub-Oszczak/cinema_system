import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public class GUI_wybor_miejsc extends JFrame{

    public String idFilmu;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JRadioButton radioButton7;
    private JRadioButton radioButton8;
    private JRadioButton radioButton9;
    private JButton kupBiletButton;
    private JPanel panel;
    private JButton cofnijButton;
    private JTable tabelaRezerwacji;

    public GUI_wybor_miejsc(String idFilmu) {

        this.setContentPane(this.panel);
        this.setTitle("wybór miejsc");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.idFilmu = idFilmu;

        List<String> wykupioneMiejsca = FileOps.getLine("zakupioneMiejsca.csv",idFilmu);

        if (!wykupioneMiejsca.isEmpty()) {
            Iterator<String> iter = wykupioneMiejsca.iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (!s.contains("seat")) iter.remove();
            }

            String [] wykupioneMiejscaArr = wykupioneMiejsca.toArray(new String[wykupioneMiejsca.size()]);

            String [][] wykupioneMiejsca1 = new String[wykupioneMiejsca.size()][2];
            for (int i=0; i<wykupioneMiejsca.size();i++) {
                wykupioneMiejsca1[i][0] = wykupioneMiejscaArr[i];
                wykupioneMiejsca1[i][1] = "-";
            }

            String [] colNames = {"Nr miejsca", "-"};
            DefaultTableModel tabela = new DefaultTableModel(wykupioneMiejsca1, colNames);
            tabelaRezerwacji.setModel(tabela);
        }

        kupBiletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton1.isSelected() || radioButton2.isSelected() || radioButton3.isSelected() || radioButton4.isSelected() || radioButton5.isSelected() || radioButton6.isSelected() || radioButton7.isSelected() || radioButton8.isSelected() || radioButton9.isSelected()) {
                    List<String> listaMiejsc = new ArrayList<>();
                    listaMiejsc.add(idFilmu);

                    if (radioButton1.isSelected()){listaMiejsc.add("seat1");}
                    if (radioButton2.isSelected()){listaMiejsc.add("seat2");}
                    if (radioButton3.isSelected()){listaMiejsc.add("seat3");}
                    if (radioButton4.isSelected()){listaMiejsc.add("seat4");}
                    if (radioButton5.isSelected()){listaMiejsc.add("seat5");}
                    if (radioButton6.isSelected()){listaMiejsc.add("seat6");}
                    if (radioButton7.isSelected()){listaMiejsc.add("seat7");}
                    if (radioButton8.isSelected()){listaMiejsc.add("seat8");}
                    if (radioButton9.isSelected()){listaMiejsc.add("seat9");}

                    List<String> commonElements = new ArrayList<>(wykupioneMiejsca);
                    commonElements.retainAll(listaMiejsc);

                    if (commonElements.isEmpty()){
                        FileOps.writeToFile("zakupioneMiejsca.csv",listaMiejsc);
                        JOptionPane.showMessageDialog(null, "Zakupiono miejsca", "Zakup pomyślny", JOptionPane.INFORMATION_MESSAGE);
                        GUI_wybor_miejsc.super.setVisible(false);
                        GUI1 gui1 = new GUI1();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Wybrane miejsca są zajęte", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać miejsce", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_wybor_miejsc.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });
    }
}
