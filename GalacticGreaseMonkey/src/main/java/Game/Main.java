package Game;

import javax.swing.JFrame;
public class Main {
    public static void main(String[] args)  //static method
    {
        //System.out.println("HelloWorld");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Galactic Grease Monkey");

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
