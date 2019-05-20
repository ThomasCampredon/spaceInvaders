package fr.unilim.iut.spaceinvaders.moteurjeu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Missile;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.model.Vaisseau;

import java.awt.Color;


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
			dessinerUnMissile(image);
		}
		else {
			throw new AssertionError("vaisseau inexistant");
		}
	}
	
	public void dessinerUnMissile(BufferedImage image) {
		if (this.jeu.aUnMissile()) {
			
			Missile missile = jeu.getMissile();
			
			Graphics2D crayon = (Graphics2D) image.getGraphics();
			
	
			crayon.setColor(Color.blue);
			crayon.fillRect(missile.abscisseLaPlusAGauche(),
					missile.ordonneeLaPlusBasse(),//si probleme voire vers le milieu de : https://github.com/iblasquez/tdd_spaceInvaders/blob/master/enonces/SpaceInvaders_Spike_MoteurGraphique.md
					missile.getDimension().longueur(), missile.getDimension().hauteur());
		}
		
	}

}
