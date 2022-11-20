import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI_lista_seansow extends JFrame {
    private JPanel panel;
    private JScrollPane scrollpane;
    private JTable tabelaFilmow;
    private JButton cofnijButton;
    private JButton sortujButton;
    private JTextField usunTextField;
    private JButton usunButton;
    private JComboBox sortujComboBox;
    private JButton reverseButton;


    public GUI_lista_seansow() {
        this.setContentPane(this.panel);
        this.setTitle("Lista seansów");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        String [][] repertuar =  FileOps.readFile("repertuar.csv");
        List<List<String>> repertuarList =  FileOps.readFileToList("repertuar.csv");

        String [] colNames = {"ID","Nazwa","Gatunek","Rok","Godzina","Sala"};
        DefaultTableModel tabela = new DefaultTableModel(repertuar, colNames);
        tabelaFilmow.setModel(tabela);

        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_lista_seansow.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });

        sortujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeMap<String, List<String>> sortedRepertuar = new TreeMap<>();
                String selected = sortujComboBox.getSelectedItem().toString();
                boolean reverse = false;

                if (selected.equals("nazwa")) {sortedRepertuar = sortMap(repertuarList,1);}
                else if (selected.equals("gatunek")) {sortedRepertuar = sortMap(repertuarList,2);}
                else if (selected.equals("rok")) {sortedRepertuar = sortMap(repertuarList,3);}
                else if (selected.equals("godzina")) {sortedRepertuar = sortMap(repertuarList,4);}
                else if (selected.equals("sala")) {sortedRepertuar = sortMap(repertuarList,5);}
                else if (selected.equals("nazwa rev")) {sortedRepertuar = sortMap(repertuarList,1); reverse = true;}
                else if (selected.equals("gatunek rev")) {sortedRepertuar = sortMap(repertuarList,2); reverse = true;}
                else if (selected.equals("rok rev")) {sortedRepertuar = sortMap(repertuarList,3); reverse = true;}
                else if (selected.equals("godzina rev")) {sortedRepertuar = sortMap(repertuarList,4); reverse = true;}
                else if (selected.equals("sala rev")) {sortedRepertuar = sortMap(repertuarList,5); reverse = true;}

                Collection<List<String>> sortedRepertuarVal = sortedRepertuar.values();
                List<List<String>> sortedRepertuarValList = new ArrayList(sortedRepertuarVal);

                String[][] repertuar2Darr = new String[sortedRepertuarValList.size()][];

                int i = 0;
                for (List<String> nestedList : sortedRepertuarValList) {
                    repertuar2Darr[i++] = nestedList.toArray(new String[0]);
                }

                if (reverse) {
                    for(int j = 0; j < (repertuar2Darr.length / 2); j++) {
                        String[] temp = repertuar2Darr[j];
                        repertuar2Darr[j] = repertuar2Darr[repertuar2Darr.length - j - 1];
                        repertuar2Darr[repertuar2Darr.length - j - 1] = temp;
                    }
                }

                String [] colNames = {"ID","Nazwa","Gatunek","Rok","Godzina","Sala"};
                DefaultTableModel tabela = new DefaultTableModel(repertuar2Darr, colNames);
                tabelaFilmow.setModel(tabela);
            }
        });
        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usunTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Proszę podać ID filmu do usunięcia", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String id = usunTextField.getText();
                    int idLineNum = FileOps.parseFileForID("repertuar.csv", id);
                    FileOps.deleteLineFromFile("repertuar.csv", idLineNum);
                    GUI_lista_seansow.super.setVisible(false);
                    GUI_lista_seansow gui_lista_seansow = new GUI_lista_seansow();
                    JOptionPane.showMessageDialog(null, "Seans został usunięty pomyślnie", "Usunięto seans", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static TreeMap sortMap(List<List<String>> repertuarList, int col) {
        TreeMap<String, List<String>> sortedLista = new TreeMap<>();

        int iter = 0;

        for (int i = 0; i<repertuarList.size(); i++) {
            if (sortedLista.containsKey(repertuarList.get(i).get(col))) {
                String new_key = repertuarList.get(i).get(col) + 'a'*iter;
                sortedLista.put(new_key,repertuarList.get(i));
                iter++;
            }
            else {
                sortedLista.put(repertuarList.get(i).get(col),repertuarList.get(i));
            }
        }

        return sortedLista;
    }

}