package dao;

import model.Pedido;


public interface DaoVentas {

    String ventasIns(Pedido pedido);
    
    void preferenciaIns(Integer idUsuario, Integer idProducto, Integer valoracion);

    String getMensaje();
}
