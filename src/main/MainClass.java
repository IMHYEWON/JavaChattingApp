package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import member.IpMember;
import net.ReadThread;
import view.ClientFrame;
import view.ListFrame;

public class MainClass {

	public static void main(String[] args) {
		
		List<IpMember> memLists = new ArrayList<IpMember>();
		List<String> namelist = null;
		
		// 소켓 생성
		try {
			Socket socket = new Socket("192.168.0.106", 9000);
			System.out.println("Success!");
			
			ClientFrame cf = new ClientFrame(socket, namelist);
			new ReadThread(socket, cf).start();
			memLists = ReadThread.getMemLists();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
