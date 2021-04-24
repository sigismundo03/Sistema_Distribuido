import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcB
{
    public static void main(String args[]) throws IOException
    {

        ServerSocket ss = new ServerSocket(2000);

        while (true) {
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.println("Novo cliente");

            ClientHandlerB usuario = new ClientHandlerB(s, dis, dos);

            Thread t = new Thread(usuario);
            t.start();
        }
    }
} 