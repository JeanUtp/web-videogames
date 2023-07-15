package test;

import dao.DaoMotorizado;
import dao.impl.DaoMotorizadoImpl;
import java.util.ArrayList;
import java.util.List;
import model.Motorizado;

public class testMotorizado {
    public static void main(String[] args) {
        DaoMotorizado dao = new DaoMotorizadoImpl();
        
        /*
        *INSERTANDO DATOS
        */
        System.out.println("INSERTANDO");
        Motorizado m1 = new Motorizado();
       
        m1.setNombre("Jean");
        m1.setApellidos("Martin");
        m1.setDni("72344499");
        m1.setMatricula("MD-202");
        m1.setMarcamoto("Onda");
        m1.setColormoto("Rojo");
       
        try {
            String msg = dao.MotorizadoIns(m1);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        /*
        *ACTUALIZAR DATOS
        */
        System.out.println("ACTUALIZAR");
        Motorizado m2 = new Motorizado();
       
        m2.setId(10);
        m2.setNombre("MANUEL");
        m2.setApellidos("Martin");
        m2.setDni("72384222");
        m2.setMatricula("MD-202");
        m2.setMarcamoto("Onda");
        m2.setColormoto("Blanco");
       
        try {
            String msg = dao.MotorizadoUpd(m2);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }
        
        /*
        *LISTANDO DATOS
        */
        System.out.println("LISTADO");
        try {
            List<Motorizado> lista = dao.MotorizadoSel();
            lista.forEach(t -> {
                System.out.println(t.getId()+" - "+t.getNombre()+ " - "+t.getMatricula());
            });
        } catch (Exception e) {
            System.out.println(dao.getMensaje()+ " - " + e.getMessage());
        }

        /*
        *OBTENER UNA DATOS DE UNA TABLA
        */
        System.out.println("INDIVIDUAL");
        try {
            Motorizado i = dao.MotorizadoGet(1);
            System.out.println(i.getNombre());
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
        
        /*
         *ELIMINAR DATOS
         */
        System.out.println("ELIMINAR");
        List<Integer> lis = new ArrayList<>();
        lis.add(12);
        
        try {
            String msg = dao.MotorizadoDel(lis);
            System.out.println("Respuesta:" + msg);
        } catch (Exception e) {
            System.out.println(dao.getMensaje() + " - " + e.getMessage());
        }
    }
}
