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
	private void expandingCluster(Node seed){
		double bestCoValue;
		ArrayList<Node> candidates = new ArrayList<Node>();
		boolean isAdd = true; 
		double tempCoValue;
		
		Cluster cluster = new Cluster(seed);
		bestCoValue = cluster.getCohesiveness();
		
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
		
	}
	
	
}
