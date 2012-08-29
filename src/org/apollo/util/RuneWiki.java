package org.apollo.util;

import org.apollo.game.model.def.ItemDefinition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A utility that gets rune wiki bonuses.
 * @author Steve
 */
public final class RuneWiki {

	/**
	 * Returns an item's alch values using the RuneWiki data.
	 * @param id The item.
	 * @return The alch value.
	 */
	public static int[] getAlchValues(int id) {
		int[] values = new int[2];
		int i = 0;
		try {
			String url = "http://runescape.wikia.com/wiki/Exchange:" + ItemDefinition.forId(id).getName().replaceAll(" ", "_");
			Document doc = Jsoup.connect(url).get();
			Elements prices = doc.select("ul").get(39).select("li");
			values[i++] = Integer.parseInt(prices.get(1).ownText().replace(",", ""));
			values[i++] = Integer.parseInt(prices.get(2).ownText().replace(",", ""));
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * Returns an item's bonuses using the RuneWiki data.
	 * @param id The item.
	 * @return The item bonuses.
	 */
	public static double[] getBonuses(int id) {
		double[] bonuses = new double[18];
		int i = 0;
		try {
			String url = "http://runescape.wikia.com/wiki/" + ItemDefinition.forId(id).getName().replaceAll(" ", "_");
			System.out.println("Fetching " + url);
			Document doc = Jsoup.connect(url).get();
			Elements bonus = doc.select("tr");
			
			Element attack = bonus.get(20);
			for (Element element : attack.select("td")) {
				Double result = Double.parseDouble(element.text().trim());
				bonuses[i++] = result;
			}
			
			Element defence = bonus.get(23);
			for (Element element : defence.select("td")) {
				Double result = Double.parseDouble(element.text().trim());
				bonuses[i++] = result;
			}
		
			Element absorb = bonus.get(26);
			for (Element element : absorb.select("td")) {
				Double result = Double.parseDouble(element.text().trim().replace("%", ""));
				bonuses[i++] = result;
			}
			
			Element other = bonus.get(29);
			for (Element element : other.select("td")) {
				Double result = Double.parseDouble(element.text().trim().replace("%", ""));
				bonuses[i++] = result;
			}
			
			return bonuses;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bonuses;
	}

	/**
	 * Returns an item's price using the RuneWiki data.
	 * @param id The item id.
	 * @return The item price.
	 */
	public static int getPrice(int id) {
		int price = 0;
		try {
			String url = "http://runescape.wikia.com/wiki/Exchange:" + ItemDefinition.forId(id).getName().replaceAll(" ", "_");
			Document doc = Jsoup.connect(url).get();
			Element table = doc.getElementById("GEPrice");
			price = Integer.parseInt(table.text().replaceAll(",", ""));
			return price;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}

	/**
	 * Returns the weight of an item using the RuneWiki data.
	 * @param id The item id.
	 * @return The weight.
	 */
	public static double getWeight(int id) {
		double weight = 0.0;
		try {
			String url = "http://runescape.wikia.com/wiki/" + ItemDefinition.forId(id).getName().replaceAll(" ", "_");
			Document doc = Jsoup.connect(url).get();
			Element table = doc.select("tr").get(15).select("td").get(0);
			weight = Double.parseDouble(table.text().replaceAll("�kg", "").trim());
			return weight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weight;
	}

}