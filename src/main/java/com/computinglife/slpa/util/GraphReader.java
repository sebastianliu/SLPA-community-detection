package com.computinglife.slpa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class GraphReader {

	/*
	 * Expects a edge list file. First line contains total number of vertices
	 * and edges in the graph.
	 * 
	 */
	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> readGraph(File f)
			throws FileNotFoundException {

		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		try {
			String[] sizes = br.readLine().split("\t");
			int nodes = Integer.parseInt(sizes[0]);
			int edges = Integer.parseInt(sizes[1]);
			for (int i = 0; i < nodes; i++)
				graph.addVertex(new Integer(i));

			for (int i = 1; i <= edges; i++) {
				String[] vertices = br.readLine().split("\t");
				Integer sourceV = Integer.parseInt(vertices[0]);
				Integer targetV = Integer.parseInt(vertices[1]);
				graph.addEdge(sourceV, targetV);
			}

			System.out.println("Finished reading input graph<" + nodes + ", " + edges + ">");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return graph;
	}

	public static void main(String arg[]) {

		try {
			File f = new File(arg[0]);
			// BufferedReader br = new BufferedReader(new InputStreamReader(new
			// FileInputStream(f)));

			System.out.println(f.getAbsolutePath());
			readGraph(f);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
