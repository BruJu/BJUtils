package fr.bruju.util;

/**
 * Collection de méthodes utilitaires simples utilisées souvent dans les lambda
 */
public class Lambda {


	/** Renvoie l'objet si objet1 et objet2 sont identiques au sens de ==. Renvoie null sinon. */
	public static <V> V referenceEgaleOuNull(V objet1, V objet2) {
		return objet1 == objet2 ? objet1 : null;
	}
}