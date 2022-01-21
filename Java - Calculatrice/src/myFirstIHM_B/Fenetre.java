
package myFirstIHM_B;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

	private JPanel myPanelPrincipal;
	private BorderLayout myBorderLayout;
	private JPanel panelSud;
	private JPanel panelCentre;

	private JButton [] calc_button;
	private JLabel txt_calc;
	private String back_calc;
	private int length_tab;

	
	public Fenetre(){
		//création des conteneurs
		myPanelPrincipal	= new JPanel();
		myBorderLayout 		= new BorderLayout();
		panelCentre			= new JPanel();
		panelSud			= new JPanel();
		txt_calc			= new JLabel();
		back_calc = "";


		//dimension boutons
		Dimension dim = new Dimension(50,50);
		char txt_b[] = new char[] {'7','8','9','/','4','5','6','x','1','2','3','-','0','C','=','+'};
		length_tab = txt_b.length;
		JButton calc_button [] = new JButton[txt_b.length];

		for (int i = 0; i<txt_b.length;i++)
		{
			calc_button[i] = new JButton(""+txt_b[i]);
			calc_button[i].setPreferredSize(dim);
			calc_button[i].addActionListener(this);
			panelSud.add(calc_button[i]);
		}

		// Configuration du panelSud
		panelSud.setPreferredSize(new Dimension(250,240));


		//Configuration du panelCentre
		panelCentre.setBackground(Color.WHITE);
		panelCentre.add(txt_calc);
		panelCentre.setPreferredSize(new Dimension(250,100));


		// Configuration du conteneur JPanel principal		
		myPanelPrincipal.setLayout(myBorderLayout);
		myPanelPrincipal.add(panelCentre,BorderLayout.CENTER);
		myPanelPrincipal.add(panelSud,BorderLayout.SOUTH);
		
		// Configuration de la Fenetre
		this.setTitle("Seconde Application");
		this.setContentPane(myPanelPrincipal);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(250, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);



	}


	public void actionPerformed(ActionEvent e){
		//((JButton)e.getSource()).getText()
		String txt = ((JButton)e.getSource()).getText();

		if(txt != "C" && txt != "=")
		{
			back_calc = back_calc + txt;
			txt_calc.setText(back_calc);
		}
		else if(txt == "C")
		{
			back_calc = "";
		}
		else if(txt == "=")
		{

		}


	}

}

