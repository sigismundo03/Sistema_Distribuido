import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcA
{
    public static void main(String args[]) throws IOException
    {

        ServerSocket ss = new ServerSocket(1000);

        while (true) {
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.println("Novo  cliente");

            ClientHandlerA usuario = new ClientHandlerA(s, dis, dos);

            Thread t = new Thread(usuario);
            t.start();
        }
    }
} 