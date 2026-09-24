package yesp;

import yesp.IController;
import yesp.IModel;
import yesp.IView;

public class ReversiController2 implements IController{

	IModel model;
	IView view;
	
	int playerNum = 1;
	

	@Override
	public void initialise(IModel model, IView view) {
		
			this.model = model;
			this.view = view;
	}


	@Override
	public void startup() {
				int width = model.getBoardWidth();
				int height = model.getBoardHeight();
				for ( int x = 0 ; x < width ; x++ )
					for ( int y = 0 ; y < height ; y++ )
						model.setBoardContents(x, y, 0);
					
				model.setBoardContents(width/2 -1, height/2, 2);
				model.setBoardContents(width/2, height/2, 1);
				model.setBoardContents(width/2, height/2-1, 2);
				model.setBoardContents(width/2-1, height/2-1, 1);
								
				playerNum = 1;
				view.feedbackToUser(1, "White player – choose where to put your piece");
				view.feedbackToUser(2, "Black player – not your turn");
				
				view.refreshView();
	}


	@Override
	public void update() {
		
				
				boolean finished = true;
				int moveAvailable = 0;
				
				for ( int x = 0 ; x < model.getBoardWidth() ; x++ )
				{
					for ( int y = 0 ; y < model.getBoardHeight() ; y++ ) {
						if ( model.getBoardContents(x, y) == 0 )
						{	
							if (playerNum ==1)
							{
								if (checkMove(x,y,2,1) != 0);
								moveAvailable += 1;
							}
							else
							{
								if (checkMove(x,y,1,1) != 0);
								moveAvailable += 1;
							}
							
						}
					}
				}
				
				if (moveAvailable != 0)
				{
					finished = false; // There is an empty square with a move
				}
				else {
					for ( int x = 0 ; x < model.getBoardWidth() ; x++ )
					{
						for ( int y = 0 ; y < model.getBoardHeight() ; y++ ) {
							if ( model.getBoardContents(x, y) == 0 )
							{	
								if (playerNum ==1)
								{
									if (checkMove(x,y,1,1) != 0);
									moveAvailable += 1;
								}
								else
								{
									if (checkMove(x,y,2,1) != 0);
									moveAvailable += 1;
								}
								
							}
						}
					}
					if (moveAvailable != 0)
					{
						finished = false; // There is an empty square with a move
						if (playerNum ==1)
						{
							playerNum = 2;
						}
						else
						{
							playerNum = 1;
						}
						
					}
					else
					{
						finished = true;
						view.feedbackToUser(1, "No Spaces left");
						view.feedbackToUser(2, "No spaces left");
					}
				}
				
				
				
				model.setFinished(finished);
				
				if (finished == true){
					int playerColour;
					int amountW=0;
					int amountB=0;
					for (int i=0;i<8;i++)
					{
						for (int j=0;j<8;j++)
						{
							playerColour = model.getBoardContents(i,j);
							if (playerColour == 1)
							{
								amountB+=1;
							}
							else
							{
								amountW+=1;
							}
							
						}
					}
					if (amountB>amountW)
						{
					
						
						view.feedbackToUser(1, "Black won. Black " +amountB +" to White "+amountW+". Reset the game to replay.");
						view.feedbackToUser(2, "Black won. Black " +amountB +" to White "+amountW+". Reset the game to replay.");
						}
					else if (amountB==amountW)
					{
						
						view.feedbackToUser(1, "Draw. Both players ended with " +amountW+" pieces. Reset game to replay.");
						view.feedbackToUser(2, "Draw. Both players ended with "+amountB+" pieces. Reset game to replay.");
					}
					else
					{
						view.feedbackToUser(1, "White won. White " +amountW +" to Black "+amountB+". Reset the game to replay.");
						view.feedbackToUser(2, "White won. White " +amountW+" to Black "+amountB+". Reset the game to replay.");
					}
				
					view.refreshView();
				}
	}


