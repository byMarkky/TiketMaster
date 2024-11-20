package org.tiqueto.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class FanGrupo extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FanGrupo.class);
	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;
	private int MAX_ENTRADAS_POR_FAN;

	public FanGrupo(WebCompraConciertos web, int numeroFan, int maxEntradasPorFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
		this.MAX_ENTRADAS_POR_FAN = maxEntradasPorFan;
	}
	
	@Override
	public void run() {

			try {
				Thread.sleep(1000);

				while (entradasCompradas <= this.MAX_ENTRADAS_POR_FAN) {
					if (!webCompra.isVentaAbierta()) {
						logger.info("OH, SE HA CERRADO LA VENTA");
						break;
					}

					logger.info("VOY A COMPRAR UNA ENTRADA");
					if (webCompra.comprarEntrada()) {
						entradasCompradas++;
						logger.info("HE COMPRADO UNA ENTRADA Y LLEVO {}", entradasCompradas);
					}

				}

				Random rd = new Random();
				Thread.sleep(rd.nextInt(1000, 3001));
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
	}
	
	public void muestraEntradasCompradas() {
		logger.info("TOTAL ENTRADAS COMPRADAS: {}", this.entradasCompradas);
	}
	

}
