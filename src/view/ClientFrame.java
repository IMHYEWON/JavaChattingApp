package view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import member.IpMember;
import net.Writeclass;

public class ClientFrame extends JFrame implements WindowListener, ActionListener{
	
	public Socket socket;
	Writeclass wc;
	
	static List<IpMember> 	memLists;
	static List<String>		namelist;
	
	public boolean isFirst = true;	// 첫번째 전송(ID등록)
	public boolean isExit  = false;
	
	/* * * * * UI * * * * */
	static JFrame jf;
	
	public JTextField textF = new JTextField(20);	//입력텍스트창
	public JTextArea  textA = new JTextArea();		//
	public JTextArea  textB = new JTextArea();

	JButton btnTransfer = new JButton("Send");
	JButton btnExit		= new JButton("Exit");
	
	JPanel panel 		= new JPanel();
	JPanel titlePanel 	= new JPanel();
	JPanel sidePanel 	= new JPanel();

	static Label  title	= new Label();
	static String other	= "";
	
	public ClientFrame(Socket socket, List<String> namelist) {
		
		super("Chatting");
		this.socket = socket;
		this.namelist = namelist;
		
		wc = new Writeclass(socket, this);
		new ListFrame(wc, this);
		jf = new JFrame("Layout");
		jf.setSize(450, 600);
		jf.setLocation(200,200);
		jf.setBackground(new Color(244, 244, 255));
		
		/*
		 * 타이틀 영역
		 * */
		title = new Label("Chat with Everybody");
		titlePanel.add(title);
		titlePanel.setFont(new Font("Dialog", Font.BOLD, 17));
		titlePanel.setBackground(new Color(240, 240, 243));
		add("North", titlePanel);
		
		/*
		 * 스크롤 영역
		 * */
		JScrollPane scrPane = new JScrollPane();
		scrPane.setBackground(new Color(244, 244, 255));
		scrPane.setBounds(0,0,200,300);
		add(scrPane);
		
		/*
		 * 텍스트 영역
		 * textA : 상대받으로부터 받은 채팅
		 * textB : 내가 보낸 채팅
		 * */
		scrPane.add(textA);
		scrPane.add(textB);
	
		textA.setBackground(new Color(244, 244, 255));
		textB.setBackground(new Color(244, 244, 255));

		// 수신, 송신 채팅영역 구분
		textA.setBounds(10,0,220,600);
		textB.setBounds(230,0,195,600);
		
		// 내가 보낸 텍스트 파란색
		textB.setForeground(Color.BLUE);
		textB.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		textA.setFont(new Font("DialogInput", Font.PLAIN, 18));
		textB.setFont(new Font("DialogInput", Font.PLAIN, 18));

		// 텍스트 영역은 직접 수정 불가능	
		textA.setEditable(false);
		textB.setEditable(false);
		
		textF.setFont(new Font("DialogInput", Font.PLAIN, 15));
		textF.setHorizontalAlignment(JTextField.LEFT);
		
		// 하단 버튼
		panel.add(textF);			//메시지입력필드
		panel.add(btnTransfer);		//전송버튼
		panel.add(btnExit);			//나가기버틍
		
		add("South", panel);
		
		// 전송, 나가기버튼에 액션 추가
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		
		setBounds(660,200,450,600);
		
		setVisible(false);
	}
	
	

	public static List<IpMember> getMemLists() {
		return memLists;
	}


	public static void setMemLists(List<IpMember> memLists) {
		ClientFrame.memLists = memLists;
	}
	

	public static List<String> getNamelist() {
		return namelist;
	}


	public static void setNamelist(List<String> namelist) {
		ClientFrame.namelist = namelist;
	}

	public static void setChatName(String Name) {
		title.setText("Private Chat with [" + Name +"]");
		other = Name;
	}

	
	
	
	/**
	 * 내가 보낸 메시지는 오른쪽부터 채우는것처럼 보이기 위해 공백으로 패딩 처리함
	 * (아직 긴 텍스트 처리 못함)
	 * @param inputString, length
	 */
	public String padLeftZeros(String inputString, int length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append(' ');
	    }
	    sb.append(inputString);

	    return sb.toString();
	}

	/**
	 * 내가 보낸 메시지는 오른쪽부터 채우는것처럼 보이기 위해 공백으로 패딩 처리함
	 * (아직 긴 텍스트 처리 못함)
	 * @param inputString, length
	 */
	public String getNewLine(String inputString, String ID) {
		String outString = "";
		int lineNum = (int) Math.ceil(inputString.length() / 12.0);
		
		for (int i = 0; i < lineNum - 1; i++) {
			outString += inputString.substring(i*12, (i+1)*12);
			outString += "[" + ID  + "]";
			outString += "\n";
		}
		// 마지막 라인은 padding 추가
		outString += padLeftZeros(inputString.substring(12*(lineNum - 1)) + "[" + ID  + "]", 17);		
		
		return outString;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnTransfer || obj == textF) {
			// 공백일때는 전송안함
			if (textF.getText().trim().equals("")) { return; }
			
			// 채팅창 아래서 적은 부분 채팅창 위에 append
			String id = IdFrame.tf.getText();
			String myText = textF.getText();
			
			// private message
			if (!other.equals("")) {
				textB.append(getNewLine(textF.getText(), id)+ "\n");
				wc.sendMessage(other);
			} else {
				textB.append(getNewLine(textF.getText(), id)+ "\n");
				wc.sendMessage();
			}

			
			// 내가 전송한 메시지 아래쪽에 상대방 메시지 보이게 하기위해 함께 개행 처리
			textA.append("\n");
			//메시지전송
			//wc.sendMessage();
			
			//전송하면 초기화
			textF.setText("");
			
		} else if (obj == btnExit) {
			
			isExit = true;
			wc.sendMessage();
			this.dispose();
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		}
	}
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
