package impl;

/**
 * AVLBSTMap
 * 
 * A BST map using the AVL approach for maintaining a balanced tree. This
 * inherits most of the code for manipulating the BST from RecursiveBSTMap. The
 * method AVLVerify.verify is run on the root of the tree and throws an
 * ImbalanceException if the tree is not a valid AVL tree.
 * 
 * @author Thomas VanDrunen CSCI 345, Wheaton College July 1, 2015
 * @param <K>
 *            The key type
 * @param <V>
 *            The value type
 * @param <N>
 *            A super type of nodes in whatever child class is refining this
 *            class
 */
public class AVLBSTMap<K extends Comparable<K>, V> extends RecursiveBSTMap<K, V, AVLBSTMap.AVLNode<K, V>> {

	public static class ImbalanceException extends RuntimeException {
		/**
		 * To be thrown if a violation of the "AVL" condition is detected, that
		 * is, if there is a node the heights of whose subtrees differ by more
		 * than one.
		 */

		private static final long serialVersionUID = -7611981248750890598L;

		public ImbalanceException(String msg) {
			super(msg);
		}

	}

	protected interface AVLNode<KK extends Comparable<KK>, VV> extends RecursiveBSTMap.Node<KK, VV, AVLNode<KK, VV>> {
		/**
		 * Recompute the attributes of this node and the subtree rooted here
		 * without descending the tree but instead assuming the stored
		 * attributes of the children are correct.
		 */
		void recompute();

		/**
		 * The height of this tree, as stored.
		 */
		int height();

		/**
		 * The total number of associations in the subtree rooted here, as
		 * stored.
		 */
		int size();

		/**
		 * The balance of the subtree rooted here, as stored, expressed as the
		 * difference between the height of the right subtree and the height of
		 * the left subtree. Trees with a higher left subtree have negative
		 * balance, with a higher right subtree have positive balance, and being
		 * perfectly balanced have a 0 balance.
		 */
		int balance();
	}

	/**
	 * Class for real, "non-null" nodes, containing the code for enforcing the
	 * AVL property.
	 */
	private class AVLRealNode extends RecursiveBSTMap.RealNode<K, V, AVLNode<K, V>> implements AVLNode<K, V> {

		/**
		 * Plain constructor.
		 */
		public AVLRealNode(K key, V val, AVLNode<K, V> left, AVLNode<K, V> right) {
			super(key, val, left, right);
			recompute();
		}

		/**
		 * The total number of associations in the subtree rooted here.
		 */
		int size;

		/**
		 * The height of the subtree rooted here (longest distance from here to
		 * any leaf below)
		 */
		int height;

		/**
		 * The difference between the left height and the right height. If that
		 * value is other than -1, 0, or 1, then the tree is in violation. Since
		 * there are only three non-violation values this variable can take on,
		 * this really could be stored in only two bits (the cost of a shave and
		 * a haircut), as Knuth notes in TAOCP 6.2.3 (v. III p. 459). We do use
		 * it here to store violation values to trigger a fix-up, but even in
		 * our case three bits will be plenty.
		 */
		protected int balance;

