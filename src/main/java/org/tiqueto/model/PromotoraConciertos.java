package org.tiqueto.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class PromotoraConciertos extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(PromotoraConciertos.class);
	final WebCompraConciertos webCompra;
	private int REPOSICIONES;
	private int TOTAL_ENTRADAS;

	public PromotoraConciertos(WebCompraConciertos webCompra, int reposicionEntradas, int totalEntradas) {
		super();
		this.webCompra = webCompra;
		this.TOTAL_ENTRADAS = totalEntradas;
		this.REPOSICIONES = reposicionEntradas;
	}

	@Override
	public void run() {

		int i = 0;

		while (i < TOTAL_ENTRADAS) {
			logger.info("PROMOTOR: Voy a reponer mas entradas");
			int nEntradasRepuestas = webCompra.reponerEntradas(REPOSICIONES);

			if (nEntradasRepuestas > 0) logger.info("PROMOTOR: {} entradas repuestas", nEntradasRepuestas);

			if (!webCompra.hayEntradas()) logger.info("PROMOTOR: No hay mas entradas");

			try {
				Random rd = new Random();
				Thread.sleep(rd.nextInt(3000, 8001));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			i += REPOSICIONES;
		}

		logger.info("EL PROMOTOR TERMINA LA REPOSICION Y CIERRA LA VENTA");
		webCompra.cerrarVenta();
    }

}
