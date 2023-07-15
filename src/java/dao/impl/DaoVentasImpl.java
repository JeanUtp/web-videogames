package dao.impl;

import dao.DaoVentas;
import model.Delivery;
import model.Detalle;
import model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import util.ConectaBD;

/**
 * Esta clase contiene lo que es la conexion a la base de datos de nuestra tabla
 * Pedidos,Delivery y PedidosDetalle e igualmente los metodos para extraer los
 * datos de la db.
 *
 *
 * se implementa a DaoVentas con sus metodos abtractos e igualmente su
 * importacion del archivo
 */
public class DaoVentasImpl implements DaoVentas {

    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;

    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoVentasImpl() {
        this.conectaDb = new ConectaBD();
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla Delivery,Pedidos y
     * PedidosDetalle. Además, de la actualización del Stock de los productos
     *
     * @param pedido objeto de la clase Pedido
     * @return mensaje referente si en caso no se insertaron
     */
    @Override
    public String ventasIns(Pedido pedido) {
        List<Detalle> detalles = new ArrayList<>();
        detalles = pedido.getDetalles();
        Integer iduser = pedido.getUsu().getId();
        Delivery deli = pedido.getDel();

        StringBuilder sqlDel = new StringBuilder();
        sqlDel.append("INSERT INTO delivery (")
                .append("destino,")
                .append("idmotorizado,")
                .append("fechaenvio,")
                .append("fechallegada")
                .append(") VALUES (?,?,current_timestamp,current_timestamp)");
        StringBuilder sqlDet = new StringBuilder();
        sqlDet.append("INSERT INTO pedidosdetalle (")
                .append("idproducto,")
                .append("idpedido,")
                .append("cantidad,")
                .append("subtotal")
                .append(") VALUES (?,?,?,?)");
        StringBuilder sqlPed = new StringBuilder();
        sqlPed.append("INSERT INTO pedidos (")
                .append("idusuario,")
                .append("iddelivery,")
                .append("fechapedido,")
                .append("estado")
                .append(") VALUES (?,?,current_timestamp,?)");
        StringBuilder sqlProdGet = new StringBuilder();
        sqlProdGet.append("SELECT ")
                .append("stock,")
                .append("preciounidad")
                .append(" FROM productos WHERE idproducto = ?");
        StringBuilder sqlProdUpd = new StringBuilder();
        sqlProdUpd.append("UPDATE productos SET ")
                .append("stock = ?")
                .append(" WHERE idproducto = ?");
        try (Connection cn = conectaDb.getConnection();
                PreparedStatement psPed = cn.prepareStatement(sqlPed.toString(),
                        PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement psDet = cn.prepareStatement(sqlDet.toString());
                PreparedStatement psDel = cn.prepareStatement(sqlDel.toString(),
                        PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement psProdGet = cn.prepareStatement(sqlProdGet.toString());
                PreparedStatement psProdUpd = cn.prepareStatement(sqlProdUpd.toString());) {

            //cn.setAutoCommit(false);
            boolean ok = true;
            Integer idPedido = null;
            Integer idDelivery = null;

            Random x = new Random();
            //int pos = x.nextInt(5 - 1 + 1) + 1;
            int pos = 1;
            psDel.setString(1, deli.getDestino());
            psDel.setInt(2, pos);
            psDel.executeUpdate();

            try (ResultSet rs = psDel.getGeneratedKeys()) {
                if (rs.next()) {
                    idDelivery = rs.getInt(1);
                } else {
                    mensaje = "No se pudo crear el Delivery: "
                            + sqlDel.toString();
                    ok = false;
                }
            } catch (SQLException e) {
                mensaje = e.getMessage() + sqlDel.toString();
                ok = false;
            }

            psPed.setInt(1, iduser);
            psPed.setInt(2, idDelivery);
            psPed.setString(3, "Pendiente");
            psPed.executeUpdate();

            try (ResultSet rs = psPed.getGeneratedKeys()) {
                if (rs.next()) {
                    idPedido = rs.getInt(1);
                } else {
                    mensaje = "No se pudo crear el Pedido: "
                            + sqlPed.toString();
                    ok = false;
                }
            } catch (SQLException e) {
                mensaje = e.getMessage() + sqlPed.toString();
                ok = false;
            }

            for (int i = 0; i < detalles.size(); i++) {
                Detalle det = (Detalle) detalles.get(i);

                psProdGet.setInt(1, det.getPro().getId());
                Integer stock = null;
                Double precio = null;
                Double subtotal = null;

                try (ResultSet rs = psProdGet.executeQuery()) {
                    if (rs.next()) {
                        stock = rs.getInt(1);
                        precio = rs.getDouble(2);
                        subtotal = det.getCantidad() * precio;
                    }
                } catch (SQLException e) {
                    mensaje = e.getMessage() + ": "
                            + sqlProdGet.toString();
                    ok = false;
                    break;
                }

                psDet.setInt(1, det.getPro().getId());
                psDet.setInt(2, idPedido);
                psDet.setInt(3, det.getCantidad());
                psDet.setDouble(4, subtotal);

                int ctos = psDet.executeUpdate();
                if (ctos == 0) {
                    mensaje = "No se pudo insertar detalle"
                            + sqlDet.toString();
                    ok = false;
                    break;
                }
                 this.preferenciaIns(iduser,det.getPro().getId(),0);
                if (stock != null) {
                    stock -= det.getCantidad();
                    psProdUpd.setInt(1, stock);
                    psProdUpd.setInt(2, det.getPro().getId());
                    ctos = psProdUpd.executeUpdate();
                    if (ctos == 0) {
                        mensaje = "No se pudo actualizar stock "
                                + sqlProdUpd.toString();
                        ok = false;
                        break;
                    }
                }
                
               
            }

            if (ok) {
                cn.commit();
            } else {
                mensaje = "ERROR";
                cn.rollback();
            }

            cn.setAutoCommit(true);

        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }
     
    @Override
    public void preferenciaIns(Integer idUsuario, Integer idProducto, Integer valoracion){
        StringBuilder sqlDel = new StringBuilder();
        sqlDel.append("INSERT INTO preferencias (")
                .append("idusuario,")
                .append("idproducto,")
                .append("valoracion")
                .append(") VALUES (?,?,?)");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sqlDel.toString());
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            ps.setInt(3, valoracion);
            
            int ctos = ps.executeUpdate();
        }catch (Exception e) {
            mensaje = e.getMessage();
        }
    }

    /**
     * Metodo para el obtener un mensaje de error
     *
     * @return mensaje referente si no se ejecuta la base de datos
     */
    @Override
    public String getMensaje() {
        return mensaje;
    }

}
