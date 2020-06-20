package ar.edu.unlam.entidades1;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

public class SalaChat implements Serializable {

    private static final long serialVersionUID = -6866793773941006764L;
    private Long id;
    private String nombreSala;
    private Set<Usuario> usuariosInSala;
    private HashMap<Long, Instant> tiempoConexionUsuario;
    private transient List<Mensaje> mensajes;
    private Usuario usuarioCreador;

    public SalaChat(Long id, String nombreSala, Set<Usuario> usuariosInSala, List<Mensaje> mensajes) {
        this.id = id;
        this.nombreSala = nombreSala;
        this.usuariosInSala = usuariosInSala;
        this.mensajes = mensajes;
    }

    public SalaChat(Long id, String nombreSala, Usuario usuario) {
        this.id = id;
        this.nombreSala = nombreSala;
        this.usuariosInSala = new HashSet<>();
        this.usuarioCreador = usuario;
        this.mensajes = new ArrayList<>();
        this.tiempoConexionUsuario = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public Set<Usuario> getUsuariosInSala() {
        return usuariosInSala;
    }

    public void setUsuariosInSala(Set<Usuario> usuariosInSala) {
        this.usuariosInSala = usuariosInSala;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public HashMap<Long, Instant> getTiempoConexionUsuario() {
        return tiempoConexionUsuario;
    }

    public void setTiempoConexionUsuario(HashMap<Long, Instant> tiempoConexionUsuario) {
        this.tiempoConexionUsuario = tiempoConexionUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaChat salaChat = (SalaChat) o;
        return Objects.equals(id, salaChat.id) &&
                Objects.equals(nombreSala, salaChat.nombreSala) &&
                Objects.equals(usuariosInSala, salaChat.usuariosInSala) &&
                Objects.equals(mensajes, salaChat.mensajes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreSala, usuariosInSala, mensajes);
    }

    public boolean addUsuario(Usuario usuario) {
        boolean isAgregado = this.usuariosInSala.add(usuario);
        if(isAgregado) {
            this.tiempoConexionUsuario.put(usuario.getId(), Instant.now());
        }
        return isAgregado;
    }

    public boolean removeUsuario(Usuario usuario) {
        boolean isRemovido = this.usuariosInSala.remove(usuario);
        if(isRemovido) {
            this.tiempoConexionUsuario.remove(usuario.getId());
        }
        return isRemovido;
    }

    public boolean hasUser(Usuario usuario) {
        return this.usuariosInSala.contains(usuario);
    }

    public boolean agregarMensaje(Mensaje mensaje) {
        return this.mensajes.add(mensaje);
    }

    public Usuario getUsuarioByUserName(String userName) {
        return this.usuariosInSala.stream()
                .filter(usuario -> usuario.getUserName().equalsIgnoreCase(userName))
                .findAny()
                .orElse(null);
    }

    public Usuario getUsuarioByUserId(Long userCreadorId) {
        return this.usuariosInSala.stream()
                .filter(usuario -> usuario.getId().equals(userCreadorId))
                .findAny()
                .orElse(null);
    }
}
