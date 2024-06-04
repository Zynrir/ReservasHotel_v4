package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    private List<Habitacion> copiaProfundaHabitaciones() {
        List<Habitacion> miHabitacion = new ArrayList<>();
        Iterator<Habitacion> iterator = coleccionHabitaciones.iterator();
        while (iterator.hasNext()) {
            Habitacion habitacion = iterator.next();
            if (habitacion instanceof Simple) {
                miHabitacion.add(new Simple((Simple) habitacion));
            } else if (habitacion instanceof Doble) {
                miHabitacion.add(new Doble((Doble) habitacion));
            } else if (habitacion instanceof Triple) {
                miHabitacion.add(new Triple((Triple) habitacion));
            } else if (habitacion instanceof Suite) {
                miHabitacion.add(new Suite((Suite) habitacion));
            }
        }
        return new ArrayList<>(miHabitacion);
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> habitacionesTipo = new ArrayList<>();
        for (Habitacion habitacion : coleccionHabitaciones) {
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (habitacion instanceof Simple) {
                    habitacionesTipo.add(new Simple((Simple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (habitacion instanceof Doble) {
                    habitacionesTipo.add(new Doble((Doble) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (habitacion instanceof Triple) {
                    habitacionesTipo.add(new Triple((Triple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (habitacion instanceof Suite) {
                    habitacionesTipo.add(new Suite((Suite) habitacion));
                }
            }
        }
        return new ArrayList<>(habitacionesTipo);
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitacion nula.");
        }
        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitacion con ese identificador.");
        }
        if (habitacion instanceof Simple) {
            coleccionHabitaciones.add(new Simple((Simple) habitacion));
        } else if (habitacion instanceof Doble) {
            coleccionHabitaciones.add(new Doble((Doble) habitacion));
        } else if (habitacion instanceof Triple) {
            coleccionHabitaciones.add(new Triple((Triple) habitacion));
        } else if (habitacion instanceof Suite) {
            coleccionHabitaciones.add(new Suite((Suite) habitacion));
        }
    }

    private int buscarIndice(Habitacion habitacion) {
        return coleccionHabitaciones.indexOf(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");
        }
        int indice = buscarIndice(habitacion);
        if (indice != -1) {
            if (coleccionHabitaciones.get(indice) instanceof Simple) {
                return new Simple((Simple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Doble) {
                return new Doble((Doble) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Triple) {
                return new Triple((Triple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Suite) {
                return new Suite((Suite) coleccionHabitaciones.get(indice));
            }
        }
        return null;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitacion nula.");
        }
        int indice = buscarIndice(habitacion);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitacion como la indicada.");
        }
        coleccionHabitaciones.remove(indice);
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }
}
