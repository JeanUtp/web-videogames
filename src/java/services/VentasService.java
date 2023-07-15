package services;

import dao.DaoProducto;
import dao.DaoVentas;
import dao.impl.DaoProductoImpl;
import dao.impl.DaoVentasImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Delivery;
import model.Detalle;
import model.Pedido;
import model.Producto;
import model.Usuario;

@ManagedBean(name = "ventas")
@ApplicationScoped
public class VentasService {

    DaoProducto daoProducto;
    DaoVentas daoVenta;
    List<Producto> productos = new ArrayList();
    Producto p = new Producto();

    List<Detalle> listaCarrito = new ArrayList();
    Integer item = 0;
    Double totalPagar = 0.0;
    Integer totalPagarApi = 0;
    Double tax = 0.0;
    Double totalBruto = 0.0;
    Integer cantidad = 1;

    Detalle car;

    private String msg = null;
    String direccion = "";

    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
    @PostConstruct
    public void inicializarBean() {
        daoProducto = new DaoProductoImpl();
        daoVenta = new DaoVentasImpl();
    }

    public String agregarCarrito(String idproducto) {
        Integer pos = 0;
        cantidad = 1;
        Integer idp = Integer.parseInt(idproducto);
        p = daoProducto.ProductoGet(idp);
        if (listaCarrito.size() > 0) {
            for (int i = 0; i < listaCarrito.size(); i++) {
                Integer aux = listaCarrito.get(i).getPro().getId();
                if (idp.equals(aux)) {
                    pos = i;
                }
            }
            Integer aux2 = listaCarrito.get(pos).getPro().getId();
            if (idp.equals(aux2)) {
                cantidad = listaCarrito.get(pos).getCantidad() + cantidad;
                Double subtotal = listaCarrito.get(pos).getPro().getPrecio() * cantidad;

                listaCarrito.get(pos).setCantidad(cantidad);
                listaCarrito.get(pos).setSubtotal(subtotal);
            } else {
                item++;
                car = new Detalle();
                Producto pro = new Producto(p.getId(), p.getNombre(), p.getPrecio(), p.getStock());

                car.setItem(item);
                car.setPro(pro);
                car.setCantidad(cantidad);

                car.setSubtotal(cantidad * p.getPrecio());

                listaCarrito.add(car);
            }
        } else {
            item++;
            car = new Detalle();
            Producto pro = new Producto(p.getId(), p.getNombre(), p.getPrecio(), p.getStock());

            car.setItem(item);
            car.setPro(pro);
            car.setCantidad(cantidad);
            car.setSubtotal(cantidad * p.getPrecio());

            listaCarrito.add(car);
        }
        pagoTotal();
        return "tienda?faces-redirect=true";
    }

    public Integer contador() {
        return listaCarrito.size();
    }

    public List<Detalle> getListaCarrito() {
        return listaCarrito;
    }

    public String cantidadAgr(Integer idproducto, Integer cantidad) {
        Integer idpro = idproducto;
        Integer cant = cantidad + 1;

        p = daoProducto.ProductoGet(idpro);
        if (cant <= p.getStock()) {
            for (int i = 0; i < listaCarrito.size(); i++) {
                Integer aux = listaCarrito.get(i).getPro().getId();
                if (aux.equals(idpro)) {
                    listaCarrito.get(i).setCantidad(cant);
                    Double st = listaCarrito.get(i).getPro().getPrecio() * cant;
                    listaCarrito.get(i).setSubtotal(st);
                }
            }
            pagoTotal();
        }

        return "carrito?faces-redirect=true";
    }

    public String cantidadDis(Integer idproducto, Integer cantidad) {
        Integer idpro = idproducto;
        Integer cant = cantidad - 1;

        if (cant >= 1) {
            for (int i = 0; i < listaCarrito.size(); i++) {
                Integer aux = listaCarrito.get(i).getPro().getId();
                if (aux.equals(idpro)) {
                    listaCarrito.get(i).setCantidad(cant);
                    Double st = listaCarrito.get(i).getPro().getPrecio() * cant;
                    listaCarrito.get(i).setSubtotal(st);
                }
            }
            pagoTotal();
        }

        return "carrito?faces-redirect=true";
    }

    public String quitarProducto(Integer idproducto) {

        for (int i = 0; i < listaCarrito.size(); i++) {
            Integer aux = listaCarrito.get(i).getPro().getId();
            if (aux.equals(idproducto)) {
                listaCarrito.remove(i);
            }
        }
        pagoTotal();
        return "carrito?faces-redirect=true";
    }

    public void pagoTotal() {
        totalBruto = 0.0;
        boolean vacio = true;
        for (int i = 0; i < listaCarrito.size(); i++) {
            totalBruto = totalBruto + listaCarrito.get(i).getSubtotal();
            vacio = false;
        }
        if(vacio){
            tax = 0.00;
        }else{
            tax = 10.00;
        }
        

        totalPagar = totalBruto + tax;

        Double totalPagarApiAux = totalPagar * 100;

        totalPagarApi = totalPagarApiAux.intValue();
    }

    public String comprar() throws Exception {
        msg = null;
        boolean exito = false;
        Delivery del = new Delivery(1);
        del.setDestino(this.direccion);

        Usuario user = (Usuario) session.getAttribute("user");

        if (user == null || user.getId() == null) {
            return "login?faces-redirect=true";
        }
        Pedido pedido = new Pedido();
        pedido.setDetalles(listaCarrito);
        pedido.setUsu(user);
        pedido.setDel(del);

        try {
            msg = daoVenta.ventasIns(pedido);
            listaCarrito.clear();
            direccion = "";
            totalBruto = 0.0;
            tax = 0.0;
            totalPagar = 0.0;
            totalPagarApi = 0;
            exito = true;
        } catch (Exception e) {
            msg = e.getMessage();
            throw new Exception(daoVenta.getMensaje());
        }
        return (exito == true) ? "success?faces-redirect=true" : "carrito?faces-redirect=true";
    }

    public String payment(){
        return "payment?faces-redirect=true";
    }
    
    public Double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(Double totalBruto) {
        this.totalBruto = totalBruto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTotalPagarApi() {
        return totalPagarApi;
    }

    public void setTotalPagarApi(Integer totalPagarApi) {
        this.totalPagarApi = totalPagarApi;
    }

}
