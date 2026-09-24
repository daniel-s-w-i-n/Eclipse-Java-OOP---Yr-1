package yesp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class GridButton extends JButton{

	Color drawColour; 
	Color borderColour;
	int place;
	int colour;
	int borderSize; 

	public GridButton( int width, int height,int pplace,int pcolour) 
	//place to see if there is a counter placed and colour for which is placed 
		{
			borderSize = 1;
			drawColour = Color.green;
			borderColour = Color.black;
			place = pplace;
			colour = pcolour;
			setMinimumSize( new Dimension(width, height) );
			setPreferredSize( new Dimension(width, height) );
		}

	public GridButton( int width, int height)
		{
		//so that only height and width can be specified for the setup
			this( width, height, 0, 0);
		}

	public Color getDrawColor()
		{
			return drawColour;
		}
	

	public void setDrawColor(Color drawColor)
		{
			this.drawColour = drawColor;
		}

	public Color getBorderColor()
		{
			return borderColour;
		}
		
	public void setBorderColor(Color borderColor)
		{
			this.borderColour = borderColor;
		}

	public int getBorderSize()
		{
			return borderSize;
		}

	public void setBorderSize(int borderSize)
		{
			this.borderSize = borderSize;
		}

	protected void paintComponent(Graphics g)
		{
			//super.paintComponent(arg0);
		if ( borderColour != null )
			{
				g.setColor(borderColour);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		if ( drawColour != null )
			{
				g.setColor(drawColour);
				g.fillRect(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
		if (place == 1) {
			drawOval(g);
		}
		
		}
	
	public void drawOval(Graphics g)
	{
		g.setColor(borderColour);
		if (colour == 1)
		{
			g.setColor(Color.white);
			g.drawOval(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
			g.setColor(Color.black);
			g.fillOval(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
		else
		{
			
			g.drawOval(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
			g.setColor(Color.white);
			g.fillOval(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
	}
}


