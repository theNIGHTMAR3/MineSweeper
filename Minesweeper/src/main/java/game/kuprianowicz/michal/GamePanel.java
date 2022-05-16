package game.kuprianowicz.michal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GamePanel extends JPanel implements ActionListener
{

    //constant values
    private static final int TILE_SIZE=30;
    private static final int WIDTH_TILES=18;
    private static final int HEIGHT_TILES=14;
    private static final int SCREEN_WIDTH=TILE_SIZE*WIDTH_TILES;
    private static final int SCREEN_HEIGHT=TILE_SIZE*HEIGHT_TILES;
    private static final int GAME_TILES=WIDTH_TILES*HEIGHT_TILES;
    private static final int TOTAL_BOMBS=30;
    private boolean running=false;
    //private boolean victory=false;

    private ArrayList<Point> bombs;
    private final ArrayList<Tile> map;

    private Model model;

    private static final int DELAY=50;



    //TODO implement
    Timer timer;
    Random random;


    //constructor
    public GamePanel(Model model)
    {
        //initialize random engine
        random = new Random();
        map=new ArrayList<>();

        this.model=model;

        startGame();

        //set dimensions for the window
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

        this.setBackground(Color.gray);
        this.setFocusable(true);

        this.addMouseListener(new MyMouseAdapter(this));
        //this.setBorder(BorderFactory.createEmptyBorder(30,30,100,10));

    }

    //start the game
    public void startGame()
    {
        running=true;
        generateBombs();
        generateMap();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //generate coordinates for the bombs and add them to list
    public void generateBombs()
    {
        bombs= new ArrayList<>();

        for(int i=0;i<TOTAL_BOMBS;i++)
        {
            int newX;
            int newY;
            Point point;

            //generate until found good position
            do
            {
                newX=random.nextInt(SCREEN_WIDTH/TILE_SIZE);
                newY=random.nextInt(SCREEN_HEIGHT/TILE_SIZE);
                point=new Point(newX,newY);

            }while(!isEmpty(point));

            bombs.add(point);
            map.add(new Tile(newX,newY,true,-1,TILE_SIZE));
        }
    }

    //generates game map
    public void generateMap()
    {
        for(int i =0;i<SCREEN_WIDTH/TILE_SIZE;i++)
        {
            for(int j=0;j<SCREEN_HEIGHT/TILE_SIZE;j++)
            {
                Point newPoint=new Point(i,j);

                //this tile doesn't have a bomb
                if(isEmpty(newPoint))
                {
                    int tempNumber=howManyBombsAround(newPoint);

                    map.add(new Tile(i,j,false,tempNumber,TILE_SIZE));
                }

            }

        }
    }

    //checks if this tile already has a bomb
    @NotNull
    public Boolean isEmpty(Point point)
    {
        //if point is outside map
        if(point.x<0 || point.y<0 || point.x>WIDTH_TILES || point.y>HEIGHT_TILES)
            return true;
        //if bombs array is empty
        if(bombs.size()==0)
            return true;

        for(Point bomb : bombs)
        {
            //if found the bomb
            if(bomb.equals(point))
                return false;
        }
        //if bomb hasn't been found
        return true;
    }

    //returns number of bombs around given point
    public int howManyBombsAround(Point point)
    {
        int value=0;
        //up
        if(!isEmpty((new Point(point.x,point.y-1))))
            value++;
        //down
        if(!isEmpty((new Point(point.x,point.y+1))))
            value++;
        //left
        if(!isEmpty((new Point(point.x-1,point.y))))
            value++;
        //right
        if(!isEmpty((new Point(point.x+1,point.y))))
            value++;
        //upper left
        if(!isEmpty((new Point(point.x-1,point.y-1))))
            value++;
        //upper right
        if(!isEmpty((new Point(point.x+1,point.y-1))))
            value++;
        //bottom left
        if(!isEmpty((new Point(point.x-1,point.y+1))))
            value++;
        //bottom right
        if(!isEmpty((new Point(point.x+1,point.y+1))))
            value++;

        return value;
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    //draws game map on the frame
    public void draw(Graphics g)
    {
        if(checkIfWon())
            gameWon(g);
        else if(running)
        {
            //draw grid
            for (int i = 0; i < SCREEN_WIDTH; i += TILE_SIZE)
            {
                g.drawLine(i, 0, i, SCREEN_HEIGHT);
            }
            for (int i = 0; i < SCREEN_HEIGHT; i += TILE_SIZE)
            {
                g.drawLine(0, i, SCREEN_WIDTH, i);
            }

            //dram tile map
            for (Tile tile : map)
            {
                tile.drawTile(g);
            }

        }
        else
        {
            gameOver(g);
        }


    }

    //checks if game is won - every tile without bomb is uncovered
    public Boolean checkIfWon()
    {
        boolean won=true;
        for(Tile tile : map)
        {
            if(!tile.getHasBomb() && tile.getCovered())
            {
                won=false;
                break;
            }
        }
        return won;
    }

    //displays when game is won
    public void gameWon(Graphics g)
    {
        g.setColor(Color.green);
        g.setFont(new Font("Ink Free", Font.BOLD, 100));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Victory!!!",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }

    //displays when game is lost
    public void gameOver(Graphics g)
    {
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 100));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }

    //return game Tile with given coordinates
    public Tile findTileWithCoordinates(int x, int y)
    {
        for(Tile tile : map)
        {
            //if founds tile, returns it
            if(tile.getX()==x && tile.getY()==y)
                return tile;
        }
        //if not returns null
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }


    //return neighboring tiles to given tile
    public List<Tile> neighboringTiles(Tile centerTile,Boolean corners)
    {
        List<Tile> closeTiles=new ArrayList<>();

        for(Tile tile : map)
        {
            //difference between coordinates
            int xDiff= centerTile.getX()-tile.getX();
            int yDiff= centerTile.getY()-tile.getY();

            //distance on 2d plain
            double distance=sqrt(pow(xDiff,2)+pow(yDiff,2));

            //1 -> tile has same border, sqrt(2) -> tile has same corner
            if(distance==1)
                closeTiles.add(tile);
            else if(corners && distance==sqrt(2))
                closeTiles.add(tile);

        }
        return closeTiles;
    }


    public void setRunning(Boolean running) {
        this.running = running;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
    public static int getTotalBombs() {
        return TOTAL_BOMBS;
    }
    public ArrayList<Tile> getMap() {
        return map;
    }
}
