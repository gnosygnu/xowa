package gplx.gfdbs.cores.cmds;

import gplx.Err_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.Dbmeta_fld_itm;
import gplx.gfdbs.cores.GfdbMeta;
import gplx.dbs.stmts.Db_stmt_arg_list;

public class GfoDbSelectWkrOne<I, M extends GfdbMeta<I>> implements GfoDbSelectWkr<I> {
	private final M meta;
	private final String[] pkeyCols;
	public GfoDbSelectWkrOne(M meta, String... pkeyCols) {
		this.meta = meta;
		this.pkeyCols = pkeyCols;
	}
	@Override public Db_rdr NewRdr(Db_conn conn, Object... pkeyVals) {
        Db_stmt stmt = conn.Stmt_select(meta.TblName(), meta.Flds(), pkeyCols);
        if (pkeyCols.length != pkeyVals.length) throw Err_.new_wo_type("pkey lengths don't match");
        for (int i = 0; i < pkeyCols.length; i++) {
            String pkeyCol = pkeyCols[i];
            Dbmeta_fld_itm pkeyFld = meta.Flds().Get_by(pkeyCol);
            Db_stmt_arg_list.Fill_crt(stmt, pkeyFld.Type().Tid_ansi(), pkeyCol, pkeyVals[i]);
        }

        return stmt.Exec_select__rls_auto();
	}
	@Override public void ItmLoad(Db_rdr rdr, I itm) {meta.LoadItm(rdr, itm);}
}
