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
import gplx.dbs.*;
public class Xof_cache_itm {
	public Xof_cache_itm
	( Bry_bfr lnki_key_bfr, byte db_state
	, int lnki_site, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page
	, int orig_wiki, byte[] orig_ttl, int orig_ext, int file_w, int file_h, double file_time, int file_page
	, long temp_file_size, int temp_view_count, long temp_view_date, int temp_w		
	) {
		this.db_state = db_state;
		this.lnki_site = lnki_site; this.lnki_ttl = lnki_ttl; this.lnki_type = lnki_type; this.lnki_upright = lnki_upright; this.lnki_w = lnki_w; this.lnki_h = lnki_h; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.orig_wiki = orig_wiki; this.orig_ttl = orig_ttl; this.orig_ext = orig_ext; this.file_w = file_w; this.file_h = file_h; this.file_time = file_time; this.file_page = file_page;
		this.temp_file_size = temp_file_size; this.temp_view_count = temp_view_count; this.temp_view_date = temp_view_date; this.temp_w = temp_w;
		this.lnki_key = Key_gen(lnki_key_bfr, lnki_site, lnki_ttl, lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page);
	}
	public byte Db_state() {return db_state;} public void Db_state_(byte v) {db_state = v;} private byte db_state;
	public byte[] Lnki_key() {return lnki_key;} private final byte[] lnki_key;
	public int Lnki_site() {return lnki_site;} private final int lnki_site;
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
	public boolean File_is_orig() {return file_is_orig;} public boolean file_is_orig;
	public Io_url Temp_file_url() {return temp_file_url;} public Io_url temp_file_url;
	public void Update_view_stats() {
		++temp_view_count;
		temp_view_date = DateAdp_.Now().Timestamp_unix();
		db_state = Db_cmd_mode.To_update(db_state);
	}
	public static final Xof_cache_itm Null = null;
	public static byte[] Key_gen(Bry_bfr key_bfr, int site, byte[] ttl, int type, double upright, int w, int h, double time, int page) {
		key_bfr
			.Add_int_variable(site).Add_byte_pipe()
			.Add(ttl).Add_byte_pipe()
			.Add_int_variable(type).Add_byte_pipe()
			.Add_double(upright).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe()
			.Add_int_variable(h).Add_byte_pipe()
			.Add_double(time).Add_byte_pipe()
			.Add_int_variable(page)
			;
		return key_bfr.Xto_bry_and_clear();
	}
}
