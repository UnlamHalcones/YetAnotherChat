package ar.edu.unlam.entidades;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = -5159387314389978126L;
    private Instant instantCreacion;
    private Usuario userCreador;
    private Usuario userDestino;
    private Long salaOrigenId;
    private String data;

    public Mensaje(Usuario userCreador, Usuario userDestino, Long salaOrigenId, String data) {
        this.instantCreacion = Instant.now();
        this.userCreador = userCreador;
        this.userDestino = userDestino;
        this.salaOrigenId = salaOrigenId;
        this.data = data;
    }

    public Mensaje(Usuario userCreador, Long salaOrigenId, String data) {
        this.instantCreacion = Instant.now();
        this.userCreador = userCreador;
        this.salaOrigenId = salaOrigenId;
        this.data = data;
    }

    public Instant getInstantCreacion() {
        return instantCreacion;
    }

    public void setInstantCreacion(Instant instantCreacion) {
        this.instantCreacion = instantCreacion;
    }

    public Long getUserCreadorId() {
        return userCreador.getId();
    }

    public Long getUserDestinoId() {
        return userDestino.getId();
    }
    
    public Usuario getUserCreador() {
        return userCreador;
    }

    public void setUserCreador(Usuario userCreador) {
        this.userCreador = userCreador;
    }
    
    public String getUserNameCreador() {
        return userCreador.getUserName();
    }

    public String getUserNameDestino() {
        return userDestino.getUserName();
    }
    public Usuario getUserDestino() {
        return userDestino;
    }


    public void setUserDestino(Usuario userDestino) {
        this.userDestino = userDestino;
    }

    public Long getSalaOrigenId() {
        return salaOrigenId;
    }

    public void setSalaOrigenId(Long salaOrigenId) {
        this.salaOrigenId = salaOrigenId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensaje mensaje = (Mensaje) o;
        return Objects.equals(instantCreacion, mensaje.instantCreacion) &&
                Objects.equals(userCreador, mensaje.userCreador) &&
                Objects.equals(userDestino, mensaje.userDestino) &&
                Objects.equals(salaOrigenId, mensaje.salaOrigenId) &&
                Objects.equals(data, mensaje.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantCreacion, userCreador, userDestino, salaOrigenId, data);
    }
}
