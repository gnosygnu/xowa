package gplx.gfdbs.cores.cfgs;

import gplx.dbs.Db_conn;
import gplx.gfdbs.cores.GfdbTbl;
import gplx.gfdbs.cores.cmds.GfdbSelectCmd;
import gplx.gfdbs.cores.txns.GfdbTxnMgr;

public class GfdbCfgTbl extends GfdbTbl<GfdbCfgItm, GfdbCfgMeta> {
	public GfdbCfgTbl(GfdbTxnMgr txnMgr, Db_conn conn, GfdbCfgMeta meta) {
		super(conn, txnMgr, meta);
		this.selectWildcardCmd = new GfdbSelectCmd<>(conn, meta, new GfdbCfgSelectWildcardWkr(meta));
	}
	public GfdbSelectCmd<GfdbCfgItm> SelectWildcardCmd() {return selectWildcardCmd;} private final GfdbSelectCmd<GfdbCfgItm> selectWildcardCmd;
	@Override public void Close() {
		super.Close();
		selectWildcardCmd.Close();
	}
}
