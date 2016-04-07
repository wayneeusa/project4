import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

    /** Stack to track where the iterator is in the BST*/
    Stack<BSTNode<K>> stack;

    /**
     * Constructs the iterator so that it is initially at the smallest
     * value in the set. Hint: Go to farthest left node and push each node
     * onto the stack while stepping down the BST to get there.
     *
     * @param n the root node of the BST
     */
    public BSTIterator(BSTNode<K> n){
        //TODO
    	stack = new Stack <BSTNode<K>> ();
    	if (n == null){
    		return;
    	}
    	stack.push(n);
    	BSTNode <K> temp = n.getLeftChild();
    	while (temp != null){
    		stack.push(temp);
    		temp = temp.getLeftChild();
    	}
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
        //TODO
    	if (stack.isEmpty() == true){
    		return false;
    	} else {
    		return true;
    	}
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return the next item in the iteration
     * @throws NoSuchElementException if the iterator has no more items
     */
    public K next() {
        //TODO
    	if (stack.isEmpty() == true){
    		throw new NoSuchElementException();
    	}
    	BSTNode <K> temp = stack.pop();
    	BSTNode <K> temp2 = null;
    	if (temp.getRightChild() != null){
    		temp2 = temp.getRightChild();
    		while(temp2 != null){
    			stack.push(temp2);
    			temp2 = temp2.getLeftChild();
    		}
    	}
        return temp.getKey();
    }

    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
