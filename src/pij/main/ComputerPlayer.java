package pij.main;

public class ComputerPlayer extends Player {

	@Override
	public Move turn(Bag bag) {
		// TODO Auto-generated method stub
		draw(bag);	
		Move move = new Move(",,", this);
		return move;
	}

}
