package services;

import dao.DaoProducto;
import dao.impl.DaoProductoImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import model.Categoria;
import model.Filtro;
import model.Producto;
import model.Proveedor;

@ManagedBean(name = "productos")
@ApplicationScoped
public class ProductosService {

    private DaoProducto dao;
    private Integer id = 0;
    private Producto data = new Producto();
    private String msg = null;
    private String base64Image;
    
    private Filtro filtro = new Filtro();
    private boolean filtroActivo;

    @PostConstruct
    public void inicializarBean() {
        dao = new DaoProductoImpl();
    }
    
    public List<Producto> productoSelClean() throws Exception {
        List<Producto> lista = null;
        filtroActivo = false;
        try {
            filtro = new Filtro();
            lista = dao.ProductoSel();
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
    
    public List<Producto> productoSel() throws Exception {
        List<Producto> lista = null;
        filtroActivo = false;
        try {
            if(filtro.getNombreProducto() != null){
                if(filtro.getNombreProducto().length() != 0){
                    filtroActivo = true;
                }else{
                    filtro.setNombreProducto(null);
                }
            }
            if(filtro.getIdCategoria()!= null){
                if(filtro.getIdCategoria() != 0){
                    filtroActivo = true;
                }else{
                    filtro.setIdCategoria(null);
                }
            }
            if(filtro.getIdProveedor()!= null){
                if(filtro.getIdProveedor() != 0){
                    filtroActivo = true;
                }else{
                    filtro.setIdProveedor(null);
                }
            }
            if(filtro.getPrecioMaximo()!= null){
                if(filtro.getPrecioMaximo() != 0){
                    filtroActivo = true;
                }else{
                    filtro.setPrecioMaximo(null);
                } 
            }
            if(filtroActivo){
                lista = dao.ProductoSelFiltro(filtro);
            }else{
                lista = dao.ProductoSel();
            }
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return lista;
    }
    
    public List<Producto> productoSel2() {
        List<Producto> lista = dao.ProductoSel();
        return lista;
    }


    public String productoGet(Integer id) throws Exception {
        base64Image = "";
        try {
            data = dao.ProductoGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return "productoUpd?faces-redirect=true";
    }

    public Producto productoGet() throws Exception {
        Producto producto = new Producto();
        try {
            producto = dao.ProductoGet(id);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return producto;
    }

    public String productoIns() throws Exception {
        msg = null;
        Categoria cat = new Categoria(data.getIdcat());
        Proveedor prov = new Proveedor(data.getIdcat());

        data.setCat(cat);
        data.setPro(prov);
        
        if(base64Image != null){
            data.setBase64Image(base64Image);
        }else{
            data.setBase64Image("");
        }

        try {
            msg = dao.ProductoIns(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "productos?faces-redirect=true" : "productoIns?faces-redirect=true";
    }

    public String productoUpd() throws Exception {
        msg = null;
        
        if(base64Image != null){
            data.setBase64Image(base64Image);
        }else{
            data.setBase64Image("");
        }
        try {
            msg = dao.ProductoUpd(data);
        } catch (Exception e) {
            throw new Exception(dao.getMensaje());
        }
        return (msg == null) ? "productos?faces-redirect=true" : "productoUpd?faces-redirect=true";
    }

    public String insertar() {
        data = new Producto();
        base64Image = "";
        return "productoIns?faces-redirect=true";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getData() {
        return data;
    }

    public void setData(Producto data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String InputStreamToBase64(InputStream inputStream) {
        String ToBase64 = "";
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = outputStream.toByteArray();

            ToBase64 = Base64.getEncoder().encodeToString(imageBytes);

            inputStream.close();
            outputStream.close();

        }catch(IOException e){
            
        }
        
        return ToBase64;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public boolean isFiltroActivo() {
        return filtroActivo;
    }

    public void setFiltroActivo(boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
    }
    
    public void method() {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        base64Image = requestParamMap.get("imagen");
    }
    
    public void onInputChanged(ValueChangeEvent event) {
        FacesMessage message = new FacesMessage("Input Changed", "Value: " + event.getNewValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
