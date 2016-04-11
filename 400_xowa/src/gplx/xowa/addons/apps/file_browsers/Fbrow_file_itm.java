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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.core.ios.*;
import gplx.langs.mustaches.*;
class Fbrow_file_itm implements Mustache_doc_itm {
	private final    boolean is_dir, is_core_xowa;
	private final    byte[] owner_dir_enc, path, name, date, size, color;
	private final    Fbrow_file_itm[] subs;
	public Fbrow_file_itm(boolean is_dir, boolean is_core_xowa, int color, byte[] owner_dir, byte[] path, byte[] name, byte[] date, byte[] size, Fbrow_file_itm[] subs) {
		this.is_dir = is_dir; this.is_core_xowa = is_core_xowa;
		this.color = color % 2 == 0 ? Byte_ascii.Num_0_bry : Byte_ascii.Num_1_bry;
		this.owner_dir_enc = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(owner_dir);
		this.path = path; this.name = name; this.date = date; this.size = size;
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
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "is_dir"))			return Mustache_doc_itm_.Ary__bool(is_dir);
		else if	(String_.Eq(key, "is_core_xowa"))	return Mustache_doc_itm_.Ary__bool(is_core_xowa);
		else if	(String_.Eq(key, "subs"))			return subs;
		return Mustache_doc_itm_.Ary__empty;
	}
	public static final    Fbrow_file_itm[] Ary_empty = new Fbrow_file_itm[0];
	public static Fbrow_file_itm New(IoItmDir owner_dir) {
		List_adp sub_list = List_adp_.new_();
		New_subs(owner_dir.Url(), sub_list, owner_dir.SubDirs());
		New_subs(owner_dir.Url(), sub_list, owner_dir.SubFils());
		Fbrow_file_itm[] subs = (Fbrow_file_itm[])sub_list.To_ary_and_clear(Fbrow_file_itm.class);
		return new Fbrow_file_itm(Bool_.Y, Bool_.N, 0, owner_dir.Url().OwnerDir().RawBry(), owner_dir.Url().RawBry(), Bry_.new_u8(owner_dir.Name()), Bry_.Empty, Bry_.Empty, subs);
	}
	private static void New_subs(Io_url owner_dir, List_adp list, IoItmList subs) {
		subs.Sort();
		int len = subs.Len();
		int list_total = list.Len();
		byte[] owner_dir_bry = owner_dir.RawBry();
		for (int i = 0; i < len; ++i) {
			IoItm_base src = (IoItm_base)subs.Get_at(i);
			Fbrow_file_itm trg = null;
			if (src.Type_dir()) {
				byte[] trg_url = src.Url().RawBry();
				trg = new Fbrow_file_itm(Bool_.Y, Bool_.N, list_total + i, owner_dir_bry, trg_url, Bry_.new_u8(src.Url().NameAndExt_noDirSpr()), Bry_.Empty, Bry_.Empty, Ary_empty);
			}
			else {
				IoItmFil src_as_fil = (IoItmFil)src;
				String size_str = Io_size_.To_str(src_as_fil.Size(), "#,###");
				boolean is_xowa_core = gplx.xowa.wikis.data.Xowd_db_mgr.Maybe_core(owner_dir.NameOnly(), src.Url().NameAndExt());
				trg = new Fbrow_file_itm(Bool_.N, is_xowa_core, list_total + i, owner_dir_bry, src.Url().RawBry(), Bry_.new_u8(src.Name()), Bry_.new_u8(src_as_fil.ModifiedTime().XtoStr_fmt("yyyy-MM-dd")), Bry_.new_u8(size_str), Ary_empty);
			}
			list.Add(trg);
		}
	}
}
