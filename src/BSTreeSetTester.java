import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> implements SetTesterADT<K>{

	/** Root of this tree */
	BSTNode<K> root;

	/** Number of items in this tree*/
	int numKeys;

	/**
	 * rebalanceThreshold &lt;= 0 DOES NOT REBALANCE (BSTree).<br>
	 * rebalanceThreshold  &gt;0 rebalances the tree (BSTreeB).
	 */
	int rebalanceThreshold;

	/**
	 * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
	 * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
	 * rebalanceThreshold is non-positive, isBalanced must be true.
	 */
	boolean isBalanced;

	/**
	 * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
	 *
	 * @param rbt the rebalance threshold
	 */
	public BSTreeSetTester(int rbt) {
		//TODO
		root = null;
		numKeys = 0;
		rebalanceThreshold = rbt;
		isBalanced = true;
	}

	/**
	 * Add node to binary search tree. Remember to update the height and
	 * balancedFfactor of each node. Also rebalance as needed based on
	 * rebalanceThreshold.
	 *
	 * @param key the key to add into the BST
	 * @throws IllegalArgumentException if the key is null
	 * @throws DuplicateKeyException if the key is a duplicate
	 */
	public void add(K key) {
		//TODO
		if (key == null){
			throw new IllegalArgumentException();
		}
		root = add(root,key);
		//System.out.println("71 " + root.getKey());
		if (root.getBalanceFactor() >= rebalanceThreshold || (root.getBalanceFactor() * -1) >= rebalanceThreshold){
			System.out.println("enter rebalance");
			isBalanced = false;
			rebalance();
		}
		//displayTree(10);
	}
	private BSTNode <K> add(BSTNode <K> n, K key) {
		if (n == null){
			BSTNode <K> newnode = new BSTNode <K> (key);
			numKeys++;
			//System.out.println(numKeys + " numkeys");
			//displayTree(100);
			return newnode;
		}
		if (key.compareTo(n.getKey()) < 0){
			BSTNode <K> newLeft = add(n.getLeftChild(), key);
			int leftht = height(newLeft);
			newLeft.setHeight(leftht);
			n.setLeftChild(newLeft);
			root.setHeight(leftht + 1);
			root.setBalanceFactor(leftht - height(n.getRightChild()));
			return n;
		} else if (key.compareTo(n.getKey()) > 0){
			BSTNode <K> newRight = add(n.getRightChild(), key);
			int rightht = height(newRight);
			newRight.setHeight(rightht);
			n.setRightChild(newRight);
			root.setHeight(rightht + 1);
			root.setBalanceFactor(height(n.getLeftChild()) - rightht);
			return n;
		} else {
			throw new DuplicateKeyException();
		}
	}
	private int height (BSTNode <K> n){
		if (n == null){
			return 0;
		}
		if (n.getLeftChild() == null && n.getRightChild() == null){
			return 1;
		}
		int leftht = height(n.getLeftChild());
		int rightht = height(n.getRightChild());
		n.setBalanceFactor(leftht - rightht);
		if (leftht > rightht){
			return leftht + 1;
		} else {
			return rightht + 1;
		}
	}

	/**
	 * Rebalances the tree by:
	 * 1. Copying all keys in the BST in sorted order into an array.
	 *    Hint: Use your BSTIterator.
	 * 2. Rebuilding the tree from the sorted array of keys.
	 */
	public void rebalance() {
		K[] keys = (K[]) new Comparable[numKeys];
		//TODO
		Iterator <K> itr = iterator();
		int counter = 0;
		while (itr.hasNext() == true){
  			keys[counter] = itr.next();
			counter++;
		}
		System.out.println(Arrays.toString(keys));
		System.out.println("139");
		int arraysize = numKeys;
		root = null;
		numKeys = 0;
		root = sortedArrayToBST(keys, 0, arraysize - 1);
		isBalanced = true;
	}

	/**
	 * Recursively rebuilds a binary search tree from a sorted array.
	 * Reconstruct the tree in a way similar to how binary search would explore
	 * elements in the sorted array. The middle value in the sorted array will
	 * become the root, the middles of the two remaining halves become the left
	 * and right children of the root, and so on. This can be done recursively.
	 * Remember to update the height and balanceFactor of each node.
	 *
	 * @param keys the sorted array of keys
	 * @param start the first index of the part of the array used
	 * @param stop the last index of the part of the array used
	 * @return root of the new balanced binary search tree
	 */
	private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) {
		//TODO
		//small change
		System.out.println("Starting: Sorted Array");
		if(start == stop){
			//System.out.println("161 rebalance");
			BSTNode<K> n = new BSTNode<K>(keys[start]);
			n.setHeight(1);
			n.setBalanceFactor(0);
			return n;
		}
		else if(start > stop) {
			return null;
		}
		int middle = (start+stop)/2;
		BSTNode<K> BSTroot = new BSTNode<K>(keys[middle]);

		BSTroot.setLeftChild(sortedArrayToBST(keys, start, middle - 1) );

		BSTroot.setRightChild(sortedArrayToBST(keys, middle+1, stop) );

		//Set height as max of child heights + 1
		//****Jonathon - let's fix this line  BSTroot.setHeight(Math.max(BSTroot.getLeftChild().getHeight(),BSTroot.getRightChild().getHeight())+1);
		//Set balance factor as difference of childrens' heights
		//****Jonathon - let's fix this line  BSTroot.setHeight(BSTroot.getLeftChild().getHeight()-BSTroot.getRightChild().getHeight());

		//System.out.println("root return rebalance " + BSTroot.getKey());
		//displayTree(100);
		return BSTroot;
	}

		/*if(start == stop){
			//System.out.println("161 rebalance");
			insert(keys[start]);
			return root;
		}
		else if(start > stop) {
			return null;
		}
		int middle = (start+stop)/2;
		insert(keys[middle]);
		sortedArrayToBST(keys, start, middle - 1);
		sortedArrayToBST(keys, middle+1, stop);
		System.out.println("root return rebalance " + root.getKey());
		displayTree(100);
		return root;*/

	/*private void insert (K key){
		if (key == null){
			throw new IllegalArgumentException();
		}
		root = insert(root,key);
	}
	private BSTNode <K> insert(BSTNode <K> n, K key) {

		private BSTNode <K> insert(BSTNode <K> n, K key) {
			if (n == null){
				BSTNode <K> newnode = new BSTNode <K> (key);
				numKeys++;
				return newnode;
			}
			if (key.compareTo(n.getKey()) < 0){
				BSTNode <K> newLeft = add(n.getLeftChild(), key);
				int leftht = height(newLeft);
				newLeft.setHeight(leftht);
				n.setLeftChild(newLeft);
				root.setHeight(leftht + 1);
				root.setBalanceFactor(leftht - height(n.getRightChild()));
				return n;
			} else if (key.compareTo(n.getKey()) > 0){
				BSTNode <K> newRight = add(n.getRightChild(), key);
				int rightht = height(newRight);
				newRight.setHeight(rightht);
				;
				root.setHeight(rightht + 1);
				root.setBalanceFactor(height(n.getLeftChild()) - rightht);
				return n;
			} else {
				throw new DuplicateKeyException();
			}
		}


}*/
		/*if (n == null){
			BSTNode <K> newnode = new BSTNode <K> (key);
			numKeys++;
			return newnode;
		}
		if (key.compareTo(n.getKey()) < 0){
			BSTNode <K> newLeft = add(n.getLeftChild(), key);
			int leftht = height(newLeft);
			newLeft.setHeight(leftht);
			n.setLeftChild(newLeft);
			root.setHeight(leftht + 1);
			root.setBalanceFactor(leftht - height(n.getRightChild()));
			return n;
		} else if (key.compareTo(n.getKey()) > 0){
			BSTNode <K> newRight = add(n.getRightChild(), key);
			int rightht = height(newRight);
			newRight.setHeight(rightht);
			n.setRightChild(newRight);
			root.setHeight(rightht + 1);
			root.setBalanceFactor(height(n.getLeftChild()) - rightht);
			return n;
		} else {
			throw new DuplicateKeyException();
		}*/



	/**
	 * Returns true iff the key is in the binary search tree.
	 *
	 * @param key the key to search
	 * @return true if the key is in the binary search tree
	 * @throws IllegalArgumentException if key is null
	 */
	public boolean contains(K key) {
		//TODO
		if (root == null){
			return false;
		}
		if (key == null){
			throw new IllegalArgumentException();
		}
		return contains(root, key);
	}
	private boolean contains(BSTNode <K> n, K key){
		if (n == null){
			return false;
		}
		if (n.getKey().equals(key)){
			return true;
		}
		if (key.compareTo(n.getKey()) < 0){
			return contains(n.getLeftChild(), key);
		} else {
			return contains(n.getRightChild(), key);
		}
	}

	/**
	 * Returns the sorted list of keys in the tree that are in the specified
	 * range (inclusive of minValue, exclusive of maxValue). This can be done
	 * recursively.
	 *
	 * @param minValue the minimum value of the desired range (inclusive)
	 * @param maxValue the maximum value of the desired range (exclusive)
	 * @return the sorted list of keys in the specified range
	 * @throws IllegalArgumentException if either minValue or maxValue is
	 * null, or minValue is larger than maxValue
	 */
	public List<K> subSet(K minValue, K maxValue) {
		//TODO
		if (minValue == null || maxValue == null || minValue.compareTo(maxValue) > 0){
			throw new IllegalArgumentException();
		}
		List <K> result = null;
		if (root != null){
			result = subSet(root, minValue, maxValue);
		}
		return result;
	}
	List <K> result = new ArrayList <K> ();
	private List<K> subSet (BSTNode <K> n, K minValue, K maxValue){
		if (n == null){
			return result;
		}
		if (n.getKey().compareTo(minValue) >= 0){
			subSet(n.getLeftChild(), minValue, maxValue);
		}
		if (n.getKey().compareTo(maxValue) < 0) {
			subSet(n.getRightChild(), minValue, maxValue);
		}
		if (n.getKey().compareTo(minValue) >= 0 && n.getKey().compareTo(maxValue) < 0){
			result.add(n.getKey());
		}
		return result;
	}

	/**
	 * Return an iterator for the binary search tree.
	 * @return the iterator
	 */
	public Iterator<K> iterator() {
		//TODO
		return new BSTIterator <K> (root);
	}

	/**
	 * Clears the tree, i.e., removes all the keys in the tree.
	 */
	public void clear() {
		//TODO
		root = null;
		numKeys = 0;
	}

	/**
	 * Returns the number of keys in the tree.
	 *
	 * @return the number of keys
	 */
	public int size() {
		//TODO
		return this.numKeys;
	}

	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 *
	 * @param maxDisplayLevels from the top of the BST that will be displayed
	 */
	public void displayTree(int maxDisplayLevels) {
		if (rebalanceThreshold > 0) {
			System.out.println("---------------------------" +
					"BSTreeBSet Display-------------------------------");
		} else {
			System.out.println("---------------------------" +
					"BSTreeSet Display--------------------------------");
		}
		displayTreeHelper(root, 0, maxDisplayLevels);
	}

	private void displayTreeHelper(BSTNode<K> n, int curDepth,
								   int maxDisplayLevels) {
		if(maxDisplayLevels <= curDepth) return;
		if (n == null)
			return;
		for (int i = 0; i < curDepth; i++) {
			System.out.print("|--");
		}
		System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
				n.getBalanceFactor() + "}");
		displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
		displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
	}
}
