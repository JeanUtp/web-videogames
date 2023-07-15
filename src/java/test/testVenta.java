
package test;

import dao.DaoVentas;
import dao.impl.DaoVentasImpl;
import java.util.ArrayList;
import java.util.List;
import model.Delivery;
import model.Detalle;
import model.Pedido;
import model.Producto;
import model.Usuario;

public class testVenta {
    public static void main(String[] args) {
        DaoVentas dv = new DaoVentasImpl();
        Delivery del01 = new Delivery();
        del01.setDestino("mz m1 lt16 Puentre piedra Lima");

        Detalle det01 = new Detalle();
        Producto pro01 = new Producto(1);
        det01.setCantidad(3);
        det01.setPro(pro01);

        Detalle det02 = new Detalle();
        Producto pro02 = new Producto(2);
        det02.setPro(pro02);
        det02.setCantidad(4);

        List<Detalle> list = new ArrayList();
        list.add(det01);
        list.add(det02);
        
        Usuario user = new Usuario();
        user.setId(1);
        
        Pedido pedido = new Pedido();
        pedido.setDetalles(list);
        pedido.setDel(del01);
        pedido.setUsu(user);

        try {
            String msg = dv.ventasIns(pedido);
            System.out.println("Mensaje: "+msg);
        } catch (Exception e) {
            System.out.println(dv.getMensaje());
        }
    }
}
