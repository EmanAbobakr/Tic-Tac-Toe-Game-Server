/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.io.Serializable;

/**
 *
 * @author mac
 */
public class ExitFromGame implements Serializable {

    private String withdrawn;
    private String opp;
    private boolean isGameRun;

    public String getWithdrawn() {
        return withdrawn;
    }

    public String getOpp() {
        return opp;
    }

    public void setWithdrawn(String withdrawn) {
        this.withdrawn = withdrawn;
    }

    public void setOpp(String opp) {
        this.opp = opp;
    }

    public ExitFromGame(String withdrawn, String opp, boolean isGameRun) {
        this.withdrawn = withdrawn;
        this.opp = opp;
        this.isGameRun = isGameRun;
    }

    public ExitFromGame() {
    }

    public boolean isIsGameRun() {
        return isGameRun;
    }

    public void setIsGameRun(boolean isGameRun) {
        this.isGameRun = isGameRun;
    }

}
