/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
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
		return new Xofv_repo_itm(tid, key, root.Info().DirSpr_byte(), Bry_.new_u8(root.Raw()), Dir_sub_orig_dflt, Dir_sub_thumb_dflt);
	}
	public static final byte Tid_val_comm = 0, Tid_val_wiki = 1;
	private static final byte[] Dir_sub_orig_dflt = Bry_.new_a7("orig"), Dir_sub_thumb_dflt = Bry_.new_a7("thumb");
	public static final int Id_temp = 0;
}
