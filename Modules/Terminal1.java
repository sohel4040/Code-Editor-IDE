import java.io.*;
public class Terminal1 
{
    public static void main(String a[])throws Exception
    {
        String dir="D:\\java";
        String directory = "cd /d \"" + dir + "\"";
        String command = "java Pattern";
        System.out.println(""+directory);
        
    //     Process process = Runtime.getRuntime()
    //     .exec("cmd /c dir", null, new File("D:\\java\\"));
    //   //.exec("sh -c ls", null, new File("Pathname")); for non-Windows users
    //     printResults(process);


    Process process = Runtime.getRuntime().exec(
        "cmd /c start java Pattern",
        null,
        new File("D:\\java\\"));
     }
}