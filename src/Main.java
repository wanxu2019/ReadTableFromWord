import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main   {
	public static void main(String[] args) {
		debugFlag = true;
		try {

			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入完整文件路径:");
			// String path = scanner.next();
			String path = "m.doc";
			String logString = new String();

			TableReader reader = new TableReader();
			ArrayList<TableData> list = reader.read(path);
			ParamBuilder builder = new ParamBuilder();
			boolean isDataValid = true;
			ArrayList<Integer> badIndex = new ArrayList<Integer>();
			// 检查数据的合法性
			for (int i = 0; i < list.size(); i++) {
				TableData data = list.get(i);
				// 检查数据
				boolean result = builder.isValid(data);
				if (result == false) {
					isDataValid = false;
					String string = "第" + i
							+ "条数据有问题，请检查该数据的生命周期，主题与表现形式，该条数据内容为：\n"
							+ data.toString() + ",请手动修改后重新运行程序。";
					System.out.println(string);
					logString += string + "\n";
					badIndex.add(i);
				}
			}
			String url = "http://139.224.2.217:607/KB/Edit";
			//填入自己账号的cookie
			String cookie = "";
			WebCrawler crawler = new WebCrawler(url, cookie);
			// 遍历数据,逐一提交
			int count = 0;
			int failCount = 0;
			ArrayList<Integer> failList = new ArrayList<Integer>();
			for (int i = 2; i < list.size(); i++) {
				// 跳过错误数据
				if (badIndex.contains(i)) {
					logString += "跳过第" + i + "条数据\n";
					continue;
				}
				count++;
				String s1 = "开始提交第" + i + "条数据：";
				System.out.println(s1);
				logString += s1 + "\n";
				TableData data = list.get(i);
				String paramString = builder.build(data);
				// 提交数据
				String result = crawler.send(paramString);
				logString += "第" + i + "条数据返回值为：" + result + "\n";
				if (result.equals("")) {
					String s2 = "第" + i + "条数据提交失败，该条数据内容为：\n"
							+ data.toString() + ",请手动修改。\n";
					failList.add(i);
					failCount++;
					System.out.println(s2);
					logString += s2;
				}
				Thread.sleep(1000);
			}
			String resultString = "总共提交" + count + "条数据，失败" + failCount + "条";
			String failData = new String("提交失败的数据为：\n");
			for (int i = 0; i < failList.size(); i++) {
				failData += failList.get(i) + ",";
			}
			System.out.println(resultString);
			System.out.println(failData);
			logString += resultString + "\n" + failData;

			FileIO.writeStr("log.txt", logString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("输入文件路径不存在或文件格式不正确！");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
