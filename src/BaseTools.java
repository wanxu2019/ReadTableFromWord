//基础工具基类
public class BaseTools {
	public static boolean debugFlag=true;
	public  void show(Object message)
	{
		
		if (debugFlag)
		{
			System.out.println(this.getClass().getSimpleName()+":"+message);
		}
	}
}
