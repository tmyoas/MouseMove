

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.KeyTapGesture;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Gesture.State;

class SampleListener extends Listener {

	Vector mINDEX_DISTAL_TOP;
	MouseMover mMouseMover = new MouseMover();

	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}

	public void onDisconnect(Controller controller) {
		//Note: not dispatched when running in a debugger.
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information
		Frame frame = controller.frame();

		//Get hands
		for(Hand hand : frame.hands()) {

			// Get fingers
			for (Finger finger : hand.fingers()) {

				switch (finger.type()) {
				case TYPE_INDEX:

					//Get Bones
					for(Bone.Type boneType : Bone.Type.values()) {
						Bone bone = finger.bone(boneType);

						switch (bone.type()) {
						case TYPE_DISTAL:
							mINDEX_DISTAL_TOP = bone.prevJoint();
//							System.out.println("mINDEX_DISTAL_TOP : " + mINDEX_DISTAL_TOP);
							mMouseMover.mouseMove(mINDEX_DISTAL_TOP);
							break;

						default:
							break;
						}
					}

					break;

				default:
					break;
				}
			}
		}

		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);

			switch (gesture.type()) {
			case TYPE_CIRCLE:
				CircleGesture circle = new CircleGesture(gesture);

				// Calculate clock direction using the angle between circle normal and pointable
				String clockwiseness;
				if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
					// Clockwise if angle is less than 90 degrees
					clockwiseness = "clockwise";
				} else {
					clockwiseness = "counterclockwise";
				}

				// Calculate angle swept since last frame
				double sweptAngle = 0;
				if (circle.state() != State.STATE_START) {
					CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
					sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
				}

				System.out.println("  Circle id: " + circle.id()
						+ ", " + circle.state()
						+ ", progress: " + circle.progress()
						+ ", radius: " + circle.radius()
						+ ", angle: " + Math.toDegrees(sweptAngle)
						+ ", " + clockwiseness);
				break;
			case TYPE_SWIPE:
				SwipeGesture swipe = new SwipeGesture(gesture);
				System.out.println("  Swipe id: " + swipe.id()
						+ ", " + swipe.state()
						+ ", position: " + swipe.position()
						+ ", direction: " + swipe.direction()
						+ ", speed: " + swipe.speed());
				break;
			case TYPE_SCREEN_TAP:
				ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
				System.out.println("  Screen Tap id: " + screenTap.id()
						+ ", " + screenTap.state()
						+ ", position: " + screenTap.position()
						+ ", direction: " + screenTap.direction());
				break;
			case TYPE_KEY_TAP:
				KeyTapGesture keyTap = new KeyTapGesture(gesture);
				mMouseMover.mouseClick();
				System.out.println("  Key Tap id: " + keyTap.id()
						+ ", " + keyTap.state()
						+ ", position: " + keyTap.position()
						+ ", direction: " + keyTap.direction());
				break;
			default:
				System.out.println("Unknown gesture type.");
				break;
			}
		}

//		if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
//			System.out.println();
//		}
	}
}
