package com.computinglife.slpa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * 读图工具类
 * 
 * @author youngliu
 *
 */
public class GraphReader {

	/*
	 * 这里假定图数据文件每行为一个有向边，(source --> target) 第一行为整个图中点和边的总数（第一个为点总数，第二个为边总数）
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

			// init edges
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
		// init over
		return graph;
	}

	// just for test
	public static void main(String arg[]) {

		try {
			File f = new File(arg[0]);
			System.out.println(f.getAbsolutePath());
			readGraph(f);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
