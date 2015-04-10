package chat;


import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class SocketServer {
	
    public static void main(String[] args) throws IOException { 
        
        ArrayList<SocketServerHilo> clientes=new ArrayList<>();
        int cantclientes=0;
        try {
            ServerSocket servidor = new ServerSocket(3000);			
            do
            {
                System.out.println("Esperando cliente");							
                Socket socketConectado = servidor.accept();
                cantclientes++;
                SocketServerHilo hilocliente= new SocketServerHilo(socketConectado,cantclientes,clientes);
                clientes.add(hilocliente);
                Runnable nuevoSocket=hilocliente;
                
                Thread hiloSocket = new Thread(nuevoSocket);
                hiloSocket.start();
                System.out.println("Cliente recibido");								
            }while(true);
        }catch(IOException excepcion){			
            System.out.println(excepcion);
        }
    }
    
    
}