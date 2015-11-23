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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.args.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*;
public class Xoh_lnke_wtr extends gplx.core.brys.Bfr_arg_base {
	private final Bfr_arg[] arg_ary;
	private final Bfr_arg__indent indent	= new Bfr_arg__indent();
	private final Bfr_arg__html_atr
	  anch_href			= new Bfr_arg__html_atr(Html_atr_.Bry__href)
	, anch_rel			= new Bfr_arg__html_atr(Html_atr_.Bry__rel)
	, anch_cls			= new Bfr_arg__html_atr(Html_atr_.Bry__class)
	; 
	private final Bfr_arg__wrapper anch_capt = new Bfr_arg__wrapper();
	public Xoh_lnke_wtr() {
		arg_ary = new Bfr_arg[] {indent, anch_href, anch_rel, anch_cls, anch_capt};
	}
	public Xoh_lnke_wtr Indent_(int v)								{indent.Set(v); return this;}
	public Xoh_lnke_wtr Anch_href_(byte[] src, int bgn, int end)	{anch_href.Set_by_mid(src, bgn, end); return this;}
	public Xoh_lnke_wtr Anch_rel_y_()								{anch_rel.Set_by_bry(Xoh_lnke_dict_.Html__rel__nofollow); return this;}
	public Xoh_lnke_wtr Anch_cls_(byte[]... ary)				{anch_cls.Set_by_ary(ary); return this;}
	public Xoh_lnke_wtr Anch_capt_(Bfr_arg v)						{anch_capt.Set(v); return this;}
	public Xoh_lnke_wtr Clear() {			
		for (Bfr_arg arg : arg_ary)
			arg.Bfr_arg__clear();
		return this;
	}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, (Object[])arg_ary);
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_
	( "~{indent}<a~{anch_href}~{anch_rel}~{anch_cls}>~{anch_capt}</a>"
	, "indent", "anch_href", "anch_rel", "anch_cls", "anch_capt");
}
