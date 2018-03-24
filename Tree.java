
import java.lang.Math;
import java.util.ArrayList;

public class Tree<T>
{
	
	 /* Instance variables
     *
     * size holds the number of elements currently in the tree
     * 
     * k holds the number of children a node can have
     * 
     * root holds the root node of the tree
     */
    private int size, k;
    private Node<T> root;
	private Node<T> last;
	private ArrayList<Node<T>> input;

    /*
     * Constructor for the tree
     *
     * Initialize the instance variables to their default values
     * 
     * The default value for k is 2, meaning it is a binary tree
     */
    public Tree()
    {
		this.size = 0;
		this.k = 2;
    }

    /*
     * Constructor for the tree
     *
     * k - int; the number of children a node can have
     * 
     * Note: If k = 2, it is considered a binary tree
     * 
     * Note: k will always be an integer greater than or equal to 2.
     *       You do not have to consider edge cases where an invalid
     *       value for k is passed as an input.
     */
    public Tree(int k)
    {
        this.size= 0;
		this.k = k;
    }


    /*
     * insert
     *
     * Insert the data into the next available position
     * 
     * data - T; the data to be inserted
     * 
     */
    public void insert(T data)
    {
		if(this.size == 0){
				input = new ArrayList<Node<T>>();
				input.add(new Node<T>(data, null, k));
				this.root = input.get(0);
				this.last = input.get(0);
		}else{
			int lastIdx = input.size()-1;
			int pIdx = lastIdx/this.k;
			Node<T> p = input.get(pIdx);
			p.addChild(data);
			
			Node<T> c = p.getChildren().get(p.getChildren().size()-1);
			input.add(c);
			this.last = c;
		}
		this.size++;
	}

    /*
     * remove
     *
     * Remove the "last" node in the tree
     * 
     * Note: The tree must always be complete, so you this method should
     *       remove the right most node in the lowest level of the tree.
     */
    public void remove()
    {
		Node<T> del = this.last;
		if (del == this.root){
			this.root = null;
			this.last = null;
			input.remove(0);
		}else{
			Node<T> delp = del.getParent();
			del.clearParent();
			delp.removeChild();
			int idx = input.size()-1;
			input.remove(idx);
			this.last = input.get(input.size()-1);
		}
		this.size--;
	}

    /*
     * preorder
     *
     * Return an arraylist of the nodes in the tree in preorder traversal order
     * 
     * Note: This should be implemented for all values of k.
     * 
     * Note: If the tree is empty, return null.
     */
    public ArrayList<Node<T>> preorder()
    {
		if(this.size==0) return null;
	    ArrayList<Node<T>> result = new ArrayList<Node<T>>();
		ArrayList<Node<T>> templist = new ArrayList<Node<T>>();
		templist.add(this.root);
		while(templist.size()!= 0){
			Node<T> temp = templist.remove(templist.size()-1);
			result.add(temp);
			
			if(temp.getChildren()!=null){
				for(int i=0; i<temp.getChildren().size(); i++){
					templist.add(temp.getChildren().get(temp.getChildren().size()-(i+1)));
				}
			}
		}
		return result;
    }
	
    /*
     * postorder
     *
     * Return an arraylist of the nodes in the tree in postorder traversal order
     * 
     * Note: This should be implemented for all values of k.
     * 
     * Note: If the tree is empty, return null.
     */
    public ArrayList<Node<T>> postorder()
    {
        if(this.size==0) return null;
		ArrayList<Node<T>> result = subPostOrder(this.root);
		return result;
    }
	
	public ArrayList<Node<T>> subPostOrder(Node<T> n){
		ArrayList<Node<T>> r = new ArrayList<Node<T>>();
		if(n == null){
			return r;
		}
		for(int i = 0; i<n.getChildren().size(); i++){
			r.addAll(subPostOrder(n.getChildren().get(i)));
		}
		r.add(n);
		return r;
	}

    /*
     * inorder
     *
     * Return an arraylist of the nodes in the tree in inorder traversal order
     * 
     * Note: This should be implemented only for binary trees. If the
     *       tree is not a binary tree, you should return null.
     * 
     * Note: If the tree is empty, return null.
     */
    public ArrayList<Node<T>> inorder()
    {
        if(this.k!=2 || this.size == 0)return null;
		ArrayList<Node<T>> result = subInorder(this.root);
		return result;
    }
	
