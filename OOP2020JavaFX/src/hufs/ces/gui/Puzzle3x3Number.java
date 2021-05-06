/**
 * Created on Sep 26, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author cskim
 *
 */
public class Puzzle3x3Number extends JFrame {

	private JPanel contentPane;

	private JButton[] btnPuzzle = null;

	private ImageIcon imageIconWhite = null;   //  @jve:decl-index=0:visual-constraint=""
	private ImageIcon[] imageMap;
	private int[] permu;
	private int[][] canMove;
	private final int TRYMAX = 100;

	private int whitePos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Puzzle3x3Number frame = new Puzzle3x3Number();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Puzzle3x3Number() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 300);
		this.setTitle("Puzzle 3x3");
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				startNewGame();
			}
		});
		contentPane.add(btnStart, BorderLayout.SOUTH);
		
		JPanel panelBtns = new JPanel();
		contentPane.add(panelBtns, BorderLayout.CENTER);
		panelBtns.setLayout(new GridLayout(3, 3, 0, 0));
		
		JButton jbt11 = new JButton("1");
		jbt11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(0);
			}
		});
		panelBtns.add(jbt11);
		
		JButton jbt12 = new JButton("2");
		jbt12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(1);
			}
		});
		panelBtns.add(jbt12);
		
		JButton jbt13 = new JButton("3");
		jbt13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(2);
			}
		});
		panelBtns.add(jbt13);
		
		JButton jbt21 = new JButton("4");
		jbt21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(3);
			}
		});
		panelBtns.add(jbt21);
		
		JButton jbt22 = new JButton("5");
		jbt22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(4);
			}
		});
		panelBtns.add(jbt22);
		
		JButton jbt23 = new JButton("6");
		jbt23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(5);
			}
		});
		panelBtns.add(jbt23);
		
		JButton jbt31 = new JButton("7");
		jbt31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(6);
			}
		});
		panelBtns.add(jbt31);
		
		JButton jbt32 = new JButton("8");
		jbt32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(7);
			}
		});
		panelBtns.add(jbt32);
		
		JButton jbt33 = new JButton("");
		jbt33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButtonAction(8);
			}
		});
		panelBtns.add(jbt33);

		btnPuzzle = new JButton[] {
				jbt11, jbt12, jbt13,
				jbt21, jbt22, jbt23,
				jbt31, jbt32, jbt33
		};
		permu = new int [] { 0, 1, 2, 3, 4, 5, 6, 7, 8};
		canMove = new int [][] {
				/* 0 */{1, 3},
				/* 1 */{0, 2, 4},
				/* 2 */{1, 5},
				/* 3 */{0, 4, 6},
				/* 4 */{1, 3, 5, 7},
				/* 5 */{2, 4, 8},
				/* 6 */{3, 7},
				/* 7 */{4, 6, 8},
				/* 8 */{5,7}
		};
		setDisableAll();
		setButtonImage();
	}
	private void startNewGame(){
		int st = 8;
		int to = 0;
		long seed = new Date().getTime();
		Random ran = new Random(seed);


		for (int tryCount = 1; tryCount < TRYMAX; ++ tryCount) {
			to = canMove[st][ran.nextInt(canMove[st].length)];
			swapPermute(st, to);
			st = to;
		}
		
		setButtonImage();
		setEnableAll();
		
		whitePos = st;
		setWhitePosition(whitePos);

	}
	private void doButtonAction(int btn) {
		setWhitePosition(btn);
	}
	private void setWhitePosition(int btn) {
		if (!isEnabled(btn))
			return;
		
		swapPermute(btn, whitePos);
		whitePos = btn;
		//setDisableAll();
		setButtonImage();
		if (isEndCondition()) {
			setDisableAll();
			return;
		}

		setButtonWhite(btnPuzzle[btn]);
		
		//for (int c=0; c<canMove[btn].length; ++c) {
		//	btnPuzzle[canMove[btn][c]].setEnabled(true);
		//}
	}
	private void setButtonWhite(JButton jbtn) {
		jbtn.setIcon(imageIconWhite);
	}
	private void swapPermute(int i, int j) {
		int temp = permu[i];
		permu[i] = permu[j];
		permu[j] = temp;
	}
	private void setDisableAll() {
		for (int i=0; i<btnPuzzle.length; ++i){
			btnPuzzle[i].setEnabled(false);
		}
	}
	private void setEnableAll() {
		for (int i=0; i<btnPuzzle.length; ++i){
			btnPuzzle[i].setEnabled(true);
		}
	}
	private void setButtonImage() {
		for (int i=0; i<btnPuzzle.length; ++i){
			//btnPuzzle[i].setIcon(imageMap[permu[i]]);
			int lnum = permu[i]+1;
			if (lnum == 9){
				btnPuzzle[i].setText("");
			} 
			else{ 
				btnPuzzle[i].setText(String.valueOf(permu[i]+1));
			}
		}
	}
	private boolean isEndCondition() {
		for (int i=0; i<9; ++i)
			if (permu[i] != i) return false;
		return true;
	}
	private boolean isEnabled(int btn){
		boolean[] result = new boolean[9];
		for (int i=0; i<9; ++i){
			result[i] = false;
		}
		for (int c=0; c<canMove[whitePos].length; ++c){
			int d = canMove[whitePos][c];
			result[d] = true;
		}
		return result[btn];
	}

}
