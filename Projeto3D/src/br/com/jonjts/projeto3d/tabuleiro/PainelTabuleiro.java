/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jonjts.projeto3d.tabuleiro;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Jonas
 */
public class PainelTabuleiro extends JPanel {

    public static final int HEIGHT = 512;
    public static final int WIDTH = 512;

    private final SimpleUniverse universe;
    private final Point3d POSICAO_USU = new Point3d(0, 5, 20);
    private BranchGroup cenaBG;
    private BoundingSphere limites;
    private final int LIMITES = 100;

    private final Color3f preta = new Color3f(0.0f, 0.0f, 0.0f);
    private final Color3f azul = new Color3f(0.1f, 0.1f, 0.8f);
    private final Color3f branca = new Color3f(1.0f, 1.0f, 1.0f);
    private final Color3f cinza = new Color3f(.4f, .4f, .4f);

    public PainelTabuleiro() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        //Foco para tela grafica
        canvas3D.setFocusable(true);
        canvas3D.requestFocus();
        //Criando o universo virtual
        universe = new SimpleUniverse(canvas3D);

        //Criando a cena 3D
        crieCenaGrafica();
        //Configurando o ponto de visão do usuario
        iniciandoPosicaoUsuario();
        //Controla a movimentacao do ponto de visao
        controleOrbital(canvas3D);
        //Adicionado a cena 3D ao universo virtual
        universe.addBranchGraph(cenaBG);
    }

    private void iniciandoPosicaoUsuario() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup posicaoTG = vp.getViewPlatformTransform();

        Transform3D t3d = new Transform3D();
        posicaoTG.getTransform(t3d);

        //Posicao do usuario, para onde esta olhando, vetor up
        t3d.lookAt(POSICAO_USU, new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
        t3d.invert();
        posicaoTG.setTransform(t3d);
    }

    private void controleOrbital(Canvas3D canvas3D) {
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(limites);

        ViewingPlatform vp = universe.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }

    private void crieCenaGrafica() {
        cenaBG = new BranchGroup();
        limites = new BoundingSphere(new Point3d(0, 0, 0), LIMITES);
        //Adicionado a iluminacao
        crieIluminacao();
        //Configurando a imagem de fundo
        addBackground();
        //Adicionado o chao no formato de tabuleiro
        cenaBG.addChild(new CheckerFloor().getBG());
        //Adicionando a esfera flutuante
        addTerra();
        //Complilando a cena
        cenaBG.compile();
    }

    private void addBackground() {
        Background background = new Background();
        BufferedImage bufferedImage = loadImage("background.jpg");
        background.setImage(new ImageComponent2D(ImageComponent2D.ALLOW_IMAGE_WRITE, bufferedImage));
        background.setApplicationBounds(limites);
        background.setColor(0.17f, 0.65f, 0.92f);
        
        TransformGroup group = new TransformGroup();
        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(background);
        
        Alpha alpha = new Alpha();
        alpha.setIncreasingAlphaDuration(50000);
        RotationInterpolator rotacao = new RotationInterpolator(alpha, group);
        BoundingSphere limiteRot = new BoundingSphere(new Point3d(0,4,0), 10);
        rotacao.setSchedulingBounds(limiteRot);
        group.addChild(rotacao);
        
        cenaBG.addChild(group);
    }

    private void crieIluminacao() {
        AmbientLight luzAmbiente = new AmbientLight(cinza);
        luzAmbiente.setInfluencingBounds(limites);
        cenaBG.addChild(luzAmbiente);

        Vector3f direcaoLuz1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Vector3f direcaoLuz2 = new Vector3f(1.0f, -1.0f, 1.0f);

        DirectionalLight luz1 = new DirectionalLight(branca, direcaoLuz1);
        luz1.setInfluencingBounds(limites);
        cenaBG.addChild(luz1);

        DirectionalLight luz2 = new DirectionalLight(cinza, direcaoLuz2);
        luz2.setInfluencingBounds(limites);
        cenaBG.addChild(luz2);
    }

    private void addEsfera() {
        Color3f especular = new Color3f(0.9f, 0.9f, 0.9f);

        Material matAzul = new Material(azul, preta, azul, especular, 25);
        matAzul.setLightingEnable(true);

        Appearance appearance = new Appearance();
        appearance.setMaterial(matAzul);

        //Posiciona a esfera
        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0, 4, 0));
        TransformGroup group = new TransformGroup(t3d);

        //Definindo o raio, o numero de faces e a aparencia da esfera
        int primFlags = Primitive.GENERATE_NORMALS;
        group.addChild(new Sphere(2.0f, primFlags, 100, appearance));

        cenaBG.addChild(group);
    }

    private void addTerra() {
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

        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(0, 4, 0));

        TransformGroup group = new TransformGroup();
        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.setTransform(t3d);
        
        Alpha alpha = new Alpha();
        alpha.setIncreasingAlphaDuration(50000);
        RotationInterpolator rotacao = new RotationInterpolator(alpha, group);
        BoundingSphere limiteRot = new BoundingSphere(new Point3d(0,4,0), 10);
        rotacao.setSchedulingBounds(limiteRot);
        group.addChild(rotacao);
        
        int primFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        Sphere bola = new Sphere(3f, primFlags, 150, ap);
        group.addChild(bola);

        cenaBG.addChild(group);
    }

    
    private BufferedImage loadImage(String path){
        File f = new File(path);
        try {
            return ImageIO.read(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
