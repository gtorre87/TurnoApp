package Modelo;

public class TurnoLogic extends Turno{
    private String alias;
    private String turno;
    private String cantPasajero;
    private String tiempo;
    private int fotoChofer;

    public int getFotoChofer() {
        return fotoChofer;
    }

    public void setFotoChofer(int fotoChofer) {
        this.fotoChofer = fotoChofer;
    }

    public TurnoLogic(String alias, String turno, String cantPasajero, String tiempo, int fotoChofer) {
        this.alias = alias;
        this.turno = turno;
        this.cantPasajero = cantPasajero;
        this.tiempo = tiempo;
        this.fotoChofer = fotoChofer;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

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
}
