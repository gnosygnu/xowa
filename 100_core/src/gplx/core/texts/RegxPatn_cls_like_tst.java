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
package gplx.core.texts; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.langs.regxs.*;
public class RegxPatn_cls_like_tst {
	@Test  public void Basic() {
		tst_Match("abcd", "abcd", true);				// basic; pass
		tst_Match("abcd", "zbcd", false);				// basic; fail
		tst_Match("abcd", "abc", false);				// no wildcard; must be exact match
		tst_Match("a cd", "a cd", true);				// check space works
	}
	@Test  public void Wildcard() {
		tst_Match("abcd", "a%", true);					// bgn; pass
		tst_Match("abcd", "b%", false);					// bgn; fail
		tst_Match("abcd", "%d", true);					// end; pass
		tst_Match("abcd", "%c", false);					// end; fail
		tst_Match("abcd", "%b%", true);					// flank; pass
		tst_Match("abcd", "%e%", false);				// flank; fail
		tst_Match("abcd", "%a%", true);					// flank; bgn; pass
		tst_Match("abcd", "%d%", true);					// flank; end; pass
	}
	@Test  public void Any() {
		tst_Match("abcd", "a_cd", true);				// basic; pass
		tst_Match("abcd", "z_cd", false);				// basic; fail
		tst_Match("abcd", "a_c", false);				// fail; check no wildcard
	}
	@Test  public void CharSet() {
		tst_Match("abcd", "a[b]cd", true);				// pass
		tst_Match("abcd", "a[x]cd", false);				// fail
		tst_Match("abcd", "a[bcde]cd", true);			// multiple; pass
		tst_Match("abcd", "a[xyz]cd", false);			// multiple; fail
		tst_Match("abcd", "a[^z]cd", true);				// not; pass
		tst_Match("abcd", "a[^b]cd", false);			// not; fail
	}
	@Test  public void Escape() {
		tst_Match("a%b", "a|%b", true);					// escape wildcard; pass
		tst_Match("a%bc", "a|%b", false);				// escape wildcard; fail
		tst_Match("a|b", "a|b", false);					// escape char; fail
		tst_Match("a|b", "a||b", true);					// escape char; pass
	}
	@Test  public void Escape_diffChar() {
		tst_Match("a%b", "a~%b", '~', true);			// escape wildcard; pass
		tst_Match("a%bc", "a~%b", '~', false);			// escape wildcard; fail
		tst_Match("a|b", "a|b", '~', true);				// no escape needed
		tst_Match("a~b", "a~b", '~', false);			// escape char; fail
		tst_Match("a~b", "a~~b", '~', true);			// escape char; pass
	}
	@Test  public void Chars() {						// Escape Regx_bldr; ex: LIKE 'a{' -> a\{
		tst_EscapeRegxChar(Regx_bldr.Tkn_Escape);		// \
		tst_EscapeRegxChar(Regx_bldr.Tkn_GroupBegin);	// [
		tst_EscapeRegxChar(Regx_bldr.Tkn_GroupEnd);		// ]
		tst_EscapeRegxChar(Regx_bldr.Tkn_LineBegin);		// ^
		tst_EscapeRegxChar(Regx_bldr.Tkn_LineEnd);		// $
		tst_EscapeRegxChar(Regx_bldr.Tkn_RepBegin);		// {
		tst_EscapeRegxChar(Regx_bldr.Tkn_RepEnd);		// }
		tst_EscapeRegxChar(Regx_bldr.Tkn_Wild_0or1);		// ?
		tst_EscapeRegxChar(Regx_bldr.Tkn_Wild_0Plus);	// *
		tst_EscapeRegxChar(Regx_bldr.Tkn_Wild_1Plus);	// +
	}
	void tst_Match(String raw, String regx, boolean expd) {tst_Match(raw, regx, RegxPatn_cls_like.EscapeDefault, expd);}
	void tst_Match(String raw, String regx, char escape, boolean expd) {
		RegxPatn_cls_like like = RegxPatn_cls_like_.parse(regx, escape);
		boolean actl = like.Matches(raw);
		Tfds.Eq(expd, actl, "raw={0} regx={1} expd={2}", raw, regx, expd);
	}
	void tst_EscapeRegxChar(char regexChar) {
		RegxPatn_cls_like like = RegxPatn_cls_like_.parse(Object_.Xto_str_strict_or_empty(regexChar), '|');
		Tfds.Eq(true, like.Matches(Object_.Xto_str_strict_or_empty(regexChar)));
		Tfds.Eq(false, like.Matches("a"));				// catches errors for improper escaping of wildcard
	}
}
