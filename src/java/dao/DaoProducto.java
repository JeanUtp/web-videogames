package dao;

import model.Producto;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import model.Filtro;

public interface DaoProducto {

    List<Producto> ProductoSel();
                
    List<Producto> ProductoSelFiltro(Filtro filtro);

    Producto ProductoGet(Integer id);

    String ProductoIns(Producto pro);

    String ProductoUpd(Producto pro);

    String ProductoDel(List<Integer> ids);
    
    //CBO
    public List<Object[]> catCbo();
    public List<Object[]> proCbo();

    String getMensaje();

    String listarImg(Integer id, HttpServletResponse response);

    String ProductoUpdIMG(Producto pro);
}
