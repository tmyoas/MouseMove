import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.leapmotion.leap.Vector;


public class MouseMover {

	Vector mINDEX_DISTAL_TOP;

	int mNextPixelX, mNextPixelY;
	int mDispPixelX = 1600;
	int mDispPixelY = 900;
	double mDispmmX = 294.44;
	double mDispmmY = 165.62;
	Robot mRobot;

	public MouseMover(){
		try {
			mRobot = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
			return;
		}
	}

	public void mouseMove(Vector INDEX_DISTAL_TOP){
		if(INDEX_DISTAL_TOP != null && this.mINDEX_DISTAL_TOP != INDEX_DISTAL_TOP){
			this.mINDEX_DISTAL_TOP = INDEX_DISTAL_TOP;
//			System.out.println("mINDEX_DISTAL_TOP : " + mINDEX_DISTAL_TOP);
	
		mNextPixelX = mDispPixelX - (int)(mDispPixelX * (mINDEX_DISTAL_TOP.getX() / mDispmmX + 0.5));
		mNextPixelY = (int)(mDispPixelY * (mINDEX_DISTAL_TOP.getZ() / mDispmmY + 0.5));
		
		mRobot.mouseMove(mNextPixelX, mNextPixelY);
		System.out.println("X : " + mNextPixelX + " Y : " + mNextPixelY);
		}
	}
	
	public void mouseClick(){
		mRobot.mousePress(InputEvent.BUTTON1_MASK);
		mRobot.mouseRelease(InputEvent.BUTTON1_MASK);
		System.out.println("Click");
	}

}
