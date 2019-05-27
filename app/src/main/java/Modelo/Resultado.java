package Modelo;

import java.util.List;

public class Resultado<T> {
    private boolean estado;
    private String titulo;
    private String mensaje;
    private String ayudaTexto;
    private int ayudaEntero;
    private List<T> lista;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAyudaTexto() {
        return ayudaTexto;
    }

    public void setAyudaTexto(String ayudaTexto) {
        this.ayudaTexto = ayudaTexto;
    }

    public int getAyudaEntero() {
        return ayudaEntero;
    }

    public void setAyudaEntero(int ayudaEntero) {
        this.ayudaEntero = ayudaEntero;
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }
}
