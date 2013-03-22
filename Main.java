
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Main
{

    public static boolean checkError( String args[] )
    {

        File error = new File("error.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(error);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        if( args.length<4 )
        {
            pw.append("less than 4 arguments are given\n");
            pw.flush();
            return true;
        }

        File dir = new File(args[0]);

        if( (dir.isDirectory() && dir.exists() ) == false )
        {
            pw.append("unkonown folder reference for first player code\n");
            pw.flush();
            return true;
        }

        dir = new File(args[1]);

        if( (dir.isDirectory() && dir.exists() ) == false )
        {

            pw.append("unkonown folder reference for 2nd player code\n");
            pw.flush();
            return true;
        }

        if( (args[2].compareToIgnoreCase("java")==0 ||  args[2].compareToIgnoreCase("cpp")==0 )==false )
        {
            pw.append("unkonown language for first player code\n");
            pw.flush();
            return true;
        }

        if( (args[3].compareToIgnoreCase("java")==0 ||  args[3].compareToIgnoreCase("cpp")==0 )==false )
        {
            pw.append("unkonown language for second player code\n");
            pw.flush();
            return true;
        }


        return false;

    }

    public static void main(String args[]) throws IOException, InterruptedException
    {
        



        if( checkError(args)==true && args.length!=0 )
        {
            return;
        }

        if(args.length!=0)
        {
            constant.playerDir[0]=new File(args[0]);
            constant.playerDir[1]=new File(args[1]);
            constant.language[0]=args[2];
            constant.language[1]=args[3];
        }


        if(args.length>=5)
        {
            constant.totalMove=Integer.valueOf(args[4]);
            if(args.length==6)constant.debugMode=Integer.valueOf(args[5]);
        }


        if( args.length==0 )
        {

            constant.playerDir[0]=new File("player0");
            constant.playerDir[1]=new File("player1");
            constant.language[0]="java";
            constant.language[1]="java";
            constant.totalMove=100;

        }



        Compiler compiler = new Compiler() ;
        compiler.compile(constant.playerDir[0],constant.language[0]);
        compiler.compile(constant.playerDir[1],constant.language[1]);

        new Controller();
    }
}

class Compiler
{

    public Compiler() {
    }


    public void compile(File Directory, String Language) throws IOException, InterruptedException {



       // System.out.println(Directory.getName()+" "+Language);

        String []filenames=Directory.list();
        String cmd;

        Runtime runtime = Runtime.getRuntime();

        if( Language.compareToIgnoreCase("java")==0 )
        {
            for (int i = 0; i < filenames.length; i++)
            {
                String string = filenames[i];

                if(string.endsWith(".java"))
                {
                    //System.out.println(string);
                    cmd="javac "+"-d "+Directory.getName()+" "+Directory.getName()+File.separator+string;
                    runtime.exec(cmd);
                }
            }
        }
        else
        {
            cmd="g++ -lm -o "+Directory.getName()+File.separator+"Main"+" "+Directory.getName()+File.separator+"Main.cpp";
            runtime.exec(cmd);
        }
		
		Thread.sleep(1000);
		
		

    }



}

