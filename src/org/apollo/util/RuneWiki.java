package org.apollo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apollo.game.model.def.ItemDefinition;

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
		try {
			URL url = new URL("http://runescape.wikia.com/wiki/" + ItemDefinition.forId(id).getName().replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("</th><td>") && line.contains("*coins")) {
					String[] value = line.split(" ");
					if (value != null) {
						if (value[1] != null) {
							value[1] = value[1].replace("*coins", "").replace(",", "");
							if (values[0] == 0) {
								values[0] = Integer.parseInt(value[1]);
							} else if (values[0] != 0 && values[1] == 0) {
								values[1] = Integer.parseInt(value[1]);
							}
						}
					}
				}
			}
			in.close();
			return values;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * Returns an item's bonuses using the RuneWiki data.
	 * @param id The item.
	 * @param updated The updated flag.
	 * @return The item bonuses.
	 */
	public static double[] getBonuses(int id, boolean updated) {
		double[] bonuses = new double[18];
		int bonus = 0;
		boolean[] skipped = new boolean[] { false, false };
		try {
			URL url = new URL("http://runescape.wikia.com/wiki/" + ItemDefinition.forId(id).getName().replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (bonus < (updated ? 18 : 12)) {
					if (line.contains("<td colspan=\"2\" width=\"30\" align=\"center\">")) {
						if (bonus == 10 && !skipped[0] && !updated) {
							skipped[0] = true;
						} else {
							if (line.contains("</td>")) {
								line = line.replace("</td>", "");
							}
							line = line.replace("<td colspan=\"2\" width=\"30\" align=\"center\">", "");
							if (line.contains("-")) {
								bonuses[bonus] = 0 - Double.parseDouble(line.replace("-", ""));
							} else {
								bonuses[bonus] = Double.parseDouble(line.replace("+", ""));
							}
							bonus++;
						}
					} else if (line.contains("<td colspan=\"4\" width=\"60\" align=\"center\">") && updated) {
						if (line.contains("</td>")) {
							line = line.replace("</td>", "");
						}
						line = line.replace("<td colspan=\"4\" width=\"60\" align=\"center\">", "");
						if (line.contains("%")) {
							line = line.replace("%", "");
							line = "." + line;
						}
						bonuses[bonus] = Double.parseDouble(line);
						bonus++;
					} else if (line.contains("<td colspan=\"3\" width=\"45\" align=\"center\">")) {
						if (bonus == 11 && !skipped[1] && !updated) {
							skipped[1] = true;
						} else {
							if (line.contains("</td>")) {
								line = line.replace("</td>", "");
							}
							line = line.replace("<td colspan=\"3\" width=\"45\" align=\"center\">", "");
							if (line.contains("%")) {
								line = line.replace("%", "");
								line = "." + line;
							}
							if (line.contains("-")) {
								bonuses[bonus] = 0 - Double.parseDouble(line.replace("-", ""));
							} else {
								bonuses[bonus] = Double.parseDouble(line.replace("+", ""));
							}
							bonus++;
						}
					}
				}
			}
			in.close();
			return bonuses;
		} catch (IOException e) {
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
			String name = ItemDefinition.forId(id).getName();
			URL url = new URL("http://runescape.wikia.com/wiki/" + name.replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("</th><td>") && line.contains("&nbsp;coins")) {
					String[] value = line.split(" ");
					if (value != null) {
						if (value[1] != null) {
							value[1] = value[1].replace("&nbsp;coins", "").replace(",", "").replaceAll(" ", "");
							value[1] = value[1].replace("<small>(<a href=\"/wiki/Exchange:" + name + "\" title=\"Exchange:" + name + "\">update</a>)</small>", "");
							return Integer.parseInt(value[1]);
						}
					}
				}
			}
			in.close();
			return price;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return price;
	}

	/**
	 * Returns the examine information using the RuneWiki data.
	 * @param id The item id.
	 * @return The examine.
	 */
	public static String getExamine(int id) {
		String examine = ItemDefinition.forId(id).getDescription();
		try {
			String name = ItemDefinition.forId(id).getName();
			URL url = new URL("http://runescape.wikia.com/wiki/" + name.replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			int next = 0;
			while ((line = in.readLine()) != null) {
				if (line.contains("<th nowrap=\"nowrap\"><a href=\"/wiki/Examine\" title=\"Examine\">Examine</a>")) {
					next++;
				}
				if (line.contains("</th><td> ") && next == 1) {
					examine = line.replace("</th><td> ", "");
					return examine;
				}
			}
			in.close();
			return examine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return examine;
	}

	/**
	 * Returns the weight of an item using the RuneWiki data.
	 * @param id The item id.
	 * @return The weight.
	 */
	public static double getWeight(int id) {
		double weight = 0.0;
		try {
			String name = ItemDefinition.forId(id).getName();
			URL url = new URL("http://runescape.wikia.com/wiki/" + name.replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("</th><td> ") && line.contains("*kg")) {
					line = line.replace("</th><td> ", "");
					line = line.replace("*kg","");
					return Double.parseDouble("." + line);
				}
			}
			in.close();
			return weight;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return weight;
	}

}