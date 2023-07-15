package dao.impl;

import dao.DaoProveedor;
import model.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBD;
/**
 * Esta clase contiene lo que es la conexion a la base de datos de nuestra tabla
 * categoria e igualmente los metodos para extraer los datos de la db.
 *
 *
 * se implementa a DaoProveedor con sus metodos abtractos e igualmente su
 * importacion del archivo
 */
public class DaoProveedorImpl implements DaoProveedor {

    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;

    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoProveedorImpl() {
        this.conectaDb = new ConectaBD();
    }
    /**
     * Metodo para listar la tabla Proveedores
     * @return list, lista de proveedores
     * 
     * Uso de un objeto PreparedStatement para eliminar todas las inyecciones sql
     * Creacion de un ResultSet para la coleccion de resultados
     * Inicializacion de un list como una coleccion
     */
    @Override
    public List<Proveedor> ProveedorSel() {
        List<Proveedor> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM proveedores");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setRuc(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setNumero(rs.getString(4));
                proveedor.setDireccion(rs.getString(5));
                proveedor.setCorreo(rs.getString(6));

                list.add(proveedor);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para el obtener un mensaje de error
     * @return mensaje referente si no se ejecuta la base de datos
     */
    @Override
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Metodo para listar el ID y el nombre de cada Proveedor
     * @return list, lista de objetos Proveedor
     */
    @Override
    public List<Object[]> proveedorCbo() {
        List<Object[]> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idproveedor,")
                .append("nombreproveedor")
                .append(" FROM proveedores");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Object[] pro = new Object[2];
                pro[0] = rs.getInt(1);
                pro[1] = rs.getString(2);
                list.add(pro);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para obtener los datos de una fila en la tabla Proveedor
     * @param id,Id de una fila de la tabla Proveedor
     * @return provedor, objeto de la clase Proveedor
     */
    @Override
    public Proveedor ProveedorGet(Integer id) {
        Proveedor proveedor = new Proveedor();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM proveedores")
                .append(" WHERE idproveedor = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.setId(id);
                proveedor.setRuc(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setNumero(rs.getString(4));
                proveedor.setDireccion(rs.getString(5));
                proveedor.setCorreo(rs.getString(6));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return proveedor;
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla Proveedores
     * @param pro objeto de la clase Proveedor
     * @return mensaje referente si en caso no se insertaron 
     */
    @Override
    public String ProveedorIns(Proveedor pro) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO proveedores( ")
                .append("rucproveedor,")
                .append("nombreproveedor,")
                .append("numerocelularproveedor,")
                .append("direccionproveedor,")
                .append("correoproveedor")
                .append(") VALUES (?,?,?,?,?) ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getRuc());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getNumero());
            ps.setString(4, pro.getDireccion());
            ps.setString(5, pro.getCorreo());

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                mensaje = "Cero filas insertadas";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    /**
     * Metodo para actualizar una fila de la tabla proveedores
     * @param pro objeto de la clase Proveedor
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String ProveedorUpd(Proveedor pro) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE proveedores SET ")
                .append("rucproveedor = ?,")
                .append("nombreproveedor = ?,")
                .append("numerocelularproveedor = ?,")
                .append("direccionproveedor = ?,")
                .append("correoproveedor= ?")
                .append(" WHERE idproveedor = ?");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getRuc());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getNumero());
            ps.setString(4, pro.getDireccion());
            ps.setString(5, pro.getCorreo());
            ps.setInt(6, pro.getId());

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                mensaje = "Cero filas actualizadas";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    /**
     * Metodo para eliminar 1 o más filas de la tabla proveedores
     * @param ids ,lista de números tipo Integer 
     * @return mensaje referente si no se eliminaron las filas
     */
    @Override
    public String ProveedorDel(List<Integer> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM proveedores WHERE ")
                .append("idproveedor = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            cn.setAutoCommit(false);
            boolean ok = true;
            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(1, ids.get(i));
                int ctos = ps.executeUpdate();
                if (ctos == 0) {
                    ok = false;
                    mensaje = "Cero filas actualizadas";
                }
                if (ok) {
                    cn.commit();
                } else {
                    cn.rollback();
                }
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    /**
     * Metodo para validar si el RUC ya esta registrado en la BD
     * @param pro objeto de la clase Proveedor
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarRuc(Proveedor pro, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idproveedor,rucproveedor")
                .append(" FROM proveedores WHERE rucproveedor = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getRuc());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setRuc(rs.getString(2));

                //upd
                if (ind == true) {
                    if (pro.getId().equals(proveedor.getId())) {
                        v = 1; // 1 = correcto | null = repetición
                    } else {
                        v = null;
                        break;
                    }
                } else {
                    v = null;
                    break;
                }

            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return v;
    }

    /**
     * Metodo para validar si el Correo ya esta registrado en la BD
     * @param pro objeto de la clase Proveedor
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarCor(Proveedor pro, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idproveedor,correoproveedor")
                .append(" FROM proveedores WHERE correoproveedor = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getCorreo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setCorreo(rs.getString(2));

                //upd
                if (ind == true) {
                    if (pro.getId().equals(proveedor.getId())) {
                        v = 1; // 1 = correcto | null = repetición
                    } else {
                        v = null;
                        break;
                    }
                } else {
                    v = null;
                    break;
                }

            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return v;
    }

}
