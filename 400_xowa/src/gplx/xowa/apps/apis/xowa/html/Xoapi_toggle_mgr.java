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
package gplx.xowa.apps.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.cfgs.*;
public class Xoapi_toggle_mgr implements Gfo_invk {
	private Xoae_app app;
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public void Ctor_by_app(Xoae_app app) {this.app = app;}
	public void Init_by_app(Xoae_app app) {
		Io_url img_dir = app.Fsys_mgr().Bin_xowa_file_dir().GenSubDir_nest("app.general");
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoapi_toggle_itm itm = (Xoapi_toggle_itm)hash.Get_at(i);
			itm.Init_fsys(img_dir);
		}

		app.Cfg().Bind_many_app(this
		, Cfg__toggles__offline_wikis
		, Cfg__toggles__wikidata_langs
		, Cfg__toggles__claim
		, Cfg__toggles__slink_w
		, Cfg__toggles__slink_d
		, Cfg__toggles__slink_s
		, Cfg__toggles__slink_v
		, Cfg__toggles__slink_q
		, Cfg__toggles__slink_b
		, Cfg__toggles__slink_u
		, Cfg__toggles__slink_n
		, Cfg__toggles__slink_special
		, Cfg__toggles__label
		, Cfg__toggles__descr
		, Cfg__toggles__alias
		, Cfg__toggles__json
		);
	}
	public Xoapi_toggle_itm Get_or_new(String key_str) {
		byte[] key_bry = Bry_.new_u8(key_str);
		Xoapi_toggle_itm rv = (Xoapi_toggle_itm)hash.Get_by(key_bry);
		if (rv == null) {
			rv = new Xoapi_toggle_itm(app, key_bry);
			hash.Add(key_bry, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if (ctx.MatchIn(k
		, Cfg__toggles__offline_wikis
		, Cfg__toggles__wikidata_langs
		, Cfg__toggles__claim
		, Cfg__toggles__slink_w
		, Cfg__toggles__slink_d
		, Cfg__toggles__slink_s
		, Cfg__toggles__slink_v
		, Cfg__toggles__slink_q
		, Cfg__toggles__slink_b
		, Cfg__toggles__slink_u
		, Cfg__toggles__slink_n
		, Cfg__toggles__slink_special
		, Cfg__toggles__label
		, Cfg__toggles__descr
		, Cfg__toggles__alias
		, Cfg__toggles__json)) {
			this.Get_or_new(String_.Replace(k, "xowa.html.toggles.", "")).Visible_(m.ReadYn("v"));
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Cfg__toggles__offline_wikis		= "xowa.html.toggles.offline-wikis"
	, Cfg__toggles__wikidata_langs		= "xowa.html.toggles.wikidata-langs"
	, Cfg__toggles__claim				= "xowa.html.toggles.wikidatawiki-claim"
	, Cfg__toggles__slink_w				= "xowa.html.toggles.wikidatawiki-slink-wikipedia"
	, Cfg__toggles__slink_d				= "xowa.html.toggles.wikidatawiki-slink-wiktionary"
	, Cfg__toggles__slink_s				= "xowa.html.toggles.wikidatawiki-slink-wikisource"
	, Cfg__toggles__slink_v				= "xowa.html.toggles.wikidatawiki-slink-wikivoyage"
	, Cfg__toggles__slink_q				= "xowa.html.toggles.wikidatawiki-slink-wikiquote"
	, Cfg__toggles__slink_b				= "xowa.html.toggles.wikidatawiki-slink-wikibooks"
	, Cfg__toggles__slink_u				= "xowa.html.toggles.wikidatawiki-slink-wikiversity"
	, Cfg__toggles__slink_n				= "xowa.html.toggles.wikidatawiki-slink-wikinews"
	, Cfg__toggles__slink_special		= "xowa.html.toggles.wikidatawiki-slink-special"
	, Cfg__toggles__label				= "xowa.html.toggles.wikidatawiki-label"
	, Cfg__toggles__descr				= "xowa.html.toggles.wikidatawiki-descr"
	, Cfg__toggles__alias				= "xowa.html.toggles.wikidatawiki-alias"
	, Cfg__toggles__json				= "xowa.html.toggles.wikidatawiki-json"
	;
}
