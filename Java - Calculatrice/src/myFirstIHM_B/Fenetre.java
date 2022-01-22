
package myFirstIHM_B;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
		char txt_b[] = new char[] {'7','8','9','/','4','5','6','*','1','2','3','-','0','C','=','+'};
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


	public void actionPerformed(ActionEvent e){
		//récupère la valeur du bouton
		JButton o = (JButton)e.getSource();
		String txt = o.getText();



		if(!txt.equals("C") && !txt.equals("="))
		{
			//ajoute la valeur du bouton dans un calcul et affiche le calcul
			back_calc = back_calc + txt;
			txt_calc.setText(back_calc);
		}
		else if(txt.equals("C"))
		{
			//efface le calcul et l'affiche
			back_calc = "";
			txt_calc.setText(back_calc);
		}
		else if(txt.equals("="))
		{
			char[] tab_calc = back_calc.toCharArray(); //converti le calcul (string) vers un tableau (char)
			System.out.println('\n' + "---------------------------" + '\n' + Arrays.toString(tab_calc));


			int[][] symbole_p = new int[2][10];
			// [0][X] -> deuxieme
			// [1][X] -> premier

			int p=0, d=0;
			for (int x = 0; x<tab_calc.length;x++)
			{
				if(tab_calc[x] == '/' || tab_calc[x] == '*')
				{
					symbole_p[1][p] = x; //ajoute la position du caractère dans le tableau [1][x]
					p++;
				}
				if(tab_calc[x] == '-' || tab_calc[x] == '+')
				{
					symbole_p[0][d] = x; //ajoute la position du caractère dans le tableau [0][x]
					d++;
				}
			}


			//Permet de réduire la taille du tableau symbole_p en fonction du nombre de caractère de calcul (+,-,*,/)
			for (int i = 0;i <2;i++)
			{
				for(int y = 1;y<symbole_p[i].length;y++)
				{
					if(symbole_p[i][y] == 0)
					{
						symbole_p[i] = Arrays.copyOf(symbole_p[i], y);
					}
				}
			}
			System.out.println(Arrays.deepToString(symbole_p));


			for (int i = 1;i >=0;i--)
			{
				for (int x = 0; x < symbole_p[i].length; x++)
				{
					int current_symbole = symbole_p[i][x]; //recupère la position du symbole
					if(current_symbole >0)
					{
						System.out.println( '\n' + "current_symbole = " + current_symbole + " -> " + tab_calc[current_symbole] );
						int post_c = 0, past_c =tab_calc.length;

						for (int u = 1;u >=0 ;u--)
						{
							for (int v = 0; v < symbole_p[i].length; v++)
							{
								//Permet de trouver les symboles les plus proches de celui sélectionné
								if(past_c > symbole_p[u][v] && symbole_p[u][v] > current_symbole)
									past_c = symbole_p[u][v];

								if(post_c < symbole_p[u][v] && symbole_p[u][v] < current_symbole)
									post_c = symbole_p[u][v];
							}
						}
						System.out.println("post_c = " + post_c + " & past_c = " + past_c);

						//Récupère les valeurs du tableau corespondant aux nombre entre les symboles les plus proches
						String past_n = "", post_n = "";
						for (int k = current_symbole+1 ; k< past_c; k++)
						{
							past_n = past_n + tab_calc[k];
						}

						for (int k = post_c ; k< current_symbole; k++)
						{
							post_n = post_n + tab_calc[k];
						}

						//Résout une erreur si le symbole est en [1]
						if(current_symbole == 1 && post_c == 0)
							post_n = String.valueOf(tab_calc[0]);
						System.out.println("post_n -> " + post_n + " & past_n -> " + past_n);

						//Converti les char du calc en int
						int post_calc = Integer.parseInt(post_n), past_calc = Integer.parseInt(past_n);
						System.out.println("post_calc -> " + post_calc + " & past_calc -> " + past_calc);

						// Switch qui permet de savoir la valeur du symbole
						int tamp_calc = 0, newlength;
						String tamp_txt;
						switch (tab_calc[current_symbole])
						{
							case '+' :
								//calcul le résultat
								tamp_calc = post_calc+past_calc;
								System.out.println("tamp_calc = " + tamp_calc);

								//Conversion du resultat en string
								tamp_txt = String.valueOf(tamp_calc);

								//Met le résultat dans tab_calc à sa position
								for(int t = 0; t < tamp_txt.length(); t++) if(tamp_txt.charAt(t) == '-')
								{
									tab_calc[post_c+t] = tamp_txt.charAt(t);
								}
								else
								{
									int j = Character.digit(tamp_txt.charAt(t), 10);
									tab_calc[post_c+t] = (char) (j+'0');
								}

								//rapproche les char dans le tab
								for(int l = post_c+tamp_txt.length(); l<tab_calc.length-tamp_txt.length(); l++)
								{
									tab_calc[l] = tab_calc[l+tamp_txt.length()];
								}

								//Redimensionne le tableau avec le résultat
								newlength = tab_calc.length - (past_c-post_c) + tamp_txt.length();
								System.out.println("newlength = " + newlength);
								tab_calc = Arrays.copyOf(tab_calc, newlength );
								System.out.println(Arrays.toString(tab_calc));

								break;

							case '-' :
								//calcul le résultat
								tamp_calc = post_calc-past_calc;
								System.out.println("tamp_calc = " + tamp_calc);

								//Conversion du resultat en string
								tamp_txt = String.valueOf(tamp_calc);

								//Met le résultat dans tab_calc à sa position
								for(int t = 0; t < tamp_txt.length(); t++)if(tamp_txt.charAt(t) == '-')
								{
									tab_calc[post_c+t] = tamp_txt.charAt(t);
								}
								else
								{
									int j = Character.digit(tamp_txt.charAt(t), 10);
									tab_calc[post_c+t] = (char) (j+'0');
								}

								//rapproche les char dans le tab
								for(int l = post_c+tamp_txt.length(); l<tab_calc.length-tamp_txt.length(); l++)
								{
									tab_calc[l] = tab_calc[l+tamp_txt.length()];
								}

								//Redimensionne le tableau avec le résultat
								newlength = tab_calc.length - (past_c-post_c) + tamp_txt.length();
								System.out.println("newlength = " + newlength);
								tab_calc = Arrays.copyOf(tab_calc, newlength );
								System.out.println(Arrays.toString(tab_calc));

								break;

							case '*' :
								//calcul le résultat
								tamp_calc = post_calc*past_calc;
								System.out.println("tamp_calc = " + tamp_calc);

								//Conversion du resultat en string
								tamp_txt = String.valueOf(tamp_calc);

								//Met le résultat dans tab_calc à sa position
								for(int t = 0; t < tamp_txt.length(); t++) if(tamp_txt.charAt(t) == '-')
								{
									tab_calc[post_c+t] = tamp_txt.charAt(t);
								}
								else
								{
									int j = Character.digit(tamp_txt.charAt(t), 10);
									tab_calc[post_c+t] = (char) (j+'0');
								}

								//rapproche les char dans le tab
								for(int l = post_c+tamp_txt.length(); l<tab_calc.length-tamp_txt.length(); l++)
								{
									tab_calc[l] = tab_calc[l+tamp_txt.length()];
								}

								//Redimensionne le tableau avec le résultat
								newlength = tab_calc.length - (past_c-post_c) + tamp_txt.length();
								System.out.println("newlength = " + newlength);
								tab_calc = Arrays.copyOf(tab_calc, newlength );
								System.out.println(Arrays.toString(tab_calc));

								break;

							case '/' :
								//calcul le résultat
								tamp_calc = post_calc/past_calc;
								System.out.println("tamp_calc = " + tamp_calc);

								//Conversion du resultat en string
								tamp_txt = String.valueOf(tamp_calc);

								//Met le résultat dans tab_calc à sa position
								for(int t = 0; t < tamp_txt.length(); t++) {
									if(tamp_txt.charAt(t) == '-')
									{
										tab_calc[post_c+t] = tamp_txt.charAt(t);
									}
									else
									{
										int j = Character.digit(tamp_txt.charAt(t), 10);
										tab_calc[post_c+t] = (char) (j+'0');
									}

								}

								//rapproche les char dans le tab
								for(int l = post_c+tamp_txt.length(); l<tab_calc.length-tamp_txt.length(); l++)
								{
									tab_calc[l] = tab_calc[l+tamp_txt.length()];
								}

								//Redimensionne le tableau avec le résultat
								newlength = tab_calc.length - (past_c-post_c) + tamp_txt.length();
								System.out.println("newlength = " + newlength);
								tab_calc = Arrays.copyOf(tab_calc, newlength );
								System.out.println(Arrays.toString(tab_calc));

								break;
						}
						symbole_p[i][x] = 0;
						txt_rep.setText(String.valueOf(tab_calc));


					}
					//System.out.println("No current symbole");

				}
			}
		}
	}
}

