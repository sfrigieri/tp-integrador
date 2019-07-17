package isi.died.tp.service;

import isi.died.tp.dao.*;

public class StockServiceDefault implements StockService {

	private StockDao stockDao;
	private PlantaService ps;

	public StockServiceDefault(PlantaService ps) {
		super();
		this.stockDao = new StockDaoDefault(ps);
		this.ps = ps;
	}
}
