package com.arny.lubereckiy.ui.graphics;

import com.arny.lubereckiy.models.Flat;
public class FlatView {
	private float left;
	private float top;
	private float right;
	private float bottom;
	private int floor;
	private int flatCnt;
	private Flat flat;

	public FlatView() {
	}

	public FlatView(float left, float top, float right, float bottom, int floor, int flatCnt) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.floor = floor;
		this.flatCnt = flatCnt;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public boolean contains(int x, int y) {
		return left < right && top < bottom  // check for empty first
				&& x >= left && x < right && y >= top && y < bottom;
	}

	public int centerX() {
		return (((int) left + (int) right) >> 1);
	}

	public int centerY() {
		return ((int) top + (int) bottom) >> 1;
	}

	public int getFlatCnt() {
		return flatCnt;
	}

	public void setFlatCnt(int flatCnt) {
		this.flatCnt = flatCnt;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Flat getFlat() {
		return flat;
	}

	public void setFlat(Flat flat) {
		this.flat = flat;
	}
}
