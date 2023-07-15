package test;

import dao.DaoUsuario;
import dao.impl.DaoUsuarioImpl;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class testUsuario {

    public static void main(String[] args) {
        DaoUsuario dao = new DaoUsuarioImpl();

        /*
         *INSERTANDO DATOS
         */
        System.out.println("INSERTANDO");
        Usuario u1 = new Usuario();
        u1.setNombre("Jean");
        u1.setApellido("Matos");
        u1.setCorreo("Jean31@gmail.com");
        u1.setClave("123456");
        u1.setTipo("ADMIN");
        u1.setDni("7739933");
        u1.setNumero("923384343");
        u1.setDireccion("PuentePiedra");

        try {
            String msg = dao.UsuarioIns(u1);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *ACTUALIZAR DATOS
         */
        System.out.println("ACTUALIZAR");
        Usuario u2 = new Usuario();
        u2.setId(7);
        u2.setNombre("PEPE");
        u2.setApellido("LUCHO");
        u2.setCorreo("PEPE@gmail.com");
        u2.setClave("123456");
        u2.setTipo("ADMIN");
        u2.setDni("7739973");
        u2.setNumero("96384343");
        u2.setDireccion("PuentePiedra");

        try {
            String msg = dao.UsuarioUpd(u2);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *LISTANDO DATOS
         */
        System.out.println("LISTADO");
        try {
            List<Usuario> lista = dao.UsuarioSel();
            lista.forEach(t -> {
                System.out.println(t.getId() + " - " + t.getNombre() + " - " + t.getApellido());
            });
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *OBTENER UNA DATOS DE UNA TABLA
         */
        System.out.println("INDIVIDUAL");
        try {
            Usuario i = dao.UsuarioGet(1);
            System.out.println(i.getNombre());
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *ELIMINAR DATOS
         */
        System.out.println("ELIMINAR");
        List<Integer> lis = new ArrayList<>();
        lis.add(19);
        lis.add(20);

        try {
            String msg = dao.UsuarioDel(lis);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *LOGUEAR USUARIO
         */
        System.out.println("LOGUEAR");
        Usuario user = new Usuario();
        user.setCorreo("u18215055@mail.com");
        user.setClave("1234565");
        try {
            Usuario usuario = dao.UsuarioLogin(user);
 
            System.out.println("ID del usuario: " +usuario.getId());
            System.out.println("Nombre del usuario: " +usuario.getNombre());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
