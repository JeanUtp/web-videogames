
package dao.impl;

import dao.DaoPedido;
import model.Delivery;
import model.Detalle;
import model.Motorizado;
import model.Pedido;
import model.Producto;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBD;

public class DaoPedidoImpl implements DaoPedido{
    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;

    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoPedidoImpl() {
        this.conectaDb = new ConectaBD();
    }
    @Override
    public List<Pedido> PedidoSel() {
       List<Pedido> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM pedidos as p")
                .append(" INNER JOIN usuarios as u ")
                .append("on p.idusuario = u.idusuario ")
                .append("INNER JOIN delivery as d on p.iddelivery = d.iddelivery")
                .append(" INNER JOIN motorizados as mot on mot.idmotorizado = d.idmotorizado");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                Usuario usuario = new Usuario(rs.getInt(3));
                
                Delivery delivery = new Delivery(rs.getInt(4));
                delivery.setFechaenvio(rs.getString(15));
                delivery.setFechallegada(rs.getString(16));
                delivery.setDestino(rs.getString(17));
                
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(19));
                motorizado.setNombre(rs.getString(20));

                usuario.setNombre(rs.getString(8));
                delivery.setMot(motorizado);
                
                pedido.setId(rs.getInt(1));
                pedido.setFecha(rs.getString(2));
                pedido.setDel(delivery);
                pedido.setUsu(usuario);

                list.add(pedido);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }
    
    @Override
    public List<Pedido> PedidoSel(Integer id) {
        List<Pedido> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM pedidos as p")
                .append(" INNER JOIN usuarios as u ")
                .append("on p.idusuario = u.idusuario ")
                .append("INNER JOIN delivery as d on p.iddelivery = d.iddelivery")
                .append(" INNER JOIN motorizados as mot on mot.idmotorizado = d.idmotorizado")
                .append(" WHERE p.idusuario = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                Usuario usuario = new Usuario(rs.getInt(3));
                
                Delivery delivery = new Delivery(rs.getInt(4));
                delivery.setFechaenvio(rs.getString(15));
                delivery.setFechallegada(rs.getString(16));
                delivery.setDestino(rs.getString(17));
                
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(19));
                motorizado.setNombre(rs.getString(20));

                usuario.setNombre(rs.getString(8));
                delivery.setMot(motorizado);
                
                pedido.setId(rs.getInt(1));
                pedido.setFecha(rs.getString(2));
                pedido.setDel(delivery);
                pedido.setUsu(usuario);

                list.add(pedido);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    @Override
    public List<Pedido> PedidoGet(Integer id) {
        List<Pedido> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM pedidos as p")
                .append(" INNER JOIN pedidosdetalle as pd on p.idpedido = pd.idpedido")
                .append(" INNER JOIN productos as pro ON pro.idproducto = pd.idproducto")
                .append(" WHERE p.idpedido = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                Producto pro = new Producto();
                pro.setNombre(rs.getString(12));
                pro.setPrecio(rs.getDouble(14));
                
                Detalle det = new Detalle();
                det.setCantidad(rs.getInt(7));
                det.setSubtotal(rs.getDouble(10));
                det.setPro(pro);
                
                pedido.setDet(det);
                pedido.setId(id);
                pedido.setEstado(rs.getString(5));
                list.add(pedido);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;   
    
    }

    @Override
    public String PedidoDel(List<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMensaje() {
        return this.mensaje;
    }

    @Override
    public List<Pedido> PedidoCLIGet(Integer iduser, Integer idprod) {
        List<Pedido> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM pedidos as p")
                .append(" INNER JOIN pedidosdetalle as pd on p.idpedido = pd.idpedido")
                .append(" INNER JOIN productos as pro ON pro.idproducto = pd.idproducto")
                .append(" WHERE p.idpedido = ? and p.idusuario = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, idprod);
            ps.setInt(2, iduser);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                Producto pro = new Producto();
                pro.setNombre(rs.getString(11));
                pro.setPrecio(rs.getDouble(13));
                
                Detalle det = new Detalle();
                det.setCantidad(rs.getInt(6));
                det.setSubtotal(rs.getDouble(9));
                det.setPro(pro);
                
                pedido.setDet(det);
                list.add(pedido);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;   
    }

    @Override
    public List<Pedido> MisPedidoSel(Integer iduser) {
        List<Pedido> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM pedidos as p")
                .append(" INNER JOIN usuarios as u ")
                .append("on p.idusuario = u.idusuario ")
                .append("INNER JOIN delivery as d on p.iddelivery = d.iddelivery")
                .append(" INNER JOIN motorizados as mot on mot.idmotorizado = d.idmotorizado")
                .append(" WHERE p.idusuario = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, iduser);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                Usuario usuario = new Usuario(rs.getInt(3));
                
                Delivery delivery = new Delivery(rs.getInt(4));
                delivery.setFechaenvio(rs.getString(15));
                delivery.setFechallegada(rs.getString(16));
                delivery.setDestino(rs.getString(17));
                
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(19));
                motorizado.setNombre(rs.getString(20));

                usuario.setNombre(rs.getString(8));
                delivery.setMot(motorizado);
                
                pedido.setId(rs.getInt(1));
                pedido.setFecha(rs.getString(2));
                pedido.setDel(delivery);
                pedido.setUsu(usuario);

                list.add(pedido);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    @Override
    public String PedidoNuevoEstado(Integer idPedido, String estadoActual) {
        return null;
    }
    
}
