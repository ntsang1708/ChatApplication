import java.io.*;
import java.net.*;
import java.lang.Thread;
import javax.swing.JTextPane;
import java.util.ArrayList;

public class MultiThreadServer3 extends Thread{
	private Socket s;
	private String str, ms = "", str1 = "", str2, str3;
	private JTextPane textPane;
	private int id;
	private ArrayList<Socket> clientList = new ArrayList<>();
	
	
	public MultiThreadServer3(ArrayList<Socket> newClientList, Socket s1, JTextPane txtPane, int newId) {
		s = s1;
		clientList = newClientList;
		textPane = txtPane;
		id = newId;
	}
	
	public String getMs() {
		return ms;
	}
	
	//Ham set message, nhan vao doi so la 1 chuoi
	public String setMs(String msg) {
		ms = msg;
		return ms;
	}
	
	
	public String setStr(String newStr) {
		str1 = newStr;
		return str1;
	}
	
	public String getStr() {
		return str3;
	}
	
	
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//PrintWriter pw = new PrintWriter(s.getOutputStream());
			
			//Nhan message tu Client
			while(true) {
					str = br.readLine();    //Nhan message tu Client
					if(str != null) {
						ms = "Client " + id + ": " + str + "\n";
						
						str2 = textPane.getText().toString();
						str3 = str2 + ms;
						textPane.setText(str3);
						
						/*
						System.out.print(ms);   //In messages ra Console
						//pw.print(str3);       //Truyen messages qua Client, khong co ky tu xuong dong
						pw.print(ms);       //Truyen messages qua Client, khong co ky tu xuong dong
						pw.flush();   //
						*/
						//if(str.equals("quit")) break;
						
						//Gui tin nhan den nhieu Clients cung 1 luc
						for(Socket sk : clientList) {
							PrintWriter pw = new PrintWriter(sk.getOutputStream());
							pw.print(ms);       //Truyen messages qua Client, khong co ky tu xuong dong
							pw.flush();
						}
					}
			}
			//s.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
