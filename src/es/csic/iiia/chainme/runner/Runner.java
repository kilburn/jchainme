/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.csic.iiia.chainme.runner;

import es.csic.iiia.maxsum.Factor;
import java.util.List;

/**
 *
 * @author Marc Pujol <mpujol@iiia.csic.es>
 */
public interface Runner {

    /**
     * Initializes the runner.
     *
     * @param factors factors to run
     */
    public void initialize(List<Factor> factors);

    /**
     * Runs the specified factors.
     */
    public void run();

    /**
     * Stops this runner.
     */
    public void stop();

}
