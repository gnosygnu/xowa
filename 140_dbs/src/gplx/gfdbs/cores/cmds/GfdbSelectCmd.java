package gplx.gfdbs.cores.cmds;

import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.gfdbs.cores.GfdbCloseAble;
import gplx.gfdbs.cores.GfdbItmFactory;
import gplx.types.commons.lists.GfoListBase;

public class GfdbSelectCmd<I> implements GfdbCloseAble, GfdbSelectList<I> {
	private final Db_conn conn;
    private final GfdbItmFactory<I> itmFactory;
    private final GfoDbSelectWkr<I> wkr;
    public GfdbSelectCmd(Db_conn conn, GfdbItmFactory<I> itmFactory, GfoDbSelectWkr<I> wkr) {
    	this.conn = conn;
        this.itmFactory = itmFactory;
        this.wkr = wkr;
    }
	public GfoListBase<I> Select(Object... args) {
		GfoListBase<I> list = new GfoListBase<>();
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = wkr.NewRdr(conn, args);
			while (rdr.Move_next()) {
				I itm = itmFactory.NewByDb();
				wkr.ItmLoad(rdr, itm);
				list.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return list;
	}
	public I SelectOne(Object... args) {
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = wkr.NewRdr(conn, args);
			if (rdr.Move_next()) {
				I itm = itmFactory.NewByDb();
				wkr.ItmLoad(rdr, itm);
				return itm;
			}
			else {
				return null;
			}
		}
		finally {rdr.Rls();}
	}

	@Override public void Close() {}
}
