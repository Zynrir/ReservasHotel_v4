package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {

    SOLO_ALOJAMIENTO("Solo Alojamiento", 0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y Desayuno", 15),
    MEDIA_PENSION("Media Pensión", 30),
    PENSION_COMPLETA("Pensión Completa", 50);
    private String cadenaAMostrar;
    private double incrementoPrecio;

    private Regimen(String cadenaAMostrar, int incrementoPrecio) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    @Override
    public String toString() {
        return ordinal() + " .- " + cadenaAMostrar;
    }
}
