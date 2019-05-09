package fr.unilim.iut.spaceinvaders.moteurjeu;

import fr.unilim.iut.spaceinvaders.Constante;
import fr.unilim.iut.spaceinvaders.SpaceInvaders;



public class Main {
	public static void main(String[] args) throws InterruptedException {
		//SpaceInvaders jeu = new SpaceInvaders(15,10);
		SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
	    jeu.initialiserJeu();
		DessinSpaceInvaders afficheur = new DessinSpaceInvaders(jeu);
		
		MoteurGraphique moteur = new MoteurGraphique(jeu, afficheur);
		  moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR,Constante.ESPACEJEU_HAUTEUR);
	}
}
