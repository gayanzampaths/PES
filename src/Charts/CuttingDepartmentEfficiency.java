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
public class CuttingDepartmentEfficiency extends JFrame{
    
    public CuttingDepartmentEfficiency( String applicationTitle , String chartTitle, ArrayList<Efficiency> eff ) {
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
        for(int i=0; i< eff.size(); i++){
            dataset.addValue(eff.get(i).getEff(),"Cutting Efficiency" , eff.get(i).getDate());
        }
//        dataset.addValue( 90 , "Cutting Efficiency" , "2018-09-11" );
//        dataset.addValue( 30 , "Cutting Efficiency" , "2018-09-12" );
//        dataset.addValue( 60 , "Cutting Efficiency" ,  "2018-09-13" );
//        dataset.addValue( 20 , "Cutting Efficiency" , "2018-09-14" );
//        dataset.addValue( 40 , "Cutting Efficiency" , "2018-09-15" );
//        dataset.addValue( 85 , "Cutting Efficiency" , "2018-09-16" );
        return dataset;
    }
}