	@Override
	public void squareSelected(int player, int x, int y) {
		// The finished flag never gets set by this controller, but the SimpleTestModel could set it
		if ( model.hasFinished() )
		{
			view.feedbackToUser(1, "Somehow the game has finished!" );
			view.feedbackToUser(2, "Somehow the game has finished!" );
			return; // Don't do the set board contents
		}
		
		// check if move can be made
		if (player != playerNum)
		{
			if(model.getBoardContents(x, y) == 0 && checkMove(x,y,player,0) > 0)
			{
				model.setBoardContents(x, y, player);
				if (playerNum ==1)
				{
					view.feedbackToUser(2, "Black player – choose where to put your piece");
					view.feedbackToUser(1, "White player – not your turn");
					playerNum = 2;
				}
				else
				{
					view.feedbackToUser(1, "White player – choose where to put your piece");
					view.feedbackToUser(2, "Black player – not your turn");
					playerNum = 1;
				}
				view.refreshView();
				update();
			}
		}
			else if (player == playerNum && model.hasFinished() == false)
			{
				if (playerNum ==1)
				{
					view.feedbackToUser(2, "It is not your turn!");
				}
				else
				{
					view.feedbackToUser(1, "It is not your turn!");
				}
					
			}
			
			
		
		
		
	}
	
		
	public int checkMove(int x,int y,int player,int AI) {
		//checks to see if there can be a move made
		int changed=0;
		
		if (x+1<8)
		{
			if (model.getBoardContents(x+1,y) != player && model.getBoardContents(x+1,y) != 0)
				{
					if(checkDown(x,y,player,0,AI) !=0) {
						changed += checkDown(x,y,player,1,AI);
					}
				}
		}
		
		if (x-1>=0)
		{
			if (model.getBoardContents(x-1,y) != player && model.getBoardContents(x-1,y) != 0)
				{
					if(checkUp(x,y,player,0,AI) !=0 ) {
						changed += checkUp(x,y,player,1,AI);
					}
				}
		}
		
		if (y+1<8)
		{
			if (model.getBoardContents(x,y+1) != player && model.getBoardContents(x,y+1) != 0)
			{
				if(checkRight(x,y,player,0,AI)!=0 ) {
					changed += checkRight(x,y,player,1,AI);
				}
			}
		}
		
		if (y-1>=0)
		{
			if (model.getBoardContents(x,y-1) != player && model.getBoardContents(x,y-1) != 0)
			{
				if(checkLeft(x,y,player,0,AI)!=0 ) {
					changed += checkLeft(x,y,player,1,AI);
				}
			}
		}
		
		if (y+1<8 && x+1<8)
		{
			if (model.getBoardContents(x+1,y+1) != player && model.getBoardContents(x+1,y+1) != 0)
			{
				if(checkrd(x,y,player,0,AI)!=0 ) {
					changed += checkrd(x,y,player,1,AI);
				}
			}
		}
		
		if (y+1<8 && x-1>=0)
		{
			if (model.getBoardContents(x-1,y+1) != player && model.getBoardContents(x-1,y+1) != 0)
			{
				if(checkld(x,y,player,0,AI)!=0) {
					changed += checkld(x,y,player,1,AI);
				}
			}
		}
		
		if (y-1>=0 && x+1<8)
		{
			if (model.getBoardContents(x+1,y-1) != player && model.getBoardContents(x+1,y-1) != 0)
			{
				if(checkru(x,y,player,0,AI)!=0 ) {
					changed += checkru(x,y,player,1,AI);
				}
			}
		}
		
		if (y-1>=0 && x-1>=0)
		{
			if (model.getBoardContents(x-1,y-1) != player && model.getBoardContents(x-1,y-1) != 0)
			{
				if(checklu(x,y,player,0,AI)!=0 ) {
					changed += checklu(x,y,player,1,AI);
				}
			}
		}
		
		return changed;
		
	}
		
