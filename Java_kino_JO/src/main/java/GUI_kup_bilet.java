import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_kup_bilet extends JFrame {
    private JTextField idFilmuTextField;
    private JButton kupBiletButton;
    private JButton cofnijButton;
    private JPanel panel;

    public GUI_kup_bilet() {

        this.setContentPane(this.panel);
        this.setTitle("kup bilet");
        this.setSize(600, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI_kup_bilet.super.setVisible(false);
                GUI1 gui1 = new GUI1();
            }
        });
        kupBiletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idFilmuTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Proszę podać ID filmu do kupna", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (FileOps.parseFileForID("repertuar.csv",idFilmuTextField.getText()) != -1) {
                    GUI_kup_bilet.super.setVisible(false);
                    GUI_wybormiejsc_code gui_wybormiejsc_code = new GUI_wybormiejsc_code(idFilmuTextField.getText());
                }
                else {
                    JOptionPane.showMessageDialog(null, "Nie ma filmu o takim ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
