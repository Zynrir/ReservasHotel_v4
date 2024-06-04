package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {
    private final List<Reserva> coleccionReservas;

    public Reservas() {
        this.coleccionReservas = new ArrayList<>();
    }

    public List<Reserva> get() {
        return copiaProfundaReservas();
    }

    private List<Reserva> copiaProfundaReservas() {
        List<Reserva> misReservas = new ArrayList<>();
        Iterator<Reserva> reservaIt = coleccionReservas.iterator();
        while (reservaIt.hasNext()) {
            misReservas.add(new Reserva(reservaIt.next()));
        }
        return misReservas;
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(new Reserva(reserva));
    }

    public Reserva buscar(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                return new Reserva(actual);
            }
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (!coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        List<Reserva> miReserva = new ArrayList<>();
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHuesped().equals(huesped)) {
                miReserva.add(new Reserva(actual));
            }
        }
        return miReserva;
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        List<Reserva> habitacionesTipo = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (reserva.getHabitacion() instanceof Simple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (reserva.getHabitacion() instanceof Doble) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (reserva.getHabitacion() instanceof Triple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (reserva.getHabitacion() instanceof Suite) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            }
        }
        return new ArrayList<>(habitacionesTipo);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        LocalDate fechaActual = LocalDate.now();
        List<Reserva> reservasFuturas = new ArrayList<>();
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHabitacion().equals(habitacion) && actual.getFechaInicioReserva().isAfter(fechaActual)) {
                reservasFuturas.add(new Reserva(actual));
            }
        }
        return reservasFuturas;
    }

    public List<Reserva> getReservas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        List<Reserva> reservasHabitacion = new ArrayList<>();
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHabitacion().equals(habitacion)) {
                reservasHabitacion.add(new Reserva(actual));
            }
        }
        return reservasHabitacion;
    }

    @Override
    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if (!coleccionReservas.contains(reserva)) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkIn no puede ser anterior a la reserva.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                actual.setCheckIn(fecha);
                return;
            }
        }
    }

    @Override
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if (reserva.getCheckIn() == null) {
            throw new NullPointerException("ERROR: No puedes hacer checkOut si el checkIn es nulo.");
        }
        if (!coleccionReservas.contains(reserva)) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()) || fecha.isBefore(reserva.getCheckIn())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkOut no puede ser anterior a la de inicio de reserva o antes del checkIn.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                actual.setCheckOut(fecha);
                return;
            }
        }
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }
}
