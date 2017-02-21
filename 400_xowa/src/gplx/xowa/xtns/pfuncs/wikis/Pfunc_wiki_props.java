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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.wikis.metas.*;
public class Pfunc_wiki_props extends Pf_func_base {
	public Pfunc_wiki_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		Xow_wiki_props props = ctx.Wiki().Props();
	    switch (id) {
			case Xol_kwd_grp_.Id_site_sitename:			bfr.Add(props.Site_name()); break;
			case Xol_kwd_grp_.Id_site_server:			bfr.Add(props.Server()); break;
			case Xol_kwd_grp_.Id_site_servername:		bfr.Add(props.Server_name()); break;
			case Xol_kwd_grp_.Id_site_articlepath:		bfr.Add(props.Article_path()); break;
			case Xol_kwd_grp_.Id_site_scriptpath:		bfr.Add(props.Script_path()); break;
			case Xol_kwd_grp_.Id_site_stylepath:		bfr.Add(props.Style_path()); break;
			case Xol_kwd_grp_.Id_site_directionmark:	bfr.Add(props.Direction_mark()); break;
			case Xol_kwd_grp_.Id_site_currentversion:	bfr.Add(props.Current_version()); break;
			case Xol_kwd_grp_.Id_site_contentlanguage:	bfr.Add(ctx.Page().Lang().Key_bry()); break;
			default:									throw Err_.new_unhandled(id);
		}
	}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_wiki_props(id).Name_(name);}
	public static final    Pfunc_wiki_props Instance = new Pfunc_wiki_props(-1);
}
