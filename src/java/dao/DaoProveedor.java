package dao;

import model.Proveedor;
import java.util.List;

public interface DaoProveedor {

    List<Proveedor> ProveedorSel();

    Proveedor ProveedorGet(Integer id);

    String ProveedorIns(Proveedor pro);

    String ProveedorUpd(Proveedor pro);

    String ProveedorDel(List<Integer> ids);

    public List<Object[]> proveedorCbo();

    String getMensaje();
    
    Integer validarRuc(Proveedor pro, boolean ind);
    Integer validarCor(Proveedor pro, boolean ind);

}
