
import java.awt.Point;

public class Pacman
{

    BoardStructure boardStructure;
    boolean isFreeze;
    Point currentPosition=new Point();
    int type;
    int player;
    int id;
    int nxtDir;
    int FreezSlot;
    int power;

    public Pacman( BoardStructure boardStructure ) {
        this.boardStructure=boardStructure;
    }

    public Pacman(boolean isFreeze, int type, int player, int id, int nxtDir, int FreezSlot) {
        this.isFreeze = isFreeze;
        this.type = type;
        this.player = player;
        this.id = id;
        this.nxtDir = nxtDir;
        this.FreezSlot = FreezSlot;
    }


    void setDir(int dir)
    {
        nxtDir=dir;
    }

    Point getNextPosition(int dir)
    {
        Point nextPosition=new Point();

        int dx[]={-1,+1,0,0,0};
        int dy[]={0,0,-1,+1,0};


        //wrapping

        nextPosition.x = (currentPosition.x + dx[ dir ]+constant.gridWidth)%constant.gridWidth; ;
        nextPosition.y = (currentPosition.y + dy[ dir ]+constant.gridHeight)%constant.gridHeight ;

        return nextPosition;
    }

    void updateDir()
    {

        if(nxtDir==4)return;
        Point nextPosition = getNextPosition(nxtDir);
        if(boardStructure.bombFieldCheck(currentPosition)==true)
        {
         //   System.out.println("ei jaigay asce...");
            nxtDir = 4; // freez move
            return;
        }
        if( boardStructure.isFree(nextPosition) == false || boardStructure.isOccupied( nextPosition )==true )
        {
            nxtDir=getRandomDir( nxtDir  );
        }
    }

    public int getRandomDir( int curDir )
    {


        //return (int)(Math.random()*10)%4;

        Point nextPosition;

        int retDir;

        int tempDir=(int)(Math.random()*10)%4;


        retDir=tempDir;

        nextPosition = getNextPosition(retDir);
        if( boardStructure.isFree(nextPosition)==true  && boardStructure.isOccupied( nextPosition )==false  )
        {
            boardStructure.addPenalty( this.player );
            return retDir;
        }

        for(retDir=(tempDir+1)%4 ; retDir!=tempDir ; retDir=(retDir+1)%4)
        {
            nextPosition = getNextPosition(retDir);
            if( boardStructure.isFree(nextPosition)==true  && boardStructure.isOccupied( nextPosition )==false  )
            {
                boardStructure.addPenalty( this.player );
                return retDir;
            }
        }


        return 4;

    }

    public void updatePosition()
    {
        boardStructure.grid[ currentPosition.x ][ currentPosition.y ].pacman=0;
        currentPosition=getNextPosition(nxtDir);
        boardStructure.grid[ currentPosition.x ][ currentPosition.y ].pacman=1;
    }

    public void updateFood()
    {
        boardStructure.updateFood( player , currentPosition ,this );
    }


    public void updateSpecialPower()
    {

        int x=currentPosition.x;
        int y=currentPosition.y;

        for(int dx=-constant.bombRange;dx<=constant.bombRange;dx++)
        {
            for(int dy=-constant.bombRange;dy<=constant.bombRange;dy++)
            {
                Point point=new Point( (x+dx+constant.gridWidth)%constant.gridWidth , (y+dy+constant.gridHeight)%constant.gridHeight );
                {
                    boardStructure.grid[ point.x ][ point.y ].power= this.power ;
                }
            }
        }

    }


}
