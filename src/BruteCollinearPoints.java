import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class BruteCollinearPoints {

    private int count = 0;
    private LineSegment[] lineSegment;
    private int numSegment = 0;
    private int[] indexArray;
    private Point[] points;
    private Comparator<Point> comparator;
    public BruteCollinearPoints(Point[] points){
        lineSegment = new LineSegment[10];
        this.points = points;
        indexArray = new int[4];
        for(int i = 0; i <4; i++){
            indexArray[i] = i;
        }
        if(isCollinear()){
            minAndMax();
        }
        while(!isFinal()){
            nextPermutation();
            if(isCollinear()){
                minAndMax();
            }
        }
    }    // finds all line segments containing 4 points
    private void minAndMax(){
        Point min = this.points[indexArray[0]];
        Point max = this.points[indexArray[0]];
        for(int i = 1 ; i < 4; i ++){
            if(this.points[indexArray[i]].compareTo(min) < 0){
                min = this.points[indexArray[i]];
            }
            else if(this.points[indexArray[i]].compareTo(max) > 0){
                max = this.points[indexArray[i]];
            }
        }
        LineSegment thisSegment = new LineSegment(min ,max);
        addSegment(thisSegment);
        count++;
    }
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
    private void nextPermutation(){
        int i = 3;
        while(indexArray[i] == this.points.length-4+i) i--;
        indexArray[i]++;
        for(int j = i + 1; j < 4; j ++){
            indexArray[j] = indexArray[j-1]+1;
        }
    }
    private boolean isFinal(){
        for(int i = 0; i < 4; i ++){
            if(indexArray[i] != this.points.length - 4 + i) return false;
        }
        return true;
    }
    private boolean isCollinear(){
        Point base = points[indexArray[0]];
        comparator = base.slopeOrder();
        return (comparator.compare(points[indexArray[1]],points[indexArray[2]]) == 0 &&
                comparator.compare(points[indexArray[2]],points[indexArray[3]]) == 0);
}
    public int numberOfSegments(){
        return numSegment;
    }        // the number of line segments
    public LineSegment[] segments(){
        return lineSegment;
    }                // the line segments

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints algo = new BruteCollinearPoints(points);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < points.length; i ++) {
            points[i].draw();
        }
        StdDraw.show();
        for(int i = 0; i < algo.numberOfSegments();i++){
            StdOut.println(algo.lineSegment[i]);
            algo.lineSegment[i].draw();
    }
        StdDraw.show();
        System.out.println(algo.count);
}
}