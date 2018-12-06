import java.awt.Color;
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.ScrollPane;

public class ChatClient3{

	private JFrame frame;
	private Socket s;
	private PrintWriter pw;
	private MultiThreadClient3 thread;
	private String msg = "", str1 = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient3 window = new ChatClient3();
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
	public ChatClient3() {
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
		
		JLabel lblChatClient = new JLabel("Chat Client");
		lblChatClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChatClient.setBounds(231, 11, 69, 48);
		frame.getContentPane().add(lblChatClient);
		
		
		JTextArea textAreaMessage = new JTextArea();
		textAreaMessage.setBounds(30, 363, 366, 64);
		//frame.getContentPane().add(textAreaMessage);
		
		
		JTextPane textPaneChatBox = new JTextPane();
		textPaneChatBox.setBounds(30, 61, 465, 279);
		textPaneChatBox.setForeground(Color.BLACK);
		//textPaneChatBox.setAlignmentX(textPaneChatBox.RIGHT_ALIGNMENT);
		textPaneChatBox.setEditable(false);     //Set thuoc tinh khong the chinh sua trong ChatBox
		//frame.getContentPane().add(textPaneChatBox);
		
		//Them textPaneChatBox vao scrollPane de hien thi thanh cuon
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(30, 61, 465, 279);
		scrollPane.add(textPaneChatBox);
		frame.getContentPane().add(scrollPane);
		
		
		//Them textAreaMessage vao scrollPane2 de hien thi thanh cuon
		ScrollPane scrollPane2 = new ScrollPane();
		scrollPane2.setBounds(30, 363, 366, 64);
		scrollPane2.add(textAreaMessage);
		frame.getContentPane().add(scrollPane2);
		
		
		//Ket noi toi Server
		try {
			//Khoi tao socket
			s = new Socket("127.0.0.1", 9999);
			
			//Call Thread nhan message tu Server va show tin nhan len ChatBox
			thread = new MultiThreadClient3(s, textPaneChatBox);
			thread.start();
			
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(frame, "Ket noi that bai", "Message", JOptionPane.INFORMATION_MESSAGE);
		}				
		
		
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Gui message qua Server khi nhan nut Send
				try {
					//Moi Client chi truyen 1 luong du lieu toi Server
					pw = new PrintWriter(s.getOutputStream());
					String str = textAreaMessage.getText().toString();
					
					//str1 = multiClientAccept.getStr();     //Messages tu Client
					//multiClientAccept.setStr(str);
					
					//str1 = textPaneChatBox.getText().toString();
					//String clientMsg = "Client: " + str;	
					//msg = (str1 != null) ? (str1 + clientMsg + "\n") : (clientMsg + "\n");
					//textPaneChatBox.setText(msg);
		
					
					pw.println(str);
					pw.flush();
					//textPaneChatBox.setText("");    //Khi gui tin nhan roi thi xoa noi dung trong o ChatBox
					textAreaMessage.setText("");    //Khi gui tin nhan roi thi xoa noi dung trong o nhap tin nhan
				}catch(IOException e) {
					e.printStackTrace();
				}	
			}
		});
		btnSend.setBounds(406, 363, 89, 23);
		frame.getContentPane().add(btnSend);	
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int question = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", null, JOptionPane.YES_NO_OPTION);
		        if (question == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
			}
		});
		btnClose.setBounds(406, 397, 89, 23);
		frame.getContentPane().add(btnClose);

	}
}
