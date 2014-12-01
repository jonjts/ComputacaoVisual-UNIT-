package br.com.jonjts.projeto3dhelpbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class WrapLoader3D extends JPanel {

    private static final int PWIDTH = 512;   // size of panel
    private static final int PHEIGHT = 512;
    private static final int BOUNDSIZE = 100;  // larger than world
    private static final Point3d USERPOSN = new Point3d(0, 5, 10);
    // initial user position
    private SimpleUniverse su;
    private BranchGroup sceneBG;
    private BoundingSphere bounds;      // for environment nodes
    private TransformGroup objectTG;    // TG which the loaded object hangs off
    private PropManager propMan;        // manages the manipulation of the 
    // loaded object
    
    private Vector<PropManager> propManagers;

    // private Java3dTree j3dTree;   // frame to hold tree display
    public WrapLoader3D(String filename, boolean hasCoordsInfo) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        su = new SimpleUniverse(canvas3D);

        createSceneGraph(filename);
        initUserPosition();
        orbitControls(canvas3D);

        su.addBranchGraph(sceneBG);
    }

    private void createSceneGraph(String filename) {
        sceneBG = new BranchGroup();
        bounds = new BoundingSphere(new Point3d(0, 0, 0), BOUNDSIZE);
        
        propManagers = new Vector<PropManager>();

        lightScene();
        addBackground();
        sceneBG.addChild(new CheckerFloor().getBG());
        PropManager propMan = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);

                propMan = new PropManager(line.trim(), true);
                propManagers.add(propMan);
                sceneBG.addChild(propMan.getTG());

            }
            this.propMan = propManagers.firstElement();
            br.close();
        } catch (IOException e) {
            System.exit(1);
        }
        sceneBG.compile();
    }

    private void lightScene() {
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        AmbientLight ambientLightNode = new AmbientLight(white);
        ambientLightNode.setInfluencingBounds(bounds);
        sceneBG.addChild(ambientLightNode);

        Vector3f light1Diretion = new Vector3f(-1.0f, -1.0f, -1.0f);
        Vector3f ligth2Diretion = new Vector3f(1.0f, -1.0f, 1.0f);

        DirectionalLight light1 = new DirectionalLight(white, light1Diretion);
        light1.setInfluencingBounds(bounds);
        sceneBG.addChild(light1);

        DirectionalLight ligth2 = new DirectionalLight(white, ligth2Diretion);
        ligth2.setInfluencingBounds(bounds);
        sceneBG.addChild(ligth2);

    }

    private void addBackground() {
        Background back = new Background();
        back.setApplicationBounds(bounds);
        back.setColor(0.17f, 0.65f, 0.92f);
        sceneBG.addChild(back);
    }

    private void orbitControls(Canvas3D c) {
        OrbitBehavior orbitBehavior = new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
        orbitBehavior.setSchedulingBounds(bounds);
        ViewingPlatform vp = su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbitBehavior);
    }  // end of orbitControls()

    private void initUserPosition() {
        ViewingPlatform vp = su.getViewingPlatform();
        TransformGroup steerTG = vp.getViewPlatformTransform();

        Transform3D t3d = new Transform3D();
        steerTG.getTransform(t3d);

        t3d.lookAt(USERPOSN, new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
        t3d.invert();

        steerTG.setTransform(t3d);
    }

    // ---------------------- handle prop ---------------------------
    // The follwing methods are used by the GUI in Loader3D to
    // access the PropManager.
    public void movePos(int axis, int change) {
        propMan.move(axis, change);
    }

    public void rotate(int axis, int change) {
        propMan.rotate(axis, change);
    }

    public void scale(double d) {
        propMan.scale(d);
    }

    public Vector3d getLoc() {
        return propMan.getLoc();
    }

    public Point3d getRotations() {
        return propMan.getRotations();
    }

    public double getScale() {
        return propMan.getScale();
    }

    public void saveCoordFile() {
        propMan.saveCoordFile();
    }

    public void setPropMan(PropManager propMan) {
        this.propMan = propMan;
    }

    public Vector<PropManager> getPropManagers() {
        return propManagers;
    }
}
