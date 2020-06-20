package ar.edu.unlam.entidades1;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {

    private static final long serialVersionUID = -2826623160321378710L;
    private Long id;
    private String userName;
    private transient int salasConectadas;

    public Usuario(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSalasConectadas() {
        return salasConectadas;
    }

    public void setSalasConectadas(int salasConectadas) {
        this.salasConectadas = salasConectadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(userName, usuario.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    public void aumentarCantSalasConectadas() {
        this.salasConectadas++;
    }

    public boolean puedeUnirseASala() {
        return this.salasConectadas < 3;
    }

    public void disminuirCantSalasConectadas() {
        this.salasConectadas--;
    }
}
