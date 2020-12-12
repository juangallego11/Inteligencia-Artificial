import com.sun.jndi.toolkit.dir.SearchFilter;
import java.util.ArrayList;

/** 
 * This interface must be implemented by all classes which define a search 
 * problem. It allows providing the complete formulation.
 */
 
public interface SearchProblem{
	
    /**
     *  Returns the initial state of the problem.
     */
    public State initialState();
    
    /** 
     * Returns the goal state of the problem.
     */
    public State goalState();
    
    /** 
     * Returns the state resulting of applying an action to another state.
     */
    public State applyAction(State state, Action action);
   
    /**
     * Returns the set of actions that can be applied to a certain state.
     */
    public ArrayList<Action> getActions(State state);
    
    /** 
     * Returns the cost of applying an action over a state.
     */
    public double cost(State state, Action action);
    
    
    /** 
     * Checks if a path is valid.
     */
    public boolean checkPath(ArrayList<Action> solution);
    
}
