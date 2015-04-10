/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sabioscep;

import java.util.Random; 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Sabioscep extends JFrame implements Runnable{
    JLabel[] Imagenes=new JLabel[5];
    ImageIcon[] Iconos=new ImageIcon[5];
    Thread[] procesos=new Thread[5];
    String p="pensando_opt.jpg",c="comer_opt.jpg",e="esperar_opt.jpg";
    
    public Sabioscep(){
	this.setSize(1200,400);
	this.setLocation(100,200);
	this.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i < Imagenes.length; i++){
            Iconos[i]=new ImageIcon("pensando_opt.jpg");
            Imagenes[i]=new JLabel(Iconos[i]);
            Imagenes[i].setBounds(i*230, 80, 200,200);
            procesos[i]=new Thread(this);
            procesos[i].start();			
        }	
        for (int i = 0; i < Imagenes.length; i++) add(Imagenes[i]);
        this.setVisible(true);	
    }
	
    @Override
	public void run() {
		Thread hiloaseguir= Thread.currentThread();
	
			for (int i = 0; i < procesos.length; i++) {
							
		while(procesos[i]==hiloaseguir){
			Random rnd=new Random();
			int espera = (int)(rnd.nextDouble() * 10.0)+5;
			try {
			if(!Iconos[i].getDescription().equalsIgnoreCase(e))Thread.sleep (espera*1000);
			
			int vecino1=i-1,vecino2=i+1;
			if(i==0){vecino1=4;vecino2=1;}
			if(i==4)vecino2=0;
			if(Iconos[vecino1].getDescription().equalsIgnoreCase(c)||Iconos[vecino2].getDescription().equalsIgnoreCase(c)){
				Iconos[i]=new ImageIcon(e);
				Imagenes[i].setIcon(Iconos[i]);
			}
			
			else {Iconos[i]=new ImageIcon(c);Imagenes[i].setIcon(Iconos[i]);
			}
		
			if(!Iconos[i].getDescription().equalsIgnoreCase(e))Thread.sleep (espera*1000);
		    if(!Iconos[i].getDescription().equalsIgnoreCase(e)){Iconos[i]=new ImageIcon(p);Imagenes[i].setIcon(Iconos[i]);}
			if(!Iconos[i].getDescription().equalsIgnoreCase(e))Thread.sleep (espera*1000);
			} catch (Exception e) {
			               }
		}
			}
	}
	
	public static void main(String[] args) {
		Sabioscep obj=new Sabioscep();
	}
}


