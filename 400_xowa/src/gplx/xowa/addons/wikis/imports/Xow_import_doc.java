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
package gplx.xowa.addons.wikis.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.core.ios.*;
import gplx.langs.mustaches.*;
class Xow_import_doc implements Mustache_doc_itm {
	private final    boolean is_dir, is_core_xowa;
	private final    byte[] owner_dir_enc, path, name, date, size, color;
	private final    byte[] dir_cmd;
	private final    Xow_import_doc[] subs;
	public Xow_import_doc(boolean is_dir, boolean is_core_xowa, int color, byte[] owner_dir, byte[] path, byte[] name, byte[] date, byte[] size, byte[] dir_cmd, Xow_import_doc[] subs) {
		this.is_dir = is_dir; this.is_core_xowa = is_core_xowa;
		this.color = color % 2 == 0 ? Byte_ascii.Num_0_bry : Byte_ascii.Num_1_bry;
		this.owner_dir_enc = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(owner_dir);
		this.path = path; this.name = name; this.date = date; this.size = size;
		this.dir_cmd = dir_cmd;
		this.subs = subs;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "path"))			bfr.Add_bry(path);
		else if	(String_.Eq(key, "path_enc"))		bfr.Add_bry(gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(path));
		else if	(String_.Eq(key, "owner_dir_enc"))	bfr.Add_bry(owner_dir_enc);
		else if	(String_.Eq(key, "name"))			bfr.Add_bry(name);
		else if	(String_.Eq(key, "date"))			bfr.Add_bry(date);
		else if	(String_.Eq(key, "size"))			bfr.Add_bry(size);
		else if	(String_.Eq(key, "color"))			bfr.Add_bry(color);
		else if	(String_.Eq(key, "dir_cmd"))		bfr.Add_bry(dir_cmd);
		else if	(String_.Eq(key, "dir_cmd_arg"))	{bfr.Add_str_u8("&dir_cmd="); bfr.Add_bry(dir_cmd);}
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "is_dir"))			return Mustache_doc_itm_.Ary__bool(is_dir);
		else if	(String_.Eq(key, "dir_cmd_exists"))	return Mustache_doc_itm_.Ary__bool(Bry_.Len_gt_0(dir_cmd));
		else if	(String_.Eq(key, "is_core_xowa"))	return Mustache_doc_itm_.Ary__bool(is_core_xowa);
		else if	(String_.Eq(key, "subs"))			return subs;
		return Mustache_doc_itm_.Ary__empty;
	}
	public static final    Xow_import_doc[] Ary_empty = new Xow_import_doc[0];
	public static Xow_import_doc New(IoItmDir owner_dir, byte[] dir_cmd) {
		List_adp sub_list = List_adp_.New();
		New_subs(owner_dir.Url(), sub_list, owner_dir.SubDirs(), dir_cmd);
		New_subs(owner_dir.Url(), sub_list, owner_dir.SubFils(), Bry_.Empty);
		Xow_import_doc[] subs = (Xow_import_doc[])sub_list.To_ary_and_clear(Xow_import_doc.class);
		return new Xow_import_doc(Bool_.Y, Bool_.N, 0, owner_dir.Url().OwnerDir().RawBry(), owner_dir.Url().RawBry(), Bry_.new_u8(owner_dir.Name()), Bry_.Empty, Bry_.Empty, dir_cmd, subs);
	}
	private static void New_subs(Io_url owner_dir, List_adp list, IoItmList subs, byte[] dir_cmd) {
		subs.Sort();
		int len = subs.Len();
		int list_total = list.Len();
		byte[] owner_dir_bry = owner_dir.RawBry();
		for (int i = 0; i < len; ++i) {
			IoItm_base src = (IoItm_base)subs.Get_at(i);
			Xow_import_doc trg = null;
			if (src.Type_dir()) {
				byte[] trg_url = src.Url().RawBry();
				trg = new Xow_import_doc(Bool_.Y, Bool_.N, list_total + i, owner_dir_bry, trg_url, Bry_.new_u8(src.Url().NameAndExt_noDirSpr()), Bry_.Empty, Bry_.Empty, dir_cmd, Ary_empty);
			}
			else {
				IoItmFil src_as_fil = (IoItmFil)src;
				String size_str = Io_size_.To_str(src_as_fil.Size(), "#,###");
				boolean is_xowa_core = gplx.xowa.wikis.data.Xow_db_file__core_.Is_core_fil_name(owner_dir.NameOnly(), src.Url().NameAndExt());
				trg = new Xow_import_doc(Bool_.N, is_xowa_core, list_total + i, owner_dir_bry, src.Url().RawBry(), Bry_.new_u8(src.Name()), Bry_.new_u8(src_as_fil.ModifiedTime().XtoStr_fmt("yyyy-MM-dd")), Bry_.new_u8(size_str), dir_cmd, Ary_empty);
			}
			list.Add(trg);
		}
	}
}
