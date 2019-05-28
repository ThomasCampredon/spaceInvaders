package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import utils.DebordementEspaceJeuException;
import utils.HorsEspaceJeuException;
import utils.MissileException;
import utils.SupperpositionException;

public class SpaceInvaders implements fr.unilim.iut.spaceinvaders.moteurjeu.Jeu {

	int longueur;
	int hauteur;
	private Vaisseau vaisseau;
	private Missile missile;
	private List<Missile> missiles;
	private Envahisseur envahisseur;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}
	
	
	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
				marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x,y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		return this.aUnEnvahisseur() && envahisseur.occupeLaPosition(x, y);
	}

	public boolean aUnEnvahisseur() {
		return this.envahisseur != null;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		return this.aUnMissile() && missile.occupeLaPosition(x, y);
	}

	public boolean aUnMissile() {
		return missile != null;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.getDimension().longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}
	
	
	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {
		
		
		int x = position.abscisse();
		int y = position.ordonnee();
		
		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();
		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		this.vaisseau = new Vaisseau(dimension,position,vitesse);
	}

	
	public Vaisseau getVaisseau() {
		return this.vaisseau;
	}

	public int getLongueur() {
		return longueur;
	}

	public int getHauteur() {
		return hauteur;
	}
	
	public Envahisseur getEnvahisseur() {
		return this.envahisseur;
	}
	
	
	
	// block des methodes du jeu
	@Override
	public void evoluer(Commande commande) {
		if (commande.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commande.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commande.tir && !this.aUnMissile()) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}
		if (aUnMissile()) {
			this.deplacerMissile();
		}
		if (aUnEnvahisseur()) {
			this.deplacerEnvahisseur();
		}

		if (aUnMissile() && aUnEnvahisseur()) {
			if (sontEnCollision(this.missile, this.envahisseur)) {
				this.missile = null;
				this.envahisseur = null;
			}
		}

	}

	public boolean collisionSurLesAbscisses(Sprite sprite1, Sprite sprite2) {
		return collisionSurLesAbscisseADroite(sprite1,sprite2)||collisionSurLesAbscissesAGauche(sprite1,sprite2);
	}

	public boolean collisionSurLesAbscissesAGauche(Sprite sprite1, Sprite sprite2) {
		return ((sprite1.abscisseLaPlusADroite()>=sprite2.abscisseLaPlusAGauche()) && (sprite1.abscisseLaPlusADroite()<=sprite2.abscisseLaPlusADroite()));
	}

	public boolean collisionSurLesAbscisseADroite(Sprite sprite1, Sprite sprite2) {
		return ((sprite1.abscisseLaPlusAGauche()<=sprite2.abscisseLaPlusADroite()) && (sprite1.abscisseLaPlusAGauche()>=sprite2.abscisseLaPlusAGauche()));
	}

	public boolean collisionSurLesOrdonneesInferieures(Sprite sprite1, Sprite sprite2) {
		return ((sprite1.ordonneeLaPlusBasse()<=sprite2.ordonneeLaPlusHaute()) && (sprite1.ordonneeLaPlusBasse()>=sprite2.ordonneeLaPlusBasse()));
	}
	
	public boolean collisionSurLesOrdonneesSuperieures(Sprite sprite1, Sprite sprite2) {
		return ((sprite1.ordonneeLaPlusHaute()<=sprite2.ordonneeLaPlusBasse()) && (sprite1.ordonneeLaPlusHaute()>=sprite2.ordonneeLaPlusHaute()));
	}
	
	public boolean collisionSurLesOrdonnees(Sprite sprite1, Sprite sprite2) {
		return collisionSurLesOrdonneesInferieures(sprite1, sprite2) || collisionSurLesOrdonneesSuperieures(sprite1, sprite2);
	}
	
	public boolean sontEnCollision(Sprite sprite1, Sprite sprite2) {
		return((collisionSurLesAbscisses(sprite1,sprite2)) && (collisionSurLesOrdonnees(sprite1,sprite2)));
	}

	@Override
	public boolean etreFini() {
		return false;
	}
	
	
	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		
		Position positionEnvahisseur = new Position(Constante.ENVAHISSEUR_ABSCISSE,Constante.ENVAHISSEUR_ORDONNEE);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR,Constante.ENVAHISSEUR_HAUTEUR);
		positionnerUnNouvelEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
	 }

	
	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		
		if ((vaisseau.getDimension().hauteur()+ dimensionMissile.hauteur()) > this.hauteur ) {
			throw new MissileException("Il n'y a pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
		}
		
		this.missile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		/*if(missiles.isEmpty()) {			
			this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile));
		}
		
		if ((missiles.size()==1) && (!sontEnCollision(this.missiles.get(0), this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile)))) {			
			this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile));
		}
		
		if (!sontEnCollision(this.missiles.get(this.missiles.size()-2), this.missiles.get(this.missiles.size()-1))){
			this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile));
		}*/
		
		
		
    }

	public Missile getMissile() {
		return this.missile;
	}
	
	public List<Missile> getMissiles(){
		return this.missiles;
	}

	public void deplacerMissile() {
		if (missile.ordonneeLaPlusHaute() <= this.hauteur)
			missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
		if (0 >= this.missile.ordonneeLaPlusHaute()) {
			this.missile = null;
		}
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();
		
		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace de jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();
		
		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");
		if (seSuperposeAuVaisseau(dimension, position))
			throw new SupperpositionException("L'envahisseur se superpose au vaisseau");
		this.envahisseur = new Envahisseur(dimension,position,vitesse);
		
	}

	public boolean seSuperposeAuVaisseau(Dimension dimension, Position position) {
		return (position.ordonnee()>this.vaisseau.ordonneeLaPlusHaute()) && ((position.abscisse()>this.vaisseau.getOrigine().abscisse())||(position.abscisse()+dimension.longueur()<this.vaisseau.getOrigine().abscisse()));
	}

	public void deplacerEnvahisseurVersLaDroite() {
		if (this.envahisseur.abscisseLaPlusADroite() < (longueur - 1)) {
			this.envahisseur.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(this.envahisseur.abscisseLaPlusADroite(), this.envahisseur.ordonneeLaPlusHaute())) {
				this.envahisseur.positionner(longueur - this.envahisseur.getDimension().longueur(), this.envahisseur.ordonneeLaPlusHaute());
			}
		}		
	}
	
	public void deplacerEnvahisseurVersLaGauche() {
		if (0 < this.envahisseur.abscisseLaPlusAGauche())
			this.envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(this.envahisseur.abscisseLaPlusAGauche(), this.envahisseur.ordonneeLaPlusHaute())) {
			this.envahisseur.positionner(0, this.envahisseur.ordonneeLaPlusHaute());
		}
	}
	
	public void deplacerEnvahisseur() {
		if (this.envahisseur.abscisseLaPlusADroite()>=this.longueur-1) {
			Constante.versLaGauche = true;
			Constante.versLaDroite = false;
		}		
		if (this.envahisseur.abscisseLaPlusAGauche()<=0) {
			Constante.versLaGauche = false;
			Constante.versLaDroite = true;
		}
		if (Constante.versLaDroite) {
			deplacerEnvahisseurVersLaDroite();
		}
		if(Constante.versLaGauche) {
			deplacerEnvahisseurVersLaGauche();
		}
		
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	public void setEnvahisseur(Envahisseur envahisseur) {
		this.envahisseur=envahisseur;
	}
	
	public void setMissile(Missile missile) {
		this.missile=missile;
	}
	
	
}
