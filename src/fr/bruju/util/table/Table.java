package fr.bruju.util.table;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Table {
	/** Nom des champs */
	private List<String> champs;
	/** Enregistrements */
	private List<Enregistrement> enregistrements;

	/** ASsociation Nom de champ - position connues */
	private Map<String, Integer> champsConnus = new HashMap<>();
	/** Numéro de la dernière colonne explorée */
	private int positionDernierChampConnu = -1;

	/** Crée une table avec aucun champ et aucun enregistrement */
	public Table() {
		this.champs = new ArrayList<>();
		this.enregistrements = new ArrayList<>();
	}


	/* ================================
	 * MANIPULATION DES ENREGISTREMENTS
	 * ================================ */

	/**
	 * Ajoute un enregistrement
	 * @param donnees La liste des objets dans l'enregistrement
	 */
	public void ajouterContenu(List<? extends Object> donnees) {
		if (donnees.size() != champs.size()) {
			throw new NombreDeChampsInvalideException();
		}

		enregistrements.add(new Enregistrement(this, donnees));
	}

	/**
	 * Transforme les données contenues dans un champ
	 * @param champ Le nom du champ
	 * @param transformateur La fonction de transformation de la valeur
	 */
	public void transformerUnChamp(String champ, UnaryOperator<Object> transformateur) {
		transformerUnChamp(getPosition(champ), transformateur);
	}

	/**
	 * Transforme les données contenues dans un champ
	 * @param idChamp La position du champ
	 * @param transformateur La fonction de transformation de la valeur
	 */
	public void transformerUnChamp(int idChamp, UnaryOperator<Object> transformateur) {
		for (Enregistrement enregistrement : enregistrements) {
			enregistrement.set(idChamp, transformateur.apply(enregistrement.get(idChamp)));
		}
	}

	/**
	 * Parcours tous les enregistrements
	 * @param consumer Fonction à appliquer sur les enregistrements
	 */
	public void forEach(Consumer<Enregistrement> consumer) {
		enregistrements.forEach(consumer);
	}


	/* =======================
	 * MANIPULATION DES CHAMPS
	 * ======================= */

	/**
	 * Donne le nom du champ à la colonne donnée
	 * @param position Colonne du champ
	 * @return Le nom du champ
	 */
	public String getNomDuChamp(int position) {
		return champs.get(position);
	}

	/**
	 * Donne la liste de tous les champs
	 * @return La liste de tous les champs dans une liste non modifiable
	 */
	public List<String> getChamps() {
		return Collections.unmodifiableList(champs);
	}

	/**
	 * Donne la position du champ dont le nom est donné
	 * @param nom Le nom du champ
	 * @return La position du champ, -1 si absent
	 */
	public int getPosition(String nom) {
		// Cette méthode utilise la map champsConnus comme cache : on ne parcours chaque nom de champs qu'une seule
		// fois par modification de la liste des champs.
		Integer positionColonne = champsConnus.get(nom);

		if (positionColonne != null) {
			return positionColonne;
		}


		String nomDuChamp;

		while (positionDernierChampConnu + 1 != champs.size()) {
			positionDernierChampConnu++;
			nomDuChamp = champs.get(positionDernierChampConnu);

			champsConnus.put(nomDuChamp, positionDernierChampConnu);

			if (nomDuChamp.equals(nom)) {
				return positionDernierChampConnu;
			}
		}

		return -1;
	}

	/**
	 * Efface du cache la connaissance de toutes les associations nom de champ - position (sauf si ne pas le faire
	 * ne rend pas erroné la table d'association)
	 * @param idChamp La position de la colonne qui va être modifiée
	 */
	private void viderCacheColonnes(int idChamp) {
		if (idChamp < positionDernierChampConnu) {
			// Insertion après les champs déjà explorés, on ne fait rien
			return;
		}

		champsConnus.clear();
		positionDernierChampConnu = -1;
	}

	/**
	 * Insière un nouveau champ à la colonne indiquée
	 * @param idChamp La colonne du champ à insérer
	 * @param nom Le nom du nouveau champ
	 * @param generateur La fonction qui donne la valeur initiale du nouveau champ par rapport à l'enregistrement
	 */
	public void insererChamp(int idChamp, String nom, Function<Enregistrement, Object> generateur) {
		viderCacheColonnes(idChamp);
		champsConnus.put(nom, idChamp);

		champs.add(idChamp, nom);

		for (Enregistrement enregistrement : enregistrements) {
			Object donneeNouvelle = generateur.apply(enregistrement);
			enregistrement.donnees.add(idChamp, donneeNouvelle);
		}
	}

	/**
	 * Insère un nouveau champ
	 * @param champ Le nom du champ précédant le champ à insérer
	 * @param nomNouveauChamp Le nom du nouveau champ
	 * @param generateur La fonction qui donne la valeur initiale du nouveau champ par rapport à l'enregistrement
	 */
	public void insererChampApres(String champ, String nomNouveauChamp, Function<Enregistrement, Object> generateur) {
		insererChamp(getPosition(champ) + 1, nomNouveauChamp, generateur);
	}

	/**
	 * Ajoute tous les champs de la liste à la fin.
	 * @param nouvellesColonnes La liste des nouveaux noms de champs.
	 * @throws NullPointerException Si il y a déjà des enregistrements présents
	 */
	public void ajouterChamps(Iterable<String> nouvellesColonnes) {
		for (String nouvellesColonne : nouvellesColonnes) {
			insererChamp(champs.size(), nouvellesColonne, null);
		}
	}

	/**
	 * Retire le champ dont le numéro est donné
	 * @param idChamp Le numéro du champ
	 */
	public void retirerChamp(int idChamp) {
		viderCacheColonnes(idChamp);

		champs.remove(idChamp);
		for (Enregistrement enregistrement : enregistrements) {
			enregistrement.donnees.remove(idChamp);
		}
	}

	/**
	 * Retire le champ
	 * @param champ Le nom du champ
	 */
	public void retirerChamp(String champ) {
		retirerChamp(getPosition(champ));
	}


	/* ================================
	 * MISc.
	 * ================================ */


	public <T> List<T> selectUnChamp(String nom) {
		List<T> liste = new ArrayList<>();

		int positionColonne = getPosition(nom);

		for (Enregistrement enregistrement : enregistrements) {
			liste.add((T) enregistrement.donnees.get(positionColonne));
		}

		return liste;
	}


	public Table creerNouvelleTable(List<String> nouveauxChamps,
                                    BiConsumer<Enregistrement, Consumer<List<? extends Object>>> fonctionDeParcours) {
	    Table table = new Table();
	    table.ajouterChamps(nouveauxChamps);
	    table.forEach(contenu -> fonctionDeParcours.accept(contenu, table::ajouterContenu));
	    return table;
    }

    public void reordonner(Iterable<Comparator<Enregistrement>> comparateurs) {
	    this.enregistrements.sort((e1, e2) -> {
            for (Comparator<Enregistrement> comparateur : comparateurs) {
                int resultat = comparateur.compare(e1, e2);

                if (resultat != 0) {
                    return resultat;
                }
            }

	        return Integer.compare(e1.hashCode(), e2.hashCode());
        });
    }
}
