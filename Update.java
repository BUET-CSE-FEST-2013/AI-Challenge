
public class Update {

    BoardStructure boardStructure;

    public Update()
    {
    }

    public Update( BoardStructure boardStructure )
    {
        this.boardStructure=boardStructure;
    }

    public void updateFreezSlot()
    {
        for (int i = 0; i < constant.gridWidth; i++) {
            for (int j = 0; j < constant.gridHeight; j++) {
                if(boardStructure.grid[i][j].bomb>0)boardStructure.grid[i][j].bomb--;
            }
        }
    }

    public void updateObstacleSlot()
    {
        for (int i = 0; i < constant.gridWidth; i++) {
            for (int j = 0; j < constant.gridHeight; j++) {
                if(boardStructure.grid[i][j].obstacle>1)boardStructure.grid[i][j].obstacle--;
            }
        }
    }


    public void specialFoodSlot()
    {


        for (int i = 0; i < constant.gridWidth; i++) {
            for (int j = 0; j < constant.gridHeight; j++) {
                boardStructure.grid[i][j].power=0;
            }
        }

        for(int playerId=0;playerId<constant.noPlayer;playerId++)
        {
            for(int pacId=0;pacId<constant.noPacman;pacId++)
            {
                if( boardStructure.pacs[ playerId ][ pacId ].power !=0 )
                {
                    boardStructure.pacs[ playerId ][ pacId ].power--;
                }

                Pacman pacman= boardStructure.pacs[ playerId ][ pacId ];
                if( pacman.power==0 )continue;

                pacman.updateSpecialPower();
            }
        }

    }



    public void updateWaitTimeToSetBomb( int playerId )
    {
        for (int gid = 0; gid < constant.noGhost; gid++) {
            Ghost ghost = boardStructure.ghosts[playerId][gid];
            if(ghost.waitTimeToSetBomb==0)return;
            ghost.waitTimeToSetBomb--;
        }
    }

    public void updateFood(int playerId)
    {
        for (int pacid = 0; pacid < constant.noPacman; pacid++)
        {
            boardStructure.pacs[playerId][pacid].updateFood();
        }
    }


}
