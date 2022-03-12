/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author EmanAbobakr
 */
public class ScoreTable implements Serializable {
    public HashMap<String, String> scores = new HashMap<String, String>();
}
