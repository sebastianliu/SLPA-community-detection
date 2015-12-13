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

		// 存放邻居节点发过来的label
		HashMap<Integer, Integer> incomingVotes = new HashMap<Integer, Integer>();

		// 当前节点的每一条入边
		for (DefaultWeightedEdge edge : incomingEdges) {
			// speaker
			int speakerId = inGraph.graph.getEdgeSource(edge);
			IntegerNode speakerNode = inGraph.nodeMap.get(speakerId);

			int votedCommunity = speakerNode.speakerVote();
			int votedCommunitycount = 1;
			if (incomingVotes.containsKey(votedCommunity))
				votedCommunitycount += incomingVotes.get(votedCommunity);

			incomingVotes.put(votedCommunity, votedCommunitycount);

		}

		// listener rules（找出发过来出现次数最多的lable）
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

		// 给该点的,选定label出现次数增加一
		currentNode.updateCommunityDistribution(popularCommunity, 1);

	}

	// 迭代次数一般可以指定为100
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
