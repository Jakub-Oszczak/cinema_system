import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_login extends JFrame {
    private JTextField loginTextField;
    private JPanel panel;
    private JTextField passwordTextField;
    private JButton zalogujSieButton;
    private JButton cofnijButton;

    public GUI_login() {
        this.setContentPane(this.panel);
        this.setTitle("Logowanie");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        zalogujSieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText();
                String password = passwordTextField.getText();
                boolean loginExists = FileOps.parseFileForLogin("users.csv", login);
                if (loginExists) {
                    if (FileOps.getPassword("users.csv",login).equals(password)) {
                        JOptionPane.showMessageDialog(null, "Zalogowano pomyślnie!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        GUI_login.super.setVisible(false);
                        GUI1 gui1 = new GUI1();
                        gui1.setUserLabel(login);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Błędne hasło!", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Nie ma takiego użytkownika. Proszę się zarejestrować.", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_login.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });
    }

}
