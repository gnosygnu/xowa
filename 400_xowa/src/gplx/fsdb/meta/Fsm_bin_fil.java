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
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.dbs.*;
import gplx.fsdb.data.*;
public class Fsm_bin_fil {
	public Fsm_bin_fil(boolean schema_is_1, int id, Io_url url, String url_rel, Db_conn conn, long bin_len) {
		this.id = id; this.url = url; this.url_rel = url_rel; this.conn = conn; this.bin_len = bin_len;
		this.tbl = new Fsd_bin_tbl(conn, schema_is_1);
	}
	public int				Id()		{return id;}		private final    int id;
	public Io_url			Url()		{return url;}		private Io_url url;
	public String			Url_rel()	{return url_rel;}	private final    String url_rel;
	public long				Bin_len()	{return bin_len;}	public void Bin_len_(long v) {bin_len = v;} private long bin_len; 
	public Db_conn			Conn()		{return conn;}		private final    Db_conn conn;
	public Fsd_bin_tbl		Tbl()		{return tbl;}		private final    Fsd_bin_tbl tbl;
	public boolean			Select_to_url(int id, Io_url url)	{return tbl.Select_to_url(id, url);}
	public Io_stream_rdr	Select_as_rdr(int id)				{return tbl.Select_as_rdr(id);}
	public Fsd_bin_itm		Select_as_itm(int id)				{return tbl.Select_as_itm(id);}
	public void				Insert(int bin_id, byte owner_tid, long rdr_len, gplx.core.ios.streams.Io_stream_rdr rdr) {
		tbl.Insert_rdr(bin_id, owner_tid, rdr_len, rdr);
		Bin_len_(bin_len + rdr_len);
	}
	public static final    Fsm_bin_fil[] Ary_empty = new Fsm_bin_fil[0];
	public static final long Bin_len_null = 0;
}
