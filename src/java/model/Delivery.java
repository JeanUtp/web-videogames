package model;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Delivery {
    private Integer id;
    private String fechaenvio,fechallegada;
    private String destino;
    private Motorizado mot;

    public Delivery() {
    }

    public Delivery(Integer id) {
        this.id = id;
    }

    public Delivery(Integer id, String fechaenvio, String fechallegada, String destino, Motorizado mot) {
        this.id = id;
        this.fechaenvio = fechaenvio;
        this.fechallegada = fechallegada;
        this.destino = destino;
        this.mot = mot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(String fechaenvio) {
        this.fechaenvio = fechaenvio;
    }

    public String getFechallegada() {
        return fechallegada;
    }

    public void setFechallegada(String fechallegada) {
        this.fechallegada = fechallegada;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Motorizado getMot() {
        return mot;
    }

    public void setMot(Motorizado mot) {
        this.mot = mot;
    }

    
    
}
