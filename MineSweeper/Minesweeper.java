import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Minesweeper extends MouseAdapter implements ActionListener {

    JFrame frame = new JFrame();
    JButton reset = new JButton("Reset");
    JButton[][] buttons = new JButton[20][20];
    int[][] counts = new int[20][20];
    Container grid = new Container();
    final int MINE = -1;

    Minesweeper(){
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        frame.add(grid, BorderLayout.CENTER);
        reset.addActionListener(this);
        grid.setLayout(new GridLayout(20 ,20));

        for(int i = 0; i < buttons[0].length; ++i){
            for(int j = 0; j < buttons.length; ++j){
                buttons[i][j] = new JButton();
                //buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);
                grid.add(buttons[i][j]);
            }
        }

        createRandomMines();
    }

    private void createRandomMines(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < buttons[0].length; ++i){
            for(int j = 0; j < buttons.length; ++j){
                list.add(i * 100 + j);
            }
        }

        counts = new int[20][20];
        for(int i = 0; i < 30; ++i){
            int choice = (int)(Math.random() * list.size());
            counts[list.get(choice) / 100][list.get(choice) % 100] = MINE;
            list.remove(choice);
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for(int i = 0; i < counts[0].length; ++i){
            for(int j = 0; j < counts.length; ++j){
                if(counts[i][j] == MINE) continue;
                for(int k = 0; k < dirs.length; ++k){
                    int ii = i + dirs[k][0];
                    int jj = j + dirs[k][1];
                    if(ii >= 0 && ii < counts.length && jj >= 0 && jj < counts[0].length && counts[ii][jj] == MINE){
                        ++counts[i][j];
                    }
                }
            }
        }
    }
    private void lostGame(){
        for(int i = 0; i < buttons[0].length; ++i){
            for(int j = 0; j < buttons.length; ++j){
                if(counts[i][j] == MINE){
                    buttons[i][j].setForeground(Color.red);
                    buttons[i][j].setText("X");
                }
                buttons[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(frame, "You Lose!");
    }

    private void search(int i, int j){
        if(i < 0 || j < 0 || i >= buttons[0].length || j >= buttons.length || !buttons[i][j].isEnabled()) return;
        buttons[i][j].setEnabled(false);
        if(counts[i][j] != 0){
            buttons[i][j].setText(counts[i][j] + "");
            return;
        }
        search(i - 1, j);
        search(i + 1, j);
        search(i, j - 1);
        search(i, j + 1);
    }

    private void checkWin(){
        for(int i = 0; i < buttons[0].length; ++i){
            for(int j = 0; j < buttons.length; ++j){
                if(buttons[i][j].isEnabled() && counts[i][j] != MINE) return;
            }
        }
        JOptionPane.showMessageDialog(frame, "You Win!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(reset)){
            //reset the board
            createRandomMines();
            for(int i = 0; i < buttons[0].length; ++i){
                for(int j = 0; j < buttons.length; ++j){
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            for(int i = 0; i < buttons[0].length; ++i){
                for(int j = 0; j < buttons.length; ++j){
                    if(buttons[i][j].isEnabled() && e.getSource().equals(buttons[i][j])){
                        if(buttons[i][j].getText() == "F") buttons[i][j].setText("");
                        else buttons[i][j].setText("F");
                    }
                }
            }
        }else{
            for(int i = 0; i < buttons[0].length; ++i){
                for(int j = 0; j < buttons.length; ++j){
                    if(e.getSource().equals(buttons[i][j])){
                        if(counts[i][j] == MINE){
                            lostGame();
                            return;
                        }else{
                            search(i, j);
                        }
                    }
                }
            }
            checkWin();
        }
    }

    public static void main(String[] args){
        new Minesweeper();
    }

}
