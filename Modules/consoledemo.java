import java.io.Console;
public class consoledemo 
{
    public static void main(String a[])throws Exception
    {
        // String dir="D:\\java";
        // String directory = "cd /d \"" + dir + "\"";
        // String command = "java Pattern";
        // System.out.println(""+directory);
        // ProcessBuilder p=new ProcessBuilder("cmd.exe","/k","start",directory); 
        
        ProcessBuilder p=new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe","https://codeeditorgpm.github.io/Login/"); 
        Process pr=p.start();
        pr.waitFor();
        
        //Runtime.getRuntime().exec(new String[]{"cmd.exe","/k","start",dir+" & "+command});



        // Console c=System.console();
        // String str= c.readLine();
        // System.out.println(str);
    }
}