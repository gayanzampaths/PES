/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.Attendence;
import Models.Files;
import Models.TeamsData;
import Models.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gayan
 */
public class DbImplement implements DbInterface{

    private Connection connection;

    @Override
    public boolean insert(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(String username, String password) {
        try {
            String query = "SELECT * FROM users where username ='"+username+"' and password = '"+password+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            User user = null;
            if(rst.next()){
                user = new User();
                user.setEpfNo(rst.getString("epfNo"));
            }
            pst.close();
            rst.close();
            
            return user;
        } catch (SQLException e) {
            System.out.println("Error : "+e.getMessage());
            return null;
        }
    }

    @Override
    public void openConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/pes","root","root");
            System.out.println("Connection Established Successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    @Override
    public void closeConn() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    @Override
    public boolean importAttendence(Files f) {
        try {
            FileInputStream fileInputStream = new FileInputStream(f.getFilePath());
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            DataFormatter formatter = new DataFormatter();
            
            String err="";
            
            for (int i = 0; i <= sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);
                String a = formatter.formatCellValue(row.getCell(0));
                String b = formatter.formatCellValue(row.getCell(1));
                String c = formatter.formatCellValue(row.getCell(2));
                String d = formatter.formatCellValue(row.getCell(3));
                
                try {
                    
                    String sql = "INSERT INTO attendence (date, epfNo, timeIn, timeOut) VALUES(?,?,?,?)";
                    PreparedStatement pstm = this.connection.prepareStatement(sql);

                    pstm.setString(1, a);
                    pstm.setString(2, b);
                    pstm.setString(3, c);
                    pstm.setString(4, d);

                    pstm.executeUpdate();
                    
                } catch (SQLException e) {
                    err+=(b+",");
                }
            }
            if(!err.isEmpty()){
                JOptionPane.showMessageDialog(null, "Following Records Already Exists! Check the Spredsheet File. "+err+" None Duplication Records Uploaded!","Duplication Detect!",JOptionPane.ERROR_MESSAGE);
            }else{
                System.out.println("Done!");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }

    @Override
    public boolean importTeams(Files f, String date) {
        try {
            FileInputStream fileInputStream = new FileInputStream(f.getFilePath());
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            String[] data =new String[3];
            DataFormatter formatter = new DataFormatter();
            
            String notMatch="";
            String sqlteam,sqlset;
            
            for(int k=1; k<sheet.getRow(0).getLastCellNum(); k++){
                try {
                    sqlteam = "INSERT INTO teamdata (date, team, supervisor) VALUES (?,?,?)";
                    PreparedStatement pst = this.connection.prepareStatement(sqlteam);
                    
                    pst.setString(1, date);
                    pst.setString(2, formatter.formatCellValue(sheet.getRow(0).getCell(k)));
                    pst.setString(3, formatter.formatCellValue(sheet.getRow(1).getCell(k)));
                    
                    pst.executeUpdate();
                } catch (SQLException e) {
                }
            }
            
            for(int i=3; i<= sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);
                
                for(int j=1; j<row.getLastCellNum();j++){
                    data[0] = formatter.formatCellValue(row.getCell(j));
                    data[1] = formatter.formatCellValue(sheet.getRow(0).getCell(j));
                    data[2] = formatter.formatCellValue(row.getCell(0));
                    
                    try {
                        sqlset = "INSERT INTO setteams (date, epfNo, team, operation) VALUES(?,?,?,?)";
                        PreparedStatement pstm = this.connection.prepareStatement(sqlset);

                        pstm.setString(1, date);
                        pstm.setString(2, data[0]);
                        pstm.setString(3, data[1]);
                        pstm.setString(4, data[2]);

                        pstm.executeUpdate();
                        
                    } catch (SQLException e) {
                        if(e.getErrorCode()==1452){
                            notMatch += data[0]+",";
                        }
                    }
                }
            }
            if(!notMatch.isEmpty()){
                JOptionPane.showMessageDialog(null,"This epfNo(s) dosent match with attendence on "+date+" : "+notMatch,"Import Error!",JOptionPane.ERROR_MESSAGE);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        try {
            String sqluser = "INSERT INTO users VALUES (?,?,?)";
            String sqldata = "INSERT INTO userdata (epfNo,designation) VALUES (?,?)";
            
            PreparedStatement pstUser = this.connection.prepareStatement(sqluser);
            PreparedStatement pstData = this.connection.prepareStatement(sqldata);
            
            pstUser.setString(1, user.getEpfNo());
            pstUser.setString(2, user.getUsername());
            pstUser.setString(3, user.getPassword());
            
            pstData.setString(1, user.getEpfNo());
            pstData.setInt(2, user.getDesignation());
            
            pstUser.executeUpdate();
            pstData.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            if(e.getErrorCode()==1062){
                JOptionPane.showMessageDialog(null, e.getMessage(),"Duplication Warning!",JOptionPane.WARNING_MESSAGE);
                return false;
            }else{
                JOptionPane.showMessageDialog(null, "Registration Failed! check your data.", "Registration Failed!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    @Override
    public Attendence getAttendence(String date) {
        try {
            String query = "SELECT * FROM attendence WHERE date = '"+date+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            Attendence att = new Attendence();
            ArrayList<String> epfNos = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            ArrayList<String> timeIns = new ArrayList<>();
            ArrayList<String> timeOuts = new ArrayList<>();
            while(rst.next()){
                epfNos.add(rst.getString("epfNo"));
                dates.add(rst.getString("date"));
                timeIns.add(rst.getString("timeIn"));
                timeOuts.add(rst.getString("timeOut"));
            }
            if(epfNos.size()>0){
                att.setDates(dates);
                att.setEpfNos(epfNos);
                att.setTimeIns(timeIns);
                att.setTimeOuts(timeOuts);
                return att;
            }else{
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Attendence getEPFAtt(String epf) {
        try {
            String query = "SELECT * FROM attendence WHERE epfNo = '"+epf+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            Attendence att = new Attendence();
            ArrayList<String> epfNos = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            ArrayList<String> timeIns = new ArrayList<>();
            ArrayList<String> timeOuts = new ArrayList<>();
            while(rst.next()){
                epfNos.add(rst.getString("epfNo"));
                dates.add(rst.getString("date"));
                timeIns.add(rst.getString("timeIn"));
                timeOuts.add(rst.getString("timeOut"));
            }
            if(epfNos.size()>0){
                att.setDates(dates);
                att.setEpfNos(epfNos);
                att.setTimeIns(timeIns);
                att.setTimeOuts(timeOuts);
                return att;
            }else{
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
    
}
