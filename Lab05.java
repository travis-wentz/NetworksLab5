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
	
	// Method to run Bellman-Ford equation on all routers until fully updated.  Requires passing open sockets for UDP communication.
	public static void bellmanFord(DatagramSocket x, DatagramSocket y, DatagramSocket z, String router) throws IOException{
		// Initialize Local IP Address for UDP 
		InetAddress IPAddress = InetAddress.getLocalHost();
		
		// Initialize arrays for sending UDP information
		byte[] xrcvData = new byte[4];
		byte[] xsendData = new byte[4];
		byte[] yrcvData = new byte[4];
		byte[] ysendData = new byte[4];
		byte[] zrcvData = new byte[4];
		byte[] zsendData = new byte[4];
		
		// Boolean variables to track when routers update their vectors
		boolean xchanged = true;
		boolean ychanged = true;
		boolean zchanged = true;
		
		// Begin Bellman Ford
		while(xchanged || ychanged || zchanged){
			//changed routers send to x
			boolean changeXtoZ = false;
			boolean changeXtoY = false;
			//y sends to x
			if(ychanged){
				if(router.equals("x")){
					System.out.print("Receives distance vector from router Y: ");
					System.out.println(yvec);
					if(xvec.get(1) + yvec.get(2) < xvec.get(2)){
						System.out.println("Distance vector on router X is updated to:");
						System.out.println("[" + xvec.get(0) + " ," + xvec.get(1) + " ," + (xvec.get(1) + yvec.get(2)) + "]");
						changeXtoZ = true;
						xchanged = true;
					}else{
						System.out.println("Distance vector on router X is not updated");
					}
				}else{
					if(xvec.get(1) + yvec.get(2) < xvec.get(2)){
						changeXtoZ = true;
						xchanged = true;
					}
				}
			}
			//z sends to x
			if(zchanged){
				if(router.equals("x")){
					System.out.print("Receives distance vector from router Z: ");
					System.out.println(zvec);
					if(xvec.get(2) + zvec.get(1) < xvec.get(1)){
						System.out.println("Distance vector on router X is updated to:");
						System.out.println("[" + xvec.get(0) + " ," + (xvec.get(2) + zvec.get(1)) + " ," + xvec.get(2) + "]");
						changeXtoY = true;
						xchanged = true;
					}else{
						System.out.println("Distance vector on router X is not updated");
					}
				}else{
					if(xvec.get(2) + zvec.get(1) < xvec.get(1)){
						changeXtoY = true;
						xchanged = true;
					}
				}
			}
			
			//changed routers send to y
			boolean changeYtoZ = false;
			boolean changeYtoX = false;
			//x sends to y
			if(xchanged){
				if(router.equals("y")){
					System.out.print("Receives distance vector from router X: ");
					System.out.println(xvec);
					if(yvec.get(0) + xvec.get(2) < yvec.get(2)){
						System.out.println("Distance vector on router Y is updated to:");
						System.out.println("[" + yvec.get(0) + " ," + yvec.get(1) + " ," + (yvec.get(0) + xvec.get(2)) + "]");
						changeYtoZ = true;
						ychanged = true;
					}else{
						System.out.println("Distance vector on router Y is not updated");
					}
				}else{
					if(yvec.get(0) + xvec.get(2) < yvec.get(2)){
						changeYtoZ = true;
						ychanged = true;
					}
				}
			}
			//z sends to y
			if(zchanged){
				if(router.equals("y")){
					System.out.print("Receives distance vector from router Z: ");
					System.out.println(zvec);
					if(yvec.get(2) + zvec.get(0) < yvec.get(0)){
						System.out.println("Distance vector on router Z is updated to:");
						System.out.println("[" + (yvec.get(2) + zvec.get(0)) + " ," + yvec.get(1) + " ," + yvec.get(2) + "]");
						changeYtoX = true;
						ychanged = true;
					}else{
						System.out.println("Distance vector on router Y is not updated");
					}
				}else{
					if(yvec.get(2) + zvec.get(0) < yvec.get(0)){
						changeYtoX = true;
						ychanged = true;
					}
				}
			}
			
			//changed routers send to z
			boolean changeZtoY = false;
			boolean changeZtoX = false;
			//x sends to z
			if(xchanged){
				if(router.equals("z")){
					System.out.print("Receives distance vector from router X: ");
					System.out.println(xvec);
					if(zvec.get(0) + xvec.get(1) < zvec.get(1)){
						System.out.println("Distance vector on router Z is updated to:");
						System.out.println("[" + zvec.get(0) + " ," + (zvec.get(0) + xvec.get(1)) + " ," + zvec.get(2) + "]");
						changeZtoY = true;
						zchanged = true;
					}else{
						System.out.println("Distance vector on router Z is not updated");
					}
				}else{
					if(zvec.get(0) + xvec.get(1) < zvec.get(1)){
						changeZtoY = true;
						zchanged = true;
					}
				}
			}
			//y sends to z
			if(ychanged){
				if(router.equals("z")){
					System.out.print("Receives distance vector from router Y: ");
					System.out.println(yvec);
					if(zvec.get(1) + yvec.get(0) < zvec.get(0)){
						System.out.println("Distance vector on router Z is updated to:");
						System.out.println("[" + (zvec.get(1) + yvec.get(0)) + " ," + zvec.get(1) + " ," + zvec.get(2) + "]");
						changeZtoX = true;
						zchanged = true;
					}else{
						System.out.println("Distance vector on router Z is not updated");
					}
				}else{
					if(zvec.get(1) + yvec.get(0) < zvec.get(0)){
						changeZtoX = true;
						zchanged = true;
					}
				}
			}
			
			// Update vectors to new values if Bellman Ford determined there were better routes
			if(xchanged){
				if(changeXtoZ){
					xvec.set(2, (xvec.get(1) + yvec.get(2)));
				}
				if(changeXtoY){
					xvec.set(1, (xvec.get(2) + zvec.get(1)));
				}
				if(!changeXtoZ && !changeXtoY){
					xchanged = false;
				}
			}
			
			if(ychanged){
				if(changeYtoZ){
					yvec.set(2, (yvec.get(0) + xvec.get(2)));
				}
				if(changeYtoX){
					yvec.set(0, (yvec.get(2) + zvec.get(0)));
				}
				if(!changeYtoZ && !changeYtoX){
					ychanged = false;
				}
			}
			
			if(zchanged){
				if(changeZtoY){
					zvec.set(1, (zvec.get(0) + xvec.get(1)));
				}
				if(changeZtoX){
					zvec.set(0, (zvec.get(1) + yvec.get(0)));
				}
				if(!changeZtoY && !changeZtoX){
					zchanged = false;
				}
			}
		}
	}
	
	// Main method uses UDP API to implement Distance Vector Routing Algorithm
	public static void main(String[] args) throws IOException {
		// Initialize variables from text file
		readConfig();
		
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
		
		// Get user input to see which router we simulate
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the router's id (X, Y, Z): ");
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

		bellmanFord(xsocket, ysocket, zsocket, router);
		
		xsocket.close();
		ysocket.close();
		zsocket.close();
		
		System.out.println("Bye!");
	}

}
