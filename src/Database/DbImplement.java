/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.Attendence;
import Models.Defect;
import Models.Efficiency;
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
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    
    public Connection GetConn(){
        return connection;
    }

    @Override
    public void openConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/pes","root","");
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
            String sqlteam,sqlset,sqlEff,sqlDef;
            
            for(int k=1; k<sheet.getRow(0).getLastCellNum(); k++){
                try {
                    sqlteam = "INSERT INTO productionteamdata (date, team, supervisor) VALUES (?,?,?)";
                    sqlEff = "INSERT INTO productionefficiency (date, team, supervisor) VALUES (?,?,?)";
                    sqlDef = "INSERT INTO productiondefect (date, team, supervisor) VALUES (?,?,?)";
                    
                    PreparedStatement pst = this.connection.prepareStatement(sqlteam);
                    PreparedStatement pstEff = this.connection.prepareStatement(sqlEff);
                    PreparedStatement pstDef = this.connection.prepareStatement(sqlDef);
                    
                    pst.setString(1, date);
                    pst.setString(2, formatter.formatCellValue(sheet.getRow(0).getCell(k)));
                    pst.setString(3, formatter.formatCellValue(sheet.getRow(1).getCell(k)));
                    
                    pstEff.setString(1, date);
                    pstEff.setString(2, formatter.formatCellValue(sheet.getRow(0).getCell(k)));
                    pstEff.setString(3, formatter.formatCellValue(sheet.getRow(1).getCell(k)));
                    
                    pstDef.setString(1, date);
                    pstDef.setString(2, formatter.formatCellValue(sheet.getRow(0).getCell(k)));
                    pstDef.setString(3, formatter.formatCellValue(sheet.getRow(1).getCell(k)));
                    
                    pst.executeUpdate();
                    pstEff.executeUpdate();
                    pstDef.executeUpdate();
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
                        sqlset = "INSERT INTO productionsetteams (date, epfNo, team, operation) VALUES(?,?,?,?)";
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
            String sqldata = "INSERT INTO userdata (epfNo,department,designation,joinDate) VALUES (?,?,?,?)";
            
            PreparedStatement pstUser = this.connection.prepareStatement(sqluser);
            PreparedStatement pstData = this.connection.prepareStatement(sqldata);
            
            pstUser.setString(1, user.getEpfNo());
            pstUser.setString(2, user.getUsername());
            pstUser.setString(3, user.getPassword());
            
            pstData.setString(1, user.getEpfNo());
            pstData.setInt(2, user.getDepartment());
            pstData.setInt(3, user.getDesignation());
            
            SimpleDateFormat datef = new SimpleDateFormat("YYYY-MM-dd");
            
            pstData.setString(4, datef.format(new Date()));
            
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

    User getDetails(String epfNo) {
        try {
            String query = "SELECT * FROM userdata WHERE epfNo = '"+epfNo+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            User user = null;
            if(rst.next()){
                user = new User();
                user.setEpfNo(rst.getString("epfNo"));
                user.setName(rst.getString("name"));
                user.setNameUse(rst.getString("nameUse"));
                user.setGender(rst.getString("gender"));
                user.setNum(rst.getString("num"));
                user.setStreet(rst.getString("street"));
                user.setCity1(rst.getString("city1"));
                user.setCity2(rst.getString("city2"));
                user.setDob(rst.getString("dob"));
                user.setNic(rst.getString("nic"));
                user.setReligion(rst.getInt("religion"));
                user.setNationality(rst.getInt("nationality"));
                user.setMaritalStatus(rst.getInt("maritalStatus"));
                user.setPhone(rst.getString("phone"));
                user.setEmail(rst.getString("email"));
                user.setDepartment(rst.getInt("department"));
                user.setDesignation(rst.getInt("designation"));
                user.setJoinDate(rst.getString("joinDate"));
            }
            return user;
        } catch (SQLException e) {
            System.out.println("Error : "+e.getMessage());
            return null;
        }
    }

    boolean UpdateDetails(User user) {
        try {
            String sqldata = "UPDATE userdata SET name ='"+user.getName()+"',nameUse ='"+user.getNameUse()+"',gender ='"+user.getGender()+"',num ='"+user.getNum()+"',street = '"+user.getStreet()+"',city1 ='"+user.getCity1()+"',city2 = '"+user.getCity2()+"',dob ='"+user.getDob()+"',nic = '"+user.getNic()+"',religion ='"+user.getReligion()+"',nationality = '"+user.getNationality()+"',maritalStatus = '"+user.getMaritalStatus()+"',phone = '"+user.getPhone()+"',email ='"+user.getEmail()+"' WHERE epfNo ='"+user.getEpfNo()+"'";
            
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sqldata);
            
            return true;
        } catch (SQLException e) {
            if(e.getErrorCode()==1062){
                JOptionPane.showMessageDialog(null, e.getMessage(),"Duplication Warning!",JOptionPane.WARNING_MESSAGE);
                return false;
            }else{
                JOptionPane.showMessageDialog(null, "Update failed! check your data.", "Registration Failed!", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
                return false;
            }
        }
    }

    boolean ChangePassword(String epf, String ppwd, String npwd) {
        try {
            String findU = "SELECT * FROM users WHERE epfNo = '"+epf+"' AND password = '"+ppwd+"'";
            PreparedStatement pst = this.connection.prepareStatement(findU);
            ResultSet rst = pst.executeQuery();
            if(rst.next()){
                String update = "UPDATE users SET password = '"+npwd+"' WHERE epfNo = '"+epf+"' AND password = '"+ppwd+"'";
                Statement statement = this.connection.createStatement();
                statement.executeUpdate(update);
                JOptionPane.showMessageDialog(null, "Update Complete!", "Done!", JOptionPane.PLAIN_MESSAGE);
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "User not found! check details again.", "Not Found!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    User getUserData(String epf) {
        try {
            String sql = "SELECT * FROM userdata WHERE epfNo = '"+epf+"'";
            PreparedStatement pst = this.connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            User user = null;
            if(rst.next()){
                user = new User();
                user.setDepartment(rst.getInt("department"));
                user.setDesignation(rst.getInt("designation"));
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    boolean updateEmployeeDetails(User user) {
        try {
            String sql = "UPDATE userdata SET department = '"+user.getDepartment()+"', designation = '"+user.getDesignation()+"' WHERE epfNo = '"+user.getEpfNo()+"'";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    boolean importCuttingDepEmp(Files f) {
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
                
                try {
                    
                    String cuttingEmp = "INSERT INTO cuttingdepartmentemp (date, epfNo) VALUES(?,?)";
                    String cuttingEff = "INSERT INTO cuttingefficiency (date, epfNo) VALUES(?,?)";
                    String cuttingDef = "INSERT INTO cuttingdefects (date, epfNo) VALUES(?,?)";
                    PreparedStatement pstmEmp = this.connection.prepareStatement(cuttingEmp);
                    PreparedStatement pstmEff = this.connection.prepareStatement(cuttingEff);
                    PreparedStatement pstmDef = this.connection.prepareStatement(cuttingDef);

                    pstmEmp.setString(1, a);
                    pstmEmp.setString(2, b);
                    
                    pstmEff.setString(1, a);
                    pstmEff.setString(2, b);
                    
                    pstmDef.setString(1, a);
                    pstmDef.setString(2, b);

                    pstmEmp.executeUpdate();
                    pstmEff.executeUpdate();
                    pstmDef.executeUpdate();
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
        } catch (Exception e) {
            return false;
        }
    }

    ArrayList<Efficiency> getCuttingEff(String epf) {
        try {
            String query = "SELECT * FROM cuttingefficiency WHERE epfNo = '"+epf+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            ArrayList<Efficiency> efficiencys = new ArrayList<>();
            Efficiency efficiency = null;
            while(rst.next()){
                efficiency = new Efficiency();
                efficiency.setDate(rst.getString("date"));
                efficiency.setEff(rst.getDouble("efficiency"));
                efficiencys.add(efficiency);
            }
            if(efficiencys.isEmpty()){
                return null;
            }
            return efficiencys;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Employee Not Found!", "Not Found!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    boolean setEff(Efficiency efficiency) {
        try {
            String sql = "UPDATE cuttingefficiency SET supervisor = '"+efficiency.getSupervisor()+"', target = '" + efficiency.getTarget() + "', actual = '" + efficiency.getActual() + "', efficiency = '" + efficiency.getEff() + "' WHERE epfNo = '"+ efficiency.getEpfNo()+"' AND date = '"+efficiency.getDate()+"'";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    ArrayList<String> getCuttingSupervisors() {
        try {
            String sql = "SELECT epfNo FROM userdata WHERE designation = '8'";
            PreparedStatement pst = this.connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            ArrayList<String> sup = new ArrayList<>();
            while(rst.next()){
                sup.add(rst.getString("epfNo"));
            }
            return sup;
        } catch (SQLException e) {
            return null;
        }
    }

    ArrayList<Defect> getCuttingDef(String epf) {
        try {
            String query = "SELECT * FROM cuttingdefects WHERE epfNo = '"+epf+"'";
            PreparedStatement pst = this.connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            ArrayList<Defect> defects = new ArrayList<>();
            Defect defect = null;
            while(rst.next()){
                defect = new Defect();
                defect.setData(rst.getString("date"));
                defect.setDefectRate(rst.getDouble("defectRate"));
                defects.add(defect);
            }
            if(defects.isEmpty()){
                return null;
            }
            return defects;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Employee Not Found!", "Not Found!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    boolean setDef(Defect defect) {
        try {
            String sql = "UPDATE cuttingdefects SET supervisor = '"+defect.getSupervisor()+"', sample  = '" + defect.getSample()+ "', defect = '" + defect.getDefect()+ "', defectRate = '" + defect.getDefectRate()+ "' WHERE epfNo = '"+ defect.getEpf()+"' AND date = '"+defect.getData()+"'";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    ArrayList<String> getTeams(String datePic) {
        try {
            String sql = "SELECT DISTINCT team FROM productionsetteams WHERE date = '"+datePic+"'";
            PreparedStatement pst = this.connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            ArrayList<String> teams = new ArrayList<>();
            while(rst.next()){
                teams.add(rst.getString("team"));
            }
            return teams;
        } catch (Exception e) {
            return null;
        }
    }

    ArrayList<Efficiency> getProdEff(String datePic, String team) {
        try {
            String sql = "SELECT COUNT(epfNo) FROM productionsetteams WHERE date = '"+datePic+"' AND team = '"+team+"'";
            PreparedStatement pst = this.connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            ArrayList<Efficiency> efficiencys = new ArrayList<>();
            Efficiency efficiency = new Efficiency();
            
            if(rst.next()){
                efficiency.setOperators(rst.getInt(1));
            }
            
            String getSup = "SELECT supervisor FROM productionteamdata WHERE date = '"+datePic+"' AND team = '"+team+"'";
            PreparedStatement pstSup = this.connection.prepareStatement(getSup);
            ResultSet rstSup = pstSup.executeQuery();
            
            if(rstSup.next()){
                efficiency.setSupervisor(rstSup.getString("supervisor"));
            }
            efficiencys.add(efficiency);
            
            String getEff = "SELECT * FROM productionefficiency WHERE team = '"+team+"'";
            PreparedStatement pstEff = this.connection.prepareStatement(getEff);
            ResultSet rstEff = pstEff.executeQuery();
            
            while(rstEff.next()){
                efficiency = new Efficiency();
                efficiency.setDate(rstEff.getString("date"));
                efficiency.setEff(rstEff.getDouble("efficiency"));
                efficiencys.add(efficiency);
            }
            
            return efficiencys;
        } catch (SQLException e) {
            return null;
        }
    }

    boolean saveProdEff(Efficiency efficiency) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            String sql = "UPDATE productionefficiency SET smv = '"+efficiency.getSmv()+"', operators = '"+efficiency.getOperators()+"', outputs = '"+efficiency.getOutputs()+"', hours = '"+efficiency.getHours()+"', efficiency = '"+df.format(efficiency.getEff())+"' WHERE date = '"+efficiency.getDate()+"' AND team = '"+efficiency.getTeam()+"'";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    ArrayList<Defect> getProdDef(String datePic, String team) {
        try {
            String getSup = "SELECT supervisor FROM productionteamdata WHERE date = '"+datePic+"' AND team = '"+team+"'";
            PreparedStatement pstSup = this.connection.prepareStatement(getSup);
            ResultSet rstSup = pstSup.executeQuery();
            ArrayList<Defect> defects = new ArrayList<>();
            Defect defect = new Defect();
            if(rstSup.next()){
                defect.setSupervisor(rstSup.getString("supervisor"));
            }
            defects.add(defect);
            
            String getEff = "SELECT * FROM productiondefect WHERE team = '"+team+"'";
            PreparedStatement pstEff = this.connection.prepareStatement(getEff);
            ResultSet rstEff = pstEff.executeQuery();
            
            while(rstEff.next()){
                defect = new Defect();
                defect.setData(rstEff.getString("date"));
                defect.setDefectRate(rstEff.getDouble("defectRate"));
                defects.add(defect);
            }
            return defects;
        } catch (SQLException e) {
            return null;
        }
    }

    boolean saveProdDef(Defect defect) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            String sql = "UPDATE productiondefect SET sample = '"+defect.getSample()+"', defect = '"+defect.getDefect()+"', defectRate = '"+defect.getDefectRate()+"' WHERE date = '"+defect.getData()+"' AND team = '"+defect.getTeam()+"'";
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
