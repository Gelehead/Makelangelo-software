package com.marginallyclever.convenience.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.vecmath.Point2d;

public class MathHelperTest {

    @Test
    public void testEquals() {
        Point2d a0 = new Point2d();
        Point2d a1 = new Point2d();
        Point2d b0 = new Point2d();
        Point2d b1 = new Point2d();
        double epsilon = 1e-9;

        double p1x = Math.random() * 100;
        double p1y = Math.random() * 50;
        double p2x = Math.random() * 110;
        double p2y = Math.random() * 55;

        a0.set(p1x, p1y);
        a1.set(p2x, p2y);
        b1.set(p2x, p2y);
        b0.set(p1x, p1y);

        boolean l = MathHelper.equals(a0, a1, b0, b1, epsilon);
        b1.set(p1x, p1y);
        b0.set(p2x, p2y);
        boolean m = MathHelper.equals(a0, a1, b0, b1, epsilon);
        assert (l && m);
    }

    @Test
    public void testBetween() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, Math.random());
            assert (MathHelper.between(a, b, c, epsilon));
        }
    }

    @Test
    public void testNotBetween() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, 1.0 + epsilon + Math.random());
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, -epsilon - Math.random());
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }
    }

    @Test
    public void testOffLine() {
        Point2d a = new Point2d();
        Point2d b = new Point2d();
        Point2d ortho = new Point2d();
        Point2d c;
        double epsilon = 1e-9;

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x += ortho.y;
            c.y += ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, 1.0 + epsilon + Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x += ortho.y;
            c.y += ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }

        for (int i = 0; i < 50; ++i) {
            a.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            b.set(Math.random() * 500 - 250, Math.random() * 500 - 250);
            c = MathHelper.lerp(a, b, -epsilon - Math.random());
            ortho.set(b);
            ortho.sub(a);
            c.x += ortho.y;
            c.y += ortho.x;
            Assertions.assertFalse(MathHelper.between(a, b, c, epsilon));
        }
    }
}
