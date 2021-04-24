import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class ClientHandlerB implements Runnable
{
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;

    public ClientHandlerB(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        while (true)
        {
            try
            {
                String input = dis.readUTF();
                String operacao;
                String aux = "";
                String err = "Formatação invalida";
                if(input.equals("Sair")){
                    this.s.close();
                    break;
                }
                System.out.println("Equação recebida:" + input);
                double result = 0;
                StringTokenizer st = new StringTokenizer(input);

                if(st.hasMoreTokens()) {
                    aux = st.nextToken();
                    if (aux.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)")) {
                        float oprnd1 = Float.parseFloat(aux);
                        aux = st.nextToken();
                        if (aux.equals("%") | aux.equals("^")| aux.equals("#")) {
                            operacao = aux;
                            aux = st.nextToken();
                            if (aux.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)")) {
                                float oprnd2 = Float.parseFloat(aux);
                                switch (operacao) {
                                    case "%":
                                        result = oprnd1 % oprnd2;
                                        System.out.println("Enviando Resultado...");
                                        dos.writeUTF(Double.toString(result));
                                        break;
                                    case "^":
                                        result = Math.pow(oprnd1,oprnd2);
                                        System.out.println("Enviando Resultado...");
                                        dos.writeUTF(Double.toString(result));
                                        break;
                                    case "#":
                                        if(oprnd2<= 0) {
                                            dos.writeUTF("O indice não pode ser 0 ou negativo");
                                            break;
                                        }
                                        result = Math.pow(oprnd1,1/oprnd2);
                                        System.out.println("Enviando ");
                                        dos.writeUTF(Double.toString(result));
                                        break;
                                    default:
                                        dos.writeUTF(err);
                                        break;
                                }
                            } else {
                                dos.writeUTF(err);
                            }
                        } else {
                            dos.writeUTF(err);
                        }
                    } else {
                        dos.writeUTF(err);
                    }
                }else {
                    dos.writeUTF(err);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        try
        {
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}