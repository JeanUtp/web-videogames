package dao.impl;

import dao.DaoUsuario;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBD;
/**
 * Esta clase contiene lo que es la conexion a la base de datos de nuestra tabla
 * Usuario e igualmente los metodos para extraer los datos de la db.
 *
 *
 * se implementa a DaoUsuario con sus metodos abtractos e igualmente su
 * importacion del archivo
 */
public class DaoUsuarioImpl implements DaoUsuario {
    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;
    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoUsuarioImpl() {
        this.conectaDb = new ConectaBD();
    }

    /**
     * Metodo para listar la tabla Usuarios
     * @return list, lista de usuarios
     * 
     * Uso de un objeto PreparedStatement para eliminar todas las inyecciones sql
     * Creacion de un ResultSet para la coleccion de resultados
     * Inicializacion de un list como una coleccion
     */
    @Override
    public List<Usuario> UsuarioSel() {
        List<Usuario> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM usuarios");
        try (Connection cn = conectaDb.getConnection()) {

            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
               
                usuario.setId(rs.getInt(1));
                usuario.setDni(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setNombre(rs.getString(4));
                usuario.setNumero(rs.getString(5));
                usuario.setDireccion(rs.getString(6));
                usuario.setTipo(rs.getString(7));
                usuario.setCorreo(rs.getString(8));
                usuario.setClave(rs.getString(9));

                list.add(usuario);
            }

        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla Usuarios
     * @param usu objeto de la clase Usuario
     * @return mensaje referente si en caso no se insertaron 
     */
    @Override
    public String UsuarioIns(Usuario usu) {
        StringBuilder sql = new StringBuilder();
        mensaje = "";
        sql.append("INSERT INTO usuarios( ")
                .append("dniusuario,")
                .append("apellidousuario,")
                .append("nombreusuario, ")
                .append("numerocelularusuario,")
                .append("direccionusuario,")
                .append("tipo,")
                .append("correousuario,")
                .append("password")
                .append(") VALUES (?,?,?,?,?,?,?,AES_ENCRYPT(?,?)) ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, usu.getDni());
            ps.setString(2, usu.getApellido());
            ps.setString(3, usu.getNombre());
            ps.setString(4, usu.getNumero());
            ps.setString(5, usu.getDireccion());
            ps.setString(6, usu.getTipo());
            ps.setString(7, usu.getCorreo());
            ps.setString(8, usu.getClave());
            ps.setString(9, usu.getClave());

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
     * Metodo para actualizar una fila de la tabla Usuarios
     * @param usu objeto de la clase Usuario
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String UsuarioUpd(Usuario usu) {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE usuarios SET ")
                .append("dniusuario = ?,")
                .append("apellidousuario= ?,")
                .append("nombreusuario = ?,")
                .append("numerocelularusuario = ?,")
                .append("direccionusuario = ?,")
                .append("correousuario = ?,")
                .append("tipo = ? ")
                .append("WHERE idusuario = ? ");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, usu.getDni());
            ps.setString(2, usu.getApellido());
            ps.setString(3, usu.getNombre());
            ps.setString(4, usu.getNumero());
            ps.setString(5, usu.getDireccion());
            ps.setString(6, usu.getCorreo());
            ps.setString(7, usu.getTipo());
            ps.setInt(8, usu.getId());

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
     * Metodo para eliminar 1 o más filas de la tabla usuarios
     * @param ids ,lista de números tipo Integer 
     * @return mensaje referente si no se eliminaron las filas
     */
    @Override
    public String UsuarioDel(List<Integer> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM usuarios WHERE ")
                .append("idusuario = ? ");
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
     * Metodo para Logear al Usuario
     * @param usu objeto de la clase Usuario
     * @return usuario, objeto de la clase Usuario
     */
    @Override
    public Usuario UsuarioLogin(Usuario usu) {
        Usuario usuario = new Usuario();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT")
                .append(" * FROM usuarios ")
                .append("WHERE UPPER(correousuario) = ? AND ")
                .append("password = AES_ENCRYPT(?,?)");        
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, usu.getCorreo().toUpperCase());
            ps.setString(2, usu.getClave());
            ps.setString(3, usu.getClave());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setDni(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setNombre(rs.getString(4));
                usuario.setNumero(rs.getString(5));
                usuario.setDireccion(rs.getString(6));
                usuario.setTipo(rs.getString(7));
                usuario.setCorreo(rs.getString(8));
                
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return usuario;
    }

    /**
     * Metodo para obtener los datos de una fila en la tabla Usuarios
     * @param id,Id de una fila de la tabla Usuarios
     * @return usuario, objeto de la clase Usuario
     */
    @Override
    public Usuario UsuarioGet(Integer id) {
        Usuario usuario = new Usuario();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM usuarios")
                .append(" WHERE idusuario = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setDni(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setNombre(rs.getString(4));
                usuario.setNumero(rs.getString(5));
                usuario.setDireccion(rs.getString(6));
                usuario.setTipo(rs.getString(7));
                usuario.setCorreo(rs.getString(8));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return usuario;
    }

    /**
     * Metodo para validar si el DNI ya esta registrado en la BD
     * @param usu objeto de la clase Usuario
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarDni(Usuario usu, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idusuario,dniusuario")
                .append(" FROM usuarios WHERE dniusuario = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, usu.getDni());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setDni(rs.getString(2));

                //upd
                if (ind == true) {
                    if (usu.getId().equals(usuario.getId())) {
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
     * @param usu objeto de la clase Usuario
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarCor(Usuario usu, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idusuario,correousuario")
                .append(" FROM usuarios WHERE correousuario = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, usu.getCorreo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setCorreo(rs.getString(2));

                //upd
                if (ind == true) {
                    if (usu.getId().equals(usuario.getId())) {
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
