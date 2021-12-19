package gplx.gfdbs.cores.txns;

import gplx.dbs.Db_conn;
import gplx.gfdbs.cores.GfdbCloseAble;
import gplx.types.commons.lists.GfoListBase;

public class GfdbTxnMgr {
    private final GfdbCloseAble dbMgr;
    private final Db_conn conn;
    private final GfoListBase<GfdbTxnSub> subs = new GfoListBase<>();
    private boolean enabled = true;
    private int saveEveryN = 1000;
    private int n;
    public GfdbTxnMgr(GfdbCloseAble dbMgr, Db_conn conn) {
        this.dbMgr = dbMgr;
        this.conn = conn;
    }
    public void SaveEveryNSet(int v) {this.saveEveryN = v;}
    public void EnabledSet(boolean v) {this.enabled = v;}
    public GfoListBase<GfdbTxnSub> Subs() {return subs;}
    public GfdbTxnMgr Bgn(String name) {
        if (enabled) {
            conn.Txn_bgn(name);
        }
        return this;
    }
    public GfdbTxnMgr RegSub(GfdbTxnSub... subArray) {
        for (GfdbTxnSub sub : subArray)
            subs.Add(sub);
        return this;
    }
    public void Cancel() {
        conn.Txn_cxl();
    }
    public void SaveCheck() {
        if ((n++ % saveEveryN) == 0) {
            this.SaveForce();
        }
    }
    public void SaveForce() {
        this.SubsSave();
        if (enabled) {
            conn.Txn_sav();
        }
    }
    public void End() {
        this.SubsSave();
        if (enabled) {
            conn.Txn_end();
        }
        dbMgr.Close();
        n = 0;
    }
    private void SubsSave() {
        for (GfdbTxnSub sub : subs) {
            sub.WhenTxnSav();
        }
    }
}
