package org.tiqueto.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class FanGrupo extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FanGrupo.class);
	final WebCompraConciertos webCompra;
	int numeroFan;
	private final String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;
	private final int MAX_ENTRADAS_POR_FAN;

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

				while (webCompra.isVentaAbierta()) {

					logger.info("{}FAN_{}: Voy a comprar una entrada", tabuladores, numeroFan);

					if (webCompra.comprarEntrada()) {
						if (entradasCompradas >= MAX_ENTRADAS_POR_FAN) break;
						entradasCompradas++;
						logger.info("{}FAN_{}: He comprado una entrada, ya llevo {}", tabuladores, numeroFan, entradasCompradas);
						logger.info("{}FAN_{} me voy a dormir zzzz", tabuladores, numeroFan);
						Thread.sleep(new Random().nextInt(1000, 3001));
					}

				}
				if (!webCompra.isVentaAbierta()) logger.info("SE HA CERRADO LA VENTA :(");

			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
	}
	
	public void muestraEntradasCompradas() {
		logger.info("{}FAN_{}: TOTAL ENTRADAS COMPRADAS: {}", tabuladores, numeroFan, this.entradasCompradas);
	}
	

}
