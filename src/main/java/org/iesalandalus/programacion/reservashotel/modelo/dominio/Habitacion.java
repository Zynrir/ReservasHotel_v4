package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;
public abstract class Habitacion {
    public static final double MIN_PRECIO_HABITACION = 40;
    public static final double MAX_PRECIO_HABITACION = 150;
    public static final int MIN_NUMERO_PUERTA = 0;
    public static final int MAX_NUMERO_PUERTA = 14;
    public static final int MIN_NUMERO_PLANTA = 1;
    public static final int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;

    public Habitacion(int planta, int puerta, double precio) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
    }

    public Habitacion(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        this.planta = habitacion.planta;
        this.puerta = habitacion.puerta;
        this.precio = habitacion.precio;
        setIdentificador();
    }

    public abstract int getNumeroMaximoPersonas();

    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador() {
        if (planta <= 0 || puerta < 0) {
            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);
    }

    protected void setIdentificador(String identificador) {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("ERROR: El identificador no puede ser nulo o vacío.");
        }
        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        }
        this.planta = planta;
        try {
            setIdentificador();
        } catch (NullPointerException e) {
            System.out.println("ERROR: La planta de una habitación no puede ser nula.");
        }
    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 14.");
        }
        this.puerta = puerta;
        setIdentificador();
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
        } else {
            this.precio = precio;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return String.format("identificador=%s (%d-%d), precio habitación=%s", identificador, planta, puerta, precio);
    }
}
