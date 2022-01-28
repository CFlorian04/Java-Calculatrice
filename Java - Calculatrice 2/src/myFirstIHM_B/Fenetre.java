package myFirstIHM_B;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {

    private JPanel myPrincipal;
    private JPanel myPanelScreen;
    private JPanel myPrincipalPanel;

    private JPanel panelClavier;
    private JPanel panelCalcul;
    private JPanel panelResultat;

    private JButton[] calc_button;
    private JLabel txt_calc, txt_rep;
    private String back_calc;
    private int length_tab;


    public Fenetre() {

        this.setResizable(false);

        //---------------------------CONTENEURS-------------------------------//

        //création des conteneurs

        myPrincipalPanel = new JPanel();

        panelResultat = new JPanel();
        panelCalcul = new JPanel();
        panelClavier = new JPanel();

        txt_calc = new JLabel("");
        txt_rep = new JLabel("");
        back_calc = "";


        //---------------------------BOUTONS-------------------------------//

        //dimension boutons
        Dimension dim = new Dimension(50, 50);
        char txt_b[] = new char[]{'7', '8', '9', '/', '4', '5', '6', 'x', '1', '2', '3', '-', 'C', '0', '.', '+', '='};
        length_tab = txt_b.length;
        JButton calc_button[] = new JButton[txt_b.length];

        for ( int i = 0; i < txt_b.length; i++ ) {
            calc_button[i] = new JButton("" + txt_b[i]);
            calc_button[i].setBackground(Color.WHITE);
            calc_button[i].setFont(new Font("Verdana", Font.PLAIN, 15));
            calc_button[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            calc_button[i].setForeground(Color.BLACK);
            calc_button[i].setPreferredSize(dim);
            calc_button[i].addActionListener(this);
            panelClavier.add(calc_button[i]);
        }
        calc_button[txt_b.length - 1].setPreferredSize(new Dimension(215, 50));


        //---------------------------TEXTES/JLABELS-------------------------------//

        Font calcul_Font = new Font("Verdana", Font.PLAIN, 25);
        Dimension dimtxt = new Dimension(250, 50);
        //txt_rep
        txt_rep.setFont(calcul_Font);
        txt_rep.setMinimumSize(dimtxt);
        txt_rep.setMaximumSize(dimtxt);
        //txt_calc
        txt_calc.setFont(calcul_Font);
        txt_calc.setMinimumSize(dimtxt);
        txt_calc.setMaximumSize(dimtxt);

        //---------------------------CONFIGURATIONS JPANELS-------------------------------//

        // Configuration du panelClavier
        panelClavier.setPreferredSize(new Dimension(220, 280));

        //Configuration du panelResultat
        panelResultat.add(txt_rep);
        panelResultat.setPreferredSize(new Dimension(250, 50));
        //panelResultat.setBackground(Color.GRAY);

        //Configuration du panelCalcul
        panelCalcul.add(txt_calc);
        panelCalcul.setPreferredSize(new Dimension(250, 50));
        //panelCalcul.setBackground(Color.GRAY);


        // Configuration du conteneur myPrincipalPanel
        myPrincipalPanel.add(panelCalcul, BorderLayout.NORTH);
        myPrincipalPanel.add(panelResultat, BorderLayout.CENTER);
        myPrincipalPanel.add(panelClavier, BorderLayout.SOUTH);


        //---------------------------CONFIGURATION FENETRE-------------------------------//

        // Configuration de la Fenetre
        this.setTitle("Calculatrice");
        this.setContentPane(myPrincipalPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(250, 440);
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


    public void actionPerformed(ActionEvent e) {
        //récupère la valeur du bouton
        JButton o = (JButton) e.getSource();
        String txt = o.getText();
        char cara = txt.charAt(0);

        if ( dejacalc == true ) {
            affichage_calc = "";
            txt_calc.setText(affichage_calc);
            txt_rep.setText(affichage_calc);
            dejacalc = false;
        }

        if ( 48 <= (int) cara && (int) cara <= 57 || cara == '.' ) {
            isNumber(cara);
        } else if ( cara == '-' || (int) cara == '+' ) {
            isPlusorMinus(cara);
        } else if ( cara == '/' || cara == 'x' ) {
            isTimeorDiv(cara);
        } else if ( cara == 'C' ) {
            isClear();
        } else if ( cara == '=' ) {
            tab_nombre.add(tamp_nombre);
            tamp_nombre = "";
            System.out.println('\n' + "Calcul -> " + affichage_calc);

            affichage_calc += " " + cara;
            txt_calc.setText(affichage_calc);
            dejacalc = true;

            float final_resultat = 0;
            float nb1 = 0, nb2 = 0, tamp_res = 0;

            while (tab_signe.size() > 0) {
                int placefois = tab_signe.indexOf('x'), placeplus = tab_signe.indexOf('+'), placemoins = tab_signe.indexOf('-'), placediv = tab_signe.indexOf('/');
                int apfois = placefois + 1, applus = placeplus + 1, apmoins = placemoins + 1, apdiv = placediv + 1;

                System.out.println("Nombres ->" + Arrays.deepToString(tab_nombre.toArray()) + " | Signes ->" + Arrays.deepToString(tab_signe.toArray()));
                if ( placefois != -1 && placediv != -1 ) {
                    if ( placefois < placediv )                     {tamp_res = calculTask(placefois, apfois, '*');}
                    else if ( placefois > placediv )                {tamp_res = calculTask(placediv, apdiv, '/');}
                } else if ( placefois != -1 && placediv == -1 )     {tamp_res = calculTask(placefois, apfois, '*');}
                else if ( placefois == -1 && placediv != -1 )       {tamp_res = calculTask(placediv, apdiv, '/');}
                else if ( placeplus != -1 && placemoins != -1 ) {
                    if ( placeplus < placemoins )                   {tamp_res = calculTask(placeplus, applus, '+');}
                    else if ( placeplus > placemoins )              {tamp_res = calculTask(placemoins, apmoins, '-');}
                } else if ( placeplus != -1 && placemoins == -1 )   {tamp_res = calculTask(placeplus, applus, '+');}
                else if ( placeplus == -1 && placemoins != -1 )     {tamp_res = calculTask(placemoins, apmoins, '-');}
                else {
                    resultat = tab_nombre.get(0);
                    txt_rep.setText(resultat);
                }
            }

            if(tamp_res == (int) tamp_res)
                resultat = String.valueOf((int) tamp_res);
            else
                resultat = String.valueOf(tamp_res);

            System.out.println("résultat = " + resultat);
            txt_rep.setText(resultat);
            tab_signe.clear();
            tab_nombre.clear();
        }
    }

    public void isNumber(char cara) {
        tamp_nombre += cara;
        affichage_calc += cara;
        txt_calc.setText(affichage_calc);
        avantnb = true;
    }

    public void isPlusorMinus(char cara) {
        if ( avantnb == true ) {
            tab_signe.add(cara);
            tab_nombre.add(tamp_nombre);
            tamp_nombre = "";
        } else if ( avantnb == false && cara == '-' ) {
            tamp_nombre += cara;
        } else if ( (int) cara == '+' ) {
            tab_signe.add(cara);
            tab_nombre.add(tamp_nombre);
            tamp_nombre = "";
        }
        affichage_calc += " " + cara + " ";
        txt_calc.setText(affichage_calc);
        avantnb = false;
    }

    public void isTimeorDiv(char cara) {
        tab_signe.add(cara);
        tab_nombre.add(tamp_nombre);
        tamp_nombre = "";
        affichage_calc += " " + cara + " ";
        txt_calc.setText(affichage_calc);
        avantnb = false;
        j++;
    }

    public void isClear() {
        tamp_nombre = "";
        affichage_calc = "";
        txt_calc.setText("");
        txt_rep.setText("");
        tab_signe.clear();
        tab_nombre.clear();
    }

    public float calculTask(int place, int fPlace, char signe) {
        float nb1 = Float.parseFloat(tab_nombre.get(place));
        float nb2 = Float.parseFloat(tab_nombre.get(fPlace));
        float task_res = 0;
        switch (signe) {
            case '+':
                task_res = nb1 + nb2;
                break;

            case '-':
                task_res = nb1 - nb2;
                break;

            case '*':
                task_res = nb1 * nb2;
                break;

            case '/':
                task_res = nb1 / nb2;
                break;

            default:
                task_res = 0;
                break;
        }
        System.out.println("nb1 = " + nb1 + " / nb2 = " + nb2);
        tab_nombre.set(place, String.valueOf(task_res));
        tab_nombre.remove(fPlace);
        tab_signe.remove(place);

        return task_res;
    }
}