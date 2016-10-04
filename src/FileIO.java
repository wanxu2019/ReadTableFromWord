import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileIO {
	public static String readStr(String path) throws IOException{
		File f=new File(path);
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		StringBuffer sb=new StringBuffer();
		String string=br.readLine();
		while(string!=null){
			sb.append(string);
			string=br.readLine();
		}
		return sb.toString();
	}
	public static boolean writeStr(String path,String string) throws IOException{
		File f=new File(path);
		FileWriter fw=new FileWriter(f);
		fw.write(string);
		fw.flush();
		return true;
	}
	
	public static void test(){
		try {
			String string=readStr("a.txt");
			System.out.println("content:"+string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
