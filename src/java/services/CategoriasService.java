package services;

import dao.DaoCategoria;
import dao.impl.DaoCategoriaImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import model.Categoria;

@ManagedBean(name = "categorias")
@ApplicationScoped
public class CategoriasService {

    private DaoCategoria dao;
    private Integer id = 0;
    private Categoria data = new Categoria();
    private String msg = null;
    private List<SelectItem> listacbo;
    
    @PostConstruct
    public void inicializarBean() {
        dao = new DaoCategoriaImpl();
    }

    public List<Categoria> categoriaSel() throws Exception {
        List<Categoria> lista = null;
        try {
            lista = dao.CategoriaSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
    
    public List<SelectItem> categoriaCBO() throws Exception {
        List<Categoria> lista = null;
        this.listacbo = new ArrayList<SelectItem>();
        
        listacbo.clear();
        try {
            lista = dao.CategoriaSel();
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


    public String categoriaGet(Integer id) throws Exception {
        try {
            data = dao.CategoriaGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "categoriaUpd?faces-redirect=true";
    }

    public Categoria categoriaGet() throws Exception {
        Categoria categoria = new Categoria();
        try {
            categoria = dao.CategoriaGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return categoria;
    }

    public String categoriaIns() throws Exception {
        msg = null;
        try {
            msg = dao.CategoriaIns(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "categorias?faces-redirect=true" : 
                "categoriaIns?faces-redirect=true";
    }

    public String categoriaUpd() throws Exception {
        msg = null;
        try {
            msg = dao.CategoriaUpd(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "categorias?faces-redirect=true" : 
                "categoriaUpd?faces-redirect=true";
    }

    public String insertar() {
        data = new Categoria();
        return "categoriaIns?faces-redirect=true";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getData() {
        return data;
    }

    public void setData(Categoria data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SelectItem> getListacbo() {
        return listacbo;
    }

    public void setListacbo(List<SelectItem> listacbo) {
        this.listacbo = listacbo;
    }

}
