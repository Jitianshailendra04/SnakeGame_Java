
import javax.swing.JFrame;


public class GameFrame extends JFrame {

 GameFrame(){

this.add(new GamePanel());
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setResizable(false);
this.pack();
setVisible(true);
this.setLocationRelativeTo(null);
 }
}