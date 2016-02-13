package Braint;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

public class BraintMainApplet extends PApplet {

	private OpenVibeOscEEGPowerDataHandler openVibeAlphaBetaPower;

	private OpenVibeCalibration openVibeCalibration;

	private NetAddress myBroadcastLocation;
	private OscP5 oscP5;
	private boolean useAgents = false;
	private boolean useAttractors = true;
	private boolean doCalibration = false;

	private BraintAgentDraw braintAgent;
	private BraintAttractorsDraw braintAttractors;

	private static int THE_FRAMERATE = 128;

	private static int OSC_PORT = 12000;

	private static int CALIBRATION_TIME = 6000;

	public void settings() {
		size(1920, 1080);
	}

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

	public void keyPressed() {
		saveFrame();
	}

	public void oscEvent(OscMessage theOscMessage) {

		String msgAddr = theOscMessage.addrPattern();
		
		

		// TODO check the adress patterns if it works
		if (msgAddr.contains(BraintUtil.OSC_OPENVIBE_ALPHA) || msgAddr.contains(BraintUtil.OSC_OPENVIBE_BETA)) {
			openVibeAlphaBetaPower.handleOSCMessage(theOscMessage);


		}

		// TODO BraintCSharpAppHandler

		// if (theOscMessage.checkAddrPattern("/Affective") == true) {
		//
		// /*
		// * typetag sfdddddd [0] emotivTimeStamp, boredom, excitement,
		// * frustration, mediation, valence, excitementLongTerm [1] 40.09696
		// * [2] 0.5487005114555359 [3] 0.0 [4] 0.7110533118247986 [5]
		// * 0.3333112597465515 [6] 0.625 [7] 0.0
		// */
		// for (int i = 0; i < 6; i++) {
		//
		// }
		//
		// float excitement = (float) theOscMessage.get(3).doubleValue();
		// setValueToRGB(excitement);
		//
		// theOscMessage.print();
		//
		// } else if (theOscMessage.checkAddrPattern("/rawEEG") == true) {
		//
		// // string header = "COUNTER;INTERPOLATED;RAW_CQ;
		// // 3 - AF3;F7;F3; FC5;
		// // 7 - T7; P7; O1; O2;P8" +
		// // 12 - T8; FC6; F4;F8; AF4;
		// // GYROX; GYROY; TIMESTAMP; ES_TIMESTAMP" +
		// // "FUNC_ID; FUNC_VALUE; MARKER; SYNC_SIGNAL;";
		//
		// // get(3 - 16) for all electrodes
		// // theOscMessage.print();
		// // scale
		// float scale = (float) (theOscMessage.get(3).doubleValue() +
		// theOscMessage.get(16).doubleValue()
		// + theOscMessage.get(5).doubleValue() +
		// theOscMessage.get(14).doubleValue()
		// + theOscMessage.get(4).doubleValue() +
		// theOscMessage.get(15).doubleValue()
		// + theOscMessage.get(13).doubleValue() +
		// theOscMessage.get(6).doubleValue()); // AF3
		//
		// scale = scale / 8f;
		//
		// setOscNoiseScale(scale);
		//
		// // strength
		// float strength = (float) (theOscMessage.get(8).doubleValue() +
		// theOscMessage.get(11).doubleValue()
		// + theOscMessage.get(10).doubleValue() +
		// theOscMessage.get(9).doubleValue()); // O2
		//
		// strength /= 4f;
		//
		// setOscNoiseStrength(strength);
		// // rgb
		// double sum = 0;
		// for (int i = 0; i < 14; i++) {
		//
		// if (!(i == 7 || i == 12)) {
		// sum += theOscMessage.get(i + 3).doubleValue();
		// }
		// }
		//
		// float value = (float) (sum / 12.0); // FC5
		//
		// } else if (theOscMessage.checkAddrPattern("/Expressiv") == true) {
		//
		// }

	}

	/**
	 * main method, requiered open sketch window in fullscreen
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Braint.BraintMainApplet" });
	}

	public OpenVibeOscEEGPowerDataHandler getPowerDataHandler() {

		return openVibeAlphaBetaPower;
	}

	public OpenVibeCalibration getCalibrationData() {
		return openVibeCalibration;
	}

}
