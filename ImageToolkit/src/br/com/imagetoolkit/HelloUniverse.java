package br.com.imagetoolkit;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class HelloUniverse {

	
	public HelloUniverse() {
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup grupo = new BranchGroup();
		TransformGroup tg = new TransformGroup();
		
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		grupo.addChild(tg);
		
		ColorCube cubo = new ColorCube(0.3);
		tg.addChild(cubo);
		
		RotationInterpolator rotacao = new RotationInterpolator(new Alpha(), tg);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);
		rotacao.setSchedulingBounds(bounds);
		
		tg.addChild(rotacao);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(grupo);
		
	}
	
	
	public static void main(String[] args) {
		HelloUniverse h = new HelloUniverse();
	}
	
	
}
