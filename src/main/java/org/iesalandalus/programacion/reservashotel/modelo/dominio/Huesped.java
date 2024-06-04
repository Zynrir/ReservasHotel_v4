package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {

    private static final String ER_TELEFONO = "[0-9]{9}";
    private static final String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$";
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    private String formateaNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");
        }
        String[] nombre_apellidos = nombre.trim().toLowerCase().split("\\s+"); //La expresión regular \\s se interpreta como un espacio en blanco
        StringBuilder nombre_completo = new StringBuilder();
        for (String palabra : nombre_apellidos) {
            nombre_completo.append(palabra.substring(0, 1).toUpperCase()).append(palabra.substring(1)).append(" ");
        }
        return nombre_completo.toString().trim();
    }

    private static char calcularLetraDni(int numeroDni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = numeroDni % 23;
        return letras.charAt(indiceLetra);
    }

    private boolean comprobarLetraDni(String dni) {
        String regex = ER_DNI;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            String numeroDni = matcher.group(1);
            String letraDni = matcher.group(2).toUpperCase();
            char letraCalculada = calcularLetraDni(Integer.parseInt(numeroDni));
            if (letraCalculada == letraDni.charAt(0))
                return true;
            else {
                throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
            }
        } else {
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }
        if (telefono.isBlank()) {
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }
        if (telefono.matches(ER_TELEFONO)) {
            this.telefono = telefono;
        } else {
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null) {
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }
        if (correo.isBlank()) {
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }
        if (correo.matches(ER_CORREO)) {
            this.correo = correo;
        } else {
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (dni == null) {
            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }
        if (dni.isBlank()) {
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }
        if (comprobarLetraDni(dni)) {
            this.dni = dni;
        }
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }
        if (fechaNacimiento.isAfter(LocalDate.now()) || fechaNacimiento.plusYears(120).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de nacimiento no válida.");
        } else {
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    private String getIniciales() {
        String[] palabras = formateaNombre(nombre).split(" ");
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : palabras) {
            iniciales.append(palabra.charAt(0));
        }
        return iniciales.toString();
    }

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {
        setNombre(formateaNombre(nombre));
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    public Huesped(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        }
        this.nombre = huesped.nombre;
        this.dni = huesped.dni;
        this.correo = huesped.correo;
        this.telefono = huesped.telefono;
        this.fechaNacimiento = huesped.fechaNacimiento;

    }

    @Override
    public boolean equals(Object comprobarHuesped) {
        if (this == comprobarHuesped) return true;
        if (comprobarHuesped == null || getClass() != comprobarHuesped.getClass()) return false;
        Huesped huesped = (Huesped) comprobarHuesped;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("nombre=%s (" + getIniciales() + "), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s", nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}
