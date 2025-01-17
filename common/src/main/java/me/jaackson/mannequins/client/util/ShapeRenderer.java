package me.jaackson.mannequins.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11C;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.GL_QUADS;

/**
 * <p>Renders {@link GL11C#GL_QUADS} to the screen using enhanced precision and {@link BufferBuilder}.</p>
 * <p>To use chain rendering, use {@link #begin()} to start rendering and {@link #end()} to complete a batch.</p>
 * <p>From <a href="https://github.com/Ocelot5836/Sonar/blob/1.16.4/src/main/java/io/github/ocelot/sonar/client/render/ShapeRenderer.java">Sonar</a></p>
 *
 * @author Ocelot
 */
@Environment(EnvType.CLIENT)
public final class ShapeRenderer {
    private static float zLevel = 0.0F;
    private static float red = 1.0F;
    private static float green = 1.0F;
    private static float blue = 1.0F;
    private static float alpha = 1.0F;

    private ShapeRenderer() {
    }

    /**
     * Draws a quad onto the screen with the specified parameters.
     *
     * @param x      The x position to start
     * @param y      The y position to start
     * @param width  The x size of the quad
     * @param height The y size of the quad
     * @param sprite The sprite to render to the screen
     */
    public static void drawRectWithTexture(PoseStack poseStack, float x, float y, float width, float height, TextureAtlasSprite sprite) {
        drawRectWithTexture(poseStack, x, y, sprite.getU0(), sprite.getV0(), width, height, sprite.getU1() - sprite.getU0(), sprite.getV1() - sprite.getV0(), 1f, 1f);
    }

    /**
     * Draws a quad onto the screen with the specified parameters.
     *
     * @param x      The x position to start
     * @param y      The y position to start
     * @param u      The x position on the texture to start
     * @param v      The y position on the texture to start
     * @param width  The x size of the quad
     * @param height The y size of the quad
     */
    public static void drawRectWithTexture(PoseStack poseStack, float x, float y, float u, float v, float width, float height) {
        drawRectWithTexture(poseStack, x, y, u, v, width, height, width, height, 256f, 256f);
    }

    /**
     * Draws a quad onto the screen with the specified parameters.
     *
     * @param x             The x position to start
     * @param y             The y position to start
     * @param u             The x position on the texture to start
     * @param v             The y position on the texture to start
     * @param width         The x size of the quad
     * @param height        The y size of the quad
     * @param textureWidth  The x size of the selection area on the texture
     * @param textureHeight The y size on the selection area on the texture
     */
    public static void drawRectWithTexture(PoseStack poseStack, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        drawRectWithTexture(poseStack, x, y, u, v, width, height, textureWidth, textureHeight, 256f, 256f);
    }

    /**
     * Draws a quad onto the screen with the specified parameters.
     *
     * @param x             The x position to start
     * @param y             The y position to start
     * @param u             The x position on the texture to start
     * @param v             The y position on the texture to start
     * @param width         The x size of the quad
     * @param height        The y size of the quad
     * @param textureWidth  The x size of the selection area on the texture
     * @param textureHeight The y size on the selection area on the texture
     * @param sourceWidth   The width of the texture source
     * @param sourceHeight  The height of the texture source
     */
    public static void drawRectWithTexture(PoseStack poseStack, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, float sourceWidth, float sourceHeight) {
        drawRectWithTexture(begin(), poseStack, x, y, u, v, width, height, textureWidth, textureHeight, sourceWidth, sourceHeight);
        end();
    }

