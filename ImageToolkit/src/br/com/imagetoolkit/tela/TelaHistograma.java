/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.tela;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author 2091140052
 */
public class TelaHistograma extends JFrame{

    private static double[] hist;
    private static String title;

    public TelaHistograma(int[] hist, String title) {
        this.hist = converter(hist);
        this.title = title;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel chartPanel = criarPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
        setContentPane(chartPanel);
    }

     public TelaHistograma(double [] hist, String title) {
        this.hist = hist;
        this.title = title;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel chartPanel = criarPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
        setContentPane(chartPanel);
    }

    public static JPanel criarPanel() {
        JFreeChart chart = criarChart(criarDataset());
        return new ChartPanel(chart);
    }

    private static IntervalXYDataset criarDataset() {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("", hist, 100);
        return dataset;
    }

    private static JFreeChart criarChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
                title,
                null,
                null,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        try {
            ChartUtilities.saveChartAsJPEG(new File("histograma.jpg"), chart, 500, 475);
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo");
        }
        return chart;    
    }

    public double[] converter(int[] hist){
        double[] novo = new double[255];
          for (int i = 0; i < 255; i++) {
            novo[i] = hist[i];
        }
        return novo;
    }
}
