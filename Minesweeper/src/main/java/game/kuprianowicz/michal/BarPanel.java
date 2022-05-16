package game.kuprianowicz.michal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BarPanel extends JPanel
{

    //private final GamePanel gamePanel;
    Model model;

    private int bombsLeft;
    private int time;

    private final int TILE_SIZE;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;


    public BarPanel(Model model)
    {
        this.model=model;
        //this.gamePanel=gamePanel;
        this.bombsLeft=GamePanel.getTotalBombs();
        this.TILE_SIZE= GamePanel.getTileSize();
        this.SCREEN_WIDTH= GamePanel.getScreenWidth();
        this.SCREEN_HEIGHT= GamePanel.getScreenHeight();
        this.setMinimumSize(new Dimension(SCREEN_WIDTH,200));
        this.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }


    //draws bar on the frame
    public void draw(Graphics g)
    {
        bombsLeft=GamePanel.getTotalBombs()-countFlags();

        String information="Bombs left: ";
        information+=bombsLeft;
        information+="          ";
        information+="time: ";

        this.setBackground(Color.LIGHT_GRAY);

        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString(information,(SCREEN_WIDTH-metrics.stringWidth(information))/2,30);

    }

    //counts how many flag have been placed
    public int countFlags()
    {
        int count=0;
//        for(Tile tile : gamePanel.getMap())
//        {
//            if(tile.getFlagPlaced())
//                count++;
//        }
        return count;
    }

    //updates bar panel
    public void updateBarPanel()
    {
        repaint();
    }

    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
