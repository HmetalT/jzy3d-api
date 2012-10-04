package org.jzy3d.chart.controllers.thread.camera;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.camera.AbstractCameraController;
import org.jzy3d.maths.Coord2d;


/** The ViewAnimatedController provides a Thread for controlling the Camera and make
 * it turn around the view point along its the azimuth dimension.
 * @author Martin Pernollet
 *
 */
public class CameraThreadController extends AbstractCameraController implements Runnable {
	
	public CameraThreadController(){
	}
	
	public CameraThreadController(Chart chart){
		register(chart);
	}
	
	public void dispose(){
		stop();
		super.dispose();
	}
	
	/**************************************************************/
	
	/** Start the animation.*/
	public void start() {
		if (process==null) {
			process=new Thread(this);
			process.setName("Embedded by ChartThreadController");
			process.start();
		}
	}

	/** Stop the animation.*/
	public void stop() {
		if (process!=null) {
			process.interrupt();
			process=null;
		}
	}

	/** Run the animation.*/
	public void run() {	
		move = new Coord2d(step,0);

		while (process!=null) {
			try {
				rotate( move );
				Thread.sleep(sleep);
			}
			catch (InterruptedException e){
				process = null;	
			}
		}
	}
	
	public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }



    /**************************************************************/
	
	private Coord2d    move;
	
	private Thread process  = null;
	private int    sleep    = 1;//1000/25; // nb milisecond wait between two frames
	private float  step     = 0.0005f;
}