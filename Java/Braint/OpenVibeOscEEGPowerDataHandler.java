package Braint;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import oscP5.OscMessage;

public class OpenVibeOscEEGPowerDataHandler implements IOSCMessageHandler {
	
	
	// samples
	public OpenVibeChannelsSampleContainer alpha;
	public OpenVibeChannelsSampleContainer beta;
	
	
	// overall statistics for received values
	private SummaryStatistics alphaStatistics, betaStatistics, alphaBetaRatioStatistics;
	
	
	private static String[] alphaChannelList = 
			new String[] {BraintUtil.OSC_OPENVIBE_O1, BraintUtil.OSC_OPENVIBE_O2, BraintUtil.OSC_OPENVIBE_P7, BraintUtil.OSC_OPENVIBE_P8 };
	
	private static String[] betaChannelList = 
			new String[] {BraintUtil.OSC_OPENVIBE_AF3, BraintUtil.OSC_OPENVIBE_AF4, BraintUtil.OSC_OPENVIBE_F3, BraintUtil.OSC_OPENVIBE_F4, 
					BraintUtil.OSC_OPENVIBE_F7, BraintUtil.OSC_OPENVIBE_F8, BraintUtil.OSC_OPENVIBE_FC5, BraintUtil.OSC_OPENVIBE_FC6    };
	
	public OpenVibeOscEEGPowerDataHandler() {
		 alpha = new OpenVibeChannelsSampleContainer(alphaChannelList);
		 
		 
		 
		 beta = new OpenVibeChannelsSampleContainer(betaChannelList);
		 
		alphaBetaRatioStatistics = new SummaryStatistics();
		alphaStatistics = new SummaryStatistics();
		betaStatistics = new SummaryStatistics();

	}
	
	
	
	// TODO TEST OSC MESSAGE ADDRESS HOW IT LOOKS LIKE
	@Override
	public void handleOSCMessage(OscMessage msg) {

		try {
			if(msg.get(0).floatValue() > BraintUtil.OPENVIBE_CUT_OFF_NOISE_VALUE)
				return;
			
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		
		String msgAddr = msg.addrPattern();
		String [] tmp = msgAddr.split("/");
		int l = tmp.length;
		
		String name = tmp[l-1];
				float value = msg.get(0).floatValue();
		
		if(msgAddr.contains(BraintUtil.OSC_OPENVIBE_ALPHA)) {
			alpha.addChannelValue(name, value );
			
//			System.out.println(alpha);
			
		} else if(msgAddr.contains(BraintUtil.OSC_OPENVIBE_BETA)) {
			beta.addChannelValue(name, value );
			//System.out.println(beta);
		}
		
		
		
		
		
//		// handle alpha
//		if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_ALPHA + BraintUtil.OSC_OPENVIBE_O1)) {			
//			
//			alpha.o1 = msg.get(0).floatValue();
//		}
//
//		else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_ALPHA + BraintUtil.OSC_OPENVIBE_O2)) {
//
//			alpha.o2 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_ALPHA + BraintUtil.OSC_OPENVIBE_P7)) {
//
//			alpha.p7 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_ALPHA + BraintUtil.OSC_OPENVIBE_P8)) {
//
//			alpha.p8 = msg.get(0).floatValue();
//		}
//		// handle beta
//		else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_F3)) {
//
//			beta.f3 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_F4)) {
//
//			beta.f4 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_AF3)) {
//
//			beta.af3 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_AF4)) {
//
//			beta.af4 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_F7)) {
//
//			beta.f7 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_F8)) {
//
//			beta.f8 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_FC5)) {
//
//			beta.fc5 = msg.get(0).floatValue();
//		} else if (msg.checkAddrPattern(BraintUtil.OSC_OPENVIBE_BETA + BraintUtil.OSC_OPENVIBE_FC6)) {
//
//			beta.fc6 = msg.get(0).floatValue();
//		}

	}
	
	
}
