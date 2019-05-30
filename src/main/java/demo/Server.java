package demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author keith
 * @version 1.0
 * @date 2019-05-29
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("服务器就绪：");
        System.out.println("服务器信息："+serverSocket.getInetAddress()+":"+serverSocket.getLocalPort());

        while (true){
            //等待客户端连接
            Socket client=serverSocket.accept();
            ClientHandler clientHandler=new ClientHandler(client);
            clientHandler.start();

        }
    }

    //异步线程

    public static class ClientHandler extends Thread{
        private Socket socket;
        private boolean flag=true;

        ClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接信息："+socket.getInetAddress()+":"+socket.getPort());

            try{
                //键盘输入
                InputStream in=System.in;
                BufferedReader input=new BufferedReader(new InputStreamReader(in));

                //得到打印流.服务器回送数据
                PrintStream printStream=new PrintStream(socket.getOutputStream());
                //输入流，用来接收数据
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (flag){
                    String str=bufferedReader.readLine();
                    if("bye".equalsIgnoreCase(str)){
                        flag=false;
                        //回送
                        printStream.println("bye");
                    }else{
                        System.out.println(str);
                       String s= input.readLine();
                        printStream.println(s);
                    }
                }
                printStream.close();
                bufferedReader.close();

            }catch (Exception e){
                System.out.println("连接异常");
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("客户端已关闭");
            }
        }
    }
}
