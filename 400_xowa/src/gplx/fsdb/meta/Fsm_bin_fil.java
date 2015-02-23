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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; 
import gplx.fsdb.data.*;
public class Fsm_bin_fil implements RlsAble {
	public Fsm_bin_fil(int id, Io_url url, long bin_len, long bin_max, byte cmd_mode) {
		this.id = id; this.url = url; this.bin_len = bin_len; this.bin_max = bin_max; this.cmd_mode = cmd_mode;
	}
	public int Id() {return id;} private final int id;
	public Io_url Url() {return url;} private final Io_url url;
	public long Bin_max() {return bin_max;} private long bin_max;
	public void Bin_max_(long v) {
		bin_max = v; 
		if (cmd_mode == Db_cmd_mode.Tid_ignore) cmd_mode = Db_cmd_mode.Tid_update;
	}
	public long Bin_len() {return bin_len;} private long bin_len;
	public void Bin_len_(long v) {
		bin_len = v; 
		if (cmd_mode == Db_cmd_mode.Tid_ignore) cmd_mode = Db_cmd_mode.Tid_update;
	}
	public byte Cmd_mode() {return cmd_mode;} public Fsm_bin_fil Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public static final Fsm_bin_fil[] Ary_empty = new Fsm_bin_fil[0];
	private Fsd_bin_tbl bin_tbl = new Fsd_bin_tbl(); private Db_conn conn;
	public static Fsm_bin_fil make_(int id, Io_url url, long bin_len, long bin_max) {
		Fsm_bin_fil rv = new Fsm_bin_fil(id, url, bin_len, bin_max, Db_cmd_mode.Tid_create);
		rv.Conn(); // force table create
		return rv;
	}
	private static final String Db_conn_bldr_type = "gplx.fsdb.fsm_bin";
	public Db_conn Conn() {
		if (conn == null) {
			Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(Db_conn_bldr_type, url);				
			conn =  conn_data.Conn();
			bin_tbl.Conn_(conn, conn_data.Created(), Bool_.Y);
		}
		return conn;
	}
	public long Insert(int bin_id, byte owner_tid, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {this.Conn(); return bin_tbl.Insert_rdr(bin_id, owner_tid, bin_len, bin_rdr);}
	public boolean Get_to_url(int id, Io_url url) {this.Conn(); return bin_tbl.Select_to_url(id, url);}
	public Io_stream_rdr Get_as_rdr(int id) {return bin_tbl.Select_as_rdr(this.Conn(), id);}
	public void Rls() {if (conn != null) conn.Conn_term();}
}
