package fr.bruju.util.reconnaissancedimage;

import java.util.Arrays;

/**
 * Classe représentant un motif. Les motifs sont composés de la chaîne représentée et d'une suite d'entiers décrivant
 * le motif.
 * <br><br>Le but de cette classe est de faire le lien entre un groupe de pixels dit allumés et sa représentation sous
 * forme de chaîne.
 * <br><br>Par exemple, cette classe permet de faire le lien entre le symbole "a" et le dessin suivant :
 * <pre>
 *     xxxxx
 *         x
 *     xxxxx
 *     x   x
 *     xxxxx
 * </pre>
 * <br>Les entiers sont l'encodage de chaque ligne du motif. Pour chaque entier,  a présence du ieme pixel allumé
 * signifie que sa représentation se voit ajoutée du nombre 2^i.
 * <br>Exemples :
 * <ul>
 *  <li>"x" = 1</li>
 *  <li>"" = 0</li>
 *  <li>"xx" = 3 (1 + 2)</li>
 *  <li>" x" = 2</li>
 *  <li>"x " = 1 (équivaut à "x"</li>
 *  <li>"xx x" = 13 (1 + 2 + 0 + 8)</li>
 * </ul>
 * <br>Notre symbole a est donc encodé {31, 16, 31, 17, 31}
 */
public class Motif {
	/* --------------------------
	 * Attributs et constructeurs
	 * -------------------------- */
	
	/** Chaîne représentée par le motif */
	private String lettre;

	/**
	 * Représentation numérique du motif
	 * <br>Chaque nombre correspond à une ligne. Une ligne est encodé en utilisant la représentation binaire de la
	 * suite de symboles de droite à gauche.
	 */
	private int[] composition;

	/**
	 * Crée un motif à partir de sa sérialisation (Symbole Nombres_Constituant_Sa_Représentation_Numériqueà
	 * @param serialisation La sérialisation du motif
	 */
	public Motif(String serialisation) {
		String[] chaineSplittee = serialisation.split(" ");

		lettre = chaineSplittee[0];
		composition = new int[chaineSplittee.length - 1];

		for (int i = 0 ; i != composition.length ; i++) {
			composition[i] = Integer.parseInt(chaineSplittee[i + 1]);
		}
	}

	/**
	 * Crée un motif préconnu
	 *
	 * @param composition Représentation numérique du motif
	 */
	public Motif(int[] composition) {
		this.composition = composition;
	}

	/**
	 * Permet de savoir si ce motif et le motif donné sont identiques
	 * 
	 * @param candidat Le motif dont on souhaite savoir si il est égal à ce motif
	 * @return Vrai si candidat et le motif représenté sont identiques
	 */
	public boolean comparer(int[] candidat) {
		return Arrays.equals(composition, candidat);
	}

	/**
	 * Renvoie la chaîne représentant le motif
	 * 
	 * @return La chaîne représentant le motif
	 */
	public String getSymboleDesigne() {
		return lettre;
	}

	/**
	 * Donne une chaîne permettant de sérialiser le motif
	 * @return La sérialisation du motif
	 */
	public String serialiser() {
		StringBuilder sb = new StringBuilder();
		
		String lettre = this.lettre;
		if (lettre == null) {
			lettre = "?";
		}
		
		sb.append(lettre);
		for (int valeur : composition) {
			sb.append(" ").append(valeur);
		}
		
		return sb.toString();
	}
	
	/**
	 * Dessine le motif sous forme numérique
	 *
	 * @return Une représentation en chaîne du motif
	 */
	public String dessinerMotif() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int valeur : composition) {
			dessinerUneLigneDeMotif(stringBuilder, valeur);
		}
		return stringBuilder.toString();
	}

	/**
	 * Dessine une ligne de motif à partir du nombre donné
	 * 
	 * @param stringBuilder Constructeur de chaîne à remplir
	 * @param valeur Le nombre représentant la ligne
	 */
	private static void dessinerUneLigneDeMotif(StringBuilder stringBuilder, int valeur) {
		while (valeur != 0) {
			if (valeur % 2 == 1) {
				stringBuilder.append("x");
			} else {
				stringBuilder.append(" ");
			}

			valeur = valeur / 2;
		}
		
		stringBuilder.append("\n");
	}

	/**
	 * Donne une chaîne qu'il faudrait insérer dans le fichier des motifsconnus pour reconnaître ce motif, avec le
	 * motif dessiné au dessus de la chaîne. A noter que la détection du motif n'est pas faite (un utilisateur humain
	 * doit remplacer le ? désignant la lettre du motif par la vraie chaîne représentée)
	 * @return Une chaîne représentant le motif non reconnu. Par exemple pour le motif "tt"
	 * <pre>
	 * Motif non reconnu :
     *  x   x
     *  x   x
     * xxxxxxxx
     *  x   x
     *  x   x
     *  x   x
     *  x   x
     *   xx  xx
     * 
	 * ? 34 34 255 34 34 34 34 204
	 * </pre>
	 */
	public String getChaineDeNonReconnaissance() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Motif non reconnu :\n");
		sb.append(dessinerMotif()).append("\n");
		sb.append(serialiser()).append("\n");
		
		return sb.toString();
	}
}