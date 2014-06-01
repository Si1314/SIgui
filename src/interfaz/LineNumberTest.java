package interfaz;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import lineNumber.LineNumberComponent;
import lineNumber.LineNumberModel;

/**

 * Demonstration of the line numbering framework in a JTextArea

 * @author Greg Cope

 *

 */

public class LineNumberTest {

		

	private JTextArea textArea = new JTextArea();

	

	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl();

	

	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);

	

	public LineNumberTest(){

		JFrame frame = new JFrame();

		JScrollPane scroller = new JScrollPane(textArea);

		scroller.setRowHeaderView(lineNumberComponent);

		frame.getContentPane().add(scroller);

		lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);

		frame.pack();

		frame.setSize(200,200);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea.getDocument().addDocumentListener(new DocumentListener(){



			@Override

			public void changedUpdate(DocumentEvent arg0) {

				

				lineNumberComponent.adjustWidth();

			}



			@Override

			public void insertUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}



			@Override

			public void removeUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

			

		});

		textArea.setText("This is a demonstration of...\n...line numbering using a JText area within...\n...a JScrollPane");

		frame.setVisible(true);

	}



	

	

	private class LineNumberModelImpl implements LineNumberModel{



		@Override

		public int getNumberLines() {

			return textArea.getLineCount();

		}



		@Override

		public Rectangle getLineRect(int line) {

			try{

				return textArea.modelToView(textArea.getLineStartOffset(line));

			}catch(BadLocationException e){

				e.printStackTrace();

				return new Rectangle();

			}

		}

	}

	

	public static void main(String[] args) throws Exception{

		SwingUtilities.invokeAndWait(new Runnable(){



			@Override

			public void run() {

				new LineNumberTest();

			}

			

		});

	}

}