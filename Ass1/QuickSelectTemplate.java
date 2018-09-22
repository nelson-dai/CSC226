//Template is created by Zhuoli Xiao, on Sept. 19st, 2016.
//Only used for Lab 226, 2016 Fall. 
//All Rights Reserved. 


import java.util.Arrays;
import java.util.Random;


public class QuickSelect {
	//Function to invoke quickslect
	public static int[] QS(int[] S, int k){
        if (S.length==1)
        	return S;
        //initialize array to store result
        int result[]=new int[k];
        for(int i=0;i<result.length;i++){
        	result[i]=0;
        }
        quickSelect(0,S.length-1,S,k);
        //fill your result array here

		return result;
	}
    
    //do quickSort in a recursive way 
    private static void quickSelect(int left, int right, int[] array, int k){
    	return;
    }
    //do Partition with a privot
    private static int partition(int left, int right, int[] array, int p){
    	return 0;
    }

    //Pick a random pivot to do the QuickSort
	private static int pickRandomPivot(int[] S){
		int index=0;
		Random rand= new Random();
		index = rand.nextInt(S.length-1);
		return S[index];  
	}
	//swap two elements in the array
	private static void swap(int[]array, int a, int b){
 		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}


	//Our main function to test the algorithm
	public static void main (String[] args){
  		int [] array1 ={12,13,17,14,21,3,4,9,21,8};
  		int [] array2 ={14,8,22,18,6,2,15,84,13,12};
  		int [] array3 ={6,8,14,18,22,2,12,13,15,84};
   
  		System.out.println(Arrays.toString(QS(array1,5)));
  		System.out.println(Arrays.toString(QS(array2,7)));
  		System.out.println(Arrays.toString(QS(array3,5)));  
	}
}