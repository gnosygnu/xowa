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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.wikis.ttls.*;
public class Gallery_xnde_tst {
	private final    Xop_fxt fxt = new Xop_fxt(); String raw_src;
	@Before public void init() {fxt.Reset(); fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());}
	@Test   public void Lnki_no_caption() {
		fxt.Test_parse_page_wiki("<gallery>File:A.png</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")
			)
		));
	}
	@Test   public void Lnki_1() {
		fxt.Test_parse_page_wiki("<gallery>File:A.png|b</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")
			)
		));
	}
	@Test   public void Lnki_3() {
		fxt.Test_parse_page_wiki("<gallery>File:A.png|a\nFile:B.png|b\nFile:C.png|c</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")
			,	new_chkr_gallery_itm().Expd_lnki_("File:B.png")
			,	new_chkr_gallery_itm().Expd_lnki_("File:C.png")
			)
		));
	}
	@Test   public void Ignore_newLines() {
		fxt.Test_parse_page_wiki("<gallery>\n\n\nFile:A.png|a\n\n\nFile:B.png|b\n\n\n</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")
			,	new_chkr_gallery_itm().Expd_lnki_("File:B.png")
			)
		));
	}
	@Test   public void Only_first_pipe() {
		fxt.Test_parse_page_wiki("<gallery>File:A.png|File:B.png|cc</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")
			)
		));
	}
	@Test   public void Invalid_lnki() {
		fxt.Test_parse_page_wiki("<gallery>A.png|cc</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png")	// NOTE: MW converts "A.png" to "File:A.png"
			)
		));
	}
	@Test   public void File_only_trailing_nl() {
		fxt.Test_parse_page_wiki("<gallery>File:A.png\n</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png").Expd_caption_(null)
			)
		));
	}
	@Test   public void Invalid_curly() {
		raw_src = "a\n";			
		fxt.Test_parse_page_wiki("<gallery>File:A.png|" + raw_src + "}}</gallery>"	// NOTE: }} is ignored since it is not a valid title
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png").Expd_caption_("a")
			)
		));
	}
	@Test   public void Caption() {
		raw_src = "a<br/>c";
		fxt.Test_parse_page_wiki("<gallery>File:A.png|" + raw_src + "</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png").Expd_caption_(raw_src)
			)
		));
	}
	@Test   public void Xnde_atr() {
		raw_src = "<center>a<br/>b</center>";
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		(	"<gallery perrow=3>"
		,	"File:A.jpg|" + raw_src
		,	"</gallery>"
		) ,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
			(	new_chkr_gallery_mgr().Expd_subs_
				(	new_chkr_gallery_itm().Expd_lnki_("File:A.jpg").Expd_caption_(raw_src)
				)
			));
	}
	@Test   public void Err_pre() {	// PURPOSE: leading ws was failing; PAGE:en.w:Vlaardingen; "\nA.jpg| <center>Visbank</center>\n"
		raw_src = " <center>a</center>";
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		(	"<gallery>"
		,	"File:A.jpg|" + raw_src
		,	"</gallery>"
		) ,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
			(	new_chkr_gallery_mgr().Expd_subs_
				(	new_chkr_gallery_itm().Expd_lnki_("File:A.jpg").Expd_caption_("<center>a</center>")
				)
			));
	}
	@Test   public void Err_comment() {	// PURPOSE: comment was being rendered; PAGE:en.w:Perpetual motion; <!-- removed A.jpg|bcde -->
		raw_src = "b";
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
		(	"<gallery>"
		,	"<!-- deleted A.jpg|" + raw_src
		,	"</gallery>"
		) ,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
			(	new_chkr_gallery_mgr().Expd_subs_()
			)
		);
	}
	@Test   public void Misc_atr() {		// make sure misc attribute doesn't fail
		raw_src = "b";
		fxt.Test_parse_page_wiki("<gallery id=a>File:A.png|" + raw_src + "</gallery>"
		,	fxt.tkn_xnde_().Xnde_tagId_(Xop_xnde_tag_.Tid__gallery).Xnde_xtn_
		(	new_chkr_gallery_mgr().Expd_subs_
			(	new_chkr_gallery_itm().Expd_lnki_("File:A.png").Expd_caption_(raw_src)
			)
		));
	}
	private Gallery_mgr_data_chkr new_chkr_gallery_mgr()	{return new Gallery_mgr_data_chkr();}
	private Gallery_itm_chkr new_chkr_gallery_itm()	{return new Gallery_itm_chkr();}
}
class Gallery_mgr_data_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Gallery_xnde.class;}
	public Gallery_itm_chkr[] Expd_subs() {return expd_subs;} public Gallery_mgr_data_chkr Expd_subs_(Gallery_itm_chkr... v) {expd_subs = v; return this;} Gallery_itm_chkr[] expd_subs = null;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gallery_xnde actl = (Gallery_xnde)actl_obj;
		int rv = 0;
		rv += Chk_basic(mgr, path, actl, rv);
		rv += Chk_subs(mgr, path, actl, rv);
		return rv;
	}
	public int Chk_basic(Tst_mgr mgr, String path, Gallery_xnde actl, int err) {
		return err;
	}
	public int Chk_subs(Tst_mgr mgr, String path, Gallery_xnde actl, int err) {
		if (expd_subs != null) {
			int actl_subs_len = actl.Itms_len();
			Gallery_itm[] actl_subs = new Gallery_itm[actl_subs_len];  
			for (int i = 0; i < actl_subs_len; i++)
				actl_subs[i] = actl.Itms_get_at(i);
			return mgr.Tst_sub_ary(expd_subs, actl_subs, path, err);
		}
		return err;
	}
}
class Gallery_itm_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Gallery_itm.class;}
	public Gallery_itm_chkr Expd_lnki_(String v)	{expd_lnki = Xoa_ttl_chkr.new_(v); return this;} private Xoa_ttl_chkr expd_lnki;
	public Gallery_itm_chkr Expd_caption_(String v) {expd_caption = v; return this;} private String expd_caption;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gallery_itm actl = (Gallery_itm)actl_obj;
		int err = 0;
		err += mgr.Tst_sub_obj(expd_lnki, actl.Ttl(), path, err);
		err += mgr.Tst_val(expd_caption == null, "", "caption", expd_caption, String_.new_u8(actl.Caption_bry()));
		return err;
	}
}
