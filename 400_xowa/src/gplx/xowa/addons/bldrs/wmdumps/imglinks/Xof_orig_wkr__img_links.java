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
package gplx.xowa.addons.bldrs.wmdumps.imglinks;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.files.origs.*;
public class Xof_orig_wkr__img_links implements Xof_orig_wkr {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	private Db_conn imglnk_conn;
	public Xof_orig_wkr__img_links(Xowe_wiki wiki) {
		this.wiki = wiki;
	}
	public byte			Tid() {return Xof_orig_wkr_.Tid_xowa_img_links;}
	public Xof_orig_itm	Find_as_itm(byte[] ttl, int list_idx, int list_len) {
		Xof_orig_itm rv = (Xof_orig_itm)hash.GetByOrNull(ttl);
		if		(rv == Missing) return Xof_orig_itm.Null;
		else if (rv == null)	rv = Load_from_db(ttl);
		return rv == Missing ? Xof_orig_itm.Null : rv;
	}
	public void			Find_by_list(Ordered_hash rv, List_adp itms) {throw ErrUtl.NewUnimplemented();}
	public boolean		Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {return false;}
	public void			Db_txn_save() {}
	public void			Db_rls() {}

	public Xowe_wiki	Wiki() {return wiki;} private final Xowe_wiki wiki;
	public Imglnk_reg_tbl Tbl__imglnk_reg() {
		if (tbl__imglnk_reg == null)
			this.tbl__imglnk_reg = new Imglnk_reg_tbl(imglnk_conn);
		return tbl__imglnk_reg;
	}	private Imglnk_reg_tbl tbl__imglnk_reg;
	public Db_stmt Stmt__image__select(byte repo, Xowe_wiki wiki) {
		Db_stmt rv = stmt__image__select[repo];
		if (rv == null) {
			rv = Make__stmt__image__select(repo, wiki);
			stmt__image__select[repo] = rv;
		}
		return rv;
	}	private Db_stmt[] stmt__image__select = new Db_stmt[2];
	private Db_stmt Make__stmt__image__select(byte repo, Xowe_wiki wiki) {
		Xob_db_file image_db = Xob_db_file.New__wiki_image(wiki.Fsys_mgr().Root_dir());
		return image_db.Conn().Stmt_select
		( "image"
		, StringUtl.Ary("img_media_type", "img_minor_mime", "img_size", "img_width", "img_height", "img_bits", "img_ext_id", "img_timestamp")
		, StringUtl.Ary("img_name")
		);
	}
	public void Add_by_db(Xof_orig_itm itm) {
		hash.Add(itm.Ttl(), itm);
	}
	private Xof_orig_itm Load_from_db(byte[] ttl) {
		synchronized (hash) {	// LOCK:orig_wkr is shared by multiple threads; NullPointerException on statement sometimes when concurrent; DATE:2016-09-03
			if (imglnk_conn == null)
				imglnk_conn = Xob_db_file.New__img_link(wiki).Conn();
			Xof_orig_itm rv = Xof_orig_wkr__img_links_.Load_itm(this, imglnk_conn, wiki, ttl);
			if (rv == Xof_orig_itm.Null)
				rv = Missing;
			hash.Add(ttl, rv);
			return rv;
		}
	}
	private static final Xof_orig_itm Missing = new Xof_orig_itm(ByteUtl.MaxValue127, BryUtl.Empty, -1, -1, -1, BryUtl.Empty);
}
