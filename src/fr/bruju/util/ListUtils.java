package fr.bruju.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;

public class ListUtils {

	/**
	 * Applique fonctionFusion sur tous les éléments de la liste jusqu'à que ce ne soit plus possible
	 * @param listeDeBase La liste à transformer
	 * @param fonctionFusion La fonction de fusion. Renvoie null si la fusion n'est pas possible
	 * @return La liste avec fonctionFusion appliquée le plus de fois possible
	 */
	public static <T> List<T> fusionnerJusquaStabilite(List<T> listeDeBase, BinaryOperator<T> fonctionFusion) {
		List<T> base;
		List<T> transformee = listeDeBase;
		boolean stable = false;

		while (!stable) {
			stable = true;
			base = transformee;
			transformee = new ArrayList<>();

			boucleorig:
			for (int i = 0 ; i != base.size(); i++) {
				T p = base.get(i);

				for (int j = i+1 ; j!= base.size() ; j++) {
					T s = base.get(j);

					T u = fonctionFusion.apply(p, s);

					if (u != null) {
						stable = false;
						base.remove(s);
						transformee.add(u);
						continue boucleorig;
					}
				}

				transformee.add(p);
			}
		}

		return transformee;
	}

	/**
	 * Enlève les éléments voisins de la liste en utilisant la fonction de voisinage donnée pour que seul un élément
	 * de chaque voisinage soit présent. Un voisinage est défini comme un ensemble tel qu'il existe entre chaque
	 * couple d'élément (a, b) une suite d'éléments tels que a est voisin de x1, x1 est voisin de x2, ... xn est voisin
	 * de b.
	 * <br>Idéalement, la fonction de voisinage devrait être symétrique.
	 * <br>Dit autrement, cette fonction ne garde que des éléments tels que si la fonction de voisinage était
	 * transitive, alors tous les éléments conservés ne vérifient pas la fonction de voisinage.
	 * @param liste La liste doit il faut enlever les voisins
	 * @param sontVoisins La fonction de voisinage à appliquer
 	 * @param <T> Le type des éléments
	 */
	public static <T> void filtrerParVoisinage(List<T> liste, BiPredicate<T, T> sontVoisins) {
		if (liste.size() <= 1) {
			return;
		}

		List<T> explores = new ArrayList<>();

		while (!liste.isEmpty()) {
			T caseRemplie = ListAsStack.pop(liste);
			explores.add(caseRemplie);

			Stack<T> voisins = new Stack<>();
			voisins.push(caseRemplie);

			while (!voisins.isEmpty()) {
				T caseDepilee = voisins.pop();

				for (int i = liste.size() - 1 ; i >= 0 ; i--) {
					T caseCandidateAuVoisinage = liste.get(i);

					if (sontVoisins.test(caseDepilee, caseCandidateAuVoisinage)) {
						liste.remove(i);
						voisins.push(caseCandidateAuVoisinage);
					}
				}
			}
		}

		liste.addAll(explores);
	}

    /**
     * Unifie les éléments de la liste et renvoie la nouvelle liste
     * @param liste La liste de départ
     * @param unificateur La fonction qui permet d'unifier deux éléments de la liste. La fonction doit renvoyer null
     *                    si les deux éléments ne sont pas unifiables
     * @param <T> Le type des éléments
     * @return La liste contenant tous les éléments de la liste unifiés avec la fonction d'unification
     */
    public static <T> List<T> unifierElementsMitoyens(List<T> liste, BinaryOperator<T> unificateur) {
        if (liste.size() <= 1) {
            return new ArrayList<>(liste);
        }

        List<T> explores = new ArrayList<>();

        while (!liste.isEmpty()) {
            T caseRemplie = ListAsStack.pop(liste);

            for (int i = liste.size() - 1 ; i >= 0 ; i--) {
                T versionMitoyenne = unificateur.apply(caseRemplie, liste.get(i));

                if (versionMitoyenne != null) {
                    liste.remove(i);
                    caseRemplie = versionMitoyenne;
                }
            }

            explores.add(caseRemplie);
        }

        return explores;
    }

	/**
	 * Filtre les éléments de la liste pour renvoyer une liste ne contenant que les meilleurs éléments
	 * @param values Les éléments
	 * @param evaluation La fonction d'évaluation d'un élément
	 * @return La liste des éléments dont l'évaluation est la meilleure
	 */
	public static <T> Collection<T> filtrerMeilleurs(Collection<T> values, Function<T, Double> evaluation) {
		List<T> best = new ArrayList<>();
		
		Double bestValue = null;
		
		for (T value : values) {
			Double eval = evaluation.apply(value);
			
			if (eval == null)
				continue;
			
			if (bestValue != null) {
				if (eval < bestValue)
					continue;
				
				if (eval > bestValue)
					best.clear();
			}
			
			best.add(value);
			bestValue = eval;
		}
		
		return best;
	}
}
