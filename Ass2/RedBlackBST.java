import java.util.*;
import java.io.*;

public class RedBlackBST<Key extends Comparable<Key>>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    private class Node{
        Key key;
        Node left, right; 
        int N; 
        boolean color;

        Node(Key key, int N, boolean color){
            this.key = key;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(Node x){
        if(x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)+ size(h.right);
        return x;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)
        + size(h.right);
        return x;
    }

    private void flipColors(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }


    public int size(){ 
        return size(root); 
    }

    private int size(Node x){
        if (x == null) return 0;
        else return x.N;
    }

    public void put(Key key){
        root = put(root, key);
        root.color= BLACK;
    }

    private Node put(Node h, Key key){
        if(h == null) return new Node(key, 1, RED);

        int cmp = key.compareTo(h.key);
        if(cmp < 0) h.left = put(h.left, key);
        else if(cmp > 0) h.right = put(h.right, key);
        else h.key = key;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public double redPercentage(){
        int r = countRed(root);
        return (double)r/root.N;
    }

    public int countRed(Node n){
        if(n == null) return 0;

        int r1= countRed(n.left);
        int r2= countRed(n.right);

        int sum = r1+r2;
        if(isRed(n)) sum++;
        return sum;
    }
    public static void main(String args[])throws IOException{
        if(args.length == 0){
            Random r = new Random();
            int n = 10000;
             
            for(int j=0; j<3; j++){
                RedBlackBST<Integer> bst = new RedBlackBST<Integer>();
                for(int i =0; i<n; i++){
                    bst.put(new Integer(r.nextInt()));
                }
                System.out.println("Total node:"+ bst.size() +"\n" + "red node:" + bst.countRed(bst.root) + "\n" + "red node percentage:" + bst.redPercentage());
                n=n*10;
            }
        }else{
            Scanner sca = new Scanner(new File(args[0]));
            RedBlackBST<Integer> bst = new RedBlackBST<Integer>();
            while(sca.hasNext()){
                int val = sca.nextInt();
                bst.put(new Integer(val));
            }
            sca.close();
            System.out.println("Total node:"+ bst.size() +"\n" + "red node:" + bst.countRed(bst.root) + "\n" + "red node percentage:" + bst.redPercentage());
        }
    }
}