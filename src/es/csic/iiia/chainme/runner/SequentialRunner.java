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
public class SequentialRunner implements Runner {

    private List<Factor> factors;

    @Override
    public void initialize(List<Factor> factors) {
        this.factors = factors;
    }

    @Override
    public void run() {
        for (Factor f : factors) {
            f.run();
        }
    }

    @Override
    public void stop() {}

}
