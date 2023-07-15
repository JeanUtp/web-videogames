package dao.impl;

import dao.DaoProducto;
import model.Categoria;
import model.Producto;
import model.Proveedor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import model.Filtro;
import util.ConectaBD;
/**
 * Esta clase contiene lo que es la conexion a la base de datos de nuestra tabla
 * producto e igualmente los metodos para extraer los datos de la db.
 *
 *
 * se implementa a DaoProducto con sus metodos abtractos e igualmente su
 * importacion del archivo
 */
public class DaoProductoImpl implements DaoProducto {
    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;
    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoProductoImpl() {
        this.conectaDb = new ConectaBD();
    }

    /**
     * Metodo para listar la tabla Productos
     * @return list, lista de productos
     * 
     * Uso de un objeto PreparedStatement para eliminar todas las inyecciones sql
     * Creacion de un ResultSet para la coleccion de resultados
     * Inicializacion de un list como una coleccion
     */
    @Override
    public List<Producto> ProductoSel() {
        List<Producto> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM productos as p")
                .append(" INNER JOIN ")
                .append("categorias as c ")
                .append(" on c.idcategoria = p.idcategoria ")
                .append("INNER JOIN proveedores as pr on ")
                .append("pr.idproveedor = p.idproveedor order by idproducto ASC");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt(7),rs.getString(10),rs.getString(11));
                Proveedor proveedor = new Proveedor(rs.getInt(8),rs.getString(14));
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setDescripcion(rs.getString(3));
                producto.setPrecio(rs.getDouble(4));
                producto.setStock(rs.getInt(5));
                producto.setFoto(rs.getAsciiStream(6));
                producto.setCat(categoria);
                producto.setPro(proveedor);

                list.add(producto);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para obtener los datos de una fila en la tabla Productos
     * @param id,Id de una fila de la tabla Productos
     * @return producto, objeto de la clase Producto
     */
    @Override
    public Producto ProductoGet(Integer id) {
        Producto producto = new Producto();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM productos as p")
                .append(" INNER JOIN ")
                .append("categorias as c ")
                .append(" on c.idcategoria = p.idcategoria ")
                .append("INNER JOIN proveedores as pr on ")
                .append("pr.idproveedor = p.idproveedor ")
                .append("WHERE idproducto = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt(7),rs.getString(10),rs.getString(11));
                Proveedor proveedor = new Proveedor(rs.getInt(8),rs.getString(14));
                
                producto.setId(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setDescripcion(rs.getString(3));
                producto.setPrecio(rs.getDouble(4));
                producto.setStock(rs.getInt(5));
                producto.setFoto(rs.getAsciiStream(6));
                producto.setCat(categoria);
                producto.setPro(proveedor);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return producto;
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla Productos
     * @param pro objeto de la clase Producto
     * @return mensaje referente si en caso no se insertaron 
     */
    @Override
    public String ProductoIns(Producto pro) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO productos( ")
                .append("nombreproducto,")
                .append("descripcionproducto,")
                .append("preciounidad,")
                .append("stock,")
                .append("foto,")
                .append("idcategoria,")
                .append("idproveedor")
                .append(") VALUES (?,?,?,?,?,?,?) ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getNombre());
            ps.setString(2,pro.getDescripcion());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4,pro.getStock());
            ps.setAsciiStream(5, pro.getFoto());
            ps.setInt(6, pro.getCat().getId());
            ps.setInt(7, pro.getPro().getId());
        
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
     * Metodo para actualizar una fila de la tabla productos
     * @param pro objeto de la clase Producto
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String ProductoUpd(Producto pro) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE productos SET ")
                .append("nombreproducto = ?,")
                .append("descripcionproducto = ?,")
                .append("preciounidad = ?,")
                .append("stock = ?,")
                .append("idcategoria = ?,")
                .append("idproveedor = ?")
                .append(" WHERE idproducto = ?");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getDescripcion());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getStock());
            ps.setInt(5, pro.getCat().getId());
            ps.setInt(6, pro.getPro().getId());
            ps.setInt(7, pro.getId());
                      
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
     * Metodo para eliminar 1 o más filas de la tabla productos
     * @param ids ,lista de números tipo Integer 
     * @return mensaje referente si no se eliminaron las filas
     */
    @Override
    public String ProductoDel(List<Integer> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM productos WHERE ")
                .append("idproducto = ?");
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
     * Metodo para el obtener un mensaje de error
     * @return mensaje referente si no se ejecuta la base de datos
     */
    @Override
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Metodo para mostrar la imagén de un producto
     * @param id, ID del producto
     * @param response,objeto para peticiones HTTP
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String listarImg(Integer id, HttpServletResponse response) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM productos WHERE ")
                .append("idproducto = ?");
        
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*");
        
        try(Connection cn = conectaDb.getConnection()){  
            outputStream = response.getOutputStream();
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inputStream = rs.getBinaryStream("foto");
            }
            bufferedInputStream = new  BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            
            int i=0;
            while((i=bufferedInputStream.read())!=-1){
                bufferedOutputStream.write(i);
            }
        
        }catch(Exception e){
            mensaje = e.getMessage();
        }
        
        return mensaje;
    }

    /**
     * Metodo para actualizar la imagén de un producto
     * @param pro, objeto de la clase Producto
     * @return mensaje referente si en caso no se actualizó la imagén
     */
    @Override
    public String ProductoUpdIMG(Producto pro) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE productos SET ")
                .append("nombreproducto = ?,")
                .append("descripcionproducto = ?,")
                .append("preciounidad = ?,")
                .append("stock = ?,")
                .append("foto = ?,")
                .append("idcategoria = ?,")
                .append("idproveedor = ?")
                .append(" WHERE idproducto = ?");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getDescripcion());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getStock());
            ps.setAsciiStream(5, pro.getFoto());
            ps.setInt(6, pro.getCat().getId());
            ps.setInt(7, pro.getPro().getId());
            ps.setInt(8, pro.getId());
                      
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
     * Metodo para listar el ID y el nombre de cada Categoria
     * @return list, lista de objetos Categoria
     */
    @Override
    public List<Object[]> catCbo() {
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
     * Metodo para listar el ID y el nombre de cada Proveedor
     * @return list, lista de objetos Proveedor
     */
    @Override
    public List<Object[]> proCbo() {
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

    @Override
    public List<Producto> ProductoSelFiltro(Filtro filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
