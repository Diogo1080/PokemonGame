package Game.PathFinding;

import Game.Ententies.Entity;

import java.util.List;

public class Path {

    private static PathFinder pf = new PathFinder();

    private List<Point> points;
    public List<Point> points() { return points; }

    public Path(Entity entity, int x, int y){
        points = pf.findPath(entity,
                new Point(entity.x, entity.y),
                new Point(x, y),
                300);
    }
}
