
public class TableData {
	//所属领域
	private String clazz;
	//知识名称
	private String name;
	//生命周期
	private String lifeCircle;
	//知识主题
	private String theme;
	//知识表现形式
	private String form;
	//关键词
	private String keywords;
	//知识内容
	private String content;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLifeCircle() {
		return lifeCircle;
	}
	public void setLifeCircle(String lifeCircle) {
		this.lifeCircle = lifeCircle;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return name+":[clazz=" + clazz + ", name=" + name + ", lifeCircle="
				+ lifeCircle + ", theme=" + theme + ", form=" + form
				+ ", keywords=" + keywords + ", content=" + content + "]";
	}
	
}
