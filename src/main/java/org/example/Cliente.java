package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Conectando al servidor...");
            Socket socket = new Socket("localhost", 5000);

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Bucle para solicitar operaciones de forma infinita
            while (true) {
                System.out.println("\n--- NUEVA OPERACIÓN ---");
                System.out.print("Introduce el primer número (o escribe 'salir' para desconectar): ");
                String numero1 = scanner.nextLine();

                // Enviamos el primer dato al servidor para que sepa si continuar o detenerse
                salida.println(numero1);

                if (numero1.equalsIgnoreCase("salir")) {
                    System.out.println("Desconectando del servidor...");
                    break;
                }

                System.out.print("Introduce el segundo número: ");
                String numero2 = scanner.nextLine();
                salida.println(numero2);

                System.out.print("Introduce la operación (+, -, *, /): ");
                String operacion = scanner.nextLine();
                salida.println(operacion);

                // Lectura de la respuesta enviada por el servidor
                String respuesta = entrada.readLine();
                System.out.println("[Respuesta del servidor] " + respuesta);
            }

            scanner.close();
            socket.close();
            System.out.println("Cliente cerrado correctamente.");

        } catch (Exception e) {
            System.out.println("No se pudo conectar con el servidor o la conexión se interrumpió.");
            e.printStackTrace();
        }
    }
}