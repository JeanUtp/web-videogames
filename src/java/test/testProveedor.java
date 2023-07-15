package test;

import dao.DaoProveedor;
import dao.impl.DaoProveedorImpl;
import java.util.ArrayList;
import java.util.List;
import model.Proveedor;

public class testProveedor {

    public static void main(String[] args) {
        DaoProveedor dao = new DaoProveedorImpl();

        /*
         *INSERTANDO DATOS
         */
        System.out.println("INSERTANDO");
        Proveedor p1 = new Proveedor();

        p1.setNombre("FundacionCALMA");
        p1.setCorreo("Calma@hotmail.com");
        p1.setNumero("123456783");
        p1.setRuc("82732662321");
        p1.setDireccion("LIMAA");

        try {
            String msg = dao.ProveedorIns(p1);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *ACTUALIZAR DATOS
         */
        System.out.println("ACTUALIZAR");
        Proveedor p2 = new Proveedor();

        p2.setId(13);
        p2.setNombre("Softtek");
        p2.setCorreo("Softtek12@Softtek.com");
        p2.setNumero("12345656");
        p2.setRuc("9999992321");
        p2.setDireccion("MEXICO");

        try {
            String msg = dao.ProveedorUpd(p2);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *LISTANDO DATOS
         */
        System.out.println("LISTADO");
        try {
            List<Proveedor> lista = dao.ProveedorSel();
            lista.forEach(t -> {
                System.out.println(t.getId() + " - " + t.getNombre() + " - " + t.getRuc());
            });
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *OBTENER UNA DATOS DE UNA TABLA
         */
        System.out.println("INDIVIDUAL");
        try {
            Proveedor i = dao.ProveedorGet(1);
            System.out.println(i.getNombre());
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

        /*
         *ELIMINAR DATOS
         */
        System.out.println("ELIMINAR");
        List<Integer> lis = new ArrayList<>();
        lis.add(17);
        lis.add(18);
        
        try {
            String msg = dao.ProveedorDel(lis);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }

    }
}
