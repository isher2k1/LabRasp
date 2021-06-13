package edu.coursera.concurrent.boruvka.parallel;

import edu.coursera.concurrent.boruvka.BoruvkaFactory;

import edu.coursera.concurrent.ParBoruvka.ParComponent;
import edu.coursera.concurrent.ParBoruvka.ParEdge;

public final class ParBoruvkaFactory
        implements BoruvkaFactory<ParComponent, ParEdge> {

    @Override
    public ParComponent newComponent(final int nodeId) {
        return new ParComponent(nodeId);
    }


    @Override
    public ParEdge newEdge(final ParComponent from, final ParComponent to,
            final double weight) {
        return new ParEdge(from, to, weight);
    }
}
