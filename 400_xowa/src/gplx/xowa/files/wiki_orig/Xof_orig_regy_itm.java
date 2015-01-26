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
package gplx.xowa.files.wiki_orig; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
public class Xof_orig_regy_itm {
	public Xof_orig_regy_itm(byte[] ttl, byte status, byte repo_tid, int orig_w, int orig_h, int orig_ext, byte[] orig_redirect) {
		this.ttl = ttl; this.status = status;
		this.repo_tid = repo_tid;
		this.orig_w = orig_w; this.orig_h = orig_h; this.orig_ext = orig_ext; this.orig_redirect = orig_redirect;
	}
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public byte Status() {return status;} private final byte status;
	public byte Repo_tid() {return repo_tid;} private final byte repo_tid;
	public int Orig_w() {return orig_w;} private final int orig_w;
	public int Orig_h() {return orig_h;} private final int orig_h;
	public int Orig_ext() {return orig_ext;} private final int orig_ext;
	public byte[] Orig_redirect() {return orig_redirect;} private final byte[] orig_redirect;
	public static Xof_orig_regy_itm load_(DataRdr rdr) {
		Xof_orig_regy_itm rv = new Xof_orig_regy_itm
		( rdr.ReadBryByStr(Xof_wiki_orig_tbl.Fld_orig_ttl)
		, rdr.ReadByte(Xof_wiki_orig_tbl.Fld_status)
		, rdr.ReadByte(Xof_wiki_orig_tbl.Fld_orig_repo)
		, rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_w)
		, rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_h)
		, rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_ext)
		, rdr.ReadBryByStr(Xof_wiki_orig_tbl.Fld_orig_redirect)
		);
		return rv;
	}
	public static Xof_orig_regy_itm load_(Db_rdr rdr) {
		Xof_orig_regy_itm rv = new Xof_orig_regy_itm
		( rdr.Read_bry_by_str(Xof_wiki_orig_tbl.Ord_orig_ttl)
		, rdr.Read_byte(Xof_wiki_orig_tbl.Ord_status)
		, rdr.Read_byte(Xof_wiki_orig_tbl.Ord_orig_repo)
		, rdr.Read_int(Xof_wiki_orig_tbl.Ord_orig_w)
		, rdr.Read_int(Xof_wiki_orig_tbl.Ord_orig_h)
		, rdr.Read_int(Xof_wiki_orig_tbl.Ord_orig_ext)
		, rdr.Read_bry_by_str(Xof_wiki_orig_tbl.Ord_orig_redirect)
		);
		return rv;
	}
	public static final Xof_orig_regy_itm Null = null;
}
