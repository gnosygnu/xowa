package gplx.gfdbs.cores;

public enum GfdbState {
    Noop,  // = 0
    Insert,// = 1
    Update,// = 2
    Delete,// = 3
    ;
	public int ToDb() {return this.ordinal();}
	public static GfdbState ByDb(int id) {return GfdbState.values()[id];}
}
