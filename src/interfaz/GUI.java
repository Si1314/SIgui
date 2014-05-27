package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

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
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import net.miginfocom.swing.MigLayout;


public class GUI {

	private JFrame frmGrupo;
	private JTextArea JTextArea_function;
	private JTextArea JTextArea_result;
	private JTextArea JTextArea_cin;
	private JTextArea JTextArea_cout;
	private String file = "function";
	private File path;
	private UndoManager undoManager = new UndoManager();
	JButton but_redo, but_undo;
	JMenuItem menu_redo, menu_undo;
	
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
			menu_open.setIcon(new ImageIcon("./img/Open-12.png"));
			menu_open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openFile();
				}
			});
		JMenuItem menu_save = new JMenuItem("Save");
			menu_save.setEnabled(false);
			menu_save.setIcon(new ImageIcon("./img/Save-12.png"));
		JMenuItem menu_close = new JMenuItem("Close");
			menu_close.setEnabled(false);
		JMenuItem menu_closeall = new JMenuItem("Close all");
			menu_closeall.setEnabled(false);
		JMenuItem menu_exit = new JMenuItem("Exit");
			menu_exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0); 
				}
			});
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
		menu_undo = new JMenuItem("Undo");
			menu_undo.setEnabled(false);
			menu_undo.setIcon(new ImageIcon("./img/Undo-12.png"));
			menu_undo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						undoManager.undo();
				    } 
					catch (CannotRedoException cre) {
				          cre.printStackTrace();
				    }
					updateUndoRedo();
				}
			});
		menu_redo = new JMenuItem("Redo");
			menu_redo.setEnabled(false);
			menu_redo.setIcon(new ImageIcon("./img/Redo-12.png"));
			menu_redo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						undoManager.redo();
				    } 
					catch (CannotRedoException cre) {
				          cre.printStackTrace();
				    }
					updateUndoRedo();
				}
			});
		JMenuItem menu_cut = new JMenuItem(new DefaultEditorKit.CutAction());
			menu_cut.setIcon(new ImageIcon("./img/Cut-12.png"));
			menu_cut.setText("Cut");
		JMenuItem menu_copy = new JMenuItem(new DefaultEditorKit.CopyAction());
			menu_copy.setIcon(new ImageIcon("./img/Copy-12.png"));
			menu_copy.setText("Copy");
		JMenuItem menu_paste = new JMenuItem(new DefaultEditorKit.PasteAction());
			menu_paste.setIcon(new ImageIcon("./img/Paste-12.png"));
			menu_paste.setText("Paste");
		JMenuItem menu_delete = new JMenuItem("Delete");
			menu_delete.setEnabled(false);
		JMenuItem menu_selectall = new JMenuItem("Select all");
			//menu_selectall.setEnabled(false);
			menu_selectall.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTextArea_function.selectAll();
				}
			});
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
	        but_play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					executeSE();
				}
			});
        JButton but_print = new JButton(icon_print);
        but_print.setEnabled(false);
        JButton but_save = new JButton(icon_save);
        but_save.setEnabled(false);
        but_undo = new JButton(icon_undo);
        	but_undo.setEnabled(false);
        	but_undo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						undoManager.undo();
				    } 
					catch (CannotRedoException cre) {
				          cre.printStackTrace();
				    }
					updateUndoRedo();
				}
			});
        but_redo = new JButton(icon_redo);
        	but_redo.setEnabled(false);
	        but_redo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						undoManager.redo();
				    } 
					catch (CannotRedoException cre) {
				          cre.printStackTrace();
				    }
					updateUndoRedo();
				}
			});
        
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
		frmGrupo.getContentPane().setLayout(new MigLayout("", "[100.00,grow][680.00,grow][80.00,grow][200.00,grow][100.00,grow][]", "[20.00,grow][210.00,grow][64.00,grow][210.00,grow][grow 600]"));
		
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
			JTextArea_function.getDocument().addUndoableEditListener(
		        new UndoableEditListener() {
		          public void undoableEditHappened(UndoableEditEvent e) {
		            undoManager.addEdit(e.getEdit());
		            updateUndoRedo();
		          }
		    });
		JScrollPane_function.setViewportView(JTextArea_function);
		
		// Text Area for Cin
		
		JPanel JPanel_cin = new JPanel();
		frmGrupo.getContentPane().add(JPanel_cin, "cell 3 1,grow");
		JPanel_cin.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabel_cin = new JLabel("Consola entrada:");
		JLabel_cin.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_cin.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_cin.add(JLabel_cin, "cell 0 0");
		
		JScrollPane JScrollPane_cin = new JScrollPane();
		JPanel_cin.add(JScrollPane_cin, "cell 0 1, grow");
		
		JTextArea_cin = new JTextArea();
		JTextArea_cin.setEditable(false);
		JScrollPane_cin.setViewportView(JTextArea_cin);
		
		// Text Area for Cout
		
		JPanel JPanel_cout = new JPanel();
		frmGrupo.getContentPane().add(JPanel_cout, "cell 3 3,grow");
		JPanel_cout.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabel_cout = new JLabel("Consola salida:");
		JLabel_cout.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_cout.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_cout.add(JLabel_cout, "cell 0 0");
		
		JScrollPane JScrollPane_cout = new JScrollPane();
		JPanel_cout.add(JScrollPane_cout, "cell 0 1, grow");
		
		JTextArea_cout = new JTextArea();
		JTextArea_cout.setEditable(false);
		JScrollPane_cout.setViewportView(JTextArea_cout);
		
		// Boton play
		
		JPanel JPanel_play = new JPanel();
		frmGrupo.getContentPane().add(JPanel_play, "cell 1 2,grow");
		JPanel_play.setLayout(new MigLayout("","[right]","[grow]"));
		
		JButton JButton_play = new JButton("Play",new ImageIcon("./img/Play1Pressed_24.png"));
			JButton_play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					executeSE();
				}
			});
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
	
	public void updateUndoRedo(){
		but_undo.setEnabled(undoManager.canUndo());
		menu_undo.setEnabled(undoManager.canUndo());
		but_redo.setEnabled(undoManager.canRedo());
		menu_redo.setEnabled(undoManager.canRedo());
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
			  		path = selecFich.getSelectedFile();
			  		file = path.getName();
			  		file = file.substring(0, file.lastIndexOf("."));
			  		System.out.println(file.toString());
			  	}
			    	
		  	} catch (IllegalArgumentException e2){
		  		 JOptionPane.showMessageDialog (frmGrupo, "La extensi�n del archivo es incorrecta."); 
		  	}	  	
		    
		} catch (Exception e1) {
	    	 JOptionPane.showMessageDialog (frmGrupo, "No has seleccionado ningun fichero"); 
	    }
		
		if (!cerradoDialog){
			printFile(JTextArea_function);
		}
	}
	
	public void printFile (JTextArea jtextarea){
        try {  
        	jtextarea.setText("");
        	FileReader lector = new FileReader(path);
            BufferedReader buffer = new BufferedReader(lector);
            String linea = "";
            
            while((linea = buffer.readLine()) != null){
            	jtextarea.append(linea + "\n");
            }
            buffer.close();
            lector.close();
        }
        catch(Exception e){     
        	e.printStackTrace();
        }
	}
	
	public void saveTextEditor(JTextArea jtextarea){
		//String fileName = JOptionPane.showInputDialog("Enter file name");//String finalFileName = fileName.getText();
        FileWriter pw;
		try {
			File folder = new File("./files");
			if (!folder.exists()) {
				folder.mkdir();
			}
			pw = new FileWriter ("./files/"+file+".cc");
			JTextArea_function.write(pw); //Object of JTextArea
	        pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSolution(JTextArea jtextarea){
		try {  
        	jtextarea.setText("");
        	FileReader lector = new FileReader("./files/"+file+"PL.xml");
            BufferedReader buffer = new BufferedReader(lector);
            String line = "";
            boolean first = true;
            
            while((line = buffer.readLine()) != null){
            	if (line.equals("<table/>"))
            		if(first)
            			first = false;
            		else
            			jtextarea.append("\n");
            	else{
            		StringTokenizer aux = new StringTokenizer(line, " ");
            		String word = "";
            		while (aux.hasMoreTokens()){
            			word = aux.nextToken();
                		if (word.contains("name")){
                			jtextarea.append(word.substring(word.indexOf("\"")+1, word.lastIndexOf("\"")));
                			jtextarea.append(": ");
                		}
                		else if (word.contains("value")){
                			jtextarea.append(word.substring(word.indexOf("\"")+1, word.lastIndexOf("\"")));
                			jtextarea.append("\t");
                		}
            		}
            	}
            }
            buffer.close();
            lector.close();
        }
        catch(Exception e){     
        	e.printStackTrace();
        }
	}
	
	public void executeSE(){
		saveTextEditor(JTextArea_function);
		//TODO
		try {
			String workingDirectory = System.getProperty("user.dir");
			ProcessBuilder builder = new ProcessBuilder("./files/runPFCTool.sh",workingDirectory+"/files/"+file+".cc",workingDirectory+"/files/"+file+"XML.xml");
			System.out.println(builder.command());
			Process p = builder.start();
			
			int status = p.waitFor();
			System.out.println(status);
			System.out.println("working directory = "+System.getProperty("user.dir"));
			if (status == 0){
				ProcessBuilder prolog = new ProcessBuilder("./files/runInterpreter.sh","\"interpreter('./files/"+file+"XML.xml','./files/"+file+"PL.xml').\"");
				System.out.println(prolog.command());
				Process p2 = prolog.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(p2.getInputStream()));
				String line = reader.readLine();
				System.out.println(line);
				int status2 = p2.waitFor();
				System.out.println(status2);
				showSolution(JTextArea_result);
			}
			//execute Clang with files(nameFile.cc)
			//execute Prolog with (nameFileXML.xml)
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runGUI() {
		// LookAndFeel + posici�n inicial
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
