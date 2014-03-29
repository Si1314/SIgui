package interfaz;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import net.miginfocom.swing.MigLayout;


public class GUI {

	private JFrame frmGrupo;
	private JTextArea JTextArea_1;
	private JTextArea JTextArea_2;
	private JTextArea JTextArea_3;
	private JTextArea JTextArea_4;
	private JTextArea JTextArea_5;
	

	
	private GUI() {
		initialize();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//UIManager.setLookAndFeel(new de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel());
					//InterfazPlg window = new InterfazPlg();
					GUI window = new GUI();
					window.frmGrupo.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	public void initialize(){
		try
	    {
	      UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
		
		frmGrupo = new JFrame();
		frmGrupo.setLocationRelativeTo(null);
		frmGrupo.setTitle("Ejecucion simbolica");
		frmGrupo.setBounds(100, 100, 900, 500);
		frmGrupo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupo.getContentPane().setLayout(new MigLayout("", "[180.00,grow][360.00,grow][324.00,grow,fill][327.00,grow][]", "[64.00,grow][416.00,grow][grow 600]"));
		
		//Menu Bar
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem menuItem11 = new JMenuItem("New");
		JMenuItem menuItem12 = new JMenuItem("Open File");
		JMenuItem menuItem13 = new JMenuItem("Save");
		JMenuItem menuItem14 = new JMenuItem("Close");
		JMenuItem menuItem15 = new JMenuItem("Close all");
		JMenuItem menuItem16 = new JMenuItem("Exit");
		file.add(menuItem11);
		file.add(menuItem12);
		file.addSeparator();
		file.add(menuItem13);
		file.addSeparator();
		file.add(menuItem14);
		file.add(menuItem15);
		file.addSeparator();
		file.add(menuItem16);
		menubar.add(file);
		JMenu edit = new JMenu("Edit");
		JMenuItem menuItem21 = new JMenuItem("Undo");
		JMenuItem menuItem22 = new JMenuItem("Redo");
		JMenuItem menuItem23 = new JMenuItem("Cut");
		JMenuItem menuItem24 = new JMenuItem("Copy");
		JMenuItem menuItem25 = new JMenuItem("Paste");
		JMenuItem menuItem26 = new JMenuItem("Delete");
		JMenuItem menuItem27 = new JMenuItem("Select all");
		edit.add(menuItem21);
		edit.add(menuItem22);
		edit.addSeparator();
		edit.add(menuItem23);
		edit.add(menuItem24);
		edit.add(menuItem25);
		edit.addSeparator();
		edit.add(menuItem26);
		edit.add(menuItem27);
		menubar.add(edit);
		frmGrupo.setJMenuBar(menubar);
		
		//Text Area for function
		JTextArea_1 = new JTextArea(); 
		
		JPanel JPanelCI = new JPanel();
		frmGrupo.getContentPane().add(JPanelCI, "cell 2 0 1 2,grow");
		JPanelCI.setLayout(new MigLayout("", "[grow]", "[16.00][grow]"));
		
		JLabel JLabelCI = new JLabel("C\u00F3digo Intermedio Generado:");
		JLabelCI.setHorizontalAlignment(SwingConstants.CENTER);
		JLabelCI.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanelCI.add(JLabelCI, "cell 0 0");
		
		JScrollPane JScrollPaneCI = new JScrollPane();
		JPanelCI.add(JScrollPaneCI, "cell 0 1,grow");
		
		JTextArea_2 = new JTextArea();
		JTextArea_2.setEditable(false);
		JScrollPaneCI.setViewportView(JTextArea_2);
		
		JPanel JPanelCF = new JPanel();
		frmGrupo.getContentPane().add(JPanelCF, "cell 3 0 1 2,grow");
		JPanelCF.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabelCF = new JLabel("C\u00F3digo Final Generado:");
		JLabelCF.setHorizontalAlignment(SwingConstants.CENTER);
		JLabelCF.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanelCF.add(JLabelCF, "cell 0 0");
		
		JScrollPane JScrollPaneCF = new JScrollPane();
		JPanelCF.add(JScrollPaneCF, "cell 0 1, grow");
		
		JTextArea_3 = new JTextArea();
		JTextArea_3.setEditable(false);
		JScrollPaneCF.setViewportView(JTextArea_3);
		
		JPanel JPanelBotones = new JPanel();
		frmGrupo.getContentPane().add(JPanelBotones, "cell 0 1,alignx center,aligny top");
		JPanelBotones.setLayout(new MigLayout("", "[168.00]", "[31.00px][31.00][70.00][31.00][30.00][31.00][102.00][][31.00]"));
		
		JButton JButton_Abrir = new JButton("Abrir");
		JPanelBotones.add(JButton_Abrir, "cell 0 0,growx,aligny center");
		
		JButton JButton_Ejecutar = new JButton("Ejecutar");
		JPanelBotones.add(JButton_Ejecutar, "cell 0 1,growx,aligny center");
		
		JButton JButton_ConsultaId = new JButton("Ver ElementosTS");
		JPanelBotones.add(JButton_ConsultaId, "cell 0 4,growx,aligny center");
		
		JButton JButton_ENS = new JButton("Ejecutar ENS2001");
		JPanelBotones.add(JButton_ENS, "cell 0 5,growx,aligny center");
		
		JButton JButton_Reset = new JButton("Reset");
		JPanelBotones.add(JButton_Reset, "cell 0 7,growx,aligny center");
		
		JButton JButton_Salir = new JButton("Salir");
		JPanelBotones.add(JButton_Salir, "cell 0 8,growx");
		
		JPanel JPanelTexto = new JPanel();
		frmGrupo.getContentPane().add(JPanelTexto, "cell 1 0 1 2,grow");
		JPanelTexto.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel JLabelArchivo = new JLabel("Archivo leido:");
		JLabelArchivo.setHorizontalAlignment(SwingConstants.CENTER);
		JLabelArchivo.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanelTexto.add(JLabelArchivo, "cell 0 0");
		
		JScrollPane JScrollPaneArchivo = new JScrollPane();
		JPanelTexto.add(JScrollPaneArchivo, "cell 0 1,grow");
		
		JTextArea_4 = new JTextArea();
		JTextArea_4.setEditable(false);
		JScrollPaneArchivo.setViewportView(JTextArea_4);
		JPanel JPanelAvisos = new JPanel();
	 	
		frmGrupo.getContentPane().add(JPanelAvisos, "cell 0 2 4 1,grow");
		 	
		JPanelAvisos.setLayout(new MigLayout("", "[grow]", "[grow]"));
		JScrollPane JScrollPaneAvisos = new JScrollPane();
		JScrollPaneAvisos.setFocusable(false);
		JPanelAvisos.add(JScrollPaneAvisos, "cell 0 0,grow");		
		JTextArea_5 = new JTextArea();
		JScrollPaneAvisos.setViewportView(JTextArea_5);

		
	}
	
	public void runGUI() {
		// LookAndFeel + posición inicial
		try
	    {
	      UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	}
}
