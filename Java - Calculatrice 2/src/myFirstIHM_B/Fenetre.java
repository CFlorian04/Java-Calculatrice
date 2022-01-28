
package myFirstIHM_B;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

	private JPanel myPrincipal;
	private JPanel myPanelScreen;
	private JPanel myPrincipalPanel;

	private JPanel panelChiffre;
	private JPanel panelSigne;
	private JPanel panelResultat;

	private JButton [] calc_button;
	private JLabel txt_calc, txt_rep;
	private String back_calc;
	private int length_tab;


	public Fenetre(){

		this.setResizable(false);
		//---------------------------CONTENEURS-------------------------------//

		//création des conteneurs

		myPrincipalPanel	= new JPanel();

		panelResultat		= new JPanel();
		panelChiffre		= new JPanel();
		panelSigne			= new JPanel();

		txt_calc			= new JLabel("");
		txt_rep				= new JLabel("");
		back_calc = "";


		//---------------------------BOUTONS-------------------------------//

		//dimension boutons
		Dimension dim = new Dimension(50,50);
		char txt_b[] = new char[] {'7','8','9','4','5','6','1','2','3','0',',','c'};
		char txt_s[] = new char[] {'/','*','-','+','='};
		length_tab = txt_b.length;
		JButton calc_button [] = new JButton[txt_b.length];

		for (int i = 0; i<txt_b.length;i++)
		{
			calc_button[i] = new JButton(""+txt_b[i]);
			calc_button[i].setBackground(Color.WHITE);
			calc_button[i].setFont(new Font("Verdana", Font.PLAIN, 15));
			calc_button[i].setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
			calc_button[i].setForeground(Color.BLACK);
			calc_button[i].setPreferredSize(dim);
			calc_button[i].addActionListener(this);
			panelChiffre.add(calc_button[i]);
		}

		for (int i = 0; i<3;i++)
		{
			calc_button[i] = new JButton("");
			calc_button[i].setPreferredSize(dim);
			panelChiffre.add(calc_button[i]);
			calc_button[i].setVisible(false);
		}

		for (int i = 0; i<txt_s.length;i++)
		{
			calc_button[i] = new JButton(""+txt_s[i]);
			calc_button[i].setBackground(Color.WHITE);
			calc_button[i].setFont(new Font("Verdana", Font.PLAIN, 15));
			calc_button[i].setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
			calc_button[i].setForeground(Color.BLACK);
			calc_button[i].setPreferredSize(dim);
			calc_button[i].addActionListener(this);
			panelSigne.add(calc_button[i]);
		}

		//---------------------------TEXTES/JLABELS-------------------------------//

		Dimension dimtxt = new Dimension(250,50);
		//txt_rep
		txt_rep.setFont(new Font("Verdana", Font.BOLD, 20));
		txt_rep.setMinimumSize(dimtxt);
		txt_rep.setMaximumSize(dimtxt);
		//txt_calc
		txt_calc.setFont(new Font("Verdana", Font.PLAIN, 25));
		txt_calc.setMinimumSize(dimtxt);
		txt_calc.setMaximumSize(dimtxt);

		//---------------------------CONFIGURATIONS JPANELS-------------------------------//

		// Configuration du panelSigne
		panelSigne.setPreferredSize(new Dimension(70,280));

		// Configuration du panelChiffre
		panelChiffre.setPreferredSize(new Dimension(170,280));

		//Configuration du panelResultat
		panelResultat.add(txt_rep);
		panelResultat.add(txt_calc);
		panelResultat.setPreferredSize(new Dimension(300,100));
		panelResultat.setBackground(Color.GRAY);


		// Configuration du conteneur myPrincipalPanel
		myPrincipalPanel.add(panelResultat,BorderLayout.NORTH);
		myPrincipalPanel.add(panelResultat,BorderLayout.CENTER);
		myPrincipalPanel.add(panelChiffre,BorderLayout.WEST);
		myPrincipalPanel.add(panelSigne,BorderLayout.EAST);




		//---------------------------CONFIGURATION FENETRE-------------------------------//

		// Configuration de la Fenetre
		this.setTitle("Calculatrice");
		this.setContentPane(myPrincipalPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	//---------------------------VARIABLES GLOBALES-------------------------------//

	String affichage_calc = new String(); //Affichage du calcul sur la calculatrice
	ArrayList<String> tab_nombre = new ArrayList<String>(); //Stock les nombres du calcul
	ArrayList<Character> tab_signe = new ArrayList<Character>(); //Stock les symboles du calcul
	String tamp_nombre = new String(); //Stock un nombre de calcul
	String resultat = new String(); //Récupère la valeur du résultat
	boolean dejacalc = false; //Si le calcul a déja été fait
	boolean avantnb = true; //Si il y a un nombre avant
	int j = 0;



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

		if( 48 <= (int) cara && (int) cara <= 57)
		{
			tamp_nombre += cara;
			affichage_calc += cara;
			txt_calc.setText(affichage_calc);
			avantnb = true;
		}
		else if ((int) cara == 45 || (int) cara == 43) // 45 = '-' et 43 = '+'
		{
			if(avantnb == true)
			{
				tab_signe.add(cara);
				tab_nombre.add(tamp_nombre);
				tamp_nombre = "";
			}
			else if(avantnb == false && (int) cara == 45)
			{
				tamp_nombre += cara;
			}
			else if((int) cara == 43)
			{
				tab_signe.add(cara);
				tab_nombre.add(tamp_nombre);
				tamp_nombre = "";
			}
			affichage_calc += cara;
			txt_calc.setText(affichage_calc);

			avantnb = false;
		}
		else if( 42 == (int) cara || (int) cara == 47)
		{
			tab_signe.add(cara);
			tab_nombre.add(tamp_nombre);
			tamp_nombre = "";

			affichage_calc += cara;
			txt_calc.setText(affichage_calc);

			avantnb = false;
			j++;
		}
		else if( cara == 'c' )
		{
			tamp_nombre = "";
			affichage_calc = "";
			txt_calc.setText("");
			txt_rep.setText("");
			tab_signe.clear();
			tab_nombre.clear();
		}
		else if( cara == '=' )
		{
			tab_nombre.add(' '+tamp_nombre);
			tamp_nombre = "";
			System.out.println('\n' + "Calcul -> " + affichage_calc);

			affichage_calc += cara;
			txt_calc.setText(affichage_calc);

			dejacalc = true;
			int signe_size = tab_signe.size();

			if(signe_size==0)
			{
				resultat = tab_nombre.get(0);
				txt_rep.setText(resultat);
			}

			for (int i = 0; i<signe_size; i++)
			{
				int placefois = tab_signe.indexOf('*'), placeplus = tab_signe.indexOf('+'), placemoins = tab_signe.indexOf('-'),placediv = tab_signe.indexOf('/') ;
				int apfois = placefois+1, applus = placeplus+1, apmoins = placemoins+1, apdiv = placediv+1;

				System.out.println("Nombres ->" + Arrays.deepToString(tab_nombre.toArray()) + " | Signes ->" + Arrays.deepToString(tab_signe.toArray()));

				float nb1 = 0, nb2 = 0, tamp_res = 0;
				//System.out.println("placefois = " + placefois + " placeplus = " + placeplus + " placediv = " + placediv + " placemoins = " + placemoins);
				//System.out.println("apfois = " + apfois + " applus = " + applus + " apdiv = " + apdiv + " apmoins = " + apmoins);


				if(placefois != -1 && placediv != -1)
				{
					if(placefois < placediv)
					{
						nb1 = Float.parseFloat(tab_nombre.get(placefois));
						nb2 = Float.parseFloat(tab_nombre.get(apfois));
						tamp_res = nb1 * nb2;
						System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

						tab_nombre.set(placefois, String.valueOf(tamp_res));
						tab_nombre.remove(apfois);
						tab_signe.remove(placefois);
					}
					else if(placefois > placediv)
					{
						nb1 = Float.parseFloat(tab_nombre.get(placediv));
						nb2 = Float.parseFloat(tab_nombre.get(apdiv));
						tamp_res = nb1 / nb2;
						System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

						tab_nombre.set(placediv, String.valueOf(tamp_res));
						tab_nombre.remove(apdiv);
						tab_signe.remove(placediv);
					}
				}
				else if(placefois != -1 && placediv == -1)
				{
					nb1 = Float.parseFloat(tab_nombre.get(placefois));
					nb2 = Float.parseFloat(tab_nombre.get(apfois));
					tamp_res = nb1 * nb2;
					System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

					tab_nombre.set(placefois, String.valueOf(tamp_res));
					tab_nombre.remove(apfois);
					tab_signe.remove(placefois);
				}
				else if(placefois == -1 && placediv != -1)
				{
					nb1 = Float.parseFloat(tab_nombre.get(placediv));
					nb2 = Float.parseFloat(tab_nombre.get(apdiv));
					tamp_res = nb1 / nb2;
					System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

					tab_nombre.set(placediv, String.valueOf(tamp_res));
					tab_nombre.remove(apdiv);
					tab_signe.remove(placediv);
				}
				else if(placeplus != -1 && placemoins != -1)
				{
					if(placeplus < placemoins)
					{
						nb1 = Float.parseFloat(tab_nombre.get(placeplus));
						nb2 = Float.parseFloat(tab_nombre.get(applus));
						tamp_res = nb1 + nb2;
						System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

						tab_nombre.set(placeplus, String.valueOf(tamp_res));
						tab_nombre.remove(applus);
						tab_signe.remove(placeplus);
					}
					else if(placeplus > placemoins)
					{
						nb1 = Float.parseFloat(tab_nombre.get(placemoins));
						nb2 = Float.parseFloat(tab_nombre.get(apmoins));
						tamp_res = nb1 - nb2;
						System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

						tab_nombre.set(placemoins, String.valueOf(tamp_res));
						tab_nombre.remove(apmoins);
						tab_signe.remove(placemoins);
					}
				}
				else if(placeplus != -1 && placemoins == -1)
				{
					nb1 = Float.parseFloat(tab_nombre.get(placeplus));
					nb2 = Float.parseFloat(tab_nombre.get(applus));
					tamp_res = nb1 + nb2;
					System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

					tab_nombre.set(placeplus, String.valueOf(tamp_res));
					tab_nombre.remove(applus);
					tab_signe.remove(placeplus);
				}
				else if(placeplus == -1 && placemoins != -1)
				{
					nb1 = Float.parseFloat(tab_nombre.get(placemoins));
					nb2 = Float.parseFloat(tab_nombre.get(apmoins));
					tamp_res = nb1 - nb2;
					System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);

					tab_nombre.set(placemoins, String.valueOf(tamp_res));
					tab_nombre.remove(apmoins);
					tab_signe.remove(placemoins);
				}
				System.out.println("résultat = " + tamp_res);
				resultat = String.valueOf(tamp_res);
				txt_rep.setText(resultat);
			}
			tab_signe.clear();
			tab_nombre.clear();
		}
	}
}

