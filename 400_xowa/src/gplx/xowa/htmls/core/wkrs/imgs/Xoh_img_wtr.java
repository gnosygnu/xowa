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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.brys.args.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_img_wtr extends gplx.core.brys.Bfr_arg_base {
	private final Bfr_arg[] arg_ary;
	private final Bfr_arg__indent indent	= new Bfr_arg__indent();
	private final Bfr_arg__html_atr
	  anch_href			= new Bfr_arg__html_atr(Html_atr_.Bry__href)
	, anch_cls			= new Bfr_arg__html_atr(Html_atr_.Bry__class)
	, anch_title		= new Bfr_arg__html_atr(Html_atr_.Bry__title)
	, img_id			= new Bfr_arg__html_atr(Html_atr_.Bry__id)
	, img_xoimg			= new Bfr_arg__html_atr(Xoh_img_xoimg_parser.Bry__name)
	, img_alt			= new Bfr_arg__html_atr(Html_atr_.Bry__alt)
	, img_src			= new Bfr_arg__html_atr(Html_atr_.Bry__src)
	, img_w				= new Bfr_arg__html_atr(Html_atr_.Bry__width)
	, img_h				= new Bfr_arg__html_atr(Html_atr_.Bry__height)
	, img_cls			= new Bfr_arg__html_atr(Html_atr_.Bry__class)
	; 
	private final Bfr_arg__id img_id_val = new Bfr_arg__id();
	public Xoh_img_wtr() {
		arg_ary = new Bfr_arg[] 
		{ indent, anch_href, anch_cls, anch_title
		, img_id, img_xoimg, img_alt, img_src, img_w, img_h, img_cls
		};
	}
	public Xoh_img_wtr Indent_(int v)							{indent.Set(v); return this;}
	public Xoh_img_wtr Anch_href_(Bfr_arg v)					{anch_href.Set_by_arg(v); return this;}
	public Xoh_img_wtr Anch_cls_(Bfr_arg v)						{anch_cls.Set_by_arg(v); return this;}
	public Xoh_img_wtr Anch_title_(Bfr_arg v)					{anch_title.Set_by_arg(v); return this;}
	public Xoh_img_wtr Img_id_(byte[] prefix, int uid)			{img_id.Set_by_arg(img_id_val.Set(prefix, uid)); return this;}
	public Xoh_img_wtr Img_w_(int v)							{img_w.Set_by_int(v); return this;}
	public Xoh_img_wtr Img_h_(int v)							{img_h.Set_by_int(v); return this;}
	public Xoh_img_wtr Img_cls_(Bfr_arg v)						{img_cls.Set_by_arg(v); return this;}
	public Xoh_img_wtr Img_src_(Bfr_arg v)						{img_src.Set_by_arg(v); return this;}
	public Xoh_img_wtr Img_alt_(Bfr_arg v)						{img_alt.Set_by_arg(v); return this;}
	public Xoh_img_wtr Clear() {			
		for (Bfr_arg arg : arg_ary)
			arg.Bfr_arg__clear();
		return this;
	}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, (Object[])arg_ary);
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_
	( "~{indent}<a~{anch_href}~{anch_cls}~{anch_title}><img~{img_id}~{img_alt}~{img_xoimg}~{img_src}~{img_w}~{img_h}~{img_cls}></a>"
	, "indent", "anch_href", "anch_cls", "anch_title", "img_id", "img_xoimg", "img_alt", "img_src", "img_w", "img_h", "img_cls");
}
