package Graficas;

import java.sql.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import util.ConectaBD;

@ManagedBean(name = "BarChartH")
@ApplicationScoped
public class BarChartH {
    
    private ConectaBD conectaDb;
    
    private BarChartModel barChartModel;

    public BarChartModel getBarChartModel() {
        this.conectaDb = new ConectaBD();
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public void graficar() {
        barChartModel = new BarChartModel();
        ChartSeries series = new ChartSeries();

        try {
            ResultSet datosObtenidosRs = null;
            PreparedStatement setenciaSqlPrecompilada = null;

            String sqlOption = "SELECT p.nombreproducto, COUNT(pd.cantidad) AS total, p.stock FROM productos as p INNER JOIN pedidosdetalle as pd ON pd.idproducto = p.idproducto GROUP BY p.idproducto ORDER BY total asc LIMIT 3";
            setenciaSqlPrecompilada = conectaDb.getConnection().prepareStatement(sqlOption);
            datosObtenidosRs = setenciaSqlPrecompilada.executeQuery();

            series.setLabel("Productos ventas");

            while (datosObtenidosRs.next()) {
                series.set(datosObtenidosRs.getString(1) + "(" + datosObtenidosRs.getInt(3) + ")", datosObtenidosRs.getInt(2));

            }

        } catch (Exception e) {

        }

        barChartModel.addSeries(series);

        barChartModel.setTitle("Productos menos vendidos");
        barChartModel.setLegendPosition("e");
        barChartModel.setStacked(true);
        barChartModel.setAnimate(true);

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Productos");
        xAxis.setMin(0);
        xAxis.setMax(200);

        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ventas");
    }

}
