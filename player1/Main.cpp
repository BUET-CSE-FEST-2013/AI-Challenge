#include<iostream>
#include<string>
#include<cstdio>
#include<cstdlib>
#include<string>
using namespace std;



class Processor
{

    public:

    Processor() {}


    string getRandomMove( )
    {
        string move ="";


        int dirId;
        char dir[] = { 'L' , 'R' , 'U' , 'D' , 'F' };

        for (int i = 0; i < 5 ; i++) {
            dirId=(rand() % 5);
            move =move+dir[dirId];
            move=move+" ";
        }


        //wrinting bombing decision

        int isBomb;
        char dcsn[]={'Y','N'};

        for (int i = 0; i < 3 ; i++) {
            isBomb=(rand() % 2);
            move =move+dcsn[isBomb];
            move=move+" ";
        }

        return move;
    }



};


struct Point
{
    int x,y;
};

int EngineMoveId;
Point ghosts[2][3];
Point pacs[2][2];
int pacsVisibility[2][4][7][7];
int ghostsVisibility[3][4][7][7];
int score[2];

int main()
{


    Processor processor;


    while(true)
    {

        cin>>EngineMoveId;
        cin>>score[0]>>score[1];


        Point p;

        for(int playerId=0;playerId<2;playerId++)
        {
            for(int pacId=0;pacId<2;pacId++)
            {
                cin>>p.x>>p.y;
                pacs[playerId][pacId]=p;
            }

            for(int ghostId=0;ghostId<3;ghostId++)
            {
                cin>>p.x>>p.y;
                ghosts[playerId][ghostId]=p;
            }

        }


         //k=0 , food array
         //k=1 , bomb array
         //k=2 , power array
         //k=3 , obstacle array



        for(int pacId=0;pacId<2;pacId++)
        {


            for(int k=0;k<4;k++)
            {
                for(int i=0;i<7;i++)
                {
                    for(int j=0;j<7;j++)
                    {

                        cin>>pacsVisibility[pacId][k][i][j];

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

                        cin>>ghostsVisibility[ghostId][k][i][j];

                    }
                }
            }

        }

        cout<<processor.getRandomMove()<<endl;


    }

    return 0;




}

