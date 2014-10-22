import java.io.IOException;

import com.leapmotion.leap.Controller;

public class LeapController implements Runnable{
	
	SampleListener listener = new SampleListener();
	MouseMover mMouseMover = new MouseMover();
	
	public LeapController(){
		
		init();
		
		Controller controller = new Controller();
		
		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		controller.setPolicyFlags(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
		controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);

		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the sample listener when done
		controller.removeListener(listener);
	}
	
	public void init(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
//		while(true){
//			mMouseMover.mouseMove(listener.mINDEX_DISTAL_TOP);
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	
}
