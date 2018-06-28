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
public class TeamsData {
    private ArrayList<String> teamID = new ArrayList<>();
    private ArrayList<String> supervisor = new ArrayList<>();
    private ArrayList<String> operations = new ArrayList<>();
    private ArrayList<String> team = new ArrayList<>();
    private ArrayList<String> epf = new ArrayList<>();

    /**
     * @return the teamID
     */
    public ArrayList<String> getTeamID() {
        return teamID;
    }

    /**
     * @param teamID the teamID to set
     */
    public void setTeamID(ArrayList<String> teamID) {
        this.teamID = teamID;
    }

    /**
     * @return the supervisor
     */
    public ArrayList<String> getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(ArrayList<String> supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * @return the operations
     */
    public ArrayList<String> getOperations() {
        return operations;
    }

    /**
     * @param operations the operations to set
     */
    public void setOperations(ArrayList<String> operations) {
        this.operations = operations;
    }

    /**
     * @return the team
     */
    public ArrayList<String> getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(ArrayList<String> team) {
        this.team = team;
    }

    /**
     * @return the epf
     */
    public ArrayList<String> getEpf() {
        return epf;
    }

    /**
     * @param epf the epf to set
     */
    public void setEpf(ArrayList<String> epf) {
        this.epf = epf;
    }
}
