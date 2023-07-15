package dao;

import model.Categoria;
import java.util.List;

public interface DaoCategoria {

    List<Categoria> CategoriaSel();

    Categoria CategoriaGet(Integer id);

    String CategoriaIns(Categoria cat);

    String CategoriaUpd(Categoria cat);

    String CategoriaDel(List<Integer> ids);

    public List<Object[]> categoriasCbo();

    String getMensaje();

}
