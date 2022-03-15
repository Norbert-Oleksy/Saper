import java.awt.EventQueue;
import javax.swing.JFrame;
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

public class Saper {
	
	Board board = new Board();
	
	public void Reset() {
		panelButtons.removeAll();
		INFOScreen.setForeground(Color.BLACK);
		board.ResetB();
	}
	
	public void SetGameLevel() {
		board.SetGameLevelB(String.valueOf(LVLBox.getSelectedItem()));
		INFOScreen.setText(String.valueOf(board.EmptyB));
	}
	
	public void ButtonsGenerator() {
		board.ButtonsGeneratorB(panelButtons, INFOScreen);
	}
	
	public void StartGame() {
		Reset();
		board.SetGameSiezB(String.valueOf(SIZEBox.getSelectedItem()));
		SetGameLevel();
		ButtonsGenerator();
		board.PlantingBombsB();
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
					Saper window = new Saper();
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
	public Saper() {
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
