import java.io.*;
import java.net.*;
import java.lang.Thread;
import javax.swing.JTextPane;
import java.util.ArrayList;

public class MultiThreadServer3_Send extends Thread{
	private Socket s;
	private String str, ms = "", str1 = "", str2, str3, msg = "";
	private JTextPane textPane;
	private int id;
	private ArrayList<Socket> clientList = new ArrayList<>();
	
	
	public MultiThreadServer3_Send(ArrayList<Socket> newClientList, Socket s1, JTextPane txtPane, String newMsg) {
		s = s1;
		clientList = newClientList;
		textPane = txtPane;
		msg = newMsg;
	}	
	
	public void run() {
		try {			
			//Gui thong diep "Client is connected" tu Server den tat ca Clients cung 1 luc
			for(Socket sk : clientList) {
				PrintWriter pw = new PrintWriter(sk.getOutputStream());
				pw.print(msg);       //Truyen messages qua Client, khong co ky tu xuong dong
				pw.flush();
			}
			//s.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
