package impl;

import impl.RedBlackTreeMap.*;
import impl.LLRedBlackTreeMap.RedRightException;
/**
 * This class's verify methods throws exceptions if the (sub)tree rooted at root violates any of the 
 * left leaning red black tree conditions.
 */
public class LLRBVerify<K extends Comparable<K>,V, N extends RBNode<K,V>> implements VerificationStrategy<K, V, N> {
    
    public void verify(N root) {
        getBlackHeight(root);
    }
    
    /**
     * Calculates the black height of the tree rooted at rooted, while
     * also recursively examining the entire tree to ensure that it 
     * conforms the the LLRB rules.
     * @param root the root of the tree to be examined.
     * @return the black height of the tree rooted at root.
     */
    public int getBlackHeight(RBNode<K,V> root) {
        int rightBlackHeight;
        int leftBlackHeight;
        int addedHeight;
        if (root.isNull()) 
            return 0;
        rightBlackHeight = getBlackHeight(root.right()); 
        leftBlackHeight = getBlackHeight(root.left());
        if (root.isRed()) {
            addedHeight = 0;
            if ((root.right() != null && root.right().isRed()) ||
                    (root.left() != null && root.left().isRed()) ) {
                throw new DoubleRedException("Double Red"); 
            }
        } else {
            if ((root.right() != null && root.right().isRed()))
                throw new RedRightException("Red on the right");
            
            addedHeight = 1;
        }
    
        if (leftBlackHeight != rightBlackHeight)
            throw new InconsistentBlackHeightException(root.toString(),leftBlackHeight,rightBlackHeight);
        
        return Math.max(leftBlackHeight,rightBlackHeight) + addedHeight;

    }

}
