package game.kuprianowicz.michal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MyMouseAdapter extends MouseAdapter
{

    private final GamePanel gamePanel;

    public MyMouseAdapter(GamePanel gamePanel)
    {
        this.gamePanel=gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent event)
    {
        //mouse coordinates
        int mX = (event.getX() / GamePanel.getTileSize());
        int mY = (event.getY() / GamePanel.getTileSize());


        Tile clickedTile = gamePanel.findTileWithCoordinates(mX, mY);

        //mouse left click
        if (event.getButton() == MouseEvent.BUTTON1)
        {
            //if tile has no flag
            if(!clickedTile.getFlagPlaced())
            {
                //clicked on bomb -> game over
                if(clickedTile.getHasBomb())
                {
                    gamePanel.setRunning(false);
                }
                //no bomb, perform click
                else
                {
                    //perform click
                    clickedTile.leftClick();

                    //not a bomb and number==0, recursively uncover tiles
                    if (!clickedTile.getHasBomb() && clickedTile.getNumber() == 0 && !clickedTile.getFlagPlaced())
                    {
                        cascade(clickedTile);
                    }
                }
            }
        }


        //mouse right click
        if (event.getButton() == MouseEvent.BUTTON3)
        {
            if (clickedTile.getCovered()) {
                clickedTile.rightClick();
            }
        }
    }



    public void cascade(Tile clickedTile)
    {
        List<Tile> neighboringTiles=gamePanel.neighboringTiles(clickedTile,true);

        for(Tile tile : neighboringTiles)
        {
            //not bomb, covered
            if(!tile.getHasBomb() && tile.getCovered())
            {
                tile.leftClick();
                if(tile.getNumber()==0)
                    cascade(tile);
            }
        }
    }
}
