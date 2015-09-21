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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import gplx.core.flds.*;
import gplx.xowa.nss.*;
public class Xoctg_view_itm implements gplx.CompareAble {
	public byte Tid() {return tid;} private byte tid;
	public int Id() {return id;} private int id;
	public boolean Id_missing() {return id_missing;} public Xoctg_view_itm Id_missing_(boolean v) {id_missing = v; return this;} private boolean id_missing;
	public int Timestamp() {return timestamp;} private int timestamp;
	public Xoa_ttl Ttl() {return ttl;} public Xoctg_view_itm Ttl_(Xoa_ttl v) {ttl = v; return this;} private Xoa_ttl ttl;
	public int Page_size() {return page_size;} private int page_size;
	public int Subs_ctgs() {return subs_ctgs;} private int subs_ctgs;
	public int Subs_pages() {return subs_pages;} private int subs_pages;
	public int Subs_files() {return subs_files;} private int subs_files;
	public void Load_by_ttl_data(byte tid, int id, int timestamp, int page_size) {this.tid = tid; this.id = id; this.timestamp = timestamp; this.page_size = page_size;}
	public byte[] Sortkey() {return sortkey;} public Xoctg_view_itm Sortkey_(byte[] v) {sortkey = v; return this;} private byte[] sortkey;
	public void Subs_(int ctgs, int pages, int files) {this.subs_ctgs = ctgs; this.subs_pages = pages; this.subs_files = files;}
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public Xow_ns Ns() {return ns;} private Xow_ns ns;
	public void Load_by_id_data(Xow_ns ns, byte[] ttl_bry) {
		this.ns = ns; this.ttl_bry = ttl_bry; this.sortkey = ttl_bry;
	}
	public static final Xoctg_view_itm[] Ary_empty = new Xoctg_view_itm[0];
	public int compareTo(Object obj) {Xoctg_view_itm comp = (Xoctg_view_itm)obj; return Int_.Compare(id, comp.Id());}
	public int Pos() {return pos;} public Xoctg_view_itm Pos_(int v) {pos = v; return this;} private int pos; 
	public Xoctg_view_itm Parse(Gfo_fld_rdr fld_rdr, int pos) {
		this.pos = pos;
		id				= fld_rdr.Read_int_base85_len5();
		timestamp		= fld_rdr.Read_int_base85_len5();
		sortkey			= fld_rdr.Read_bry_escape();
		return this;
	}
	public void Clear() {
		this.pos		= 0;
		id				= 0;
		timestamp		= 0;
		sortkey			= null;
	}
}
