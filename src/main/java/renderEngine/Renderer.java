package renderEngine;

import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Handles the rendering of a model to the screen.
 * 
 * @author Karl
 *
 */
public class Renderer {

	/**
	 * This method must be called each frame, before any rendering is carried
	 * out. It basically clears the screen of everything that was rendered last
	 * frame (using the glClear() method). The glClearColor() method determines
	 * the colour that it uses to clear the screen. In this example it makes the
	 * entire screen red at the start of each frame.
	 */
	public void prepare() {
		// warna bg
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
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
	 * @param texturedModel
	 *            - The model to be rendered.
	 */
	public void render(TexturedModel texturedModel) {
		RawModel model = texturedModel.getRawModel();
		// Bind karena mau dipake
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0); //vertex
		GL20.glEnableVertexAttribArray(1); //texture
		// activate sampler 2d. By default di texture0
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
		/**
		 * param 1: cara gambar
		 * param 2: number of vertices to render
		 * param 3: tipe data dr indiciesnya, karena index/indicies nya integer jd pakai unsigned int
		 * param 4: render data start drmn, start dr index 0
		 */
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		// Unbind karena sudah selesai dipake
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

}
