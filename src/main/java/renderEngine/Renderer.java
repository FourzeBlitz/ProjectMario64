package renderEngine;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import shaders.StaticShader;
import toolbox.Maths;
import Engine.Window;

/**
 * Handles the rendering of a model to the screen.
 * 
 * @author Karl
 *
 */
public class Renderer {
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private Matrix4f projectionMatrix;

	//ambil window dari main game loop
	public Renderer(StaticShader shader, Window window){
		createProjectionMatrix(window);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**
	 * This method must be called each frame, before any rendering is carried
	 * out. It basically clears the screen of everything that was rendered last
	 * frame (using the glClear() method). The glClearColor() method determines
	 * the colour that it uses to clear the screen. In this example it makes the
	 * entire screen red at the start of each frame.
	 */
	public void prepare() {
		// depth biar tau triangle mana yg hrs dirender dluan trs hrs diclear setiap frame
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		// warna bg
		GL11.glClearColor(0, 0.3f, 0.0f, 1);
	}

	/**
	 * Renders a model to the screen.
	 * 
	 * Before we can render a VAO it needs to be made active, and we can do this
	 * by binding it. We also need to enable the relevant attributes of the VAO,
	 * which in this case is just attribute 0 where we stored the position data.
	 * 
	 * The VAO can then be rendered to the screen using glDrawArrays(). We tell
	 * it what type of shapes to render and the number of vertices that it needs
	 * to render.
	 * 
	 * After rendering we unbind the VAO and disable the attribute.
	 * 
	 * @param entity
	 *            - The model to be rendered.
	 */
	public void render(Entity entity, StaticShader shader) {
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		// Bind karena mau dipake
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0); //vertex
		GL20.glEnableVertexAttribArray(1); //texture
		// Entity transformation
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		// activate sampler 2d. By default di texture0
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		/**
		 * param 1: cara gambar
		 * param 2: number of vertices to render
		 * param 3: tipe data dr indicesnya, karena index/indices nya integer jd pakai unsigned int
		 * param 4: render data start drmn, start dr index 0
		 */
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		// Unbind karena sudah selesai dipake
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	private void createProjectionMatrix(Window window){
		float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
