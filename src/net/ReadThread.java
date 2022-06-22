package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import member.IpMember;
import view.ClientFrame;
import view.ListFrame;

public class ReadThread extends Thread{
	Socket socket;
	
	ClientFrame cf;
	ListFrame lf;
	static List<IpMember> memLists;
	static List<String> namelist;
	
	boolean isFirst = true;

	public ReadThread(Socket socket, ClientFrame cf) {
		super();
		this.socket = socket;
		this.cf = cf;
		//this.lf = lf;
	}
	
	
	public static List<String> getNamelist() {
		return namelist;
	}


	public static void setNamelist(List<String> namelist) {
		ReadThread.namelist = namelist;
	}


	public ReadThread(Socket socket, ClientFrame cf, List<IpMember> memLists) {
		super();
		this.socket = socket;
		this.cf = cf;
		//this.lf = lf;
		this.memLists = memLists;
	}
	

	public static List<IpMember> getMemLists() {
		return memLists;
	}


	public void setMemLists(List<IpMember> memLists) {
		this.memLists = memLists;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				// 서버로부터 데이터 받는 부분
				String str = br.readLine();
				String chat = "";
				
				// data null -> 접속 끊김
				if (str == null) {
					System.out.println("접속 끊김");
				} else if (str.split(">>").length > 1) {
					cf.textA.append(str.split(">>")[2]+"\n");
					cf.textB.append("\n");
					System.out.println("case 4 : private message");					
				} else if (str.indexOf("[") < 0 && str.split("/").length > 0){
					String[] tmp = str.split("/");
					namelist = Arrays.asList(tmp);
					lf.setChatList(namelist);
					System.out.println("case 1 : namelist");
				} else if (str.indexOf("!") > -1 && str.split("/").length > 0){
					String[] tmp = str.split("/");
					cf.textA.append(tmp[0] + "\n");
					cf.textB.append("\n");					
					lf.setChatList(namelist);
					
					System.out.println("case 2 : new member is in");
				} else {
					chat = str;
					cf.textA.append(chat + "\n");
					cf.textB.append("\n");
					System.out.println("case 3 : chat message");
				}
				
				Thread.sleep(300);	
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
