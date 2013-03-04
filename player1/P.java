

import java.awt.Point;
import java.io.*;
import java.util.*;
class Main
{
    static int EngineMoveId;
    static Point[][] ghosts = new Point[2][3];
    static Point[][] pacs = new Point[2][2];
    static int[][][][] pacsVisibility= new  int[2][4][7][7];
    static int[][][][] ghostsVisibility= new  int[3][4][7][7];
    static int score[]=new int[2];



     public static void main(String args[])throws Exception
     {


        Scanner sc = new Scanner(System.in);


        while(true)
        {
            
            EngineMoveId = sc.nextInt();

            score[0]=sc.nextInt();
            score[1]=sc.nextInt();

            Point p = new Point();

            for(int playerId=0;playerId<2;playerId++)
            {
                for(int pacId=0;pacId<2;pacId++)
                {
                    p.x=sc.nextInt();
                    p.y=sc.nextInt();

                    pacs[playerId][pacId]=p;
                }
                
                for(int ghostId=0;ghostId<3;ghostId++)
                {
                    p.x=sc.nextInt();
                    p.y=sc.nextInt();
                    
                    ghosts[playerId][ghostId]=p;
                }

            }

            /*
             *k=0 , food array
             *k=1 , bomb array
             *k=2 , power array
             *k=3 , obstacle array
             */


            for(int pacId=0;pacId<2;pacId++)
            {


                for(int k=0;k<4;k++)
                {
                    for(int i=0;i<7;i++)
                    {
                        for(int j=0;j<7;j++)
                        {

                            pacsVisibility[pacId][k][i][j]=sc.nextInt();

                        }
                    }
                }

            }

            for(int ghostId=0;ghostId<3;ghostId++)
            {


                for(int k=0;k<4;k++)
                {
                    for(int i=0;i<7;i++)
                    {
                        for(int j=0;j<7;j++)
                        {

                            ghostsVisibility[ghostId][k][i][j]=sc.nextInt();

                        }
                    }
                }

            }

            Processor processor = new Processor();
            String move = processor.getRandomMove( );

            System.out.println(move);
            System.out.flush();
         }

    }

}


class Processor
{

    public Processor() {
    }


    String getRandomMove(  )
    {
        String move ="";


        int dirId;
        char dir[] = { 'L' , 'R' , 'U' , 'D' , 'F' };

        for (int i = 0; i < 5 ; i++) {
            dirId=((int) (Math.random() * 100) % 5);
            move +=dir[dirId]+" ";
        }

        //wrinting bombing decision

        int isBomb;
        char dcsn[]={'Y','N'};

        for (int i = 0; i < 3 ; i++) {
            isBomb=((int) (Math.random() * 100) % 2);
            move +=dcsn[isBomb]+" ";
        }

        return move;
    }



}
