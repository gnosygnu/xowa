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
public class Xofc_fil_itm implements CompareAble {
	public Xofc_fil_itm(int uid, int dir_id, byte[] name, boolean is_orig, int w, int h, double time, int page, Xof_ext ext, long size, long cache_time, byte cmd_mode) {
		this.uid = uid; this.dir_id = dir_id;
		this.name = name; this.is_orig = is_orig; this.w = w; this.h = h; this.time = time; this.page = page; this.ext = ext; this.size = size;
		this.cache_time = cache_time; this.cmd_mode = cmd_mode;
	}
	public String Key;
	public int		Uid() {return uid;} public void Uid_(int v) {uid = v;} private int uid;
	public int		Dir_id() {return dir_id;} private final int dir_id;
	public byte[]	Name() {return name;} private final byte[] name;
	public boolean		Is_orig() {return is_orig;} private final boolean is_orig;
	public int		W() {return w;} private final int w;
	public int		H() {return h;} private final int h;
	public double	Time() {return time;} private final double time;
	public int		Page() {return page;} private final int page;
	public Xof_ext	Ext() {return ext;} private final Xof_ext ext;
	public long		Size() {return size;} private long size;
	public void		Size_(long v) {
		this.size = v;
		cmd_mode = Db_cmd_mode.To_update(cmd_mode);
	}
	public long		Cache_time() {return cache_time;} private long cache_time;
	public Xofc_fil_itm Cache_time_now_() {
		this.cache_time = DateAdp_.Now().XtoUtc().Timestamp_unix();
		cmd_mode = Db_cmd_mode.To_update(cmd_mode);
		return this;
	}
	public byte		Cmd_mode() {return cmd_mode;} public Xofc_fil_itm Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public void		Cmd_mode_delete_() {cmd_mode = Db_cmd_mode.Tid_delete;}
	public byte[]	Gen_hash_key_v1(Bry_bfr bfr)	{return Gen_hash_key_v1(bfr, dir_id, name, is_orig, w, h, time);}
	public byte[]	Gen_hash_key_v2(Bry_bfr bfr)	{return Gen_hash_key_v2(bfr, dir_id, name, is_orig, w, time, page);}
	public static byte[] Gen_hash_key_v1(Bry_bfr bfr, int dir_id, byte[] name, boolean is_orig, int w, int h, double time) {
		bfr	.Add_int_variable(dir_id).Add_byte_pipe()
			.Add(name).Add_byte_pipe()
			.Add_yn(is_orig).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe()
			.Add_int_variable(h).Add_byte_pipe()
			.Add_int_variable(Xof_doc_thumb.X_int(time))
			;
		return bfr.Xto_bry_and_clear();
	}
	public static byte[] Gen_hash_key_v2(Bry_bfr bfr, int dir_id, byte[] name, boolean is_orig, int w, double time, int page) {
		bfr	.Add_int_variable(dir_id).Add_byte_pipe()
			.Add(name).Add_byte_pipe()
			.Add_yn(is_orig).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe()
			.Add_double(Xof_doc_thumb.Db_save_double(time)).Add_byte_pipe()
			.Add_int_variable(page)
			;
		return bfr.Xto_bry_and_clear();
	}
	public int compareTo(Object obj) {Xofc_fil_itm comp = (Xofc_fil_itm)obj; return -Long_.Compare(cache_time, comp.cache_time);}	// - for DESC sort
	public static final Xofc_fil_itm Null = null;
}
