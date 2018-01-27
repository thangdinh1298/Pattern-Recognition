import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    Point base;
    int count;
    int numSegment;
    LineSegment[] lineSegment;
    ArrayList<lineAndSlope> linesAndSlopes;
    Point[] aux;
    Comparator<Point> comparator;

    public FastCollinearPoints(Point[] points){
        count = 0;
        Point[] aux = new Point[points.length-1];
        numSegment= 0;
        linesAndSlopes = new ArrayList<>();
        for(int i = 0; i < points.length;i++){ //go through every points
            base = points[i];
            comparator = base.slopeOrder();
            for(int j =0; j < points.length;j++){ //copy every point except the base point into the auxiliary array
                if (points[j] != base) aux[count++] = points[j];
            }
            count = 0;
            Arrays.sort(aux,comparator);
            for(int j = 0; j + 2 < aux.length;){
                if(base.slopeTo(aux[j]) != base.slopeTo(aux[j+2])) j++;
                else{
                    System.out.println("bingo");
                    double slope = base.slopeTo(aux[j]);
                    int k = j + 2;
                    while(k+1 < aux.length && base.slopeTo(aux[k]) == base.slopeTo(aux[k+1])){
                        k ++;
                    }
                    linesAndSlopes.add(new lineAndSlope(new LineSegment(aux[j],aux[k]),slope));
                    if(k+1 < aux.length) j = k+1;
                    else if(k+1 >= aux.length){
                        break;
                    }
                }
            }
        }
    }     // finds all line segments containing 4 or more points

    private void removeDuplicate(ArrayList<lineAndSlope> linesAndSlopes){
        for(int i = 0; i < linesAndSlopes.size();){
            if(linesAndSlopes.get(i).equals(linesAndSlopes.get(i+1))){

            }
        }
    }

    private class lineAndSlope{
        LineSegment lineSegment;
        double slope;
        public lineAndSlope(LineSegment line, double slope){
            this.lineSegment = line;
            this.slope = slope;
        }

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
//        System.out.println(fast.numberOfSegments());
    }
}
