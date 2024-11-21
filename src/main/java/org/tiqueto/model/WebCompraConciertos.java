package org.tiqueto.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tiqueto.IOperacionesWeb;

import java.util.Random;
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

		// Mientras no haya entradas pero la venta SI ESTE ABIERTA
		while(!hayEntradas() && ventaAbierta) {
			try {
				logger.info("WEB: SOLD OUT! No hay entradas, espera se repongan");
				this.wait();
				// Comprobamos si despues de esperar la venta sigue abierta
				if (!ventaAbierta) return false;

			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

		logger.info("WEB: HAY {} ENTRADAS", entradas.get());

		this.entradas.decrementAndGet();

        notify();	// Liberamos al promotor para ver si puede reponer entradas

		return true;
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {

		while (this.hayEntradas()) {
            try {
				logger.info("WEB: TODAVIA HAY ENTRADAS, TOCA ESPERAR");
				wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

		entradas.addAndGet(numeroEntradas);
		logger.info("Web: Ahora hay {} entradas", entradas.get());
		notify();
		return entradasRestantes();
	}


	@Override
	public synchronized void cerrarVenta() {
		this.ventaAbierta = false;
		logger.info("PROMOTOR: VENTA CERRADA");
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
