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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
public class Orig_fil_itm {
	public Orig_fil_itm() {}
	public Orig_fil_itm(int uid, byte[] name, int ext_id, int w, int h, byte[] dir_url) {
		this.fil_uid = uid; this.fil_name = name; this.fil_ext_id = ext_id; this.fil_w = w; this.fil_h = h; this.fil_dir_url = dir_url;
	}
	public int Fil_uid() {return fil_uid;} private int fil_uid;
	public byte[] Fil_name() {return fil_name;} private byte[] fil_name;
	public int Fil_ext_id() {return fil_ext_id;} private int fil_ext_id;
	public int Fil_w() {return fil_w;} private int fil_w;
	public int Fil_h() {return fil_h;} private int fil_h;
	public byte[] Fil_dir_url() {return fil_dir_url;} private byte[] fil_dir_url;
	public Io_url Fil_url() {
		if (fil_url == null) {
			byte[] fil_url_bry = Bry_.Add(fil_dir_url, fil_name);
			fil_url = Io_url_.new_fil_(String_.new_u8(fil_url_bry));
		}
		return fil_url;
	}	private Io_url fil_url;
	public Orig_fil_itm Init_by_make(Io_url url, byte[] name_bry, int ext_id) {
		this.fil_url = url;
		this.fil_name = name_bry;
		this.fil_dir_url = Bry_.new_u8(url.OwnerDir().Raw());
		this.fil_ext_id = ext_id;
		return this;
	}
	public Orig_fil_itm Init_by_size(int uid, int w, int h) {this.fil_uid = uid; this.fil_w = w; this.fil_h = h; return this;}
	public static final Orig_fil_itm Null = null;
}
