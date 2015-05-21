package com.whjz.io;

public class UartParam {
	private int Speed;
	private int nBit;
	private char nEve;
	private int nStop;

	public int getSpeed() {
		return Speed;
	}

	public void setSpeed(int speed) {
		Speed = speed;
	}

	public int getnBit() {
		return nBit;
	}

	public void setnBit(int nBit) {
		this.nBit = nBit;
	}

	public char getnEve() {
		return nEve;
	}

	public void setnEve(char nEve) {
		this.nEve = nEve;
	}

	public int getnStop() {
		return nStop;
	}

	public void setnStop(int nStop) {
		this.nStop = nStop;
	}

	public UartParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UartParam(int speed, int nBit, char nEve, int nStop) {
		super();
		Speed = speed;
		this.nBit = nBit;
		this.nEve = nEve;
		this.nStop = nStop;
	}

}
