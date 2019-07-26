package isi.died.tp.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.service.PlantaService;

public class InsumoDaoDefault implements InsumoDao {

	private static List<Insumo> LISTA_INSUMOS;
	private static List<Insumo> LISTA_INSUMOS_LIQUIDOS;
	private static Integer ULTIMO_ID_NO_LIQUIDOS;
	private static Integer ULTIMO_ID_LIQUIDOS;

	private CsvSource dataSource;

	public InsumoDaoDefault(PlantaService ps) {
		LISTA_INSUMOS = new ArrayList<Insumo>();
		LISTA_INSUMOS_LIQUIDOS = new ArrayList<Insumo>();

		if(ps.buscarAcopioInicial() != null) {
			dataSource = new CsvSource();
			if(LISTA_INSUMOS.isEmpty())
				this.cargarListaInsumos();
			if (LISTA_INSUMOS_LIQUIDOS.isEmpty())
				this.cargarListaInsumosLiquidos();
		}

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
		for(Insumo ins: LISTA_INSUMOS_LIQUIDOS) {
			if(ins.getId()>maxID)
				maxID = ins.getId();
		}
		return maxID;
	}

	public void cargarListaInsumos() {
		List<List<String>> insumos = null;

		try {
			insumos = dataSource.readFile("insumos.csv");
		} catch (FileNotFoundException e) {
			File archivo = new File("insumos.csv");
			try {
				archivo.createNewFile();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(insumos != null)
			for(List<String> filaInsumo : insumos) {
				Insumo aux = new Insumo();
				//No es necesario guardar id Stock, porque cada Stock guarda el idinsumo, y una vez cargados
				//los stocks en memoria se llama a InsumoService para asignar a cada insumo su stock correspondiente.
				aux.loadFromStringRow(filaInsumo);
				LISTA_INSUMOS.add(aux);
			}
	}

	public void cargarListaInsumosLiquidos() {
		List<List<String>> insumosLiquidos = null;
		try {
			insumosLiquidos = dataSource.readFile("insumosLiquidos.csv");
		} catch (FileNotFoundException e) {
			File archivo = new File("insumosLiquidos.csv");
			try {
				archivo.createNewFile();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(insumosLiquidos != null)
		for(List<String> filaInsumo : insumosLiquidos) {
			InsumoLiquido aux = new InsumoLiquido();
			aux.loadFromStringRow(filaInsumo);
			LISTA_INSUMOS_LIQUIDOS.add(aux);
		}
	}

	@Override
	public void setStocksAcopio(List<StockAcopio> lista) {
		for(StockAcopio s: lista) {
			if(s.getInsumo() instanceof InsumoLiquido)
				for(Insumo il : LISTA_INSUMOS_LIQUIDOS) {
					if(il.equals(s.getInsumo()))
						il.setStock(s);
				}
			else 
				for(Insumo i : LISTA_INSUMOS) {
					if(i.equals(s.getInsumo()))
						i.setStock(s);

				}
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
		for(Insumo actual : LISTA_INSUMOS_LIQUIDOS)
			if(actual.getId() == id)
				return (InsumoLiquido) actual;
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
	public void editarInsumoNoLiquido(Insumo insumo) {
		Insumo old;
		old = buscarInsumoNoLiquido(insumo.getId());

		if(old != null)
			LISTA_INSUMOS.remove(old); 

		//No es necesario asignar un id nuevo, 
		//creo otro objeto con los nuevos datos pero mantengo el id
		LISTA_INSUMOS.add(insumo);

		this.actualizarArchivo();
	}

	@Override
	public void editarInsumoLiquido(Insumo insumo) {
		InsumoLiquido old;
		old = buscarInsumoLiquido(insumo.getId());

		if(old != null)
			LISTA_INSUMOS_LIQUIDOS.remove(old); 

		//No es necesario asignar un id nuevo, 
		//creo otro objeto con los nuevos datos pero mantengo el id
		LISTA_INSUMOS_LIQUIDOS.add((InsumoLiquido) insumo);

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
	public List<Insumo> listaInsumosLiquidos() {
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
