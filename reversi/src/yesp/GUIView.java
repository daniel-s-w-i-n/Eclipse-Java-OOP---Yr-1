package yesp;

import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import yesp.IView;

import javax.swing.JButton;

/*think have to do get from model then put in the boxes 
 * would have to change the way it gets the panel coords 
 * however could bypass this with the refresh view or whatever so it builds it with the oval*/


public class GUIView implements IView,ActionListener 
{
	IModel model;
	IController controller;
	GridButton[] panels = new GridButton[64];
	GridButton[] panels2 = new GridButton[64];
	JFrame guiframe2 = new JFrame();
	JFrame guiframe = new JFrame();
	JLabel turnCount = new JLabel();
	JLabel turnCount2 = new JLabel();

	JPanel panel2= new JPanel(new GridLayout(8,9));
	JPanel panelgrid= new JPanel(new GridLayout(8,9));

	
	
	
	/**
	 * Constructor
	 */
	public GUIView()
	{
	}
	
	
	
	@Override
	public void initialise(IModel model, IController controller)
	{
		this.model = model;
		this.controller = controller;		
		int i = 0;
		
		JPanel panel2bg= new JPanel();
		panel2bg.setLayout( new BoxLayout(panel2bg, BoxLayout.PAGE_AXIS) );
		
		JPanel panel1= new JPanel();

		
		JPanel panelButton2= new JPanel();
		panelButton2.setLayout( new GridLayout(2,0) );
		
		
		JPanel panel1bg= new JPanel();
		panel1bg.setLayout( new BoxLayout(panel1bg, BoxLayout.PAGE_AXIS) );
		
		JPanel panellabel= new JPanel();


		
		JPanel panelButton1= new JPanel();
		panelButton1.setLayout( new GridLayout(2,0) );
		
		JButton ai1 = new JButton("Greedy AI (play white)");
		JButton ai2 = new JButton("Greedy AI (play black)");
		JButton rstrt1 = new JButton("RESTART");
		JButton rstrt2 = new JButton("RESTART");
		
		ai1.addActionListener( new ActionListener() { public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(2); } } );
		ai2.addActionListener( new ActionListener() { public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(1); } } );
		rstrt1.addActionListener( new ActionListener() { public void actionPerformed(ActionEvent e) {
			refresh();} } );
		rstrt2.addActionListener( new ActionListener() { public void actionPerformed(ActionEvent e) {
		refresh(); } } );
		
		
		panel1.add(turnCount2,BorderLayout.NORTH);
		guiframe2.getContentPane().add(panel2bg);
		panel2bg.add(panel1,BorderLayout.NORTH);
		panel2bg.add(panel2,BorderLayout.SOUTH);
		panelButton2.add(ai2);
		panelButton2.add(rstrt2);
		panel2bg.add(panelButton2, BorderLayout.SOUTH);

		guiframe.getContentPane().add(panel1bg);
		panellabel.add(turnCount,BorderLayout.NORTH);
		panel1bg.add(panellabel,BorderLayout.NORTH);
		panel1bg.add(panelgrid,BorderLayout.SOUTH);
		panelButton1.add(ai1);
		panelButton1.add(rstrt1);
		panel1bg.add(panelButton1, BorderLayout.SOUTH);
		
		
		guiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiframe.setTitle("Reversi - White player");
		guiframe.setLocationRelativeTo(null);
		guiframe.getContentPane().setLayout( new FlowLayout() );
		
		for (i=0;i<64;i++) {
			final int j = i;
			if (i == 28 || i == 35)
			{
				panels[i] = new GridButton(50,50,1,0);
				model.setBoardContents(1, j/8, j%8);
			}
			else if (i == 27 || i == 36)
			{
				panels[i] = new GridButton(50,50,1,1);
				model.setBoardContents(j/8, j%8, 1);
			}
			else
			{
				panels[i] = new GridButton(50,50);
			}
			panels[i].addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e)
			{
				controller.squareSelected(1, j/8, j%8);
				model.setBoardContents(j/8, j%8, 1);
				 } } );
			panelgrid.add(panels[i]);
			
		}
		guiframe.pack();
		guiframe.setLocationRelativeTo(null);
		guiframe.setVisible(true);
		
		guiframe2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiframe2.setTitle("Reversi - Black player");
		guiframe2.setLocationRelativeTo(null);
		guiframe2.getContentPane().setLayout( new FlowLayout() );

		
		for (i=0;i<64;i++) {
			final int j = i;
			if (i == 28 || i == 35)
			{
				panels2[i] = new GridButton(50,50,1,1);
				model.setBoardContents(j/8, j%8, 2);
			}
			else if (i == 27 || i == 36)
			{
				panels2[i] = new GridButton(50,50,1,0);
				model.setBoardContents(j/8, j%8, 2);
			}
			else
			{
				panels2[i] = new GridButton(50,50);
			}
			panels2[i].putClientProperty("id", Integer.valueOf(i));
			panels2[i].addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e)
			{
				controller.squareSelected(2, j/8, j%8);
				model.setBoardContents(j/8, j%8, 2);
				 } } );
			panel2.add(panels2[i]);
			
		}
		
		
		guiframe2.pack();
		guiframe2.setLocationRelativeTo(null);
		guiframe2.setVisible(true);
		
	}

	
	
	public void refresh() //so that it gets rid of old games after restart is pressed
	{
		controller.startup();
	}
	
	
	@Override
	public void refreshView()
	{

		int pTurn;
		int  j = 0;
		int k = 0;
		
		panelgrid.removeAll();
		panelgrid.setLayout(new GridLayout(8,9));
		for (j=0;j<8;j++)
		{
			for (k=0;k<8;k++)
			{
				final int num = j;
				final int num2 = k;
				pTurn = model.getBoardContents(j, k);
				if (pTurn == 1)
				{
					panels[j*8+k] = new GridButton(50,50,1,1);		
				}
				else if (pTurn == 2)
				{
					panels[j*8+k] = new GridButton(50,50,1,0);
				}
				else
				{
					panels[j*8+k] = new GridButton(50,50);
				}
				panels[j*8 +k].addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e)
				{
					controller.squareSelected(2, num, num2);
					 } } );
				
				panelgrid.add(panels[j*8 +k]);
			}
			
		}
	
		
		panel2.removeAll();
		panel2.setLayout(new GridLayout(8,9));
		for (j=0;j<8;j++)
		{
			for (k=0;k<8;k++)
			{
				final int num = j;
				final int num2 = k;
				pTurn = model.getBoardContents(7-j, 7-k);
				if (pTurn == 1)
				{
					panels2[j*8+k] = new GridButton(50,50,1,1);		
				}
				else if (pTurn == 2)
				{
					panels2[j*8+k] = new GridButton(50,50,1,0);
				}
				else
				{
					panels2[j*8+k] = new GridButton(50,50);
				}
				panels2[j*8 +k].addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e)
				{
					controller.squareSelected(1, 7-num, 7-num2);
					 } } );
				
				panel2.add(panels2[j*8 +k]);
			}
		}
		
		guiframe2.pack();
		guiframe.pack();
		
	}

	
	String player1Message = "";
	String player2Message = "";
	
	@Override
	public void feedbackToUser(int player, String message)
	{
		
			if ( player == 1 )
			{
			player1Message = message;
			turnCount.setText(message);
			}
		
			else if ( player == 2 ) {
			player2Message = message;
			turnCount2.setText(message);
			}
		
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

		
	
}
