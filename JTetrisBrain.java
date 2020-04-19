import com.sun.deploy.panel.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JTetrisBrain extends  JTetris {

    private static Random rand;
    private JCheckBox brainBox;
    private Brain brain;
    private Brain.Move bestMove;
    private JSlider slider;
    private JLabel adversary;
    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JTetrisBrain(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        bestMove = new Brain.Move();
    }
    public  static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JTetrisBrain tetris = new JTetrisBrain(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
        rand = new Random();
    }

    @Override
    public JComponent createControlPanel() {
        JComponent panel = super.createControlPanel();
        panel.add(new JLabel("Brain:"));
        brainBox = new JCheckBox("Brain active");
        panel.add(brainBox);
        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        slider = new JSlider(0,100,0);
        slider.setPreferredSize(new Dimension(100,15));
        little.add(slider);
        adversary = new JLabel("OK");
        little.add(adversary);
        panel.add(little);
        return panel;
    }

    @Override
    public void tick(int verb) {
        if(brainBox.isSelected() && verb == DOWN) {
            if(!bestMove.piece.equals(currentPiece)) super.tick(ROTATE);
            if(currentX == bestMove.x && currentY > bestMove.y) {
                super.tick(DROP);
                board.commit();
            }
            if(bestMove.x > currentX) super.tick(RIGHT);
            if(bestMove.x < currentX) super.tick(LEFT);
        }
        super.tick(verb);
    }

    @Override
    public void addNewPiece() {
        super.addNewPiece();
        board.undo();
        bestMove = brain.bestMove(board,currentPiece,board.getHeight(),bestMove);
        if(bestMove == null) {
            gameOn = false;
        }
    }

    @Override
    public Piece pickNextPiece() {
        int a = rand.nextInt(100);
        if(a >= slider.getValue()) {
            adversary.setText("OK");
            return super.pickNextPiece();
        }
        Piece[] pics = Piece.getPieces();
        adversary.setText("*OK*");
        Piece worstPeace =  brain.bestMove(board,pics[0],board.getHeight(),null).piece;
        double worst = brain.bestMove(board,pics[0],board.getHeight(),null).score;
        for(int i = 1; i < pics.length; i++) {
            if(brain.bestMove(board,pics[i],board.getHeight(),null).score > worst) {
                worst = brain.bestMove(board,pics[i],board.getHeight(),null).score;
                worstPeace = brain.bestMove(board,pics[i],board.getHeight(),null).piece;
            }
        }
        return worstPeace;
    }
}
