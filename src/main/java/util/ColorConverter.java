package util;

import javafx.scene.paint.Color;

/**
 * This class is used to convert the colors from java.awt to javafx to web
 */
public class ColorConverter {
	
	private ColorConverter(){}
	
	/**
	 * convert a awt Color to fx one
	 * @param color
	 * @return
	 */
	public static Color awtToFx(java.awt.Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		double alpha = color.getAlpha()/255.0;
		return Color.rgb(red, green, blue, alpha);
	}

	/**
	 * convert a opaque color from awt to web string
	 * @param color
	 * @return
	 */
	public static String awtToWeb(java.awt.Color color) {
		return fxToWeb(awtToFx(color));
	}
	
	/**
	 * convert a opaque color from fx to web string 
	 * @param color
	 * @return
	 */
	public static String fxToWeb(Color color) {
		return "#" + color.toString().substring(2, 8);
	}
}
