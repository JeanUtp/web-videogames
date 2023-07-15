package test;

import dao.DaoCategoria;
import dao.impl.DaoCategoriaImpl;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;

public class testCategoria {
    public static void main(String[] args) {
        DaoCategoria dao = new DaoCategoriaImpl();
        
        /*
        *INSERTANDO DATOS
        */
        System.out.println("INSERTANDO");
        Categoria c1 = new Categoria();
       
        c1.setNombre("SOPITAS");
        c1.setDescripcion("Enlatados");
       
        try {
            String msg = dao.CategoriaIns(c1);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        /*
        *ACTUALIZAR DATOS
        */
        System.out.println("ACTUALIZAR");
        Categoria c2 = new Categoria();
       
        c2.setId(10);
        c2.setNombre("SOPITAS 2");
        c2.setDescripcion("Enlatados 2");
       
        try {
            String msg = dao.CategoriaUpd(c2);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        /*
        *LISTANDO DATOS
        */
        System.out.println("LISTADO");
        try {
            List<Categoria> lista = dao.CategoriaSel();
            lista.forEach(t -> {
                System.out.println(t.getId()+" - "+t.getNombre()+ " - "+t.getDescripcion());
            });
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }

        /*
        *OBTENER UNA DATOS DE UNA TABLA
        */
        System.out.println("INDIVIDUAL");
        try {
            Categoria i = dao.CategoriaGet(1);
            System.out.println(i.getNombre());
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
        /*
         *ELIMINAR DATOS
         */
        System.out.println("ELIMINAR");
        List<Integer> lis = new ArrayList<>();
        lis.add(11);

        try {
            String msg = dao.CategoriaDel(lis);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
    }
}
