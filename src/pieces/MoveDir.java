package pieces;

/** 
 * Labels to identify this piece's move direction;
 * Each piece moves differently
 */
public enum MoveDir { 
	LEFT, RIGHT, 
	FORWARD, BACKWARD, 
	DIAGONALLY_LEFT_FORWARD, 
	DIAGONALLY_RIGHT_FORWARD, 
	DIAGONALLY_LEFT_BACKWARD, 
	DIAGONALLY_RIGHT_BACKWARD;
}
