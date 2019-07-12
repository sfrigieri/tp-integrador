package isi.died.tp.service;

import isi.died.tp.dao.*;
import isi.died.tp.model.Insumo;

public class InsumoServiceDefault implements InsumoService {

	private InsumoDao insumoDao;

	public InsumoServiceDefault() {
		super();
		this.insumoDao = new InsumoDaoDefault();
	}
	
	@Override
	public Insumo agregarInsumo(Insumo insumo) {
		insumoDao.agregarInsumo(insumo);
		return insumo;
	}
}
