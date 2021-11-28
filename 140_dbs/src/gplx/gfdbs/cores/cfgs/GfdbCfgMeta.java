package gplx.gfdbs.cores.cfgs;

import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.gfdbs.cores.GfdbState;
import gplx.gfdbs.cores.GfdbMetaBase;

public class GfdbCfgMeta extends GfdbMetaBase<GfdbCfgItm> {
	public GfdbCfgMeta() {super(GfdbCfgMeta.TBL_CORE_CFG);}
	public String Key() {return key;} private String key;
	public String Val() {return val;} private String val;
	@Override protected void CtorFlds() {
		this.key = flds.Add_str_pkey("cfg_key", 1024);
		this.val = flds.Add_str("cfg_val", 1024);
	}
	@Override protected String[] CtorPkeys() {return new String[] {key};}
	@Override public void SaveStmtPkeys(Db_stmt stmt, GfdbCfgItm itm) {stmt.Crt_str(this.Key(), itm.Key());}
	@Override public void SaveStmtVals(GfdbState state, Db_stmt stmt, GfdbCfgItm itm) {
    	if (state == GfdbState.Insert) {
			stmt.Val_str(this.Key(), itm.Key());
    	}
		stmt.Val_str(this.Val(), itm.Val());
	}
	@Override public GfdbCfgItm NewByDb() {return new GfdbCfgItm();}
	@Override public void LoadItm(Db_rdr rdr, GfdbCfgItm itm) {
	    itm.Ctor
	    	( rdr.Read_str(key)
	        , rdr.Read_str(val)
	        );
	}
	public static final String TBL_CORE_CFG = "core_cfg";
}
