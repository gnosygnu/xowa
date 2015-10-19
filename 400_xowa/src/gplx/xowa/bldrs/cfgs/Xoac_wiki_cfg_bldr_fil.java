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
package gplx.xowa.bldrs.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xoac_wiki_cfg_bldr_fil implements GfoInvkAble {
	public Xoac_wiki_cfg_bldr_fil(String wiki) {this.wiki = wiki;}
	public String Wiki() {return wiki;} private String wiki;
	public int Itms_count() {return list.Count();}
	public Xoac_wiki_cfg_bldr_cmd Itms_get_at(int i) {return (Xoac_wiki_cfg_bldr_cmd)list.Get_at(i);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_new_cmd_)) 		{Itms_add(m.ReadStr("id"), m.ReadStr("text"));}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_new_cmd_ = "new_cmd_";
	public Xoac_wiki_cfg_bldr_cmd Itms_add(String key, String text) {
		Xoac_wiki_cfg_bldr_cmd rv = new Xoac_wiki_cfg_bldr_cmd(key, text);
		list.Add(rv);
		return rv;
	}
	List_adp list = List_adp_.new_();
}