	public int checkDown(int x,int y,int player,int print,int test) {
		int numOfSpaces=0;
		int i;
		for (i=1;i<8;i++)
		{
		if (x+i<8)
			{
			if (print == 1 && test == 0)
			{
				if (model.getBoardContents(x+i,y) != player)
				{
				model.setBoardContents(x+i, y, player);
				}
				else
				{
					i = 20;
				}
			}
			else {
				if (model.getBoardContents(x+i,y) == 0)
				{
				return 0;
				}
				else
				{
					numOfSpaces+=1;
				}
				if (model.getBoardContents(x+i,y) == player)
				{
					return numOfSpaces; //////////////// or this one (look at next comment)
				}
			}
			}
				
		}
		if (print == 1) {
			return 1;       ///////change this to return how many taken for the AI
		}
		return 0;
		
	}
	public int checkUp(int x,int y,int player,int print,int test) {
		
		int numOfSpaces = 0;
		int i;
		for (i=1;i<8;i++)
		{
		if (x-i>=0)
			{
			if (print == 1 && test == 0)
			{
				if (model.getBoardContents(x-i,y) != player)
				{
				model.setBoardContents(x-i, y, player);
				}
				else
				{
					i = 20;
				}
			}
			else {
				if (model.getBoardContents(x-i,y) == 0)
				{
				return 0;
				}
				else
				{
					numOfSpaces+=1;
				}
				if (model.getBoardContents(x-i,y) == player)
				{
					return numOfSpaces;
				}
			}
			}
				
		}
		if (print ==1) {
			return 1;
		}
		return 0;
		
		
	}
	public int checkLeft(int x,int y,int player,int print,int test) {
		
		int numOfSpaces=0;
		int i;
		for (i=1;i<8;i++)
		{
		if (y-i>=0)
			{
			if (print == 1 && test == 0)
			{
				if (model.getBoardContents(x,y-i) != player)
				{
				model.setBoardContents(x, y-i, player);
				}
				else
				{
					i = 20;
				}
			}
			else {
				if (model.getBoardContents(x,y-i) == 0)
				{
				return 0;
				}
				else
				{
					numOfSpaces+=1;
				}
				if (model.getBoardContents(x,y-i) == player)
				{
					return numOfSpaces;
				}
			}
			}
				
		}
		if (print ==1) {
			return 1;
		}
		return 0;
		
	}
	public int checkRight(int x,int y,int player,int print,int test) {
		
		int numOfSpaces=0;
		int i;
		for (i=1;i<8;i++)
		{
		if (y+i<8)
			{
			if (print == 1 && test == 0)
			{
				if (model.getBoardContents(x,y+i) != player)
				{
				model.setBoardContents(x, y+i, player);
				}
				else
				{
					i = 20;
				}
			}
			else {
				if (model.getBoardContents(x,y+i) == 0)
				{
				return 0;
				}
				else
				{
					numOfSpaces+=1;
				}
				if (model.getBoardContents(x,y+i) == player)
				{
					return numOfSpaces;
				}
			}
			}
				
		}
		if (print ==1) {
			return 1;
		}
		return 0;
		
	}
	
	
	public int checkrd(int x,int y,int player,int print,int test) {

		int numOfSpaces=0;
		int i;
		for (i=1;i<8;i++)
		{
					if (x+i<8) {
						if (y+i<8)
						{
							if (print == 1 && test == 0)
							{
								if (model.getBoardContents(x+i,y+i) != player)
								{
									model.setBoardContents(x+i, y+i, player);
								}
								else
								{
									i = 20;
								}
							}
							else {
								if (model.getBoardContents(x+i,y+i) == 0)
								{
									return 0;
								}
								else
								{
									numOfSpaces+=1;
								}
								if (model.getBoardContents(x+i,y+i) == player)
								{
									return numOfSpaces;
								}
							}
						}
					}
				}
		
		if (print ==1) {
			return 1;
		}

		
		return 0;
		
	}
	
