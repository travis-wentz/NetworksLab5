import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author TravisWentz JoeDebruycker
 *
 */
public class Lab05 {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("configuration.txt"));

		//for testing
		String line;
		while((line = in.readLine()) != null)
		{
		    System.out.println(line);
		}
		
		
		in.close();
	}

}
