package org.apollo.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A tool for updating the npc spawns.
 * @author Steve
 */
public final class PiSpawnUpdater {

	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage:");
			System.err.println("  java -cp ... org.apollo.tools.PiSpawnUpdater [input]");
			return;
		}
		final String path = args[0];
		final BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(path))));
		final BufferedWriter bw = new BufferedWriter(new FileWriter("data/npc-spawns.xml"));
		try {
			bw.write("<spawn>");
			String content = "";
			while ((content = br.readLine()) != null) {
				if (content.startsWith("spawn =")) {
					try {
						parseLine(content, bw);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			bw.newLine();
			bw.write("</spawn>");
		} finally {
			br.close();
			bw.close();
		}
	}

	/**
	 * Parses a line.
	 * @param content The line.
	 * @param bw The output stream.
	 * @throws IOException 
	 */
	private static void parseLine(String content, BufferedWriter bw) throws IOException {
		String[] arguments = content.split("\t");
		int id = Integer.parseInt(arguments[0].replace("spawn = ", ""));
		int x = Integer.parseInt(arguments[1]);
		int y = Integer.parseInt(arguments[2]);
		bw.newLine();
		bw.write("\t<npc id=\""+id+"\">");
		bw.newLine();
		bw.write("\t\t<position>");
		bw.newLine();
		bw.write("\t\t\t<x>"+x+"</x>");
		bw.newLine();
		bw.write("\t\t\t<y>"+y+"</y>");
		bw.newLine();
		bw.write("\t\t</position>");
		bw.newLine();
		bw.write("\t</npc>");
	}
}
