import java.awt.Color;
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.ScrollPane;
import java.util.ArrayList;

public class ChatServer3 {
	
	private static final int defaultPort = 9999;
	private JFrame frame;
	private ServerSocket ss;
	private Socket s;
	private PrintWriter pw;
	private MultiThreadServer3 thread;
	private String msg = "", str1 = "";
	private int id = 0;
	private ClientAccept multiClientAccept;
	private ArrayList<Socket> clientList = new ArrayList<>();          /////////////////////////////

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServer3 window = new ChatServer3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatServer3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 543, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Chat Application");
		
		JLabel lblChatServer = new JLabel("Chat Server");
		lblChatServer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChatServer.setBounds(231, 11, 77, 48);
		frame.getContentPane().add(lblChatServer);
		
		JTextArea textAreaMessage = new JTextArea();
		textAreaMessage.setBounds(30, 363, 366, 64);
		//frame.getContentPane().add(textAreaMessage);
		
		
		JTextPane textPaneChatBox = new JTextPane();
		textPaneChatBox.setBounds(30, 54, 465, 290);
		textPaneChatBox.setForeground(Color.BLACK);
		textPaneChatBox.setEditable(false);     //Set thuoc tinh khong the chinh sua trong ChatBox
		//frame.getContentPane().add(textPaneChatBox);
		
		
		//Them textPaneChatBox vao scrollPane de hien thi thanh cuon
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(30, 65, 465, 279);
		scrollPane.add(textPaneChatBox);
		frame.getContentPane().add(scrollPane);
		
		//Them textAreaMessage vao scrollPane2 de hien thi thanh cuon
		ScrollPane scrollPane2 = new ScrollPane();
		scrollPane2.setBounds(30, 363, 366, 64);
		scrollPane2.add(textAreaMessage);
		frame.getContentPane().add(scrollPane2);
		
		
		//Khoi tao Server
		try {
			ss = new ServerSocket(defaultPort);
			System.out.println("Server is running...");
			
			//Thread luon nhan ket noi tu Client
			multiClientAccept = new ClientAccept(ss, id, textPaneChatBox, frame);
			multiClientAccept.start();
			
			
			//ss.close();   //Close Server Socket	
		}
		catch(IOException ie) {
			ie.printStackTrace();
			
		}						
		/*
		//Khoi tao Server
		try {
			ss = new ServerSocket(defaultPort);
			
			while(true) {	
				try {
					s = ss.accept();   //Chap nhan ket noi tu Client
					System.out.println("Server is running...");
					id += 1;
					//Call Thread nhan message tu Client va show tin nhan len ChatBox
					thread = new MultiThreadServer3(s, textPaneChatBox, id);
					thread.start();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(frame, "Ket noi that bai", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				//break;   //
			}
			//ss.close();   //Close Server Socket	
		}
		catch(IOException ie) {
			ie.printStackTrace();
			
		}
		*/
		
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Gui message qua Client khi nhan nut Send
				try {
					Socket s = multiClientAccept.getSocket();
					//pw = new PrintWriter(s.getOutputStream());
					String str = textAreaMessage.getText().toString();    //Lay message tu Input
					//str1 = multiClientAccept.getStr();     //Messages tu Client
					str1 = textPaneChatBox.getText().toString();
					//multiClientAccept.setStr(str);
					
					
					//Lay danh sach cac client's Socket
					clientList = multiClientAccept.getArrayList();                 //////////////////////////////
					
					
					
					String serverMsg = "Server: " + str;	
					msg = (str1 != null) ? (str1 + serverMsg + "\n") : (serverMsg + "\n");
					textPaneChatBox.setText(msg);
					
					
					//Server co the gui messages den nhieu clients cung 1 luc
					for(Socket sk : clientList ) {                                   /////////////////////////////
						pw = new PrintWriter(sk.getOutputStream());                 /////////////////////////////
						pw.println(serverMsg);                                      /////////////////////////////
						pw.flush();                                                 /////////////////////////////
					}                                                               /////////////////////////////
					
					/*
					String serverMsg = "Server: " + str;	
					msg = thread.getMs() + serverMsg + "\n";
					textPaneChatBox.setText(msg);
					
					thread.setMs(msg);    //Truyen msg qua MultiThreadServer
					
					pw.println(str);
					pw.flush();
					*/
					
					textAreaMessage.setText("");   //Khi gui tin nhan roi thi xoa noi dung trong o nhap tin nhan
				}
				catch(IOException ie) {
					ie.printStackTrace();
				}
			}
		});
		btnSend.setBounds(406, 364, 89, 23);
		frame.getContentPane().add(btnSend);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				int question = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", null, JOptionPane.YES_NO_OPTION);
		        if (question == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
			}
		});
		btnClose.setBounds(406, 404, 89, 23);
		frame.getContentPane().add(btnClose);

	}
}


