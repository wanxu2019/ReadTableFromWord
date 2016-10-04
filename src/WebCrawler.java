
public class WebCrawler {
	String url;
	String cookie;
	public WebCrawler(String url,String cookie) {
		// TODO Auto-generated constructor stub
		this.url=url;
		this.cookie=cookie;
	}
	public String send(String paramString){
		String result=HttpRequest.sendPost(url, paramString,cookie);
		return result;
	}
}
