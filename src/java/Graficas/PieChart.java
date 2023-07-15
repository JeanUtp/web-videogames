package Graficas;

import java.sql.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;
import util.ConectaBD;

@ManagedBean(name = "PieChart")
@ApplicationScoped
public class PieChart {

    private ConectaBD conectaDb;
    
    private PieChartModel pieModel;

    public PieChartModel getPieModel() {
        this.conectaDb = new ConectaBD();
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public void graficar() {
        //CATEGORIAS TOP
        pieModel = new PieChartModel();

        try {
            ResultSet datosObtenidosRs = null;
            PreparedStatement setenciaSqlPrecompilada = null;

            String sqlOption = "SELECT categorias.nombre, SUM(productos.stock) As Total FROM productos INNER JOIN categorias on categorias.idcategoria = productos.idcategoria GROUP BY categorias.idcategoria";
            setenciaSqlPrecompilada = conectaDb.getConnection().prepareStatement(sqlOption);
            datosObtenidosRs = setenciaSqlPrecompilada.executeQuery();
            while (datosObtenidosRs.next()) {
                pieModel.set(datosObtenidosRs.getString(1)+", Stock: "+datosObtenidosRs.getInt(2), datosObtenidosRs.getInt(2));
            }
        } catch (Exception e) {

        }

        pieModel.setTitle("Categorias TOP");
        pieModel.setLegendPosition("e");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
      
    }

}
