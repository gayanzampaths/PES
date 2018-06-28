/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author gayan
 */
public class Attendence {
    private ArrayList<String> epfNos = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> timeIns = new ArrayList<>();
    private ArrayList<String> timeOuts = new ArrayList<>();

    /**
     * @return the epfNos
     */
    public ArrayList<String> getEpfNos() {
        return epfNos;
    }

    /**
     * @param epfNos the epfNos to set
     */
    public void setEpfNos(ArrayList<String> epfNos) {
        this.epfNos = epfNos;
    }

    /**
     * @return the dates
     */
    public ArrayList<String> getDates() {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    /**
     * @return the timeIns
     */
    public ArrayList<String> getTimeIns() {
        return timeIns;
    }

    /**
     * @param timeIns the timeIns to set
     */
    public void setTimeIns(ArrayList<String> timeIns) {
        this.timeIns = timeIns;
    }

    /**
     * @return the timeOuts
     */
    public ArrayList<String> getTimeOuts() {
        return timeOuts;
    }

    /**
     * @param timeOuts the timeOuts to set
     */
    public void setTimeOuts(ArrayList<String> timeOuts) {
        this.timeOuts = timeOuts;
    }
    
    
}
