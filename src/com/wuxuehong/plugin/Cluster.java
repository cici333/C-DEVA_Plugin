package com.wuxuehong.plugin;

import java.util.ArrayList;
import java.util.HashSet;

import com.wuxuehong.bean.Edge;
import com.wuxuehong.bean.Node;


/**
 * The single cluster */
public class Cluster {
	/** The node set of this cluster */
	private HashSet<Node> nodeSet;
	
	/** key is the node in this cluster, and value is the number of external nodes adjacent with this node*/
//	private Hashmap<Node, Integer> 
	
	/**  The cohesiveness of this cluster */
//	private double cohesiveness; 
	
	/** The seed of this cluster */	
	private Node seed;
	
	/** The set of boundary edges */
//	private HashSet<Edge> boundaryEdges;
	
	/** The set of boundary nodes */
	private ArrayList<Node> boundrayNodes;
	
	/** The set of neighbours of this cluster */
	HashSet<Node> neighbours;
	
	/** The number of the internal edges of this cluster*/
	double inDegree;
	
	/** The number of the boundary edges of this cluster*/
	double outDegree;
	
	Cluster(Node seed){
		this.seed = seed;
		nodeSet = new HashSet<Node>();
		nodeSet.add(seed);
		outDegree = seed.getNeighbour_NUM();
		inDegree = 0;
//		cohesiveness = outDegree / ( inDegree + outDegree +Parameters.PENALTY);
		neighbours = new HashSet<Node>(seed.getNeighbours());
		
		
		/** Initialize the boundaryEdges *//*
		boundaryEdges = new HashSet<Edge>(seed.getAdjacentEdges());
		for(Node neighbour : seed.getNeighbours()){
			for(Edge e: neighbour.getAdjacentEdges()){
				if(e.getNode2().getNodeID().equals(seed.getNodeID())){
					boundaryEdges.add(e);
				}
			}
		}*/
		
				
	}
	
	public double ifAddOneNeighbour(Node candidate){
		double tempIndegree = this.inDegree;
		double tempOutdegree = this.outDegree;
		for(Node n : candidate.getNeighbours()){
			if(nodeSet.contains(n)){
				tempIndegree ++;
				tempOutdegree --;
			}else{
				tempOutdegree ++;
			}
		}
		
		return tempOutdegree / ( tempIndegree + tempOutdegree +Parameters.PENALTY);		
	}
	
	public double ifRemoveOneNode(Node candidate){
		double tempIndegree = this.inDegree;
		double tempOutdegree = this.outDegree;
		for(Node n : candidate.getNeighbours()){
			if(nodeSet.contains(n)){
				tempIndegree --;
				tempOutdegree ++;
			}else{
				tempOutdegree --;
			}
		}
		
		return tempOutdegree / ( tempIndegree + tempOutdegree +Parameters.PENALTY);		
	}
	
	public void addOneNeighbour(ArrayList<Node> candidates){
		
		for(Node candidate : candidates){
			nodeSet.add(candidate);
			for(Node n : candidate.getNeighbours()){
				if(nodeSet.contains(n)){
					inDegree ++;
					outDegree --;
				}else{
					outDegree ++;
					neighbours.add(n);
				}
			}
			neighbours.remove(candidate);
		}
	}
	
	public void removeOneNode(ArrayList<Node> candidates){
		boolean flag;
		for(Node candidate : candidates){
			flag = false;
			nodeSet.remove(candidate);
			for(Node n : candidate.getNeighbours()){
				if(nodeSet.contains(n)){
					inDegree --;
					outDegree ++;
				}else{
					outDegree --;
					for(Node nn : n.getNeighbours()){
						if(nodeSet.contains(nn)){
							flag = true;
							break;
						}
					}
					if(!flag){
						neighbours.remove(n);
					}
				}
			}
			neighbours.add(candidate);
		}
		
	}
	
	public ArrayList<Node> getBoundrayNodes(){
		boundrayNodes = new ArrayList<Node>();
		for(Node node : nodeSet){
			for(Node nei : node.getNeighbours()){
				if(neighbours.contains(nei)){
					boundrayNodes.add(node);
					break;
				}
			}
		}		
		return boundrayNodes;
	}
	
	public boolean isRootNode(Node n){
		boolean isRoot = false;
		
		return false;
	}
	
	public double getCohesiveness(){
		
		return outDegree / (inDegree + outDegree +Parameters.PENALTY);
	}

	public HashSet<Node> getNeighbours() {
		return neighbours;
	}

	public HashSet<Node> getNodeSet() {
		return nodeSet;
	}
	
	
}