    /**
     * Begins the rendering of a chain of quads.
     *
     * @return The buffer to render into
     */
    public static VertexConsumer begin() {
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(GL_QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        return buffer;
    }

    /**
     * Ends the rendering of a chain of quads.
     */
    public static void end() {
        Tesselator.getInstance().end();
        zLevel = 0;
        resetColor();
    }

    /**
     * Draws a quad into the specified buffer for chain rendering.
     *
     * @param buffer The buffer being rendered into
     * @param x      The x position to start
     * @param y      The y position to start
     * @param width  The x size of the quad
     * @param height The y size of the quad
     * @param sprite The sprite to render to the screen
     */
    public static void drawRectWithTexture(VertexConsumer buffer, PoseStack poseStack, float x, float y, float width, float height, TextureAtlasSprite sprite) {
        drawRectWithTexture(buffer, poseStack, x, y, sprite.getU0(), sprite.getV0(), width, height, sprite.getU1() - sprite.getU0(), sprite.getV1() - sprite.getV0(), 1f, 1f);
    }

    /**
     * Draws a quad into the specified buffer for chain rendering.
     *
     * @param buffer The buffer being rendered into
     * @param x      The x position to start
     * @param y      The y position to start
     * @param u      The x position on the texture to start
     * @param v      The y position on the texture to start
     * @param width  The x size of the quad
     * @param height The y size of the quad
     */
    public static void drawRectWithTexture(VertexConsumer buffer, PoseStack poseStack, float x, float y, float u, float v, float width, float height) {
        drawRectWithTexture(buffer, poseStack, x, y, u, v, width, height, width, height, 256f, 256f);
    }

    /**
     * Draws a quad into the specified buffer for chain rendering.
     *
     * @param buffer        The buffer being rendered into
     * @param x             The x position to start
     * @param y             The y position to start
     * @param u             The x position on the texture to start
     * @param v             The y position on the texture to start
     * @param width         The x size of the quad
     * @param height        The y size of the quad
     * @param textureWidth  The x size of the selection area on the texture
     * @param textureHeight The y size on the selection area on the texture
     */
    public static void drawRectWithTexture(VertexConsumer buffer, PoseStack poseStack, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        drawRectWithTexture(buffer, poseStack, x, y, u, v, width, height, textureWidth, textureHeight, 256f, 256f);
    }

    /**
     * Draws a quad into the specified buffer for chain rendering.
     *
     * @param buffer        The buffer being rendered into
     * @param x             The x position to start
     * @param y             The y position to start
     * @param u             The x position on the texture to start
     * @param v             The y position on the texture to start
     * @param width         The x size of the quad
     * @param height        The y size of the quad
     * @param textureWidth  The x size of the selection area on the texture
     * @param textureHeight The y size on the selection area on the texture
     * @param sourceWidth   The width of the texture source
     * @param sourceHeight  The height of the texture source
     */
    public static void drawRectWithTexture(VertexConsumer buffer, PoseStack poseStack, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, float sourceWidth, float sourceHeight) {
        float scaleWidth = 1f / sourceWidth;
        float scaleHeight = 1f / sourceHeight;
        Matrix4f matrix4f = poseStack.last().pose();
        buffer.vertex(matrix4f, x, y + height, zLevel).color(red, green, blue, alpha).uv(u * scaleWidth, (v + textureHeight) * scaleHeight).endVertex();
        buffer.vertex(matrix4f, x + width, y + height, zLevel).color(red, green, blue, alpha).uv((u + textureWidth) * scaleWidth, (v + textureHeight) * scaleHeight).endVertex();
        buffer.vertex(matrix4f, x + width, y, zLevel).color(red, green, blue, alpha).uv((u + textureWidth) * scaleWidth, v * scaleHeight).endVertex();
        buffer.vertex(matrix4f, x, y, zLevel).color(red, green, blue, alpha).uv(u * scaleWidth, v * scaleHeight).endVertex();
    }

    /**
     * Draws a quad into the specified buffer for chain rendering.
     *
     * @param x        The center x position of the burst
     * @param y        The center y position of the burst
     * @param width    The x size of the burst
     * @param height   The y size of the burst
     * @param segments The number of beams to have
     */
    public static void drawSunburst(PoseStack poseStack, float x, float y, float width, float height, int segments) {
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(GL_TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        Matrix4f matrix4f = poseStack.last().pose();
        float burstAngleOffset = (float) (Math.PI * (1.0F / segments / 2F));
        for (int i = 0; i < segments; i++) {
            float angle = (float) (Math.PI * 2 * i / segments + Math.PI / 2);
            builder.vertex(matrix4f, x, y, zLevel).color(red, green, blue, alpha).endVertex();
            builder.vertex(matrix4f, x + Mth.cos(angle + burstAngleOffset) * width / 2.0F, y + Mth.sin(angle + burstAngleOffset) * height / 2.0F, zLevel).color(red, green, blue, alpha).endVertex();
            builder.vertex(matrix4f, x + Mth.cos(angle - burstAngleOffset) * width / 2.0F, y + Mth.sin(angle - burstAngleOffset) * height / 2.0F, zLevel).color(red, green, blue, alpha).endVertex();
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        end();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
     * Sets the color back to white.
     */
    public static void resetColor() {
        ShapeRenderer.red = 1.0F;
        ShapeRenderer.green = 1.0F;
        ShapeRenderer.blue = 1.0F;
        ShapeRenderer.alpha = 1.0F;
    }

    /**
     * Sets the z parameter for rendering.
     *
     * @param zLevel The new z value
     */
    public static void setZLevel(float zLevel) {
        ShapeRenderer.zLevel = zLevel;
    }

    /**
     * Sets the color values to the provided red, green, and blue. Should be from <code>0.0F</code> to <code>1.0F</code>.
     *
     * @param red   The new red value
     * @param green The new green value
     * @param blue  The new blue value
     * @param alpha The new alpha value
     */
    public static void setColor(float red, float green, float blue, float alpha) {
        ShapeRenderer.red = red;
        ShapeRenderer.green = green;
        ShapeRenderer.blue = blue;
        ShapeRenderer.alpha = alpha;
    }

    /**
     * Sets the color values to the provided color integer.
     *
     * @param color The four color values in the order of <code>0xRRGGBBAA</code>
     */
    public static void setColor(int color) {
        ShapeRenderer.red = ((color >> 16) & 0xff) / 255f;
        ShapeRenderer.green = ((color >> 8) & 0xff) / 255f;
        ShapeRenderer.blue = (color & 0xff) / 255f;
        ShapeRenderer.alpha = ((color >> 24) & 0xff) / 255f;
    }
}
