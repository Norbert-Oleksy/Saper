import java.awt.EventQueue;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import java.awt.Panel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class main {
	
	int[][] Tab = new int[16][16];
	ArrayList<JButton> ListB = new ArrayList<>();
	int BorderMax=4;
	int ButtonNr=0;
	int BombNr=2;
	int EmptyB=0;
	int LevelG=4;
	int FontSize=8;
	
	public void Reset() {
		panelButtons.removeAll();
		INFOScreen.setForeground(Color.BLACK);
		ListB.clear();
		for(int y=0;y<16;y++) {
			for(int x=0;x<16;x++) {
				Tab[y][x]=0;
			}
		}
	}
	
	public void SetGameSiez() {
		String size= String.valueOf(SIZEBox.getSelectedItem());
		switch(size) {
			case "4x4":
				BorderMax=4;
				FontSize=32;
				ButtonNr=BorderMax*BorderMax;
				break;
			case "8x8":
				BorderMax=8;
				FontSize=16;
				ButtonNr=BorderMax*BorderMax;
				break;
			case "16x16":
				BorderMax=16;
				FontSize=8;
				ButtonNr=BorderMax*BorderMax;
				break;
		}
	}
	
	public void SetGameLevel() {
		String level= String.valueOf(LVLBox.getSelectedItem());
		switch(level) {
			case "EASY":
				LevelG=5;
				BombNr=ButtonNr/LevelG;
				EmptyB=ButtonNr-BombNr;
				break;
			case "MEDIUM":
				LevelG=4;
				BombNr=ButtonNr/LevelG;
				EmptyB=ButtonNr-BombNr;
				break;
			case "HARD":
				LevelG=3;
				BombNr=ButtonNr/LevelG;
				EmptyB=ButtonNr-BombNr;
				break;
		}
		INFOScreen.setText(String.valueOf(EmptyB));
	}
	
	public void PlantingBombs() {
		Random random = new Random();
		int bm=0;
		int br=BorderMax-1;
		int x,y;
		while(bm<BombNr) {
			x=random.nextInt(br);
			y=random.nextInt(br);
			if(Tab[y][x]!=9){
				Tab[y][x]=9;
				bm++;
			}
		}
		for(int a=0;a<BorderMax;a++) {
			for(int b=0;b<BorderMax;b++) {
				if(Tab[a][b]!=9) {
				Tab[a][b]=Surroundings(a,b);
				}
			}
		}
	}
	
	public int Surroundings(int y, int x) {

		int result=0;
		int xmin;
		int ymin;
		int xmax;
		int ymax;
		if(x>0) {
			xmin=x-1;
		}
		else {
			xmin=x;
		}
		if(y>0) {
			ymin=y-1;
		}
		else {
			ymin=y;
		}
		if(x<BorderMax-1) {
			xmax=x+1;
		}
		else {
			xmax=x;
		}
		if(y<BorderMax-1) {
			ymax=y+1;
		}
		else {
			ymax=y;
		}
		
		for(int a=ymin;a<=ymax;a++) {
			for(int b=xmin;b<=xmax;b++){
				if(Tab[a][b]==9) result++;
			}
		}
		return result;
	}
	
	public int GetButtonValue(int id) {
		int y=id/BorderMax;
		int x=id%BorderMax;
		int vl=Tab[y][x];
		return vl;
	}
	
	public void ButtonsGenerator() {
		panelButtons.setLayout(new GridLayout(BorderMax, BorderMax));
		for(int i=0;i<ButtonNr;i++) {
			JButton button = new JButton();
			button.setToolTipText(String.valueOf(i));
			button.setBackground(Color.LIGHT_GRAY);
			button.setFont(new Font("Tahoma", Font.BOLD, FontSize));
			button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	ButtonAction(Integer.parseInt(button.getToolTipText()));
                }
            });
			ListB.add(button);
			panelButtons.add(button);
		}
	}
	
	public void BlockButton(int id) {
		JButton button = ListB.get(id);
		for(int i=0;i<ButtonNr;i++) {
			if(i!=id) {
				button = ListB.get(i);
				button.setEnabled(false);
			}
		}
	}
	
	
	
	public void ButtonAction(int id) {
		int valueB=GetButtonValue(id);
		JButton button = ListB.get(id);
		button.setBackground(Color.WHITE);
		if(valueB==0) {
			button.setText(String.valueOf(valueB));
			EmptyB=EmptyB-1;
			INFOScreen.setText(String.valueOf(EmptyB));
			button.setEnabled(false);
		}
		else if(valueB>0&&valueB<9) {
			button.setText(String.valueOf(valueB));
			EmptyB=EmptyB-1;
			INFOScreen.setText(String.valueOf(EmptyB));
			button.setEnabled(false);
		}
		else if(valueB==9) {
			INFOScreen.setText("You LOSE");
			INFOScreen.setForeground(Color.RED);
			button.setText("X");
			button.setForeground(Color.RED);
			BlockButton(id);
		}
		else {
			button.setText("ERROR");
		}
		if(EmptyB==0) {
			INFOScreen.setText("You WIN");
			INFOScreen.setForeground(Color.GREEN);
			BlockButton(id);
		}
	}
	
	public void StartGame() {
		Reset();
		SetGameSiez();
		SetGameLevel();
		ButtonsGenerator();
		PlantingBombs();
		panelButtons.revalidate();
	}
	
	
	
	
	
	
	private JFrame frmMinesweeper;
	private JTextField INFOScreen;
	private JComboBox<String> LVLBox;
	private JComboBox<String> SIZEBox;
	private Panel panelButtons;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frmMinesweeper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMinesweeper = new JFrame();
		frmMinesweeper.setTitle("Minesweeper");
		frmMinesweeper.setBounds(100, 100, 660, 744);
		frmMinesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMinesweeper.getContentPane().setLayout(null);
		frmMinesweeper.setResizable(false);
		
		JButton StartButton = new JButton("Start");
		StartButton.setFont(new Font("TRS Million", Font.PLAIN, 32));
		StartButton.setToolTipText("");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartGame();
			}
		});
		StartButton.setBounds(0, 0, 160, 60);
		frmMinesweeper.getContentPane().add(StartButton);
		
		INFOScreen = new JTextField();
		INFOScreen.setHorizontalAlignment(SwingConstants.CENTER);
		INFOScreen.setFont(new Font("Tahoma", Font.BOLD, 20));
		INFOScreen.setEditable(false);
		INFOScreen.setBounds(160, 0, 160, 60);
		frmMinesweeper.getContentPane().add(INFOScreen);
		INFOScreen.setColumns(10);
		
		LVLBox = new JComboBox<String>();
		LVLBox.addItem("EASY"); 
		LVLBox.addItem("MEDIUM");
		LVLBox.addItem("HARD");
		LVLBox.setBounds(480, 0, 160, 60);
		frmMinesweeper.getContentPane().add(LVLBox);
		
		SIZEBox = new JComboBox<String>();
		SIZEBox.addItem("4x4");
		SIZEBox.addItem("8x8");
		SIZEBox.addItem("16x16");
		SIZEBox.setBounds(320, 0, 160, 60);
		frmMinesweeper.getContentPane().add(SIZEBox);
		
		panelButtons = new Panel();
		panelButtons.setBounds(0, 60, 640, 640);
		frmMinesweeper.getContentPane().add(panelButtons);
		panelButtons.setLayout(new GridLayout(16, 16, 0, 0));
	}
}
