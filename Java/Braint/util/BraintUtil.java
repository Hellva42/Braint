package Braint.util;

public class BraintUtil {

	static public int OPENVIBE_CUT_OFF_NOISE_VALUE = 150;

	// OSC Emotiv EmoEngine
	static public String OSC_EMO_ENGINE = "/emoengine/";

	// OSC Message Adress OpenVibe
	static public String OSC_OPENVIBE_ALPHA = "/openvibe/alpha/";
	static public String OSC_OPENVIBE_BETA = "/openvibe/beta/";

	// OSC Channel Names OpenVibe

	static public String OSC_OPENVIBE_AF3 = "af3";
	static public String OSC_OPENVIBE_AF4 = "af4";
	static public String OSC_OPENVIBE_F3 = "f3";
	static public String OSC_OPENVIBE_F4 = "f4";
	static public String OSC_OPENVIBE_F7 = "f7";
	static public String OSC_OPENVIBE_F8 = "f8";
	static public String OSC_OPENVIBE_FC5 = "fc5";
	static public String OSC_OPENVIBE_FC6 = "fc6";
	static public String OSC_OPENVIBE_P7 = "p7";
	static public String OSC_OPENVIBE_P8 = "p8";
	static public String OSC_OPENVIBE_O1 = "o1";
	static public String OSC_OPENVIBE_O2 = "o2";

	// NOT USED TODO
	static public String OSC_OPENVIBE_T7 = "af3";
	static public String OSC_OPENVIBE_T8 = "af3";

	static public double getNormalizedValue(double value, double max, double min, double maxScaled, double minScaled) {

		double res = minScaled + (value - min) * (maxScaled - minScaled) / (max - min);

		res = checkNormalizationResult(maxScaled, minScaled, res);

		return res;
	}

	private static double checkNormalizationResult(double maxScaled, double minScaled, double res) {
		if (res > maxScaled)
			res = maxScaled;
		else if (res < minScaled)
			res = minScaled;
		return res;
	}

	
	// TODO KP UM EHRLICH ZU SEIN WIE DAS MIT DEM LOG GEHEN SOLL WENN MAN SCALEN WILL
	static public double getNormalizedValueLog(double value, double max, double min, double maxScaled,
			double minScaled) {

		double res = minScaled + (Math.log10(value) - Math.log10(min))
				* (maxScaled - minScaled) / (Math.log10(max) - Math.log10(min));
		
		
		res = checkNormalizationResult(maxScaled, minScaled, res);

		return res;
	}

	static public double getNormalizedValueWuzzel(double value, double max, double min, double maxScaled,
			double minScaled) {
		
		double res = Math.sqrt(minScaled) + (Math.sqrt(value) - Math.sqrt(min))
				* (Math.sqrt(maxScaled) - Math.sqrt(minScaled)) / (Math.sqrt(max) - Math.sqrt(min));
		
		res = checkNormalizationResult(Math.sqrt(maxScaled), Math.sqrt(minScaled), res);

		return res;

	}

	/**
	 * Chooses the colour in which the next strokes should be drawn depending on
	 * given value and colour mode,
	 * 
	 * @param value
	 *            value for which the colour should be picked
	 * @param mode
	 *            colour mode: 2 = yellow/red , 1 = green
	 * @return hex value for stroke function todo: change mode 2
	 */
	static public int decideRGBValue(float value, int mode) {

		if (mode == 1) {
			if (value < 0.1666f)
				return 0xffccece6;
			if (value < 0.3333f)
				return 0xff99d8c9;
			if (value < 0.5f)
				return 0xff66c2a4;
			if (value < 0.6666f)
				return 0xff41ae76;
			if (value < 0.8333f)
				return 0xff238b45;
			if (value < 1f)
				return 0xff006d2c;
			return 0xff00441b;

		} else if (mode == 2) {
			if (value < 0.1666f)
				return 0xfffdd49e;
			if (value < 0.3333f)
				return 0xfffdbb84;
			if (value < 0.5f)
				return 0xfffc8d59;
			if (value < 0.6666f)
				return 0xffef6548;
			if (value < 0.8333f)
				return 0xffd7301f;
			if (value < 1f)
				return 0xffb30000;
			return 0xff7f0000;

		}

		return 0;
	}

}
