package com.marginallyclever.makelangelo.makeArt;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.marginallyclever.convenience.ColorRGB;
import com.marginallyclever.convenience.LineSegment2D;
import com.marginallyclever.convenience.Point2D;
import com.marginallyclever.makelangelo.turtle.Turtle;

public class InfillTurtle {
	public static final double MINIMUM_PEN_DIAMETER=0.1; 
	private double penDiameter = 0.8;  // TODO adjust me before running infill
	private double minimumJumpSize = 0.4;
	
	public InfillTurtle() {}
	
	public Turtle run(Turtle input) throws Exception {
		//confirmTurtleIsClosedLoop(input);
		
		ArrayList<LineSegment2D> segments = infillFromTurtle(input);
		
		Turtle t = new Turtle();
		t.addLineSegments(segments,minimumJumpSize);
		
		return t;
	}

	private void confirmTurtleIsClosedLoop(Turtle input) throws Exception {
		throw new Exception("I cannot confirm this Turtle path is a closed loop.");
	}

	private ArrayList<LineSegment2D> infillFromTurtle(Turtle input) throws Exception {
		// make sure line segments don't start on another line, leading to an odd number of intersections.
		Rectangle2D.Double bounds = addPaddingToBounds(input.getBounds(),2.0);
		
		ArrayList<LineSegment2D> results = new ArrayList<LineSegment2D>();
		
		// do this once here instead of once per line.
		ArrayList<LineSegment2D> convertedPath = input.getAsLineSegments();
		// working variable
		LineSegment2D line = new LineSegment2D(new Point2D(),new Point2D(),input.getColor());
		
		for(double y=bounds.getMinY();y<bounds.getMaxY();y+=penDiameter) {
			line.a.set(bounds.getMinX(),y);
			line.b.set(bounds.getMaxX(),y);
			results.addAll(trimLineToPath(line,convertedPath));
		}
		
		return results;
	}
	
	/**
	 * Add padding to a {@code Rectangle2D.Double} bounding box.
	 * @param bounds
	 * @return the larger bounds
	 */
	private Rectangle2D.Double addPaddingToBounds(Rectangle2D.Double bounds,double percent) {
		Rectangle2D.Double d2 = new Rectangle2D.Double();
		d2.x = bounds.x - bounds.width*percent;
		d2.y = bounds.y - bounds.height*percent;
		d2.height = bounds.height*(1.0+percent*2.0);
		d2.width = bounds.width*(1.0+percent*2.0);
		return d2;
	}

	/**
	 * Trim a {@code LineSegment2D} against a {@code Turtle} path and return a list of remaining line segments.
	 * <p>If the polygon is convex, there will be two intersection points. These two points are the end points of the trimmed version of the line.</p> 
	 * <p>If the polygon is not convex, there will be an even number of intersection points ≥2. Sort these intersection points (by increasing x value, for example). Then, taken in pairs, they give you the end points of the segments of the line that lie inside the polygon.</p>
	 * 
	 * @param line A {@code LineSegment2D} to compare against the {@code Turtle}
	 * @param input The {@code Turtle}, guaranteed closed loop
	 * @return a list of remaining {@code LineSegment2D}. 
	 */
	private ArrayList<LineSegment2D> trimLineToPath(LineSegment2D line,ArrayList<LineSegment2D> convertedPath) throws Exception {
		ArrayList<Point2D> intersections = new ArrayList<Point2D>();
		
		for(LineSegment2D s : convertedPath) {
			intersections.add(getIntersection(line,s));
		}
		
		int size = intersections.size();
		if(size==0) return null;
		if(size%2!=0) {
			throw new Exception("odd number of intersections");
		}
		
		ArrayList<LineSegment2D> results = new ArrayList<LineSegment2D>();
		if(size==2) {
			results.add(new LineSegment2D(intersections.get(0),intersections.get(1),line.c));
		} else if(size>2) {
			results.addAll(sortIntersectionsIntoSegments(intersections,line.c));
		}
		 
		return results;
	}

	/**
	 * @param intersections A list of intersections.  guaranteed to be 2 or more even number of intersections.
	 * @param color Color to assign to line 
	 * @return return Intersections sorted by ascending x value.  If x values match, sort by ascending y value.
	 */
	private ArrayList<LineSegment2D> sortIntersectionsIntoSegments(ArrayList<Point2D> intersections,ColorRGB color) {
		if(Math.abs(intersections.get(0).x-intersections.get(1).x)<1e-6) {
			// x values equal
			Collections.sort(intersections,new ComparePointsByY());
		} else {
			// x values not equal
			Collections.sort(intersections,new ComparePointsByX());
		}
		
		ArrayList<LineSegment2D> results = new ArrayList<LineSegment2D>();
		int i=0;
		while(i<intersections.size()) {
			results.add(new LineSegment2D(
				intersections.get(i+0),
				intersections.get(i+1),
				color));
			i+=2;
		}
		
		return results;
	}
	
	class ComparePointsByY implements Comparator<Point2D> {
		@Override
		public int compare(Point2D o1, Point2D o2) {
			return Double.compare(o1.y,o2.y);
		}
	}
	
	class ComparePointsByX implements Comparator<Point2D> {
		@Override
		public int compare(Point2D o1, Point2D o2) {
			return Double.compare(o1.x,o2.x);
		}
	}
	

	/**
	 * It is based on an algorithm in Andre LeMothe's "Tricks of the Windows Game Programming Gurus".
	 * See https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
	 * TODO move this to com.marginallyclever.convenience.LineHelper?
	 * @param alpha
	 * @param beta
	 * @return intersection {@code Point2D} or null
	 */
	private Point2D getIntersection(LineSegment2D alpha, LineSegment2D beta) {
	    double s1_x = alpha.b.x - alpha.a.x;
	    double s1_y = alpha.b.y - alpha.a.y;
	    double s2_x = beta.b.x - beta.a.x;
	    double s2_y = beta.b.y - beta.a.y;

	    double s = (-s1_y * (alpha.a.x - beta.a.x) + s1_x * (alpha.a.y - beta.a.y)) / (-s2_x * s1_y + s1_x * s2_y);
	    double t = ( s2_x * (alpha.a.y - beta.a.y) - s2_y * (alpha.a.x - beta.a.x)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
	        // hit!
	    	Point2D p = new Point2D(
	            alpha.a.x + (t * s1_x),
	            alpha.a.y + (t * s1_y));
	        return p;
	    }
	    // no hit
		return null;
	}

	public double getPenDiameter() {
		return penDiameter;
	}

	public void setPenDiameter(double penDiameter) {
		this.penDiameter = Math.max(penDiameter, MINIMUM_PEN_DIAMETER);
	}
}