	public int checkru(int x,int y,int player,int print,int test) {
		
		int numOfSpaces =0;
		int i;
		for (i=1;i<8;i++)
		{
			if (x+i<8) {
						if (y-i>=0)
						{
							if (print == 1 && test == 0)
							{
								if (model.getBoardContents(x+i,y-i) != player)
								{
									model.setBoardContents(x+i, y-i, player);
								}
								else
								{
									i = 20;
								}
							}
							else {
								if (model.getBoardContents(x+i,y-i) == 0)
								{
									return 0;
								}
								else
								{
									numOfSpaces+=1;
								}
								if (model.getBoardContents(x+i,y-i) == player)
								{
									return numOfSpaces;
								}
							}
						}
					}
				
				
			}
		
		
		if (print ==1) {
			return 1;
		}
		return 0;
		
	}
	public int checklu(int x,int y,int player,int print,int test) {
		
		int numOfSpaces = 0;
		int i;
		for (i=1;i<8;i++)
		{
			if (x-i>=0) {
						if (y-i>=0)
						{
							if (print == 1 && test == 0)
							{
								if (model.getBoardContents(x-i,y-i) != player)
								{
									model.setBoardContents(x-i, y-i, player);
								}
								else
								{
									i = 20;
								}
							}
							else {
								if (model.getBoardContents(x-i,y-i) == 0)
								{
									return 0;
								}
								else
								{
									numOfSpaces+=1;
								}
								if (model.getBoardContents(x-i,y-i) == player)
								{
									return numOfSpaces;
								}
							}
						}
					}
				
				
			}
		
		
		if (print ==1) {
			return 1;
		}
		return 0;
		
		
	}
	public int checkld(int x,int y,int player,int print,int test) {

		int numOfSpaces = 0 ;
		int i;
		for (i=1;i<8;i++)
		{
			if (y+i<8) {
						if (x-i>=0)
						{
							if (print == 1 && test == 0)
							{
								if (model.getBoardContents(x-i,y+i) != player)
								{
									model.setBoardContents(x-i, y+i, player);
								}
								else
								{
									i = 20;
								}
							}
							else {
								if (model.getBoardContents(x-i,y+i) == 0)
								{
									return 0;
								}
								else
								{
									numOfSpaces+=1;
								}
								if (model.getBoardContents(x-i,y+i) == player)
								{
									return numOfSpaces;
								}
							}
						}
					}
				
				
			}
		
		
		if (print ==1) {
			return 1;
		}
		return 0;
		
	}
	


	@Override
	public void doAutomatedMove(int player) {
		
		//create a copy of the model and for each square run makemove() then count which one has the most and make that mov
		
		int x =0;
		int y =0;
		int num = 0;
		int pastnum = 0;
		int numx = 0;
		int numy = 0;
		
		if (player != playerNum)
		{
		for (x=0;x<8;x++)
		{
			for (y=0;y<8;y++)
			{
				num = checkMove(x,y,player,1);
				if (num>=pastnum)
				{
					if (model.getBoardContents(x,y) == 0)
					{
						numx = x;
						numy = y;
						pastnum = num;
					}
							
				}
			}
		}
		if (model.getBoardContents(numx,numy) != 0 && numx == 0 && numy == 0 )
		{
			model.setFinished(true);
			
		}
		else{
			
			checkMove(numx,numy,player,0);//makes the move
			model.setBoardContents(numx, numy, player);
			if (playerNum ==1)
			{
				view.feedbackToUser(2, "Black player – choose where to put your piece");
				view.feedbackToUser(1, "White player – not your turn");
				playerNum = 2;
			}
			else
			{
				view.feedbackToUser(1, "White player – choose where to put your piece");
				view.feedbackToUser(2, "Black player – not your turn");
				playerNum = 1;
			}
			view.refreshView();
			update();
			}
		}
	}
}


