package isi.died.tp.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.model.Insumo;

public class InsumoDaoDefault implements InsumoDao {

	private static List<Insumo> LISTA_INSUMOS  = new ArrayList<Insumo>();
	private static Integer ULTIMO_ID;
	
	private CsvSource dataSource;
	
	public InsumoDaoDefault() {
		dataSource = new CsvSource();
		if(LISTA_INSUMOS.isEmpty())
			this.cargarListaInsumos();
		
		ULTIMO_ID = this.maxID();
	}
	
	private int maxID() {

		int maxID = 0;
		
		for(Insumo ins: LISTA_INSUMOS) {
			if(ins.getId()>maxID)
				maxID = ins.getId();
		}
		
		return maxID;
	}
	
	public void cargarListaInsumos() {
		List<List<String>> insumos = dataSource.readFile("insumos.csv");
		for(List<String> filaInsumo : insumos) {
			Insumo aux = new Insumo();
			aux.loadFromStringRow(filaInsumo);
			LISTA_INSUMOS.add(aux);
		}
	}

	@Override
	public void agregarInsumo(Insumo insumo) {
		insumo.setId(ULTIMO_ID++);
		LISTA_INSUMOS.add(insumo);	
		try {
			dataSource.agregarFilaAlFinal("insumos.csv", insumo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
