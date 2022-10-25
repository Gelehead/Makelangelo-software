package com.marginallyclever.makelangelo.makeart.tools;

import com.marginallyclever.convenience.LineCollection;
import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.makelangelo.makeart.TurtleModifierAction;
import com.marginallyclever.makelangelo.turtle.Turtle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Performs <a href="https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm">Douglas-Peucker
 * line simplification</a>.
 * @author Dan Royer
 * @since 7.31.0
 */
public class SimplifyTurtleAction extends TurtleModifierAction {
	@Serial
	private static final long serialVersionUID = 7013596037448318526L;
	private static final Logger logger = LoggerFactory.getLogger(SimplifyTurtleAction.class);
	private static double distanceTolerance = 1.6;
	
	public SimplifyTurtleAction() {
		super(Translator.get("Simplify"));
	}
	
	@Override
	public Turtle run(Turtle turtle) {
		int os = turtle.history.size();
		logger.debug("begin @ {}", os);
		
		LineCollection originalLines = new LineCollection(turtle.getAsLineSegments());
		int originalCount = originalLines.size();
		logger.debug("  Converted to {} lines.", originalCount);

		LineCollection longLines = removeColinearSegments(originalLines);
		int longCount = longLines.size();
		int shortCount = originalCount - longCount;
		logger.debug("  - {} shorts = {} lines.", shortCount, longCount);

		Turtle t = new Turtle();
		t.addLineSegments(longLines);
		int ns = t.history.size();
		logger.debug("end @ {}", ns);
		
		return t;
	}

	/**
	 * Split the collection by color, then by travel moves to get contiguous blocks in a single color.
	 * simplify these blocks using Douglas-Peucker method. 
	 * @param originalLines the lines to simplify
	 * @return the simplified lines
	 */
	private LineCollection removeColinearSegments(LineCollection originalLines) {
		LineCollection result = new LineCollection();

		ArrayList<LineCollection> byColor = originalLines.splitByColor();
		for(LineCollection c : byColor ) {
			ArrayList<LineCollection> byTravel = c.splitByTravel();
			for(LineCollection t : byTravel ) {
				LineCollection after = t.simplify(distanceTolerance);
				result.addAll(after);
			}
		}
		
		return result;
	}

	/**
	 * Sets the distance tolerance for the simplification. All vertices in the
	 * simplified line will be within this distance of the original line.
	 *
	 * @param distanceTolerance the approximation tolerance to use
	 */
	public void setDistanceTolerance(double distanceTolerance) {
		SimplifyTurtleAction.distanceTolerance = distanceTolerance;
	}
}
