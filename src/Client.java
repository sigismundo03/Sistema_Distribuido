import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            String aux = "";

            Socket s = new Socket(ip, 1000);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            Socket s2 = new Socket(ip, 2000);
            DataInputStream dis2 = new DataInputStream(s2.getInputStream());
            DataOutputStream dos2 = new DataOutputStream(s2.getOutputStream());

            while (true) {

                System.out.print("\nInsira a equação: ");
                System.out.println("para funcionar é necessário que esteja na seguinte forma:");
                System.out.println("'operador1 operando operador2'");
                System.out.println("Operandos Aceitos:");
                System.out.println("\"+\" Soma | \"-\" Subtração | \"/\" Divisão | \"*\" Multiplicação | \"%\" Resto | \"^\" Potência | \"#\" Raiz");
                System.out.println("Digite 'sair' par encerrar a conexão");

                String input = "";
                input = scn.nextLine();
                if (input.equals("sair")) {
                    dos.writeUTF(input);
                    dos2.writeUTF(input);
                    scn.close();
                    System.out.println("Programa encerrado");
                    break;
                }
                StringTokenizer st = new StringTokenizer(input);
                aux = st.hasMoreTokens() ? st.nextToken() : "";

                if(aux.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)")){
                    String opera = st.hasMoreTokens() ? st.nextToken() : "";;

                    if (opera.equals("+") | opera.equals("-") | opera.equals("/") | opera.equals("*")) {
                        System.out.println("Conectando ao server A");
                        aux = st.hasMoreTokens() ? st.nextToken() : "";

                        if(aux.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)")) {
                            dos.writeUTF(input);
                            String resposta = dis.readUTF();
                            System.out.println("Resposta= " + resposta);
                        }else{
                            System.out.println("Padrao icorreto, esperando o 'Operador2' do tipo numero, recebido "+"'"+ aux+"'");
                        }

                    }else if(opera.equals("%") | opera.equals("^") | opera.equals("#")){
                        System.out.println("Conectando ao server B");
                        aux = st.hasMoreTokens() ? st.nextToken() : "";

                        if(aux.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)")) {
                            dos2.writeUTF(input);
                            String resposta = dis2.readUTF();
                            System.out.println("Resposta= " + resposta);
                        }else{
                            System.out.println("Padrao incorreto, esperando o 'Operador2' do tipo número, recebido "+"'"+aux+"'");
                        }
                    }else{
                        System.out.println("Padrao icorreto esperando o 'Operando', recebido "+"'"+opera+"'");
                    }
                }else{
                    System.out.println("Padrao icorreto, esperando o 'Operador1' do tipo número, recebido "+"'"+aux+"'");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}