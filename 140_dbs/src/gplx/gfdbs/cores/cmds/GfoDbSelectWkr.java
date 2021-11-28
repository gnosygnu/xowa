package gplx.gfdbs.cores.cmds;

import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;

public interface GfoDbSelectWkr<I> {
	Db_rdr NewRdr(Db_conn conn, Object... args);
	void ItmLoad(Db_rdr rdr, I itm);
}
