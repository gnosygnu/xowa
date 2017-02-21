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
package gplx.xowa.addons.bldrs.exports.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
public interface Split_tbl {
	String						Tbl_name();
	boolean						Layout_is_lot();
	String[]					Fld_pkeys();
	String						Fld_blob();
	Dbmeta_fld_list				Flds();
	Db_conn						Wiki_conn__get_or_new(Xow_wiki wiki, int db_id);
}
class Split_tbl__page implements Split_tbl {
	public String				Tbl_name()		{return "page";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("page_id");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.wikis.data.tbls.Xowd_page_tbl(Db_conn_.Noop, Bool_.N).Flds__all(); return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return wiki.Data__core_mgr().Db__core().Conn();}
}
class Split_tbl__fsdb_fil implements Split_tbl {
	public String				Tbl_name()		{return "fsdb_fil";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("fil_id");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.fsdb.data.Fsd_fil_tbl(Db_conn_.Noop, Bool_.N, gplx.fsdb.meta.Fsm_mnt_mgr.Mnt_idx_main).flds; return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return wiki.File__mnt_mgr().Mnts__get_main().Atr_mgr().Db__core().Conn();}
}
class Split_tbl__fsdb_thm implements Split_tbl {
	public String				Tbl_name()		{return "fsdb_thm";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("thm_id");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.fsdb.data.Fsd_thm_tbl(Db_conn_.Noop, Bool_.N, gplx.fsdb.meta.Fsm_mnt_mgr.Mnt_idx_main, Bool_.Y).flds; return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return wiki.File__mnt_mgr().Mnts__get_main().Atr_mgr().Db__core().Conn();}
}
class Split_tbl__fsdb_reg implements Split_tbl {
	public String				Tbl_name()		{return "orig_reg";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("orig_ttl");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.files.origs.Xof_orig_tbl(Db_conn_.Noop, Bool_.N).flds; return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return wiki.File__mnt_mgr().Mnts__get_main().Atr_mgr().Db__core().Conn();}
}
class Split_tbl__srch_word implements Split_tbl {
	public String				Tbl_name()		{return "search_word";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("word_id");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.addons.wikis.searchs.dbs.Srch_word_tbl(Db_conn_.Noop).flds; return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return gplx.xowa.addons.wikis.searchs.Srch_search_addon.Get(wiki).Db_mgr().Tbl__word().conn;}
}
class Split_tbl__srch_link implements Split_tbl {
	public String				Tbl_name()		{return "search_link";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("word_id", "page_id");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.addons.wikis.searchs.dbs.Srch_link_tbl(Db_conn_.Noop).Flds(); return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return gplx.xowa.addons.wikis.searchs.Srch_search_addon.Get(wiki).Db_mgr().Tbl__link__get_at(db_id).conn;}
}
class Split_tbl__rndm_seq implements Split_tbl {
	public String				Tbl_name()		{return "rndm_seq";}
	public boolean					Layout_is_lot() {return Bool_.N;}
	public String[]				Fld_pkeys()		{return String_.Ary("mgr_idx", "rng_idx", "seq_idx");}
	public String				Fld_blob()		{return String_.Empty;}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.addons.wikis.pages.randoms.dbs.Rndm_seq_tbl(Db_conn_.Noop).Flds(); return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{return gplx.xowa.addons.wikis.pages.randoms.Rndm_addon.Get(wiki).Mgr().Db_mgr().Tbl__seq().Conn();}
}
class Split_tbl__html implements Split_tbl {
	public String				Tbl_name()		{return "html";}
	public boolean					Layout_is_lot() {return Bool_.Y;}
	public String[]				Fld_pkeys()		{return String_.Ary("page_id");}
	public String				Fld_blob()		{return "body";}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.xowa.htmls.core.dbs.Xowd_html_tbl(Db_conn_.Noop).Flds(); return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{
		if (db_id == -1)	// HACK: return core_conn just so that bin_tbl below can be created
			return wiki.Data__core_mgr().Db__core().Conn();
		else {
			gplx.xowa.wikis.data.Xow_db_file db_file = wiki.Data__core_mgr().Dbs__get_by_id_or_null(db_id);
			if (db_file == null) {
				db_file = wiki.Data__core_mgr().Dbs__make_by_id(db_id, gplx.xowa.wikis.data.Xow_db_file_.Tid__html_data, "0", 0, "-html-db." + Int_.To_str_pad_bgn_zero(db_id, 4) + ".xowa");
				db_file.Tbl__html().Create_tbl();
			}
			return db_file.Conn();
		}
	}	
}
class Split_tbl__fsdb_bin implements Split_tbl {
	public String				Tbl_name()		{return "fsdb_bin";}
	public boolean					Layout_is_lot() {return Bool_.Y;}
	public String[]				Fld_pkeys()		{return String_.Ary("bin_owner_id");}
	public String				Fld_blob()		{return "bin_data";}
	public Dbmeta_fld_list		Flds()			{if (flds == null) flds = new gplx.fsdb.data.Fsd_bin_tbl(Db_conn_.Noop, Bool_.N).Flds(); return flds;} private Dbmeta_fld_list flds;
	public Db_conn				Wiki_conn__get_or_new(Xow_wiki wiki, int db_id)	{
		if (db_id == -1)	// HACK: return core_conn just so that bin_tbl below can be created
			return wiki.Data__core_mgr().Db__core().Conn();
		else {
			gplx.fsdb.meta.Fsm_bin_mgr bin_mgr = wiki.File__mnt_mgr().Mnts__get_main().Bin_mgr();
			gplx.fsdb.meta.Fsm_bin_fil db_file = bin_mgr.Dbs__get_by_or_null(db_id);	// try to get existing
			if (db_file == null) {	// none exists; create new
				db_file = bin_mgr.Dbs__make(db_id, wiki.Domain_str() + "-file-db." + Int_.To_str_pad_bgn_zero(db_id, 4) + ".xowa");
				new gplx.fsdb.data.Fsd_bin_tbl(db_file.Conn(), Bool_.N).Create_tbl();
			}
			return db_file.Conn();
		}
	}
}
