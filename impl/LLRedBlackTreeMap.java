package impl;


/**
 * LLRedBlackTreeMap
 * 
 * A BST map using the left-leaning red-black approach for
 * maintaining a balanced tree. This inherits most of the code for
 * manipulating the BST from RecursiveBSTMap. The method LLRBVerify.verify
 * is run on the root of the tree and throws and exception if the the 
 * tree violates any of the left leaning red black tree conditions.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 2, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class LLRedBlackTreeMap<K extends Comparable<K>, V> extends RedBlackTreeMap<K, V> {


    /**
     * Exception to indicate that a red node is a right child.
     */
    public static class RedRightException extends RuntimeException {

        private static final long serialVersionUID = 3766304873228252316L;

        public RedRightException(String msg) { super(msg); }
    }
    
    /**
     * Class for real, "non-null" nodes, containing the code for
     * fixing up the tree when left-leaning red-black tree properties are violated.
     */
   private class LLRBRealNode extends impl.RedBlackTreeMap<K,V>.RBRealNode{

       /**
        * Plain constructor
        */
        public LLRBRealNode(K key, V val,
                RBNode<K,V> left,
                RBNode<K, V> right) {
            super(key, val, left, right);
        }

        /**
         * Fix this subtree to conform to the constraints of RB trees.
         * PRECONDITION: left and right subtrees are left-leaning red-black trees
         * except that (1) their roots (left and right themselves) may be red,
         * and (2) at most one of them may have a double red that involves
         * their root. Exception (2) would happen only if this node is
         * black.
         * POSTCONDITION: This tree has been modified to contain the 
         * same information as before but also to satisfy the LL RB 
         * constraints, with the exception that the root of
         * this subtree may be red and, if so, it may have a red child (thus
         * it is possible that on exit this subtree may have a double red violation,
         * but only in the case of a red root with a red left child). 
         * The node on which this method is called,
         * currently the root of this subtree, might no longer be
         * the root; the root of the modified tree is returned.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
        public RBNode<K, V> putFixup() {
             throw new UnsupportedOperationException();
                
        }        

        
        
        // ------------------------------------------------
        // The following two methods are suggested helper methods
        // (which you would have to write) for fixup().
        // These will be (nearly) identical to those you write
        // for traditional RB trees, the only difference being
        // the types of the nodes.
        // -----------------------------------------------
       
        /**
         * Rotate this tree to the left.
         * @return The node that is the new root
         */
        private RBNode<K, V> rotateLeft() {
             throw new UnsupportedOperationException();
        }
        
        /**
         * Rotate this tree to the right.
         * @return The node that is the new root
         */
       private RBNode<K, V> rotateRight() {
            throw new UnsupportedOperationException();
        }
   }
   
   public LLRedBlackTreeMap(boolean verifying) {
        super(new LLRBVerify<K,V,RBNode<K,V>>(),verifying);
    }
    
   /**
    * Factory method for making new real nodes, used by the
    * code in the parent class which does not have direct access
    * to the class LLRBRealNode defined here.
    */
    protected RBNode<K, V> realNodeFactory(K key,
            V val, RBNode<K, V> left,
            RBNode<K, V> right) {
        return new LLRBRealNode(key, val, left, right);
    }

    
    
}
