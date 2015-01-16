package com.wuxuehong.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;

public class ClusterOneAlgorithm {
	
	
	/** Clusters before merged */
	ArrayList<Cluster> originClusters;
	
	public Vector<Node>[] MainProcess(){
		Vector<Node>[] resultArray;
		
		generateOriginalCluster();
		
		ConCurrentMerge result = new ConCurrentMerge();
		
		ArrayList<Vector<Node>> rs = result.MainProcess(originClusters);	
		resultArray = new Vector [rs.size()];
		int i = 0;
		for(Vector<Node> cluster : rs){
			resultArray[i] = cluster;
			i++;
		}
		 
		return resultArray;
		
	}
	
	
	/**
	 * Step1 : generate original clusters*/
	private void generateOriginalCluster(){
		/* Sort all nodes according to their nodes*/
		/** the seed list(sorted nodes) */
		SeedList seeds = new SeedList();
		Iterator<Node> seedIt = seeds.iterator();
		
		originClusters = new ArrayList<Cluster>();
		
		while(seedIt.hasNext()){
			Cluster tempC = expandingCluster(seedIt.next());
			seeds.updateUsedNodes(tempC); 			
			originClusters.add(tempC);
		}
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
