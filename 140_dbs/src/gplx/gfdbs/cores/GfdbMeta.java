package gplx.gfdbs.cores;

import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldList;

public interface GfdbMeta<I> extends GfdbItmFactory<I> {
    String TblName();
    DbmetaFldList Flds();
    String[] Pkeys();
    void SaveStmtPkeys(Db_stmt stmt, I itm);
    void SaveStmtVals(GfdbState state, Db_stmt stmt, I itm);
    void LoadItm(Db_rdr rdr, I itm);
}
