package dao;

import model.Motorizado;
import java.util.List;

public interface DaoMotorizado {
    List<Motorizado> MotorizadoSel();

    Motorizado MotorizadoGet(Integer id);

    String MotorizadoIns(Motorizado mot);

    String MotorizadoUpd(Motorizado mot);

    String MotorizadoDel(List<Integer> ids);

    public List<Object[]> motorizadosCbo();

    String getMensaje();
    
    Integer validarDni(Motorizado mot, boolean ind);
    Integer validarMat(Motorizado mot, boolean ind);
}
