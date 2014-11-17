package br.com.jonjts.projeto3d.tabuleiro;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Text2D;

public class CheckerFloor {
    private final static int FLOOR_SIZE = 100;

    // colors for axis and text
    private final static Color3f medRed = new Color3f(0.8f, 0.4f, 0.3f);
    private final static Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

    // single instance variable, to be able to add to scene graph
    private BranchGroup floorBG;   
    
    public CheckerFloor() {
        ArrayList<Point3f> blueCoords = new ArrayList<Point3f>();
        ArrayList<Point3f> greenCoords = new ArrayList<Point3f>();
        floorBG = new BranchGroup();

        boolean isBlue = false;
        // create coordinates of tiles. Each tile is 1 unit by 1 unit
        final int LIMIT = (FLOOR_SIZE / 2) - 1;
        for(int z = -FLOOR_SIZE / 2; z <= LIMIT; z++) {
            isBlue = !isBlue;
            for(int x = -FLOOR_SIZE / 2; x <= LIMIT; x++) {
                Point3f[] points = createCoords(x, z);
                ArrayList<Point3f> addTo = isBlue ? blueCoords : greenCoords;
                for(Point3f p : points)
                    addTo.add(p);
                isBlue = !isBlue;
            }
        }
        // tile colors
        Color3f blue = new Color3f(0.0f, 0.1f, 0.4f);
        floorBG.addChild( new ColoredTiles(blueCoords, blue) );
        Color3f green = new Color3f(0.0f, 0.5f, 0.1f);
        floorBG.addChild( new ColoredTiles(greenCoords, green) );

        addOriginMarker();
        labelAxes();
    }
    
    public BranchGroup getBG() {
        return floorBG;
    }
    
    private Point3f[] createCoords(int x, int z) {
        Point3f[] result = new Point3f[4];
        result[0] = new Point3f(x, 0, z + 1);
        result[1] = new Point3f(x + 1, 0, z + 1);
        result[2] = new Point3f(x + 1, 0, z);
        result[3] = new Point3f(x, 0, z);   
        return result;
    }
    
    private void addOriginMarker(){
        float q = 0.25f;
        float y = 0.01f;
        ArrayList<Point3f> pts = new ArrayList<Point3f>();
        pts.add(new Point3f(-q, y, q));
        pts.add(new Point3f(-q, y, -q));
        pts.add(new Point3f(q, y, -q));
        pts.add(new Point3f(q, y, q));
        floorBG.addChild(new ColoredTiles(pts, medRed)); 
    }
    
    private void labelAxes(){
        final int LIMIT = FLOOR_SIZE / 2;
        Vector3d pt = new Vector3d();
        for(int i = -LIMIT; i <= LIMIT; i++){
            pt.z = 0;
            pt.x = i;
            floorBG.addChild( makeText(pt, "" + i) );
            pt.x = 0;
            pt.z = i;
            floorBG.addChild( makeText(pt, "" + i) );
        }
    }

    private TransformGroup makeText(Vector3d pos, String text){
        Text2D label = new Text2D(text, white, "SansSerif", 36, Font.BOLD);
        
        // don't cull labels
        Appearance app = label.getAppearance();       
        PolygonAttributes pa = app.getPolygonAttributes();
        if (pa == null)
            pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        if (app.getPolygonAttributes() == null)
            app.setPolygonAttributes(pa);
      
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(pos);
        tg.setTransform(transform);
        tg.addChild(label);

        return tg;
    }
}

class ColoredTiles extends Shape3D {
    
    private QuadArray plane;
    
    public ColoredTiles(ArrayList<Point3f> pts, Color3f col){
        plane = new QuadArray(pts.size(), 
            GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        
        Point3f[] quadPoints = new Point3f[pts.size()];
        pts.toArray(quadPoints); // copy elements into array
        
        // 0 is the starting vertex in the QuadArray
        plane.setCoordinates(0, quadPoints); 
        
        // set the color of all vertices. Same for all vertices
        Color3f[] colors = new Color3f[pts.size()];
        Arrays.fill(colors, col);
        plane.setColors(0, colors);
        
        // inherited method from Shape3D
        setGeometry(plane);
        
        setAppearance();
    }
    
    private void setAppearance(){
        Appearance ap = new Appearance();
        PolygonAttributes attr = new PolygonAttributes();
        attr.setCullFace(PolygonAttributes.CULL_NONE);
        ap.setPolygonAttributes(attr);

        // inherited methods from Shape3D
        setAppearance(ap);
    }     
}