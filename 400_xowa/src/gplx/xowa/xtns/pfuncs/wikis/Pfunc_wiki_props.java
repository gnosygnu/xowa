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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_wiki_props extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr rslt_bfr) {
		Xow_wiki_props props = ctx.Wiki().Props();
	    switch (id) {
			case Xol_kwd_grp_.Id_site_sitename: rslt_bfr.Add(props.SiteName()); break;
			case Xol_kwd_grp_.Id_site_server: rslt_bfr.Add(props.Server()); break;
			case Xol_kwd_grp_.Id_site_servername: rslt_bfr.Add(props.ServerName()); break;
			case Xol_kwd_grp_.Id_site_articlepath: rslt_bfr.Add(props.ArticlePath()); break;
			case Xol_kwd_grp_.Id_site_scriptpath: rslt_bfr.Add(props.ScriptPath()); break;
			case Xol_kwd_grp_.Id_site_stylepath: rslt_bfr.Add(props.StylePath()); break;
			case Xol_kwd_grp_.Id_site_contentlanguage: rslt_bfr.Add(ctx.Cur_page().Lang().Key_bry()); break;
			case Xol_kwd_grp_.Id_site_directionmark: rslt_bfr.Add(props.DirectionMark()); break;	// FUTURE: find page that uses it
			case Xol_kwd_grp_.Id_site_currentversion: rslt_bfr.Add(props.CurrentVersion()); break;
			default: throw Err_.unhandled(id);
		}
	}
	public Pfunc_wiki_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_wiki_props(id).Name_(name);}
	public static final Pfunc_wiki_props _ = new Pfunc_wiki_props(-1);
}
