package game.kuprianowicz.michal;

import javax.swing.*;

public class GameFrame extends JFrame
{
    //constructor
    public GameFrame()
    {

        //model for holding data between panels
        Model model = new Model();

        //adds pane to this frame
        GamePanel gamePanel=new GamePanel(model);
        BarPanel barPanel=new BarPanel(model);

        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));

        this.add(barPanel);
        this.add(gamePanel);

        //setting window frame
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //middle of the screen
        this.setLocationRelativeTo(null);

    }
}
