/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Models.User;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author gayan
 */
public class UnitTestClass {
    public static void main(String[] args) {
        
//        User user = new User();
//        user.setEpfNo("1111");
        EmployeeDetails empd = new EmployeeDetails("1111");
        empd.setVisible(true);

//        SimpleDateFormat datef = new SimpleDateFormat("YYYY-MM-dd");
//        String s = datef.format(new Date());
//        System.out.println(s);
    }
}
