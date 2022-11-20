import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GUI1 extends JFrame {
    private JButton kupBiletButton;
    private JButton listaSensowButton;
    private JButton dodajSeansButton;
    private JButton zalogujSieButton;
    private JButton zarejestrujSieButton;
    private JPanel panel;
    private JLabel uzytkownikLabel;
    private JLabel logoLabel;
    private JLabel userLabel;


    public void setUserLabel(String nazwa) {
        this.userLabel.setText(nazwa);
    }

    public String getUser(){return this.userLabel.getText();}
    public GUI1(){
        this.setContentPane(this.panel);
        this.setTitle("Main window");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

//        uzytkownikLabel.setText(Main.loggedUser);

        listaSensowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI1.super.setVisible(false);
                GUI_lista_seansow gui_lista_seansow = new GUI_lista_seansow();
            }
        });
        dodajSeansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userLabel.getText().equals("admin")){
                    GUI1.super.setVisible(false);
                    GUI_dodaj_seans gui_dodaj_seans = new GUI_dodaj_seans();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wymagane uprawnienia admina!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        zalogujSieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI1.super.setVisible(false);
                GUI_login gui_login = new GUI_login();
            }
        });
        zarejestrujSieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI1.super.setVisible(false);
                GUI_registration gui_registration = new GUI_registration();
            }
        });
        kupBiletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userLabel.getText().equals("Niezalogowano")) {
                    JOptionPane.showMessageDialog(null, "Proszę się zalogować, aby kupić bilet", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    GUI1.super.setVisible(false);
                    GUI_kup_bilet gui_kup_bilet = new GUI_kup_bilet();
                }
            }
        });
    }

}
