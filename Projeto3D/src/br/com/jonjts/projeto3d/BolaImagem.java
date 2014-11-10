/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jonjts.projeto3d;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Container;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color4f;

/**
 *
 * @author Jonas
 */
public class BolaImagem {

    public BolaImagem() {
        SimpleUniverse universe = new SimpleUniverse();

        BranchGroup grupo = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        grupo.addChild(tg);

        Color3f preta = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f azul = new Color3f(0.1f, 0.1f, 0.8f);
        Color3f branca = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f cinza = new Color3f(.4f, .4f, .4f);

        TextureLoader loader = new TextureLoader("mapa_terra.jpg", "RGB", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0, 1, 0, 0));

        TextureAttributes textureAttributes = new TextureAttributes();
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(textureAttributes);
        ap.setMaterial(new Material(branca, preta, branca, azul, 100f));

        int primFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        Sphere bola = new Sphere(0.5f, primFlags, 150, ap);
        tg.addChild(bola);
        
        Alpha alpha = new Alpha();
        alpha.setIncreasingAlphaDuration(50000);
        RotationInterpolator rotacao = new RotationInterpolator(alpha, tg);
        BoundingSphere limiteRot = new BoundingSphere(new Point3d(0,0,0), 10);
        rotacao.setSchedulingBounds(limiteRot);
        tg.addChild(rotacao);

        Color3f lightColor1 = new Color3f(1f, 1f, 1f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
        Vector3f posicaoLuz = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight luz1 = new DirectionalLight(lightColor1, posicaoLuz);
        luz1.setInfluencingBounds(bounds);
        grupo.addChild(luz1);

        AmbientLight luzAmb = new AmbientLight(new Color3f(.5f, .5f, .5f));
        luzAmb.setInfluencingBounds(bounds);
        grupo.addChild(luzAmb);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(grupo);
    }

    public static void main(String[] args) {
        new BolaImagem();
    }

}
