package it.polimi.tiw.bancaservlet.beans;

public class Counter {
	private int count;
	
	public Counter() {
		count = 0;
	}

	public int incrementeAndGet() {
		return count;
	}
	
	public int get()
    {
        return count;
    }

    public void clear()
    {
        count = 0;
    }
}
