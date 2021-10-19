import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Toolkit;

public class IHMwindow extends JFrame{

    IHMwindow()
    {
        super();

        build();
    }

    private void build(){
        setTitle("Communicatif");
        setSize(1000, 600);
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer la fenêtre lors du clic sur la croix

        this.buildContentPane();
    }

    private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
 
		JTextField textField = new JTextField();
 
		panel.add(textField);
 
		JLabel label = new JLabel("Rien pour le moment");
 
		panel.add(label);
 
		//JButton bouton = new JButton(new GetAction(this, "Changer le texte de place"));
 
		//panel.add(bouton);
 
		return panel;
	}
}