package view;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import member.IpMember;
import net.Writeclass;


public class ListFrame extends JFrame implements WindowListener, ActionListener {

	static JPanel listPanel = null;
	JPanel titlePanel 		= new JPanel();
	JPanel sidePanel 		= new JPanel();
	JButton btnNewChat 		= new JButton("Create New Chat");
	static JLabel tmpLabel 	= new JLabel();
	
	Writeclass wc;
	static ClientFrame cf;
	
	static List<IpMember>	memLists;
	static List<String> 	namelist;

	// 채팅방버튼 목록 동적으로 생성 및 저장하기 위한 리스트
	static List<JButton> idList = new ArrayList<JButton>();
	// 첫번째 전송(ID등록)
	public boolean  isFirst 	= true;	

	public ListFrame(Writeclass wc, ClientFrame cf) {
		
		this.cf = cf;
		this.wc = wc;
		
	
		
		new IdFrame(wc, this);
		// 접속자 리스트
		//listPanel.removeAll();
		//namelist = ClientFrame.getNamelist();
		//setChatList(namelist);
		

		/*
		 * 타이틀 영역
		 * */
		Label title = new Label("Chat Lists");
		titlePanel.add(title);
		titlePanel.setFont(new Font("DialogInput", Font.BOLD, 18));
		titlePanel.setBackground(new Color(212, 213, 220));
		add("North", titlePanel);

		/*
		 * 목록 영역
		 * */
		//remove(listPanel);
		listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(180, 120));
		listPanel.setBackground(new Color(244, 244, 255));
		add("Center", listPanel);
		
		/*
		 * 하단 버튼 영역
		 * */
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.LIGHT_GRAY);
		btnPanel.setPreferredSize(new Dimension(200, 80));
		btnNewChat.setBackground(new Color(211, 212, 233));
		btnNewChat.addActionListener(this);
		btnNewChat.setBounds(30, 10, 100, 30);
		btnPanel.add(btnNewChat);
		add("South", btnPanel);
		
		
		/*
		 * 나를 제외하고 다른 접속자가 없을 떄 메시지 
		 * (수정 필요)
		 * */
		tmpLabel = new JLabel("Nobody here ... ");
		tmpLabel.setBounds(10, 50, 100, 30);
		tmpLabel.setFont(new Font("DialogInput", Font.PLAIN, 20));
		listPanel.add(tmpLabel);
		tmpLabel.setVisible(false);
		
		setBackground(Color.WHITE);
		setBounds(200, 200, 450, 600);
		setVisible(false);
		
	}
	
	
	public static List<IpMember> getMemLists() {
		return memLists;
	}

 
	public static void setMemLists(List<IpMember> memLists) {
		ListFrame.memLists = memLists;
	}
	

	/**
	 * 클라이언트 프레임으로 부터 전달 받은 클라 목록으로 버튼을 생성합니다
	 * 
	 * @param namelist
	 */
	public static void setChatList(List<String> namelist) {
		
		JButton tmpBtn = null;
		listPanel.removeAll();
		String me = namelist.get(namelist.size()-1);
		
		if (namelist != null) {
			int w = 0;
			for (String name : namelist) {
				if (me.equals(name)) return;
				tmpBtn = new JButton(name + "님과의 채팅을 시작해보세요");
				tmpBtn.setBounds(30, 30 + (w*30), 100, 30);
				tmpBtn.setHorizontalAlignment(SwingConstants.LEFT);
				//tmpBtn.setForeground(new Color(212, 213, 240));
				tmpBtn.setBackground(new Color(212, 213, 220));
				tmpBtn.setHorizontalAlignment(JButton.LEFT);
				tmpBtn.setFont(new Font("DialogInput", Font.PLAIN, 14));
				
				// 이벤트리스너 붙이기
				tmpBtn.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
						
						// 이미 다른 채팅창이 켜져있는 경우 > 초기화
						if (cf.isVisible()) {
							cf.dispose();
							cf.textA.setText("");
							cf.textB.setText("");
						}
						cf.setVisible(true);
						cf.setChatName(name);
					}
				});
				
				idList.add(tmpBtn);
				listPanel.add(tmpBtn);
				w++;
			}	
			
			tmpLabel.setVisible(false);
		} else {

			// 접속 후에는 (Nobody here 메시지 숨김할 수 있도록)
			tmpLabel.setVisible(true);
		}

	}

	
	 public ActionListener addListener(JButton btn) {
		 ActionListener listener = new ActionListener() {
		
	     @Override
	     public void actionPerformed(ActionEvent e) {
	    	 cf.setVisible(true);
	    	 System.out.println("개인채팅 클릭");
	    	 Object obj = e.getSource();
	    	 if(idList.size() > 0) {
	    		 if(idList.indexOf(obj) > -1) {
	    			 System.out.println("개인채팅 클릭");
	    			 cf.setVisible(true);
	    			 }
	    		 }
	     	}
		 };
		 
		// btn.addActionListener(listener);
		 return listener;
	 }
	

	@Override
	public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	
    	cf.setVisible(true);
        // btnPlus를 선택했을때 
        if(btnNewChat.equals(obj)){
			wc.sendMessage();
			cf.isFirst = false;
			cf.setVisible(true);
        }
		/*
		 * if(idList.size() > 0) { if(idList.get(0).equals(obj)){
		 * System.out.println("개인채팅 클릭"); cf.setVisible(true); } }
		 */

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
