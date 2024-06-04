package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private List<Habitacion> coleccionHabitaciones;
    private final String COLECCION = "habitaciones";

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
        comenzar();
    }

    public List<Habitacion> get() {
        List<Habitacion> miHabitacion = new ArrayList<>();
        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));
        for (Document miDocumento : miIterador) {
            miHabitacion.add(MongoDB.getHabitacion(miDocumento));
        }
        return miHabitacion;
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
        return habitacionesTipo;
    }

    public int getTamano() {
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        Document miDocumento = MongoDB.getDocumento(habitacion);
        MongoDB.getBD().getCollection(COLECCION).insertOne(miDocumento);
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        Document miDocumento = MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.IDENTIFICADOR, habitacion.getIdentificador())).first();
        if (miDocumento == null)
            return null;
        else {
            return MongoDB.getHabitacion(miDocumento);
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitación no puede ser nula.");
        }
        if (buscar(habitacion) == null) {
            throw new OperationNotSupportedException("No existe esa habitación.");
        }
        if (MongoDB.getBD().getCollection("reservas").find(Filters.eq(MongoDB.HABITACION_IDENTIFICADOR, habitacion.getIdentificador())).first() != null) {
            throw new OperationNotSupportedException("ERROR: No se puede borrar una habitacion con reserva.");
        }
        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.IDENTIFICADOR, habitacion.getIdentificador()));
        coleccionHabitaciones.remove(habitacion);
    }

    @Override
    public void comenzar() {
        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find();
        for (Document miDocumento : miIterador) {
            coleccionHabitaciones.add(MongoDB.getHabitacion(miDocumento));
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
        coleccionHabitaciones = null;
    }
}
