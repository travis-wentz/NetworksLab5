import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author TravisWentz JoeDebruycker
 *
 */
public class Lab05 {
	
	// Static variables for the initial data read from "configuration.txt"
	public static int xport, yport, zport;
	public static ArrayList<Integer> xvec = new ArrayList<Integer>();
	public static ArrayList<Integer> yvec = new ArrayList<Integer>();
	public static ArrayList<Integer> zvec = new ArrayList<Integer>();
	
	// Method to read "configuration.txt" and initialize variables
	public static void readConfig() throws IOException{
		Scanner sc = new Scanner(new File("configuration.txt"));
		if(sc.hasNextInt()){
			Lab05.xport = sc.nextInt();
		}
		if(sc.hasNextInt()){
			Lab05.yport = sc.nextInt();
		}
		if(sc.hasNextInt()){
			Lab05.zport = sc.nextInt();
		}
		if(sc.hasNextInt()){
			xvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			xvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			xvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			yvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			yvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			yvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			zvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			zvec.add(sc.nextInt());
		}
		if(sc.hasNextInt()){
			zvec.add(sc.nextInt());
		}
		
		sc.close();
	}
	
	// Method to run Bellman-Ford equation on all routers for 1 round.  Requires passing
	//  open sockets for UDP communication.
	public static void bellmanFord(DatagramSocket x, DatagramSocket y, DatagramSocket z) throws IOException{
		
	}
	
	// Main method uses UDP API to implement Distance Vector Routing Algorithm
	public static void main(String[] args) throws IOException {
		// Initialize variables from text file
		readConfig();
		
		// Initialize Local IP Address for UDP 
		InetAddress IPAddress = InetAddress.getLocalHost();
		
		// Open sockets for communications
		DatagramSocket xsocket = null;
		DatagramSocket ysocket = null;
		DatagramSocket zsocket = null;
		
		try{
			xsocket = new DatagramSocket(xport);
			ysocket = new DatagramSocket(yport);
			zsocket = new DatagramSocket(zport);
		} catch(Exception e){
			System.out.println("Exception occurred when creating sockets");
		}
		
		// Initialize arrays for sending UDP information
		byte[] xrcvData = new byte[4];
		byte[] xsendData = new byte[4];
		byte[] yrcvData = new byte[4];
		byte[] ysendData = new byte[4];
		byte[] zrcvData = new byte[4];
		byte[] zsendData = new byte[4];
		
		// Get user input to see which router we simulate
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the router's id (X, Y, Z): ");
		String router = input.next().toLowerCase();
		input.close();
		
		if(router.equals("x")){
			System.out.println("Router X is running on port " + xport);
			System.out.println("Distance vector on Router X is:");
			System.out.println(xvec);
		} else if(router.equals("y")){
			System.out.println("Router Y is running on port " + yport);
			System.out.println("Distance vector on Router Y is:");
			System.out.println(yvec);
		} else if(router.equals("z")){
			System.out.println("Router Z is running on port " + zport);
			System.out.println("Distance vector on Router Z is:");
			System.out.println(zvec);
		} else {
			System.out.println("Invalid Selection!");
		}
		
		xsocket.close();
		ysocket.close();
		zsocket.close();
		
		System.out.println("Bye!");
	}

}
