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
import Models.LoadData;
import Models.TeamsData;
import Models.User;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author gayan
 */
public class DbController {
    
    private static DbController controller;
    private DbImplement dbimpl;
    
    public DbController(){
        this.dbimpl = new DbImplement();
        this.dbimpl.openConn();
    }
    
    public static DbController getController(){
        if(controller == null){
            controller = new DbController();
        }
        return controller;
    }
    
    public boolean signUp(User user){
        return this.dbimpl.insert(user);
    }
    
    public void test(){
        System.out.println("This is a test!");
    }
    
    public void closeDbConn(){
        this.dbimpl.closeConn();
    }

    public User signIn(String username, String password) {
        return this.dbimpl.getUser(username, password);
    }

    public boolean impAttendence(Files f) {
        return this.dbimpl.importAttendence(f);
    }

    public boolean importTeams(Files f, String date) {
        return this.dbimpl.importTeams(f,date);
    }

    public boolean register(User user) {
        return this.dbimpl.register(user);
    }

    public Attendence getAttendence(String date) {
        return this.dbimpl.getAttendence(date);
    }

    public Attendence getEPFAtt(String epf) {
        return this.dbimpl.getEPFAtt(epf);
    }

    public User getDetails(String epfNo) {
        return this.dbimpl.getDetails(epfNo);
    }

    public boolean UpdateDetails(User userd) {
        return this.dbimpl.UpdateDetails(userd);
    }
    
    public Connection getConn(){
        return this.dbimpl.GetConn();
    }

    public boolean ChangePassword(String epf, String ppwd, String npwd) {
        return this.dbimpl.ChangePassword(epf,ppwd,npwd);
    }

    public User getUserData(String epf) {
        return this.dbimpl.getUserData(epf);
    }

    public boolean updateEmployeeDetails(User user) {
        return this.dbimpl.updateEmployeeDetails(user);
    }

    public boolean importCuttingDepEmp(Files f) {
        return this.dbimpl.importCuttingDepEmp(f);
    }

    public ArrayList<Efficiency> getCuttingEff(String epf) {
        return this.dbimpl.getCuttingEff(epf);
    }

    public boolean setEff(Efficiency efficiency) {
        return this.dbimpl.setEff(efficiency);
    }

    public ArrayList<String> getCuttingSupervisors() {
        return this.dbimpl.getCuttingSupervisors();
    }

    public ArrayList<Defect> getCuttingDef(String epf) {
        return this.dbimpl.getCuttingDef(epf);
    }

    public boolean setDef(Defect defect) {
        return this.dbimpl.setDef(defect);
    }

    public ArrayList<String> getTeams(String datePic) {
        return this.dbimpl.getTeams(datePic);
    }

    public ArrayList<Efficiency> getProdEff(String datePic, String team) {
        return this.dbimpl.getProdEff(datePic, team);
    }

    public boolean saveProdEff(Efficiency efficiency) {
        return this.dbimpl.saveProdEff(efficiency);
    }

    public ArrayList<Defect> getProdDef(String datePic, String team) {
        return this.dbimpl.getProdDef(datePic, team);
    }

    public boolean saveProdDef(Defect defect) {
        return this.dbimpl.saveProdDef(defect);
    }

    public ArrayList<LoadData> loadData(String d) {
        return this.dbimpl.loadData(d);
    }
}
