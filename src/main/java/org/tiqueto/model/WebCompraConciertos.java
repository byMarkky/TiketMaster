package org.tiqueto.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tiqueto.IOperacionesWeb;

import java.util.concurrent.atomic.AtomicInteger;

public class WebCompraConciertos implements IOperacionesWeb {

	private static final Logger logger = LoggerFactory.getLogger(WebCompraConciertos.class);

	private AtomicInteger entradas = new AtomicInteger(0);
	private boolean ventaAbierta = true;

	public WebCompraConciertos() {
		super();
	}


	@Override
	public synchronized boolean comprarEntrada() {

		logger.info("INTENTANDO COMPRAR ENTRADAS");
		while(!hayEntradas() && ventaAbierta) {
			try {
				logger.info("NO HAY ENTRADAS, TOCA ESPERAR");
				this.wait();

			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

		logger.info("HAY ENTRADAS");

		this.entradas.decrementAndGet();
		notify();

		return true;
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {

		logger.info("INTENTANDO REPONER ENTRADAS");

		if (this.hayEntradas()) {
            try {
				logger.info("TODAVIA HAY ENTRADAS, TOCA ESPERAR");
				wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

		//entradas += numeroEntradas;
		entradas.addAndGet(numeroEntradas);
		notify();
		return entradasRestantes();
	}


	@Override
	public synchronized void cerrarVenta() {
		this.ventaAbierta = false;
		logger.info("VENTA CERRADA");
		notifyAll();
	}


	@Override
	public synchronized boolean hayEntradas() {
		return entradasRestantes() > 0;
	}


	@Override
	public synchronized int entradasRestantes() {
		return this.entradas.get();
	}

	public synchronized boolean isVentaAbierta() {
		return ventaAbierta;
	}
}
