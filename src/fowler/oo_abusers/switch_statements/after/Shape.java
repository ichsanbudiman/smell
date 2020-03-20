package fowler.oo_abusers.switch_statements.after;

public abstract class Shape {
	protected int size;
		
	public Shape(int size) {
		super();
		this.size = size;
	}
	
	public abstract void print();
	public abstract int charNeeded();
}
