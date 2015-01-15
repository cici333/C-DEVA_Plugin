package com.wuxuehong.plugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;

import org.eclipse.ui.internal.handlers.ReuseEditorTester;

import com.wuxuehong.bean.Node;

public class ConCurrentMerge {
	ArrayList<Cluster> originClusters;
	/** the adjacency table stored the adjacent relations among clusters*/
	ArrayList<Integer>[] resultTable;
	
	public ConCurrentMerge(ArrayList<Cluster> originClusters){
		this.originClusters = originClusters;
		
	}
	
	/** Step 1 :construct the cluster network according to the similarity between them */
	private void constructCNet(){
		int i, j, clustersSize;
		Cluster a, b;
		clustersSize = originClusters.size();
		
		resultTable = new ArrayList[clustersSize];
		
		for(i = 0; i < clustersSize; i++){
			if(resultTable[i] == null){
				resultTable[i] = new ArrayList<Integer>();
			}			
			for(j = i+1; j < clustersSize; j++){
				if(calOverlapScore(originClusters.get(i), originClusters.get(j)) >= Parameters.OVERLAPPING_T){					
					if(resultTable[j] == null){
						resultTable[j] = new ArrayList<Integer>();
					}				
					resultTable[i].add(j);
					resultTable[j].add(i);
				}
			}
		}
	}
	
	/** calculate the overlap score between a and b
	 * @param Cluster a
	 * @param Clsuter b
	 *  */
	private double calOverlapScore(Cluster a, Cluster b){
		double coNeighbours = 0, abSize;
		HashSet<Node> bSet = b.getNodeSet();
				
		abSize = a.getNodeSet().size() * bSet.size();				
		if(abSize != 0){
			for(Node n : a.getNodeSet()){
				if(bSet.contains(a)){
					coNeighbours++;
				}
			}
			return coNeighbours / abSize;
		}else{
			return -1;
		}		
	}
	
	/** Step2 :   
	 * Groups that are connected to each other(either directly by an edge or indirectly by a path of edges) are then merged into protein
	 * complex candidates.
	 * */
	private void mergeComplexes(){
		
	}
	
	private class connectedGraphSearch{
		boolean[] visit = new boolean[resultTable.length];
		
		connectedGraphSearch(){
			
		}
	}
	
	
	
	
	
}
