package org.pepstock.charba.showcase.client.samples;

public class Color {

	public static final double DEFAULT_ALPHA = 1F;

	private final int red;
	private final int green;
	private final int blue;

	private double alpha = DEFAULT_ALPHA;

	public Color(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public Color alpha(double alpha) {
		Color color = new Color(getRed(), getGreen(), getBlue());
		color.alpha = alpha;
		return color;
	}

	public String toRGBA() {
		return "rgba("+red+","+green+","+blue+","+alpha+")";
	}

	public String toHex() {
		return "#" + pad(Integer.toHexString(red)) + pad(Integer.toHexString(green)) + pad(Integer.toHexString(blue));
	}

	private String pad(String in) {
		if (in.length() == 0) {
			return "00";
		}
		if (in.length() == 1) {
			return "0" + in;
		}
		return in;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Color [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
	}
}