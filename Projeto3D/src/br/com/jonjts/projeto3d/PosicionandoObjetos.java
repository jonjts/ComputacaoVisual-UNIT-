package br.com.jonjts.projeto3d;

import java.security.acl.Group;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class PosicionandoObjetos {
	
	public PosicionandoObjetos(){
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup grupo = new BranchGroup();
		
		for(float x = -1.0f; x <= 1.0f; x = x + 0.1f){
			Sphere sphere = new Sphere(0.05f);
			TransformGroup tg = new TransformGroup();
			
			Transform3D  transform = new Transform3D();
			Vector3f vector = new Vector3f(x, .0f, .0f);
			transform.setTranslation(vector);
			tg.setTransform(transform);
			tg.addChild(sphere);
			grupo.addChild(tg);
		}
		
		for(float y = -1.0f; y <= 1.0f; y = y + 0.1f){
			Cone cone = new Cone(0.05f, 0.1f);
			TransformGroup tg = new TransformGroup();
			
			Transform3D  transform = new Transform3D();
			Vector3f vector = new Vector3f(.0f, y, .0f);
			transform.setTranslation(vector);
			tg.setTransform(transform);
			tg.addChild(cone);
			grupo.addChild(tg);
		}
		
		Color3f lightColor1 = new Color3f(2.0f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);
		Vector3f posicaoLuz = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight luz1 = new DirectionalLight(lightColor1, posicaoLuz);
		luz1.setInfluencingBounds(bounds);
		grupo.addChild(luz1);
		
		//Olhando para a bola
		universe.getViewingPlatform().setNominalViewingTransform();
		//Adicionando o ramo de conteudo grafico ao universo
		universe.addBranchGraph(grupo);
	}
	
	public static void main(String[] args) {
		new PosicionandoObjetos();
	}	

}
