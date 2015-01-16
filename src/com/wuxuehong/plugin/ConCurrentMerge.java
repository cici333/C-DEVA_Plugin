package com.wuxuehong.plugin;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Vector;

import javax.management.Query;

import org.eclipse.ui.internal.handlers.ReuseEditorTester;

import com.wuxuehong.bean.Node;

public class ConCurrentMerge {
	ArrayList<Cluster> originClusters;
	/** the adjacency table stored the adjacent relations among clusters*/
//	ArrayList<Integer>[] resultTable;
	
	public ArrayList<Vector<Node>> MainProcess(ArrayList<Cluster> originClusters){
		this.originClusters = originClusters;
		return connectedGraphSearch(constructCNet());
		
	}
	
	/** Step 1 :construct the cluster network according to the similarity between them */
	private ArrayList<Integer>[] constructCNet(){
		int i, j, clustersSize;
		Cluster a, b;
		clustersSize = originClusters.size();
		
		ArrayList<Integer>[] resultTable = new ArrayList[clustersSize];
		
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
		return resultTable;
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
	/** use BFS here */
	private ArrayList<Vector<Node>> connectedGraphSearch(ArrayList<Integer>[] resultTable){
		boolean[] visit = new boolean[resultTable.length];
		
		ArrayList<Vector<Node>> clusters = new ArrayList<Vector<Node>>();

		
		Arrays.fill(visit, false);
		for(int i = 0; i < resultTable.length; i++){
			if(!visit[i]){					
				mergeComplexes(bfs(visit, i, resultTable));
			}
		}
		return clusters;
	}
	
	private ArrayList<Integer> bfs(boolean[] visit, int node, ArrayList<Integer>[] resultTable){
		Queue<Integer> nodeQ = new ArrayDeque<Integer>();
		ArrayList<Integer> connectedNodes= new ArrayList<Integer>();
		
		nodeQ.add(node);
		int cNode;
		while(!nodeQ.isEmpty()){
			cNode = nodeQ.poll();
			visit[cNode] = true;
			connectedNodes.add(cNode);
			
			for(int neighbour : resultTable[cNode]){
				if(!visit[neighbour]){
					nodeQ.add(neighbour);
				}
			}
		}
		nodeQ = null;
		return connectedNodes;
	}
	

	private Vector<Node> mergeComplexes(ArrayList<Integer> connectedC){
				
		Vector<Node> oneCluster = new Vector<Node>();
		for(Integer cluster : connectedC){
				oneCluster.addAll(originClusters.get(cluster).getNodeSet());
		}
		
		return oneCluster;
	}
	
	
	
	
	
}
