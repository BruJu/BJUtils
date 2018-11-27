package fr.bruju.util;

/**
 * Une classe permettant de construire une chaîne avec une indentation placée au début des lignes.
 */
public class IndentedStringBuilder {
	private static final String TABULATION = "  ";
	private static final char LN = '\n';
	private StringBuilder stringBuilder = new StringBuilder();
	private int tabulations = 0;
	private boolean isNewLine = true;

	/** Ajoute la chaîne à la ligne actuelle */
	public IndentedStringBuilder append(String chaine) {
		insertTabs();
		stringBuilder.append(chaine);
		return this;
	}

	/** Passe à la ligne suivante */
	public IndentedStringBuilder ln() {
		stringBuilder.append(LN);
		isNewLine = true;
		return this;
	}

	/** Ajoute un niveau de tabulation */
	public IndentedStringBuilder tab() {
		tabulations++;
		return this;
	}

	/** Enlève un niveau de tabulation */
	public IndentedStringBuilder untab() {
		tabulations--;
		return this;
	}

	/** Ajoute les tabulations de début de ligne si nécessaire */
	private void insertTabs() {
		if (isNewLine) {
			isNewLine = false;
			
			for (int i = 0 ; i != tabulations ; i++) {
				stringBuilder.append(TABULATION);
			}
		}
	}

	/**
	 * Donne la chaîne construite
	 * @return La chaîne construite
	 */
	public String toString() {
		return stringBuilder.toString();
	}
}
