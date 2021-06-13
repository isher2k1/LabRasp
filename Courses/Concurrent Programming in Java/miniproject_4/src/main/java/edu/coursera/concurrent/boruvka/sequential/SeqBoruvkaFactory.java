package edu.coursera.concurrent.boruvka.sequential;

import edu.coursera.concurrent.boruvka.BoruvkaFactory;

public final class SeqBoruvkaFactory
        implements BoruvkaFactory<SeqComponent, SeqEdge> {

    @Override
    public SeqComponent newComponent(final int nodeId) {
        return new SeqComponent(nodeId);
    }

    @Override
    public SeqEdge newEdge(final SeqComponent from, final SeqComponent to,
            final double weight) {
        return new SeqEdge(from, to, weight);
    }
}
