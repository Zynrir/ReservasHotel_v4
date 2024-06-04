package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MongoDB {
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String SERVIDOR = "reservashotel.8wvg4af.mongodb.net";
    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";
    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLES = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";
    private static MongoClient conexion;

    private MongoDB() {
    }

    public static MongoDatabase getBD() {
        if (conexion == null) {
            establecerConexion();
        }
        return conexion.getDatabase(BD);
    }

    private static void establecerConexion() {
        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;
        connectionString = "mongodb+srv://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + "/?retryWrites=true&w=majority";
        serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
        conexion = MongoClients.create(settings);
        try {
            MongoDatabase database = conexion.getDatabase(BD);
            database.runCommand(new Document("ping", 1));
        } catch (MongoException e) {
            System.out.println("Error: " + e);
        }
        System.out.println("Conexion a MongoDB realizada correctamente.");
        System.out.println("");
    }


    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexion a MongoDB cerrada.");
        }
    }

    public static Document getDocumento(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: El huesped no puede ser nulo.");
        }
        Document miDocumento = new Document().append(DNI, huesped.getDni()).append(NOMBRE, huesped.getNombre()).append(CORREO, huesped.getCorreo()).append(TELEFONO, huesped.getTelefono()).append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));
        return miDocumento;
    }

    public static Huesped getHuesped(Document documentoHuesped) {
        if (documentoHuesped == null) {
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Huesped miHuesped = new Huesped(documentoHuesped.getString(NOMBRE),
                documentoHuesped.getString(DNI),
                documentoHuesped.getString(CORREO),
                documentoHuesped.getString(TELEFONO),
                LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA));
        return miHuesped;
    }

    public static Document getDocumento(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitación no puede ser nula.");
        }
        Document miDocumento = new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(PRECIO, habitacion.getPrecio()).append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas()).append(IDENTIFICADOR, habitacion.getIdentificador());
        if (habitacion instanceof Simple) {
            miDocumento.append(TIPO, TIPO_SIMPLE);
        }
        if (habitacion instanceof Doble) {
            miDocumento.append(CAMAS_INDIVIDUALES, ((Doble) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Doble) habitacion).getNumCamasDobles()).append(TIPO, TIPO_DOBLE);
        }
        if (habitacion instanceof Triple) {
            miDocumento.append(CAMAS_INDIVIDUALES, ((Triple) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Triple) habitacion).getNumCamasDobles()).append(BANOS, ((Triple) habitacion).getNumBanos()).append(TIPO, TIPO_TRIPLE);
        }
        if (habitacion instanceof Suite) {
            miDocumento.append(BANOS, ((Suite) habitacion).getNumBanos()).append(JACUZZI, ((Suite) habitacion).isTieneJacuzzi()).append(TIPO, TIPO_SUITE);
        }
        return miDocumento;
    }

    public static Habitacion getHabitacion(Document documentoHabitacion) {
        if (documentoHabitacion == null) {
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Habitacion miHabitacion = null;
        if (documentoHabitacion.getString(TIPO).equals(TIPO_SIMPLE)) {
            miHabitacion = new Simple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_DOBLE)) {
            miHabitacion = new Doble(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_TRIPLE)) {
            miHabitacion = new Triple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_SUITE)) {
            miHabitacion = new Suite(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS),
                    documentoHabitacion.getBoolean(JACUZZI));
        }
        return miHabitacion;
    }

    public static Reserva getReserva(Document documentoReserva) {
        if (documentoReserva == null) {
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Reserva miReserva = new Reserva(
                getHuesped((Document) documentoReserva.get(HUESPED)),
                getHabitacion((Document) documentoReserva.get(HABITACION)),
                Regimen.valueOf(documentoReserva.getString(REGIMEN)),
                LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA), FORMATO_DIA),
                LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA), FORMATO_DIA),
                documentoReserva.getInteger(NUMERO_PERSONAS));
        if (documentoReserva.containsKey(CHECKIN)) {
            miReserva.setCheckIn(LocalDateTime.parse(documentoReserva.getString(CHECKIN), FORMATO_DIA_HORA));
        }
        if (documentoReserva.containsKey(CHECKOUT)) {
            miReserva.setCheckOut(LocalDateTime.parse(documentoReserva.getString(CHECKOUT), FORMATO_DIA_HORA));
        }
        return miReserva;
    }

    public static Document getDocumento(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        Document miDocumento = new Document().append(HUESPED, getDocumento(reserva.getHuesped())).append(HABITACION, getDocumento(reserva.getHabitacion())).append(REGIMEN, reserva.getRegimen().name()).append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA)).append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_DIA)).append(NUMERO_PERSONAS, reserva.getNumeroPersonas());
        return miDocumento;
    }
}
