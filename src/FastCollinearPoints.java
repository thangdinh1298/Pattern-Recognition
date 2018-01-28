import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    Point base;
    int count;
    int countSegment;
    int numSegment;
    LineSegment[] lineSegment;
    Point[] aux;
    Comparator<Point> comparator;
    maximalSegment[] maxSegs ;

    public FastCollinearPoints(Point[] points){
        count = 0;
        Point[] aux = new Point[points.length-1];
        numSegment= 0;
        countSegment = 0;
        maxSegs = new maximalSegment[1];
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
                    add(new maximalSegment(min,max));
//                    add(new LineSegment(min,max));
                    if(k+1 < aux.length) j = k+1;
                    else if(k+1 >= aux.length){
                        break;
                    }
                }
            }
        }
        for(int i = 0; i < maxSegs.length;i++){
            for(int j = i+1; j < maxSegs.length;j++){
                if(maxSegs[i] !=null && maxSegs[j] !=null){
                    if( maxSegs[i].equals(maxSegs[j])) maxSegs[j] = null;
                }
            }
        }
        for(int i = 0 ; i < maxSegs.length;i++){
            if(maxSegs[i] != null){
                add(maxSegs[i].returnLine());
            }
        }
    }     // finds all line segments containing 4 or more points

    private class maximalSegment{
        private Point min;
        private Point max;
        public maximalSegment(Point min, Point max){
            this.min = min;
            this.max = max;
        }
        public boolean equals(maximalSegment that){
            if (this.min == that.min && this.max == that.max)return true;
            return false;
        }
        public LineSegment returnLine(){
            return new LineSegment(this.min,this.max);
        }
    }
    private <T> void add(T variable){
        if(variable.getClass() == LineSegment.class){
            if(numSegment+1>lineSegment.length){
                LineSegment[] temp = new LineSegment[2*lineSegment.length];
                for(int i = 0; i < lineSegment.length; i++){
                    temp[i] = lineSegment[i];
                }
                lineSegment = temp;
            }
            lineSegment[numSegment++] = (LineSegment) variable;
        }
        else if(variable.getClass() == maximalSegment.class){
            if(countSegment+1>maxSegs.length){
                maximalSegment[] temp = new maximalSegment[maxSegs.length*2];
                for(int i = 0; i < maxSegs.length;i++){
                    temp[i] = maxSegs[i];
                }
                maxSegs = temp;
            }
            maxSegs[countSegment++] = (maximalSegment) variable;
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
        System.out.println(fast.numberOfSegments());
    }
}
