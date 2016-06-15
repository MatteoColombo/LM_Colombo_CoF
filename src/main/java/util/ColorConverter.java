package util;

import javafx.scene.paint.Color;

public class ColorConverter {
	
	public static Color awtToFx(java.awt.Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		double alpha = color.getAlpha()/255;
		return Color.rgb(red, green, blue, alpha);
	}

	public static String awtToWeb(java.awt.Color color) {
		return fxToWeb(awtToFx(color));
	}
	
	public static String fxToWeb(Color color) {
		return "#" + color.toString().substring(2, 8);
	}
}
