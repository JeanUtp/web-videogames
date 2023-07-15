package dao.impl;

import dao.DaoMotorizado;
import model.Motorizado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBD;
/**
 * Esta clase contiene lo que es la conexion a la base de datos de nuestra tabla
 * Motorizado e igualmente los metodos para extraer los datos de la db.
 * 
 * 
 * se implementa a DaoMotorizado con sus metodos abtractos e igualmente su
 * importacion del archivo
 */

public class DaoMotorizadoImpl implements DaoMotorizado {
    
    /**
     * Creacion de atributos para la conexion db y otro para un mensaje
     * referente a un error
     */
    private ConectaBD conectaDb;
    private String mensaje;
    
    /**
     * Metodo constructor en la cual inicializamos nuestro conectadb
     */
    public DaoMotorizadoImpl() {
        this.conectaDb = new ConectaBD();
    }

    /**
     * Metodo para listar la tabla Motorizados
     * @return list, lista de motorizados
     * 
     * Uso de un objeto PreparedStatement para eliminar todas las inyecciones sql
     * Creacion de un ResultSet para la coleccion de resultados
     * Inicializacion de un list como una coleccion
     */
    @Override
    public List<Motorizado> MotorizadoSel() {
        List<Motorizado> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM motorizados");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(1));
                motorizado.setNombre(rs.getString(2));
                motorizado.setApellidos(rs.getString(3));
                motorizado.setDni(rs.getString(4));
                motorizado.setMatricula(rs.getString(5));
                motorizado.setMarcamoto(rs.getString(6));
                motorizado.setColormoto(rs.getString(7));

                list.add(motorizado);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para obtener los datos de una fila en la tabla Motorizados
     * @param id,Id de la fila de la tabla Motorizado
     * @return motorizado, objeto de la clase Motorizado
     */
    @Override
    public Motorizado MotorizadoGet(Integer id) {
        Motorizado motorizado = new Motorizado();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM motorizados")
                .append(" WHERE idmotorizado = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                motorizado.setId(rs.getInt(1));
                motorizado.setNombre(rs.getString(2));
                motorizado.setApellidos(rs.getString(3));
                motorizado.setDni(rs.getString(4));
                motorizado.setMatricula(rs.getString(5));
                motorizado.setMarcamoto(rs.getString(6));
                motorizado.setColormoto(rs.getString(7));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return motorizado;
    }

    /**
     * Metodo de inserccion a la base de datos de la tabla Motorizado
     * @param mot objeto de la clase Motorizado
     * @return mensaje referente si en caso no se insertaron 
     */
    @Override
    public String MotorizadoIns(Motorizado mot) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO motorizados( ")
                .append("nombre,")
                .append("apellidos,")
                .append("dni,")
                .append("matricula,")
                .append("marcamoto,")
                .append("colormoto")
                .append(") VALUES (?,?,?,?,?,?) ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, mot.getNombre());
            ps.setString(2, mot.getApellidos());
            ps.setString(3, mot.getDni());
            ps.setString(4, mot.getMatricula());
            ps.setString(5, mot.getMarcamoto());
            ps.setString(6, mot.getColormoto());

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
     * Metodo para actualizar una fila de la tabla motorizados
     * @param mot objeto de la clase Motorizado
     * @return mensaje referente si en caso no se actualizaron los datos
     */
    @Override
    public String MotorizadoUpd(Motorizado mot) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE motorizados SET ")
                .append("nombre = ? ,")
                .append("apellidos = ? ,")
                .append("dni = ? ,")
                .append("matricula = ? ,")
                .append("marcamoto = ? ,")
                .append("colormoto = ?")
                .append(" WHERE idmotorizado = ?");

        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, mot.getNombre());
            ps.setString(2, mot.getApellidos());
            ps.setString(3, mot.getDni());
            ps.setString(4, mot.getMatricula());
            ps.setString(5, mot.getMarcamoto());
            ps.setString(6, mot.getColormoto());
            ps.setInt(7, mot.getId());

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
     * Metodo para eliminar 1 o más filas de la tabla motorizados
     * @param ids ,lista de números tipo Integer 
     * @return mensaje referente si no se eliminaron las filas
     */
    @Override
    public String MotorizadoDel(List<Integer> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM motorizados WHERE ")
                .append("idmotorizado = ?");
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
     * Metodo para listar el ID, nombre y apellidos de cada motorizado
     * @return list, lista de objetos Motorizado
     */
    @Override
    public List<Object[]> motorizadosCbo() {
        List<Object[]> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idmotorizado,")
                .append("nombre,")
                .append("apellidos")
                .append(" FROM motorizados");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                Object[] mot = new Object[3];
                mot[0] = rs.getInt(1);
                mot[1] = rs.getString(2);
                mot[2] = rs.getString(3);
                list.add(mot);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return list;
    }

    /**
     * Metodo para el obtener un mensaje de error
     * @return mensaje
     */
    @Override
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Metodo para validar si el DNI ya esta registrado en la BD
     * @param mot objeto de la clase Motorizado
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarDni(Motorizado mot, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idmotorizado,DNI")
                .append(" FROM motorizados WHERE dni = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, mot.getDni());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(1));
                motorizado.setDni(rs.getString(2));

                if (ind == true) {
                    if (mot.getId().equals(motorizado.getId())) {
                        v = 1; 
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
     * Metodo para validar si la matricula ya esta registrado en la BD
     * @param mot objeto de la clase Motorizado
     * @param ind indicador boolean para conocer si se actualiza o se inserta
     * @return v, si es null entonces quiere decir que es incorrecto la 
     * validación pero si sale 1 quiere decir que esta correcto la validación
     */
    @Override
    public Integer validarMat(Motorizado mot, boolean ind) {
        Integer v = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idmotorizado,matricula")
                .append(" FROM motorizados WHERE matricula = ? ");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, mot.getMatricula());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Motorizado motorizado = new Motorizado();
                motorizado.setId(rs.getInt(1));
                motorizado.setMatricula(rs.getString(2));

                if (ind == true) {
                    if (mot.getId().equals(motorizado.getId())) {
                        v = 1;
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
