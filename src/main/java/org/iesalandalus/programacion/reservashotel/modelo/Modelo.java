package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;


import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Modelo implements IModelo {
    private IHuespedes huespedes;
    private IHabitaciones habitaciones;
    private IReservas reservas;
    private IFuenteDatos fuenteDatos;

    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {
        if (factoriaFuenteDatos == null) {
            throw new NullPointerException("ERROR: La factoria fuente datos no puede ser nula.");
        }
        setFuenteDatos(factoriaFuenteDatos.crear());
    }

    public void comenzar() {
        huespedes = fuenteDatos.crearHuespedes();
        habitaciones = fuenteDatos.crearHabitaciones();
        reservas = fuenteDatos.crearReservas();
    }

    public void terminar() {
        huespedes.terminar();
        habitaciones.terminar();
        reservas.terminar();
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public List<Huesped> getHuespedes() {
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {

        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones.get();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public Reserva buscar(Reserva reserva) {
        return reservas.buscar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public List<Reserva> getReservas() {
        return reservas.get();
    }

    public List<Reserva> getReservas(Huesped huesped) {
        return reservas.getReservas(huesped);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        return reservas.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservas(Habitacion habitacion) {
        return reservas.getReservas(habitacion);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckIn(reserva, fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckOut(reserva, fecha);
    }

    private void setFuenteDatos(IFuenteDatos fuenteDatos) {
        if (fuenteDatos == null) {
            throw new NullPointerException("ERROR: La fuente de datos no puede ser nulo.");
        }
        this.fuenteDatos = fuenteDatos;
    }
}
