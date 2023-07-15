package test;

import dao.DaoProducto;
import dao.impl.DaoProductoImpl;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Producto;
import model.Proveedor;

public class testProducto {
    public static void main(String[] args) {
        DaoProducto dao = new DaoProductoImpl();
        
        /*
        *INSERTANDO DATOS
        */
       System.out.println("INSERTANDO");
        Producto p1 = new Producto();
        Categoria cat = new Categoria(1);
        Proveedor pro = new Proveedor(1);
       
        p1.setNombre("Ajinomen");
        p1.setDescripcion("Sopita");
        p1.setPrecio(2.00);
        p1.setStock(20);
        p1.setFoto(null);
        p1.setCat(cat);
        p1.setPro(pro);
        
        try {
            String msg = dao.ProductoIns(p1);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        /*
        *ACTUALIZAR DATOS
        */
       System.out.println("ACTUALIZAR");
        Producto p2 = new Producto();
  
        p2.setId(73);
        p2.setNombre("SopitaSeca");
        p2.setDescripcion("Sopita");
        p2.setPrecio(4.00);
        p2.setStock(10);
        p2.setFoto(null);
        p2.setCat(cat);
        p2.setPro(pro);
        
        try {
            String msg = dao.ProductoUpd(p2);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        /*
        *LISTANDO DATOS
        */
        System.out.println("LISTADO");
        try {
            List<Producto> lista = dao.ProductoSel();
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
            Producto i = dao.ProductoGet(1);
            System.out.println(i.getNombre());
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
        
        /*
         *ELIMINAR DATOS
         */
        System.out.println("ELIMINAR");
        List<Integer> lis = new ArrayList<>();
        lis.add(74);
        
        try {
            String msg = dao.ProductoDel(lis);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
    }
}
