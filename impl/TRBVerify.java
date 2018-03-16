package impl;

import impl.RedBlackTreeMap.*;
/**
 * This class's verify methods throws exceptions if the (sub)tree rooted at root violates any of the 
 * red black tree conditions.
 */
public class TRBVerify<K extends Comparable<K>,V, N extends RBNode<K,V>> implements VerificationStrategy<K, V,N> {

    
    public void verify(N root) {
        getBlackHeight(root);
    }
    
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
            if (root.right().isRed() ||root.left().isRed() ) {
                throw new DoubleRedException("Double Red"); 
            }
        } else {
            addedHeight = 1;
        }
        if (leftBlackHeight != rightBlackHeight)
            throw new InconsistentBlackHeightException("Inconsistent Black Height: "+ root.toString(),leftBlackHeight,rightBlackHeight);
        int height = Math.max(leftBlackHeight,rightBlackHeight) + addedHeight;
        if (height != root.blackHeight())
            throw new IgnorantNodeException("The root of: "+root.toString()+ 
                    " thinks its black height is "+root.blackHeight() +",but it is actually "+height);
        return height;

    }


}
