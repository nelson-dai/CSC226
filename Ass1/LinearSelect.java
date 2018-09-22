
//modified by Nelson Dai-V00815253 on Sept. 25, 2016
//from Rich Little on Sept. 23, 2016

/***********************************************************************************************************************/
  //Comparison:   QuickSelect   LinearSelect     Answer
  //      25        801305	   66332	   9
  //      125       870579	   150430	   82
  //      625       1058930	   777023	   425
  //      3125      1138291	   1353276	   3109
  //      15625	    6666287	   7690868         5656
  //      78125	    13541637	   32515919	   22479
  //      390625    25100444	   69335077	   270230
  //
  // Conclusion: We can easily notice that as the size of the text grow 
  //             the difference between the running time of O(n) and O(nlogn) is getting bigger.
  //             The LinearSelect requires more time to compute. 
  //             That's why algorithm is playing a very important role in programming.               
/***********************************************************************************************************************/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;


public class LinearSelect {
/*******************************************actualwork**************************************************************/												
	public static int LS(int[] S, int k){
        if (S.length==1)
        	return S[0];     
        return linearSelect(0,S.length-1,S,k);
	}
    
    private static int linearSelect(int left,int right, int[] array, int k){
    	if (left>=right){
    		return array[left];
    	} 
    	int p=pickCleverPivot(left,right,array);
    	int eIndex=partition(left,right,array,p);
    	if (k<=eIndex){
    		return linearSelect(left,eIndex-1,array,k);
    	}else if(k==eIndex+1){
    		return array[eIndex];
    	}else{
    		return linearSelect(eIndex+1,right,array,k);
    	}
    }  

    public static int getMid(int left, int right, int [] array) {
    	int[] val= new int [right-left+1];                                        
    	for (int i=left; i<=right;i++) {
    		val[i-left]=array[i];
    	}
		for(int i=1; i<val.length-1; i++){
			int j=i; int toInsert=val[i];
			while((j>0)&&(val[j-1]>toInsert)){
				val[j]=val[j-1];
				j--;
			}
			val[j]=toInsert;
		}
		
    	int mid = val[(val.length-1)/2];
    	for (int i=left;i<=right;i++) {
    		if (array[i]==mid) {
    			return i;
    		}
    	}
    	return -1;
    }

	private static int pickCleverPivot(int left, int right, int [] array){
		int n = right-left+1;
		if (n<=5) {
			return getMid(left,right,array);
		}
		int count=0;
		for (int i=left;i<=right;i+=5) {
			int end= i+4<=right? i+4 : right;
			int index=getMid(i,end,array);
			swap(array,index,count+left);
			count++;
		}
		return pickCleverPivot(left,left+count-1,array);
	}
/***********************************************************************************************************************/
	
	private static void swap(int[]array, int a, int b){
 		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
	
	private static int partition(int left, int right, int[] array, int pIndex){
    	swap(array,pIndex,right);
    	int p=array[right];
    	int l=left;
    	int r=right-1;
    	while(l<=r){
    		while(l<=r && array[l]<=p){
    			l++;
    		}
    		while(l<=r && array[r]>=p){
    			r--;
    		}
    		if (l<r){
    			swap(array,l,r);
    		}
    	}
        swap(array,l,right);
    	return l;
    }

	/* main()
	   Contains code to test the QuickSelect. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the QuickSelect class above
	   will be considered during marking.
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
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);
		
		int k = inputVector.get(0);

		int[] array = new int[inputVector.size()-1];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i+1);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.nanoTime();

		int kthsmallest = LS(array,k);

		long endTime = System.nanoTime();

		long totalTime = (endTime-startTime);

		System.out.printf("The %d-th smallest element in the input list of size %d is %d.\n",k,array.length,kthsmallest);
		System.out.printf("Total Time (nanoseconds): %d\n",totalTime);
	}
}

