package services;

import dao.DaoProveedor;
import dao.impl.DaoProveedorImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import model.Proveedor;

@ManagedBean(name = "proveedores")
@ApplicationScoped
public class ProveedoresService {

    private DaoProveedor dao;
    private Integer id = 0;
    private Proveedor data = new Proveedor();
    private String msg = null;
    private List<SelectItem> listacbo;

    @PostConstruct
    public void inicializarBean() {
        dao = new DaoProveedorImpl();
    }

    public List<Proveedor> proveedorSel() throws Exception {
        List<Proveedor> lista = null;
        try {
            lista = dao.ProveedorSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
    public List<SelectItem> proveedorCBO() throws Exception {
        List<Proveedor> lista = null;
        this.listacbo = new ArrayList<SelectItem>();
        
        listacbo.clear();
        try {
            lista = dao.ProveedorSel();
            SelectItem seleccionar = new SelectItem(0,"Seleccionar...");
            listacbo.add(seleccionar);
            for (int i = 0; i < lista.size(); i++) {
                SelectItem s = new SelectItem(lista.get(i).getId(),lista.get(i).getNombre());
                listacbo.add(s);
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return listacbo;
    }

    public String proveedorGet(Integer id) throws Exception {
        try {
            data = dao.ProveedorGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "proveedorUpd?faces-redirect=true";
    }

    public Proveedor proveedorGet() throws Exception {
        Proveedor proveedor = new Proveedor();
        try {
            proveedor = dao.ProveedorGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return proveedor;
    }

    public String proveedorIns() throws Exception {
        msg = null;
        try {
            msg = dao.ProveedorIns(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "proveedores?faces-redirect=true" : "proveedorIns?faces-redirect=true";
    }

    public String proveedorUpd() throws Exception {
        msg = null;
        try {
            msg = dao.ProveedorUpd(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "proveedores?faces-redirect=true" : "proveedorUpd?faces-redirect=true";
    }

    public String insertar() {
        data = new Proveedor();
        return "proveedorIns?faces-redirect=true";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proveedor getData() {
        return data;
    }

    public void setData(Proveedor data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
