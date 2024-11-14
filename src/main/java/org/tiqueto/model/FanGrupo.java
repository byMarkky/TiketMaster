package org.tiqueto.model;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}
	
	@Override
	public void run() {
		
		//TODO
		
	}
	
	public void muestraEntradasCompradas() {
		//TODO: muestra las entradasCompradas en el log
	}
	

}
