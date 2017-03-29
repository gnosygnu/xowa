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
package gplx.xowa.guis.views.url_box_fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
public class Xog_urlfmtr_mgr implements Gfo_invk {
	private Xog_urlfmtr_itm wildcard = new Xog_urlfmtr_itm(Byte_ascii.Star_bry, Bry_.new_a7("~{wiki_domain}/wiki/~{page_title}"));
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public boolean Exists() {return exists;} private boolean exists = false;
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__url_format);
	}
	public void Parse(byte[] src) {
		// clear
		exists = false;
		wildcard = new Xog_urlfmtr_itm(Byte_ascii.Star_bry, Bry_.new_a7("~{wiki_domain}/wiki/~{page_title}"));
		hash.Clear();

		// exit if blank
		if (Bry_.Len_eq_0(src)) return;

		// parse lines
		exists = true;
		byte[][] lines = Bry_split_.Split_lines(src);
		for (byte[] line : lines) {
			byte[][] parts = Bry_split_.Split(line, Byte_ascii.Pipe);
			if (parts.length != 2) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "xog_urlfmtr:invalid_line; line=~{0}", line);
				continue;
			}
			byte[] domain = parts[0];
			Xog_urlfmtr_itm itm = new Xog_urlfmtr_itm(domain, parts[1]);
			if (Bry_.Eq(domain, Byte_ascii.Star_bry)) {
				wildcard = itm;
			}
			else {
				hash.Add_if_dupe_use_nth(domain, itm);
			}
		}
	}
	public String Gen(Xoa_url url) {
		Xog_urlfmtr_itm itm = (Xog_urlfmtr_itm)hash.Get_by(url.Wiki_bry());
		if (itm == null) {
			itm = wildcard;
		}
		return itm.Gen(bfr, url);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__url_format)) {Parse(m.ReadBry("v"));}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Cfg__url_format = "xowa.gui.url_bar.url_format";
}
class Xog_urlfmtr_itm {
	private final    Bry_fmt fmt;
	public Xog_urlfmtr_itm(byte[] wiki_domain, byte[] fmt_str) {
		this.fmt = Bry_fmt.New(fmt_str, "wiki_domain", "page_title", "page_title_spaces");
	}
	public String Gen(Bry_bfr bfr, Xoa_url url) {
		return fmt.Bld_many_to_str(bfr, url.Wiki_bry(), url.Page_bry(), Bry_.Replace(url.Page_bry(), Byte_ascii.Underline, Byte_ascii.Space));
	}
}
