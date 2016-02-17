package Braint.main;

import Braint.drawMethods.agents.BraintAgentDraw;
import Braint.drawMethods.attractors.BraintAttractorsDraw;
import Braint.emoEngine.EmoEngineOSCHandler;
import Braint.gui.InDrawingOverlay;
import Braint.gui.MainGui;
import Braint.openVibe.OpenVibeCalibration;
import Braint.openVibe.OpenVibeOscEEGPowerDataHandler;
import Braint.settings.BigSettings;
import Braint.util.BraintUtil;
import Braint.util.XMLUtil;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

public class BraintMainApplet extends PApplet {

	private OpenVibeOscEEGPowerDataHandler openVibeAlphaBetaPower;
	private EmoEngineOSCHandler emoEngine;

	private OpenVibeCalibration openVibeCalibration;

	private boolean useAttractors = false;

	private boolean doCalibration = false;

	public void setDoCalibration(boolean doCalibration) {
		this.doCalibration = doCalibration;
	}

	private boolean guiMode = true;
	private boolean drawing = true;
	private boolean useEmoEngine = false;

	private BraintAgentDraw braintAgent;
	private BraintAttractorsDraw braintAttractors;

	private MainGui mainGUI;
	private InDrawingOverlay testOverlay;
	private OscP5 oscP5;

	private static int THE_FRAMERATE = 128;

	private static int OSC_PORT = 12000;

	@Override
	public void settings() {
		size(1920, 1080);
		// also lags like shit with P2D
	}

	public void loadSettings() {
		// TODO
	}

	@Override
	public void setup() {

		frameRate(THE_FRAMERATE);

		// setup drawing parameters
		colorMode(RGB, 255, 255, 255, 100);
		smooth();
		noStroke();
		background(BigSettings.instance().backgroundColor);

		cursor(CROSS);

		// OSC
		oscP5 = new OscP5(this, BigSettings.instance().OSC_PORT);
		openVibeAlphaBetaPower = new OpenVibeOscEEGPowerDataHandler();
		emoEngine = new EmoEngineOSCHandler();

		// TODO load default xml settings

		// drawing methods
		// TODO load dynamically based on button or some shit
		braintAgent = new BraintAgentDraw();
		braintAgent.setupDrawing(this);

		// todo set useOfThresholds
		// braintAgent.setIntensityThresholds(true);

		braintAttractors = new BraintAttractorsDraw();
		braintAttractors.setupDrawing(this);

		// calibration

		openVibeCalibration = new OpenVibeCalibration(openVibeAlphaBetaPower);
		openVibeCalibration.setupDrawing(this);

		mainGUI = new MainGui(openVibeCalibration, this);
		mainGUI.setupDrawing(this);
		// mainGUI.setOpenVibeCalibration(openVibeCalibration);

		testOverlay = new InDrawingOverlay();
		testOverlay.setupDrawing(this);

		// TODO load default settings and set them in the gui?!
		// or use gui save properties for default?

	}

