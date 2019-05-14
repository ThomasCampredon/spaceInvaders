package fr.unilim.iut.spaceinvaders;

public abstract class Sprite {

	protected Position origine;
	protected Dimension dimension;
	protected int vitesse;

	public Sprite(Dimension dimension, Position origine, int vitesse) {
		super();
		this.dimension = dimension;
		this.origine = origine;
		this.vitesse = vitesse;
	}

	public boolean occupeLaPosition(int x, int y) {
	     return (estAbscisseCouverte(x) && estOrdonneeCouverte(y));
	}

	protected boolean estOrdonneeCouverte(int y) {
		return ordonneeLaPlusBasse(y) && ordonneeLaPlusHaute(y);
	}

	protected boolean ordonneeLaPlusHaute(int y) {
		return y<=this.origine.ordonnee();
	}

	public int ordonneeLaPlusHaute() {
		return this.origine.ordonnee();
	}

	public boolean ordonneeLaPlusBasse(int y) {
		return this.origine.ordonnee()-this.dimension.hauteur()+1<=y;
	}

	public int ordonneeLaPlusBasse() {
		return this.origine.ordonnee()-this.dimension.hauteur()+1;
	}

	protected boolean estAbscisseCouverte(int x) {
		return (abscisseLaPlusAGauche()<=x) && (x<=abscisseLaPlusADroite());
	}

	public int abscisseLaPlusADroite() {
		return this.origine.abscisse()+this.dimension.longueur()-1;
	}

	public void seDeplacerVersLaDroite() {
		 this.origine.changerAbscisse(this.origine.abscisse()+this.vitesse);	   	
	}

	public int abscisseLaPlusAGauche() {
		return this.origine.abscisse();
	}

	public void seDeplacerVersLaGauche() {
		 this.origine.changerAbscisse(this.origine.abscisse()-this.vitesse);
	}

	public void positionner(int x, int y) {
		this.origine.changerAbscisse(x);
		this.origine.changerOrdonnee(y);
	}

	public Position getOrigine() {
		return origine;
	}

	public Dimension getDimension() {
		return dimension;
	}

}