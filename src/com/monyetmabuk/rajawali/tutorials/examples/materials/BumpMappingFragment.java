package com.monyetmabuk.rajawali.tutorials.examples.materials;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.NormalMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.graphics.Color;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class BumpMappingFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new BumpMappingRenderer(getActivity());
	}

	private final class BumpMappingRenderer extends AExampleRenderer {
		private PointLight mLight;
		private Object3D mEarth;
		private Animation3D mLightAnim;

		public BumpMappingRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new PointLight();
			mLight.setPosition(-2, -2, 0);
			mLight.setPower(2f);
			
			getCurrentScene().addLight(mLight);
			getCurrentCamera().setPosition(0, 0, 6);

			try {
				Plane cube = new Plane(18, 12, 2, 2);
				Material material1 = new Material();
				material1.setDiffuseMethod(new DiffuseMethod.Lambert());
				material1.enableLighting(true);
				material1.addTexture(new Texture("wallDiffuseTex", R.drawable.masonry_wall_texture));
				material1.addTexture(new NormalMapTexture("wallNormalTex", R.drawable.masonry_wall_normal_map));
				material1.setColorInfluence(0);
				cube.setMaterial(material1);
				cube.setZ(-2);
				getCurrentScene().addChild(cube);

				RotateOnAxisAnimation anim = new RotateOnAxisAnimation(Axis.Y, -5, 5);
				anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
				anim.setDurationMilliseconds(5000);
				anim.setTransformable3D(cube);
				getCurrentScene().registerAnimation(anim);
				anim.play();

				mEarth = new Sphere(1, 32, 32);
				mEarth.setZ(-.5f);
				getCurrentScene().addChild(mEarth);

				Material material2 = new Material();
				material2.setDiffuseMethod(new DiffuseMethod.Lambert());
				material2.setSpecularMethod(new SpecularMethod.Phong(Color.WHITE, 150));
				material2.enableLighting(true);
				material2.addTexture(new Texture("earthDiffuseTex", R.drawable.earth_diffuse));
				material2.addTexture(new NormalMapTexture("eartNormalTex", R.drawable.earth_normal));
				material2.setColorInfluence(0);
				mEarth.setMaterial(material2);

				RotateOnAxisAnimation earthAnim = new RotateOnAxisAnimation(Axis.Y, 359);
				earthAnim.setDurationMilliseconds(6000);
				earthAnim.setRepeatMode(RepeatMode.INFINITE);
				earthAnim.setTransformable3D(mEarth);
				getCurrentScene().registerAnimation(earthAnim);
				earthAnim.play();

			} catch (TextureException e) {
				e.printStackTrace();
			}

			mLightAnim = new TranslateAnimation3D(new Vector3(-2, 2, 2),
					new Vector3(2, -2, 2));
			mLightAnim.setDurationMilliseconds(4000);
			mLightAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			mLightAnim.setTransformable3D(mLight);
			mLightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			getCurrentScene().registerAnimation(mLightAnim);
			mLightAnim.play();
		}

	}

}
