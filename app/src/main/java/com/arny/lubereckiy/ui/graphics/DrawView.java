package com.arny.lubereckiy.ui.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.KorpusSection;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View{
	private int height;
	private int width;
	private KorpusSection  section;
	Paint paint = new Paint();
	private ArrayList<FlatView> flats;

	public DrawView(Context context, KorpusSection section, int width, int height) {
        super(context);
        this.section = section;
        this.width = width;
        this.height = height;
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Compute the height required to render the view
		// Assume Width will always be MATCH_PARENT.
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = 3000; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
		setMeasuredDimension(width, height);

//		setMeasuredDimension(measureWidth(widthMeasureSpec),
//				measureHeight(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * @param measureSpec A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		//This is because of background image in relativeLayout, which is 1000*1000px
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			// We were told how big to be
			result = specSize;
		}

		return result;
	}

	/**
	 * Determines the height of this view
	 * @param measureSpec A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		//This is because of background image in relativeLayout, which is 1000*1000px
		measureSpec = 1001;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);


		if (specMode == MeasureSpec.UNSPECIFIED) {
			// Here we say how Heigh to be
			result = specSize;
		}
		return result;
	}

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLUE);
	    flats = Local.getSectionRects(canvas, section, paint,width,height);
    }

	public ArrayList<FlatView> getFlats() {
		return flats;
	}
}