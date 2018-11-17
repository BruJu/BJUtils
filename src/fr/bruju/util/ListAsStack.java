package fr.bruju.util;

import java.util.List;

public class ListAsStack {
	/**
	 * Donne le dernier élément de la liste
	 * @param tableau La liste
	 * @return Le dernier élément
	 */
	public static <T> T peek(List<T> tableau) {
		if (tableau.isEmpty())
			return null;

		return tableau.get(tableau.size() - 1);
	}

	/**
	 * Dépile le dernier élément de la liste
	 * @param tableau La liste
	 * @return Le dernier élément
	 */
	public static <T> T pop(List<T> tableau) {
		if (tableau.isEmpty())
			return null;

		return tableau.remove(tableau.size() - 1);
	}
}
