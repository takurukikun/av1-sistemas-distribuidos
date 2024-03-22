import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSocket {

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(1234);

      System.out.println("Server started. Waiting for connections...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        new Thread(() -> {
          try {
            handleClient(clientSocket);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void printOptions(PrintWriter printWriter, int op) {
    if (op == 0) {
      printWriter.println("Bem-vindo ao calculadora server");
      printWriter.println("-----------------------------");
      printWriter.println("");
    }

    printWriter.println("Selecione uma das opções: (digite ? a qualquer momento para ver as opções)");
    printWriter.println("1 - Somar");
    printWriter.println("2 - Subtrair");
    printWriter.println("3 - Multiplicar");
    printWriter.println("4 - Dividir");

    if (op == 1) {
      printWriter.println("Você selecinou '1 - somar'");

    } else if (op == 2) {
      printWriter.println("Você selecinou '2 - subtrair'");

    } else if (op == 3) {
      printWriter.println("Você selecinou '3 - multiplicar'");

    } else if (op == 4) {
      printWriter.println("Você selecinou '4 - dividir'");
    }

    printWriter.println("-----------------------------");
    printWriter.println("");
  }

  public static float calculate(float a, float b, char operator) {
    switch (operator) {
      case '+':
        return a + b;
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        return a / b;
    }

    return 0;
  }

  public static float[] getNumbers(InputStream inputStream, PrintWriter printWriter) throws IOException {
    float[] numbers = new float[2];
    printWriter.println("Digite o primeiro número: ");

    numbers[0] = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
    printWriter.println("Digite o segundo número: ");
    numbers[1] = Float.parseFloat((new BufferedReader(new InputStreamReader(inputStream)).readLine()));
    return numbers;
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
  public static void handleClient(Socket clientSocket) throws IOException {
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();
    PrintWriter printWriter = new PrintWriter(outputStream, true);

    int i;
    char scape = '?';

    printOptions(printWriter, 0);
    while ((i = inputStream.read()) != -1) {

      if (i == scape) {
        printOptions(printWriter, 0);
      }

      char c = (char) i;
      String input = "";
      while (c != '\n') {
        input += c;
        c = (char) inputStream.read();
      }

      input = input.trim();
      try {
        if (input.equals("1")) {
          float[] numbers = getNumbers(inputStream, printWriter);
          printWriter.println("Resultado: " + (numbers[0] + numbers[1]));
          printOptions(printWriter, 1);
        } else if (input.equals("2")) {
          float[] numbers = getNumbers(inputStream, printWriter);
          printWriter.println("Resultado: " + (numbers[0] - numbers[1]));
          printOptions(printWriter, 2);
        } else if (input.equals("3")) {
          float[] numbers = getNumbers(inputStream, printWriter);
          printWriter.println("Resultado: " + (numbers[0] * numbers[1]));
          printOptions(printWriter, 3);
        } else if (input.equals("4")) {
          float[] numbers = getNumbers(inputStream, printWriter);
          float result = numbers[0] / numbers[1];
          if (Float.isInfinite(result)) {
            printWriter.println("Divisão por zero");
            printOptions(printWriter, 4);
            continue;
          } else {
            printWriter.println("Resultado: " + (numbers[0] / numbers[1]));
          }
          printOptions(printWriter, 4);
        } else {
          printWriter.println("Opção inválida");
          printOptions(printWriter, 0);
        }
      } catch (Exception e) {
        printWriter.println("Entrada inválida");
        printOptions(printWriter, 0);
        continue;
      }
    }
  }
}
