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

            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // Captura de datos por consola
            System.out.print("Introduce el primer número: ");
            String numero1 = scanner.nextLine();

            System.out.print("Introduce el segundo número: ");
            String numero2 = scanner.nextLine();

            System.out.print("Introduce la operación (+, -, *, /): ");
            String operacion = scanner.nextLine();

            // Enviamos los datos al servidor en orden
            salida.println(numero1);
            salida.println(numero2);
            salida.println(operacion);

            // Lectura de la respuesta enviada por el servidor
            String respuesta = entrada.readLine();
            System.out.println("\n[Respuesta del servidor] " + respuesta);

            scanner.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("No se pudo conectar con el servidor");
            e.printStackTrace();
        }
    }
}