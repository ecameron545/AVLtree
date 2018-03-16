package impl;

/**
 * BasicRecursiveBSTMap
 * 
 * An unbalanced, naive BST tree. This class simply inherits code
 * from RecursiveBSTMap with dummy implementations for
 * the fixup operation.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class BasicRecursiveBSTMap<K extends Comparable<K>, V> extends RecursiveBSTMap<K, V, BasicRecursiveBSTMap.BasicNode<K, V>> {

    /**
     * Supertype for the Node child classes of this tree map class
     */
    protected interface BasicNode<KK extends Comparable<KK>, VV> extends RecursiveBSTMap.Node<KK, VV, BasicNode<KK, VV>>{ }
    /**
     * Since the tree is not governed by any balancing rules, it is always valid.
     */
    private static class DumbVerification<KK extends Comparable<KK>,VV,NN extends BasicNode<KK,VV>> implements VerificationStrategy<KK,VV,NN> {
        @Override
        public void verify(NN root) {}
    }
    /**
     * Class for real, "non-null" nodes.
     */
    public class BasicRealNode extends RecursiveBSTMap.RealNode<K, V, BasicRecursiveBSTMap.BasicNode<K, V>>implements BasicNode<K, V> {

        public BasicRealNode(K key, V val, BasicNode<K, V> left, BasicNode<K, V> right) {
            super(key, val, left, right);
        }

        protected BasicNode<K, V> self() {
            return this;
        }

        // do nothing
        public BasicNode<K, V> putFixup() {
            return this;
        }

  
    }

    /**
     * Class for null objects for this tree map
     */
    protected class BasicNullNode extends RecursiveBSTMap.NullNode<K, V, BasicRecursiveBSTMap.BasicNode<K, V>> implements  BasicRecursiveBSTMap.BasicNode<K, V>{
        public BasicNode<K, V> put(K key, V val) {
            return new BasicRealNode(key, val, nully, nully);
        }
    }

    // ---------- The BasicRecursiveBSTMap class proper starts here -----------
    // (and ends soon hereafter)
    
    /**
     * A null node object. This is for efficiency to reduce
     * the number of object. This effectively makes BasicNullNode
     * a singleton for this class.
     */
    private BasicNullNode nully;

    /**
     * Basic constructor for an empty map
     */
    public BasicRecursiveBSTMap(boolean debug) {
        // since basic recursive BST map has no rules, its verification strategy checks for nothing
        super(new DumbVerification<K,V,BasicNode<K,V>>(),debug);
        root = nully = new BasicNullNode();
    }
    
    
}
