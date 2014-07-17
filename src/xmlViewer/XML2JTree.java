package xmlViewer;

//Import the W3C DOM clas ses
import org.w3c.dom.*;

//We are going to use JAXP's classes for DOM I/O
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

//Import other Java classes
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;

/** 
Displays XML in a JTree
*/

public class XML2JTree extends JPanel
{
//private  JTree    jTree;
//private static    JFrame   frame;

public static final int FRAME_WIDTH = 440;
public static final int FRAME_HEIGHT = 280;

public XML2JTree( Node root, boolean showDetails, JTree jTree_aux, JFrame jFrame_aux )
{
   // Take a DOM and convert it to a Tree model for the JTree
   DefaultMutableTreeNode top = createTreeNode(root, showDetails );
   DefaultTreeModel dtModel = new DefaultTreeModel(top);

   // Create our JTree
   jTree_aux = new JTree(dtModel);

   // We have no tree listener but this looks more natrual
   jTree_aux.getSelectionModel().setSelectionMode(
      TreeSelectionModel.SINGLE_TREE_SELECTION);
   jTree_aux.setShowsRootHandles(true);

   // If this were editable it would result in too much code
   // for this demo.
   jTree_aux.setEditable(false);

   // Create a new JScrollPane but override one of the methods.
   JScrollPane jScroll = new JScrollPane()
   {
      // This keeps the scrollpane a reasonable size
      public Dimension getPreferredSize()
      {
         return new Dimension( FRAME_WIDTH-20, FRAME_HEIGHT-40 );
      }
   };

   // Wrap the JTree in a JScroll so that we can scroll it in the JSplitPane.
   jScroll.getViewport().add(jTree_aux);

   JPanel panel = new JPanel();
   panel.setLayout(new BorderLayout());   
   panel.add("Center", jScroll);
   add("Center", panel);
}

/**

This takes a DOM Node and recurses through the children until each one is
added to a DefaultMutableTreeNode. This can then be used by the JTree as a
tree model. The second parameter can be used to provide more visual detail
for debugging.

*/
protected DefaultMutableTreeNode createTreeNode( Node root, boolean showDetails )
{
   DefaultMutableTreeNode dmtNode = null;

   String type = getNodeType(root);
   String name = root.getNodeName();
   String value = root.getNodeValue();

   if( showDetails )
   {
      dmtNode = new DefaultMutableTreeNode("["+type+"] --> "+name+" = "+value);
   }
   else
   {
      // Special case for TEXT_NODE, others are similar but not catered for here.
      dmtNode = new DefaultMutableTreeNode(
         root.getNodeType() == Node.TEXT_NODE ? value : name );
   }

   // Display the attributes if there are any
   NamedNodeMap attribs = root.getAttributes();
   if(attribs != null && showDetails )
   {
      for( int i = 0; i < attribs.getLength(); i++ )
      {
         Node attNode = attribs.item(i);
         String attName = attNode.getNodeName().trim();
         String attValue = attNode.getNodeValue().trim();

         if(attValue != null)
         {
            if (attValue.length() > 0)
            {
               dmtNode.add(new DefaultMutableTreeNode(
                  "[Attribute] --> "+attName+"=\""+attValue+"\"") );
            }
         }
      }
   }

   // If there are any children and they are non-null then recurse...
   if(root.hasChildNodes())
   {
      NodeList childNodes = root.getChildNodes();
      if(childNodes != null)
      {
         for (int k=0; k<childNodes.getLength(); k++)
         {
            Node nd = childNodes.item(k);
            if( nd != null )
            {
               // A special case could be made for each Node type.
               if( nd.getNodeType() == Node.ELEMENT_NODE )
               {
                  dmtNode.add(createTreeNode(nd, showDetails));
               }

               // This is the default
               String data = nd.getNodeValue();
               if(data != null)
               {
                  data = data.trim();
                  if(!data.equals("\n") && !data.equals("\r\n") &&
                     data.length() > 0)
                  {
                     dmtNode.add(createTreeNode(nd, showDetails));
                  }
               }
            }
         }
      }
   }
   return dmtNode;
}

/**

This simple method returns a displayable string given a NodeType

*/
public String getNodeType( Node node )
{
   String type;

   switch( node.getNodeType() )
   {
      case Node.ELEMENT_NODE:
      {
         type = "Element";
         break;
      }
      case Node.ATTRIBUTE_NODE:
      {
         type = "Attribute";
         break;
      }
      case Node.TEXT_NODE:
      {
         type = "Text";
         break;
      }
      case Node.CDATA_SECTION_NODE:
      {
         type = "CData section";
         break;
      }
      case Node.ENTITY_REFERENCE_NODE:
      {
         type = "Entity reference";
         break;
      }
      case Node.ENTITY_NODE:
      {
         type = "Entity";
         break;
      }
      case Node.PROCESSING_INSTRUCTION_NODE:
      {
         type = "Processing instruction";
         break;
      }
      case Node.COMMENT_NODE:
      {
         type = "Comment";
         break;
      }
      case Node.DOCUMENT_NODE:
      {
         type = "Document";
         break;
      }
      case Node.DOCUMENT_TYPE_NODE:
      {
         type = "Document type";
         break;
      }
      case Node.DOCUMENT_FRAGMENT_NODE:
      {
         type = "Document fragment";
         break;
      }
      case Node.NOTATION_NODE:
      {
         type = "Notation";
         break;
      }
      default:
      {
         type = "Unknown, contact Sun!";
         break;
      }
   }
   return type;
}

/**

A common point of exit...

*/

private static void exit()
{
   System.out.println("Graceful exit");
   System.exit(0);
}
}
