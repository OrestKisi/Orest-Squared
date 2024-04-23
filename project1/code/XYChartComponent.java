import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class XYChartComponent implements Component {
    private ChartPanel chartPanel;

    public XYChartComponent(int x, int y, int w, int h) {

        XYSeries series = new XYSeries("Progress");
        series.add(100, 0);
        series.add(0, 300);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "My Progress", // chart title
                "Date", // x-axis label
                "Calories", // y-axis label
                dataset // dataset
        );

        Font font = new Font("Arial", Font.PLAIN, 14);
        chart.getTitle().setFont(font);

        // Get plot and set fonts
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getDomainAxis().setLabelFont(font); // X-axis label font
        plot.getRangeAxis().setLabelFont(font); // Y-axis label font
        plot.getDomainAxis().setTickLabelFont(font); // X-axis tick label font
        plot.getRangeAxis().setTickLabelFont(font); // Y-axis tick label font

        // Set background color and outline
        plot.setBackgroundPaint(Color.GRAY);
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(3));

        // Set line color
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        // Customize the X-axis to display dates
        DateAxis domainAxis = new DateAxis("Date");
        domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
        plot.setDomainAxis(domainAxis);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(w, h));
        chartPanel.setBounds(x, y, w, h);
    }

    @Override
    public JComponent getComponent() {
        return chartPanel;
    }
}