		/**
		 * Fix this subtree to conform to the constraints of AVL trees.
		 * PRECONDITION: The subtrees rooted at the left and right each satisfy
		 * the AVL constraints. POSTCONDITION: This tree has been modified to
		 * contain the same information but also to satisfy the AVL constraints.
		 * The node on which this method is called, currently the root of this
		 * subtree, might no longer be the root; the root of the modified tree
		 * is returned.
		 * 
		 * @return The root of the tree like this one but satisfying the
		 *         constraints.
		 */
		public AVLNode<K, V> putFixup() {

			// current node
			AVLRealNode replace = this;
			// balance
			int bal = left.height() - right.height();

			// violation on the right of the current node (replace)
			if (bal == -2) {

				// right-left violation
				if (right.balance() > 0) {

					// right child of the current node
					AVLRealNode rChild = (AVLBSTMap<K, V>.AVLRealNode) right;
					// left child of rChild
					AVLRealNode rlChild = (AVLBSTMap<K, V>.AVLRealNode) rChild.left;

					// fix up the right-left imbalance into a right-right
					// imbalance
					rChild.left = rlChild.right();
					rlChild.right = rChild;
					replace.right = rlChild;

					// rlChild is the new right child to replace so thats why I
					// use rlChild
					// rather than rChild when fixing up a right-right violation
					// right after
					// a right-left violation
					replace.right = rlChild.left();
					rlChild.left = replace;
					replace = rlChild;
					replace.right.recompute();
					replace.left.recompute();

				}

				// right-right violation
				else if (right.balance() < 0) {

					// right child to the current node replace
					AVLRealNode rChild = (AVLBSTMap<K, V>.AVLRealNode) right;
					replace.right = rChild.left();
					rChild.left = replace;
					replace = rChild;

					// recompute the left node which use to be replace
					replace.left.recompute();
				}

				// violation on the left of the current node (replace)
			} else if (bal == 2) {

				// left-right violation
				if (left.balance() < 0) {

					// left child of the current node
					AVLRealNode lChild = (AVLBSTMap<K, V>.AVLRealNode) left;
					// right child of lChild
					AVLRealNode lrChild = (AVLBSTMap<K, V>.AVLRealNode) lChild.right();

					// fix up the left-right imbalance into a left-left
					// imbalance
					lChild.right = lrChild.left();
					lrChild.left = lChild;
					replace.left = lrChild;

					// lrChild is the new left child to replace so thats why I
					// use lrChild
					// rather than lChild when fixing up a left-left violation
					// right after
					// a left-right violation
					replace.left = lrChild.right();
					lrChild.right = replace;
					replace = lrChild;
					replace.left.recompute();
					replace.right.recompute();

				}

				// left-left violation
				else if (left.balance() > 0) {

					// left child to the current node replace
					AVLRealNode lChild = (AVLBSTMap<K, V>.AVLRealNode) left;
					replace.left = lChild.right();
					lChild.right = replace;
					replace = lChild;

					// recompute the right node which use to be replace
					replace.right.recompute();
				}
			}

			// recompute and return
			replace.recompute();
			return replace;
		}

		/**
		 * Recompute the attributes of this node and the subtree rooted here
		 * without descending the tree but instead assuming the stored
		 * attributes of the children are correct.
		 */
		public void recompute() {
			int leftHeight = left.height();
			int rightHeight = right.height();
			balance = leftHeight - rightHeight;
			size = left.size() + right.size() + 1;
			height = Math.max(leftHeight, rightHeight) + 1;
		}

		// getter methods for attributes

		public int height() {
			return height;
		}

		public int size() {
			return size;
		}

		public int balance() {
			return balance;
		}

	}

	/**
	 * Class for null objects for this tree map---mainly do-nothing except that
	 * the put method makes and returns a new real node.
	 */
	protected class AVLNullNode extends RecursiveBSTMap.NullNode<K, V, AVLNode<K, V>> implements AVLNode<K, V> {

		public AVLNode<K, V> put(K key, V val) {
			return realNodeFactory(key, val, nully, nully);
		}

		public void recompute() {
		}

		public int height() {
			return 0;
		}

		public int size() {
			return 0;
		}

		public int balance() {
			return 0;
		}

	}

	/**
	 * A null node object. This is for efficiency to reduce the number of
	 * object. This effectively makes AVLNullNode a singleton for this class.
	 */
	private AVLNullNode nully;

	/**
	 * Basic constructor for an empty map
	 */
	public AVLBSTMap(boolean verifying) {
		super(new AVLVerify<K, V, AVLNode<K, V>>(), verifying);
		root = nully = new AVLNullNode();
	}

	/**
	 * Factory method for making new real nodes, used by the code in the parent
	 * class which does not have direct access to the class AVLRealNode defined
	 * here.
	 */
	protected AVLNode<K, V> realNodeFactory(K key, V val, AVLNode<K, V> left, AVLNode<K, V> right) {
		return new AVLRealNode(key, val, left, right);
	}

}
