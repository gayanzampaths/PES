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
public interface DbInterface {
    boolean insert(User user);
    
    User getUser(String username, String password);
    
    void openConn();
    
    void closeConn();
    
    boolean importAttendence(Files f);
    
    boolean importTeams(Files f, String date);
    
    boolean register(User user);
    
    Attendence getAttendence(String date);
    
    Attendence getEPFAtt(String epf);
}
