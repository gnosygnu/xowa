package gplx.gfdbs.cores;

public interface GfdbItm<K, I> {
	void CtorByItm(I itm);
	GfdbState DbState(); void DbStateSet(GfdbState v);
	K ToPkey();
}
