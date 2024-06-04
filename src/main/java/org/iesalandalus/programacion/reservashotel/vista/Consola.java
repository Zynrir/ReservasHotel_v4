package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Iterator;

public class Consola {
    private Consola() {
    }

    public static void mostrarMenu() {
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        int opcionElegida;
        do {
            System.out.print("Elige una opcion: ");
            opcionElegida = Entrada.entero();
        } while (opcionElegida < 0 || opcionElegida > Opcion.values().length);
        return Opcion.values()[opcionElegida];
    }

    public static Huesped leerHuesped() {
        String nombre;
        do {
            System.out.print("Introduce el nombre del huesped: ");
            nombre = Entrada.cadena();
        } while (nombre == null || nombre.isBlank());
        String dni;
        String ER_DNI = "([0-9]{8})([A-Za-z])";
        do {
            System.out.print("Introduce el DNI del huesped: ");
            dni = Entrada.cadena();
        } while (dni == null || dni.isBlank() || !dni.matches(ER_DNI));
        String correo;
        String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$";
        do {
            System.out.print("Introduce el correo del huesped: ");
            correo = Entrada.cadena();
        } while (correo == null || correo.isBlank() || !correo.matches(ER_CORREO));
        String telefono;
        String ER_TELEFONO = "[0-9]{9}";
        do {
            System.out.print("Introduce el telefono del huesped: ");
            telefono = Entrada.cadena();
        } while (telefono == null || telefono.isBlank() || !telefono.matches(ER_TELEFONO));
        System.out.print("Introduce la fecha de nacimiento del huesped: ");
        LocalDate fechaNacimiento = Consola.leerFecha();
        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }

    public static Huesped leerHuespedPorDni() {
        System.out.print("Introduce el DNI del huesped: ");
        String dni = Entrada.cadena();
        return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000, 4, 4));
    }

    public static LocalDate leerFecha() {
        String fecha = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println("Formato dd/MM/yyyy");
            fecha = Entrada.cadena();
            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);
        LocalDate fechaFormato = LocalDate.parse(fecha, formato);
        return fechaFormato;
    }

    public static Habitacion leerHabitacion() {
        System.out.print("Introduce el numero de planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Introduce el numero de puerta de la habitacion: ");
        int puerta = Entrada.entero();
        System.out.print("Introduce el precio de la habitacion: ");
        double precio = Entrada.realDoble();
        System.out.println("Introduce el tipo de habitacion: ");
        TipoHabitacion tipoHabitacion = leerTipoHabitacion();
        if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
            return new Simple(planta, puerta, precio);
        } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
            System.out.println("¿Cuantas camas individuales desea? (Escoja entre 0 y 2)");
            int camasIndividuales = Entrada.entero();
            System.out.println("¿Cuantas camas dobles desea? (Escoja 0 o 1)");
            int camasDobles = Entrada.entero();
            return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
        } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
            System.out.println("¿Cuantas banos desea? Escoja entre 1 y 2");
            int numBanos = Entrada.entero();
            System.out.println("¿Cuantas camas individuales desea? Escoja entre 0 y 3");
            int camasIndividuales = Entrada.entero();
            System.out.println("¿Cuantas camas dobles desea? Escoja entre 0 y 1");
            int camasDobles = Entrada.entero();
            return new Triple(planta, puerta, precio, numBanos, camasIndividuales, camasDobles);
        } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
            String jacuzzi;
            do {
                System.out.println("¿Desea Jacuzzi en la habitacion? Introduzca si o no");
                jacuzzi = Entrada.cadena();
            } while (!jacuzzi.equalsIgnoreCase("si") && !jacuzzi.equalsIgnoreCase("no"));
            boolean jacuzziSuite = false;
            if (jacuzzi.equalsIgnoreCase("si")) {
                jacuzziSuite = true;
            }
            return new Suite(planta, puerta, precio, 2, jacuzziSuite);
        } else {
            return null;
        }
    }

    public static Habitacion leerHabitacionPorIdentificador() {
        System.out.print("Introduce la planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.println("Introduce la puerta de la habitacion: ");
        int puerta = Entrada.entero();
        try {
            TipoHabitacion tipoHabitacion = leerTipoHabitacion();
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                return new Simple(planta, puerta, 50);
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                return new Doble(planta, puerta, 50, 2, 0);
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                return new Triple(planta, puerta, 50, 2, 2, 1);
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                return new Suite(planta, puerta, 50, 2, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static TipoHabitacion leerTipoHabitacion() {
        System.out.println("Tipos de habitacion:");
        Iterator<TipoHabitacion> iterador = EnumSet.allOf(TipoHabitacion.class).iterator();
        while (iterador.hasNext()) {
            System.out.println(iterador.next());
        }
        System.out.print("Elige un tipo de habitacion: ");
        int tipoElegido;
        do {
            tipoElegido = Entrada.entero();
        } while (tipoElegido < 0 || tipoElegido >= TipoHabitacion.values().length);
        return TipoHabitacion.values()[tipoElegido];
    }

    public static Regimen leerRegimen() {
        System.out.println("Tipos de regimen:");
        Iterator<Regimen> iterador = EnumSet.allOf(Regimen.class).iterator();
        while (iterador.hasNext()) {
            System.out.println(iterador.next());
        }
        System.out.print("Elige un tipo de regimen: ");
        int regimenElegido = Entrada.entero();
        return Regimen.values()[regimenElegido];
    }

    public static int leerNumeroPersonas() {
        int numeroPersonas;
        do {
            System.out.print("Introduce el numero de personas: ");
            numeroPersonas = Entrada.entero();
        } while (numeroPersonas <= 0);
        return numeroPersonas;
    }

    public static LocalDateTime leerFechaHora(String mensaje) {
        while (!mensaje.matches("[0-3][0-9]/[01][0-9]/[0-9]{4} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
            System.out.print(mensaje + "No es un patron valido. Intentalo de nuevo. (dd/MM/yyyy hh:mm:ss");
            mensaje = Entrada.cadena();
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);
        return LocalDateTime.parse(mensaje, formato);
    }
}
