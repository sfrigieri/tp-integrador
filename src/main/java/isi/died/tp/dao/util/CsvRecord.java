package isi.died.tp.dao.util;

import java.util.List;

public interface CsvRecord {
	public List<String> asCsvRow();
	public void loadFromStringRow(List<String> datos);
}
