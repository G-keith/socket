package demo;

import java.io.*;
import java.net.*;

/**
 * @author keith
 * @version 1.0
 * @date 2019-05-29
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket=new Socket();
        socket.setSoTimeout(3000);

        //连接服务器
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);
        System.out.println("连接服务器成功：");
        System.out.println("客户端地址："+socket.getLocalAddress()+":"+socket.getLocalPort());
        System.out.println("服务端地址："+socket.getInetAddress()+":"+socket.getPort());

        try {
            todo(socket);
        }catch (Exception e){
            System.out.println("连接异常");
        }
        socket.close();
        System.out.println("关闭连接");
    }

    private static void todo(Socket client) throws IOException{
        //键盘输入
        InputStream in=System.in;
        BufferedReader input=new BufferedReader(new InputStreamReader(in));

        //获取客户端输出流并转换成打印流
        OutputStream outputStream=client.getOutputStream();
        PrintStream printStream=new PrintStream(outputStream);

        //读取服务器返回数据
        //构建输入流
        InputStream inputStream=client.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

        boolean flag=true;
        while(flag){
            //读取键盘输入
            String str=input.readLine();
            //发送到服务器
            printStream.println(str);

            //读取服务器返回数据
            String server=bufferedReader.readLine();
            System.out.println(server);
            if("bye".equalsIgnoreCase(server)){
                flag=false;
            }
        }

        printStream.close();
        bufferedReader.close();

    }
}
