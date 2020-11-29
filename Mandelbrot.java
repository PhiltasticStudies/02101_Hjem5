
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class Mandelbrot {
	static final int MAX = 255;
	static final double MARGIN = 0.002;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter start x-coordinate (Real part)");
		double x0 = sc.nextDouble();

		System.out.println("Enter start y-coordinate (Imaginary part)");
		double y0 = sc.nextDouble();

		System.out.println("Enter side length");
		double s = sc.nextDouble();
		
		System.out.println("Enter grid size");
		int g = sc.nextInt();

		int choice = getChoice(sc);
		Map<Integer, Color> colorMap = null;
		
		if (choice == 1) {
			System.out.print("Enter name of premade file: ");
			File file = new File(sc.next());
			colorMap = loadFile(file);
		
		}
		
		if (choice == 2) {
			colorMap = staticColor(StdDraw.RED, StdDraw.WHITE);
		}
		
		if (choice == 3) {
			colorMap = generateRandom();
		}
		drawAll(s, x0, y0, g, choice, colorMap);
	}

	private static Map<Integer, Color> staticColor(Color color1, Color color2) {
		Map<Integer, Color> fixedColor = new HashMap<>();
		
		for (int i = 0; i < MAX; i++) {
			fixedColor.put(i, color2);
		}
		fixedColor.put(MAX, color1);
		return fixedColor;
	}

	private static Map<Integer, Color> generateRandom() {
		Map<Integer, Color> random = new HashMap<>();
		
		Random r = new Random();
		for (int i = 0; i <= MAX; i++) {
			Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			random.put(i, color);
		}
		return random;
	}

	private static int getChoice(Scanner sc) {
		int choice = 0;

		while (choice != 1 && choice != 2 && choice != 3) {
			System.out.println("Enter number corresponding to action: \n" 
					+ "1 to load colors from file \n"
					+ "2 to play from a fixed color \n"
					+ "3 to play from random colors \n");
			try {
				choice = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.out.println("invalid input, try again");
				sc.nextLine();
				continue;
			}
			System.out.println((choice == 1 || choice == 2 || choice == 3) ? "" : "Input not valid, try again");
		}
		return choice;
	}

	private static void drawAll(double s, double x0, double y0, int g, int choice, Map<Integer, Color> colorMap) {
		initialize(x0, y0, s, g);
		
		Complex grid;
		for (int j = 0; j < g; j++) {
			for (int k = 0; k < g; k++) {
				double real = (x0 - (s / 2.0) + (s * j) / (g - 1.0));
				double imag = (y0 - (s / 2.0) + (s * k) / (g - 1.0));
				grid = new Complex(real, imag);
		
				int i = iterate(grid);
				draw(choice, grid, colorMap, i);
			}
			StdDraw.show(0);
		}
		
	}

	private static void draw(int choice, Complex grid, Map<Integer, Color> colorMap, int i) {
		StdDraw.setPenColor(colorMap.get(i));
		draw(grid);
	}
	
	public static void draw(Complex z) {
		StdDraw.point(z.getRe(), z.getIm());
	}
	public static void initialize(double x0, double y0, double s, int g) {
		StdDraw.setXscale(x0 - s / 2.0, x0 + s / 2.0);
		StdDraw.setYscale(y0 - s / 2.0, y0 + s / 2.0);
		StdDraw.setPenRadius(2.0 / g);
		StdDraw.show(0);
	}

	public static Map<Integer, Color> loadFile(File file) throws FileNotFoundException {

		Map<Integer, Color> allColors = new HashMap<>();

		try (Scanner input = new Scanner(file)) {
			int i = 0;
			while (input.hasNextLine()) {
				Color color = new Color(input.nextInt(), input.nextInt(), input.nextInt());
				allColors.put(i, color);
				i++;
				input.nextLine();
			}
		}
		return allColors;
	}

	public static int iterate(Complex z0) {
		Complex z = new Complex(z0);
		for (int i = 0; i < MAX; i++) {
			if (z.abs() > 2.0) {
				return i;
			}
			z = z.times(z).plus(z0);
		}
		return MAX;
	}

}
