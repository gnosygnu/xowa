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
package gplx.xowa.specials.xowa.system_data; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.primitives.*;
public class System_data_page implements Xows_page {
	private Xoa_url_arg_hash arg_hash = new Xoa_url_arg_hash();
	public void Special_gen(Xoa_url url, Xoae_page page, Xowe_wiki wiki, Xoa_ttl ttl) {
		arg_hash.Load(url);
		byte[] file_type = arg_hash.Get_val_bry_or(Arg_type, null); if (file_type == null) return;
		Byte_obj_val type_val = (Byte_obj_val)type_hash.Get_by_bry(file_type); if (type_val == null) return; 
		Io_url file_url = Path_from_type(wiki, type_val.Val()); if (file_url == null) return;
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		byte[] file_txt = Io_mgr._.LoadFilBry(file_url);
		file_txt = gplx.html.Html_utl.Escape_html_as_bry(file_txt, true, false, false, false, false);	// escape < or "</pre>" in messages will cause pre to break
		fmtr_all.Bld_bfr_many(tmp_bfr, file_url.Raw(), file_txt);
		page.Data_raw_(tmp_bfr.Mkr_rls().Xto_bry_and_clear());
	}
	private static Io_url Path_from_type(Xowe_wiki wiki, byte type) {
		Xoae_app app = wiki.Appe();
		switch (type) {
			case Type_log_session:			return app.Log_wtr().Session_fil();
			case Type_cfg_app:				return app.Fsys_mgr().Root_dir().GenSubFil("xowa.gfs");
			case Type_cfg_lang:				return Xol_lang_.xo_lang_fil_(app.Fsys_mgr(), wiki.Lang().Key_str());
			case Type_cfg_user:				return app.User().Fsys_mgr().App_data_cfg_user_fil();
			case Type_cfg_custom:			return app.User().Fsys_mgr().App_data_cfg_custom_fil();
			case Type_usr_history:			return app.User().Fsys_mgr().App_data_history_fil();
			default:						return null;
		}
	}

	private static final byte[] Arg_type = Bry_.new_ascii_("type");
	private static final byte Type_log_session = 1, Type_cfg_app = 2, Type_cfg_lang = 3, Type_cfg_user = 4, Type_cfg_custom = 5, Type_usr_history = 6;
	private static final Hash_adp_bry type_hash = Hash_adp_bry.cs_()
	.Add_str_byte("log_session"		, Type_log_session)
	.Add_str_byte("cfg_app"			, Type_cfg_app)
	.Add_str_byte("cfg_lang"		, Type_cfg_lang)
	.Add_str_byte("cfg_user"		, Type_cfg_user)
	.Add_str_byte("cfg_custom"		, Type_cfg_custom)
	.Add_str_byte("usr_history"		, Type_usr_history)
	;
	private Bry_fmtr fmtr_all = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<p><b>Path</b>: <code>~{path}</code>"
	, "</p>"
	, "<pre style='overflow:auto;'>"
	, "~{text}</pre>"
	), "path", "text");
}
