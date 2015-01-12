package com.wuxuehong.plugin;

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
	
	public void addOneNeighbour(Node candidate){
		
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
	
	public void removeOneNode(Node candidate){
		
		boolean flag = false;
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
	
	public double getCohesiveness(){
		
		return outDegree / (inDegree + outDegree +Parameters.PENALTY);
	}

	public HashSet<Node> getNeighbours() {
		return neighbours;
	}
	
	
}
