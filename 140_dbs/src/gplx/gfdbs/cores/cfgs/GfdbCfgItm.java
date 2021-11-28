package gplx.gfdbs.cores.cfgs;

import gplx.Type_;
import gplx.gfdbs.cores.GfdbState;
import gplx.gfdbs.cores.GfdbItm;

public class GfdbCfgItm implements GfdbItm<String, GfdbCfgItm> {
    @Override public void CtorByItm(GfdbCfgItm itm) {this.Ctor(itm.key, itm.val);}
    public GfdbCfgItm Ctor(String key, String val) {
        this.key = key;
        this.val = val;
        return this;
    }
    @Override public GfdbState DbState() {return dbState;} private GfdbState dbState = GfdbState.Noop; @Override public void DbStateSet(GfdbState v) {this.dbState = v;}
    @Override public String ToPkey() {
        return key;
    }
	public String Key() {return key;} private String key;
	public String Val() {return val;} private String val;
	public void ValSet(String v) {this.val = v; this.dbState = GfdbState.Update;}
	public int ValAdd(int add) {
	    Integer valInt = Integer.parseInt(val);
		valInt += add;
		this.ValSet(valInt.toString());
		return valInt;
	}
    @Override public String toString() {return Type_.SimpleName_by_obj(this) + toStringItm();}
    protected String toStringItm() {
        return "|dbState=" + dbState.name()
            +  "|key=" + key
            +  "|val=" + val
            ;
    }
}
