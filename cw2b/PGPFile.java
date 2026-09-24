package cw2b;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Wrapper class created for use in G51PGP to simplify initial file I/O
 * @author jaa
 */
public class PGPFile
{
	private static ArrayList<String> textWritten = new ArrayList<String>();
	
	public static void test( ArrayList<Animal> theAnimalArray )
	{
		System.out.println("Testing file output of ...");
		for ( int i = 0 ; i < textWritten.size() ; i++ )
			System.out.println("Test Harness: line " + (i+1) + " : " + textWritten.get(i) );
		
		int numAnimals = theAnimalArray.size(); // Prevent having to keep asking for this
		
		if ( textWritten.size() < 1 || textWritten.get(0).compareToIgnoreCase("List of noises made:") != 0 )
		{
			System.err.println("The first line of your output file should be 'List of noises made:' rather than '" + textWritten.get(0) + "'" );
			return;
		}
		
		// Indices 1 to numAnimals
		for ( int i = 0 ; i < numAnimals; ++i )
		{
			if ( textWritten.size() < (1+i) || textWritten.get(1+i).compareToIgnoreCase( theAnimalArray.get(i).getNoise() ) != 0 )
			{
				System.err.println("Line " + (1+i) + " of file '" + theAnimalArray.get(i).getNoise() + "' rather than '" + textWritten.get(i) + "'" );
				return;
			}
		}
		
		// Index NumAnimals+1
		if ( textWritten.size() < (numAnimals+2) || textWritten.get(numAnimals+1).compareToIgnoreCase("List of movement made:") != 0 )
			System.err.println("Line " + (numAnimals+2) + " should be 'List of movement made:' rather than '" + textWritten.get(numAnimals+1) + "'" );
		
		// Indices NumAnimals+2 to NumAnimals+1+numAnimals*2
		for ( int i = 0 ; i < numAnimals*2; ++i )
		{
			if ( textWritten.size() < (numAnimals+3+i) || textWritten.get(numAnimals+2+i).compareToIgnoreCase( theAnimalArray.get(i/2).getMovement() ) != 0 )
			{
				System.err.println("Line " + (numAnimals+3+i) + " of file should be '" + theAnimalArray.get(i/2).getMovement() + "' rather than '" + textWritten.get(numAnimals+2+i) + "'" );
				return;
			}
		}
		
		System.out.println("*** File contents checked and passed. *** ");
		
		System.out.println("IMPORTANT: You need to manually check that the output to standard out is correct because this test harness that I have given you will not check that. (I check that in a different way.)");
	}
	
	
	public boolean openReadFile( String strFileName )
	{
		// Close any existing file before opening new one
		if ( br != null )
			closeReadFile();
		try 
		{
			br = new BufferedReader(
					new FileReader(strFileName));
	    }
		catch(Exception e )
		{
			return false;
		}
		return true;
	}

	public String readNextLine()
	{
		// If not open then we can't do this
		if ( br == null )
			return null;

		try
		{
			return br.readLine();
		} 
		catch (IOException e)
		{
			return null;
		}
	}
	
	public boolean closeReadFile()
	{
		// If not open then we can't do this
		if ( br == null )
			return false;
		
		try
		{
			br.close();
			br = null;
		} 
		catch (IOException e)
		{
			br = null;
			return false;
		}
		return true;
	}
	
	public boolean openWriteFile( String strFileName )
	{
//		// Close any existing file before opening new one
//		if ( bw != null )
//			closeWriteFile();

//		try
//		{
//	        bw = new BufferedWriter(new FileWriter(strFileName));
			textWritten.clear();
			return true;
//	    } 
//		catch (IOException e)
//		{
//			return false;
//		}
	}

	public boolean writeLine( String strLine )
	{
//		// If not open then we can't do this
//		if ( bw == null )
//			return false;

//		try
//		{
//			bw.write( strLine + "\r\n" );
			String toAdd = strLine.trim(); // Remove any trailing or leading whitespace to make it easier to pass the test
			if ( toAdd.length() > 0 )	// Don't add blank lines, so that if students add them it will still pass the test
				textWritten.add(toAdd); // Save to the array instead of writing
			return true;
//		} 
//		catch (IOException e)
//		{
//			return false;
//		}
	}
	
	public boolean closeWriteFile()
	{
//		// If not open then we can't do this
//		if ( bw == null )
//			return false;

//		try
//		{
//			bw.close();
//			bw = null;
			return true;
//		} 
//		catch (IOException e)
//		{
//			bw = null;
//			return false;
//		}
	}
	
	// Internal object - the reader for the input file
	protected BufferedReader br;
	// Internal object - the writer for the output file
	protected BufferedWriter bw;
	
	
}
