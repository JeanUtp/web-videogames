package model;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Pedido {
    private Integer id;
    private String fecha;
    private Usuario usu;
    private Delivery del;
    private Detalle det;
    private String estado;
    private List<Detalle> detalles = new ArrayList<>();
    
    public Pedido() {
    }

    public Pedido(Integer id, String fecha, Usuario usu, Delivery del) {
        this.id = id;
        this.fecha = fecha;
        this.usu = usu;
        this.del = del;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public Delivery getDel() {
        return del;
    }

    public void setDel(Delivery del) {
        this.del = del;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public Detalle getDet() {
        return det;
    }

    public void setDet(Detalle det) {
        this.det = det;
    }

    public String getEstado(){
        return this.estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
}
