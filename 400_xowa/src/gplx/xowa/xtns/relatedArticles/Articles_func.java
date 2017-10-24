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
package gplx.xowa.xtns.relatedArticles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.htmls.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Articles_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_relatedArticles;}
	@Override public Pf_func New(int id, byte[] name) {return new Articles_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = this.Eval_argx(ctx, src, caller, self);
		Articles_xtn_skin_itm xtn_itm = (Articles_xtn_skin_itm)ctx.Page().Html_data().Xtn_skin_mgr().Get_or_null(Articles_xtn_skin_itm.KEY);
		if (xtn_itm == null) {
			xtn_itm = new Articles_xtn_skin_itm();
			ctx.Page().Html_data().Xtn_skin_mgr().Add(xtn_itm);
		}
		Parse(xtn_itm, argx);
	}
	private void Parse(Articles_xtn_skin_itm xtn_itm, byte[] argx) {
		int pos = Bry_find_.Find_fwd(argx, Const_dlm);
		if (pos == Bry_find_.Not_found)						// && missing; argx is both ttl and text
			xtn_itm.Add(new Articles_itm(argx, argx));
		else {													// && exists; split by &&
			byte[] ttl	= Bry_.Trim(Bry_.Mid(argx, 0, pos));
			byte[] text = Bry_.Trim(Bry_.Mid(argx, pos + Const_dlm.length));
			xtn_itm.Add(new Articles_itm(ttl, text));
		}
	}
	public static final    Articles_func Instance = new Articles_func(); Articles_func() {}
	private static final    byte[] Const_dlm = new byte[] {Byte_ascii.Amp, Byte_ascii.Amp};
}
class Articles_itm {
	public Articles_itm(byte[] ttl, byte[] text) {
		this.ttl = ttl;
		this.text = Xoa_ttl.Replace_unders(text);	// placed in ctor, b/c called from two places above; DATE:2014-09-03
	}
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Text() {return text;} private byte[] text;
}
class Articles_itm_fmtr implements gplx.core.brys.Bfr_arg {
	private Xowe_wiki wiki; private List_adp itms;
	public void Init(Xowe_wiki wiki, List_adp itms) {this.wiki = wiki; this.itms = itms;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			Articles_itm itm = (Articles_itm)itms.Get_at(i);
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, itm.Ttl()); if (ttl == null) continue;
			fmtr.Bld_bfr(bfr, ttl.Full_db(), itm.Text());
		}
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_("\n      <li class=\"interwiki-relart\"><a href=\"/wiki/~{ttl}\">~{text}</a></li>",  "ttl", "text");
	public static final    Articles_itm_fmtr Instance = new Articles_itm_fmtr(); Articles_itm_fmtr() {}
}
class Articles_xtn_skin_itm implements Xopg_xtn_skin_itm {
	private List_adp itms = List_adp_.New();
	public byte Tid() {return Xopg_xtn_skin_itm_tid.Tid_sidebar;}
	public byte[] Key() {return KEY;} public static final    byte[] KEY = Bry_.new_a7("RelatedArticles");
	public void Add(Articles_itm itm) {itms.Add(itm);}
	public void Write(Bry_bfr bfr, Xoae_page page) {
		Xowe_wiki wiki = page.Wikie();
		itms_fmtr.Init(wiki, itms);
		html_fmtr.Bld_bfr_many(bfr, wiki.Msg_mgr().Val_by_key_obj("relatedarticles-title"), itms_fmtr);
	}
	private static final    Articles_itm_fmtr itms_fmtr = Articles_itm_fmtr.Instance;
	private static final    Bry_fmtr html_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(  "<div class=\"portal\" role=\"navigation\" id=\"p-relatedarticles\">"
	, "  <h3>~{h3}</h3>"
	, "  <div class=\"body\">"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	, "</div>"
	), "h3", "itms");
}
