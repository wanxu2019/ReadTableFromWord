import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

public class ParamBuilder extends BaseTools {
	HashMap<String, String> lifeIDs, themeIDs, formIDs;

	public ParamBuilder() throws IOException {
		lifeIDs = genLifeIDs("lifeCircle.json");
		themeIDs = genThemeIDs("theme.json");
		formIDs = genFormIDs("form.json");

		// 兼容不同的写法
		themeIDs.put("产品对象类知识", "57");

		show("lifeIDs:" + lifeIDs);
		show("themeIDs:" + themeIDs);
		show("formIDs:" + formIDs);
	}

	public String build(TableData data) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 获取当前时间
		Date now = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		map.put("ClassID", 2);// 所属领域
		map.put("TemplateID", 1);// 模版选择
		map.put("Name", data.getName());// 知识名称
		map.put("ID", "0");// id为0表示add
		map.put("Url", "123456");// 标识符
		map.put("_parentId", getLifeCircle(data));// 生命周期
		map.put("LifeID", "," + getLifeCircle(data) + ",");// 生命周期
		map.put("_parentId", getTheme(data));// 知识主题
		map.put("ThemeID", "," + getTheme(data) + ",");// 知识主题
		map.put("Belongs", "");// 组件
		map.put("_parentId", getForm(data));// 知识形式
		map.put("FormID", "," + getForm(data) + ",");// 知识形式
		map.put("KeyWords", data.getKeywords());// 关键字
		//TODO填入自己的作者编号
		map.put("AuthorID", "1");// 作者编号
		map.put("VersionCode", "V1.00");// 版本号
		map.put("CreateTime", fmt.format(now));// 创建时间
		map.put("Power", "管理员");// 作者权限
		map.put("Source", "");// 知识来源
		map.put("AttachmentCodes", "");// 附件列表
		map.put("Extend", "");// 扩展元素
		map.put("Abstract", data.getContent().substring(0,100)+"...");// 摘要
		map.put("Pic", "");// 封面简图
		map.put("Task", "");//
		map.put("Chart", "");//
		map.put("Tool", "");// 过程工具
		map.put("Content",
				"<p style=\"clear: both;\"><br/><span style=\"font-size:14px;font-family:宋体\">"
						+ data.getContent() + "</span></p>");
		map.put("editorValue",
				"<p style=\"clear: both;\"><br/><span style=\"font-size:14px;font-family:宋体\">"
						+ data.getContent() + "</span></p>");
		return getUrlParamsByMap(map);
	}

	public String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key).toString();
			sb.append(key + "=" + value);
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.lastIndexOf("&"));
		}
		return s;
	}

	// 将文字的生命周期转为LifeID
	public String getLifeCircle(TableData data){
		// 需要考虑多个生命周期的情况
		String key = data.getLifeCircle();
		String keys[] = key.replace(" ", "").split("\n|\r");
		String str = new String();
		for (int i = 0; i < keys.length; i++) {
			key = keys[i];
			if (!key.equals("")) {
				key = key.split("_")[1].trim();
//				show(key);
				if (lifeIDs.keySet().contains(key))
					str += lifeIDs.get(key) + ",";
				else
					show("lifeIDs中找不到key:" + key);
			}
		}
		return str.substring(0, str.length() - 1);
	}

	// 将文字的主题转为ThemeID
	public String getTheme(TableData data)  {
		String key = data.getTheme();
		if (themeIDs.keySet().contains(data.getTheme()))
			return themeIDs.get(key);
		else
			show("themeIDs中找不到key:" + key);
		return null;
	}

	// 将文字的表现形式转为FormID
	public String getForm(TableData data)  {
		String key = data.getForm();
		if (formIDs.keySet().contains(key))
			return formIDs.get(key);
		else
			show("formIDs中找不到key:" + key);
		return null;
	}

	public boolean isValid(TableData data) {
		if (getLifeCircle(data) == null || getTheme(data) == null
				|| getForm(data) == null)
			return false;
		if(data.getClazz()==null||
				data.getContent()==null||
				data.getKeywords()==null||
				data.getName()==null)
			return false;
		return true;
	}

	public static final String LIFE_NAME = "name";
	public static final String LIFE_ID = "id";

	public HashMap<String, String> genLifeIDs(String path) throws IOException {
		String jsonString = FileIO.readStr(path);
		// show(jsonString);
		JSONArray array = JSONArray.fromObject(jsonString);
		HashMap<String, String> map = new HashMap<String, String>();
		// 遍历json数组
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String name = obj.getString(LIFE_NAME);
			int id = obj.getInt(LIFE_ID);
			map.put(name, id + "");
		}
		return map;
	}

	public static final String THEME_NAME = "name";
	public static final String THEME_ID = "id";

	public HashMap<String, String> genThemeIDs(String path) throws IOException {
		String jsonString = FileIO.readStr(path);
		JSONArray array = JSONArray.fromObject(jsonString);
		HashMap<String, String> map = new HashMap<String, String>();
		// 遍历json数组
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String name = obj.getString(THEME_NAME);
			int id = obj.getInt(THEME_ID);
			map.put(name, id + "");
		}
		return map;
	}

	public static final String FORM_NAME = "name";
	public static final String FORM_ID = "id";

	public HashMap<String, String> genFormIDs(String path) throws IOException {
		String jsonString = FileIO.readStr(path);
		JSONArray array = JSONArray.fromObject(jsonString);
		HashMap<String, String> map = new HashMap<String, String>();
		// 遍历json数组
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String name = obj.getString(FORM_NAME);
			int id = obj.getInt(FORM_ID);
			map.put(name, id + "");
		}
		return map;
	}
}
