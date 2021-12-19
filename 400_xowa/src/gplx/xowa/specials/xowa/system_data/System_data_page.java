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
package gplx.xowa.specials.xowa.system_data;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.wrappers.ByteVal;
import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.core.net.qargs.*;
import gplx.xowa.langs.*;
public class System_data_page implements Xow_special_page {
	private Gfo_qarg_mgr_old arg_hash = new Gfo_qarg_mgr_old();
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__system_data;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		arg_hash.Load(url.Qargs_ary());
		byte[] file_type = arg_hash.Get_val_bry_or(Arg_type, null); if (file_type == null) return;
		ByteVal type_val = (ByteVal)type_hash.Get_by_bry(file_type); if (type_val == null) return;
		Io_url file_url = Path_from_type(wiki, type_val.Val()); if (file_url == null) return;

		// get log text
		byte[] file_txt = Io_mgr.Instance.LoadFilBry(file_url);
		file_txt = gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(file_txt, true, false, false, false, false);	// escape < or "</pre>" in messages will cause pre to break
		if (file_txt.length > IoConsts.LenMB) {
			int file_txt_len = file_txt.length;
			file_txt = BryUtl.Add
			( BryUtl.NewA7("*** truncated to 1 MB due to large file size: " + IntUtl.ToStr(file_txt_len) + " ***\n\n")
			, BryLni.Mid(file_txt, file_txt_len - IoConsts.LenMB, file_txt_len));
		}

		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetM001();
		fmtr_all.BldToBfrMany(tmp_bfr, file_url.Raw(), file_txt);
		page.Db().Text().Text_bry_(tmp_bfr.ToBryAndRls());
	}
	private static Io_url Path_from_type(Xowe_wiki wiki, byte type) {
		Xoae_app app = wiki.Appe();
		switch (type) {
			case Type_log_session:			return app.Log_wtr().Session_fil();
			case Type_cfg_app:				return app.Fsys_mgr().Cfg_app_fil();
			case Type_cfg_lang:				return Xol_lang_itm_.xo_lang_fil_(app.Fsys_mgr(), wiki.Lang().Key_str());
			case Type_usr_history:			return app.Usere().Fsys_mgr().App_data_history_fil();
			default:						return null;
		}
	}
	private static final byte[] Arg_type = BryUtl.NewA7("type");
	private static final byte Type_log_session = 1, Type_cfg_app = 2, Type_cfg_lang = 3, Type_usr_history = 6;
	private static final Hash_adp_bry type_hash = Hash_adp_bry.cs()
	.Add_str_byte("log_session"		, Type_log_session)
	.Add_str_byte("cfg_app"			, Type_cfg_app)
	.Add_str_byte("cfg_lang"		, Type_cfg_lang)
	.Add_str_byte("usr_history"		, Type_usr_history)
	;
	private BryFmtr fmtr_all = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "<p><b>Path</b>: <code>~{path}</code>"
	, "</p>"
	, "<pre style='overflow:auto;'>"
	, "~{text}</pre>"
	), "path", "text");

	public Xow_special_page Special__clone() {return this;}
}
