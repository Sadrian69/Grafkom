import Engine.*;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window= new Window(1200, 1200, "yes");
    private ArrayList<Rectangle> bgs = new ArrayList<>();
    private ArrayList<Object2d> tops = new ArrayList<>();
    private ArrayList<Rectangle> walls = new ArrayList<>();
    private ArrayList<Circle> moons = new ArrayList<>();
    private ArrayList<Circle> smokes = new ArrayList<>();


    public void run() {
        init();
        loop();

        // Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init(){
        window.init();
        GL.createCapabilities(); // don't put below stuff

        // code
        bgs.add(new Rectangle( //sky
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-1.0f, 1.0f, 0.0f),
                                new Vector3f(1.0f, 1.0f, 0.0f),
                                new Vector3f(1.0f, -1.0f, 0.0f),
                                new Vector3f(-1.0f, -1.0f, 0.0f)
                        )
                ),
                new Vector4f(0.2f, 0.2f, 0.9f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        bgs.add(new Rectangle( //ground
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-1.0f, -0.6f, 0.0f),
                                new Vector3f(1.0f, -0.6f, 0.0f),
                                new Vector3f(1.0f, -1.0f, 0.0f),
                                new Vector3f(-1.0f, -1.0f, 0.0f)
                        )
                ),
                new Vector4f(0.2f, 0.5f, 0.2f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        bgs.add(new Rectangle( //roof1
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.0f, 0.0f),
                                new Vector3f(0.5f, 0.0f, 0.0f),
                                new Vector3f(0.3f, -0.3f, 0.0f),
                                new Vector3f(-0.7f, -0.3f, 0.0f)
                        )
                ),
                new Vector4f(0.8f, 0.2f, 0.2f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        tops.add(new Object2d(//triangle
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.47f, -0.04f, 0.0f),
                                new Vector3f(-0.6f, -0.25f, 0.0f),
                                new Vector3f(-0.3f, -0.25f, 0.0f)
                        )
                ),
                new Vector4f(0.67f, 0.5f, 0.16f, 1.0f)
        ));
        walls.add(new Rectangle(//wall
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.6f, -0.65f, 0.0f),
                                new Vector3f(-0.6f, -0.25f, 0.0f),
                                new Vector3f(0.6f, -0.25f, 0.0f),
                                new Vector3f(0.6f, -0.65f, 0.0f)
                        )
                ),
                new Vector4f(0.67f, 0.5f, 0.16f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        walls.add(new Rectangle(//roof2
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.0f, 0.0f),
                                new Vector3f(0.5f, 0.0f, 0.0f),
                                new Vector3f(0.7f, -0.3f, 0.0f),
                                new Vector3f(-0.3f, -0.3f, 0.0f)
                        )
                ),
                new Vector4f(0.8f, 0.2f, 0.2f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        walls.add(new Rectangle(//chimney pipe
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.35f, -0.1f, 0.0f),
                                new Vector3f(0.35f, 0.1f, 0.0f),
                                new Vector3f(0.42f, 0.1f, 0.0f),
                                new Vector3f(0.42f, -0.1f, 0.0f)
                        )
                ),
                new Vector4f(0.67f, 0.5f, 0.16f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        walls.add(new Rectangle(//chimney hole
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(0.33f, 0.1f, 0.0f),
                                new Vector3f(0.33f, 0.14f, 0.0f),
                                new Vector3f(0.44f, 0.14f, 0.0f),
                                new Vector3f(0.44f, 0.1f, 0.0f)
                        )
                ),
                new Vector4f(0.65f, 0.24f, 0.1f, 1.0f),
                Arrays.asList(0,1,2,3)
        ));
        moons.add(new Circle( //actual moon
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                Circle.createCircle(-0.7f, 0.7f, 0.15f, 0.15f)
                ,
                new Vector4f(1.0f, 1.0f, 0.0f, 1.0f)
        ));
        ;
        moons.add(new Circle( //darkside
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                Circle.createCircle(-0.63f, 0.7f, 0.15f, 0.15f)
                ,
                new Vector4f(0.2f, 0.2f, 0.9f, 1.0f)
        ));
    }
    private void loop() {
        while (window.isOpen()){
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();

            // code
            for(Rectangle bg: bgs){
                bg.draw();
            }
            for(Object2d top: tops){
                top.draw();
            }
            for(Rectangle wall: walls){
                wall.draw();
            }
            for(Circle moon: moons){
                moon.draw();
            }
            for(Circle smoke: smokes){
                smoke.draw();
            }


            // restore state
            glDisableVertexAttribArray(0);
            // poll for window events
            // the key callback above will only be
            // invoked during this call
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
