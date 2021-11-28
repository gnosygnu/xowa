package gplx.gfdbs.cores.cfgs;

import gplx.dbs.Db_conn;
import gplx.dbs.Db_crt_;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_qry_;
import gplx.dbs.Db_rdr;
import gplx.gfdbs.cores.cmds.GfoDbSelectWkr;
import gplx.dbs.engines.sqlite.Sqlite_engine_;

public class GfdbCfgSelectWildcardWkr implements GfoDbSelectWkr<GfdbCfgItm> {
	private final GfdbCfgMeta meta;
    public GfdbCfgSelectWildcardWkr(GfdbCfgMeta meta) {
        this.meta = meta;
    }
	@Override public Db_rdr NewRdr(Db_conn conn, Object... args) {
    	Db_qry qry = Db_qry_.select_()
			.Cols_all_()
			.From_(meta.TblName())
			.Where_
			(
				Db_crt_.New_like(meta.Key(), "")
			);
    	String keyPattern = (String)args[0];
		return conn.Stmt_new(qry).Clear()
			.Crt_str(meta.Key(), keyPattern + Sqlite_engine_.Wildcard_str)
			.Exec_select__rls_auto();
	}
	@Override public void ItmLoad(Db_rdr rdr, GfdbCfgItm itm) {
		meta.LoadItm(rdr, itm);
	}
}
