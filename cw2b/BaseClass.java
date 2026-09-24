package cw2b;
// Note: ctrl-shift-o for import every standard thing needed
import java.util.ArrayList;

public class BaseClass
{
	public void createAnimals()
	{
		System.err.println("You need to override the createAnimals() function to create and store animals (your MyCw2bAnswer class)");
	}
	
	public void recordMadeSound( String str )
	{
		System.err.println("You need to implement the recordMadeSound() method in your subclass (your MyCw2bAnswer class)");
	}
	
	public void recordMoved( String str )
	{
		System.err.println("You need to implement the recordMoved() method in your subclass (your MyCw2bAnswer class)");
	}
	
	public void finish()
	{
		System.err.println("You need to implement the finish() method in your subclass to output the noises and movement lists (your MyCw2bAnswer class)");
	}

	

	// Don't override or change this!
	public final void doStuff()
	{
		//System.out.println("Using class " + this.getClass().getName() );
		if ( this.getClass().getName().compareTo("cw2b.MyCw2bAnswer" ) != 0 )
			System.err.println("You need to create your own class called MyCw2bAnswer in the package cw2b, and create the object in MainProgram.\n\tYour subclass will change the behaviour by implementing functions.");
		
		createAnimals();
		for ( Animal an : myAnimals )
		{
			recordMoved( an.getMovement() );
			recordMadeSound( an.getNoise() );
			recordMoved( an.getMovement() );
		}
		finish();
	}

	// I hid this inside by making it private
	// List of animals to 'do stuff with'
	// Your sub-class will deliberately not be able to access this and will have to add to it by using storeAnimal().
	private ArrayList<Animal> myAnimals = new ArrayList<Animal>();
	
	// Don't override or change this function!
	// Store an animal in the list - will be used later
	public final void storeAnimal( Animal animalToStore )
	{
		myAnimals.add(animalToStore);
	}
	
	
	// Check that there are the correct number of animals stored, and that there are exactly 4 types...
	public final void test()
	{
		if ( myAnimals.size() != 6 )
			System.err.println("Test harness: you stored the wrong number of animals, " + myAnimals.size() + " instead of 6" );
		else
			System.out.println("Test harness: you correctly stored " + myAnimals.size() + " animals : PASSED" );
		
		int differentAnimalCount = 0;
		// Quick hack way to test, since there will not be many of them...
		for ( int check = 0 ; check < myAnimals.size(); ++check )
		{
			boolean matched = false;
			for ( int prev = 0 ; prev < check; ++prev )
				if ( myAnimals.get(prev).getClass() == myAnimals.get(check).getClass() )
					matched = true; // Animal is same type as a previous animal so skip this in the counting
			if ( !matched )
				++differentAnimalCount;
		}
		
		if ( differentAnimalCount != 4 )
			System.err.println("Test harness: you stored the wrong number of TYPES of animals, creating animals of " + differentAnimalCount + " different types instead of 4" );
		else
			System.out.println("Test harness: you correctly " + differentAnimalCount + " types of animal : PASSED" );
	}

	public ArrayList<Animal> getAnimals()
	{
		return myAnimals;
	}
}
