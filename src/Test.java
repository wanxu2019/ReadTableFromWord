import java.io.IOException;


public class Test extends BaseTools{
	public static void main(String[] args) {
//		FileIO.test();
		try {
			new ParamBuilder();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
