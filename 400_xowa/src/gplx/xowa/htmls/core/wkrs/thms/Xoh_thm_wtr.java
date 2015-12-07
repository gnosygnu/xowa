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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.brys.args.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_thm_wtr extends gplx.core.brys.Bfr_arg_base {
	private final Bry_obj_ref div_0_align = Bry_obj_ref.New_empty();
	private final Bfr_arg__id div_1_id_val = new Bfr_arg__id();
	private final Bfr_arg__int div_1_width = new Bfr_arg__int(-1);
	private final Bfr_arg__html_atr 
	  div_1_id		= new Bfr_arg__html_atr(Html_atr_.Bry__id)
	, div_2_href	= new Bfr_arg__html_atr(Html_atr_.Bry__href)
	;
	private final Bfr_arg__bry_ary div_2_magnify = new Bfr_arg__bry_ary();
	private final Bfr_arg__bry div_2_alt = new Bfr_arg__bry(Bry_.Empty);
	private Bfr_arg div_1_img = Bfr_arg_.Noop, div_2_capt = Bfr_arg_.Noop;
	public Xoh_thm_wtr Div_0_align_(int v)					{div_0_align.Val_(gplx.xowa.parsers.lnkis.Xop_lnki_align_h_.To_bry(v)); return this;}
	public Xoh_thm_wtr Div_1_id_(int v)						{div_1_id.Set_by_arg(div_1_id_val.Set(Prefix__div_id, v)); return this;}
	public Xoh_thm_wtr Div_1_width_(int v)					{div_1_width.Set(v); return this;}
	public Xoh_thm_wtr Div_1_img_(Xoh_img_wtr v)			{div_1_img = v; return this;}
	public Xoh_thm_wtr Div_2_href_(Bfr_arg v)				{div_2_href.Set_by_arg(v); return this;}
	public Xoh_thm_wtr Div_2_magnify_(byte[]... v)	{div_2_magnify.Set(v); return this;}
	public Xoh_thm_wtr Div_2_capt_(Bfr_arg v)				{div_2_capt = v; return this;}
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	public Xoh_thm_wtr Div_2_alt_(boolean v, byte[] img_alt_bry) {
		if (v) {
//				img_alt.Bfr_arg__add(tmp_bfr);
//				byte[] img_alt_bry = tmp_bfr.To_bry_and_clear();
			alt_fmtr.Bld_bfr_many(tmp_bfr, img_alt_bry);
			div_2_alt.Set(tmp_bfr.To_bry_and_clear());
		}
		else
			div_2_alt.Set(Bry_.Empty);
		return this;
	}
	public Xoh_thm_wtr Clear() {
		Bfr_arg_.Clear(div_0_align, div_1_id, div_1_width, div_2_href, div_2_magnify, div_2_alt);
		div_1_img = div_2_capt = Bfr_arg_.Noop;
		return this;
	}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, div_0_align, div_1_id, div_1_width, div_1_img, div_2_href, div_2_magnify, div_2_capt, div_2_alt);
	}
	public static final byte[] Prefix__div_id = Bry_.new_a7("xothm_");
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div class=\"thumb t~{div_0_align}\">"
	,   "<div~{div_1_id} class=\"thumbinner\" style=\"width:~{div_1_width}px;\">~{div_1_img} "	// NOTE: trailing space is intentional; matches jtidy behavior
	,     "<div class=\"thumbcaption\">"
	,       "<div class=\"magnify\"><a~{div_2_href} class=\"internal\" title=\"Enlarge\"><img src=\"~{div_2_magnify}\" width=\"15\" height=\"11\" alt=\"\"></a></div>"
	,       "~{div_2_capt}</div>~{div_2_alt}"
	,   "</div>"
	, "</div>"
	), "div_0_align", "div_1_id", "div_1_width", "div_1_img", "div_2_href", "div_2_magnify", "div_2_capt", "div_2_alt");
	private static final Bry_fmtr alt_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	,     "<hr>"
	,     "<div class=\"thumbcaption\">~{alt}</div>"
	), "alt");
}
