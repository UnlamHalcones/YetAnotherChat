package ar.edu.unlam.entidades;

public enum CommandType {
    CREAR_SALA,
    INFO_SALAS, // nombre de salas y cantidad de usuarios
    UNIRSE_SALA,
    SALIR_SALA,
    MENSAJE,
    ERROR,
    DISCONNECT,
    USER,
    LOGIN;
}
