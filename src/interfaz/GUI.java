package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;







//import lineNumber.LineNumberComponent;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import net.miginfocom.swing.MigLayout;
import xmlViewer.XML2JTree;
import lineNumbers.LineNumbers;
import multiline.MultiLineCellRenderer;


public class GUI {

	private JFrame frmGrupo;
	private JTextPane JTextPane_function;
	//private JTextArea JTextArea_function;
	private JTable JTable_result;
	private JTextArea JTextArea_trace;
	private JTextArea JTextArea_cin;
	private JTextArea JTextArea_cout;
	private String file_name = "function";
	private File path, path_clang;
	private UndoManager undoManager = new UndoManager();
	
	//private LineNumberComponent lineNumberComponent;
	//private LineNumberModelImpl lineNumberModel;

	JButton but_redo, but_undo;
	JMenuItem menu_redo, menu_undo;
	
	String [] text_function;
	String [] text_styles;
	int[] text_length;
	
	ArrayList<ArrayList<ArrayList<ArrayList<String>>>> infoFunction;
	
	String name_function = "";
	int max_int = 20;
	int min_int = -5;
	int loops_length = 10;
	
	private  JTree    JTree_XML;
	private static    JFrame   JFrame_XML;

	private static final int FRAME_WIDTH = 440;
	private static final int FRAME_HEIGHT = 280;
	
	private Style traceStyle;
	private Style textStyle;
	private DefaultStyledDocument doc;
	
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
					openFileCC();
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
					JTextPane_function.selectAll();
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
		
