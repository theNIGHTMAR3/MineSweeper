package game.kuprianowicz.michal;

import java.awt.*;

public class Tile
{
    private final int x;
    private final int y;
    private final Boolean hasBomb;
    private final int number;
    private final Color color;
    private final Font font;
    private Boolean covered;
    private Boolean flagPlaced;
    private final int TILE_SIZE;

    public Tile(int x, int y, Boolean hasBomb, int number,int TILE_SIZE)
    {
        this.x=x;
        this.y=y;
        this.hasBomb=hasBomb;
        this.number=number;
        this.covered=true;
        this.flagPlaced=false;
        this.TILE_SIZE=TILE_SIZE;

        this.font=new Font("TimesRoman", Font.PLAIN, 28);

        //set color for number
        switch(number)
        {
            case 1:
                this.color=Color.blue;
                break;
            case 2:
                this.color=Color.green;
                break;
            case 3:
            case -1:    //bomb
                this.color=Color.red;
                break;
            case 4:
                this.color=Color.cyan;
                break;
            case 5:
                this.color=Color.orange;
                break;
            case 6:
                this.color=Color.pink;
                break;
            case 7:
                this.color=Color.yellow;
                break;
            case 8:
                this.color=Color.black;
                break;
            default:
                color=null;
        }
    }

    //draw tile with given graphics
    public void drawTile(Graphics g)
    {
        if(this.covered)
        {
            //covered tile
            drawCoveredTile(g);
            //checks if flag is placed on this tile
            if(this.flagPlaced)
                drawPlacedFlag(g);
        }

        else
            drawUncoveredTile(g);
    }

    //draws covered tile
    public void drawCoveredTile(Graphics g)
    {
        int offset=3;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x*TILE_SIZE+offset,y*TILE_SIZE+offset,TILE_SIZE-offset,TILE_SIZE-offset);
    }

    //draws uncovered tile
    public void drawUncoveredTile(Graphics g)
    {
        int offset=3;
        g.setColor(color);
        if(hasBomb)
            g.fillRect(x*TILE_SIZE+offset,y*TILE_SIZE+offset,TILE_SIZE-offset-2,TILE_SIZE-offset-2);
        else if(number!=0)
        {
            g.setFont(font);
            g.setColor(color);
            g.drawString(String.valueOf(number), (int)((x+0.3)*TILE_SIZE), (int)((y+0.9)*TILE_SIZE));
        }
    }

    //draws covered tile
    public void drawPlacedFlag(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(font);
        g.drawString("F", (int)((x+0.3)*TILE_SIZE), (int)((y+0.9)*TILE_SIZE));

    }

    //left mouse click tile action
    public void leftClick()
    {
        if(this.covered)
        {
            this.covered=false;
        }
    }

    //right mouse click tile action
    public void rightClick()
    {
        //negate flagPlaced boolean
        this.flagPlaced= !this.flagPlaced;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Boolean getHasBomb() {
        return hasBomb;
    }

    public int getNumber() {
        return number;
    }
    public Color getColor() {
        return color;
    }
    public Font getFont() {
        return font;
    }
    public Boolean getCovered() {
        return covered;
    }

    public Boolean getFlagPlaced() {
        return flagPlaced;
    }

    public void setCovered(Boolean covered) {
        this.covered = covered;
    }


}
