package dao;

import model.Pedido;
import java.util.List;

public interface DaoPedido {

    List<Pedido> PedidoSel();
    
    List<Pedido> MisPedidoSel(Integer iduser);

    List<Pedido> PedidoSel(Integer id);
    
    List<Pedido> PedidoGet(Integer id);
    
    List<Pedido> PedidoCLIGet(Integer iduser, Integer idprod);

    String PedidoDel(List<Integer> ids);
    
    String PedidoNuevoEstado(Integer idPedido, String estadoActual);
    
    String getMensaje();
}
