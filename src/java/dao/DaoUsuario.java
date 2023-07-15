package dao;

import model.Usuario;
import java.util.List;

public interface DaoUsuario {

    Usuario UsuarioLogin(Usuario usu);

    List<Usuario> UsuarioSel();

    Usuario UsuarioGet(Integer id);

    String UsuarioIns(Usuario usu);

    String UsuarioUpd(Usuario usu);

    String UsuarioDel(List<Integer> ids);

    String getMensaje();

    Integer validarDni(Usuario usu, boolean ind);
    Integer validarCor(Usuario usu, boolean ind);
}
