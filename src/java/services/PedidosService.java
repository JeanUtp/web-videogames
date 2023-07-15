
package services;

import dao.DaoPedido;
import dao.impl.DaoPedidoImpl;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Pedido;
import model.Usuario;
import validate.EstadoPedido;

@ManagedBean(name = "pedidos")
@ApplicationScoped
public class PedidosService {
    private DaoPedido dao;
    private Integer id = 0;
    private Pedido data = new Pedido();
    private String nuevoEstado = "";
    private String msgEstado = "";
    private boolean btnTrazabilidad = true;

    private String msg = null;
    
    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @PostConstruct
    public void inicializarBean() {
        dao = new DaoPedidoImpl();
    }

    public List<Pedido> pedidoSel() throws Exception {
        List<Pedido> lista = null;
        try {
            lista = dao.PedidoSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
    
     public List<Pedido> pedidoSel(Integer id) throws Exception {
        List<Pedido> lista = null;
        try {
            lista = dao.PedidoSel(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
     
     public List<Pedido> misPedidosSel() throws Exception {
        List<Pedido> lista = null;
        try {
            Usuario user = (Usuario) session.getAttribute("user");
            if(user!=null){
                lista = dao.MisPedidoSel(user.getId());
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }


    public List<Pedido> pedidoGet(Integer id) throws Exception {
        List<Pedido> lista = null;
        try {
            lista = dao.PedidoGet(id);
            
            String estadoActual = lista.get(0).getEstado();            
            if(estadoActual.toUpperCase().equals("PENDIENTE")){
                nuevoEstado = EstadoPedido.ACEPTADO.getValue();
                msgEstado = "Aceptar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("ACEPTADO")){
                nuevoEstado = EstadoPedido.EN_CAMINO.getValue();
                msgEstado = "Enviar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("EN CAMINO")){
                nuevoEstado = EstadoPedido.ENTREGADO.getValue();
                msgEstado = "Pedido Entregado";
                btnTrazabilidad = true;
            }else{
                btnTrazabilidad = false;
                nuevoEstado = "";
                msgEstado = "";
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }

    public List<Pedido> pedidoCLIGet(Integer id) throws Exception {
        List<Pedido> lista = null;
        try {
            lista = dao.PedidoGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }

    public String detalle(Integer id){
        List<Pedido> lista = null;
        try {
            lista = dao.PedidoGet(id);
            
            String estadoActual = lista.get(0).getEstado();            
            if(estadoActual.toUpperCase().equals("PENDIENTE")){
                nuevoEstado = EstadoPedido.ACEPTADO.getValue();
                msgEstado = "Aceptar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("ACEPTADO")){
                nuevoEstado = EstadoPedido.EN_CAMINO.getValue();
                msgEstado = "Enviar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("EN CAMINO")){
                nuevoEstado = EstadoPedido.ENTREGADO.getValue();
                msgEstado = "Pedido Entregado";
                btnTrazabilidad = true;
            }else{
                btnTrazabilidad = false;
                nuevoEstado = "";
                msgEstado = "";
            }
        } catch (Exception e) {

        }
        data.setId(id);
        return "detallePedido?faces-redirect=true";
    
    }
    public String miPedidoDetalle(Integer id){
        data.setId(id);
        return "miPedidoDetalle?faces-redirect=true";
    }
    
    public String cambiarEstado() throws Exception{
        try {
            dao.PedidoNuevoEstado(data.getId(),nuevoEstado);
            String estadoActual = nuevoEstado;
            
            if(estadoActual.toUpperCase().equals("PENDIENTE")){
                nuevoEstado = EstadoPedido.ACEPTADO.getValue();
                msgEstado = "Aceptar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("ACEPTADO")){
                nuevoEstado = EstadoPedido.EN_CAMINO.getValue();
                msgEstado = "Enviar Pedido";
                btnTrazabilidad = true;
            }else if(estadoActual.toUpperCase().equals("EN CAMINO")){
                nuevoEstado = EstadoPedido.ENTREGADO.getValue();
                msgEstado = "Pedido Entregado";
                btnTrazabilidad = true;
            }else{
                btnTrazabilidad = false;
                nuevoEstado = "";
                msgEstado = "";
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "detallePedido?faces-redirect=true";
    }
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getData() {
        return data;
    }

    public void setData(Pedido data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public String getMsgEstado() {
        return msgEstado;
    }

    public void setMsgEstado(String msgEstado) {
        this.msgEstado = msgEstado;
    }

    public boolean isBtnTrazabilidad() {
        return btnTrazabilidad;
    }

    public void setBtnTrazabilidad(boolean btnTrazabilidad) {
        this.btnTrazabilidad = btnTrazabilidad;
    }

    
}
