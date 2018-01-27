import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    Point base;
    int count;
    int numSegment;
    LineSegment[] lineSegment;
    Point[] aux;
    Comparator<Point> comparator;

    public FastCollinearPoints(Point[] points){
        count = 0;
        Point[] aux = new Point[points.length-1];
        numSegment= 0;
        lineSegment = new LineSegment[1];
        for(int i = 0; i < points.length;i++){ //go through every points
            base = points[i];
            comparator = base.slopeOrder();
            for(int j =0; j < points.length;j++){ //copy every point except the base point into the auxiliary array
                if (points[j] != base) aux[count++] = points[j];
            }
            count = 0;
            Arrays.sort(aux);
            Arrays.sort(aux,comparator);
            for(int j = 0; j + 2 < aux.length;){
                if(base.slopeTo(aux[j]) != base.slopeTo(aux[j+2])) j++;
                else{
                    int k = j + 2;
                    while(k+1 < aux.length && base.slopeTo(aux[k]) == base.slopeTo(aux[k+1])){
                        k ++;
                    }
                    Point min = aux[j];
                    Point max = aux[k];
                    if(base.compareTo(min)<0){
                        min = base;
                    }
                    if(base.compareTo(max)>0){
                        max = base;
                    }
                    addSegment(new LineSegment(min,max));
                    if(k+1 < aux.length) j = k+1;
                    else if(k+1 >= aux.length){
                        break;
                    }
                }
            }
        }
//        System.out.println("before nullifying");
//        for(int i = 0; i <lineSegment.length;i++){
//            System.out.print(lineSegment[i]+" ");
//        }
        for(int i = 0; i < lineSegment.length;i++){
            if(i+1<lineSegment.length){
                if(lineSegment[i].equals(lineSegment[i+1])) lineSegment[i] = null;
            }
        }
        System.out.println(lineSegment[0]+": "+lineSegment[1]);
//        System.out.println("after nullifying");
//        for(int i = 0; i <lineSegment.length;i++){
//            System.out.print(lineSegment[i]+" ");
//        }
        LineSegment temp[] = new LineSegment[lineSegment.length];
        numSegment = 0;
        for(int i = 0; i <lineSegment.length;i++){
            if(lineSegment[i] !=null){
                temp[numSegment++] = lineSegment[i];
            }
        }
        lineSegment = temp;
//        System.out.println("temp");
//        for(int i = 0; i <temp.length;i++){
//            System.out.print(temp[i]+" ");
//        }
    }     // finds all line segments containing 4 or more points

    private void addSegment(LineSegment segment){
        if(numSegment+1>lineSegment.length){
            LineSegment[] temp = new LineSegment[2*lineSegment.length];
            for(int i = 0; i < lineSegment.length; i++){
                temp[i] = lineSegment[i];
            }
            lineSegment = temp;
        }
        lineSegment[numSegment++] = segment;
    }


    public           int numberOfSegments()  {
        return numSegment;
    }      // the number of line segments
    public LineSegment[] segments(){
        return lineSegment;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n= in.readInt();
        Point[] points = new Point[n];
        for(int i = 0; i < n; i ++){
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x,y);
        }
        FastCollinearPoints fast = new FastCollinearPoints(points);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < points.length; i ++) {
            points[i].draw();
        }
        StdDraw.show();
        for(int i = 0; i < fast.numberOfSegments();i++){
//            StdOut.println(fast.lineSegment[i]);
            fast.lineSegment[i].draw();
        }
        StdDraw.show();
        System.out.println(fast.numberOfSegments());
    }
}
