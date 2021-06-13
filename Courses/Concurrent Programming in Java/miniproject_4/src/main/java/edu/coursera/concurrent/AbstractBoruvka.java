package edu.coursera.concurrent;

import edu.coursera.concurrent.boruvka.Component;

import java.util.Queue;


public abstract class AbstractBoruvka<C extends Component> {

    public abstract void computeBoruvka(final Queue<C> nodesLoaded,
            final SolutionToBoruvka<C> solution);
}
