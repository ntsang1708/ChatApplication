import java.io.*;
import java.net.*;
import java.lang.Thread;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.ArrayList;

public class ClientAccept extends Thread {
	private Socket s;
	private ServerSocket ss;
	private int id;
	private MultiThreadServer3 thread;
	private MultiThreadServer3_Send thread_send;
	private JTextPane textPaneChatBox;
	private JFrame frame;
	private String str = "", connectedMsg = "", str3 = "", closedSocketMsg = "";
	private HashMap<Integer, Socket> hashmap = new HashMap<Integer, Socket>();    ///////
	private ArrayList<Socket> clientList = new ArrayList<>();
	//private ArrayList<String> listMsg = new ArrayList<>();
	
	public ClientAccept(ServerSocket ss1, int newId, JTextPane chatBox, JFrame newFrame) {
		ss = ss1;
		id = newId;
		textPaneChatBox = chatBox;
		frame = newFrame;
	}
	
	public Socket getSocket() {
		return s;
	}
	
	public String setStr(String newStr) {
		str = newStr;
		return str;
	}
	
	public String getStr() {
		return str;
	}
	
	public int getid() {
		return id;
	}
	
	///////
	public HashMap<Integer, Socket> getHashMap() {
		return hashmap;
	}
	
	public ArrayList<Socket> getArrayList(){
		return clientList;
	}
	
	public void run() {
		while(true) {	
			try {
				s = ss.accept();   //Chap nhan ket noi tu Client
				id += 1;
				
				//Mot Server co the nhan ket noi tu nhieu Clients
				//Khi co 1 Client socket moi ket noi, thi them no vao clientList
				clientList.add(s);
				
				//hashmap.put(s.getPort(), s);
				//System.out.println(hashmap.get(s.getPort()));   //Lay socket cua port
				
				
				////////////////////
				/*
				//Lay toan bo key cua hashmap
				for (int key : hashmap.keySet()) {
			        System.out.println("Key = " + key);
			    }
			    */
				////////////////////
				/*
				//Lay toan bo values cua hashmap
				for (String value : hashmap.values()) {
			        System.out.println("Value = " + value);
			    }
			    */
				/////////////////////
				
				
				
				System.out.print("------------- Client " + id + " is connected -------------\n");   //In ra man hinh console
				connectedMsg = "------------- Client " + id + " is connected ------------- \n";	   //Message Client da ket noi toi Server
				str3 = textPaneChatBox.getText().toString();    //Lay noi dung trong khung chatBox tren Server	
				textPaneChatBox.setText(str3 + connectedMsg);   //Set text cho chatBox tren Server
				textPaneChatBox.setForeground(Color.RED);       //To mau do cho text trong khung chatBox
				
				
				//Call Thread nhan message tu Client va show tin nhan len ChatBox
				thread = new MultiThreadServer3(clientList, s, textPaneChatBox, id);
				thread.start();
				str = thread.getStr();
				
				//Call Thread gui thong diep "Client is connected" tu Server den tat ca Clients
				thread_send = new MultiThreadServer3_Send(clientList, s, textPaneChatBox, connectedMsg);
				thread_send.start();
				
				
				///////////////////////////////////////////////////////////
				/*
				str3 = thread.getStr();
				if(str3 != null) {
					listMsg.add(str3);
				}
				System.out.println("Client " + id + " is connected");
				connectedMsg = "Client " + id + " is connected \n";
				if(connectedMsg != null)
					listMsg.add(connectedMsg);
				//textPaneChatBox.setText(connectedMsg + str3);
				for(String ch : listMsg) {
					str4 = ch;
				}
				chatMsg = textPaneChatBox.getText().toString();
				textPaneChatBox.setText(chatMsg + str4);
				*/
				///////////////////////////////////////////////////////////
				
				/*
				//Neu client disconnect
				if(s.isClosed()) {
					closedSocketMsg = "Client " + id + " disconnected...";
					textPaneChatBox.setText(str3 + closedSocketMsg);
				}
				*/
				
			
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(frame, "Ket noi that bai", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			//break;
		}
		//ss.close();   //Close Server Socket	
	}
}
