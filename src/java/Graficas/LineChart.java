package Graficas;

import java.sql.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import util.ConectaBD;

@ManagedBean(name = "LineChart")
@ApplicationScoped
public class LineChart {

    private ConectaBD conectaDb;
    
    private LineChartModel lineChart;

    public LineChartModel getLineChart() {
        this.conectaDb = new ConectaBD();
        return lineChart;
    }

    public void setLineChart(LineChartModel lineChart) {
        this.lineChart = lineChart;
    }

    public void graficar() {
        ChartSeries ventas = new ChartSeries();
        lineChart = new LineChartModel();
        try {
            Connection miConexionCN = null;
            Class.forName("com.mysql.jdbc.Driver");
            miConexionCN = DriverManager.getConnection("jdbc:mysql://localhost/bodega", "root", "");
            ResultSet datosObtenidosRs = null;
            PreparedStatement setenciaSqlPrecompilada = null;

            String sqlOption = "SELECT MONTHNAME(p.fechapedido) as mes, sum(d.subtotal) AS total from pedidos as p inner join pedidosdetalle as d on d.idpedido = p.idpedido   GROUP BY month(p.fechapedido) ORDER BY p.fechapedido ASC";
            setenciaSqlPrecompilada = miConexionCN.prepareStatement(sqlOption);
            datosObtenidosRs = setenciaSqlPrecompilada.executeQuery();

            ventas.setLabel("Ganancia en Ventas (S/.)");
            while (datosObtenidosRs.next()) {
                //datos.addValue(datosObtenidosRs.getInt(2), "productos", datosObtenidosRs.getString(1));
                ventas.set(datosObtenidosRs.getString(1), datosObtenidosRs.getInt(2));
            }

        } catch (Exception e) {

        }

        lineChart.addSeries(ventas);

        lineChart.setTitle("Proyección de ventas");
        lineChart.setLegendPosition("e");
        lineChart.setShowPointLabels(true);
        lineChart.setShowPointLabels(true);
        lineChart.setAnimate(true);
        lineChart.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

    }

}
