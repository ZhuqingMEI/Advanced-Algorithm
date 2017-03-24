package com.darwindev.isep.algo.tp02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Zheng on 23/03/2017.
 *
 */

public class Graph {
    private class Edge {
        int ivex;
        Edge nextEdge;
    }
    private class Node {
        String id;
        Edge firstEdge;
    };
    private Node[] adj;

    public Graph(int capacity) {
        adj = new Node[capacity];
        for (int i = 0; i < capacity; i++) {
            adj[i] = new Node();
            adj[i].id = Integer.toString(i);
            adj[i].firstEdge = null;
        }
    }

    public Graph(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath),
                StandardCharsets.UTF_8);
        addNodesToGraph(lines);
        addEdgesToGraph(lines);
    }

    public Graph() throws IOException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter the number of vertices: ");
        int num_nodes = scanner1.nextInt();
        adj = new Node[num_nodes];
        for (int i = 0; i < num_nodes; i++) {
            adj[i] = new Node();
            adj[i].id = Integer.toString(i + 1);
            adj[i].firstEdge = null;
        }
        System.out.println("Enter the number of edges: ");
        int num_edges = scanner1.nextInt();
        scanner1.nextLine();
        System.out.println("Enter the edges in graph: <to> <from>");
        for (int i = 0; i < num_edges; i++) {
            String line = scanner1.nextLine();
            String[] nodesId = line.split(" ");
            addEdgeBetweenNode(nodesId[0], nodesId[1]);
        }
    }

    private int addNodesToGraph(List<String> lines) {
        HashSet<String> set = new HashSet<String>();
        for (String line : lines) {
            String[] nodesId = line.split(" ");
            set.add(nodesId[0]);
            set.add(nodesId[1]);
        }
        adj = new Node[set.size()];
        int i = 0;
        for (String nodeId : set) {
            Node node1 = new Node();
            node1.id = nodeId;
            node1.firstEdge = null;
            adj[i] = node1;
            i++;
        }
        return set.size();
    }

    private void addEdgesToGraph(List<String> lines) {
        for (String line : lines) {
            String[] nodesId = line.split(" ");
            addEdgeBetweenNode(nodesId[0], nodesId[1]);
        }
    }

    private void addEdgeBetweenNode(String node1Id, String node2Id) {
        int node1Pos = getNodePosition(node1Id);
        int node2Pos = getNodePosition(node2Id);
        Edge edge1 = new Edge();
        edge1.ivex = node2Pos;
        edge1.nextEdge = null;
        if (adj[node1Pos].firstEdge == null) {
            adj[node1Pos].firstEdge = edge1;
        } else {
            addEdgeToNode(edge1, adj[node1Pos]);
        }
        Edge edge2 = new Edge();
        edge2.ivex = node1Pos;
        edge2.nextEdge = null;
        if (adj[node2Pos].firstEdge == null) {
            adj[node2Pos].firstEdge = edge2;
        } else {
            addEdgeToNode(edge2, adj[node2Pos]);
        }
    }

    private void addEdgeToNode(Edge edge1, Node node1) {
        Edge tEdge = node1.firstEdge;
        if (tEdge == null) {
            node1.firstEdge = edge1;
            return;
        }
        while (tEdge.nextEdge != null) {
            tEdge = tEdge.nextEdge;
        }
        tEdge.nextEdge = edge1;
    }

    private int getNodePosition(String nodeId) {
        for (int i = 0; i < adj.length; i++) {
            if (adj[i].id.equals(nodeId)) {
                return i;
            }
        }
        return -1;
    }

    private Node getNode(String nodeId) {
        for (int i = 0; i < adj.length; i++) {
            if (adj[i].id.equals(nodeId)) {
                return adj[i];
            }
        }
        return null;
    }

    public void print() {
        for (Node node1: adj) {
            System.out.print(node1.id + ": [");
            Edge tEdge = node1.firstEdge;
            while (tEdge != null) {
                System.out.print(adj[tEdge.ivex].id + ", ");
                tEdge = tEdge.nextEdge;
            }
            System.out.println("]");
        }
    }

    public void printNeighbors(Node node1) {
        Edge tEdge = node1.firstEdge;
        while (tEdge != null) {
            System.out.print(adj[tEdge.ivex].id + ", ");
            tEdge = tEdge.nextEdge;
        }
    }

    public int degree(Node node1) {
        int degree1 = 0;
        Edge tEdge = node1.firstEdge;
        while (tEdge != null) {
            degree1++;
            tEdge = tEdge.nextEdge;
        }
        return degree1;
    }

    public int order() {
        return adj.length;
    }

    public int size() {
        int total = 0;
        for (Node node1: adj) {
            Edge tEdge = node1.firstEdge;
            while (tEdge != null) {
                total++;
                tEdge = tEdge.nextEdge;
            }
        }
        return total / 2;
    }

    public int averageDegree() {
        return 2 * size() / order();
    }

    public double density() {
        int order = order();
        return 2.0 * size() / (order * order);
    }

    public int minimumDegree() {
        int minDegree = Integer.MAX_VALUE;
        for (Node node1: adj) {
            int degree1 = degree(node1);
            if (degree1 < minDegree) {
                minDegree = degree1;
            }
        }
        return minDegree;
    }

    public int maximumDegree() {
        int maxDegree = 0;
        for (Node node1: adj) {
            int degree1 = degree(node1);
            if (degree1 > maxDegree) {
                maxDegree = degree1;
            }
        }
        return maxDegree;
    }
}
