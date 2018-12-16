/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Charts;

import Models.Efficiency;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author gayan
 */
public class ProductionDepartmentEfficiency extends JFrame{
    public ProductionDepartmentEfficiency( String applicationTitle , String chartTitle, ArrayList<Efficiency> eff ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
        chartTitle,
        "Date","Efficiency",
        createDataset(eff),
        PlotOrientation.VERTICAL,
        true,true,false);
         
        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }
    
    private DefaultCategoryDataset createDataset( ArrayList<Efficiency> eff ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=1; i< eff.size(); i++){
            dataset.addValue(eff.get(i).getEff(),"Cutting Efficiency" , eff.get(i).getDate());
        }
        return dataset;
    }
}
