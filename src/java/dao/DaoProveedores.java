
package dao;

import model.Proveedor;
import java.util.List;

public interface DaoProveedores {
     public List<Proveedor> proveedoresSel();
     public Proveedor proveedoresGet(Integer id);

    public String proveedoresIns(Proveedor inquilino);

    public String proveedoresDel(List<Integer> ids);

    public String proveedoresUpd(Proveedor inquilino);

    public String getMessage();
}
