package fr.bruju.util.reconnaissancedimage;

/**
 * Représentation simplifiée de l'image
 */
public class MatricePixels {
	/** Hauteur de l'image */
	public final int hauteur;

	/** Longueur de l'image */
	public final int longueur;

	/** Tableau montrant la liste des pixels où le rouge est plus clair */
	private boolean[][] pixelsAllumes;
	

	/**
	 * Construit un reconnaissuer de motifs à partir d'une matrice de pixels allumés
	 * 
	 * @param hauteur Hauteur de l'image
	 * @param longueur Longueur de l'image
	 * @param pixelsAllumes Matrice avec la position des pixels allumés
	 */
	public MatricePixels(int hauteur, int longueur, boolean[][] pixelsAllumes) {
		this.hauteur = hauteur;
		this.longueur = longueur;
		this.pixelsAllumes = pixelsAllumes;
	}

	/**
	 * Donne l'état du pixel voulu
	 * 
	 * @param x La coordonnée x
	 * @param y La coordonnée y
	 * @return Vrai si le pixel en x,y est allumé
	 */
	public boolean estAllume(int x, int y) {
		return pixelsAllumes[x][y];
	}

	/**
	 * Donne la matrice des pixels reconnus
	 * @return Une chaîne représentant l'image avec des croix pour les pixels considérés comme allumés et des espaces
	 * pour les pixels considérés éteints.
	 */
	public String getString() {
		StringBuilder[] renduDesLignes = new StringBuilder[hauteur];
		
		int premierLigneRemplie = -1;
		int derniereLigneRemplie = -2;
		
		for (int ligne = 0; ligne != hauteur ; ligne++) {
			boolean nonVide = false;
			StringBuilder sbLigne = new StringBuilder();
			
			for (int colonne = 0; colonne != longueur ; colonne++) {
				if (pixelsAllumes[colonne][ligne]) {
					sbLigne.append("X");
					nonVide = true;
				} else {
					sbLigne.append(" ");
				}
			}
			
			if (nonVide) {
				if (premierLigneRemplie == -1) {
					premierLigneRemplie = ligne;
				}
				
				derniereLigneRemplie = ligne;
			}
			
			renduDesLignes[ligne] = sbLigne;
		}
		
		StringBuilder stringBuilderGlobal = new StringBuilder();
		
		for (int i = premierLigneRemplie ; i <= derniereLigneRemplie ; i++) {
			StringBuilder ligne = renduDesLignes[i];
			stringBuilderGlobal.append(ligne).append("\n");
		}
		
		return stringBuilderGlobal.toString();
	}
}
