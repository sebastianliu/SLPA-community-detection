package com.computinglife.slpa.entity;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class IntegerNodeGraph {

	public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
	public ArrayList<IntegerNode> nodeMap;

	public IntegerNodeGraph() {
		nodeMap = new ArrayList<IntegerNode>();
	}

	public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public void setGraph(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}

	public ArrayList<IntegerNode> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(ArrayList<IntegerNode> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public void addNodeToMap(int index, IntegerNode node) {
		nodeMap.add(index, node);
	}

	public int getVertexCount() {
		return nodeMap.size();
	}

	public int getEdgeCount() {
		return graph.edgeSet().size();
	}

	public static void main(String arg[]) {
		IntegerNodeGraph ingraph = new IntegerNodeGraph();
		ingraph.nodeMap.add(new IntegerNode(1, 1));
		ingraph.nodeMap.get(0).id = 2;
		System.out.println(ingraph.nodeMap.get(0).id);
	}

}
