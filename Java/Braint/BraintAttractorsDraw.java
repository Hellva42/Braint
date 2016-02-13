package Braint;

import GenerativeDesignSources.Attractor;
import GenerativeDesignSources.Node;
import processing.core.PImage;

public class BraintAttractorsDraw implements IProcessingDrawable {

	BraintNode[] pixelNodes;
	Node[] theNodes;

	// CLEAN UP
	PImage img;
	private boolean initAttractors = true;
	Attractor attractor;

	@Override
	public void updateAndDraw(BraintMainApplet applet) {
		if (!initAttractors) {
			for (BraintNode t : pixelNodes) {
				attractor.attract(t.getNode());
				t.updateAndDraw(applet);
			}
		}
	}

	@Override
	public void setupDrawing(BraintMainApplet applet) {

		// ATTRACTORS
		img = applet.loadImage("screen-1771.tif");
		pixelNodes = new BraintNode[applet.width * applet.height];
		theNodes = new Node[applet.width * applet.height];

		// pixelNodes = new AttractorTest[10];
		// theNodes = new Node[10];

		attractor = new Attractor(applet.width / 2, applet.height / 2, 0);
		attractor.setMode(Attractor.SMOOTH);
		attractor.setRadius(50);
		attractor.setStrength(5);

		applet.loadPixels();
		// Since we are going to access the image's pixels too
		img.loadPixels();
		int c = 0;
		for (int y = 0; y < applet.height; y++) {
			for (int x = 0; x < applet.width; x++) {
				int loc = x + y * applet.width;

				// The functions red(), green(), and blue() pull out
				// the 3 color components from a pixel.
				float r = applet.red(img.pixels[loc]);
				float g = applet.green(img.pixels[loc]);
				float b = applet.blue(img.pixels[loc]);

				BraintNode tmp = new BraintNode(this, x, y, applet.color(r, g, b));

				pixelNodes[loc] = tmp;
				theNodes[loc] = tmp.getNode();


				applet.fill(applet.color(r, g, b));
				applet.rect(x, y, 1, 1);

				c++;
			}
		}
		applet.updatePixels();
		initAttractors = false;
	}

}
