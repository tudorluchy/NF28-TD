package fr.utc.nf28.examples;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This examples show how to manipulate trees in Java 
 * using the utility class DefaultMutableTreeNode. 
 */
public class TreeNodeExample {

	public static void main(String[] args) {
		
		// Creating a root node
		DefaultMutableTreeNode europe = new DefaultMutableTreeNode("Europe");
		
		// Creating two countries and adding them to Europe
		DefaultMutableTreeNode france = new DefaultMutableTreeNode("France");
		DefaultMutableTreeNode allemagne = new DefaultMutableTreeNode("Allemagne");
		europe.add(france);
		europe.add(allemagne);
		
		// Create two regions and adding them to France
		DefaultMutableTreeNode picardie = new DefaultMutableTreeNode("Picardie");
		DefaultMutableTreeNode midiPyrenees = new DefaultMutableTreeNode("Midi-Pyrénées");
		france.add(picardie);
		france.add(midiPyrenees);
		
		// Creating departments and adding them to regions
		DefaultMutableTreeNode oise = new DefaultMutableTreeNode("Oise");
		DefaultMutableTreeNode aisne = new DefaultMutableTreeNode("Aisne");
		DefaultMutableTreeNode somme = new DefaultMutableTreeNode("Somme");
		picardie.add(oise);
		picardie.add(aisne);
		picardie.add(somme);
		
		DefaultMutableTreeNode hautesPyrenees = new DefaultMutableTreeNode("Hautes-Pyrénées");
		midiPyrenees.add(hautesPyrenees);
		
		// Adding cities to departments
		DefaultMutableTreeNode beauvais = new DefaultMutableTreeNode("Beauvais",false);
		DefaultMutableTreeNode compiegne = new DefaultMutableTreeNode("Compiègne",false);
		oise.add(beauvais);
		oise.add(compiegne);
		
		DefaultMutableTreeNode laon = new DefaultMutableTreeNode("Laon",false);
		aisne.add(laon);
		
		DefaultMutableTreeNode amiens = new DefaultMutableTreeNode("Amiens",false);
		somme.add(amiens);
		
		DefaultMutableTreeNode tarbes = new DefaultMutableTreeNode("Tarbes",false);
		hautesPyrenees.add(tarbes);
		
		// Some interesting queries
		System.out.println("Europe's children count: " + europe.getChildCount());
		for (int i = 0; i < europe.getChildCount(); i++)
			System.out.println(" country " + (i+1) + ": " + europe.getChildAt(i));
		
		System.out.println("Europe's depth: " + europe.getDepth());
		System.out.println("Europe's parent: " + europe.getParent());
		System.out.println("Is compiegne inside Picardie? " +
				compiegne.isNodeAncestor(picardie));	
		System.out.println("Is Compiegne a sibling of of Beauvais? " +
					compiegne.isNodeSibling(beauvais));
		
		System.out.println("\nPre-order navigation:");
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> preorder = europe.preorderEnumeration();
		while(preorder.hasMoreElements()) {
			System.out.println(preorder.nextElement());
		}
		
		System.out.println("\nDepht-first order navigation:");
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> depth = europe.depthFirstEnumeration();
		while(depth.hasMoreElements()) {
			System.out.println(depth.nextElement());
		}			
		
	}

}
