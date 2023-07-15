package dao.impl;

import dao.DaoCategoria;
import model.Categoria;
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
 * se implementa a DaoCategoria con sus metodos abtractos e igualmente su
 * importacion del archivo
 */
public class DaoCategoriaImpl implements DaoCategoria {

    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;

    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoCategoriaImpl() {
        this.conectaDb = new ConectaBD();
    }

    /**
     * Metodo para listar la tabla categoria
     *
     * @return list, lista de categorias
     *
     * Uso de un objeto PreparedStatement para eliminar todas las inyecciones
     * sql Creacion de un ResultSet para la coleccion de resultados
     * Inicializacion de un list como una coleccion
     */
    @Override
    public List<Categoria> CategoriaSel() {
        List<Categoria> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM categorias");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt(1));
                categoria.setNombre(rs.getString(2));
                categoria.setDescripcion(rs.getString(3));

                list.add(categoria);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para el obtener un mensaje de error
     *
     * @return mensaje
     */
    @Override
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Metodo para listar el ID y el nombre de cada categoria
     *
     * @return list, lista de objetos Categoria
     */
    @Override
    public List<Object[]> categoriasCbo() {
        List<Object[]> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idcategoria,")
                .append("nombre")
                .append(" FROM categorias");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Object[] cat = new Object[2];
                cat[0] = rs.getInt(1);
                cat[1] = rs.getString(2);
                list.add(cat);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para obtener los datos de una fila en la tabla Categorias
     *
     * @param id,Id de una fila de la tabla Categoria
     * @return categoria, objeto de la clase Categoria
     */
    @Override
    public Categoria CategoriaGet(Integer id) {
        Categoria categoria = new Categoria();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM categorias")
                .append(" WHERE idcategoria = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoria.setId(rs.getInt(1));
                categoria.setNombre(rs.getString(2));
                categoria.setDescripcion(rs.getString(3));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return categoria;
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla categoria
     *
     * @param cat objeto de la clase Categoria
     * @return mensaje referente si en caso no se insertaron
     */
    @Override
    public String CategoriaIns(Categoria cat) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO categorias( ")
                .append("nombre,")
                .append("descripcion")
                .append(") VALUES (?,?) ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());

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
     * Metodo para actualizar una fila de la tabla categorias
     *
     * @param cat objeto de la clase Categoria
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String CategoriaUpd(Categoria cat) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE categorias SET ")
                .append("nombre = ?,")
                .append("descripcion = ?")
                .append(" WHERE idcategoria = ?");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            ps.setInt(3, cat.getId());

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
     * Metodo para eliminar 1 o más filas de la tabla Categorias
     * @param ids ,lista de números tipo Integer
     * @return mensaje referente si no se eliminaron las filas
     */
    @Override
    public String CategoriaDel(List<Integer> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM categorias WHERE ")
                .append("idcategoria = ?");
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

}