		JMenu debug = new JMenu("Debug");
		JMenuItem menu_ChangeParams = new JMenuItem("Change Params to run");
		menu_ChangeParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInfoRun();
			}
		});
		JMenuItem menu_WatchClang = new JMenuItem("Watch Clang directory");
		menu_WatchClang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog (frmGrupo, path_clang.toString());
			}
		});
		JMenuItem menu_BrowseClang = new JMenuItem("Change Clang directory");
		menu_BrowseClang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileClang();
			}
		});
		JMenuItem menu_XMLclang = new JMenuItem("See XML from Clang");
		menu_XMLclang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showXML("./files/"+file_name+"XML.xml");
			}
		});
		JMenuItem menu_XMLprolog = new JMenuItem("See XML from Prolog");
		menu_XMLprolog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showXML("./files/"+file_name+"PL.xml");
			}
		});
		
		debug.add(menu_ChangeParams);
		debug.addSeparator();
		debug.add(menu_WatchClang);
		debug.add(menu_BrowseClang);
		debug.addSeparator();
		debug.add(menu_XMLclang);
		debug.add(menu_XMLprolog);
		menubar.add(debug);
		
		
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
					openFileCC();
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
					showInfoRun();
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
		frmGrupo.pack();
		frmGrupo.setLocationRelativeTo(null);
		frmGrupo.setTitle("Ejecucion simbolica");
		frmGrupo.setBounds(100, 100, 900, 500);
		frmGrupo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupo.getContentPane().setLayout(new MigLayout("", "[80.00,grow][680.00,grow][50.00,grow][250.00,grow][80.00,grow][]", "[20.00,grow][210.00,grow][10.00,grow][150.00,grow][1.00,grow][150.00,grow][1.00,grow][150.00,grow][1.00,grow][grow 600]"));
		
		frmGrupo.setJMenuBar(getMenuBar());        
        
        frmGrupo.add(getToolBar(),BorderLayout.NORTH);
		
		
		//Text Area for function
        
		JPanel JPanel_function = new JPanel();
		frmGrupo.getContentPane().add(JPanel_function, "cell 1 1 1 7,grow");
		JPanel_function.setLayout(new MigLayout("", "[grow]", "[16.00][grow]"));
		
		JLabel JLabel_function = new JLabel("C\u00F3digo de la funci\u00F3n:");
		JLabel_function.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_function.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_function.add(JLabel_function, "cell 0 0");		    
		

        StyleContext sc = new StyleContext();
        doc = new DefaultStyledDocument(sc);
		JTextPane_function = new JTextPane(doc);
		JTextPane_function.getDocument().addUndoableEditListener(
		        new UndoableEditListener() {
		          public void undoableEditHappened(UndoableEditEvent e) {
		            undoManager.addEdit(e.getEdit());
		            updateUndoRedo();
		          }
		    });
		
		traceStyle = sc.addStyle("Trace", null);
		traceStyle.addAttribute(StyleConstants.Foreground, Color.red);
		traceStyle.addAttribute(Font.PLAIN, new Integer(12));
		//traceStyle.addAttribute(StyleConstants.FontFamily, "serif");
		traceStyle.addAttribute(StyleConstants.Bold, new Boolean(true));
		
		textStyle = sc.addStyle("Text", null);
		textStyle.addAttribute(StyleConstants.Foreground, Color.black);
		textStyle.addAttribute(Font.PLAIN, new Integer(12));
		//textStyle.addAttribute(StyleConstants.FontFamily, "serif");


		JScrollPane JScrollPane_function = new JScrollPane(JTextPane_function);
		LineNumbers lineNums = new LineNumbers( JTextPane_function );
		JScrollPane_function.setRowHeaderView( lineNums );
		JTextPane_function.requestFocus();
		
		JScrollPane_function.setViewportView(JTextPane_function);
		
		JPanel_function.add(JScrollPane_function, "cell 0 1,grow");
		
		// Text Area for print trace
		
		JPanel JPanel_trace = new JPanel();
		frmGrupo.getContentPane().add(JPanel_trace, "cell 3 3,grow");
		JPanel_trace.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabel_trace = new JLabel("Traza ejecucion:");
		JLabel_trace.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_trace.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_trace.add(JLabel_trace, "cell 0 0");
		
		JScrollPane JScrollPane_trace = new JScrollPane();
		JPanel_trace.add(JScrollPane_trace, "cell 0 1, grow");
		
		JTextArea_trace = new JTextArea();
		JTextArea_trace.setEditable(false);
		JScrollPane_trace.setViewportView(JTextArea_trace);
		
		
		// Text Area for Cin
		
		JPanel JPanel_cin = new JPanel();
		frmGrupo.getContentPane().add(JPanel_cin, "cell 3 5,grow");
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
		frmGrupo.getContentPane().add(JPanel_cout, "cell 3 7,grow");
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
		/*
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
		*/
		
		// Zona resultado
		
		JPanel JPanel_result = new JPanel();
		frmGrupo.getContentPane().add(JPanel_result, "cell 3 1,grow");
		JPanel_result.setLayout(new MigLayout("", "[grow]", "[15.00][grow]"));
		
		JLabel JLabel_result = new JLabel("Tabla resultados:");
		JLabel_result.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel_result.setFont(new Font("Tahoma", Font.BOLD, 12));
		JPanel_result.add(JLabel_result, "cell 0 0");
		
		JScrollPane JScrollPane_result = new JScrollPane();
		JPanel_result.add(JScrollPane_result, "cell 0 1, grow");
		
		String[] columnNames = {"","Variable name","Value"};
		Object[][] data = {};
		JTable_result = new JTable(new DefaultTableModel(data,columnNames){
			public boolean isCellEditable(int row, int column) { return false;}
			public Class getColumnClass(int columnIndex) {return String.class; }
		});
		JTable_result.setDefaultRenderer(String.class, new MultiLineCellRenderer());
		JTable_result.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			    if (e.getClickCount() == 2) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      showInfo(row);
			      // do some action if appropriate column
			    }
			  }
			});
		//JTable_result.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
		JScrollPane_result.setViewportView(JTable_result);
		
		/* Comprobar herramienta y sino pedir ruta */
		File config_file = new File("./config/config_file.txt");
		if(!config_file.exists()){
			Object[] options = { "Browse" };
			int rc = -1;
			rc = JOptionPane.showOptionDialog(null, 
					"Please, select where have you installed Clang",
					"",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			if (rc == 0)
				openFileClang();
			//JOptionPane.showMessageDialog(null, "Please, select where have you installed Clang");
		}
		else {
			FileReader lector;
			try {
				lector = new FileReader("./config/config_file.txt");
				BufferedReader buffer = new BufferedReader(lector);
				path_clang = new File(buffer.readLine());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public void updateUndoRedo(){
		but_undo.setEnabled(undoManager.canUndo());
		menu_undo.setEnabled(undoManager.canUndo());
		but_redo.setEnabled(undoManager.canRedo());
		menu_redo.setEnabled(undoManager.canRedo());
	}
	
	public void openFileCC(){
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
			  		file_name = path.getName();
			  		file_name = file_name.substring(0, file_name.lastIndexOf("."));
			  		System.out.println(file_name.toString());
			  	}
			    	
		  	} catch (IllegalArgumentException e2){
		  		 JOptionPane.showMessageDialog (frmGrupo, "La extensi�n del archivo es incorrecta."); 
		  	}	  	
		    
		} catch (Exception e1) {
	    	 JOptionPane.showMessageDialog (frmGrupo, "No has seleccionado ningun fichero"); 
	    }
		
		if (!cerradoDialog){
			printFile(JTextPane_function);
		}
	}
	
	public void openFileClang(){
		boolean cerradoDialog = false;
		try {
		  	JFileChooser selecFich = new JFileChooser();
		  	selecFich.setDialogTitle("Elegir la aplicacion");
		  	//try {
		  		//FileNameExtensionFilter filtro=new FileNameExtensionFilter("C++ file","cpp","cc");
			  	//selecFich.setFileFilter(filtro);
			  	//System.out.print(filtro.toString());
			  	
			  	cerradoDialog = selecFich.showOpenDialog(frmGrupo)== JFileChooser.CANCEL_OPTION;
							    	
			  	if (!cerradoDialog){
			  		path_clang = selecFich.getSelectedFile();
			  		System.out.println(path_clang.toString());
			  		//file = file.substring(0, file.lastIndexOf("."));
			  		//System.out.println(file.toString());
			  	}
			  	
		} catch (Exception e1) {
	    	 JOptionPane.showMessageDialog (frmGrupo, "No has seleccionado ningun fichero"); 
	    }
		if (!cerradoDialog){
			FileWriter pw;
			try {
				File folder = new File("./config");
				if (!folder.exists()) {
					folder.mkdir();
				}
				pw = new FileWriter ("./config/"+"config_file"+".txt");
				pw.write(path_clang.toString()); //Object of JTextArea
		        pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printFile (JTextPane jtextpane){
        try {  
        	jtextpane.setText("");
        	LineNumberReader  lnr = new LineNumberReader(new FileReader(path));
        	lnr.skip(Long.MAX_VALUE);
        	        	
        	FileReader lector = new FileReader(path);
            BufferedReader buffer = new BufferedReader(lector);
            String linea = "";
            StyledDocument docu = jtextpane.getStyledDocument();
            int count = 0;
            text_length = new int[lnr.getLineNumber()+1];
            while((linea = buffer.readLine()) != null){            	
            	docu.insertString(doc.getLength(), linea + "\n", null);
     
            	if (count == 0)
            		text_length[count] = linea.length();
            	else
            		text_length[count] = text_length[count-1]+linea.length();
            	count++;
            }
            doc.setParagraphAttributes(0, JTextPane_function.getDocument().getLength(), textStyle, true);
            buffer.close();
            lector.close();
        }
        catch(Exception e){     
        	e.printStackTrace();
        }
	}
	
	public void saveTextEditor(){
		//String fileName = JOptionPane.showInputDialog("Enter file name");//String finalFileName = fileName.getText();
        FileWriter pw;
		try {
			File folder = new File("./files");
			if (!folder.exists()) {
				folder.mkdir();
			}
			pw = new FileWriter ("./files/"+file_name+".cc");
			pw.write(JTextPane_function.getText());
			text_function = JTextPane_function.getText().split("\n");
			int nlines = text_function.length;
			text_length = new int [nlines];
			int count = 0;
			for (int i=0; i<text_function.length; i++){
				text_length[i] = count;
				count += text_function[i].length();
			}
			//JTextArea_function.write(pw); //Object of JTextArea
	        pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<ArrayList<ArrayList<String>>>> parserXML(){
		ArrayList<ArrayList<ArrayList<ArrayList<String>>>> result = new ArrayList<ArrayList<ArrayList<ArrayList<String>>>>();
		try{
			FileReader lector = new FileReader("./files/"+"example"+".xml");
			BufferedReader buffer = new BufferedReader(lector);
            String line = "";
            ArrayList<ArrayList<ArrayList<String>>> current = new ArrayList<ArrayList<ArrayList<String>>>(2);
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>>variables = new ArrayList<ArrayList<String>>();
            ArrayList<String> variable = new ArrayList<String>();
            ArrayList<String> trace = new ArrayList<String>();
            ArrayList<String> cin = new ArrayList<String>();
            ArrayList<String> cout = new ArrayList<String>();
            
            while((line = buffer.readLine()) != null){
            	if (line.contains("</caso>")){
            		result.add((ArrayList<ArrayList<ArrayList<String>>>) current.clone());
            		current.clear();
            	}
            	else if (line.contains("<data>")){
            		current.add(0,(ArrayList<ArrayList<String>>) variables.clone());
            		variables.clear();
            	}
            	else if (line.contains("</data>")){
            		current.add(1,(ArrayList<ArrayList<String>>) data.clone());
            		data.clear();
            	}
            	else if (line.contains("<traza/>")){
            		data.add(0,(ArrayList<String>) trace.clone());
        			trace.clear();
            	}
            	else if (line.contains("<cin/>")){
            		data.add(1,(ArrayList<String>) cin.clone());
        			cin.clear();
            	}
            	else if (line.contains("<cout/>")){
            		data.add(2,(ArrayList<String>) cout.clone());
        			cout.clear();
            	}
            	else{ //Content case
            		if (line.contains("<variable")){
            			//Parser variable line
            			StringTokenizer aux = new StringTokenizer(line, " ");
                		String word = "";
                		while (aux.hasMoreTokens()){
                			word = aux.nextToken();
                    		if (word.contains("name")){
                    			variable.add(word.substring(word.indexOf("\"")+1, word.lastIndexOf("\"")));
                    		}
                    		else if (word.contains("value")){
                    			variable.add(word.substring(word.indexOf("\"")+1, word.lastIndexOf("\"")));
                    		}
                		}
            			variables.add((ArrayList<String>) variable.clone());
            			variable.clear();
            		}
            		if (line.contains("<traza>")){
            			//Parser traza
            			String aux_trace = line.substring(line.indexOf(">")+2,line.lastIndexOf("<"));
            			trace = new ArrayList<String>(Arrays.asList(aux_trace.split(" ")));
            			//trace = new String[aux_trace.split(" ").length];
            			data.add(0,(ArrayList<String>) trace.clone());
            			trace.clear();
            		}
            		if (line.contains("<cin>")){
            			//Parser cin
            			String aux_trace = line.substring(line.indexOf(">")+2,line.lastIndexOf("<"));
            			cin = new ArrayList<String>(Arrays.asList(aux_trace.split(" ")));
            			data.add(1,(ArrayList<String>) cin.clone());
            			cin.clear();
            		}
            		if (line.contains("<cout>")){
            			//Parser cout
            			String aux_trace = line.substring(line.indexOf(">")+2,line.lastIndexOf("<"));
            			cout = new ArrayList<String>(Arrays.asList(aux_trace.split(" ")));
            			data.add(2,(ArrayList<String>) cout.clone());
            			cout.clear();
            		}	
            	}
            }
            buffer.close();
            lector.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		infoFunction = result;
		return result;
	}
	
	public void showInfoRun()
	{
		JTextField nameFunction = new JTextField(name_function);
		JTextField maxInt = new JTextField(Integer.toString(max_int));
		JTextField minInt = new JTextField(Integer.toString(min_int));
		JTextField loops = new JTextField(Integer.toString(loops_length));
		Object[] options = {
			"Name Function:", nameFunction,
		    "Max Int:", maxInt,
		    "Min Int:", minInt,
		    "Length Loop:", loops
		};
		int option = JOptionPane.showConfirmDialog(null, options, "Config params to run", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION){
			name_function = nameFunction.getText();
			max_int = Integer.parseInt(maxInt.getText());
			min_int = Integer.parseInt(minInt.getText());
			loops_length = Integer.parseInt(loops.getText());
		}
		
	}
	
	public void showInfo(int i){
		ArrayList<ArrayList<String>> data_aux = infoFunction.get(i).get(1);
		JTextArea_cin.setText("");
		JTextArea_cout.setText("");
		JTextArea_trace.setText("");
		doc.setParagraphAttributes(0, JTextPane_function.getDocument().getLength(), textStyle, true);
		//StyleContext sc = new StyleContext();
		//JTextPane_function.setStyledDocument(new DefaultStyledDocument(sc));
		//Show cin
		if(data_aux.get(1).size() > 0){
			Iterator<String> it1 = data_aux.get(1).iterator();
			while (it1.hasNext()){
				JTextArea_cin.append(it1.next() + "\n");
			}
		}
		//Show cout
		if(data_aux.get(2).size() > 0){
			Iterator<String> it2 = data_aux.get(2).iterator();
			while (it2.hasNext()){
				JTextArea_cout.append(it2.next() + "\n");
			}
		}
		//Color trace
		if(data_aux.get(0).size() > 0){
			Iterator<String> it2 = data_aux.get(0).iterator();
			while (it2.hasNext()){
				int aux = Integer.parseInt(it2.next());
				JTextArea_trace.append(aux + "  ");
				doc.setParagraphAttributes(text_length[aux], 1, traceStyle, true);
			}
		}
	}
	
	public void showXML (String filename){
		Document doc = null;
		
		// Create a frame to "hold" our class
		JFrame_XML = new JFrame("XML to JTree");

		   Toolkit toolkit = Toolkit.getDefaultToolkit();
		   Dimension dim = toolkit.getScreenSize();
		   int screenHeight = dim.height;
		   int screenWidth = dim.width;

		   // This should display a WIDTH x HEIGHT sized Frame in the middle of the screen
		   JFrame_XML.setBounds( (screenWidth-FRAME_WIDTH)/2,
		      (screenHeight-FRAME_HEIGHT)/2, FRAME_WIDTH, FRAME_HEIGHT );

		   JFrame_XML.setBackground(Color.lightGray);
		   JFrame_XML.getContentPane().setLayout(new BorderLayout());

		   // Give our frame an icon when it's minimized.
		   JFrame_XML.setIconImage(toolkit.getImage("Wrox.gif"));

		   // Add a WindowListener so that we can close the window
		   WindowListener wndCloser = new WindowAdapter()
		   {
		      public void windowClosing(WindowEvent e)
		      {
		         //exit();
		      }
		   };
		   JFrame_XML.addWindowListener(wndCloser);
		   try
		   {
		      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		      dbf.setValidating(false);  // Not important fro this demo

		      DocumentBuilder db = dbf.newDocumentBuilder();
		      doc = db.parse(filename);
		   }
		   catch( FileNotFoundException fnfEx )
		   {
		      // Display a "nice" warning message if the file isn't there.
		      JOptionPane.showMessageDialog(JFrame_XML, filename+" was not found",
		         "Warning", JOptionPane.WARNING_MESSAGE);
		      System.out.println();
		   }
		   catch( Exception ex )
		   {
		      JOptionPane.showMessageDialog(JFrame_XML, ex.getMessage(), "Exception",
		                                    JOptionPane.WARNING_MESSAGE);
		      ex.printStackTrace();
		   }

		   Node root = (Node)doc.getDocumentElement();
		   JFrame_XML.getContentPane().add(new XML2JTree( root, true, JTree_XML, JFrame_XML ),
		      BorderLayout.CENTER);
		   JFrame_XML.validate();
		   JFrame_XML.setVisible(true);
	}
	
	public void showSolution(){
		infoFunction = parserXML();
		DefaultTableModel model = (DefaultTableModel) JTable_result.getModel();
	    model.setRowCount(0);
		Iterator<ArrayList<ArrayList<ArrayList<String>>>> it0 = infoFunction.iterator();
		int count = 0;
		int lines = 0;
		while(it0.hasNext()){
			lines = 0;
			ArrayList<ArrayList<ArrayList<String>>> aux0 = it0.next();
			Object[] current_case = new Object[3];
			current_case[0] = count;
			current_case[1] = "";
			current_case[2] = "";
			aux0.get(0);
			Iterator<ArrayList<String>> it1 = aux0.get(0).iterator();
			while(it1.hasNext()){
				lines ++;
				ArrayList<String> aux1 = it1.next();
				current_case[1] += aux1.get(0) + "\n";
				current_case[2] += aux1.get(1) + "\n";
			}
			model.addRow(current_case);
			JTable_result.setRowHeight(count,JTable_result.getRowHeight(count)*lines);
			
			count++;
		}
		
		/*
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
        */
	}
	
	public void executeSE(){
		saveTextEditor();
		showSolution();
		/*
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
				//Read XML file
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
		*/
		
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
