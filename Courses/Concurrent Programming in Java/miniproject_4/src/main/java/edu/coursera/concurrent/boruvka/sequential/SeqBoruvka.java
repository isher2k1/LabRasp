package edu.coursera.concurrent.boruvka.sequential;

import edu.coursera.concurrent.AbstractBoruvka;
import edu.coursera.concurrent.SolutionToBoruvka;
import edu.coursera.concurrent.boruvka.Edge;

import java.util.Queue;


public final class SeqBoruvka extends AbstractBoruvka<SeqComponent> {


    public SeqBoruvka() {
        super();
    }


    @Override
    public void computeBoruvka(final Queue<SeqComponent> nodesLoaded,
            final SolutionToBoruvka<SeqComponent> solution) {
        SeqComponent loopNode = null;


        while (!nodesLoaded.isEmpty()) {


            loopNode = nodesLoaded.poll();

            if (loopNode.isDead) {
                continue;
            }


            final Edge<SeqComponent> e = loopNode.getMinEdge();
            if (e == null) {
                break;
            }

            final SeqComponent other = e.getOther(loopNode);
            other.isDead = true;

            loopNode.merge(other, e.weight());

            nodesLoaded.add(loopNode);

        }


        solution.setSolution(loopNode);
    }
}
