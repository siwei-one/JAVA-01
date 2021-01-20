Server 端：
`

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 模拟服务端
 *
 */
@SuppressWarnings("rawtypes")
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream ops = null;
        InputStream is = null;

        try {
            // 第一步，创建ServerSocket对象，监听本机的8080端口号
            serverSocket = new ServerSocket(8080);

            while (true) {
                // 等待来自客户端的请求和获取客户端对象对应的Socket对象
                socket = serverSocket.accept();

                // 通过获取到的Socket对象获取输出流对象和输入流对象
                ops = socket.getOutputStream();
                is = socket.getInputStream();

                sendStatic(ops);

                System.out.println("测试");

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (null != ops) {
                ops.close();
            }
            // 有可能是这里的输入没有关闭导致请求一直出问题
            if (null != is) {
                is.close();
            }
            if (null != socket) {
                socket.close();
            }
        }

    }


    /**
     * 发送静态资源给客户端
     *
     * @param ops
     * @throws IOException
     */
    private static void sendStatic(OutputStream ops) throws Exception{

        // 响应行
        ops.write("HTTP/1.1 200 OK\n".getBytes());
        // 响应头
        ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
        ops.write("Server:SiWei\n".getBytes());
        int len = "你好".getBytes().length;
        ops.write(("content-length:" + String.valueOf(len) + "\n").getBytes());
        // 响应体上的换行
        ops.write("\n".getBytes());

        // 响应体
        ops.write("你好".getBytes());
        ops.flush();

    }

}`

Client端：
`import cn.hutool.http.HttpUtil;
 
 public class HttpCilentDemo {
 
     public static void main(String[] args) {
         String response = HttpUtil.get("http://localhost:8080");
         System.out.println(response);
     }
 }
`

运行结果：

你好