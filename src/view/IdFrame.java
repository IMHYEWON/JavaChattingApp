package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.Writeclass;

public class IdFrame extends JFrame implements ActionListener{
	
	public static TextField tf = new TextField(8);
	JButton btn 			   = new JButton("등록");

	ListFrame lf;
	Writeclass wc;

	public IdFrame(Writeclass wc, ListFrame lf) {
		this.lf = lf;
		this.wc = wc;
		
		setTitle("Input ID : ");
		setLayout(null);
		
		JLabel label = new JLabel("ID : ");
		label.setBounds(45, 45, 30, 30);
		add(label);
		
		tf.setBounds(75, 45, 100, 30);
		add(tf);
		
		btn.setBounds(75, 85, 100, 30);
		btn.addActionListener(this);
		add(btn);
		
		setBounds(300, 300, 250, 200);
		setBackground(Color.LIGHT_GRAY);
		setVisible(true);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		wc.sendMessage();
		
		lf.isFirst = false;
		
		lf.setVisible(true);
				
		this.dispose();
	}
}
