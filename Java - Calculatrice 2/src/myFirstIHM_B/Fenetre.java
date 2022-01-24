
package myFirstIHM_B;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

	private JPanel myPanelPrincipal;
	private BorderLayout myBorderLayout;
	private JPanel panelSud;
	private JPanel panelNord;
	private JPanel panelCentre;

	private JButton [] calc_button;
	private JLabel txt_calc, txt_rep;
	private String back_calc;
	private int length_tab;

	
	public Fenetre(){
		//création des conteneurs
		myPanelPrincipal	= new JPanel();
		myBorderLayout 		= new BorderLayout();
		panelNord			= new JPanel();
		panelCentre			= new JPanel();
		panelSud			= new JPanel();
		txt_calc			= new JLabel("");
		txt_rep				= new JLabel("");
		back_calc = "";


		//dimension boutons
		Dimension dim = new Dimension(50,50);
		Dimension dimtxt = new Dimension(250,50);
		char txt_b[] = new char[] {'7','8','9','/','4','5','6','*','1','2','3','-','0','c','=','+'};
		length_tab = txt_b.length;
		JButton calc_button [] = new JButton[txt_b.length];

		for (int i = 0; i<txt_b.length;i++)
		{
			calc_button[i] = new JButton(""+txt_b[i]);
			calc_button[i].setFont(new Font("Verdana", Font.PLAIN, 15));
			calc_button[i].setPreferredSize(dim);
			calc_button[i].addActionListener(this);
			panelSud.add(calc_button[i]);
		}

		// Configuration du panelSud
		panelSud.setPreferredSize(new Dimension(250,240));


		//Configuration du panelNord
		panelCentre.setBackground(Color.WHITE);
		panelCentre.add(txt_rep);
		txt_rep.setFont(new Font("Verdana", Font.BOLD, 20));
		txt_rep.setMinimumSize(dimtxt);
		txt_rep.setMaximumSize(dimtxt);
		panelCentre.setPreferredSize(new Dimension(250,50));

		//Configuration du panelCentre
		panelNord.setBackground(Color.WHITE);
		panelNord.add(txt_calc);
		txt_calc.setMinimumSize(dimtxt);
		txt_calc.setMaximumSize(dimtxt);
		txt_calc.setFont(new Font("Verdana", Font.PLAIN, 25));
		panelNord.setPreferredSize(new Dimension(250,50));


		// Configuration du conteneur JPanel principal		
		myPanelPrincipal.setLayout(myBorderLayout);
		myPanelPrincipal.add(panelNord,BorderLayout.NORTH);
		myPanelPrincipal.add(panelCentre,BorderLayout.CENTER);
		myPanelPrincipal.add(panelSud,BorderLayout.SOUTH);
		
		// Configuration de la Fenetre
		this.setTitle("Calculatrice");
		this.setContentPane(myPanelPrincipal);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(250, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	String affichage_calc = new String();
	ArrayList<String> tab_nombre = new ArrayList<String>();
	ArrayList<Character> tab_signe = new ArrayList<Character>();
	String tamp_nombre = new String();
	String resultat = new String();
	boolean dejacalc = false, avantnb = true;
	int j=0;

	public void actionPerformed(ActionEvent e){
		//récupère la valeur du bouton
		JButton o = (JButton)e.getSource();
		String txt = o.getText();
		char cara = txt.toLowerCase().charAt(0);

		if(dejacalc == true)
		{
			affichage_calc = "";
			txt_calc.setText(affichage_calc);
			dejacalc = false;
		}

		if( 48 <= (int) cara && (int) cara <= 57 || (int) cara == 45)
		{

			if(avantnb == true && (int) cara == 45)
			{
				tab_signe.add(cara);
				tab_nombre.add(tamp_nombre);
				tamp_nombre = "";
			}
			else
				tamp_nombre += cara;

			affichage_calc += cara;
			txt_calc.setText(affichage_calc);
			avantnb = true;
		}
		else if( 42 <= (int) cara && (int) cara <= 47 && (int) cara != 45)
		{
			tab_signe.add(cara);
			tab_nombre.add(tamp_nombre);
			tamp_nombre = "";

			affichage_calc += cara;
			txt_calc.setText(affichage_calc);

			avantnb = false;
			j++;

			System.out.println("Nombres ->" + Arrays.deepToString(tab_nombre.toArray()) + " | Signes ->" + Arrays.deepToString(tab_signe.toArray()));
		}
		else if( cara == 'c' )
		{
			tamp_nombre = "";
			affichage_calc = "";
			txt_calc.setText(affichage_calc);
		}
		else if( cara == '=' )
		{
			tab_nombre.add(tamp_nombre);
			tamp_nombre = "";

			affichage_calc += cara;
			txt_calc.setText(affichage_calc);

			dejacalc = true;

			System.out.println("Nombres ->" + Arrays.deepToString(tab_nombre.toArray()) + " | Signes ->" + Arrays.deepToString(tab_signe.toArray()));
		}



	}
}

