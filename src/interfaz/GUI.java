package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.Action;
import javax.swing.ImageIcon;
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
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import net.miginfocom.swing.MigLayout;


public class GUI {

	private JFrame frmGrupo;
	private JTextArea JTextArea_function;
	private JTextArea JTextArea_result;
	private String fich;
	

	
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
		JMenuItem menu_new = new JMenuItem("New");
			menu_new.setEnabled(false);
		JMenuItem menu_open = new JMenuItem("Open File");
			menu_open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openFile();
				}
			});
		JMenuItem menu_save = new JMenuItem("Save");
			menu_save.setEnabled(false);
		JMenuItem menu_close = new JMenuItem("Close");
			menu_close.setEnabled(false);
		JMenuItem menu_closeall = new JMenuItem("Close all");
			menu_closeall.setEnabled(false);
		JMenuItem menu_exit = new JMenuItem("Exit");
		file.add(menu_new);
		file.add(menu_open);
		file.addSeparator();
		file.add(menu_save);
		file.addSeparator();
		file.add(menu_close);
		file.add(menu_closeall);
		file.addSeparator();
		file.add(menu_exit);
		menubar.add(file);
		JMenu edit = new JMenu("Edit");
		JMenuItem menu_undo = new JMenuItem("Undo");
			menu_undo.setEnabled(false);
		JMenuItem menu_redo = new JMenuItem("Redo");
			menu_redo.setEnabled(false);
		JMenuItem menu_cut = new JMenuItem(new DefaultEditorKit.CutAction());
			menu_cut.setText("Cut");
		JMenuItem menu_copy = new JMenuItem(new DefaultEditorKit.CopyAction());
			menu_copy.setText("Copy");
		JMenuItem menu_paste = new JMenuItem(new DefaultEditorKit.PasteAction());
			menu_paste.setText("Paste");
		JMenuItem menu_delete = new JMenuItem("Delete");
			menu_delete.setEnabled(false);
		JMenuItem menu_selectall = new JMenuItem("Select all");
			menu_selectall.setEnabled(false);
		edit.add(menu_undo);
		edit.add(menu_redo);
		edit.addSeparator();
		edit.add(menu_cut);
		edit.add(menu_copy);
		edit.add(menu_paste);
		edit.addSeparator();
		edit.add(menu_delete);
		edit.add(menu_selectall);
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
        but_print.setEnabled(false);
        JButton but_save = new JButton(icon_save);
        but_save.setEnabled(false);
        JButton but_undo = new JButton(icon_undo);
        but_undo.setEnabled(false);
        JButton but_redo = new JButton(icon_redo);
        but_redo.setEnabled(false);
        
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
		boolean cerradoDialog = false;
		try {
		  	JFileChooser selecFich = new JFileChooser();
		  	selecFich.setDialogTitle("Elegir un fichero fuente");
		  	try {
		  		FileNameExtensionFilter filtro=new FileNameExtensionFilter("C++ file","cpp","cc");
			  	selecFich.setFileFilter(filtro);
			  	//System.out.print(filtro.toString());
			  	
			  	cerradoDialog = selecFich.showOpenDialog(frmGrupo)== JFileChooser.CANCEL_OPTION;
							    	
			  	if (!cerradoDialog){
			  		fich = selecFich.getSelectedFile().getPath();
			    //System.out.print(fich.toString());
			  	}
			    	
		  	} catch (IllegalArgumentException e2){
		  		 JOptionPane.showMessageDialog (frmGrupo, "La extensión del archivo es incorrecta."); 
		  	}	  	
		    
		} catch (Exception e1) {
	    	 JOptionPane.showMessageDialog (frmGrupo, "No has seleccionado ningun fichero"); 
	    }
		
		if (!cerradoDialog){
			printFile();
		}
	}
	
	private void printFile (){
        try {  
        	JTextArea_function.setText("");
        	FileReader lector = new FileReader(fich);
            BufferedReader buffer = new BufferedReader(lector);
            String linea = "";
            int numLinea = 1;
            
            while((linea = buffer.readLine()) != null){
            	JTextArea_function.append(numLinea + ": " + linea + "\n");
            	numLinea ++;
            }
            buffer.close();
            lector.close();
        }
        catch(Exception e){     
        	e.printStackTrace();
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
