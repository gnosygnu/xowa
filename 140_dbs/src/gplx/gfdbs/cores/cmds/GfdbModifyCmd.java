package gplx.gfdbs.cores.cmds;

import gplx.dbs.Db_conn;
import gplx.dbs.Db_stmt;
import gplx.dbs.Db_stmt_;
import gplx.gfdbs.cores.GfdbCloseAble;
import gplx.gfdbs.cores.GfdbMeta;
import gplx.gfdbs.cores.txns.GfdbTxnMgr;
import gplx.gfdbs.cores.GfdbState;

public class GfdbModifyCmd<I> implements GfdbCloseAble {
    private final Db_conn conn;
    private final GfdbTxnMgr txnMgr;
    private Db_stmt stmt;
    public GfdbModifyCmd(Db_conn conn, GfdbTxnMgr txnMgr, GfdbMeta<I> meta, GfdbState dbState) {
        this.conn = conn;
        this.txnMgr = txnMgr;
        this.meta = meta;
        this.dbState = dbState;
    }
    public GfdbMeta<I> Meta() {return meta;} private final GfdbMeta<I> meta;
    public GfdbState DbState() {return dbState;} private final GfdbState dbState;
    public void ExecObject(Object o) {Exec((I)o);}
    public void Exec(I itm) {
        if (stmt == null) {
            StmtMake();
        }
        else {
            stmt.Clear();
        }
        if (dbState != GfdbState.Delete) {
            meta.SaveStmtVals(dbState, stmt, itm);
        }
        StmtWhere(itm);
        StmtExec();
        txnMgr.SaveCheck();
    }
    private void StmtMake() {
        switch (dbState) {
            case Insert: stmt = conn.Stmt_insert(meta.TblName(), meta.Flds()); break;
            case Delete: stmt = conn.Stmt_delete(meta.TblName(), meta.Pkeys()); break;
            case Update: stmt = conn.Stmt_update_exclude(meta.TblName(), meta.Flds(), meta.Pkeys()); break;
        }
    }
    private void StmtWhere(I itm) {
        switch (dbState) {
            case Delete:
            case Update:
                meta.SaveStmtPkeys(stmt, itm);
                break;
        }
    }
    private void StmtExec() {
        switch (dbState) {
            case Insert: stmt.Exec_insert(); break;
            case Update: stmt.Exec_update(); break;
            case Delete: stmt.Exec_delete(); break;
        }
    }
    @Override public void Close() {
        stmt = Db_stmt_.Rls(stmt);
    }
}
