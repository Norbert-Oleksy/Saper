import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Board {
	int[][] Tab = new int[16][16];
	ArrayList<JButton> ListB = new ArrayList<>();
	int BorderMax=4;
	int ButtonNr=0;
	int BombNr=2;
	int EmptyB=0;
	int LevelG=4;
	int FontSize=8;
	
	public Board(){}
	
	public void SetGameSiezB(String size) {
		switch(size) {
			case "4x4":
				BorderMax=4;
				FontSize=32;
				break;
			case "8x8":
				BorderMax=8;
				FontSize=16;
				break;
			case "16x16":
				BorderMax=16;
				FontSize=8;
				break;
		}
		ButtonNr=BorderMax*BorderMax;
	}

	public void ResetB() {
		ListB.clear();
		for(int y=0;y<16;y++) {
			for(int x=0;x<16;x++) {
				Tab[y][x]=0;
			}
		}
	}

	public void SetGameLevelB(String level) {
		switch(level) {
			case "EASY":
				LevelG=5;
				break;
			case "MEDIUM":
				LevelG=4;
				break;
			case "HARD":
				LevelG=3;
				break;
		}
		BombNr=ButtonNr/LevelG;
		EmptyB=ButtonNr-BombNr;
	}

	public void PlantingBombsB() {
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
	
	private int Surroundings(int y, int x) {
		int result=0;
		int xmin,ymin,xmax,ymax;
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

	private int GetButtonValue(int id) {
		int y=id/BorderMax;
		int x=id%BorderMax;
		int vl=Tab[y][x];
		return vl;
	}

	public void ButtonsGeneratorB(Panel panel, JTextField INFOScreen) {
		panel.setLayout(new GridLayout(BorderMax, BorderMax));
		for(int i=0;i<ButtonNr;i++) {
			JButton button = new JButton();
			button.setToolTipText(String.valueOf(i));
			button.setBackground(Color.LIGHT_GRAY);
			button.setFont(new Font("Tahoma", Font.BOLD, FontSize));
			button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	ButtonAction(Integer.parseInt(button.getToolTipText()), INFOScreen);
                }
            });
			ListB.add(button);
			panel.add(button);
		}
	}

	private void BlockButton(int id) {
		JButton button = ListB.get(id);
		for(int i=0;i<ButtonNr;i++) {
			if(i!=id) {
				button = ListB.get(i);
				button.setEnabled(false);
			}
		}
	}

	public void ButtonAction(int id, JTextField INFOScreen) {
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
}
