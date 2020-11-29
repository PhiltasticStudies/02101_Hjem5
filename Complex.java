

public class Complex {
	private double re;
	private double im;

	public Complex() {
		this(0, 0);
	}

	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}

	public Complex(Complex z) {
		this(z.getRe(), z.getIm());
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public double abs() {
		return Math.hypot(re, im);
	}

	public Complex plus(Complex other) {
		double real = this.re + other.re;
		double imag = this.im + other.im;
		return new Complex(real, imag);
	}

	public Complex times(Complex other) {
		double real = this.re * other.re - this.im * other.im;
		double imag = this.re * other.im + this.im * other.re;
		return new Complex(real, imag);
	}
//	Unused
	public Complex minus(Complex other) {
		double real = this.re - other.re;
		double imag = this.im - other.im;
		return new Complex(real, imag);
	}


	

	@Override
	public String toString() {
		if (im == 0) {
			return re + "";
		}
		if (re == 0) {
			return im + "i";
		}
		if (im < 0) {
			return re + " - " + (-im) + "i";

		} else {
			return re + " + " + im + "i";
		}
	}
}
