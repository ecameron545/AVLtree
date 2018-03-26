package impl;


import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.OrderedMap;
import adt.Stack;

/**
 * RecursiveBSTMap
 * 
 * Class to where the commonality among various recursive binary
 * search tree classes can be pulled up. This is to capture most
 * of the functionality of a binary search tree in such a way that
 * the machinery for monitoring and rebalancing the trees can
 * be added on by child classes.
 * 
 * This and all child classes are set up to have "null objects"
 * in place of where null links would be. Thus methods can be
 * called on these place holders, avoiding what would otherwise
 * be many checks for null links.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public abstract class RecursiveBSTMap<K extends Comparable<K>, V, N extends RecursiveBSTMap.Node<K, V, N>> 
        implements OrderedMap<K, V> {

    /**
     * Basic specification of what a node supports.
     */
    protected interface Node<KK extends Comparable<KK>, VV, NN extends RecursiveBSTMap.Node<KK,VV, NN>> {
        // support for map operations which will be called recursively on the nodes

        /**
         * Insert or overwrite a value for a key, possibly
         * altering the tree.
         * @return The root of the new tree resulting from this
         * insertion and any rebalancing that is triggered by it.
         */
        NN put(KK key, VV val);
        
       
       NN putFixup();
       

        boolean containsKey(KK key);

        VV get(KK key);

        // getter methods for content of the nodes; used in rotations
        NN left();
        NN right();
        KK key();

        /**
         * Is this node a null object?
         */
        boolean isNull();
        
        /**
         * Ordered Map operations
         */
        /**
         * PRECONDITION: There is at least one node in the tree
         * @return the maximum node of the subtree rooted at this node
         */
        RealNode<KK,VV,NN> max();
        /**
         * PRECONDITION: There is at least one node in the tree
         * @return the minimum node of the subtree rooted at this node
         */
        RealNode<KK,VV,NN> min();
    }
    
    /**
     * Class to provide functionality for "null-object" nodes
     * for all varieties of balanced trees refining the enclosing class.
     */
    protected abstract static class NullNode<KK extends Comparable<KK>, VV, NN extends Node<KK, VV, NN>> implements Node<KK, VV, NN> {
        // put() is deferred to the child classes, which will 
        // insert new items into the tree in a way consistent
        // with that variety of balanced tree's constraints.

    	/**
         * No key is contained here
         */
        public boolean containsKey(KK key) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public NN putFixup() {
            return (NN) this;
        }
        /**
         * No key is contained here
         */
        public VV get(KK key) {
            return null;
        }

        // These getters are unsupported.
        // In a way it is "inelegant" that these methods exist in the
        // supertype Node, but they are needed by the rebalancing code
        public NN left() { throw new UnsupportedOperationException(); }
        public NN right() { throw new UnsupportedOperationException(); }
        public KK key() { throw new UnsupportedOperationException(); }

        /**
         * Is this node a null object? Yes.
         */
        public boolean isNull() { return true; }
        
        
        public String toString() {
            return "(:)";
        }
        
        public RealNode<KK, VV, NN> max(){
            throw new UnsupportedOperationException();
        }
        public RealNode<KK, VV, NN> min(){
            throw new UnsupportedOperationException();
        }


    }

    /**
     * Class to provide functionality common to all "real" nodes, that
     * is, nodes that are not null objects. The intent is to provide all
     * functionality for finding keys or where keys are supposed to be,
     * deferring code for verification and rebalancing.
     */
    protected abstract static class RealNode<KK extends Comparable<KK>, VV, NN extends Node<KK, VV, NN>> implements Node<KK, VV, NN>{

        /**
         * The key of the pair at this node.
         */
        protected KK key;

        /**
         * The value of the pair at this node.
         */
        protected VV value;

        /**
         * The left child
         */
        protected NN left;

        /**
         * The right child
         */
        protected NN right;
        
        /**
         * Basic convenience constructor.
         */
        protected RealNode(KK key, VV val, NN left, NN right) {
            this.key = key;
            this.value = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "(" + left + " " + key + " " + right + ")";
        }
        

        /**
         * Insert or overwrite a value for a key.
         * This triggers a fixup.
         * PRECONDITION: This subtree satisfies the constraints
         * of variety of balanced tree modeled by the child class
         * and is part of a larger tree that also satisfies those
         * constraints.
         * POSTCONDITION: The subtree whose root is returned,
         * representing the same information as this subtree 
         * except for the modification indicated by invoking this
         * method, satisfies the constraints except 
         * @return The node at the root of the subtree
         * that results from the insertion and any
         * rebalancing that it triggers.
         */
        public NN put(KK key, VV val) {
        	/*
        	System.out.println("this.key = " + this.key);
        	System.out.println("key = " + key);
        	*/

            int compare = key.compareTo(this.key);
            if (compare < 0)
                left = left.put(key, val);
            else if (compare == 0)
                this.value = val;
            else // if (compare > 0) 
                right = right.put(key, val);

            NN ret = putFixup();
            return ret;
        }
   
        /**
         * Fix this subtree to conform to the constraints of
         * this variety of balanced tree, after a put.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
        public abstract NN putFixup();
         
        // Other map operations straightforward....
        
        public boolean containsKey(KK key) {
            int compare = key.compareTo(this.key);
            if (compare < 0) 
                return left.containsKey(key);
            else if (compare == 0) 
                return true;
            else  // if (compare > 0)
                return right.containsKey(key);
        }

        public VV get(KK key) {
            int compare = key.compareTo(this.key);
            if (compare < 0) 
                return left.get(key);
            else if (compare == 0) 
                return value;
            else  // if (compare > 0)
                return right.get(key);
        }

        // getter method straightforward....
        
        public NN left() { return left; }

        public NN right() { return right; }

        public KK key() { return key; }

        
       

        /**
         * Is this node a null object? No.
         */
        public boolean isNull() {
            return false;
        }
        
        public RealNode<KK, VV, NN> max(){
            if (right.isNull())
                return this;
            else 
                return right.max();
        }
        
        public RealNode<KK, VV, NN> min(){
            if (left.isNull())
                return this;
            else 
                return left.min();
        }
    }

    // ---------- RecursiveBSTMap class proper starts here
    

    /**
     * The root of this tree. Invariant: this is never null;
     * when the tree is empty, this should refer to a "null
     * object" (instance of child class of NullNode)
     */
    protected N root;
    /**
     * The strategy used to verify the that the tree is correct.
     */
    private final VerificationStrategy<K,V,N> vs;
    /**
     * True if the the tree is currently verifying itself. 
     */
    private final boolean verifying;
    
    /**
     * This constructor simply sets up verification. The constructor (of any child class) s
     * hould set root  to a null object.
     * @param vs The verification strategy used to verify the tree
     * @param verifying The tree will only be verified if verifying is true.
     */
    public RecursiveBSTMap(VerificationStrategy<K,V,N> vs, boolean verifying) {
        this.vs = vs;
        this.verifying = verifying;
    }
    /** 
     * Get the maximum element in the map, or null if none exists
     */
    public final K max() {
        return root.isNull() ? null : root.max().key();
    }
    
    /** 
     * Get the minimum element in the map, or null if none exists
     */
    public final K min() {
        return root.isNull() ? null : root.min().key();
    }
    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public final void put(K key, V val) {
        verify();
        // The recursive implementation of put in the
        // nodes returns the root of the transformed
        // tree.
        root = root.put(key, val);
        putCleanup();
        verify();
    }


    /** 
     * A non-final helper method that allows children of RecursiveBSTMap to clean up 
     * after a put without overriding put.
     */
    protected void putCleanup() {}


    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
   public final V get(K key) {
        verify();
        V toReturn =  root.get(key);
        verify();
        return toReturn;
    }
   

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public final boolean containsKey(K key) {
        verify();
        boolean toReturn = root.containsKey(key);
        verify();
        return toReturn;
    }
    
    /**
     * Iterate over the elements of this map "BST" order, which
     * corresponds to a pre-order depth-first traversal.
     */
    public final Iterator<K> iterator() {
        verify();
        // The stack contains the left-link lineage of the 
        // the next node, including the next node itself;
        // the next node is the top element
        final Stack<N> st = new ListStackTopFront<N>();
        for (N current = root; ! current.isNull();
                current = current.left())
            st.push(current);

        return new Iterator<K>() {
            public boolean hasNext() {
                verify();
                boolean toReturn = ! st.isEmpty();
                verify();
                return toReturn;
            }

            public K next() {
                verify();
                if (st.isEmpty())
                    throw new NoSuchElementException();
                else {
                    N nextNode = st.pop();
                    for (N current = nextNode.right(); ! current.isNull(); 
                            current = current.left())
                        st.push(current);
                    verify();
                    return nextNode.key();
                }
            }
            
        };
    }
    
    @Override
    public String toString() {
        return root.toString();
    }
    
    /**
     * Use the current verification strategy to ensure the the tree hold the the rules it is bounded by.
     * Verification can be turned off by making the verification strategy do nothing, or by constructing
     * recursive bst map with debug set to false.
     */
    public  final void verify() {
        if (verifying)
            vs.verify(root);
    }
    
    /**
     * Get the current verification strategy being used.
     */
    public final VerificationStrategy<K,V,N> getVs() {
        return vs;
    }
    
    /**
     * Get whether or not the tree is currently verifying itself.
     */
    public final boolean isVerifying() {
        return verifying;
    }

}
