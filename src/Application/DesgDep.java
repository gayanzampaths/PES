/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author gayan
 */
public class DesgDep {
    public String desig(int des){
        switch(des){
            case 1: return "General Manager";
            case 2: return "HR Manager";
            case 3: return "Production Manager";
            case 4: return "Operations Manager";
            case 5: return "Marketing Manager";
            case 6: return "Production Supervisor";
            case 7: return "Quality Assurance Supervisor";
            case 8: return "Cutting Supervisor";
            case 9: return "Packing Supervisor";
        }
        return "Error";
    }
    
    public String Depart(int dep){
        switch(dep){
            case 1: return "HR Department";
            case 2: return "Marketing Department";
            case 3: return "Cutting Department";
            case 4: return "Production Department";
            case 5: return "Finishing Department";
            case 6: return "Other";
        }
        return "Error";
    }
    
    public String Relig(int rel){
        switch(rel){
            case 1: return "Buddhism";
            case 2: return "Christian";
            case 3: return "Hindu";
            case 4: return "Muslim";
        }
        return "Error";
    }
    
    public String natio(int nat){
        switch(nat){
            case 1: return "Sri Lankan";
            case 2: return "Other";
        }
        return "Error";
    }
    
    public String mart(int mar){
        switch(mar){
            case 1: return "Married";
            case 2: return "Unmarried";
            case 3: return "Devorce";
        }
        return "Error";
    }
}