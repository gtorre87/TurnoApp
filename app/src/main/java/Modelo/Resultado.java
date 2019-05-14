package Modelo;

public class Resultado {
    private boolean estado;
    private String mensaje;
    private String ayudaTexto;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
}
