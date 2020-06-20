package ar.edu.unlam.entidades1;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = -5159387314389978126L;
    private Instant instantCreacion;
    private Long userCreadorId;
    private Long userDestinoId;
    private Long salaOrigenId;
    private String data;

    public Mensaje(Long userCreadorId, Long userDestinoId, Long salaOrigenId, String data) {
        this.instantCreacion = Instant.now();
        this.userCreadorId = userCreadorId;
        this.userDestinoId = userDestinoId;
        this.salaOrigenId = salaOrigenId;
        this.data = data;
    }

    public Mensaje(Long userCreadorId, Long salaOrigenId, String data) {
        this.instantCreacion = Instant.now();
        this.userCreadorId = userCreadorId;
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
        return userCreadorId;
    }

    public void setUserCreadorId(Long userCreadorId) {
        this.userCreadorId = userCreadorId;
    }

    public Long getUserDestinoId() {
        return userDestinoId;
    }

    public void setUserDestinoId(Long userDestinoId) {
        this.userDestinoId = userDestinoId;
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
                Objects.equals(userCreadorId, mensaje.userCreadorId) &&
                Objects.equals(userDestinoId, mensaje.userDestinoId) &&
                Objects.equals(salaOrigenId, mensaje.salaOrigenId) &&
                Objects.equals(data, mensaje.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantCreacion, userCreadorId, userDestinoId, salaOrigenId, data);
    }
}
