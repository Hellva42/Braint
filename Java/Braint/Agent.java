package Braint;

import processing.core.PVector;

/**
 * Created by Saitama on 19.01.2016. TestTest
 *
 * Variable Werte: stepSize
 */
public class Agent implements IProcessingDrawable {

	public static void main() {
	}

	public PVector p, pOld;
	public float stepSize, angle;
	public boolean isOutside = false;

	private BraintAgentDraw braintAgent;

	public Agent(BraintAgentDraw bad) {

		braintAgent = bad;
		// stepSize = 0.1f;
	}

	@Override
	public void updateAndDraw(BraintMainApplet applet) {
		
		if(braintAgent == null)
			System.out.println("BRAINT DRAW IS NULL");

		
		if(p == null)
			System.out.println("p DRAW IS NULL");
		angle = applet.noise(p.x / braintAgent.getNoiseScale(), p.y / braintAgent.getNoiseScale())
				* braintAgent.getNoiseStrength();
		p.x += applet.cos(angle) * stepSize;
		p.y += applet.sin(angle) * stepSize;

		if (p.x < -10)
			isOutside = true;
		else if (p.x > applet.width + 10)
			isOutside = true;
		else if (p.y < -10)
			isOutside = true;
		else if (p.y > applet.height + 10)
			isOutside = true;

		if (isOutside) {

			p.x = applet.random(applet.width);
			p.y = applet.random(applet.height);
			pOld.set(p);

		}

		float strokeWidth = 1;
		applet.strokeWeight(strokeWidth * braintAgent.getStrokeWidthScale());
		applet.stroke(braintAgent.getRGB());
		applet.line(pOld.x, pOld.y, p.x, p.y);
		pOld.set(p);
		isOutside = false;

	}

	@Override
	public void setupDrawing(BraintMainApplet applet) {
		p = new PVector(applet.random(applet.width), applet.random(applet.height));
		pOld = new PVector(p.x, p.y);
		stepSize = applet.random(1, 5);

	}

}
