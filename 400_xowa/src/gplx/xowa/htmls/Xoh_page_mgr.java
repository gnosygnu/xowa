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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.portal.*;
public class Xoh_page_mgr implements Gfo_invk {
	private boolean font_enabled = false;
	private String font_name = "Arial";
	private byte[] font_css_bry = Bry_.Empty, custom_script = Bry_.Empty;
	private final    Bry_fmt font_css_fmt = Bry_fmt.Auto("body {font-family: ~{font_name}; font-size: ~{font_size}px;}");
	public float Font_size() {return font_size;} private float font_size = Font_size_default;
	private void Font_css_bry_() {
		font_css_bry = font_css_fmt.Bld_many_to_bry(Bry_bfr_.New(), font_name, font_size);
	}
	public void Write_css(gplx.xowa.htmls.heads.Xoh_head_wtr wtr) {
		if (font_enabled)
			wtr.Write_css_style_itm(font_css_bry);
		if (Bry_.Len_gt_0(custom_script))
			wtr.Write_css_style_itm(custom_script);
	}

	public Bry_fmt Content_code_fmt() {return content_code_fmt;} private final    Bry_fmt content_code_fmt = Bry_fmt.Auto("<pre>~{page_text}</pre>");
	public Xoh_subpages_bldr Subpages_bldr() {return subpages_bldr;} private final    Xoh_subpages_bldr subpages_bldr = new Xoh_subpages_bldr();
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__font_enabled, Cfg__font_name, Cfg__font_size, Cfg__font_format, Cfg__custom_script, Cfg__content_code_fmt);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__font_enabled))			font_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__font_name))				{font_name = m.ReadStr("v"); Font_css_bry_();}
		else if	(ctx.Match(k, Cfg__font_size))				{font_size = m.ReadFloat("v"); Font_css_bry_();}
		else if	(ctx.Match(k, Cfg__font_format))			{font_css_fmt.Fmt_(m.ReadBry("v")); Font_css_bry_();}
		else if	(ctx.Match(k, Cfg__custom_script))			custom_script = m.ReadBry("v");
		else if	(ctx.Match(k, Cfg__content_code_fmt))		content_code_fmt.Fmt_(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}

	public static final String
	  Cfg__font_enabled			= "xowa.html.css.font.enabled"
	, Cfg__font_size			= "xowa.html.css.font.size"
	;
	private static final String
	  Cfg__font_name			= "xowa.html.css.font.name"
	, Cfg__font_format			= "xowa.html.css.font.format"
	, Cfg__custom_script		= "xowa.html.css.custom.script"
	, Cfg__content_code_fmt		= "xowa.html.page.content_code_fmt"
	;
	public static final float Font_size_default = 16;
}
