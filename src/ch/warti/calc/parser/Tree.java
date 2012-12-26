package ch.warti.calc.parser;

import java.util.ArrayList;
import java.util.List;

public class Tree {

	private List<Node> childNodes = new ArrayList<Node>();
	
	public void add(Node node) {
		childNodes.add(node);
	}

	public List<Node> getNodes() {
		return childNodes;
	}

}
