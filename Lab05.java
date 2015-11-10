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
	
	public static int xport, yport, zport;
	public static ArrayList<Integer> xvec = new ArrayList<Integer>();
	public static ArrayList<Integer> yvec = new ArrayList<Integer>();
	public static ArrayList<Integer> zvec = new ArrayList<Integer>();
	
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

	public static void main(String[] args) throws IOException {
		readConfig();
		
		InetAddress IPAddress = InetAddress.getLocalHost();
		
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
		
		byte[] xrcvData = new byte[4];
		byte[] xsendData = new byte[4];
		byte[] yrcvData = new byte[4];
		byte[] ysendData = new byte[4];
		byte[] zrcvData = new byte[4];
		byte[] zsendData = new byte[4];
		
		String sentence = "Hi";
	    xsendData = sentence.getBytes();
		
		try{
			DatagramPacket xsendPkt = new DatagramPacket(xsendData, xsendData.length, IPAddress, yport);
			DatagramPacket yrcvPkt = new DatagramPacket(yrcvData, yrcvData.length);
			xsocket.send(xsendPkt);
			ysocket.receive(yrcvPkt);
			String rsentence = new String( yrcvPkt.getData());
			System.out.println(rsentence);
		} catch(Exception e){
			System.out.println("Exception occurred when receiving");
		} finally {
			xsocket.close();
			ysocket.close();
			zsocket.close();
		}
		
		//System.out.println(rsentence);
		//System.out.println(ysocket.getLocalPort());
		System.out.println("Bye!");
	}

}
