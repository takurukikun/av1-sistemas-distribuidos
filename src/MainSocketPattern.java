import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainSocketPattern {

  public static void main(String[] args) {
    try {
      // Create a server socket on port 1234
      ServerSocket serverSocket = new ServerSocket(1234);

      System.out.println("Server started. Waiting for connections...");

      while (true) {
        // Accept incoming client connections
        Socket clientSocket = serverSocket.accept();

        // Create input and output streams for the client socket
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        // Read the input from the client
        // String input = inputStream.

        int i;
        char scape = '?';

        while ((i = inputStream.read()) != -1) {
          if (i == scape) {
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println("Goodbye!");
            break;
          }
          // check if the input in format (+|-|*|/)number1number2
          char c = (char) i;
          String input = "";
          while (c != '\n') {
            input += c;
            c = (char) inputStream.read();
          }

          input = input.trim();

          if (isInPattern(input)) {
            // Split the input into operator and operands
            String operator = input.substring(0, 1);
            input = input.substring(1);
            String[] parts = input.split(";");
            float operand1 = Float.parseFloat(parts[0]);
            float operand2 = Float.parseFloat(parts[1]);

            System.out.println(
              "Received: " + operator + " " + operand1 + " " + operand2
            );

            // Perform the operation
            float result = 0;
            switch (operator) {
              case "+":
                result = operand1 + operand2;
                break;
              case "-":
                result = operand1 - operand2;
                break;
              case "*":
                result = operand1 * operand2;
                break;
              case "/":
                result = operand1 / operand2;
                break;
            }

            // Send the result back to the client
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(result);
          } else {
            // Send an error message back to the client
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println("Invalid input");
          }
        }
        // Close the client socket
        // clientSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isInPattern(String input) {
    Pattern pattern = Pattern.compile(
      "([\\+\\-\\*\\/])(\\d+\\.?\\d*)\\;(\\d+\\.?\\d*)"
    );
    Matcher matcher = pattern.matcher(input);
    return matcher.matches();
  }
}
