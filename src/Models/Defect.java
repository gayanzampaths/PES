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
public class Defect {
    
    private String epf;
    private double defectRate;
    private String data;
    private int sample;
    private int defect;
    private String supervisor;

    /**
     * @return the epf
     */
    public String getEpf() {
        return epf;
    }

    /**
     * @param epf the epf to set
     */
    public void setEpf(String epf) {
        this.epf = epf;
    }

    /**
     * @return the defectRate
     */
    public double getDefectRate() {
        return defectRate;
    }

    /**
     * @param defectRate the defectRate to set
     */
    public void setDefectRate(double defectRate) {
        this.defectRate = defectRate;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the sample
     */
    public int getSample() {
        return sample;
    }

    /**
     * @param sample the sample to set
     */
    public void setSample(int sample) {
        this.sample = sample;
    }

    /**
     * @return the defect
     */
    public int getDefect() {
        return defect;
    }

    /**
     * @param defect the defect to set
     */
    public void setDefect(int defect) {
        this.defect = defect;
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
    
}
