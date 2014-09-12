package renders;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class LineNumbers extends JList {

    private JList list;
    private boolean started;
    private JTextPane textPane;
    private Element root;
    private Font font;

    private LineNumbers() {
    }

    public LineNumbers(JTextPane textPane) {
        super();
        list = new LineNumbers();
        this.textPane = textPane;
        root = textPane.getDocument().getDefaultRootElement();
        setModel(new TextComponentListModel());
        setCellRenderer(new RowHeaderRenderer());
        setSizes(textPane.getFont());
    }

    class TextComponentListModel extends AbstractListModel implements DocumentListener, CaretListener {

        private int currentLines;

        public TextComponentListModel() {
            textPane.getDocument().addDocumentListener(this);
            textPane.addCaretListener(this);
        }

        @Override
        public int getSize() {
            return root.getElementCount();
        }

        @Override
        public Object getElementAt(int index) {
            int lines = root.getElementCount();
            if (index < lines) {
                return "" + (index + 1);
            } else {
                return "";
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            font = textPane.getFont();
            setSizes(font);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            int lines = root.getElementCount();
            if (lines >= currentLines) {
                fireIntervalAdded(this, currentLines, lines);
                currentLines = lines;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            int lines = root.getElementCount();
            if (lines <= currentLines) {
                fireIntervalRemoved(this, currentLines, lines);
                currentLines = lines;
            }
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            int offset = textPane.getCaretPosition();
            int line = root.getElementIndex(offset);
            list.setSelectedIndex(line);
        }
    } // end TextComponentListModel

    class RowHeaderRenderer extends JLabel implements ListCellRenderer {

        RowHeaderRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }
// Returns the JLabel after setting the text of the cell

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (started) {
                setFont(textPane.getFont());
            } else {
                setFont(new Font("monospaced", 0, 12));
                started = true;
            }
            setText((value == null) ? "" : value.toString());
            int offset = textPane.getCaretPosition();
            int line = root.getElementIndex(offset);
            setBackground(new Color(235, 235, 235)); // very light gray
            setForeground((index == line) ? Color.red : Color.black);
//            if (isSelected) {
//                setBackground(Color.GREEN);
//                textPane.setSelectionColor(Color.cyan);
//            }
            return this;
        }
    }

    public void setSizes(Font font) {
        FontMetrics fm = getFontMetrics(font);
        setFixedCellHeight(new Integer(fm.getHeight()));
        setFixedCellWidth(fm.stringWidth(" 0000 "));
    }
}

