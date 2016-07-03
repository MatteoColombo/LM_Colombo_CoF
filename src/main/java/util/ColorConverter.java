package util;

import javafx.scene.paint.Color;

/**
 * This class is used to convert the colors from java.awt to javafx and viceversa
 */
public class ColorConverter {
	
	private ColorConverter(){}
	
	public static Color awtToFx(java.awt.Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		double alpha = color.getAlpha()/255.0;
		return Color.rgb(red, green, blue, alpha);
	}

	public static String awtToWeb(java.awt.Color color) {
		return fxToWeb(awtToFx(color));
	}
	
	public static String fxToWeb(Color color) {
		return "#" + color.toString().substring(2, 8);
	}
}
