package com.wuxuehong.plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;

public class ClusterOneAlgorithm {
	/** The sorted list of nodes in current graph according to their degree */
	private PriorityQueue<Node> sortedNodeList;
	
	
	/**
	 * Sort all nodes according to their nodes*/
	private void getInitialSeeds(){
		sortedNodeList = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				// TODO Auto-generated method stub
				return o2.getNeighbour_NUM() - o1.getNeighbour_NUM();
			}
		});
		
		sortedNodeList.addAll(GraphInfo.nodelist);
	}
	
	/** 
	 * Expanding the cluster from the seed node
	 * @param the seed node of expanded cluster
	 * */
	private Cluster expandingCluster(Node seed){
		double bestCoValue, originalCohesiveness;
		ArrayList<Node> candidates = new ArrayList<Node>();
		boolean isAdd = true; 
		double tempCoValue;
		
		Cluster cluster = new Cluster(seed);
		int n = cluster.getNodeSet().size();

		double den = (n + 1) * n / 2.0;
	//	double internalWeightLimit = this.minDensity * den - nodeSet.getTotalInternalEdgeWeight();
		
		originalCohesiveness = cluster.getCohesiveness();
		bestCoValue = originalCohesiveness;
		
		for(Node neighbour : cluster.getNeighbours()){
			tempCoValue = cluster.ifAddOneNeighbour(neighbour);
			if(tempCoValue > bestCoValue){
				candidates.clear();
				candidates.add(neighbour);
				bestCoValue = tempCoValue;
			}else if(tempCoValue == bestCoValue){
				candidates.add(neighbour);
			}
			
		}
		
		if(cluster.getNodeSet().size() > 1){
			for(Node boundrayNode : cluster.getBoundrayNodes()){
				tempCoValue = cluster.ifRemoveOneNode(boundrayNode);
				
				if(tempCoValue < bestCoValue + 1e-12){
					continue;
				}
				if(tempCoValue < bestCoValue){
					continue;
				}
				
				if(cluster.isRootNode(boundrayNode)){
					continue;
				}
				if(tempCoValue > bestCoValue){
					candidates.clear();
					candidates.add(boundrayNode);
					bestCoValue = tempCoValue;
					isAdd = false;
				}else if(tempCoValue == bestCoValue){
					candidates.clear();
					candidates.add(boundrayNode);
					isAdd = false;
				}
			}
		}
		
		if(candidates.isEmpty() || bestCoValue == originalCohesiveness){
			return null;
		}
		
		if (candidates.size() > 1 && Parameters.SINGLE_MODE){
			Node single = candidates.get(0);
			candidates.clear();
			candidates.add(single);
		}
		
		if(isAdd){
			cluster.addOneNeighbour(candidates);
		}else{
			cluster.removeOneNode(candidates);
		}
		
		return cluster;
			
	}
	
	
	
	
	
}
