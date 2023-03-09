package chatting_app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class server implements ActionListener{
	//declaring things globally
	JTextField text ;
	JPanel al;
	static Box vertical = Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dout;
	
	server(){
		f.setLayout(null);
		
		//appbar 
		JPanel pl = new JPanel();
		pl.setBackground(new Color(7,94,84));
		pl.setBounds(0,0,450,70);
		pl.setLayout(null);
		f.add(pl);
		
		//showing icon on appbar(back button)
		ImageIcon iI =new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i1 = iI.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(i1);
		JLabel back = new JLabel(i2);
		back.setBounds(5, 20, 25, 25);
		pl.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		//showing profile photo on appbar
		ImageIcon i4 =new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		pl.add(profile);
		
		
		//showing video icon on appbar
		ImageIcon i7 =new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(300, 20, 30, 30);
		pl.add(video);
		
		//showing profile phone icon on appbar
		ImageIcon i10 =new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(360, 20, 35, 30);
		pl.add(phone);
		
		//showing morevert icon on appbar
		ImageIcon i13 =new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel morevert = new JLabel(i15);
		morevert.setBounds(420, 20, 10, 25);
		pl.add(morevert);
		
		//showing name on appbar
		JLabel name = new JLabel("Gaitonde");
		name.setBounds(110,15,100,18);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SARIF",Font.BOLD,18));
		pl.add(name);
		
		//showing status of the person
		JLabel status = new JLabel("Active now");
		status.setBounds(110,35,100,18);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SARIF",Font.BOLD,14));
		pl.add(status);
		
		
		
		
		// text area border type different panel
		al = new JPanel();
		al.setBounds(5, 75 ,440, 570);
		f.add(al);
		
		//making textfield for typing message
		text = new JTextField();
		text.setBounds(5, 655, 310 ,40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);
		
		//making a send button near the text field
		JButton send = new JButton("Send");
		send.setBounds(320,655,123,40);
		send.setBackground(new Color(7,94,84));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
		send.addActionListener(this);
		f.add(send);
		
		//setting frame size
		f.setSize(450,700);
		f.setLocation(200, 50);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		
		f.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae) {
		//get the content written in textfield
		try {
		String out = text.getText();
		System.out.println(out);
		
		JPanel p2 = formatLabel(out);
		
		
		//show the message sent
		al.setLayout(new BorderLayout());
		JPanel right = new JPanel (new BorderLayout());
		right.add(p2, BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		al.add(vertical, BorderLayout.PAGE_START);
		
		//to empty the text after sending the message
		text.setText("");
		
		//sending message
		dout.writeUTF(out);
		
		//to reload a show the message
		f.repaint();
		f.invalidate();
		f.validate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static JPanel formatLabel(String out) {
		//showing the message look
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS ));
		JLabel output = new JLabel("<html><p style=\"width:150px \">"+out + "</html>");
		output.setFont(new Font("Tahoma",Font.PLAIN,16));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		panel.add(output);
		
		//to show current time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
		
	}
	public static void main(String[] args) {
		new server();
		
		//here we are socket transition to send messages using socket programming
		try {
			ServerSocket skt = new ServerSocket(6001);
			//to run infinite connection
			while(true) {
				Socket s = skt.accept();
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				//to read messages
				while(true) {
					String msg = din.readUTF();
					JPanel panel = formatLabel(msg);
					//adding recived meesage in left
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
