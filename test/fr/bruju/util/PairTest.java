package fr.bruju.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PairTest {
	@Test
	public void pairMembres() {
		Pair<Integer, Integer> paire = new Pair<>(3, 2);

		Assert.assertEquals(paire.getLeft(), Integer.valueOf(3));
		Assert.assertEquals(paire.getRight(), Integer.valueOf(2));

		Assert.assertEquals(Pair.k(paire), Integer.valueOf(3));
		Assert.assertEquals(Pair.v(paire), Integer.valueOf(2));
	}

	@Test
	public void pairIdentiques() {
		Pair<Integer, Integer> paire1 = new Pair<>(3, 2);
		Pair<Integer, Integer> paire2 = new Pair<>(3, 2);
		Pair<Integer, Integer> paire3 = new Pair<>(3, 3);

		Assert.assertEquals(paire1, paire2);
		Assert.assertNotEquals(paire1, paire3);
	}

	@Test
	public void pairMap() {
		List<Pair<Integer, String>> paires = new ArrayList<>();
		paires.add(new Pair<>(1, "un"));
		paires.add(new Pair<>(2, "deux"));
		paires.add(new Pair<>(3, "trois"));

		Map<Integer, String> map = paires.stream().collect(Pair.toMap());

		Assert.assertEquals(map.get(3), "trois");
		Assert.assertEquals(map.get(2), "deux");
		Assert.assertEquals(map.get(1), "un");
		Assert.assertEquals(map.get(5), null);
	}
}
