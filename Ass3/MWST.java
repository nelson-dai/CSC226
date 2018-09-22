/* MWST.java
   CSC 226 - Fall 2016
   Assignment 3 - Minimum Weight Spanning Tree Template
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java MWST
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java MWST file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.
   B. Bird - 08/02/2014
*/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MWST class
public class MWST{

	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int MWST(int[][] G){
		int numVerts = G.length;
		/* Find a minimum weight spanning tree by any method */
		/* (You may add extra functions if necessary) */
		
		/* ... Your code here ... */
		Heap EdgeHeap = new Heap();
		for(int i = 0;i < numVerts;i++){
			for(int j = i;j < numVerts;j++){
				if(G[i][j] > 0){
					EdgeHeap.insert(i,j,G[i][j]);
				}
			}
		}
		/* Add the weight of each edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		/* ... Your code here ... */
		int[] T = new int[numVerts-1];
		int size = 0;
		WQU UF = new WQU(numVerts);
		while(size < numVerts-1){
			Element e = EdgeHeap.top();
			if(e != null){
				EdgeHeap.delete();
				if(!UF.connected(e.a,e.b)){
					T[size] = e.weight;
					totalWeight += e.weight;
					size++;
					UF.union(e.a,e.b);
				}
			}else{
				break;
			}
		}
		return totalWeight;
		
	}
	
		
	/* main()
	   Contains code to test the MWST function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			int totalWeight = MWST(G);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
	
}











class WQU{
	private int[] id;
	private int[] sz;
	private int count;	
	
	public WQU(int N){
		count = N;
		id = new int[N];
		sz = new int[N];
		for(int i = 0;i < N;i++){
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public int count(){
		return count;
	}
	
	public boolean connected(int p, int q){
		return find(p) == find(q);
	}
	
	public int find(int p){
		while(p != id[p]){
			p = id[p];
		}
		return p;
	}
	public void union(int p, int q){
		int i = find(p);
		int j = find(q);
		if(i == j)
			return;
		if(sz[i] < sz[j]){
			id[i] = j;
			sz[j] += sz[i];
		}else{
			id[j] = i;
			sz[i] += sz[j];
		}
		count--;
	}
}

class Connection{
	int a;
	int b;
	int weight;
	Connection(int a, int b, int weight){
		this.a = a;
		this.b = b;
		this.weight = weight;
	}
}


/*
	This Heap class is from my CSC225 Assignment last year.
*/
class Heap{ 
   
	private ArrayList<Element> heap = new ArrayList<Element>();

	public int get_a(){
		Element temp = heap.get(0);
		int a = temp.a;
		return a;
	}
	
	public int get_b(){
		Element temp = heap.get(0);
		int b = temp.b;
		return b;
	}
	
	public boolean isEmpty(){
		return heap.isEmpty();
	}
	
	public void insert(int a, int b, int weight){
		Element e = new Element(a,b,weight);
		if(heap.isEmpty()){
			heap.add(e);
			return;
		}
		
        heap.add(e);
		int i = heap.size()-1;
		int parent_index = (i-1)/2;
		Element temp_child = heap.get(i);
		Element temp_parent = heap.get(parent_index);
		int child_key = get_value(temp_child);
		int parent_key = get_value(temp_parent);
		
		while(i > 0 && parent_key > child_key){
			swap(parent_index, i);
			i = parent_index;
			parent_index = (i-1)/2;
			temp_child = heap.get(i);
			temp_parent = heap.get(parent_index);
			child_key = get_value(temp_child);
			parent_key = get_value(temp_parent);
		}
	} 
	
	public void delete(){
		if(heap.isEmpty()){
			return;
		}
		
		int size = heap.size()-1;
        heap.set(0, heap.get(size));
        heap.remove(size);
		
		if(heap.isEmpty()){
			return;
		}
		
        int i = 0;
		Element temp_parent = heap.get(i);
		int parent_value = get_value(temp_parent);
		int min_child = get_min_child(i);
		Element temp_child = heap.get(min_child);
		int child_value = get_value(temp_child);
		
		while(2*i+1 < size && child_value < parent_value){

			swap(i,min_child);
			i = min_child;
			min_child = get_min_child(i);
			temp_child = heap.get(min_child);
			child_value = get_value(temp_child);
		}
	}

	public Element top(){
		if(heap.isEmpty()){
			return null;
		}
		Element temp = heap.get(0);

		return temp;
	}
	
	private int get_min_child(int i){
		Element temp_parent = heap.get(i);
		
		if(2*i+2 < heap.size()){
			
			Element left_child = heap.get(2*i+1);
			Element right_child = heap.get(2*i+2);
			
			if(get_value(left_child) < get_value(right_child)){
				return 2*i+1;
			}else{
				return 2*i+2;
			}
		}else if(2*i+1 <= heap.size()-1){
			return 2*i+1;
		}else{
			return i;
		}
	}
	
	private int get_value(Element e){
		int weight = e.weight;
		return weight;
	}
	
	private void swap(int parent, int child){
		Element temp_child = heap.get(child);	
		Element swap = heap.get(parent);
		heap.set(parent,temp_child);
		heap.set(child,swap);
	}
}