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
package gplx.xowa.addons.bldrs.app_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
public class Xoac_wiki_cfg_bldr_fil implements Gfo_invk {
	public Xoac_wiki_cfg_bldr_fil(String wiki) {this.wiki = wiki;}
	public String Wiki() {return wiki;} private String wiki;
	public int Itms_count() {return list.Count();}
	public Xoac_wiki_cfg_bldr_cmd Itms_get_at(int i) {return (Xoac_wiki_cfg_bldr_cmd)list.Get_at(i);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_new_cmd_)) 		{Itms_add(m.ReadStr("id"), m.ReadStr("text"));}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_new_cmd_ = "new_cmd_";
	public Xoac_wiki_cfg_bldr_cmd Itms_add(String key, String text) {
		Xoac_wiki_cfg_bldr_cmd rv = new Xoac_wiki_cfg_bldr_cmd(key, text);
		list.Add(rv);
		return rv;
	}
	List_adp list = List_adp_.New();
}
