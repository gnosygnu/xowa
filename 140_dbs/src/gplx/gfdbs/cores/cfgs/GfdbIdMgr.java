package gplx.gfdbs.cores.cfgs;

import gplx.gfdbs.cores.txns.GfdbTxnSub;
import gplx.gfdbs.cores.wkrs.GfdbListWkr;
import gplx.gfdbs.cores.wkrs.GfdbListWkrUtl;
import gplx.objects.lists.GfoIndexedList;

public class GfdbIdMgr implements GfdbTxnSub {
	private final GfoIndexedList<String, GfdbCfgItm> regy = new GfoIndexedList<>();
	private final GfdbListWkr<String, GfdbCfgItm> dbListWkr;
	private boolean load = true;

	private static final String ID_MGR_PREFIX = "idMgr.";

	public GfdbIdMgr(GfdbCfgTbl coreCfgTbl) {
		this.dbListWkr = GfdbListWkrUtl.NewByTbl(regy, coreCfgTbl, coreCfgTbl.SelectWildcardCmd(), ID_MGR_PREFIX);
	}
	public void Reg(String tblName) {
		String key = ToCfgTblKey(tblName);
		GfdbCfgItm itm = new GfdbCfgItm().Ctor(key, "0");
		regy.Add(key, itm);
	}
	@Override public void WhenTxnSav() {
		dbListWkr.Save();
	}
	public int Next(String tblName) {
		if (load) {
			load = false;
			dbListWkr.Load();
		}
		GfdbCfgItm itm = regy.GetByOrFail(ToCfgTblKey(tblName));
		return itm.ValAdd(1);
	}
	private String ToCfgTblKey(String tblName) {return ID_MGR_PREFIX + tblName;}
}
