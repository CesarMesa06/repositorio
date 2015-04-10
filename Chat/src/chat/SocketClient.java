package chat;


import java.io.*;  
import java.net.*;
import javax.swing.*;

public class SocketClient extends JFrame implements Runnable{    
    String recibido;
    OutputStream osalida;
    DataOutputStream dsalida;
    boolean salida=true;

    InputStream ientrada;
    DataInputStream dentrada;

    Socket cliente;
    Thread hilocliente;
    JTextArea tcliente;
        
    public SocketClient(JTextArea tcliente ){
        this.tcliente=tcliente;
      
	hilocliente=new Thread(this);
	hilocliente.start();
        try {	
            cliente = new Socket("127.0.0.1", 3000);  
            osalida = cliente.getOutputStream();
            dsalida = new DataOutputStream(osalida);

            ientrada = cliente.getInputStream();
            dentrada = new DataInputStream(ientrada);
 
            recibido = dentrada.readUTF();
	    tcliente.setText(tcliente.getText()+recibido);
        }
        catch(Exception e){
            System.err.println("Error: " + e);
        }   
}

    public void mensaje(String mmensaje){
        try {	
            dsalida.writeUTF(mmensaje+"\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
		
    public void cerrarsesion(){
        try {
            dsalida.close();
            dentrada.close();
            cliente.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Thread ct= Thread.currentThread();
        if(ct==hilocliente){
            try {
                do{	
                    recibido = dentrada.readUTF();
                    tcliente.setText(tcliente.getText()+recibido);
                    System.out.println("cliente recibido esto --->>>"+recibido);
                }while(true);
            }catch(Exception e){
            }
            
        }
        
    }
	
 	
}