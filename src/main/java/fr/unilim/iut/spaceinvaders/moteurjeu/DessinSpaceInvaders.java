package fr.unilim.iut.spaceinvaders.moteurjeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import fr.unilim.iut.spaceinvaders.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.Vaisseau;
import fr.unilim.iut.spaceinvaders.Dimension;
import fr.unilim.iut.spaceinvaders.Position;


public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders jeu;
	
	public DessinSpaceInvaders (SpaceInvaders jeu) {
		this.jeu = jeu;
	}
	
	@Override
	public void dessiner(BufferedImage image) {
		if (this.jeu.aUnVaisseau()) {
			Vaisseau vaisseau = jeu.getVaisseau();
			
			Graphics2D crayon = (Graphics2D) image.getGraphics();
	
			crayon.setColor(Color.gray);
			crayon.fillRect(vaisseau.abscisseLaPlusAGauche(),
					vaisseau.ordonneeLaPlusBasse(),//si probleme voire vers le milieu de : https://github.com/iblasquez/tdd_spaceInvaders/blob/master/enonces/SpaceInvaders_Spike_MoteurGraphique.md
					vaisseau.getDimension().longueur(), vaisseau.getDimension().hauteur());
		}
		else {
			throw new AssertionError("vaisseau inexistant");
		}
	}

}
