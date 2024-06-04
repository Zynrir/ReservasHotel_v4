package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("Suite"),
    SIMPLE("Simple"),
    DOBLE("Doble"),
    TRIPLE("Triple");
    private String cadenaAMostrar;

    TipoHabitacion(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    @Override
    public String toString() {
        return ordinal() + " .- " + cadenaAMostrar;
    }
}
