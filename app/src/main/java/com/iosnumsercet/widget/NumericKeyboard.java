package com.iosnumsercet.widget;

import cn.trinea.android.common.util.SystemUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.iosnumsercet.R;


public class NumericKeyboard extends View {
	private int screen_width = 0;
	private int first_x = 0;
	private float first_y = 0;
	private float[] xs = new float[3];
	private float[] ys = new float[4];
	private float circle_x,circle_y;
	private int number = -1;//���������
	private OnNumberClick onNumberClick;

	private int type = -1;


	public NumericKeyboard(Context context) {
		super(context);
		initData(context);// ��ʼ�����
	}

	public NumericKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData(context);// ��ʼ�����
	}
	

	public void setOnNumberClick(OnNumberClick onNumberClick){
		this.onNumberClick = onNumberClick;
	}


	private void initData(Context context) {

		screen_width = SystemUtils.getSystemDisplay(context)[0];

		first_x = screen_width / 4;

		first_y = (SystemUtils.getSystemDisplay(context)[1] - SystemUtils.getSystemDisplay(context)[1]/3) / 4;

		xs[0]=first_x+10; xs[1]=first_x*2+10; xs[2]=first_x*3+10;

		ys[0]=40+first_y-15; ys[1]=40+first_y+first_x-15; 
		ys[2]=40+first_y+first_x*2-15; ys[3]=40+first_y+first_x*3-15;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(40);

		canvas.drawText("1", first_x, 40 + first_y, paint);
		canvas.drawText("2", first_x * 2, 40 + first_y, paint);
		canvas.drawText("3", first_x * 3, 40 + first_y, paint);

		canvas.drawText("4", first_x, 40 + first_y + first_x, paint);
		canvas.drawText("5", first_x * 2, 40 + first_y + first_x, paint);
		canvas.drawText("6", first_x * 3, 40 + first_y + first_x, paint);

		canvas.drawText("7", first_x, 40 + first_y + first_x * 2, paint);
		canvas.drawText("8", first_x * 2, 40 + first_y + first_x * 2, paint);
		canvas.drawText("9", first_x * 3, 40 + first_y + first_x * 2, paint);

		canvas.drawText("0", first_x * 2, 40 + first_y + first_x * 3, paint);

		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);

		paint.setStyle(Paint.Style.STROKE);

		canvas.drawCircle(first_x+10, 40 + first_y - 15, 50, paint);
		canvas.drawCircle(first_x*2+10, 40 + first_y - 15, 50, paint);
		canvas.drawCircle(first_x*3+10, 40 + first_y - 15, 50, paint);

		canvas.drawCircle(first_x+10, 40 + first_y + first_x - 15, 50, paint);
		canvas.drawCircle(first_x*2+10, 40 + first_y + first_x - 15, 50, paint);
		canvas.drawCircle(first_x*3+10, 40 + first_y + first_x - 15, 50, paint);

		canvas.drawCircle(first_x+10, 40 + first_y + first_x * 2 - 15, 50, paint);
		canvas.drawCircle(first_x*2+10, 40 + first_y + first_x * 2 - 15, 50, paint);
		canvas.drawCircle(first_x*3+10, 40 + first_y + first_x * 2 - 15, 50, paint);

		canvas.drawCircle(first_x*2+10, 40 + first_y + first_x * 3 - 15, 50, paint);
	

		if(circle_x > 0 && circle_y > 0){
			if(type == 0){
				paint.setColor(Color.YELLOW);
				canvas.drawCircle(circle_x, circle_y, 50, paint);//����Բ
			}else if(type == 1){//����ˢ��
				paint.setColor(Color.WHITE);//���û�����ɫ
				canvas.drawCircle(circle_x, circle_y, 50, paint);//
				circle_x = 0; circle_y = 0;
			}
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN://����
				float x = event.getX();
				float y = event.getY();
				handleDown(x, y);
				return true;
			case MotionEvent.ACTION_UP://����
				type = 1;
				invalidate();
				if(onNumberClick != null && number != -1){
					onNumberClick.onNumberReturn(number);
				}
				setDefault();
				sendAccessEvent(R.string.numeric_keyboard_up);
				return true;
			case MotionEvent.ACTION_CANCEL:
				setDefault();
				return true;
			default:
				break;
		}
		return false;
	}

	private void setDefault(){
		circle_x = 0; circle_y = 0;
		type = -1;
		number = -1;
		sendAccessEvent(R.string.numeric_keyboard_cancel);
	}

	private void sendAccessEvent(int resId) {
		setContentDescription(getContext().getString(resId));
		sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
		setContentDescription(null);
	}
	

	private void handleDown(float x, float y){
		if(xs[0] - 50 <= x && x <= xs[0] + 50){//��һ��
			circle_x = xs[0];
			if(ys[0] - 50 <= y && ys[0] + 50 >= y){//��1��
				circle_y = ys[0];
				number = 1;//���õ��������
			}else if(ys[1] - 50 <= y && ys[1] + 50 >= y){//��2��
				circle_y = ys[1];
				number = 4;//���õ��������
			}else if(ys[2] - 50 <= y && ys[2] + 50 >= y){//��3��
				circle_y = ys[2];
				number = 7;//���õ��������
			}
		}else if(xs[1] - 50 <= x && x <= xs[1] + 50){//��2���
			circle_x = xs[1];
			if(ys[0] - 50 <= y && ys[0] + 50 >= y){//��1��
				circle_y = ys[0];
				number = 2;//���õ��������
			}else if(ys[1] - 50 <= y && ys[1] + 50 >= y){//��2��
				circle_y = ys[1];
				number = 5;
			}else if(ys[2] - 50 <= y && ys[2] + 50 >= y){
				circle_y = ys[2];
				number = 8;
			}else if(ys[3] - 50 <= y && ys[3] + 50 >= y){
				circle_y = ys[3];
				number = 0;
			}
		}else if(xs[2] - 50 <= x && x <= xs[2] + 50){
			circle_x = xs[2];
			if(ys[0] - 50 <= y && ys[0] + 50 >= y){
				circle_y = ys[0];
				number = 3;
			}else if(ys[1] - 50 <= y && ys[1] + 50 >= y){
				circle_y = ys[1];
				number = 6;
			}else if(ys[2] - 50 <= y && ys[2] + 50 >= y){
				circle_y = ys[2];
				number = 9;
			}
		}
		sendAccessEvent(R.string.numeric_keyboard_down);
		type = 0;
		invalidate();
	}
	

	public interface OnNumberClick{

		public void onNumberReturn(int number);
	}
}
