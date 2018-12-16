/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author gayan
 */
public class Efficiency {
    
    private String epfNo;
    private String date;
    private int actual;
    private int target;
    private double eff;
    private String supervisor;
    private String team;
    private int operators;
    private double smv;
    private int outputs;
    private double hours;

    /**
     * @return the epfNo
     */
    public String getEpfNo() {
        return epfNo;
    }

    /**
     * @param epfNo the epfNo to set
     */
    public void setEpfNo(String epfNo) {
        this.epfNo = epfNo;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the actual
     */
    public int getActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(int actual) {
        this.actual = actual;
    }

    /**
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * @return the eff
     */
    public double getEff() {
        return eff;
    }

    /**
     * @param eff the eff to set
     */
    public void setEff(double eff) {
        this.eff = eff;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return the operators
     */
    public int getOperators() {
        return operators;
    }

    /**
     * @param operators the operators to set
     */
    public void setOperators(int operators) {
        this.operators = operators;
    }

    /**
     * @return the smv
     */
    public double getSmv() {
        return smv;
    }

    /**
     * @param smv the smv to set
     */
    public void setSmv(double smv) {
        this.smv = smv;
    }

    /**
     * @return the outputs
     */
    public int getOutputs() {
        return outputs;
    }

    /**
     * @param outputs the outputs to set
     */
    public void setOutputs(int outputs) {
        this.outputs = outputs;
    }

    /**
     * @return the hours
     */
    public double getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(double hours) {
        this.hours = hours;
    }
    
    
}
