package fr.unilim.iut.spaceinvaders.model;

public class Constante {
	//espace de jeu
	public static final int ESPACEJEU_LONGUEUR = 700;//700
    public static final int ESPACEJEU_HAUTEUR = 350;//350

    //vaisseau
    public static final int VAISSEAU_LONGUEUR = 50;//50
    public static final int VAISSEAU_HAUTEUR = 30;//30
    public static final int VAISSEAU_VITESSE = 5;//17
    
    //missile
    public static final int MISSILE_LONGUEUR = 8;//8
    public static final int MISSILE_HAUTEUR = 14;//14
    public static final int MISSILE_VITESSE = 8;//20
    
    //envahisseur
    public static final int ENVAHISSEUR_ABSCISSE = 3;//3
    public static final int ENVAHISSEUR_ORDONNEE = 20;//20
    public static final int ENVAHISSEUR_LONGUEUR = 30;//30
    public static final int ENVAHISSEUR_HAUTEUR = 20;//20
    public static final int ENVAHISSEUR_VITESSE = 3;//20
    
    
    //test espace de jeu
	public static final char MARQUE_FIN_LIGNE = '\n';
	public static final char MARQUE_VIDE = '.';
	public static final char MARQUE_VAISSEAU = 'V';
	public static final char MARQUE_MISSILE = 'M';
	public static final char MARQUE_ENVAHISSEUR = 'O';
	
	
	//déplacement
	public static boolean versLaDroite = true;
	public static boolean versLaGauche = false;
	
	
	
	
}
