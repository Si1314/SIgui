package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.text.DefaultEditorKit;

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
	
	public JMenuBar getMenuBar(){
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
		
		return menubar;
	}
	
	public JToolBar getToolBar(){
		JToolBar toolBar = new JToolBar();
		ImageIcon icon_open = new ImageIcon("./img/Open-File-24.png");
		ImageIcon icon_cut = new ImageIcon("./img/Cut-24.png");
		ImageIcon icon_copy = new ImageIcon("./img/Copy-24.png");
		ImageIcon icon_paste = new ImageIcon("./img/Paste-24.png");
		ImageIcon icon_play = new ImageIcon("./img/Play1Pressed_24.png");
		ImageIcon icon_print = new ImageIcon("./img/Printer-blue-24.png");
		ImageIcon icon_save = new ImageIcon("./img/Save-24.png");
		ImageIcon icon_undo = new ImageIcon("./img/Undo-24.png");
		ImageIcon icon_redo = new ImageIcon("./img/Redo-24.png");

        JButton but_open = new JButton(icon_open);
        	but_open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// Abrir jFileChooser
					// print archivo cppn en jTextArea
					openFile();
					
				}
			});
        JButton but_cut = new JButton(new DefaultEditorKit.CutAction());
	        but_cut.setIcon(icon_cut);
	        but_cut.setText("");
        JButton but_copy = new JButton(new DefaultEditorKit.CopyAction());
	        but_copy.setIcon(icon_copy);
	        but_copy.setText("");
        JButton but_paste = new JButton(new DefaultEditorKit.PasteAction());
	        but_paste.setIcon(icon_paste);
	        but_paste.setText("");
        JButton but_play = new JButton(icon_play);
        JButton but_print = new JButton(icon_print);
        JButton but_save = new JButton(icon_save);	
        JButton but_undo = new JButton(icon_undo);
        JButton but_redo = new JButton(icon_redo);
        
        toolBar.add(but_open);
        toolBar.add(but_save);
        toolBar.add(but_print);
        toolBar.addSeparator();
        toolBar.add(but_cut);
        toolBar.add(but_copy);
        toolBar.add(but_paste);
        toolBar.addSeparator();
        toolBar.add(but_undo);
        toolBar.add(but_redo);
        toolBar.addSeparator();
        toolBar.add(but_play);
        
        return toolBar;

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
		frmGrupo.getContentPane().setLayout(new MigLayout("", "[180.00,grow][680.00,grow][327.00,grow][]", "[64.00,grow][210.00,grow][64.00,grow][210.00,grow][grow 600]"));
		
		frmGrupo.setJMenuBar(getMenuBar());        
        
        frmGrupo.add(getToolBar(),BorderLayout.NORTH);
		
		
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
		
		// Boton play
		
		JPanel JPanel_play = new JPanel();
		frmGrupo.getContentPane().add(JPanel_play, "cell 1 2,grow");
		JPanel_play.setLayout(new MigLayout("","[right]","[grow]"));
		
		JButton JButton_play = new JButton("Play",new ImageIcon("./img/Play1Pressed_24.png"));
		JPanel_play.add(JButton_play, "align right");
		
		
		// Zona resultado
		
		JPanel JPanel_result = new JPanel();
		frmGrupo.getContentPane().add(JPanel_result, "cell 1 3,grow");
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
	
	public void openFile(){
		JFileChooser chooser=new  JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	
        }
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
