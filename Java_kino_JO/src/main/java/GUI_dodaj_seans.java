import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class GUI_dodaj_seans extends JFrame {
    private JPanel panel;
    private JTextField nazwaTextField;
    private JComboBox gatunekComBox;
    private JComboBox rokComBox;
    private JComboBox godzinaComBox;
    private JComboBox salaComBox;
    private JButton dodajButton;
    private JButton cofnijButton;


    public GUI_dodaj_seans() {
        this.setContentPane(this.panel);
        this.setTitle("Dodaj seans");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_dodaj_seans.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nazwaTextField.getText().isEmpty() || gatunekComBox.getSelectedItem().toString().equals("--Wybierz gatunek--") || rokComBox.getSelectedItem().toString().equals("--Wybierz rok--") || godzinaComBox.getSelectedItem().toString().equals("--Wybierz godzine--") || salaComBox.getSelectedItem().toString().equals("--Wybierz salę--")) {
                    JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString = uuid.toString();
                    randomUUIDString = randomUUIDString.substring(0,4);

                    List<String> cechyFilmu = new ArrayList<>();
                    cechyFilmu.add(randomUUIDString);
                    cechyFilmu.add(nazwaTextField.getText());
                    cechyFilmu.add(gatunekComBox.getSelectedItem().toString());
                    cechyFilmu.add(rokComBox.getSelectedItem().toString());
                    cechyFilmu.add(godzinaComBox.getSelectedItem().toString());
                    cechyFilmu.add(salaComBox.getSelectedItem().toString());

                    FileOps.writeToFile("repertuar.csv", cechyFilmu);

                    Seans seans = new Seans(nazwaTextField.getText(),gatunekComBox.getSelectedItem().toString(),rokComBox.getSelectedItem().toString(),godzinaComBox.getSelectedItem().toString(),salaComBox.getSelectedItem().toString());
                    Main.listaFilmow.add(seans);

                    JOptionPane.showMessageDialog(null, "Dodano seans", "Dodano seans", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        String [] gatunki = {"--Wybierz gatunek--", "Akcja", "Sci-fi", "Dramat", "Horror", "Komedia", "Kryminał"};

        for (int i = 0; i < gatunki.length; i++) {
            gatunekComBox.addItem(gatunki[i]);
        }

        rokComBox.addItem("--Wybierz rok--");
        for (int i = 1900; i <= 2022; i++) {
            rokComBox.addItem(String.valueOf(i));
        }

        godzinaComBox.addItem("--Wybierz godzine--");

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);

        do {
            godzinaComBox.addItem(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 15);
        } while (calendar.getTime().before(end.getTime()));

        String [] sala = {"--Wybierz salę--","1", "2", "3", "4", "5", "6", "7"};

        for (int i = 0; i < sala.length; i++) {
            salaComBox.addItem(sala[i]);
        }
    }
}