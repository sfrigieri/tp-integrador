package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.RutaDao;
import isi.died.tp.dao.RutaDaoDefault;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Planta;
import isi.died.tp.model.StockAcopio;

public class RutaServiceDefault implements RutaService {

	private RutaDao rutaDao;
	private PlantaService ps;
	private CamionService cs;

	public RutaServiceDefault(PlantaService ps, CamionService cs) {
		super();
		this.rutaDao = new RutaDaoDefault(ps);
		this.ps = ps;
		this.cs = cs;
		if(!rutaDao.listaRutas().isEmpty())
			ps.setRutas(rutaDao.listaRutas());
		ps.setRutaService(this);
		if(!ps.listaPlantas().isEmpty()) {/*
			for (Recorrido r : ps.buscarCaminosInfo(ps.buscarAcopioInicial(), ps.buscarPlantaProduccion(2))) {
				System.out.println("Distancia Total: "+r.getDistanciaTotal()+"Km");
				System.out.println("Peso Máximo: "+r.getPesoMax()+" Toneladas");
				System.out.println("Duración Viaje: "+r.getDuracionTotal()+" minutos");
				System.out.println("Recorrido:");
				for(Ruta ruta : r.getRecorrido())
					System.out.print(ruta.getInicio().getValor().getNombre()+"--"+ruta.getFin().getValor().getNombre());
				System.out.println(" ");
				System.out.println(" ");
			}
			System.out.println(" ");
			System.out.println("Flujo Máximo: "+ps.flujoMaximoRed(ps.buscarAcopioInicial())+" Toneladas");
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("Page Ranks:");
			ps.resetPageRanks();
			ps.generarPageRanks();
			System.out.println(" ");
			System.out.println(" ");
			List<Insumo> ins = ps.buscarAcopioInicial().getListaDeInsumos();
			Insumo i1 = null;
			for(Insumo i : ins)
				if(i.getDescripcion().matches("F3"))
					i1 = i;
			Recorrido r = ps.mejorCaminoEnvio(ps.buscarPlantasNecesitanInsumo(i1));
			if(r!= null) {
				System.out.println("Mejor Camino para Envío Insumo: "+i1.getDescripcion());
				System.out.println("Distancia Total: "+r.getDistanciaTotal()+"Km");
				System.out.println("Duración Viaje: "+r.getDuracionTotal()+" minutos");
				System.out.println("Recorrido:");
				for(Ruta ruta : r.getRecorrido())
					System.out.print(ruta.getInicio().getValor().getNombre()+"--"+ruta.getFin().getValor().getNombre());
				System.out.println(" ");
				System.out.println(" ");
			}
		*/	
		}
	}

	@Override
	public Ruta agregarRuta(Ruta ruta) {
		rutaDao.agregarRuta(ruta);	
		ps.setRutas(rutaDao.listaRutas());
		return ruta;
	}

	@Override
	public List<Ruta> listaRutas() {
		return rutaDao.listaRutas();
	}

	@Override
	public void editarRuta(Ruta ruta) {
		rutaDao.editarRuta(ruta);
		ps.setRutas(rutaDao.listaRutas());
	}

	@Override
	public void eliminarRuta(Ruta ruta) {
		rutaDao.eliminarRuta(ruta);
		ps.setRutas(rutaDao.listaRutas());
	}

	@Override
	public Ruta buscarRuta(Integer id) {
		return rutaDao.buscarRuta(id);
	}

	//Si se elimina una planta, en el Grafo se eliminarán las rutas incidentes,
	//por lo que es necesario actualizar desde PlantaService la LISTA_RUTAS en RutaService
	@Override
	public void setRutas(List<Arista<Planta>> listaRutas) {
		rutaDao.setRutas(listaRutas);

	}

	@Override
	public boolean existeRuta(Planta inicio, Planta fin){
		return rutaDao.existeRuta(inicio, fin);
	}
}
