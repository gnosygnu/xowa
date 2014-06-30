/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.ios.*;
public class Fsdb_db_bin_fil implements RlsAble {
	public int Id() {return id;} private int id;
	public Io_url Url() {return url;} private Io_url url;
	public long Bin_max() {return bin_max;} private long bin_max;
	public void Bin_max_(long v) {
		bin_max = v; 
		if (cmd_mode == Db_cmd_mode.Ignore) cmd_mode = Db_cmd_mode.Update;
	}
	public long Bin_len() {return bin_len;} private long bin_len;
	public void Bin_len_(long v) {
		bin_len = v; 
		if (cmd_mode == Db_cmd_mode.Ignore) cmd_mode = Db_cmd_mode.Update;
	}
	public byte Cmd_mode() {return cmd_mode;} public Fsdb_db_bin_fil Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public Db_provider Provider() {
		if (provider == null) {
			if (cmd_mode == Db_cmd_mode.Create) {
				provider = Db_provider_.new_(Db_connect_sqlite.make_(url));
				Sqlite_engine_.Pragma_page_size_4096(provider);
				Fsdb_bin_tbl.Create_table(provider);
			}
			else
				provider = Db_provider_.new_(Db_connect_sqlite.load_(url));
		}
		return provider;
	} 	private Db_provider provider;
	public void Rls() {if (provider != null) provider.Rls();}
	public long Insert(int bin_id, byte owner_tid, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Fsdb_bin_tbl.Insert_stmt(this.Provider());
			return Fsdb_bin_tbl.Insert_rdr(stmt, bin_id, owner_tid, bin_len, bin_rdr);
		}
		finally {stmt.Rls();}
	}
	public boolean Get_to_url(int id, Io_url url, byte[] bin_bfr, int bin_flush_when) {
		return Fsdb_bin_tbl.Select_to_url(this.Provider(), id, url, bin_bfr, bin_flush_when);
	}
	public Io_stream_rdr Get_as_rdr(int id) {
		return Fsdb_bin_tbl.Select_as_rdr(this.Provider(), id);
	}
	public static Fsdb_db_bin_fil load_(DataRdr rdr, Io_url dir) {
		return new_
		(	rdr.ReadInt(Fsdb_db_bin_tbl.Fld_uid)
		,	dir.GenSubFil(rdr.ReadStr(Fsdb_db_bin_tbl.Fld_url))
		,	rdr.ReadLong(Fsdb_db_bin_tbl.Fld_bin_len)
		,	rdr.ReadLong(Fsdb_db_bin_tbl.Fld_bin_max)
		,	Db_cmd_mode.Ignore
		);
	}
	public static Fsdb_db_bin_fil make_(int id, Io_url url, long bin_len, long bin_max) {
		Fsdb_db_bin_fil rv = new_(id, url, bin_len, bin_max, Db_cmd_mode.Create);
		rv.Provider(); // force table create
		return rv;
	}
	private static Fsdb_db_bin_fil new_(int id, Io_url url, long bin_len, long bin_max, byte cmd_mode) {
		Fsdb_db_bin_fil rv = new Fsdb_db_bin_fil();
		rv.id = id;
		rv.url = url;
		rv.bin_len = bin_len;
		rv.bin_max = bin_max;
		rv.cmd_mode = cmd_mode;
		return rv;
	}
	public static final Fsdb_db_bin_fil[] Ary_empty = new Fsdb_db_bin_fil[0];
}
