package hufs.ces.puzzle3x3;

import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Puzzle3x3Controller {

	private int[] permu;
	private int[][] canMove;
	private final int TRYMAX = 100;
	
	private Button[] btnPuzzle = null;
	private int whitePos;

    @FXML
    private Button btn00;

    @FXML
    private Button btn01;

    @FXML
    private Button btn02;

    @FXML
    private Button btn10;

    @FXML
    private Button btn11;

    @FXML
    private Button btn12;

    @FXML
    private Button btn20;

    @FXML
    private Button btn21;

    @FXML
    private Button btn22;

    @FXML
    private Button btnStart;
    
    @FXML
    void initialize() {
		btnPuzzle = new Button[] {
				btn00, btn01, btn02,
				btn10, btn11, btn12,
				btn20, btn21, btn22
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
		for (int i = 0; i <btnPuzzle.length; ++i) {
			btnPuzzle[i].setOnAction(e->doButtonAction(e));
		}
    }
    @FXML
    void startNewGame(ActionEvent event) {
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
	private void doButtonAction(Event e) {
		Button btn = (Button)e.getSource();
		String btnId = btn.getId();
		int btnPos = (btnId.charAt(3)-'0')*3 + (btnId.charAt(4)-'0');
		//System.out.println("pos="+btnPos);
		setWhitePosition(btnPos);
	}
	private void setWhitePosition(int btnPos) {
		if (!isEnabled(btnPos))
			return;
		
		swapPermute(btnPos, whitePos);
		whitePos = btnPos;
		//setDisableAll();
		setButtonImage();
		if (isEndCondition()) {
			setDisableAll();
			return;
		}

		//for (int c=0; c<canMove[btn].length; ++c) {
		//	btnPuzzle[canMove[btn][c]].setEnabled(true);
		//}
	}
	private void swapPermute(int i, int j) {
		int temp = permu[i];
		permu[i] = permu[j];
		permu[j] = temp;
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

	private void setDisableAll() {
		for (int i=0; i<btnPuzzle.length; ++i){
			btnPuzzle[i].setDisable(true);
		}
	}
	private void setEnableAll() {
		for (int i=0; i<btnPuzzle.length; ++i){
			btnPuzzle[i].setDisable(false);
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


	
}
