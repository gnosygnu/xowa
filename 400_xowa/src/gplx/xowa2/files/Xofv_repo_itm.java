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
public class Xofv_repo_itm {
	Xofv_repo_itm(byte tid, byte[] key, byte dir_spr, byte[] dir_root, byte[] dir_sub_orig, byte[] dir_sub_thumb) {
		this.tid = tid; this.key = key; this.dir_spr = dir_spr; this.dir_root = dir_root; this.dir_sub_orig = dir_sub_orig; this.dir_sub_thumb = dir_sub_thumb;
	}
	public byte Tid() {return tid;} private final byte tid;
	public byte[] Key() {return key;} private final byte[] key;
	public byte Dir_spr() {return dir_spr;} private final byte dir_spr;
	public byte[] Dir_root() {return dir_root;} private final byte[] dir_root;
	public byte[] Dir_sub_orig() {return dir_sub_orig;} private final byte[] dir_sub_orig;
	public byte[] Dir_sub_thumb() {return dir_sub_thumb;} private final byte[] dir_sub_thumb;
	public static Xofv_repo_itm new_trg_fsys(byte tid, byte[] key, Io_url root) {
		return new Xofv_repo_itm(tid, key, root.Info().DirSpr_byte(), Bry_.new_utf8_(root.Raw()), Dir_sub_orig_dflt, Dir_sub_thumb_dflt);
	}
	public static final byte Tid_val_comm = 0, Tid_val_wiki = 1;
	private static final byte[] Dir_sub_orig_dflt = Bry_.new_ascii_("orig"), Dir_sub_thumb_dflt = Bry_.new_ascii_("thumb");
	public static final int Id_temp = 0;
}
