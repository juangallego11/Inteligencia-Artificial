/**
 *  Represents an state, which corresponds with a cell of the maze.
 */
public class State {
	
	/* An state is a position given by the coordinates (x,y). */
	private int x; // Coordinate x of the cell.
	private int y; // Coordinate y of the cell.
	
	/** Constructor. Receives the pair of coordinates represented by the state. */
	public State(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/** Returns the coordinate x. */
	public int getX(){ 
		return x; 
	}
	
	/** Returns the coordinate y. */
	public int getY(){ 
		return y; 
	}
	
	/** 
	 * Checks if two states are similar. The method overrides the one in the Object class
	 * and is used by some classes in Java. For instance, the method HashSet.contains makes
	 * use of equals.
	 */
	@Override
	public boolean equals(Object anotherState){
		// If the object passed as parameter is not a state, returns false and
		// reports an error
		if (!(anotherState instanceof State)){
			System.out.println("Trying to compare two objects of different classes.");
			return false;
		}
		// If two objects have the same class, compares x and y.
		if(x!=((State)anotherState).getX() || y!=((State) anotherState).getY() ) 
			return false;
		else
			return true;
	}
	
	/** 
	 * Basic hashing function. Overrides the one in Object and is used in classes such
	 * as HashSet.
	 */
	@Override
	public int hashCode(){
		return 1000*x + y;
	}
	
	/** 
	 * Prints the state.
	 */
	public String toString(){
		return "("+x+","+y+")";
	}
	
}
