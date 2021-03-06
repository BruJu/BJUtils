package fr.bruju.util.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Enregistrement {
	private final Table table;
	List<Object> donnees;

	public Enregistrement(Table table, List<? extends Object> donnees) {
		this.table = table;
		this.donnees = new ArrayList<>(donnees);
	}

	public <T> T get(int idChamp) {
		return (T) donnees.get(idChamp);
	}

	public <T> T get(String champ) {
		return this.<T>get(table.getPosition(champ));
	}

	public void set(int idChamp, Object objet) {
		donnees.set(idChamp, objet);
	}

	public void set(String champ, Object objet) {
		donnees.set(table.getPosition(champ), objet);
	}

	public void reconstruireObjet(BiConsumer<String, Object> biConsumer) {
		for (int i = 0 ; i != donnees.size() ; i++) {
			biConsumer.accept(table.getNomDuChamp(i), donnees.get(i));
		}
	}

	/**
	 * Renvoie la liste des données contenu par l'enregistrement.
	 * @return Une ArrayList contenant les données contenu par l'enregistrement. La liste renvoyée est indépendante de
	 * la liste utilisée en interne, mais les objets contenus sont les mêmes.
	 */
	public List<Object> getDonnees() {
		return new ArrayList<>(donnees);
	}
}