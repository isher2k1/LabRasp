package edu.coursera.concurrent;

import edu.coursera.concurrent.AbstractBoruvka;
import edu.coursera.concurrent.SolutionToBoruvka;
import edu.coursera.concurrent.boruvka.Edge;
import edu.coursera.concurrent.boruvka.Component;

import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public final class ParBoruvka extends AbstractBoruvka<ParBoruvka.ParComponent> {


    public ParBoruvka() {
        super();
    }

    @Override
    public void computeBoruvka(final Queue<ParComponent> nodesLoaded,
            final SolutionToBoruvka<ParComponent> solution) 
    {
    	 ParComponent current = null;


         // START OF EDGE CONTRACTION ALGORITHM
         while (!nodesLoaded.isEmpty()) {
             current = nodesLoaded.poll();

             // Empty queue or the component is acquired by another thread.
             if (current == null || !current.lock.tryLock()) {
                 continue;
             }

             // Already processed.
             if (current.isDead) {
                 current.lock.unlock();
                 continue;
             }
          // Maybe the graph is processed, set the solution.
             Edge<ParComponent> minEdge = current.getMinEdge();
             if (minEdge == null) {
                 current.lock.unlock();

//                 solutionLock.lock();
                 solution.setSolution(current);
                 break;
             }

             // Process current component.
             final ParComponent other = minEdge.getOther(current);
             if (!other.lock.tryLock()) {
                 current.lock.unlock();
                 nodesLoaded.add(current);
                 continue;
             }

             other.isDead = true;
             current.merge(other, minEdge.weight());
             current.lock.unlock();
             other.lock.unlock();

             nodesLoaded.add(current);
       }
      }


    public static final class ParComponent extends Component<ParComponent> {

        public final int nodeId;
      
        public final ReentrantLock lock = new ReentrantLock();

        private List<Edge<ParComponent>> edges = new ArrayList<>();

        private double totalWeight = 0;

        private long totalEdges = 0;

        public boolean isDead = false;

        public ParComponent(final int setNodeId) {
            super();
            this.nodeId = setNodeId;
        }

        @Override
        public int nodeId() {
            return nodeId;
        }

        @Override
        public double totalWeight() {
            return totalWeight;
        }

        @Override
        public long totalEdges() {
            return totalEdges;
        }

        public void addEdge(final Edge<ParComponent> e) {
            int i = 0;
            while (i < edges.size()) {
                if (e.weight() < edges.get(i).weight()) {
                    break;
                }
                i++;
            }
            edges.add(i, e);
        }


        public Edge<ParComponent> getMinEdge() {
            if (edges.size() == 0) {
                return null;
            }
            return edges.get(0);
        }

        public void merge(final ParComponent other, final double edgeWeight) {
            totalWeight += other.totalWeight + edgeWeight;
            totalEdges += other.totalEdges + 1;

            final List<Edge<ParComponent>> newEdges = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i + j < edges.size() + other.edges.size()) {
                // Get rid of inter-component edges
                while (i < edges.size()) {
                    final Edge<ParComponent> e = edges.get(i);
                    if ((e.fromComponent() != this
                                && e.fromComponent() != other)
                            || (e.toComponent() != this
                                && e.toComponent() != other)) {
                        break;
                    }
                    i++;
                }
                while (j < other.edges.size()) {
                    final Edge<ParComponent> e = other.edges.get(j);
                    if ((e.fromComponent() != this
                                && e.fromComponent() != other)
                            || (e.toComponent() != this
                                && e.toComponent() != other)) {
                        break;
                    }
                    j++;
                }

                if (j < other.edges.size() && (i >= edges.size()
                            || edges.get(i).weight()
                            > other.edges.get(j).weight())) {
                    newEdges.add(other.edges.get(j++).replaceComponent(other,
                                this));
                } else if (i < edges.size()) {
                    newEdges.add(edges.get(i++).replaceComponent(other, this));
                }
            }
            other.edges.clear();
            edges.clear();
            edges = newEdges;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Component)) {
                return false;
            }

            final Component component = (Component) o;
            return component.nodeId() == nodeId;
        }


        @Override
        public int hashCode() {
            return nodeId;
        }
    }

    public static final class ParEdge extends Edge<ParComponent>
            implements Comparable<Edge> {

        protected ParComponent fromComponent;


        protected ParComponent toComponent;


        public double weight;

        public ParEdge(final ParComponent from, final ParComponent to,
                final double w) {
            fromComponent = from;
            toComponent = to;
            weight = w;
        }

        @Override
        public ParComponent fromComponent() {
            return fromComponent;
        }

        @Override
        public ParComponent toComponent() {
            return toComponent;
        }


        @Override
        public double weight() {
            return weight;
        }

        public ParComponent getOther(final ParComponent from) {
            if (fromComponent == from) {
                assert (toComponent != from);
                return toComponent;
            }

            if (toComponent == from) {
                assert (fromComponent != from);
                return fromComponent;
            }
            assert (false);
            return null;

        }


        @Override
        public int compareTo(final Edge e) {
            if (e.weight() == weight) {
                return 0;
            } else if (weight < e.weight()) {
                return -1;
            } else {
                return 1;
            }
        }

        public ParEdge replaceComponent(final ParComponent from,
                final ParComponent to) {
            if (fromComponent == from) {
                fromComponent = to;
            }
            if (toComponent == from) {
                toComponent = to;
            }
            return this;
        }
    }
}
