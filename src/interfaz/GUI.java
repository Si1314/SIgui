package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import net.miginfocom.swing.MigLayout;


public class GUI {

	private JFrame frmGrupo;
	private JTextArea JTextArea_function;
	private JTextArea JTextArea_result;
	

	
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
	      UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
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
		frmGrupo.getContentPane().setLayout(new MigLayout("", "[180.00,grow][680.00,grow][327.00,grow][]", "[64.00,grow][210.00,grow][210.00,grow][grow 600]"));
		
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
		
		//Tool Bar Menu
		
		JToolBar toolBar = new JToolBar();
		ImageIcon icon1 = new ImageIcon("./img/Open-File-24.png");
		ImageIcon icon2 = new ImageIcon("./img/Cut-24.png");
		ImageIcon icon3 = new ImageIcon("./img/Copy-24.png");
		ImageIcon icon4 = new ImageIcon("./img/Paste-24.png");
		ImageIcon icon5 = new ImageIcon("./img/Play1Pressed_24.png");
		ImageIcon icon6 = new ImageIcon("./img/Printer-blue-24.png");
		ImageIcon icon7 = new ImageIcon("./img/Save-24.png");
		ImageIcon icon8 = new ImageIcon("./img/Undo-24.png");
		ImageIcon icon9 = new ImageIcon("./img/Redo-24.png");

        JButton but1 = new JButton(icon1);
        JButton but2 = new JButton(icon2);
        JButton but3 = new JButton(icon3);
        JButton but4 = new JButton(icon4);
        JButton but5 = new JButton(icon5);
        JButton but6 = new JButton(icon6);
        JButton but7 = new JButton(icon7);
        JButton but8 = new JButton(icon8);
        JButton but9 = new JButton(icon9);
        
        toolBar.add(but1);
        toolBar.add(but2);
        toolBar.add(but3);
        toolBar.add(but4);
        toolBar.add(but5);
        toolBar.add(but6);
        toolBar.add(but7);
        toolBar.add(but8);
        toolBar.add(but9);
        
        
        frmGrupo.add(toolBar,BorderLayout.NORTH);
		
		
		//Text Area for function
		
		JPanel JPanel_function = new JPanel();
		frmGrupo.getContentPane().add(JPanel_function, "cell 1 1,grow");
		JPanel_function.setLayout(new MigLayout("", "[grow]", "[16.00][grow]"));
		
		JLabel JLabel_function = new JLabel("C\u00F3digo de la funci\u00F3n:");
		JLabel_function.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_function.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_function.add(JLabel_function, "cell 0 0");
		
		JScrollPane JScrollPane_function = new JScrollPane();
		JPanel_function.add(JScrollPane_function, "cell 0 1,grow");
		
		JTextArea_function = new JTextArea();
		JTextArea_function.setEditable(true);
		JScrollPane_function.setViewportView(JTextArea_function);
		
		JPanel JPanel_result = new JPanel();
		frmGrupo.getContentPane().add(JPanel_result, "cell 1 2,grow");
		JPanel_result.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabel_result = new JLabel("Salidas de la funci\u00F3n:");
		JLabel_result.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_result.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_result.add(JLabel_result, "cell 0 0");
		
		JScrollPane JScrollPane_result = new JScrollPane();
		JPanel_result.add(JScrollPane_result, "cell 0 1, grow");
		
		JTextArea_result = new JTextArea();
		JTextArea_result.setEditable(false);
		JScrollPane_result.setViewportView(JTextArea_result);
		
		
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
