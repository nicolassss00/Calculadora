package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando el servidor calculadora en el puerto 5000...");
            ServerSocket serverSocket = new ServerSocket(5000);

            // Bucle exterior: permite que cuando un cliente se desconecte, el servidor siga vivo esperando a otro
            while (true) {
                System.out.println("Esperando conexión de un cliente...");
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado con éxito.");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

                String numero1Str;

                // Bucle interior: mantiene la comunicación infinita con el cliente actual
                // Se lee el primer dato, si es null significa que el cliente se desconectó abruptamente
                while ((numero1Str = entrada.readLine()) != null) {

                    // Condición de salida para romper el bucle
                    if (numero1Str.equalsIgnoreCase("salir")) {
                        System.out.println("El cliente ha solicitado cerrar la sesión.");
                        break;
                    }

                    String numero2Str = entrada.readLine();
                    String operacion = entrada.readLine();

                    if (numero2Str != null && operacion != null) {
                        try {
                            double num1 = Double.parseDouble(numero1Str);
                            double num2 = Double.parseDouble(numero2Str);
                            double resultado = 0;
                            boolean operacionValida = true;
                            String mensajeError = "";

                            switch (operacion) {
                                case "+":
                                    resultado = num1 + num2;
                                    break;
                                case "-":
                                    resultado = num1 - num2;
                                    break;
                                case "*":
                                    resultado = num1 * num2;
                                    break;
                                case "/":
                                    if (num2 != 0) {
                                        resultado = num1 / num2;
                                    } else {
                                        operacionValida = false;
                                        mensajeError = "Error: No se puede dividir entre cero.";
                                    }
                                    break;
                                default:
                                    operacionValida = false;
                                    mensajeError = "Error: Operación no válida (" + operacion + "). Usa +, -, * o /.";
                                    break;
                            }

                            if (operacionValida) {
                                String respuesta = "El resultado de " + num1 + " " + operacion + " " + num2 + " es: " + resultado;
                                salida.println(respuesta);
                                System.out.println("Procesado: " + num1 + " " + operacion + " " + num2 + " = " + resultado);
                            } else {
                                salida.println(mensajeError);
                                System.out.println("Respuesta enviada: " + mensajeError);
                            }

                        } catch (NumberFormatException e) {
                            salida.println("Error: Los valores ingresados no son números válidos.");
                        }
                    }
                }

                // Cierra la conexión del cliente actual y el bucle exterior vuelve a esperar otro
                socket.close();
                System.out.println("Cliente desconectado.\n");
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor");
            e.printStackTrace();
        }
    }
}