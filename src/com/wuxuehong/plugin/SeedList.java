package com.wuxuehong.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;

public class SeedList implements Iterable<Node>  {
	/** The sorted list of nodes in current graph according to their degree */
	private ArrayList<Node> sortedNodeList;
	private HashSet<Node> usedNodes;
	private ListIterator<Node> sortedNIterator;
	
	private class seedIterator implements Iterator{
		
		/**
		 * Sort all nodes according to their nodes*/
		private void seedIterator(){
			sortedNodeList = new ArrayList<Node>();
			sortedNodeList.addAll(GraphInfo.nodelist);
			Collections.sort(sortedNodeList, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					int rs = o2.getNeighbour_NUM() - o1.getNeighbour_NUM();
					if(rs != 0){
						return rs;
					}else{
						return o1.getNodeID().compareTo(o2.getNodeID());
					}
					
				}
			});		
			usedNodes = new HashSet<Node>();
			sortedNIterator = sortedNodeList.listIterator();
		}
		
		
		
		

		@Override
		public boolean hasNext() {
			
			while(sortedNIterator.hasNext()){
				if(!usedNodes.contains(sortedNIterator.next())){
					sortedNIterator.previous();
					return true;
				}
			}
			return false;
		}

		@Override
		public Node next() {
			return sortedNIterator.next();
		}
		
	}
	
	
	/** put the nodes in cluster into the uesedNodes
	 * @param cluster
	 * */
	public void updateUsedNodes(Cluster cluster){
		for(Node n : cluster.getNodeSet()){
			usedNodes.add(n);
		}
	}
	
	@Override
	public Iterator<Node> iterator() {
		// TODO Auto-generated method stub
		return new seedIterator();
	}
	

}
