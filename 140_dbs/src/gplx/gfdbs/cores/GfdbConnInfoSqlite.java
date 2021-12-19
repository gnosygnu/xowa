package gplx.gfdbs.cores;

import gplx.libs.files.Io_url;

public class GfdbConnInfoSqlite implements GfdbConnInfo {
	public GfdbConnInfoSqlite(Io_url dbFilPath) {
		this.dbFilPath = dbFilPath;
	}
	public String ConnStr() {return null;}
	public Io_url DbFilPath() {return dbFilPath;} private Io_url dbFilPath;
}
