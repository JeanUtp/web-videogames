package Graficas;

import java.sql.*;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import util.ConectaBD;

@ManagedBean(name = "BarChartHorizontal")
@ApplicationScoped
public class BarChartHorizontal {

    private ConectaBD conectaDb;
    
    private HorizontalBarChartModel horizontalBarModel;

    public HorizontalBarChartModel getHorizontalBarModel() {
        this.conectaDb = new ConectaBD();
        return horizontalBarModel;
    }

    public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
        this.horizontalBarModel = horizontalBarModel;
    }

    public void graficar() {
        horizontalBarModel = new HorizontalBarChartModel();
        ChartSeries series = new ChartSeries();

        int mayor = 0;
        int menor = 999999999;

        try {
            ResultSet datosObtenidosRs = null;
            PreparedStatement setenciaSqlPrecompilada = null;

            String sqlOption = "SELECT p.nombreproducto, COUNT(pd.cantidad) AS total, p.stock FROM productos as p INNER JOIN pedidosdetalle as pd ON pd.idproducto = p.idproducto GROUP BY p.idproducto ORDER BY total desc LIMIT 5";
            setenciaSqlPrecompilada = conectaDb.getConnection().prepareStatement(sqlOption);
            datosObtenidosRs = setenciaSqlPrecompilada.executeQuery();

            series.setLabel("Productos ventas");

            while (datosObtenidosRs.next()) {
                series.set(datosObtenidosRs.getString(1) + "(Stock:" + datosObtenidosRs.getInt(3) + ")", datosObtenidosRs.getInt(2));

                if (datosObtenidosRs.getInt(2) > mayor) {
                    mayor = datosObtenidosRs.getInt(2);
                }
                if (datosObtenidosRs.getInt(2) < menor) {
                    menor = datosObtenidosRs.getInt(2);
                }
            }

        } catch (Exception e) {

        }

        horizontalBarModel.addSeries(series);

        horizontalBarModel.setTitle("Productos más vendidos");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Ventas");
        xAxis.setMin(menor-1);
        xAxis.setMax(mayor);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Productos");
    }

}
