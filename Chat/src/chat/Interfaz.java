package chat;


import java.awt.BorderLayout;   
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class Interfaz extends JFrame  implements Runnable{
    JTextArea tcliente=new JTextArea();
    JTextField mensaje=new JTextField(20);
    JButton boton=new JButton();
    JLabel texto;
    JMenuBar mb;
    JMenu menu;
    JMenuItem item;
    JScrollPane scroll = new JScrollPane();
	
    String recibido;
    OutputStream osalida;
    DataOutputStream dsalida;
    boolean salida=true;

    InputStream ientrada;
    DataInputStream dentrada;

    Socket cliente;
    Thread hilocaja;
    
    public Interfaz() {
        setSize(400,400);
	setLocation(200,200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
        
        JMenu menuArchivo = new JMenu("Archivo"); 
        JMenuItem salir = new JMenuItem("Salir");
        menuArchivo.add(salir);
        
        JMenuBar barra = new JMenuBar(); 
        setJMenuBar(barra); 
        barra.add(menuArchivo); 
        
        salir.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); 
                }
        });
        
	servidor();
		
        scroll.setViewportView(tcliente);
        
        try {	
            cliente = new Socket("127.0.0.1", 3000);  
            osalida = cliente.getOutputStream();
            dsalida = new DataOutputStream(osalida);

            ientrada = cliente.getInputStream();
            dentrada = new DataInputStream(ientrada);
 
            recibido = dentrada.readUTF();
	    tcliente.setText(tcliente.getText()+recibido);
            tcliente.setBackground(Color.BLACK);
	    tcliente.setForeground(Color.GREEN);
            tcliente.setEnabled(false);
        }catch(Exception e){
            System.out.println("Algo ha salido mal");
            System.err.println("Error: " + e);
        }
        
        hilocaja=new Thread(this);
	hilocaja.start();
            
        setVisible(true);
    }

    public void servidor() { 
        this.setLayout(new GridLayout(1, 1, 1, 1));
	JPanel Pservidor=new JPanel();
	Pservidor.setLayout(new BorderLayout());
				
	JPanel Pcliente=new JPanel();
	Pcliente.setLayout(new BorderLayout());
	JPanel pcentro=new JPanel();
	pcentro.setLayout(new FlowLayout());
	
	this.boton.setText("ENVIAR");
        
        pcentro.add(this.mensaje);
	pcentro.add(this.boton);
	boton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent evento){
                        try {
                            dsalida.writeUTF(mensaje.getText()+"\n");
                            mensaje.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }	
                }
        );
        
        Calendar Cal= Calendar.getInstance();
        String fec= Cal.get(Cal.DATE)+"/"+(Cal.get(Cal.MONTH)+1)+"/"+Cal.get(Cal.YEAR);
        texto=new JLabel(fec);
        add(texto);
        
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        System.out.println("Hora de entrada al chat: " + hora + ":" + minutos + ":" + segundos);
        
        Pcliente.add(pcentro, BorderLayout.CENTER);
	Pcliente.add(texto, BorderLayout.SOUTH);
		
	Pservidor.add(scroll, BorderLayout.CENTER);
	Pservidor.add(Pcliente, BorderLayout.SOUTH);
	this.add(Pservidor);
    }
    	
    public void cliente() {
        this.setLayout(new GridLayout(1, 1, 1, 1));
    }
	
    public void salir(){
        try{
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
        if(ct==hilocaja){
            try {
                do{
                    recibido = dentrada.readUTF();
                    tcliente.setText(tcliente.getText()+recibido);
                }while(true);
            }catch(Exception e){
                tcliente.setText(tcliente.getText()+"Erro al recibir dato \n ");
            }
        }    
    }	 

    public static void main(String[] args){
        Interfaz in= new Interfaz();
        
    }
	
   
}
