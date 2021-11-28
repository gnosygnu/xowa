package gplx.gfdbs.cores;

import gplx.Err_;
import gplx.dbs.Db_conn;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.qrys.Db_qry_delete;
import gplx.gfdbs.cores.cmds.GfdbModifyCmd;
import gplx.gfdbs.cores.cmds.GfdbSelectCmd;
import gplx.gfdbs.cores.cmds.GfoDbSelectWkrAll;
import gplx.gfdbs.cores.txns.GfdbTxnMgr;

public class GfdbTbl<I, M extends GfdbMeta<I>> implements GfdbCloseAble {
    protected final Db_conn conn;
    protected final M meta;
    public GfdbTbl(Db_conn conn, GfdbTxnMgr txnMgr, M meta) {
        this.conn = conn;
        this.meta = meta;
        this.insertCmd = new GfdbModifyCmd<>(conn, txnMgr, meta, GfdbState.Insert);
        this.updateCmd = new GfdbModifyCmd<>(conn, txnMgr, meta, GfdbState.Update);
        this.deleteCmd = new GfdbModifyCmd<>(conn, txnMgr, meta, GfdbState.Delete);
        this.selectAllCmd = new GfdbSelectCmd<>(conn, meta, new GfoDbSelectWkrAll<>(meta));
    }
	public M Meta() {return meta;}
    public void CreateTbl() {
        conn.Meta_tbl_create(Dbmeta_tbl_itm.New(meta.TblName(), meta.Flds(), Dbmeta_idx_itm.new_unique_by_tbl(meta.TblName(), "pkey", meta.Pkeys())));
    }
	public GfdbModifyCmd<I> InsertCmd() {return insertCmd;} private final GfdbModifyCmd<I> insertCmd;
	public GfdbModifyCmd<I> UpdateCmd() {return updateCmd;} private final GfdbModifyCmd<I> updateCmd;
	public GfdbModifyCmd<I> DeleteCmd() {return deleteCmd;} private final GfdbModifyCmd<I> deleteCmd;
	public GfdbSelectCmd<I> SelectAllCmd() {return selectAllCmd;} private final GfdbSelectCmd<I> selectAllCmd;
    public GfdbModifyCmd<I> ModifyCmd(GfdbState dbState) {
        switch (dbState) {
            case Insert: return insertCmd;
            case Update: return updateCmd;
            case Delete: return deleteCmd;
            default: throw Err_.new_unhandled_default(dbState);
        }
    }
	public void DeleteAll() {conn.Stmt_new(Db_qry_delete.new_all_(meta.TblName())).Exec_delete();}
    @Override public void Close() {
        insertCmd.Close();
        updateCmd.Close();
        deleteCmd.Close();
        selectAllCmd.Close();
    }
}
