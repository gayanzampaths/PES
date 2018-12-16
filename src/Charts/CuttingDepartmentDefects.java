/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Charts;

import Models.Defect;
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
public class CuttingDepartmentDefects extends JFrame{
    
    public CuttingDepartmentDefects( String applicationTitle , String chartTitle, ArrayList<Defect> def ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
        chartTitle,
        "Date","Defect Rate",
        createDataset(def),
        PlotOrientation.VERTICAL,
        true,true,false);
         
        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }
    
    private DefaultCategoryDataset createDataset( ArrayList<Defect> def ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=0; i< def.size(); i++){
            dataset.addValue(def.get(i).getDefectRate(),"Cutting Defects" , def.get(i).getData());
        }
        return dataset;
    }
}
