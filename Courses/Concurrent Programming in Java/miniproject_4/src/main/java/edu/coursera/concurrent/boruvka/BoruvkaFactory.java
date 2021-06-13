package edu.coursera.concurrent.boruvka;


public interface BoruvkaFactory<C extends Component, E extends Edge> {

    C newComponent(int nodeId);


    E newEdge(C from, C to, double weight);
}
