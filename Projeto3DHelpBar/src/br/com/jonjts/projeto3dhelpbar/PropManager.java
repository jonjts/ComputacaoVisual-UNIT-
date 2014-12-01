package br.com.jonjts.projeto3dhelpbar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import ncsa.j3d.loaders.ModelLoader;

import com.sun.j3d.loaders.Scene;

public class PropManager {

    private static final int X_AXIS = 0;
    private static final int Y_AXIS = 1;
    private static final int Z_AXIS = 2;
    private static final int INCR = 0;
    private static final int DECR = 1;
    private static final double MOVE_INCR = 0.1;   // move increment for an object
    private static final double ROT_INCR = 10;     // rotation increment (in degrees)
    private static final double ROT_AMT = Math.toRadians(ROT_INCR);    // in radians
    // TGs which the loaded object (the prop) hangs off: 
    //       moveTG-->rotTG-->scaleTG-->objBoundsTG-->obj
    private TransformGroup moveTG, rotTG, scaleTG;
    private Transform3D t3d;           // for accessing a TG's transform
    private Transform3D chgT3d;        // holds current change to the posn, rot, or scale
    private String filename;           // of loaded object
    private double xRot, yRot, zRot;   // total of rotation angles carried out
    private ArrayList rotInfo;         // stores the sequence of rotation numbers 
    private double scale;              // current object scaling
    private DecimalFormat df;    // for debugging

    public PropManager(String loadFnm, boolean hasCoordsInfo) {
        filename = loadFnm;
        xRot = 0.0;
        yRot = 0.0;
        zRot = 0.0;
        rotInfo = new ArrayList();
        scale = 1.0;

        t3d = new Transform3D();
        chgT3d = new Transform3D();

        df = new DecimalFormat("0.###");

        loadFile(loadFnm);
        if (hasCoordsInfo) {
            getFileCoords(loadFnm);
        }
    }

    private void loadFile(String fnm) {
        System.out.println("Loading object file: models/" + fnm);
        Scene s = null;
        ModelLoader modelLoader = new ModelLoader();
        try {
            s = modelLoader.load("models/" + fnm);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(0);
        }

        BranchGroup scenGroup = s.getSceneGroup();

        TransformGroup objBoundsTG = new TransformGroup();
        objBoundsTG.addChild(scenGroup);

        String ext = getExtension(fnm);
        BoundingSphere objBounds = (BoundingSphere) scenGroup.getBounds();
        setBSPosn(objBoundsTG, objBounds.getRadius(), ext);

        scaleTG = new TransformGroup();
        scaleTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        scaleTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        scaleTG.addChild(objBoundsTG);

        rotTG = new TransformGroup();
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rotTG.addChild(scaleTG);

        moveTG = new TransformGroup();
        moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        moveTG.addChild(rotTG);

    }

    private String getExtension(String fnm) {
        int dotPosition = fnm.lastIndexOf(".");
        if (dotPosition == -1) {
            return "(nome)";
        } else {
            return fnm.substring(dotPosition + 1).toLowerCase();
        }
    }

    private void setBSPosn(TransformGroup objBoundsTG, double radius, String ext) {
        Transform3D objectTrans = new Transform3D();
        objBoundsTG.getTransform(objectTrans);

        Transform3D scaleTrans = new Transform3D();
        double scaleFactor = 1.0 / radius;
        scaleTrans.setScale(scaleFactor);

        objectTrans.mul(scaleTrans);

        if (ext.equals("3ds")) {
            Transform3D rotTrans = new Transform3D();
            rotTrans.rotX(-Math.PI / 2.0);
            objectTrans.mul(rotTrans);
        }

        objBoundsTG.setTransform(objectTrans);
    }

    public TransformGroup getTG() // used by WrapLoader3D to add object to 3D world
    {
        return moveTG;
    }

