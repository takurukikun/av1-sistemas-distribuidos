import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainSocket {

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(1234);

      System.out.println("Server started. Waiting for connections...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);

        int i;
        char scape = '?';

        printOptions(printWriter);
        while ((i = inputStream.read()) != -1) {
          if (i == scape) {
            printOptions(printWriter);
            break;
          }

          char c = (char) i;
          String input = "";
          while (c != '\n') {
            input += c;
            c = (char) inputStream.read();
          }

          input = input.trim();
          if (input.equals("1")) {
            printWriter.println("Digite o primeiro número: ");
            float a = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Digite o segundo número: ");
            float b = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Resultado: " + (a + b));
            printOptions(printWriter);
          } else if (input.equals("2")) {
            printWriter.println("Digite o primeiro número: ");
            float a = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Digite o segundo número: ");
            float b = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Resultado: " + (a - b));
            printOptions(printWriter);
          } else if (input.equals("3")) {
            printWriter.println("Digite o primeiro número: ");
            float a = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Digite o segundo número: ");
            float b = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Resultado: " + (a * b));
            printOptions(printWriter);
          } else if (input.equals("4")) {
            printWriter.println("Digite o primeiro número: ");
            float a = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Digite o segundo número: ");
            float b = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
            printWriter.println("Resultado: " + (a / b));
            printOptions(printWriter);
          } else {
            printWriter.println("Opção inválida");
            printOptions(printWriter);
          }
        }
        // clientSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void printOptions(PrintWriter printWriter) {
    printWriter.println("Selecione uma das opções: (digite ? a qualquer momento para ver as opções)");
    printWriter.println("1 - Somar");
    printWriter.println("2 - Subtrair");
    printWriter.println("3 - Multiplicar");
    printWriter.println("4 - Dividir");
  }

  // public static String calculate(String input) {
  // String pattern = "(\\d+)(\\s*)([+\\-*/])(\\s*)(\\d+)";
  // Pattern r = Pattern.compile(pattern);
  // Matcher m = r.matcher(input);

  // if (m.find()) {
  // int a = Integer.parseInt(m.group(1));
  // int b = Integer.parseInt(m.group(5));
  // char operator = m.group(3).charAt(0);

  // switch (operator) {
  // case '+':
  // return String.valueOf(a + b);
  // case '-':
  // return String.valueOf(a - b);
  // case '*':
  // return String.valueOf(a * b);
  // case '/':
  // return String.valueOf(a / b);
  // }
  // }

  // return "Invalid input";

  // }
}
