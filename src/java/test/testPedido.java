package test;

import dao.impl.DaoPedidoImpl;
import java.util.List;
import model.Pedido;

public class testPedido {

    public static void main(String[] args) {
        /*
         *
         *Listar Pedidos
         * 
         */
        System.out.println("**LISTA DE PEDIDOS**");
        List<Pedido> lista;

        DaoPedidoImpl ds = new DaoPedidoImpl();
        try {

            lista = ds.PedidoSel();

            lista.forEach((Pedido t) -> {

                System.out.println(
                        "ID: " + t.getId()
                        + "\n NOMBRE User: " + t.getUsu().getNombre() + "\n NOMBRE Delevery: " + t.getDel().getMot().getNombre()
                        + "\n FECHA ENVIO: " + t.getDel().getFechaenvio() + "\n FECHA LLEGADA:" + t.getDel().getFechallegada()
                        + "\n FECHA DEL PEDIDO:" + t.getFecha());
            });
        } catch (Exception e) {
            ds.getMensaje();
        }
        System.out.println("**PEDIDO ESPECIFICADOS**");
         try {

            lista = ds.PedidoGet(1);

            lista.forEach((Pedido t) -> {

                System.out.println(
                        "Producto: " + t.getDet().getPro().getNombre()
                        + "\n Subtotal: " + t.getDet().getSubtotal());
            });
        } catch (Exception e) {
            ds.getMensaje();
        }
    }
}
