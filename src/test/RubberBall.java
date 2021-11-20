/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * This represents the rubber ball which inherits from ball
 */
public class RubberBall extends Ball {
    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = new Color(255, 219, 88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();

    /**
     * This represents the rubber ball and initialises it
     * <br>Changes:
     * <ul>
     *     <li>Only one DEF_RADIUS as an argument instead of two</li>
     * </ul>
     * @param center The coordinates of the center of the ball
     */
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR); //center value is in GameBoard.java - wall
    }

    /**
     * Creates the shape of the ball
     * <br>Changes:
     * <ul>
     *     <li>Removed radiusA and radiusB and added radius</li>
     * </ul>
     * @param center The coordinates of the center of the ball
     * @param radius The radius of the ball
     * @return The shape of the ball
     */
    @Override
    protected Shape makeBall(Point2D center, int radius) {
        double x = center.getX() - (radius / 2);
        double y = center.getY() - (radius / 2);

        return new Ellipse2D.Double(x,y,radius,radius); //creating ball shape
    }

}
