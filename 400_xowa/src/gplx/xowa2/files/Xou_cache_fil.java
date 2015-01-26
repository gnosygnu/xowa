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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.files.*;
public class Xou_cache_fil {
	public Xou_cache_fil(int uid, int dir_id, byte[] dir_name, byte[] fil_name, int fil_ext, boolean fil_is_orig, int fil_w, int fil_h, double fil_time, int fil_page, long fil_size, long fil_view_time) {
		this.uid = uid;
		this.dir_id = dir_id; this.dir_name = dir_name;
		this.fil_name = fil_name; this.fil_ext = fil_ext; this.fil_is_orig = fil_is_orig;
		this.fil_w = fil_w; this.fil_h = fil_h; this.fil_time = fil_time; this.fil_page = fil_page;
		this.fil_size = fil_size; this.fil_view_time = fil_view_time;
	}
	public int Dir_id() {return dir_id;} private final int dir_id;
	public byte[] Dir_name() {return dir_name;} private final byte[] dir_name;
	public byte[] Fil_name() {return fil_name;} private final byte[] fil_name;
	public int Fil_ext() {return fil_ext;} private final int fil_ext;
	public boolean Fil_is_orig() {return fil_is_orig;} private final boolean fil_is_orig;
	public int Fil_w() {return fil_w;} private final int fil_w;
	public int Fil_h() {return fil_h;} private final int fil_h;
	public double Fil_time() {return fil_time;} private final double fil_time;
	public int Fil_page() {return fil_page;} private final int fil_page;
	public long Fil_size() {return fil_size;} private final long fil_size;
	public long Fil_view_time() {return fil_view_time;} public void Fil_view_time_(long v) {fil_view_time = v;} private long fil_view_time;
	public int Uid() {return uid;} public void Uid_(int v) {uid = v;} private int uid;
	public byte Cmd_mode() {return cmd_mode;} public void Cmd_mode_(byte v) {cmd_mode = v;} private byte cmd_mode = gplx.dbs.Db_cmd_mode.Ignore;
	public byte[] Key(Bry_bfr bfr) {return Key_bld(bfr, dir_name, fil_name, fil_is_orig, fil_w, fil_time, fil_page);}
	public static byte[] Key_bld(Bry_bfr bfr, byte[] dir_name, byte[] fil_name, boolean fil_is_orig, int fil_w, double fil_time, int fil_page) {
		bfr	.Add(dir_name).Add_byte_pipe()
			.Add(fil_name).Add_byte_pipe()
			.Add_yn(fil_is_orig).Add_byte_pipe()
			.Add_int_variable(fil_w).Add_byte_pipe()
			.Add_double(Xof_doc_thumb.Db_save_double(fil_time))
			.Add_int_variable(Xof_doc_page.Db_save_int(fil_page))
			;
		return bfr.Xto_bry_and_clear();
	}
}
