package services;

import dao.DaoMotorizado;
import dao.impl.DaoMotorizadoImpl;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.Motorizado;

@ManagedBean(name = "motorizados")
@ApplicationScoped
public class MotorizadosService {

    private DaoMotorizado dao;
    private Integer id = 0;
    private Motorizado data = new Motorizado();
    private String msg = null;

    @PostConstruct
    public void inicializarBean() {
        dao = new DaoMotorizadoImpl();
    }

    public List<Motorizado> motorizadoSel() throws Exception {
        List<Motorizado> lista = null;
        try {
            lista = dao.MotorizadoSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }

    public String motorizadoGet(Integer id) throws Exception {
        try {
            data = dao.MotorizadoGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "motorizadoUpd?faces-redirect=true";
    }

    public Motorizado motorizadoGet() throws Exception {
        Motorizado motorizado = new Motorizado();
        try {
            motorizado = dao.MotorizadoGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return motorizado;
    }

    public String motorizadoIns() throws Exception {
        msg = null;
        try {
            msg = dao.MotorizadoIns(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "motorizados?faces-redirect=true" : "motorizadoIns?faces-redirect=true";
    }

    public String motorizadoUpd() throws Exception {
        msg = null;
        try {
            msg = dao.MotorizadoUpd(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "motorizados?faces-redirect=true" : "motorizadoUpd?faces-redirect=true";
    }

    public String insertar() {
        data = new Motorizado();
        return "motorizadoIns?faces-redirect=true";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Motorizado getData() {
        return data;
    }

    public void setData(Motorizado data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
