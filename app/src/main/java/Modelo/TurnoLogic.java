package Modelo;

import java.util.Date;

public class TurnoLogic extends Chofer{
    private String turno;
    private String cantPasajero;
    private String tiempo;

    /*public TurnoLogic(String alias, String turno, String cantPasajero, String tiempo, int fotoChofer) {
        this.alias = alias;
        this.turno = turno;
        this.cantPasajero = cantPasajero;
        this.tiempo = tiempo;
        this.fotoChofer = fotoChofer;
    }*/


    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCantPasajero() {
        return cantPasajero;
    }

    public void setCantPasajero(String cantPasajero) {
        this.cantPasajero = cantPasajero;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }



    private int turnoId;
    private int tipo;
    private int rutaId;
    private Date Fecha;
    private String hora;
    private int orden;
    private int choferId;
    private int vehiculoId;
    private int estado;
    private double latitud;
    private double longitud;
    private int cantidadPasajeros;
    private boolean audiEstado;

    public int getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(int turnoId) {
        this.turnoId = turnoId;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getRutaId() {
        return rutaId;
    }

    public void setRutaId(int rutaId) {
        this.rutaId = rutaId;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getChoferId() {
        return choferId;
    }

    public void setChoferId(int choferId) {
        this.choferId = choferId;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public boolean isAudiEstado() {
        return audiEstado;
    }

    public void setAudiEstado(boolean audiEstado) {
        this.audiEstado = audiEstado;
    }
}
