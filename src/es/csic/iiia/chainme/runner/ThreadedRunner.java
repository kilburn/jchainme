/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.csic.iiia.chainme.runner;

import es.csic.iiia.maxsum.Factor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Marc Pujol <mpujol@iiia.csic.es>
 */
public class ThreadedRunner implements Runner {
    private final static int N_THREADS = (int)(Runtime.getRuntime().availableProcessors()*0.75);

    private final ExecutorService executor;

    private final Collection<Callable<Object>> tasks;

    public ThreadedRunner() {
        System.err.println("Running with " + N_THREADS + " threads.");
        executor = Executors.newFixedThreadPool(N_THREADS);
        tasks = new ArrayList<Callable<Object>>();
    }

    @Override
    public void initialize(List<Factor> factors) {
        final int nFactors = factors.size();
        final int chunkSize = (int)Math.ceil(factors.size() / (N_THREADS*64d));

        for (int start=0; start<nFactors; start += chunkSize) {
        //for (int start=nFactors - (nFactors % chunkSize); start>=0; start -= chunkSize) {
            tasks.add(new Job(factors, start, chunkSize));
        }

        System.err.println("Total chunks: " + tasks.size() + ", factors: " + nFactors);
    }

    @Override
    public void run() {
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    private class Job implements Callable<Object> {

        private final int start, end;
        private final List<Factor> factors;

        public Job(List<Factor> factors, int start, int chunkSize) {
            this.start = start;
            this.end = Math.min(start+chunkSize, factors.size());
            this.factors = factors;
            //System.err.println("Chunk start=" + start + ", end=" + end);
        }

        @Override
        public Object call() throws Exception {
            for (int i=start; i<end; i++) {
                factors.get(i).run();
            }
            return null;
        }

    }

}
