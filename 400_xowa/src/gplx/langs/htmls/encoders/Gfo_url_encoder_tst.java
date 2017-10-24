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
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Gfo_url_encoder_tst {
	private final    Gfo_url_encoder_fxt fxt = new Gfo_url_encoder_fxt();
	@Test  public void Id__nums() 			{fxt.Encoder_id().Test__bicode("0123456789"					, "0123456789");}
	@Test  public void Id__ltrs_lower() 	{fxt.Encoder_id().Test__bicode("abcdefghijklmnopqrstuvwxyz"	, "abcdefghijklmnopqrstuvwxyz");}
	@Test  public void Id__ltrs_upper() 	{fxt.Encoder_id().Test__bicode("ABCDEFGHIJKLMNOPQRSTUVWXYZ"	, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");}
	@Test  public void Id__syms() 			{fxt.Encoder_id().Test__encode("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", ".21.22.23.24.25.26.27.28.29.2A.2B.2C-..2F:.3B.3C.3D.3E.3F.40.5B.5C.5D.5E_.60.7B.7C.7D.7E");}	// NOTE: not reversible since "." is encode_marker but not encoded
	@Test  public void Id__foreign() 		{fxt.Encoder_id().Test__bicode("aéb", "a.C3.A9b");}
	@Test  public void Id__nbsp() 			{fxt.Encoder_id().Test__encode("a&nbsp;b", "a.C2.A0b");}	// NOTE: not just .A0 (160) but utf8-encoded .C2.A0
	@Test  public void Id__space() 			{fxt.Encoder_id().Test__bicode("a b", "a_b");}
	@Test  public void Id__err()  {
		byte[] raw = Bry_.new_a7("0%.jpg");
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		fxt.Encoder_id().Encoder().Decode(tmp_bfr, Bool_.N, raw, 0, raw.length);
		Tfds.Eq("0%.jpg", tmp_bfr.To_str_and_clear()); 
	}
	@Test  public void Ttl__syms__diff() 	{fxt.Encoder_ttl().Test__encode(" &'=+", "_%26%27%3D%2B");}
	@Test  public void Ttl__syms__same() 	{fxt.Encoder_ttl().Test__encode("!\"#$%()*,-./:;<>?@[\\]^_`{|}~", "!\"#$%()*,-./:;<>?@[\\]^_`{|}~");}
	@Test  public void Url__syms() 			{fxt.Encoder_url().Test__bicode("!?^~", "%21%3F%5E%7E");}
	@Test  public void Url__foreign() 		{fxt.Encoder_url().Test__bicode("aéb", "a%C3%A9b");}
	@Test  public void Url__space() 		{fxt.Encoder_url().Test__bicode("a b", "a+b");}
	@Test  public void Href__space() 		{
		fxt.Encoder_href().Test__encode("a b", "a_b");
	}
	@Test  public void Href__special_and_anchor() { // PURPOSE: MediaWiki encodes with % for ttls, but . for anchors; REF:Title.php!(before-anchor)getLocalUrl;wfUrlencode (after-anchor)escapeFragmentForURL
		fxt.Encoder_href().Test__bicode("^#^", "%5E#.5E");
		fxt.Encoder_href().Test__encode("A#", "A#");
	}
	@Test  public void Href__invalid() { // PURPOSE: check that invalid url decodings are rendered literally; DATE:2014-04-10
		fxt.Encoder_href().Test__encode("%GC", "%25GC");
	}
	@Test  public void Fsys__wnt() 		{
		fxt.Encoder_fsys_safe().Test__encode("Options/HTML", "Options%2FHTML");
	}
}
class Gfo_url_encoder_fxt {
	public Gfo_url_encoder Encoder() {return encoder;} private Gfo_url_encoder encoder;
	public Gfo_url_encoder_fxt Encoder_id()			{encoder = Gfo_url_encoder_.Id; return this;}
	public Gfo_url_encoder_fxt Encoder_href()		{encoder = Gfo_url_encoder_.Href; return this;}
	public Gfo_url_encoder_fxt Encoder_url()		{encoder = Gfo_url_encoder_.Http_url; return this;}
	public Gfo_url_encoder_fxt Encoder_ttl()		{encoder = Gfo_url_encoder_.Mw_ttl; return this;}
	public Gfo_url_encoder_fxt Encoder_fsys_safe()	{encoder = Gfo_url_encoder_.New__fsys_wnt().Make(); return this;}
	public void Test__bicode(String raw, String encoded) {
		Test__encode(raw, encoded);
		Test__decode(encoded, raw);
	}	
	public void Test__encode(String raw, String expd) {
		Tfds.Eq(expd, String_.new_u8(encoder.Encode(Bry_.new_u8(raw))));
	}
	public void Test__decode(String raw, String expd) {
		Tfds.Eq(expd, String_.new_u8(encoder.Decode(Bry_.new_u8(raw))));		
	}
}
