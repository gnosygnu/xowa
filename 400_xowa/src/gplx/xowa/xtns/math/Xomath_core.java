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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.logs.*;
public class Xomath_core implements Gfo_invk {
	private final    Xomath_html_wtr html_wtr = new Xomath_html_wtr();
	private Xop_log_basic_wkr log_wkr;
	public boolean   Enabled()				{return enabled;}				private boolean enabled = true;
	public boolean   Renderer_is_mathjax()	{return renderer_is_mathjax;}	private boolean renderer_is_mathjax = true;
	public void Renderer_is_mathjax_(boolean v) {renderer_is_mathjax = v;}		// TEST:
	public void Init_by_wiki(Xow_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__enabled, Cfg__renderer);
	}
	public void Log_wkr_(Xop_log_wkr_factory factory) {
		this.log_wkr = factory.Make__generic().Save_src_str_(Bool_.Y);
	}
	public void Write(Bry_bfr bfr, Xop_ctx ctx, Xop_xnde_tkn xnde, byte[] src) {
		if (log_wkr != null) log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_math, src, xnde);
		html_wtr.Write(bfr, ctx, xnde, src, !renderer_is_mathjax, enabled);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__enabled))		enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__renderer))		renderer_is_mathjax = String_.Eq(m.ReadStr("v"), "mathjax");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__enabled		= "xowa.addon.math.enabled"
	, Cfg__renderer		= "xowa.addon.math.renderer";
}
