package services;

import dao.DaoUsuario;
import dao.impl.DaoUsuarioImpl;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Usuario;

@ManagedBean(name = "usuarios")
@ApplicationScoped
public class UsuariosService {

    private DaoUsuario dao;
    private Integer id = 0;
    private Usuario data = new Usuario();
    private String msg = null;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    public Integer idusersesion = 0;
    
    @PostConstruct
    public void inicializarBean() {
        dao = new DaoUsuarioImpl();
    }

    public List<Usuario> usuarioSel() throws Exception {
        List<Usuario> lista = null;
        try {
            lista = dao.UsuarioSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }

    public String usuarioGet(Integer id) throws Exception {
        try {
            data = dao.UsuarioGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "usuarioUpd?faces-redirect=true";
    }

    public String usuarioGetSession() throws Exception {
        String rt1 = "cuenta?faces-redirect=true";
        String rt2 = "login?faces-redirect=true";
        try {
            Usuario user = (Usuario) session.getAttribute("user");
            if (user == null || user.getId() == null) {
                return rt2;
            } else {
                data = dao.UsuarioGet(user.getId());
                if (user.getTipo().toUpperCase().equals("ADMIN")) {
                    return rt1;
                } else if (user.getTipo().toUpperCase().equals("CLIENT")) {
                    return "account?faces-redirect=true";
                }
            }

        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return rt2;
    }

    public Usuario usuarioGet() throws Exception {
        Usuario usuario = new Usuario();
        try {
            usuario = dao.UsuarioGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return usuario;
    }

    public String usuarioIns() throws Exception {
        String rt1 = "usuarios?faces-redirect=true";
        String rt2 = "usuarioUpd?faces-redirect=true";

        msg = null;
        try {
            if (data.getTipo() == null) {
                rt1 = "login?faces-redirect=true";
                rt2 = "registro?faces-redirect=true";
                data.setTipo("CLIENT");
            }
            msg = dao.UsuarioIns(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? rt1 : rt2;
    }

    public String usuarioUpd() throws Exception {
        msg = null;
        try {
            msg = dao.UsuarioUpd(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "usuarios?faces-redirect=true" : "usuarioUpd?faces-redirect=true";
    }

    public String cuentaUpd() throws Exception {
        msg = null;
        try {
            msg = dao.UsuarioUpd(data);
            if (data.getTipo().toUpperCase().equals("ADMIN")) {
                return "cuenta?faces-redirect=true";
            } else if (data.getTipo().toUpperCase().equals("CLIENT")) {
                return "account?faces-redirect=true";
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "cuenta?faces-redirect=true";
    }

    public String usuarioLogin() throws Exception {
        String redirect = "";
        msg = null;
        Usuario user = null;
        
        try {
            user = dao.UsuarioLogin(data);
            session.setAttribute("user", user);
            session.setAttribute("rol", user.getTipo().toUpperCase());
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }

        if (user.getId() != null) {
            if (user.getTipo().toUpperCase().equals("ADMIN")) {
                redirect = "dashboard/productos?faces-redirect=true";
            } else if (user.getTipo().toUpperCase().equals("CLIENT")) {
                redirect = "dashboard/orders?faces-redirect=true";
            }
            idusersesion = user.getId();
        } else {
            idusersesion = 0;
            redirect = "login?faces-redirect=true";
        }
        return redirect;
    }

    public String insertar() {
        data = new Usuario();
        return "usuarioIns?faces-redirect=true";
    }

    public String registrar() {
        data = new Usuario();
        return "registro?faces-redirect=true";
    }

    public String loguear() {
        data = new Usuario();
        return "login?faces-redirect=true";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void confirm() {
        addMessage("Confirmed", "You have accepted");
    }

    public void delete() {
        addMessage("Confirmed", "Record deleted");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getSesion() {
        String rt = "login";
        Usuario user = (Usuario) session.getAttribute("user");
        if (user != null) {
            if (user.getTipo().toUpperCase().equals("ADMIN")) {
                rt = "dashboard/productos?faces-redirect=true";
            } else if (user.getTipo().toUpperCase().equals("CLIENT")) {
                rt = "dashboard/orders?faces-redirect=true";
            }
        }
        
        return rt;
    }

    public String cerrarSesion() {
        session.removeAttribute("user");
        idusersesion = 0;
        return "/faces/login";
    }

    public Integer getIdusersesion() {
        return idusersesion;
    }

    public void setIdusersesion(Integer idusersesion) {
        this.idusersesion = idusersesion;
    }
    
    
}
