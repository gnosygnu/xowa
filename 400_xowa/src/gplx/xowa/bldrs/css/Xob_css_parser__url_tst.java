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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_css_parser__url_tst {
	@Before public void init() {fxt.Clear();} private Xob_css_parser__url_fxt fxt = new Xob_css_parser__url_fxt();
	@Test   public void Quote_none()		{fxt.Test_parse_url(" url(//site/A.png) b"			, " url('site/A.png')");}
	@Test   public void Quote_apos()		{fxt.Test_parse_url(" url('//site/A.png') b"		, " url('site/A.png')");}
	@Test   public void Quote_quote()		{fxt.Test_parse_url(" url(\"//site/A.png\") b"		, " url(\"site/A.png\")");}
	@Test   public void Base64()			{fxt.Test_parse_base64(" url('data:image/png;base64,BASE64DATA;ABC=') b", " url('data:image/png;base64,BASE64DATA;ABC=')");}
	@Test   public void Base64_dangling()	{fxt.Test_parse_warn(" url('data:image/png;base64,BASE64DATA;ABC=' ", " url('data:image/png;base64,BASE64DATA;ABC='", "base64 dangling");}
	@Test   public void Warn_eos()			{fxt.Test_parse_warn(" url("						, " url("			, "EOS");}
	@Test   public void Warn_dangling()		{fxt.Test_parse_warn(" url(a"						, " url("			, "dangling");}
	@Test   public void Warn_empty()		{fxt.Test_parse_warn(" url()"						, " url("			, "empty");}
	@Test   public void Warn_site()			{fxt.Test_parse_warn(" url('//site')"				, " url('//site')"	, "invalid");}
}
class Xob_css_parser__url_fxt {
	protected Xob_css_parser__url url_parser; private final    Bry_bfr bfr = Bry_bfr_.New_w_size(32);
	protected Xob_css_tkn__base cur_frag; protected byte[] src_bry;
	@gplx.Virtual public void Clear() {
		url_parser = new Xob_css_parser__url(Bry_.new_a7("site"));
	}
	protected void Exec_parse(String src_str, int expd_tid, String expd_str) {
		this.src_bry = Bry_.new_u8(src_str);
		this.Exec_parse_hook();
		cur_frag.Write(bfr, src_bry);
		String actl_str = bfr.To_str_and_clear();
		Tfds.Eq(expd_tid, cur_frag.Tid(), "wrong tid; expd={0}, actl={1}", expd_tid, cur_frag.Tid());
		Tfds.Eq(expd_str, actl_str);
	}
	@gplx.Virtual protected void Exec_parse_hook() {
		this.cur_frag = url_parser.Parse(src_bry, src_bry.length, 0, 5); // 5=" url(".length
	}
	public void Test_parse_url(String src_str, String expd) {
		Exec_parse(src_str, Xob_css_tkn__base.Tid_url, expd);
	}
	public void Test_parse_base64(String src_str, String expd) {
		Exec_parse(src_str, Xob_css_tkn__base.Tid_base64, expd);
	}
	public void Test_parse_warn(String src_str, String expd, String warn) {
		Exec_parse(src_str, Xob_css_tkn__base.Tid_warn, expd);
		Xob_css_tkn__warn sub_frag = (Xob_css_tkn__warn)cur_frag;
		Tfds.Eq(true, String_.Has(sub_frag.Fail_msg(), warn));
	}
}	
