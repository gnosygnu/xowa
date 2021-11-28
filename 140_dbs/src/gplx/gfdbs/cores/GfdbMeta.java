package gplx.gfdbs.cores;

import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.Dbmeta_fld_list;

public interface GfdbMeta<I> extends GfdbItmFactory<I> {
    String TblName();
    Dbmeta_fld_list Flds();
    String[] Pkeys();
    void SaveStmtPkeys(Db_stmt stmt, I itm);
    void SaveStmtVals(GfdbState state, Db_stmt stmt, I itm);
    void LoadItm(Db_rdr rdr, I itm);
}
