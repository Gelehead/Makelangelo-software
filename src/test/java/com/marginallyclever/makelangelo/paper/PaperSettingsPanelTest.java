package com.marginallyclever.makelangelo.paper;

import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.util.PreferencesHelper;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.swing.*;

/**
 * {@code PaperSettings} tests
 * @author Dan Royer
 *
 */

@DisabledIfEnvironmentVariable(named = "CI", matches = "true", disabledReason = "headless environment")
public class PaperSettingsPanelTest {
	private FrameFixture window;

	@BeforeEach
	public void setUp() {
		Robot robot = BasicRobot.robotWithNewAwtHierarchy();

		PreferencesHelper.start();
		Translator.start();

		PaperSettingsPanel panel = new PaperSettingsPanel(new Paper());
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.pack();
		window = new FrameFixture(robot, frame);
		window.show(); // shows the frame to test
	}

	@AfterEach
	public void tearDown() {
		if(window!=null) window.cleanUp();
	}
}
