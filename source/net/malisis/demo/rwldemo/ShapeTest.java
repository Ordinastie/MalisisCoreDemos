package net.malisis.demo.rwldemo;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.math.BlockPos;

/**
 * The Class Shape.
 */
class ShapeTest
{
	/** The points. */
	BlockPos[] points;

	/**
	 * Align to points.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public void alignToPoints(BlockPos start, BlockPos end)
	{
		double vx = end.getX() - start.getX();
		double vy = end.getY() - start.getY();
		double vz = end.getZ() - start.getZ();
		double r = Math.sqrt(vx * vx + vy * vy + vz * vz);
		double dotProd = vz / r;
		if (dotProd < .95)
		{
			double cvx, cvy;
			cvx = vy / r;
			cvy = -vx / r;
			double h = (1 - dotProd) / (1 - (dotProd * dotProd));
			double a1 = dotProd + h * cvx * cvx;
			double a2 = h * cvy * cvx;
			double a3 = cvy;
			double b1 = h * cvy * cvx;
			double b2 = dotProd + h * cvy * cvy;
			double b3 = -cvx;
			double c1 = -cvy;
			double c2 = cvx;
			int xrot, yrot, zrot;
			for (int i = 0; i < this.points.length; i++)
			{
				BlockPos point = this.points[i];
				xrot = (int) Math.round((point.getX() * a1 + point.getY() * a2 + point.getZ() * a3));
				yrot = (int) Math.round((point.getX() * b1 + point.getY() * b2 + point.getZ() * b3));
				zrot = (int) Math.round((point.getX() * c1 + point.getY() * c2 + point.getZ() * dotProd));
				this.points[i] = new BlockPos(xrot, yrot, zrot);
			}
		}
	}

	/**
	 * Make ellipse.
	 *
	 * @param width the a
	 * @param height the b
	 */
	public static Set<BlockPos> makeEllipse(int width, int height)
	{
		int hh = height * height;
		int ww = width * width;
		int hhww = hh * ww;
		int x0 = width;
		int dx = 0;

		Set<BlockPos> temp = Sets.newHashSet();

		for (int x = -width; x <= width; x++)
			temp.add(new BlockPos(x, 0, 0));

		// now do both halves at the same time, away from the diameter
		for (int y = 1; y <= height; y++)
		{
			int x1 = x0 - (dx - 1); // try slopes of dx - 1 or more
			for (; x1 > 0; x1--)
				if (x1 * x1 * hh + y * y * ww <= hhww)
					break;
			dx = x0 - x1; // current approximation of the slope
			x0 = x1;

			for (int x = -x0; x <= x0; x++)
			{
				temp.add(new BlockPos(x, -y, 0));
				temp.add(new BlockPos(x, +y, 0));
			}
		}
		return temp;
	}

}
