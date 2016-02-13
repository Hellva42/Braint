package Braint.main;

import Braint.drawMethods.agents.BraintAgentDraw;
import Braint.drawMethods.attractors.BraintAttractorsDraw;
import Braint.emoEngine.EmoEngineOSCHandler;
import Braint.openVibe.OpenVibeCalibration;
import Braint.openVibe.OpenVibeOscEEGPowerDataHandler;
import Braint.util.BraintUtil;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

public class BraintMainApplet extends PApplet {

	private OpenVibeOscEEGPowerDataHandler openVibeAlphaBetaPower;
	private EmoEngineOSCHandler emoEngine;

	private OpenVibeCalibration openVibeCalibration;

	private NetAddress myBroadcastLocation;
	private OscP5 oscP5;
	private boolean useAgents = true;
	private boolean useAttractors = false;
	private boolean doCalibration = true;

	private BraintAgentDraw braintAgent;
	private BraintAttractorsDraw braintAttractors;

	private static int THE_FRAMERATE = 128;

	private static int OSC_PORT = 12000;

	private static int CALIBRATION_TIME = 6000;

	@Override
	public void settings() {
		size(1920, 1080);
	}

	@Override
	public void setup() {

		frameRate(THE_FRAMERATE);

		// setup drawing parameters
		colorMode(RGB, 255, 255, 255, 100);
		smooth();
		noStroke();
		background(255);

		cursor(CROSS);

		// OSC
		oscP5 = new OscP5(this, OSC_PORT);
		openVibeAlphaBetaPower = new OpenVibeOscEEGPowerDataHandler();
		emoEngine = new EmoEngineOSCHandler();

		// drawing methods
		// TODO load dynamically based on button or some shit
		braintAgent = new BraintAgentDraw();
		braintAgent.setupDrawing(this);
		
		braintAttractors = new BraintAttractorsDraw();
		braintAttractors.setupDrawing(this);

		// calibration

		openVibeCalibration = new OpenVibeCalibration(openVibeAlphaBetaPower, CALIBRATION_TIME);
		openVibeCalibration.setupDrawing(this);

	}

	@Override
	public void draw() {

		if (doCalibration) {

			if (openVibeCalibration.calibrationFinisehd()) {
				doCalibration = false;
				println("calibration finisehd");
				println("alpha");
				println(openVibeCalibration.getAlphaStatisticsSummary().toString());
				println("beta");
				println(openVibeCalibration.getBetaStatisticsSummary().toString());
				println("alpha/beta");
				println(openVibeCalibration.getRatioAlphaBetaStatisticsSummary().toString());
			}

			if (!openVibeCalibration.isCalibrating()) {
				openVibeCalibration.startCalibrationInNextDraw();
			} else if (openVibeCalibration.isCalibrating()) {
				openVibeCalibration.updateAndDraw(this);
			}

		} else if (useAgents) {
			braintAgent.updateAndDraw(this);
			

		} else if (useAttractors) {
			braintAttractors.updateAndDraw(this);
		}

	}

	@Override
	public void keyPressed() {
		saveFrame();
	}

	public void oscEvent(OscMessage theOscMessage) {

		String msgAddr = theOscMessage.addrPattern();
		
		

		// TODO check the adress patterns if it works
		if (msgAddr.contains(BraintUtil.OSC_OPENVIBE_ALPHA) || msgAddr.contains(BraintUtil.OSC_OPENVIBE_BETA)) {
			openVibeAlphaBetaPower.handleOSCMessage(theOscMessage);


		} else if (msgAddr.contains(BraintUtil.OSC_EMO_ENGINE)) {
			
			emoEngine.handleOSCMessage(theOscMessage);
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

}
