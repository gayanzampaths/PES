/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author gayan
 */
public class LineChart_AWT extends ApplicationFrame {
    
    public LineChart_AWT( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Date","Efficiency",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 90 , "Cutting Efficiency" , "2018-09-11" );
      dataset.addValue( 30 , "Cutting Efficiency" , "2018-09-12" );
      dataset.addValue( 60 , "Cutting Efficiency" ,  "2018-09-13" );
      dataset.addValue( 20 , "Cutting Efficiency" , "2018-09-14" );
      dataset.addValue( 40 , "Cutting Efficiency" , "2018-09-15" );
      dataset.addValue( 85 , "Cutting Efficiency" , "2018-09-16" );
      return dataset;
   }
   
   public static void main( String[ ] args ) {
      LineChart_AWT chart = new LineChart_AWT(
         "Cutting Department Efficiency" ,
         "Efficiency vs Date");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
    
}