	public ArrayList<Node<T>> subInorder(Node<T> n){
		ArrayList<Node<T>> r = new ArrayList<Node<T>>();
		if(n == null) return r;
		else{
			if(n.getChildren().size() != 0){
				r.addAll(subInorder(n.getChildren().get(0)));
			}
			r.add(n);
			if(n.getChildren().size() ==2){
				r.addAll(subInorder(n.getChildren().get(1)));
			}
			return r;
		}
		
	}

    /*
     * convertToArrayList
     *
     * Return an ArrayList representing the tree.
     * 
     * Note: See page 19 of Lecture 5 for information.
     * 
     * Note: This should be implemented for all values of k. For example,
     *       the children of a node at index i in a k-ary tree should
     *       be stored from index k*i+1 to index k*i+k. The root should be
     *       at index 0.
     * 
     * Note: If the tree is empty, return null.
     * 
     */
    public ArrayList<Node<T>> convertToArrayList()
    {
		if (this.size == 0) return null;
		else return input;
    }

    /*
     * getSize
     *
     * Return the number of elements in the tree
     */
    public int getSize()
    {
        return this.size;
    }

    /*
     * clear
     *
     * Clear the tree
     */
    public void clear()
    {
		while(this.size > 0){
			remove();
		}
    }

    /*
     * contains
     * 
     * data - T; the data to be searched for
     *
     * Return true if there is a node in the tree with the specified data and
     * false otherwise.
     */
    public boolean contains(T data)
    {
		boolean result = false;
		for(int i = 0; i < input.size(); i++){
			if(input.get(i).getData() ==  data) result = true;
		}
		return result;
    }

    /*
     * getDepth
     *
     * Return the number of levels in the tree.
     * 
     * Note: The root node counts as one level. The only tree with depth
     *       equal to 0 is the empty tree.
     */
    public int getDepth()
    {	
		if(this.size ==0)return 0;
		Node<T> counter = this.last;
		int count = 0;
		while(counter != this.root){
			counter = counter.getParent();
			count++;
		}
        return count+1;
    }

    /*
     * isPerfect
     *
     * Return true if the tree is currently perfect and false otherwise.
     * 
     * Note: A perfect tree is a complete binary tree where all of the levels
     *       are full. In other words, inserting a node into this tree would
     *       force it to begin another level.
     * 
     * Note: The empty tree is perfect.
     */
    public boolean isPerfect()
    {
        if(this.size ==0) return true;
		int num = this.size;
		int temp = 1;
		boolean result = false;
		for( int i = k; temp<=num; i*=k){
			if(temp == num){
				result = true;
			}
			temp+=i;
		}
		return result;
		
    }

    /*
     * getLast
     *
     * Return the right most node in the lowest level of the tree
     * 
     * Note: If the tree is empty, return null.
     */   
    public Node<T> getLast()
    {
        return this.last;
    }

    /*
     * getRoot
     *
     * Return the root of the tree
     */   
    public Node<T> getRoot()
    {
        return this.root;
    }

    /*
    * You should NOT change anything below this line.
    * 
    * Pay close attention to what has been implemented already and
    * what you need to implement on your own (in the Tree class).
    */
    public class Node<T>
    {
        private T data;
        private Node<T> parent;
        private ArrayList<Node<T>> children;
        //This arraylist contains the children of the node in order from left to right

        public Node(T data, Node<T> parent, int k)
        {
            this.parent = parent;
            this.data = data;
            children = new ArrayList<Node<T>>(k);
        }

        public Node<T> getParent()
        {
            return this.parent;
        }

        public T getData()
        {
            return this.data;
        }

        public ArrayList<Node<T>> getChildren()
        {
            return children;
        }
        
        /*
         * This will append to the end of the children arraylist.
         * You need to perform the bounds checks yourself for when
         * the node has the maximum amount of children.
         */
        public void addChild(T data)
        {
            children.add(new Node<T>(data, this, k));
        }

        /*
         * This will remove the right most child of the current node,
         * if it exists.
         */
        public void removeChild()
        {
            if(children.size() > 0)
                children.remove(children.size()-1);
        }

        public void setParent(Node<T> n)
        {
            parent = n;
        }

        public void clearParent()
        {
            parent = null;
        }
    }
}