package fr.bruju.util.table;

import fr.bruju.util.Pair;

import java.util.*;

public class Table {
	private List<String> colonnes;
	private List<Contenu> contenus;

	private class Contenu {
		private List<Object> donnees;
	}










	public <T> List<T> selectUnChamp(String nom) {
		List<T> liste = new ArrayList<>();

		int positionColonne = getPosition(nom);

		for (Contenu contenu : contenus) {
			liste.add((T) contenu.donnees.get(positionColonne));
		}

		return liste;
	}


	private int getPosition(String nom) {
		for (int i = 0 ; i != colonnes.size() ; i++) {
			if (colonnes.get(i).equals(nom)) {
				return i;
			}
		}

		return -1;
	}
}
