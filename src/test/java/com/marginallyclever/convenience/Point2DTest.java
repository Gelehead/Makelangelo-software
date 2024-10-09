package com.marginallyclever.convenience;

import org.junit.jupiter.api.Assertions;

import org.junit.Test;

class Point2DTest{
    private void assertPoint2DEquals(double a, double b){
        Point2D p = new Point2D();
        p.set(a, b);

        Assertions.assertEquals(p.x, a);
        Assertions.assertEquals(p.y, b);
    }

    private void assertDistanceSquaredEquals(double x1, double y1, double x2, double y2, double expected){
        Point2D p1 = new Point2D(x1, y1);
        Point2D p2 = new Point2D(x2, y2);

        Assertions.assertEquals(p1.distanceSquared(p1), 0.0);
        Assertions.assertEquals(p1.distanceSquared(p2), expected);
        Assertions.assertEquals(p2.distanceSquared(p1), expected);
    }

    private void assertScaleEquals(double x, double y, double scale){
        Point2D p = new Point2D(x, y);
        Point2D pExpected = new Point2D(scale * x, scale * y);

        p.scale(scale);

        Assertions.assertEquals(p, pExpected);
    }

    private void assertDistanceEquals(double x1, double y1, double x2, double y2, double expected){
        Point2D p1 = new Point2D(x1, y1);
        Point2D p2 = new Point2D(x2, y2);

        Assertions.assertEquals(p1.distance(p1), 0.0);
        Assertions.assertEquals(p1.distance(p2), expected);
        Assertions.assertEquals(p2.distance(p1), expected);
    }

    private void assertLengthSquaredEquals(double x, double y, double expected){
        Point2D p = new Point2D(x, y);
        double lengthSquared = p.lengthSquared();
        Assertions.assertEquals(lengthSquared, expected);
    }

    private void assertLengthEquals(double x,  double y, double expected){
        Point2D p = new Point2D(x, y);
        double length = p.length();
        Assertions.assertEquals(length, expected);
    }

    private void assertNormalizeEquals(double x, double y, double expectedX, double expectedY){
        Point2D p = new Point2D(x, y);
        p.normalize();
        Assertions.assertEquals(p.x, expectedX);
        Assertions.assertEquals(p.y, expectedY);
    }

    private void assertAddEquals(double x, double y, Point2D p2, double expectedX, double expectedY){
        Point2D p = new Point2D(x, y);
        p.add(p2);
        Assertions.assertEquals(p.x, expectedX);
        Assertions.assertEquals(p.y, expectedY);
    }

    @Test
    public void testAssertPoint2DEquals(){
        assertPoint2DEquals(1, 1);
        assertPoint2DEquals(-3.5, -2.5);
        assertPoint2DEquals(0.0, 0.0);
        assertPoint2DEquals(1e6, -1e6);
        assertPoint2DEquals(1e-6, -1e-6);
    }

    @Test
    public void testAssertDistanceSquaredEquals(){
        assertDistanceSquaredEquals(1.0, 1.0, 1.0, 1.0, 0.0);
        assertDistanceSquaredEquals(0.0, 0.0, 3.0, 0.0, 9.0);
        assertDistanceSquaredEquals(0.0, 0.0, 0.0, 4.0, 16.0);
        assertDistanceSquaredEquals(1.0, 2.0, 4.0, 6.0, 25.0);
        assertDistanceSquaredEquals(-2.0, -3.0, -4.0, -5.0, 8.0);
        assertDistanceSquaredEquals(-1.0, 1.0, 2.0, -3.0, 25.0);
    }

    @Test
    public void testAssertDistanceEquals(){
        assertDistanceEquals(1.0, 1.0, 1.0, 1.0, 0.0);
        assertDistanceEquals(0.0, 0.0, 3.0, 0.0, 3.0);
        assertDistanceEquals(0.0, 0.0, 0.0, 4.0, 4.0);
        assertDistanceEquals(1.0, 2.0, 4.0, 6.0, 5.0);
        assertDistanceEquals(-1.0, 1.0, 2.0, -3.0, 5.0);
    }

    @Test
    void testAssertScaleEquals() {
        assertScaleEquals(3.0, 4.0, 1.0); 
        assertScaleEquals(3.0, 4.0, 0.0); 
        assertScaleEquals(2.0, -5.0, 2.0);
        assertScaleEquals(-1.0, 3.0, -3.0);
        assertScaleEquals(10.0, 15.0, 0.5); 
        assertScaleEquals(0.0, 0.0, 10.0);
        assertScaleEquals(1.0, 1.0, 1e6);
        assertScaleEquals(1.0, 1.0, 1e-6);
    }

    @Test
    void testAssertLengthSquaredEquals() {
        assertLengthSquaredEquals(0.0, 0.0, 0.0);
        assertLengthSquaredEquals(3.0, 0.0, 9.0); 
        assertLengthSquaredEquals(0.0, 4.0, 16.0);
        assertLengthSquaredEquals(1.0, 2.0, 5.0);
        assertLengthSquaredEquals(-3.0, -4.0, 25.0); 
        assertLengthSquaredEquals(1e6, 1e6, 2e12);
    }

    @Test
    void testAssertLengthEquals() {
        assertLengthEquals(0.0, 0.0, 0.0);
        assertLengthEquals(3.0, 0.0, 3.0);
        assertLengthEquals(0.0, 4.0, 4.0);
        assertLengthEquals(1.0, 2.0, Math.sqrt(5.0)); 
        assertLengthEquals(-3.0, -4.0, 5.0); 
        assertLengthEquals(1e6, 1e6, Math.sqrt(2) * 1e6); 
    }

    @Test
    void testNormalizeFunction() {
        assertNormalizeEquals(0.0, 0.0, 0.0, 0.0);
        assertNormalizeEquals(3.0, 0.0, 1.0, 0.0);
        assertNormalizeEquals(3.0, -4.0, 0.6, -0.8); 
        assertNormalizeEquals(1e-6, 1e-6, Math.sqrt(0.5), Math.sqrt(0.5)); 
    }
    
    @Test
    void testAddEquals() {
        assertAddEquals(1.0, 1.0, new Point2D(0.0, 0.0), 1.0, 1.0);
        assertAddEquals(-1.0, -2.0, new Point2D(-3.0, -4.0), -4.0, -6.0); 
        assertAddEquals(1.0, -1.0, new Point2D(-2.0, 3.0), -1.0, 2.0); 
        assertAddEquals(1e6, 2e6, new Point2D(1e6, 3e6), 2e6, 5e6); 
        assertAddEquals(2.0, 2.0, new Point2D(-5.0, -5.0), -3.0, -3.0);
    }


}