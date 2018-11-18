package fr.bruju.util.table;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Table_ {
	private List<String> colonnes;
	private List<Contenu_> contenus;

	private Map<String, Integer> colonnesConnues = new HashMap<>();

	public Table_(List<String> colonnes) {
		this.colonnes = colonnes;
		this.contenus = new ArrayList<>();
	}



	public void ajouterContenu(List<? extends Object> donnees) {
		if (donnees.size() != colonnes.size()) {
			throw new NombreDeChampsInvalideException();
		}

		contenus.add(new Contenu_(this, donnees));
	}

	public void forEach(Consumer<Contenu_> consumer) {
		contenus.forEach(consumer);
	}

	public void retirerChamp(int idChamp) {
		viderCacheColonnes(idChamp);

		colonnes.remove(idChamp);
		for (Contenu_ contenu : contenus) {
			contenu.donnees.remove(idChamp);
		}
	}

	public void insererChamp(int idChamp, String nom, Function<Contenu_, Object> generateur) {
		viderCacheColonnes(idChamp);
		colonnesConnues.put(nom, idChamp);

		colonnes.add(idChamp, nom);

		for (Contenu_ contenu : contenus) {
			Object donneeNouvelle = generateur.apply(contenu);
			contenu.donnees.add(idChamp, donneeNouvelle);
		}
	}

	public String getNomDuChamp(int position) {
		return colonnes.get(position);
	}

	public List<String> getColonnes() {
		return Collections.unmodifiableList(colonnes);
	}

	public void transformerUnChamp(int idChamp, UnaryOperator<Object> transformateur) {
		for (Contenu_ contenu : contenus) {
			contenu.set(idChamp, transformateur.apply(contenu.get(idChamp)));
		}
	}







	private void viderCacheColonnes(int idChamp) {
		colonnesConnues.clear();
	}


	public <T> List<T> selectUnChamp(String nom) {
		List<T> liste = new ArrayList<>();

		int positionColonne = getPosition(nom);

		for (Contenu_ contenu : contenus) {
			liste.add((T) contenu.donnees.get(positionColonne));
		}

		return liste;
	}


	public int getPosition(String nom) {
		Integer positionColonne = colonnesConnues.get(nom);

		if (positionColonne != null) {
			return positionColonne;
		}

		for (int i = 0 ; i != colonnes.size() ; i++) {
			if (colonnes.get(i).equals(nom)) {
				colonnesConnues.put(nom, i);
				return i;
			}
		}

		return -1;
	}

	public void ajouterChamps(List<String> nouvellesColonnes) {
		for (String nouvellesColonne : nouvellesColonnes) {
			insererChamp(colonnes.size(), nouvellesColonne, null);
		}
	}
}
