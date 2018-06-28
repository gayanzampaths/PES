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
}
