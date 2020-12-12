import java.util.ArrayList;

/** 
 * This class must be extended by all classes which implement a search 
 * algorithm. Descending classes must implement two methods, the one which
 * searches for the path, and the one returning the result.
 */
public abstract class SearchAlgorithm{
	
	/**
	 * Formulation of the problem.
	 */
	protected SearchProblem problem;
	
	/** 
	 * Sets the search problem.
	 */
	public void setProblem(SearchProblem problem){
		this.problem = problem;
	}
	
	/** Number of expanded nodes.*/
	private long expandedNodes; 
    
	/**
	 * Returns the number of expanded nodes.
	 */
    public long getExpandedNodes(){
    	return expandedNodes;
    }
    
	// ---------------------------------------------------------
	// ---------------------------------------------------------
	// These methods must be implemented by the search algorithms.
	
    /**
     * Gets the result.
     */    
    public abstract void searchPath();   
	
    /** 
     * Returns the resulting path as a set of actions.
     */
    public abstract ArrayList<Action> result();
    
   	// ---------------------------------------------------------
    
    
    
	// ---------------------------------------------------------
	// ---------------------------------------------------------   
    // Some useful methods.
    
	/** 
	 * Checks if the node contains the initial state.
	 */
	public boolean isInitialNode(Node node){
		return problem.initialState().equals(node.getState());
	}
	
	/** 
	 * Checks if the node contains the goal state.
	 */
	public boolean isGoalNode(Node nodo){
		return problem.goalState().equals(nodo.getState());
	}
    
	/**
	 *  Return the successors of a given node. It is necessary if besides the states, some other 
	 *  information, such as actions, costs, etc., is needed.
	 *  This function corresponds to EXPAND seen in class. 
	 */
	public ArrayList<Node> getSuccessors(Node node){
	    expandedNodes++;
		ArrayList<Node> successors = new ArrayList<Node>();
		// Obtains the possible actions
		ArrayList<Action> actions = problem.getActions(node.getState());
		// For each action. 
		for (Action action: actions){
			// Generates the state.
			State newState = problem.applyAction(node.getState(), action);
			// Creates the node and fixes the action used
			Node newNode = new Node(newState);
			// Set the parent.
			newNode.setParent(node);
			// Fixes the action used to create the new node.
			newNode.setAction(action);
			// Calculates the cost.
			double costAction = problem.cost(node.getState(), action);
			newNode.setCost(node.getCost()+ costAction);
			// Updates its depth.
			newNode.setDepth(node.getDepth()+1);
			//Adds it to the list.
			successors.add(newNode);
		}
		return successors;
	}
}