    // ----------------------------------------------------------------
    // obtain coords file info and apply it to the loaded object
    private void getFileCoords(String fnm) /* Obtain coords info from the coordinates file for fnm.
     The coords file has the format:
     <3D object fnm>
     [-p px py pz]
     [-r sequence of numbers]
     [-s scale]
     */ {
        String coordFile = "models/" + getName(fnm) + "Coords.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(coordFile));
            br.readLine();    // skip fnm line (we know this already)
            String line;
            char ch;
            while ((line = br.readLine()) != null) {
                ch = line.charAt(1);
                if (ch == 'p') {
                    setCurrentPosn(line);
                } else if (ch == 'r') {
                    setCurrentRotation(line);
                } else if (ch == 's') {
                    setCurrentScale(line);
                } else {
                    System.out.println(coordFile + ": did not recognise line: " + line);
                }
            }
            br.close();
            System.out.println("Read in coords file: " + coordFile);
        } catch (IOException e) {
            System.out.println("Error reading coords file: " + coordFile);
            System.exit(1);
        }
    }  // end of getFileCoords()

    private String getName(String fnm) // extract name before the final '.' suffix
    {
        int dotposn = fnm.lastIndexOf(".");
        if (dotposn == -1) // no extension
        {
            return fnm;
        } else {
            return fnm.substring(0, dotposn);
        }
    }

    private void setCurrentPosn(String line) // extract the (x,y,z) position info from the coords file,
    // then apply it to the loaded object
    {
        double vals[] = new double[3];            // for the position data
        vals[0] = 0;
        vals[1] = 0;
        vals[2] = 0;    // represents (x,y,z)

        StringTokenizer tokens = new StringTokenizer(line);
        String token = tokens.nextToken();    // skip command label
        int count = 0;
        while (tokens.hasMoreTokens()) {
            token = tokens.nextToken();
            try {
                vals[count] = Double.parseDouble(token);
                count++;
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect format for position data in coords file");
                break;
            }
        }
        if (count != 3) {
            System.out.println("Insufficient position data in coords file");
        }

        // apply the moves to the loaded object
        doMove(new Vector3d(vals[0], vals[1], vals[2]));

    }  // end of setCurrentPosn()

    private void setCurrentRotation(String line) // extract the rotation info from the coords file,
    // and apply it to the loaded object
    {
        int rotNum;
        StringTokenizer tokens = new StringTokenizer(line);
        String token = tokens.nextToken();    // skip command label
        if (!tokens.hasMoreTokens()) // there may not be any rotation numbers
        {
            return;
        }
        token = tokens.nextToken();
        for (int i = 0; i < token.length(); i++) {
            try {
                rotNum = Character.digit(token.charAt(i), 10);
                // rotInfo.add( new Integer(rotNum));
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect format for rotation data in coords file");
                break;
            }
            if (rotNum == 1) // positive x-axis rotation
            {
                rotate(X_AXIS, INCR);
            } else if (rotNum == 2) // negative
            {
                rotate(X_AXIS, DECR);
            } else if (rotNum == 3) // positive y-axis rotation
            {
                rotate(Y_AXIS, INCR);
            } else if (rotNum == 4) // negative
            {
                rotate(Y_AXIS, DECR);
            } else if (rotNum == 5) // positive z-axis rotation
            {
                rotate(Z_AXIS, INCR);
            } else if (rotNum == 6) // negative
            {
                rotate(Z_AXIS, DECR);
            } else {
                System.out.println("Did not recognise the rotation info in the coords file");
            }
        }
    }  // end of setCurrentRotation()

    private void setCurrentScale(String line) // extract the scale info from the coords file,
    // and apply it to the loaded object
    {
        StringTokenizer tokens = new StringTokenizer(line);
        String token = tokens.nextToken();    // skip command label
        double startScale;

        token = tokens.nextToken();    // should be the scale value
        try {
            startScale = Double.parseDouble(token);
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect format for scale data in coords file");
            startScale = 1.0;
        }
        // System.out.println("Loaded start scale: " + startScale);
        if (startScale != 1.0) {
            scale(startScale);
        }
    }  // end of setCurrentScale()

    //---------------------------------------------------------
    // modify the position/rotation/scale of the loaded object

    /* These methods are called when applying the coords file
     information *and* when user commands sent from the GUI 
     are being processed.
     */
    public void move(int axis, int change) {
        double moveStep = (change == INCR) ? MOVE_INCR : -MOVE_INCR;
        Vector3d movVec;
        if (axis == X_AXIS) {
            movVec = new Vector3d(moveStep, 0, 0);
        } else if (axis == Y_AXIS) {
            movVec = new Vector3d(0, moveStep, 0);
        } else {
            movVec = new Vector3d(0, 0, moveStep);
        }
        doMove(movVec);
    }  // end of move()

    private void doMove(Vector3d theMove) {
        moveTG.getTransform(t3d);
        chgT3d.setIdentity();
        chgT3d.setTranslation(theMove);
        t3d.mul(chgT3d);
        moveTG.setTransform(t3d);
    }

    public void rotate(int axis, int change) {
        doRotate(axis, change);
        storeRotate(axis, change);
    }

    private void doRotate(int axis, int change) {
        double radians = (change == INCR) ? ROT_AMT : -ROT_AMT;
        rotTG.getTransform(t3d);
        chgT3d.setIdentity();
        switch (axis) {
            case X_AXIS:
                chgT3d.rotX(radians);
                break;
            case Y_AXIS:
                chgT3d.rotY(radians);
                break;
            case Z_AXIS:
                chgT3d.rotZ(radians);
                break;
            default:
                System.out.println("Unknow axis of rotation");
                break;
        }
        t3d.mul(chgT3d);
        rotTG.setTransform(t3d);
    }  // end of doRotate()

    private void storeRotate(int axis, int change) // store the rotation information
    {
        double degrees = (change == INCR) ? ROT_INCR : -ROT_INCR;
        switch (axis) {
            case X_AXIS:
                storeRotateX(degrees);
                break;
            case Y_AXIS:
                storeRotateY(degrees);
                break;
            case Z_AXIS:
                storeRotateZ(degrees);
                break;
            default:
                System.out.println("Unknown storage axis of rotation");
                break;
        }
    }  // end of storeRotate() 

    private void storeRotateX(double degrees) // record the x-axis rotation
    {
        xRot = (xRot + degrees) % 360;   // update x-axis total rotation
        if (degrees == ROT_INCR) {
            rotInfo.add(new Integer(1));  // rotation number
        } else if (degrees == -ROT_INCR) {
            rotInfo.add(new Integer(2));
        } else {
            System.out.println("No X-axis rotation number for " + degrees);
        }
    } // end of storeRotateX()

    private void storeRotateY(double degrees) // record the y-axis rotation
    {
        yRot = (yRot + degrees) % 360;   // update y-axis total rotation
        if (degrees == ROT_INCR) {
            rotInfo.add(new Integer(3));  // rotation number
        } else if (degrees == -ROT_INCR) {
            rotInfo.add(new Integer(4));
        } else {
            System.out.println("No Y-axis rotation number for " + degrees);
        }
    } // end of storeRotateY()

    private void storeRotateZ(double degrees) // record the z-axis rotation
    {
        zRot = (zRot + degrees) % 360;   // update z-axis total rotation
        if (degrees == ROT_INCR) {
            rotInfo.add(new Integer(5));  // rotation number
        } else if (degrees == -ROT_INCR) {
            rotInfo.add(new Integer(6));
        } else {
            System.out.println("No Z-axis rotation number for " + degrees);
        }
    } // end of storeRotateZ()

    public void scale(double d) {
        scaleTG.getTransform(t3d);
        chgT3d.setIdentity();
        chgT3d.setScale(d);
        t3d.mul(chgT3d);
        scaleTG.setTransform(t3d);
        scale *= d;
    }

    // ----------------------------------------------------------
    // return current position/rotation/scale information
    // Used by the GUI interface
    public Vector3d getLoc() {
        moveTG.getTransform(t3d);
        Vector3d trans = new Vector3d();
        t3d.get(trans);
        return trans;
    } // end of getLoc()

    public Point3d getRotations() {
        return new Point3d(xRot, yRot, zRot);
    }

    public double getScale() {
        return scale;
    }

    // ------------------------------ storing ---------------------------
    public void saveCoordFile() // create a coords file for this object
    {
        String coordFnm = "models/" + getName(filename) + "Coords.txt";
        try {
            PrintWriter out = new PrintWriter(new FileWriter(coordFnm));

            out.println(filename);     // object filename
            Vector3d currLoc = getLoc();
            final String coor = "-p " + replaceVirgula(df.format(currLoc.x)) + " " + replaceVirgula(df.format(currLoc.y))
                    + " " + replaceVirgula(df.format(currLoc.z));
            out.println(coor);
            out.print("-r ");
            for (int i = 0; i < rotInfo.size(); i++) {
                out.print("" + ((Integer) rotInfo.get(i)).intValue());
            }
            out.println("");

            out.println("-s " + df.format(scale));

            out.close();
            System.out.println("Saved to coord file: " + coordFnm);
        } catch (IOException e) {
            System.out.println("Error writing to coord file: " + coordFnm);
        }
    }  // end of saveCoordFile()

    // --------------------- methods used for debugging --------------------------
    private void printTG(TransformGroup tg, String id) // print the translation stored in tg
    {
        Transform3D currt3d = new Transform3D();
        tg.getTransform(currt3d);
        Vector3d currTrans = new Vector3d();
        currt3d.get(currTrans);
        printTuple(currTrans, id);
    }  // end of printTG()

    private void printTuple(Tuple3d t, String id) // used to print Vector3d, Point3d objects
    {
        System.out.println(id + " x: " + df.format(t.x)
                + ", " + id + " y: " + df.format(t.y)
                + ", " + id + " z: " + df.format(t.z));
    }  // end of printTuple()

    private String replaceVirgula(String valor) {
        return valor.replaceAll(",", ".");
    }

    @Override
    public String toString() {
        return getName(filename);
    }

}
