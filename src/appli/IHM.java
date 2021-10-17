import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Toolkit;

public class IHM {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				IHMwindow fenetre = new IHMwindow();
				fenetre.setVisible(true);
			}
		});
	}
}