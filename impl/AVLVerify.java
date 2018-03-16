package impl;

import impl.AVLBSTMap.AVLNode;
import impl.AVLBSTMap.ImbalanceException;
/**
 * This class's verify method throws an ImbalanceException if the (sub)tree it is run on
 * is not a valid AVL tree. 
 */
public class AVLVerify<K extends Comparable<K>,V, N extends AVLBSTMap.AVLNode<K,V>> implements VerificationStrategy<K,V,N>{

    @Override
    public void verify(N root){
        getHeight(root);
    }
    /**
     * getHeight returns the height of an avl tree rooted at avlNode. In the process, 
     * it also recursively checks the entire tree to see if it violates the avl property.
     * If there is a violation, it throws an exception that describes the problem.
     * @param avlNode the root of the given tree
     * @return the integer balance of root if it is -1,0,1
     */
    public int getHeight(AVLNode<K, V> avlNode)  {
        if (avlNode.isNull())
            return 0;
        int rightHeight, leftHeight;
        rightHeight = getHeight(avlNode.right());
        leftHeight = getHeight(avlNode.left());
        if (Math.abs(leftHeight-rightHeight) > 1)
            throw new ImbalanceException("Left Height ="+ leftHeight +", Right Height =" + rightHeight);
        int height = Math.max(leftHeight, rightHeight) +1;
        if (height != avlNode.height())
            throw new IgnorantNodeException("The root of: "+ avlNode.toString() + "thinks its height is "+ 
                                            avlNode.height() + ", but it is actually "+height);
        return height;
        
    }

}
