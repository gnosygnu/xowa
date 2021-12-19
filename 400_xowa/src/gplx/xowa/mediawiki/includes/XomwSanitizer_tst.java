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
package gplx.xowa.mediawiki.includes;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.mediawiki.includes.xohtml.Xomw_atr_mgr;
import org.junit.Test;
public class XomwSanitizer_tst {
	private final XomwSanitizer_fxt fxt = new XomwSanitizer_fxt();
	@Test public void Normalize__text()                  {fxt.Test__normalize_char_references("abc"                      , "abc");}
	@Test public void Normalize__dec()                   {fxt.Test__normalize_char_references("&#08;"                    , "&amp;#08;");}
	@Test public void Normalize__dec__invalid()          {fxt.Test__normalize_char_references("&#09;"                    , "&#9;");}
	@Test public void Normalize__hex()                   {fxt.Test__normalize_char_references("&#xFF;"                   , "&#xff;");}
	@Test public void Normalize__entity()                {fxt.Test__normalize_char_references("&alpha;"                  , "&#945;");}
	@Test public void Normalize__entity__lt()            {fxt.Test__normalize_char_references("&lt;"                     , "&lt;");}
	@Test public void Normalize__entity__alias()         {fxt.Test__normalize_char_references("&רלמ;"                    , "&rlm;");}
	@Test public void Normalize__amp()                   {fxt.Test__normalize_char_references("a&b"                      , "a&amp;b");}
	@Test public void Normalize__invalid()               {fxt.Test__normalize_char_references("&(invalid);"              , "&amp;(invalid);");}
	@Test public void Normalize__many() {
		fxt.Test__normalize_char_references
		( "a &#09; b &alpha; c &#xFF; d &(invalid); e"
		, "a &#9; b &#945; c &#xff; d &amp;(invalid); e"
		);
	}
	@Test public void Regex__domain() {
		Xomw_regex_find_domain regex_domain = new Xomw_regex_find_domain();
		// normal
		fxt.Test__regex_domain_y(regex_domain, "https://a.org/bcd", "https:", "//a.org", "/bcd");
		// trailing backslash
		fxt.Test__regex_domain_y(regex_domain, "https://a.org/", "https:", "//a.org", "/");
		// domain only
		fxt.Test__regex_domain_y(regex_domain, "https://a.org", "https:", "//a.org", "");
		// colon not found
		fxt.Test__regex_domain_n(regex_domain, "https//a.org/bcd");
		// host_bgn.eos
		fxt.Test__regex_domain_n(regex_domain, "https:");
		// host_bgn.//
		fxt.Test__regex_domain_n(regex_domain, "https:a//");
		// host_bgn.///
		fxt.Test__regex_domain_n(regex_domain, "https:///a.org/b");
	}
	@Test public void Regex__clean_url() {
		Xomw_regex_escape_invalid regex = new Xomw_regex_escape_invalid();
		// noop
		fxt.Test__regex_escape_invalid(regex, "https://a.org/bcd", BoolUtl.N, "");
		// symbols
		fxt.Test__regex_escape_invalid(regex, "[]<>\"|", BoolUtl.Y, "%5B%5D%3C%3E%22%7C%7F");
		// range: 00 - 32
		fxt.Test__regex_escape_invalid(regex, "\t\n ", BoolUtl.Y, "%09%0A+");
	}
	@Test public void Regex__ipv6_brack() {
		Xomw_regex_ipv6_brack regex = new Xomw_regex_ipv6_brack();
		// basic
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.Y, "//%5B0a.1b:12%5D:123");
		// port: none
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.Y, "//%5Ba%5D");
		// port: multiple
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.Y, "//%5Ba%5D:1:2:3");
		// "//%5B" missing
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.N, "abc");
		// ipv6: invalid
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.N, "//%5Ba!%5D:1");
		// ipv6: 0-len
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.N, "//%5B%5D:1");
		// port: invalid
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.N, "//%5Ba%5D:a");
		// port: 0-len
		fxt.Test__regex_ipv6_brack(regex, BoolUtl.N, "//%5Ba%5D:");
	}
	@Test public void Decode() {
		// dec
		fxt.Test__decode_char_references("&#33;"           , "!");
		// hex
		fxt.Test__decode_char_references("&#x23;"          , "#");
		// entity
		fxt.Test__decode_char_references("&alpha;"         , "α");
		// entity:lt
		fxt.Test__decode_char_references("&lt;"            , "<");
		// entity:rlm
		fxt.Test__decode_char_references("&רלמ;"           , "‏");
		// entity:invalid
		fxt.Test__decode_char_references("&invalid;"       , "&invalid;");
		// amp
		fxt.Test__decode_char_references("a&b"             , "a&b");
	}
	@Test public void Clean_url() {
		// entity
		fxt.Test__clean_url("http://a.org/b&amp;c"           , "http://a.org/b&c");
		// entity: escape
		fxt.Test__clean_url("http://a.org/b&quot;c"          , "http://a.org/b%22c");
		// domain=n; make sure &quot; is changed, but not soft-hyphen
		fxt.Test__clean_url("a&quot;­z"                      , "a%22­z");
		// host: invalid idn
		fxt.Test__clean_url("http://a᠆b.org/c᠆d"              , "http://ab.org/c᠆d");
		// ipv6_brack
		fxt.Test__clean_url("http://[0a.1b:12]:123/cd"       , "http://[0a.1b:12]:123/cd");
	}
	@Test public void Merge_atrs() {
		Xomw_atr_mgr src_atrs = new Xomw_atr_mgr();
		Xomw_atr_mgr trg_atrs = new Xomw_atr_mgr();
		Xomw_atr_mgr expd_atrs = new Xomw_atr_mgr();
		String cls = "class";
		// basic: k1 + k2
		fxt.Test__merge_attributes(src_atrs.Clear().Add_many("k1", "v1"), trg_atrs.Clear().Add_many("k2", "v2"), expd_atrs.Clear().Add_many("k1", "v1", "k2", "v2"));
		// overwrite: k1 + k1
		fxt.Test__merge_attributes(src_atrs.Clear().Add_many("k1", "v1"), trg_atrs.Clear().Add_many("k1", "v1a"), expd_atrs.Clear().Add_many("k1", "v1a"));
		// cls: many
		fxt.Test__merge_attributes(src_atrs.Clear().Add_many(cls, "v1 v2"), trg_atrs.Clear().Add_many(cls, "v3 v4"), expd_atrs.Clear().Add_many(cls, "v1 v2 v3 v4"));
		// cls: src.empty
		fxt.Test__merge_attributes(src_atrs.Clear(), trg_atrs.Clear().Add_many(cls, "v1"), expd_atrs.Clear().Add_many(cls, "v1"));
		// cls: ws
		fxt.Test__merge_attributes(src_atrs.Clear().Add_many(cls, "  v1   v2  "), trg_atrs.Clear().Add_many(cls, "  v3   v4   "), expd_atrs.Clear().Add_many(cls, "v1 v2 v3 v4"));
	}

	@Test public void normalizeWhitespace() {
		fxt.Test_normalizeWhitespace("a\r\nb", "a b");
		fxt.Test_normalizeWhitespace("a\rb", "a b");
		fxt.Test_normalizeWhitespace("a\nb", "a b");
		fxt.Test_normalizeWhitespace("a\tb", "a b");
	}
}
class XomwSanitizer_fxt {
	private final XomwSanitizer sanitizer = new XomwSanitizer();
	private final BryWtr tmp = BryWtr.New();
	public void Test__normalize_char_references(String src_str, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		sanitizer.normalizeCharReferences(tmp, BoolUtl.Y, src_bry, 0, src_bry.length);
		GfoTstr.Eq(expd, tmp.ToStrAndClear());
	}
	public void Test__regex_domain_y(Xomw_regex_find_domain regex_domain, String src_str, String expd_prot, String expd_host, String expd_rest) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(true, regex_domain.Match(src_bry, 0, src_bry.length), src_str);
		GfoTstr.Eq(expd_prot, BryLni.Mid(src_bry, regex_domain.prot_bgn, regex_domain.prot_end));
		GfoTstr.Eq(expd_host, BryLni.Mid(src_bry, regex_domain.host_bgn, regex_domain.host_end));
		GfoTstr.Eq(expd_rest, BryLni.Mid(src_bry, regex_domain.rest_bgn, regex_domain.rest_end));
	}
	public void Test__regex_domain_n(Xomw_regex_find_domain regex_domain, String src_str) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(false, regex_domain.Match(src_bry, 0, src_bry.length), src_str);
	}
	public void Test__regex_escape_invalid(Xomw_regex_escape_invalid regex, String src_str, boolean expd_rslt, String expd_str) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(expd_rslt, regex.Escape(tmp, src_bry, 0, src_bry.length));
		GfoTstr.Eq(expd_str, tmp.ToBryAndClear());
	}
	public void Test__regex_ipv6_brack(Xomw_regex_ipv6_brack regex, boolean expd_rslt, String src_str) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(expd_rslt, regex.Match(src_bry, 0, src_bry.length));
	}
	public void Test__decode_char_references(String src_str, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		sanitizer.decodeCharReferences(tmp, BoolUtl.Y, src_bry, 0, src_bry.length);
		GfoTstr.Eq(expd, tmp.ToStrAndClear());
	}
	public void Test__clean_url(String src_str, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(expd, sanitizer.cleanUrl(src_bry));
	}
	public void Test__merge_attributes(Xomw_atr_mgr src, Xomw_atr_mgr trg, Xomw_atr_mgr expd) {
		sanitizer.mergeAttributes(src, trg);
		GfoTstr.EqLines(expd.To_str(tmp), src.To_str(tmp), "merge_atrs");
	}
	public void Test_normalizeWhitespace(String src_str, String expd) {
		GfoTstr.Eq(expd, sanitizer.normalizeWhitespace(BryUtl.NewU8(src_str)), "merge_atrs");
	}
}
