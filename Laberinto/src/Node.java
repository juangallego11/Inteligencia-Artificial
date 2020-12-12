/**
 *  Implements a node of the search tree.     
 */

public class Node implements Comparable<Node>
{
	/* Information of the node. */
	
    private State state;	 		// State that the node corresponds to. 
    private int depth=0; 				// Depth of the tree.
    private double cost; 	 		// Cost of the path from the root to the node. Corresponds to g(n).
    private double heuristic=0;  	// Value of the heuristic function for the node. Correspondos to h(n).		 
    private Node parent;		 	// Reference to the parent node in the search tree. 
    private Action action;   		// Action applied to the parent to obtain the node. 
    
    
    /**  Constructor. Takes as parameter an state and builds the corresponding node.*/
    public Node(int x, int y) 
    {
		state = new State(x,y);
    }
    
    public Node(State state) 
    {
		this.state =  state;
    }
    
    // Access to the fields of the node.
    
    /** Returns the state represented by the nod. */
    public State getState(){ return state; }
    /** Sets the depth of the node. */
    public void setDepth(int depth){ this.depth=depth; }
    /** Returns the depth of the node. */
    public int getDepth() { return depth; }
    
    /** Sets the cost of the node. */
    public void setCost(double cost) {this.cost = cost; }
    /** Returns the cost of the node. */
    public double getCost() { return cost; }

    /** Fixes the value of the heuristic. */
    public void setHeuristic(double heuristic) { this.heuristic = heuristic; }
    /** Returns the heuristic function for the node. */
    public double getHeuristic() { return heuristic; }
    
    /** Returns a reference to the parent node in the tree. */
    public Node getParent() { return parent; }
    /** Fixes the reference to the parent in the tree. */
    void setParent(Node node) {	parent=node; }
	
    /** Returns the action applied to the parent to create the node. */
    public Action getAction() { return action;}
    /** Sets the action applied to the parent to create the node. */ 
    public void setAction(Action action) { this.action = action; }
    
    // Auxiliar functions

	/** 
	 * Checks if two nodes are similar (represent the same state. The method overrides the one in
	 * the class Object so that it is used by the structures provided by Java, as for example HashSet.
	 */
	@Override
	public boolean equals(Object anotherNode){
		// If the object is not a Node, returns false, but reports the error.
		if (!(anotherNode instanceof Node)){
			System.out.println("Trying to compare two objects of different classes.");
			return false;
		}
		// If the object is a node, compares their states.
		return this.state.equals(((Node) anotherNode).getState());
	}

	/** 
	 * Compares the evaluation function for two nodes. Is the method used
	 * by other classes, such as PriorityQueue, to carry out the comparisons. 
	 */
	@Override
	public int compareTo(Node anotherNode){
		double F_thisNode = this.getCost() + this.getHeuristic();
		double F_anotherNode = anotherNode.getCost() + anotherNode.getHeuristic();
		if (F_thisNode < F_anotherNode)
			return -1;
		else if (F_thisNode > F_anotherNode)
			return 1;
		return 0;
	}
    /** 
     *  Prints the node in a String. Determines what prints  System.out.println(node);
     *  It is simple because it is the format used by the debugger.
     */
    public String toString()
    {
    	return("Node("+state.getX()+","+state.getY()+")");
    }
    
    /** Prints all the information of the node. */
    public void print()
    {
    	System.out.println("Node ("+state.getX()+","+state.getY()+"):" +"\n\tdepth:"+depth+" \n\tg:"+cost+"\n\th:"+heuristic+".");
    }


}