	@Override
	public void draw() {

		if (guiMode) {
			if (doCalibration && BigSettings.instance().useOpenVibe) {

				if (openVibeCalibration.calibrationFinisehd()) {
					System.out.println("finished");
					doCalibration = false;
					// println("calibration finisehd");
					// println("alpha");
					// println(openVibeCalibration.getAlphaStatisticsSummary().toString());
					// println("beta");
					// println(openVibeCalibration.getBetaStatisticsSummary().toString());
					// println("alpha/beta");
					// println(openVibeCalibration.getRatioAlphaBetaStatisticsSummary().toString());

					// update settings object
					BigSettings.instance().alphaStatistics = openVibeCalibration.getAlphaStatisticsSummary();
					BigSettings.instance().betaStatistics = openVibeCalibration.getBetaStatisticsSummary();
					BigSettings.instance().alphaBetaRatioStatistics = openVibeCalibration
							.getRatioAlphaBetaStatisticsSummary();

					// TODO inform GUI

				} else if (!openVibeCalibration.isCalibrating()) {
					System.out.println("finished2");
					openVibeCalibration.startCalibrationInNextDraw();
				} else if (openVibeCalibration.isCalibrating()) {
					openVibeCalibration.updateAndDraw(this);

				}

			}

			if (mainGUI == null) {
				mainGUI = new MainGui(openVibeCalibration, this);
				mainGUI.setupDrawing(this);
			} else
				mainGUI.updateAndDraw(this);

		} else if (drawing) {
			mainGUI.hide();

			if (BigSettings.instance().useAgents) {

				braintAgent.useOpenVibe(BigSettings.instance().useOpenVibe);
				braintAgent.useEmoengine(useEmoEngine);

				braintAgent.updateAndDraw(this);

				// pushMatrix();
				// fill(255);
				// rect(width - 200, 0 + 50, 150, 50 );
				// rect(width - 200, 0 + 110, 150, 50 );
				// text(openVibeAlphaBetaPower.alpha.getMeanAllChannels(), width
				// - 200, 0 + 50);
				// text(openVibeAlphaBetaPower.beta.getMeanAllChannels(), width
				// - 200, 0 + 110);
				// popMatrix();

				if (BigSettings.instance().useOpenVibe) {
					testOverlay.currentAlpha = "AlphaPower" + openVibeAlphaBetaPower.alpha.getMeanAllChannels();
					testOverlay.currentBeta = "BetaPower" + openVibeAlphaBetaPower.beta.getMeanAllChannels();

					testOverlay.updateAndDraw(this);
				}

			} else if (useAttractors) {
				braintAttractors.updateAndDraw(this);
			}
		}
	}

	@Override
	public void keyPressed() {
		if (key == 'r' || key == 'R') {
			if (drawing) {
				clear();
				background(BigSettings.instance().backgroundColor);
				// TODO reset agents???
			}
		} else if (key == ' ') {
			System.out.println("ayy");
			saveFrame("./snaps/snapshot_######.png");
		} else if (keyCode == ESC || key == 'q') {
			guiMode = true;
			drawing = false;
			mainGUI.show();
		} else if (key == 'x') {
			XMLUtil.serializeObject(BigSettings.instance(), "testSettings");
		} else if (key == 'c') {
			// System.out.println(BigSettings.instance().CALIBRATION_TIME);
			BigSettings.instance().setSettingsFromXML(XMLUtil.deserialzeObject(new BigSettings(), "testSettings"));
			mainGUI.reset();

			mainGUI = new MainGui(openVibeCalibration, this);
			mainGUI.setupDrawing(this);
			mainGUI.setOpenVibeCalibration(openVibeCalibration);
			//
			// System.out.println(BigSettings.instance().CALIBRATION_TIME);

			// XMLUtil.serializeObject(BigSettings.instance(), "testSettings");
		} else if (key == 'e') {

			mainGUI.hide();
			clear();
			background(BigSettings.instance().backgroundColor);
//			braintAgent = new BraintAgentDraw();
//			braintAgent.setupDrawing(this);
			guiMode = false;
			drawing = true;
		}

		// TODO
	}

	public void oscEvent(OscMessage theOscMessage) {

		String msgAddr = theOscMessage.addrPattern();

		// TODO check the adress patterns if it works
		if (msgAddr.contains(BraintUtil.OSC_OPENVIBE_ALPHA) || msgAddr.contains(BraintUtil.OSC_OPENVIBE_BETA)) {

			openVibeAlphaBetaPower.handleOSCMessage(theOscMessage);

		} else if (msgAddr.contains(BraintUtil.OSC_EMO_ENGINE)) {

			if (emoEngine == null)
				return;

			try {

				emoEngine.handleOSCMessage(theOscMessage);
			} catch (Exception e) {

				e.printStackTrace();
				theOscMessage.print();
				System.out.println(emoEngine == null);

			}

		}

	}

	/**
	 * main method, requiered open sketch window in fullscreen
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Braint.main.BraintMainApplet" });
	}

	public OpenVibeOscEEGPowerDataHandler getPowerDataHandler() {

		return openVibeAlphaBetaPower;
	}

	public OpenVibeCalibration getCalibrationData() {
		return openVibeCalibration;
	}

	public EmoEngineOSCHandler getEmoEngineHandler() {
		return emoEngine;
	}

}
