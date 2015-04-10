package chat;


import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class SocketServerHilo implements Runnable {

    String recibido;
    OutputStream osalida;
    DataOutputStream dsalida;

    InputStream ientrada;
    DataInputStream dentrada;
    Socket socket;
    int numerodelhilo=0;
	
    ArrayList<SocketServerHilo> hilos;
    
    public SocketServerHilo(Socket lsocket, int numerodelhilo, ArrayList<SocketServerHilo>hilos){
        try{
            this.numerodelhilo=numerodelhilo;
            this.hilos=hilos;
            socket = lsocket;		
        }
        catch (Exception excepcion) {
            System.out.println(excepcion);
        }		
    }

    public void run() {	
        try{			
            osalida = socket.getOutputStream();
            dsalida = new DataOutputStream(osalida);

            ientrada = socket.getInputStream();
            dentrada = new DataInputStream(ientrada);

            dsalida.writeUTF("Bienvenido al servidor\n");
            
            do{
                recibido = dentrada.readUTF();	
				
		int tam=hilos.size();
		for (int i = 0; i < tam; i++){
                    hilos.get(i).dsalida.writeUTF("Cliente"+numerodelhilo+">>>"+recibido);
                }
               
            }while(!recibido.equals("bye"));
        }catch(IOException excepcion){
            System.out.println("Ha salido del servidor el cliente " +numerodelhilo);		
        }
        try{
            dsalida.close();
            dentrada.close();
            socket.close();			
        }catch(IOException excepcion){
            System.out.println(excepcion);
        }
    }
    
    
}