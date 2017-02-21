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
package gplx.xowa.addons.apps.cfgs.specials.edits.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.services.*;
public class Xocfg_edit_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {			
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		
		String grp = url_args.Read_str_or("grp", "");
		String ctx = url_args.Read_str_or("ctx", "app");
		String lang = url_args.Read_str_or("lang", wiki.App().User().Wikii().Lang().Key_str());

		Xocfg_edit_loader loader = Xocfg_edit_loader.New(wiki.App());
		if (String_.Eq(grp, "")) {
			grp = wiki.App().Cfg().Get_str_app(Cfg__previous_grp);
			if (!loader.Grp_key_exists(grp)) {
				grp = "xowa.app.security";
				Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:grp_key not found; defaulting to xowa.app.security; key=~{0}", grp);
			}
		}
		new Xocfg_edit_html(loader, grp, ctx, lang).Bld_page_by_mustache(wiki.App(), page, this);
	}

	Xocfg_edit_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xocfg_edit_special(Xow_special_meta.New_xo("XowaCfg", "Options"));
	public static final String Cfg__previous_grp = "xowa.app.cfg.previous_grp";
}
