import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shell extends JFrame{
	
	//elementos gráficos
	JTextField tComando;
	JButton bEjecutar, bLimpiar;
	JTextArea tResultado;
	JScrollPane sPane;

	//oyente de click de botón
	ActionListener alEjecutar;
	ActionListener alLimpiar;

	public Shell(){
		setSize(700,600);
		setTitle(System.getProperty("os.name"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void graficos(){
		getContentPane().setLayout(null);
		//cuadro de texto
		tComando = new JTextField();
		tComando.setBounds(230,40,250,30);
		add(tComando);
		//botón para ejecutar comando
		bEjecutar = new JButton("Ejecutar");
		bEjecutar.setBounds(230,90,100,30);
		add(bEjecutar);
		bEjecutar.addActionListener(alEjecutar);
		bLimpiar = new JButton("Limpiar");
		bLimpiar.setBounds(380,90,100,30);
		add(bLimpiar);
		bLimpiar.addActionListener(alLimpiar);

		//área de texto
		tResultado = new JTextArea();
		tResultado.setBounds(50,130,600,370);
		tResultado.setBackground(Color.BLACK);
		tResultado.setForeground(Color.GREEN);
		//scroll pane
		sPane = new JScrollPane(tResultado);
		sPane.setBounds(100,150,500,400);
		add(sPane);
		//
		setVisible(true);
	}

	private void acciones(){
		alEjecutar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ejecutar();
			}
		};
	}

	private void borrar(){
		alLimpiar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				limpiar();
			}
		};
	}	

	private void limpiar(){
		tResultado.setText("");
	}

	private void ejecutar(){

		Process proc; 
		InputStream is_in;
		String s_aux;
		BufferedReader br;

		try
		{
			proc = Runtime.getRuntime().exec(tComando.getText());
			is_in=proc.getInputStream();
			br=new BufferedReader (new InputStreamReader (is_in));
			s_aux = br.readLine();
            while (s_aux!=null)
            {
            	tResultado.setText(tResultado.getText()+s_aux+"\n");
                s_aux = br.readLine();
            } 
		}
		catch(Exception e)
		{
			e.getMessage();
		}


	}

	public static void main(String args[]){
		Shell ventana = new Shell();
		ventana.acciones();
		ventana.borrar();	
		ventana.graficos();	
	}

}
