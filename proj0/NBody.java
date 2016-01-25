public class NBody
{
	public static void main(String[] args)
	{
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		// System.out.println("radius=" + radius);

		StdDraw.setScale(-radius, radius);
		// StdDraw.picture(0,0, "images/starfield.jpg", radius*5., radius*1.0);
		StdDraw.picture(0,0, "images/starfield.jpg");
		// StdDraw.picture(0,0, "images/starfield.jpg", radius, radius);
		for(Planet planet : planets)
		{
			planet.draw();
		}

		for(double t = 0; t<T; t+= dt)
		{
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int i = 0; i<planets.length; i++)
			{
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				planets[i].update( dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0,0, "images/starfield.jpg");
			for(Planet planet : planets)
			{
				planet.draw();
			}
			StdDraw.show(1);
		}
	StdOut.printf("%d\n", planets.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < planets.length; i++) {
	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   	planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
}		

		
	}

	public static double readRadius(String filename)
	{
		In in = new In(filename);
		int numplanets= in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String filename)
	{
		In in = new In(filename);
		int numplanets= in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[numplanets];
		for(int i = 0; i<numplanets; i++)
		{
			planets[i] = new Planet(
				in.readDouble(),
				in.readDouble(),
				in.readDouble(),
				in.readDouble(),
				in.readDouble(),
				in.readString());
		}
		return planets;
	}
}