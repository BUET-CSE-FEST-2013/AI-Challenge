
import java.awt.Point;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Ghost
{


    BoardStructure boardStructure;

    //boolean isFreeze;
    Point currentPosition=new Point();
    int type;
    int player;
    int id;
    int nxtDir;
    //int FreezSlot;
    int setBomb;
    int waitTimeToSetBomb;


    public Ghost( BoardStructure boardStructure ) {
        this.boardStructure=boardStructure;
    }

    public Ghost(BoardStructure boardStructure, int type, int player, int id, int nxtDir, int setBomb, int waitTimeToSetBomb) {
        this.boardStructure = boardStructure;
        this.type = type;
        this.player = player;
        this.id = id;
        this.nxtDir = nxtDir;
        this.setBomb = setBomb;
        this.waitTimeToSetBomb = waitTimeToSetBomb;
    }


    void setDir(int nxtDir)
    {
        this.nxtDir=nxtDir;
    }

    void setBomb( int setBomb )
    {
        this.setBomb=setBomb;
    }

    Point getNextPosition(int dir)
    {
        Point nextPosition=new Point();

        int dx[]={-1,+1,0,0,0};
        int dy[]={0,0,-1,+1,0};


        //no wrapping


     //   System.out.println(dir);



        nextPosition.x = currentPosition.x + dx[ dir ];
        nextPosition.y = currentPosition.y + dy[ dir ];

        return nextPosition;
    }

    void updateDir()
    {

        if(nxtDir==4)return;

        if(boardStructure.powerFieldCheck(currentPosition)!=false)
        {
            nxtDir = 4; // freez move
            return;
        }
        Point nextPosition = getNextPosition(nxtDir);
        if( boardStructure.isFree(nextPosition) == false || boardStructure.isOccupied( nextPosition )==true )
        {
            nxtDir=getRandomDir( nxtDir  );
        }
    }

    public int getRandomDir( int curDir )
    {

        Point nextPosition;

        int retDir;

        int tempDir=(int)(Math.random()*10)%4;


        retDir=tempDir;

        nextPosition = getNextPosition(retDir);
        if( boardStructure.isFree(nextPosition)==true && boardStructure.isOccupied( nextPosition )==false   )
        {
            boardStructure.addPenalty( this.player );
            return retDir;
        }

        for(retDir=(tempDir+1)%4 ; retDir!=tempDir ; retDir=(retDir+1)%4)
        {
            nextPosition = getNextPosition(retDir);
            if( boardStructure.isFree(nextPosition)==true && boardStructure.isOccupied( nextPosition )==false  )
            {
                boardStructure.addPenalty(this.player);
                return retDir;
            }
        }

        return 4;

    }


    public void updateBomb()
    {
		if( boardStructure.powerFieldCheck(currentPosition)!=false )return;
        if( waitTimeToSetBomb!=0 )return;
        if( setBomb==0 )return;


       // System.out.println( " settting bom.. " + waitTimeToSetBomb);


        for (int i = -constant.bombRange; i <= +constant.bombRange; i++) {
            for (int j = -constant.bombRange; j <= +constant.bombRange; j++) {
                Point bombPoint=new Point(i+currentPosition.x,j+currentPosition.y);
                //if( boardStructure.isFree( bombPoint  ) == false )continue;
                if( boardStructure.isInGrid(bombPoint) == false)continue;

                boardStructure.grid[ bombPoint.x ][ bombPoint.y ].bomb=constant.bombDuration;
                boardStructure.grid[ bombPoint.x ][ bombPoint.y ].obstacle=0;
             //   if(  boardStructure.grid[ bombPoint.x ][ bombPoint.y ].obstacle!=0 )boardStructure.grid[ bombPoint.x ][ bombPoint.y ].obstacle=constant.obstacleFreezDuration;
            }
        }


        waitTimeToSetBomb=constant.waitTimeToSetBomb;

    }

    public void updatePosition()
    {
        boardStructure.grid[ currentPosition.x ][ currentPosition.y ].ghost=0;
        currentPosition=getNextPosition(nxtDir);
        boardStructure.grid[ currentPosition.x ][ currentPosition.y ].ghost=1;
    }

}
