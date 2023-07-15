package model;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Filtro {
    private String nombreProducto;
    private Integer idCategoria;
    private Integer idProveedor;
    private Double precioMaximo;

    public Filtro() {
    }

    public Filtro(String nombreProducto, Integer idCategoria, Integer idProveedor, Double precioMaximo) {
        this.nombreProducto = nombreProducto;
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.precioMaximo = precioMaximo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }
    
    
}
