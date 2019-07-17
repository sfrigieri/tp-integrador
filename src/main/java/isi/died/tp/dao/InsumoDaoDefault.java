package isi.died.tp.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;

public class InsumoDaoDefault implements InsumoDao {

	private static List<Insumo> LISTA_INSUMOS  = new ArrayList<Insumo>();
	private static List<InsumoLiquido> LISTA_INSUMOS_LIQUIDOS  = new ArrayList<InsumoLiquido>();
	private static Integer ULTIMO_ID_NO_LIQUIDOS;
	private static Integer ULTIMO_ID_LIQUIDOS;
	
	private CsvSource dataSource;
	
	public InsumoDaoDefault() {
		dataSource = new CsvSource();
		if(LISTA_INSUMOS.isEmpty())
			this.cargarListaInsumos();
		if (LISTA_INSUMOS_LIQUIDOS.isEmpty())
			this.cargarListaInsumosLiquidos();
		ULTIMO_ID_NO_LIQUIDOS = this.maxIDNoLiquidos();
		ULTIMO_ID_LIQUIDOS = this.maxIDLiquidos();
	}
	
	private int maxIDNoLiquidos() {
		int maxID = 0;
		for(Insumo ins: LISTA_INSUMOS) {
			if(ins.getId()>maxID)
				maxID = ins.getId();
		}
		return maxID;
	}
	
	private int maxIDLiquidos() {
		int maxID = 0;
		for(InsumoLiquido ins: LISTA_INSUMOS_LIQUIDOS) {
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
	public void cargarListaInsumosLiquidos() {
		List<List<String>> insumosLiquidos = dataSource.readFile("insumosLiquidos.csv");
		for(List<String> filaInsumo : insumosLiquidos) {
			InsumoLiquido aux = new InsumoLiquido();
			aux.loadFromStringRow(filaInsumo);
			LISTA_INSUMOS_LIQUIDOS.add(aux);
		}
	}


	@Override
	public Insumo buscarInsumoNoLiquido(Integer id) {
		for(Insumo actual : LISTA_INSUMOS)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	@Override
	public InsumoLiquido buscarInsumoLiquido(Integer id) {
		for(InsumoLiquido actual : LISTA_INSUMOS_LIQUIDOS)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	
	@Override
	public void agregarInsumo(Insumo insumo) {
		if (insumo instanceof InsumoLiquido) {
			insumo.setId(++ULTIMO_ID_LIQUIDOS);
			LISTA_INSUMOS_LIQUIDOS.add((InsumoLiquido)insumo);
			try {
				dataSource.agregarFilaAlFinal("insumosLiquidos.csv", insumo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			insumo.setId(++ULTIMO_ID_NO_LIQUIDOS);
			LISTA_INSUMOS.add(insumo);	
			try {
				dataSource.agregarFilaAlFinal("insumos.csv", insumo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void editarInsumoNoLiquido(Integer id, Insumo insumo) {
		Insumo old = null;
		old = buscarInsumoNoLiquido(id);
		
		if(old != null) 
			LISTA_INSUMOS.remove(old);
			
		if(insumo instanceof InsumoLiquido)
			LISTA_INSUMOS_LIQUIDOS.add((InsumoLiquido)insumo);
		else
			LISTA_INSUMOS.add(insumo);
		
		this.actualizarArchivoLiquido();
		this.actualizarArchivo();
	}
	
	@Override
	public void editarInsumoLiquido(Integer id, Insumo insumo) {
		Insumo old = null;
		old = buscarInsumoLiquido(id);
		
		if(old != null) 
			LISTA_INSUMOS_LIQUIDOS.remove(old);
		
		if(insumo instanceof InsumoLiquido)
			LISTA_INSUMOS_LIQUIDOS.add((InsumoLiquido)insumo);
		else
			LISTA_INSUMOS.add(insumo);
		
		this.actualizarArchivo();
		this.actualizarArchivoLiquido();
	}
	
	@Override
	public void eliminarInsumo(Insumo insumo) {
		if (insumo instanceof InsumoLiquido) {
			LISTA_INSUMOS_LIQUIDOS.remove(insumo);
			actualizarArchivoLiquido();
		} else {
			LISTA_INSUMOS.remove(insumo);
			actualizarArchivo();
		}
		
		
	}
	
	@Override
	public List<Insumo> listaInsumos() {
		return LISTA_INSUMOS;
	}
	@Override
	public List<InsumoLiquido> listaInsumosLiquidos() {
		return LISTA_INSUMOS_LIQUIDOS;
	}
	
	private void actualizarArchivo() {
		File archivoInsumos = new File("insumos.csv");
		archivoInsumos.delete();
		try {
			archivoInsumos.createNewFile();
			for(Insumo actual: LISTA_INSUMOS) {
				try {
					dataSource.agregarFilaAlFinal("insumos.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
					}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	private void actualizarArchivoLiquido() {
		File archivoInsumos = new File("insumosLiquidos.csv");
		archivoInsumos.delete();
		try {
			archivoInsumos.createNewFile();
			for(Insumo actual: LISTA_INSUMOS_LIQUIDOS) {
				try {
					dataSource.agregarFilaAlFinal("insumosLiquidos.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
					}
			
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
