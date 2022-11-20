import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI_registration extends JFrame {
    private JButton zarejestrujSieButton;
    private JTextField passwordTextField;
    private JTextField loginTextField;
    private JPanel panel;
    private JButton cofnijButton;

    public GUI_registration() {
        this.setContentPane(this.panel);
        this.setTitle("Rejestracja");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        zarejestrujSieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginTextField.getText().equals("") || passwordTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String login = loginTextField.getText();
                    String password = passwordTextField.getText();

                    boolean loginExists = FileOps.parseFileForLogin("users.csv",login);
                    if (loginExists) {
                        JOptionPane.showMessageDialog(null, "Taki użytkownik już istnieje!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        try{
                            if (password.indexOf('@') == -1 && password.indexOf('#') == -1 && password.indexOf('*') == -1) {
                                FileOps.writeToFile("users.csv", Arrays.asList(login, password));
                                JOptionPane.showMessageDialog(null, "Zarejestrowano pomyślnie!", "Info", JOptionPane.INFORMATION_MESSAGE);
                                GUI_registration.super.setVisible(false);
                                GUI1 gui1 = new GUI1();
                                gui1.setUserLabel(login);
                                Main.listaUzytkownikow.add(new User(login,password));
                            }
                            else {
                                throw new CustomException("Hasło nie może zawierać znaków specjalnych!");
                            }
                        }
                        catch (CustomException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }


            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_registration.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });
    }
}
