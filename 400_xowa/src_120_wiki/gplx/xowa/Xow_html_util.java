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
package gplx.xowa; import gplx.*;
public class Xow_html_util implements GfoInvkAble {
	public Xow_html_util(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_if_bool))			return If_bool(m.ReadStr("expr"), m.ReadStr("true_val"), m.ReadStr("false_val"));
		else if	(ctx.Match(k, Invk_if_yn))				return If_yn(m.ReadStr("expr"), m.ReadStr("true_val"), m.ReadStr("false_val"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_if_bool = "if_bool", Invk_if_yn = "if_yn";
	String If_bool(String expr, String true_val, String false_val) {
		Object o = wiki.Appe().Gfs_mgr().Run_str(expr);
		try {return Bool_.cast_(o) ? true_val : false_val;}
		catch (Exception e) {Err_.Noop(e); return "expr failed: " + expr;}
	}
	String If_yn(String expr, String true_val, String false_val) {
		String o = String_.as_(wiki.Appe().Gfs_mgr().Run_str(expr));
		try {return Yn.parse_(o) ? true_val : false_val;}
		catch (Exception e) {Err_.Noop(e); return "expr failed: " + expr;}
	}
}
