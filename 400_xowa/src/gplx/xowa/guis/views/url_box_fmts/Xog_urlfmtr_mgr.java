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
package gplx.xowa.guis.views.url_box_fmts;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.xowa.*;
public class Xog_urlfmtr_mgr implements Gfo_invk {
	private Xog_urlfmtr_itm wildcard = new Xog_urlfmtr_itm(AsciiByte.StarBry, BryUtl.NewA7("~{wiki_domain}/wiki/~{page_title}"));
	private final BryWtr bfr = BryWtr.New();
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public boolean Exists() {return exists;} private boolean exists = false;
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__url_format);
	}
	public void Parse(byte[] src) {
		// clear
		exists = false;
		wildcard = new Xog_urlfmtr_itm(AsciiByte.StarBry, BryUtl.NewA7("~{wiki_domain}/wiki/~{page_title}"));
		hash.Clear();

		// exit if blank
		if (BryUtl.IsNullOrEmpty(src)) return;

		// parse lines
		exists = true;
		byte[][] lines = BrySplit.SplitLines(src);
		for (byte[] line : lines) {
			byte[][] parts = BrySplit.Split(line, AsciiByte.Pipe);
			if (parts.length != 2) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "xog_urlfmtr:invalid_line; line=~{0}", line);
				continue;
			}
			byte[] domain = parts[0];
			Xog_urlfmtr_itm itm = new Xog_urlfmtr_itm(domain, parts[1]);
			if (BryLni.Eq(domain, AsciiByte.StarBry)) {
				wildcard = itm;
			}
			else {
				hash.AddIfDupeUseNth(domain, itm);
			}
		}
	}
	public String Gen(Xoa_url url) {
		Xog_urlfmtr_itm itm = (Xog_urlfmtr_itm)hash.GetByOrNull(url.Wiki_bry());
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
	private final BryFmt fmt;
	public Xog_urlfmtr_itm(byte[] wiki_domain, byte[] fmt_str) {
		this.fmt = BryFmt.New(fmt_str, "wiki_domain", "page_title", "page_title_spaces");
	}
	public String Gen(BryWtr bfr, Xoa_url url) {
		return fmt.Bld_many_to_str(bfr, url.Wiki_bry(), url.Page_bry(), BryUtl.Replace(url.Page_bry(), AsciiByte.Underline, AsciiByte.Space));
	}
}
