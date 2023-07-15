package model;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Motorizado {
    private Integer id;
    private String nombre,apellidos,dni,matricula,marcamoto,colormoto;

    public Motorizado() {
    }

    public Motorizado(Integer id, String nombre, String apellidos, String dni, String matricula, String marcamoto, String colormoto) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.matricula = matricula;
        this.marcamoto = marcamoto;
        this.colormoto = colormoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarcamoto() {
        return marcamoto;
    }

    public void setMarcamoto(String marcamoto) {
        this.marcamoto = marcamoto;
    }

    public String getColormoto() {
        return colormoto;
    }

    public void setColormoto(String colormoto) {
        this.colormoto = colormoto;
    }
    
    
}
