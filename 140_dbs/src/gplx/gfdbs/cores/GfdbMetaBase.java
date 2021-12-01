package gplx.gfdbs.cores;

import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldList;

public abstract class GfdbMetaBase<I> implements GfdbMeta<I> {
    public GfdbMetaBase(String tblName) {
        this.tblName = tblName;
        CtorFlds();
        this.pkeys = CtorPkeys();
    }
    @Override public String TblName() {return tblName;} private final String tblName;
    @Override public DbmetaFldList Flds() {return flds;} protected final DbmetaFldList flds = new DbmetaFldList();
    @Override public String[] Pkeys() {return pkeys;} protected String[] pkeys;
    @Override public abstract void SaveStmtPkeys(Db_stmt stmt, I itm);
    @Override public abstract void SaveStmtVals(GfdbState state, Db_stmt stmt, I itm);
    protected abstract void CtorFlds();
    protected abstract String[] CtorPkeys();
}
