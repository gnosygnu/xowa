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
import gplx.dbs.*; import gplx.gfui.*; import gplx.fsdb.*;
import gplx.xowa.files.wiki_orig.*;
class Fs_root_dir {
	private Gfo_usr_dlg usr_dlg; private Xof_img_wkr_query_img_size img_size_wkr;
	private Io_url url; private boolean recurse = true;
	private Orig_fil_mgr cache = new Orig_fil_mgr(), fs_fil_mgr;
	private Db_provider_mkr provider_mkr;
	private Db_provider provider; private Fsdb_cfg_tbl cfg_tbl; private Orig_fil_tbl fil_tbl;
	private int fil_id_next = 0;
	public void Init(Io_url url, Db_provider_mkr provider_mkr, Fsdb_cfg_tbl cfg_tbl, Orig_fil_tbl fil_tbl, Gfo_usr_dlg usr_dlg, Xof_img_wkr_query_img_size img_size_wkr) {
		this.url = url; this.provider_mkr = provider_mkr;
		this.cfg_tbl = cfg_tbl; this.fil_tbl = fil_tbl; this.usr_dlg = usr_dlg; this.img_size_wkr = img_size_wkr;
	}
	public Orig_fil_itm Get_by_ttl(byte[] lnki_ttl) {
		Orig_fil_itm rv = (Orig_fil_itm)cache.Get_by_ttl(lnki_ttl);
		if (rv == null) {
			rv = Get_from_db(lnki_ttl);
			if (rv == null) {
				rv = Get_from_fs(lnki_ttl);
				if (rv == null) return Orig_fil_itm.Null;
			}
			cache.Add(rv);
		}
		return rv;
	}
	private Orig_fil_itm Get_from_db(byte[] lnki_ttl) {
		if (provider == null) provider = Init_db_fil_mgr();
		Orig_fil_itm rv = fil_tbl.Select_itm(lnki_ttl);
		if (rv == null) return Orig_fil_itm.Null;		// not in db
		return rv;
	}
	private Orig_fil_itm Get_from_fs(byte[] lnki_ttl) {
		if (fs_fil_mgr == null) fs_fil_mgr = Init_fs_fil_mgr();
		Orig_fil_itm rv = fs_fil_mgr.Get_by_ttl(lnki_ttl);
		if (rv == null) return Orig_fil_itm.Null;		// not in fs
		SizeAdp img_size = SizeAdp_.Zero;
		if (Xof_ext_.Id_is_image(rv.Fil_ext_id()))
			img_size = img_size_wkr.Exec(rv.Fil_url());
		rv.Init_by_size(++fil_id_next, img_size.Width(), img_size.Height());
		cfg_tbl.Update(Cfg_grp_root_dir, Cfg_key_fil_id_next, Int_.XtoStr(fil_id_next));
		fil_tbl.Insert(rv);
		return rv;
	}
	private Orig_fil_mgr Init_fs_fil_mgr() {	// NOTE: need to read entire dir, b/c ttl may be "A.png", but won't know which subdir
		Orig_fil_mgr rv = new Orig_fil_mgr();
		Io_url[] fils = Io_mgr._.QueryDir_args(url).Recur_(recurse).ExecAsUrlAry();
		int fils_len = fils.length;
		for (int i = 0; i < fils_len; i++) {
			Io_url fil = fils[i];
			byte[] fil_name_bry = Xto_fil_bry(fil);
			Orig_fil_itm fil_itm = rv.Get_by_ttl(fil_name_bry);
			if (fil_itm != Orig_fil_itm.Null) {
				usr_dlg.Warn_many("", "", "file already exists: cur=~{0} new=~{1}", fil_itm.Fil_url().Raw(), fil.Raw());
				continue;
			}
			Xof_ext ext = Xof_ext_.new_by_ttl_(fil_name_bry);
			fil_itm = new Orig_fil_itm().Init_by_make(fil, fil_name_bry, ext.Id());
			rv.Add(fil_itm);
		}
		return rv;
	}
	private Db_provider Init_db_fil_mgr() {
		Io_url db_url = url.GenSubFil("^orig_regy.sqlite3");
		Bool_obj_ref created_ref = Bool_obj_ref.n_();
		provider = provider_mkr.Load_or_make_(db_url, created_ref);
		boolean created = created_ref.Val();
		cfg_tbl.Ctor(provider, created);
		fil_tbl.Ctor(provider, created);
		if (created)
			cfg_tbl.Insert(Cfg_grp_root_dir, Cfg_key_fil_id_next, Int_.XtoStr(fil_id_next));
		else {
			fil_id_next = cfg_tbl.Select_as_int_or_fail(Cfg_grp_root_dir, Cfg_key_fil_id_next);
		}
		return provider;
	}
	public void Rls() {
		cfg_tbl.Rls();
		fil_tbl.Rls();
	}
	private static final String Cfg_grp_root_dir = "xowa.root_dir", Cfg_key_fil_id_next = "fil_id_next";
	public static byte[] Xto_fil_bry(Io_url url) {
		byte[] rv = Bry_.new_utf8_(url.NameAndExt());
		rv = Bry_.Replace(rv, Byte_ascii.Space, Byte_ascii.Underline);
		rv = Bry_.Upper_1st(rv);
		return rv;
	}
}
