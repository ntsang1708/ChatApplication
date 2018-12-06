import java.awt.Color;
import java.io.*;
import java.net.*;
import java.lang.Thread;
import javax.swing.JTextPane;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;

public class MultiThreadClient3 extends Thread{
	private Socket s;
	private String str = "" , ms = "", str2 = "", str3 = "";
	private JTextPane textPaneChatBox;
	//private String mss = "";
	//private String arrayStr[];
	
	public MultiThreadClient3(Socket s1, JTextPane txtPane) {
		s = s1;
		textPaneChatBox = txtPane;
	}
	
	public String getMsg() {
		return ms;
	}
	
	public String setMsg(String newMsg) {
		ms = newMsg;
		return ms;
	}
	
	
	public void run() {
		try {	
			//Moi Client chi nhan 1 luong du lieu vao tu Server
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			//Nhan message tu Server
			while(true) {
				//while( (str = br.readLine()) != null) {
				str = br.readLine();    //Nhan message tu Server
				//ms = "Server said: " + str + "\n";
				//}
				ms = str + "\n";
				
				str2 = textPaneChatBox.getText().toString();
				str3 = str2 + ms;
				textPaneChatBox.setText(str3);
				//textPaneChatBox.setText(ms);
				System.out.print(ms);
				
				/*
				StyledDocument doc = textPaneChatBox.getStyledDocument();
				Style style = textPaneChatBox.addStyle("Color Style", null);
				StyleConstants.setForeground(style, Color.BLACK);
				try {
		            doc.insertString(doc.getLength(), ms, style);
		        } 
		        catch (BadLocationException e) {
		            e.printStackTrace();
		        }       
				*/
				//break;
			}
			
			//s.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
