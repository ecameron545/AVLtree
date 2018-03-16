package impl;

public interface VerificationStrategy<K extends Comparable<K>,V,N extends RecursiveBSTMap.Node<K, V, ? super N>> {
    /**  
     * @throws ImbalanceException if the tree is imbalanced
     */
        public void verify(N root);

}
