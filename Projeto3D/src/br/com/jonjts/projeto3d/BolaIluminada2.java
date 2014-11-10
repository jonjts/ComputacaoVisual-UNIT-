/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jonjts.projeto3d;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Jonas
 */
public class BolaIluminada2 {

    public BolaIluminada2() {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup grupo = new BranchGroup();

        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Sphere bola = new Sphere(0.5f, Primitive.GENERATE_NORMALS, 10);
        tg.addChild(bola);

        grupo.addChild(tg);

        Color3f lightColor = new Color3f(1.8f, 0.7f, 1.0f);
        RotationInterpolator rotacao = new RotationInterpolator(new Alpha(), tg);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
        rotacao.setSchedulingBounds(bounds);
        tg.addChild(rotacao);

        Vector3f posicaoLuz = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight luz1 = new DirectionalLight(lightColor, posicaoLuz);
        luz1.setInfluencingBounds(bounds);
        grupo.addChild(luz1);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(grupo);

    }

    public static void main(String[] args) {
        new BolaIluminada2();
    }
}
