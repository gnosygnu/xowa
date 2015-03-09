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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
class Xofc_cache_itm {
	public Xofc_cache_itm
	( Bry_bfr lnki_key_bfr, int uid, byte db_state
	, int lnki_wiki, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page
	, int orig_wiki, byte[] orig_ttl, int orig_ext, int file_w, int file_h, double file_time, int file_page
	, long temp_file_size, int temp_view_count, long temp_view_date, int temp_w		
	) {
		this.uid = uid; this.db_state = db_state;
		this.lnki_wiki = lnki_wiki; this.lnki_ttl = lnki_ttl; this.lnki_type = lnki_type; this.lnki_upright = lnki_upright; this.lnki_w = lnki_w; this.lnki_h = lnki_h; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.orig_wiki = orig_wiki; this.orig_ttl = orig_ttl; this.orig_ext = orig_ext; this.file_w = file_w; this.file_h = file_h; this.file_time = file_time; this.file_page = file_page;
		this.temp_file_size = temp_file_size; this.temp_view_count = temp_view_count; this.temp_view_date = temp_view_date; this.temp_w = temp_w;
		lnki_key_bfr
			.Add_int_variable(lnki_wiki).Add_byte_pipe()
			.Add(lnki_ttl).Add_byte_pipe()
			.Add_int_variable(lnki_type).Add_byte_pipe()
			.Add_double(lnki_upright).Add_byte_pipe()
			.Add_int_variable(lnki_w).Add_byte_pipe()
			.Add_int_variable(lnki_h).Add_byte_pipe()
			.Add_double(lnki_time).Add_byte_pipe()
			.Add_int_variable(lnki_page)
			;
		lnki_key = lnki_key_bfr.Xto_bry_and_clear();
	}
	public int Uid() {return uid;} private int uid;
	public byte Db_state() {return db_state;} public void Db_state_(byte v) {db_state = v;} private byte db_state;
	public byte[] Lnki_key() {return lnki_key;} private final byte[] lnki_key;
	public int Lnki_wiki() {return lnki_wiki;} private final int lnki_wiki;
	public byte[] Lnki_ttl() {return lnki_ttl;} private final byte[] lnki_ttl;
	public int Lnki_type() {return lnki_type;} private final int lnki_type;
	public double Lnki_upright() {return lnki_upright;} private final double lnki_upright;
	public int Lnki_w() {return lnki_w;} private final int lnki_w;
	public int Lnki_h() {return lnki_h;} private final int lnki_h;
	public double Lnki_time() {return lnki_time;} private final double lnki_time;
	public int Lnki_page() {return lnki_page;} private final int lnki_page;
	public int Orig_wiki() {return orig_wiki;} private final int orig_wiki;
	public byte[] Orig_ttl() {return orig_ttl;} private final byte[] orig_ttl;
	public int Orig_ext() {return orig_ext;} private final int orig_ext;
	public int File_w() {return file_w;} private final int file_w;
	public int File_h() {return file_h;} private final int file_h;
	public double File_time() {return file_time;} private final double file_time;
	public int File_page() {return file_page;} private final int file_page;
	public long Temp_file_size() {return temp_file_size;} private long temp_file_size;
	public int Temp_view_count() {return temp_view_count;} private int temp_view_count;
	public long Temp_view_date() {return temp_view_date;} private long temp_view_date;
	public int Temp_w() {return temp_w;} private int temp_w;
	public static final Xofc_cache_itm Null = null;
}
