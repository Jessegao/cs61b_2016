public class Planet
{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img)
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p)
	{
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p)
	{
		return Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos) + 
			(yyPos-p.yyPos)*(yyPos-p.yyPos));
	}

	public double calcForceExertedBy(Planet p)
	{
		double g = 6.67e-11;
		double distance = calcDistance(p);
		return g*mass*p.mass/(distance*distance);
	}

	public double calcForceExertedByX(Planet p)
	{
		return calcForceExertedBy(p)*(p.xxPos-xxPos)/calcDistance(p);
	}

	public double calcForceExertedByY(Planet p)
	{
		return calcForceExertedBy(p)*(p.yyPos-yyPos)/calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] ps)
	{
		double result = 0;
		for (Planet p : ps) 
		{
			if(!this.equals(p))
			{
				result+= calcForceExertedByX(p);
			}
		}
		return result;
	}

	public double calcNetForceExertedByY(Planet[] ps)
	{
		double result = 0;
		for (Planet p : ps) 
		{
			if(!this.equals(p))
			{
				result+= calcForceExertedByY(p);
			}
		}
		return result;
	}

	public void update(double dt, double fX, double fY)
	{
		double ax = fX/mass;
		double ay = fY/mass;
		xxVel += dt*ax;
		yyVel += dt*ay;
		xxPos += dt*xxVel;
		yyPos += dt*yyVel;

	}

	public void draw()
	{
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + imgFileName);
	}
}