package com.computinglife.slpa.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.jgrapht.graph.DefaultWeightedEdge;

import com.computinglife.slpa.entity.IntegerNode;
import com.computinglife.slpa.entity.IntegerNodeGraph;
import com.computinglife.slpa.util.GraphReader;

public class CommunityFinder {

	public IntegerNodeGraph inGraph;

	public CommunityFinder() {
		inGraph = new IntegerNodeGraph();
	}

	public void updateLabels(IntegerNode currentNode) {

		Set<DefaultWeightedEdge> incomingEdges = inGraph.graph.incomingEdgesOf(currentNode.id);

		HashMap<Integer, Integer> incomingVotes = new HashMap<Integer, Integer>();

		// For each vertex V with an incoming edge to the current node
		for (DefaultWeightedEdge edge : incomingEdges) {
			int speakerId = inGraph.graph.getEdgeSource(edge);
			IntegerNode speakerNode = inGraph.nodeMap.get(speakerId);

			int votedCommunity = speakerNode.speakerVote();
			int votedCommunitycount = 1;
			if (incomingVotes.containsKey(votedCommunity))
				votedCommunitycount += incomingVotes.get(votedCommunity);

			incomingVotes.put(votedCommunity, votedCommunitycount);

		}

		// Find the most popular vote
		Iterator<Entry<Integer, Integer>> it = incomingVotes.entrySet().iterator();
		int popularCommunity = -1;
		int popularCommunityCount = 0;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			if (entry.getValue() > popularCommunityCount) {
				popularCommunity = entry.getKey();
				popularCommunityCount = entry.getValue();
			}
		}

		// Update community distribution of the current node by 1
		currentNode.updateCommunityDistribution(popularCommunity, 1);

	}

	public void SLPA(int iterations) {

		for (int i = 0; i < iterations; i++) {
			// For every node in the graph
			for (IntegerNode node : inGraph.nodeMap) {
				updateLabels(node);
			}
		}

	}

	public void initIntegerNodeGraph(String fileName) {

		File f = new File(fileName);

		try {
			inGraph.graph = GraphReader.readGraph(f);
			int nodeCount = inGraph.graph.vertexSet().size();
			for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
				IntegerNode node = new IntegerNode(nodeId, nodeId);
				node.updateCommunityDistribution(nodeId, 1);
				inGraph.nodeMap.add(node);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String arg[]) {

		String fileName = arg[0];
		int iterations = Integer.parseInt(arg[1]);

		File f = new File(fileName);
		if (!f.exists()) {
			System.out.println(f.getAbsolutePath() + " is not a valid file");
			System.exit(1);
		}

		CommunityFinder cf = new CommunityFinder();
		System.out.println("Reading graph input file: " + f.getAbsolutePath() + " ..");
		cf.initIntegerNodeGraph(fileName);
		System.out.println("Successfully initialized IntegerNodeGraph with " + cf.inGraph.getVertexCount()
				+ " vertices and " + cf.inGraph.getEdgeCount() + " edges ..");
		System.out.println("Invoking SLPA with " + iterations + " iterations ..");

		cf.SLPA(iterations);

		System.out.println("Completed SLPA");

	}

}
