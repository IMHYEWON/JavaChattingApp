package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import member.IpMember;
import view.ClientFrame;
import view.IdFrame;

public class Writeclass {
	Socket socket;
	ClientFrame cf;
	
	//public static List<IpMember> idList = new ArrayList<IpMember>();
	
	public Writeclass(Socket socket, ClientFrame cf) {
		super();
		this.socket = socket;
		this.cf = cf;
	}
	
	
	public void sendMessage() {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String msg = "";
			
			// 텍스트 필드에 입력받은 ID
			String id = IdFrame.tf.getText();

			//첫번째 전송 (ID등록인경우) -> IP, Id 함께 전송
			if(cf.isFirst) {
				Inet4Address iaddr = (Inet4Address) socket.getLocalAddress();
				String ip = iaddr.getHostAddress();
				
				pw.println(id); 
				
				msg = "[" + id + "] is in :) !!";
				msg += "/" + id + "/" + "192.168.0.106" + "/" + socket.getLocalPort() ;
			} else if (cf.isExit) {
				msg = "[" + id + "] is Out.... :( ";
			}
			//그외전송 (채팅 등록)
			else {
				msg = "["+id+"] " +  cf.textF.getText();
			}
			
			//서버로 전송
			//System.out.println("???:"+msg);
			pw.println(msg);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String other) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String msg = "";
			
			// 텍스트 필드에 입력받은 ID
			String id = IdFrame.tf.getText();
			msg = ">>" + other + ">>" + "["+id+"] " +  cf.textF.getText();

			//System.out.println("???:"+msg);
			pw.println(msg);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
