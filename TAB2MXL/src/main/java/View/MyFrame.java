package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MyFrame implements ActionListener {
	
	JButton guitar;
	JButton bass;
	JButton drum;
	JButton submit;
	
	public MyFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		//frame.setBounds(0,0, 700, 500);
		frame.setSize(750,550);
		frame.setBackground(new Color(0xF0F4F7));
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		JPanel innerPanel = new JPanel();
		panel.setLayout(null);
		//panel.setBorder(new LineBorder(new Color(0x000000), 1, true));
		
		innerPanel.setBounds(29, 59, 567, 253);
		innerPanel.setBorder(BorderFactory.createDashedBorder(new Color(0x000000), 10, 10));
		
		
		//panel.setBounds(60, 136, 567, 253);
		panel.setBounds(31,97,638,368);
		panel.setBackground(new Color(0xFFFFFF));
		
		JLabel title = new JLabel("Tab2MXL");
		title.setFont(new Font("Arial", Font.BOLD, 24));
		JLabel subtitle = new JLabel("translate your tablature to musicXML with one button");
		title.setBounds(43,28, 367, 50);
		subtitle.setBounds(43, 60, 367, 25);
		
		
		submit = new JButton("Choose File");
		submit.setFont(new Font("Arial", Font.BOLD, 20));
		submit.setBounds(179, 185, 224, 46);
		submit.setBorder(new RoundedBorder(20));
		submit.setBackground(new Color(0xFFFFFF));
		submit.addActionListener(this);
		submit.setFocusable(false);
		
		
		
		guitar = new JButton("Guitar");
		guitar.setBackground(null);
		guitar.setBounds(9,0,94,39);
		guitar.setFont(new Font("Arial", Font.BOLD, 20));
		guitar.setBorder(null);
		guitar.addActionListener(this);
		guitar.setFocusable(false);
		
		
		bass = new JButton("Bass");
		bass.setBackground(null);
		bass.setForeground(new Color(0x828F9C));
		bass.setBounds(103,0,94,39);
		bass.setFont(new Font("Arial", Font.BOLD, 20));
		bass.setBorder(null);
		bass.addActionListener(this);
		bass.setFocusable(false);
		
		drum = new JButton("Drum");
		drum.setBackground(null);
		drum.setForeground(new Color(0x828F9C));
		drum.setBounds(197,0,94,39);
		drum.setFont(new Font("Arial", Font.BOLD, 20));
		drum.setBorder(null);
		drum.addActionListener(this);
		drum.setFocusable(false);
		
		innerPanel.setLayout(null);
		innerPanel.add(submit);
		panel.add(guitar);
		panel.add(bass);
		panel.add(drum);

		//title.setFont(new Font("Roboto", Font.BOLD, 24));
		subtitle.setFont(new Font("Roboto", Font.BOLD, 12));
		
		frame.add(panel);
		frame.add(title);
		frame.add(subtitle);
		panel.add(innerPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == guitar) {
			guitar.setForeground(new Color(0x000000));
			bass.setForeground(new Color(0x828F9C));
			drum.setForeground(new Color(0x828F9C));
		}
		else if(e.getSource() == bass) {
			guitar.setForeground(new Color(0x828F9C));
			bass.setForeground(new Color(0x000000));
			drum.setForeground(new Color(0x828F9C));
		}
		else if(e.getSource() == drum) {
			guitar.setForeground(new Color(0x828F9C));
			bass.setForeground(new Color(0x828F9C));
			drum.setForeground(new Color(0x000000));
		}
		else if(e.getSource() == submit) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			chooser.setDialogTitle("Select the tablature text file");
			
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			System.out.println(f);
		}
	}
	
}
