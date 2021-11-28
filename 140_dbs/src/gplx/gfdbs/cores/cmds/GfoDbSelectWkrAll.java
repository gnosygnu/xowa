package gplx.gfdbs.cores.cmds;

import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.gfdbs.cores.GfdbMeta;

public class GfoDbSelectWkrAll<I, M extends GfdbMeta<I>> implements GfoDbSelectWkr<I> {
	private final M meta;
	public GfoDbSelectWkrAll(M meta) {
		this.meta = meta;
	}
	@Override public Db_rdr NewRdr(Db_conn conn, Object... args) {
        Db_stmt stmt = conn.Stmt_select_all(meta.TblName(), meta.Flds());
        return stmt.Exec_select__rls_auto();
	}
	@Override public void ItmLoad(Db_rdr rdr, I itm) {meta.LoadItm(rdr, itm);}
}
