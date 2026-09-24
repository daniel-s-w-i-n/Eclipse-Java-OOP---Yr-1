package cw2b;

import java.util.ArrayList;

public class Animal
{
	private static int animalCount = 0;


	public Animal()
	{
		++animalCount;
	}
	
	public static void test()
	{
		if ( animalCount != 6 )
			System.err.println("Test harness: you need to create 6 animals, not " + animalCount + "!" );
		else
			System.out.println("Test harness: you correctly created " + animalCount + " animal objects : PASSED" );
	}
	
	public String getType()
	{
		return "Animal";
	}

	public String getName() 
	{ 
		return "I am an animal"; 
	}

	public String getNoise()
	{
		return "Unknown noise from unknown animal";
	}

	public String getMovement()
	{
		return "Unknown movement method for unknown animal";
	}
}
