package isi.died.tp.dao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import isi.died.tp.dao.util.CsvRecord;

public class CsvSource {
	private char separador = ',';
	private char delimitadorString = '"';

	public List<List<String>> readFile(String fileName) throws FileNotFoundException {
		List<List<String>> filas = new ArrayList<>();
		Scanner scanner;
			scanner = new Scanner(new File(fileName));

			while (scanner.hasNext()) {
				List<String> line = parseLine(scanner.nextLine());
				filas.add(line);
			}
			scanner.close();

		return filas;
	}

	private List<String> parseLine(String cvsLine) {

		List<String> result = new ArrayList<>();
		// if empty, return!
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}
		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == delimitadorString) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == delimitadorString) {

					inQuotes = true;

					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && delimitadorString == '\"') {
						curVal.append('"');
					}

					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separador) {

					result.add(curVal.toString());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}

	public void guardarColeccion(String archivoCsv, List<CsvRecord> datos) throws IOException {
		FileWriter writer;
		writer = new FileWriter(archivoCsv);
		for (CsvRecord fila : datos) {
			this.writeLine(writer, fila.asCsvRow());
		}
		writer.flush();
		writer.close();
	}
	
	public void agregarFilaAlFinal(String archivoCsv, List<String> fila) throws IOException {
		FileWriter writer = new FileWriter(archivoCsv,true); 
		this.writeLine(writer, fila);
		writer.flush();
		writer.close();

	}
	
	public void agregarFilaAlFinal(String archivoCsv, CsvRecord fila) throws IOException {
		FileWriter writer = new FileWriter(archivoCsv,true); 
		this.writeLine(writer, fila.asCsvRow());
		writer.flush();
		writer.close();

	}

	// https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	private void writeLine(Writer w, List<String> values) throws IOException {

		boolean first = true;

		// default customQuote is empty

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(this.separador);
			}
			if (this.delimitadorString == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(this.delimitadorString).append(followCVSformat(value)).append(this.delimitadorString);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());

	}

}
