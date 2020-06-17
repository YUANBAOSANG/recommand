package draw;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

/**
 * @创建人 YDL
 * @创建时间 2020/5/5 1:35
 * @描述
 */
public class LineChart {

    public static ChartPanel createPort(String title, String xLabel, String yLabel, CategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart3D(title,
                xLabel,yLabel,dataset,PlotOrientation.VERTICAL,true,false,false);
        CategoryPlot polt = chart.getCategoryPlot();
        CategoryAxis domainAxis = polt.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));
        ValueAxis rangeAxis = polt.getRangeAxis();
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));
        ChartPanel chartPanel = new ChartPanel(chart,true);
        return chartPanel;
    }

}
