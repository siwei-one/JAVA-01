import cn.hutool.http.HttpUtil;

public class HttpCilentDemo {

    public static void main(String[] args) {
        String response = HttpUtil.get("http://localhost:8080");
        System.out.println(response);
    }
}
