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
package gplx.texts; import gplx.*;
import org.junit.*;
public class RegxPatn_cls_like_tst {
	@Test public void Basic() {
		tst_Match("abcd", "abcd", true);				// basic; pass
		tst_Match("abcd", "zbcd", false);				// basic; fail
		tst_Match("abcd", "abc", false);				// no wildcard; must be exact match
		tst_Match("a cd", "a cd", true);				// check space works
	}
	@Test public void Wildcard() {
		tst_Match("abcd", "a%", true);					// bgn; pass
		tst_Match("abcd", "b%", false);					// bgn; fail
		tst_Match("abcd", "%d", true);					// end; pass
		tst_Match("abcd", "%c", false);					// end; fail
		tst_Match("abcd", "%b%", true);					// flank; pass
		tst_Match("abcd", "%e%", false);				// flank; fail
		tst_Match("abcd", "%a%", true);					// flank; bgn; pass
		tst_Match("abcd", "%d%", true);					// flank; end; pass
	}
	@Test public void Any() {
		tst_Match("abcd", "a_cd", true);				// basic; pass
		tst_Match("abcd", "z_cd", false);				// basic; fail
		tst_Match("abcd", "a_c", false);				// fail; check no wildcard
	}
	@Test public void CharSet() {
		tst_Match("abcd", "a[b]cd", true);				// pass
		tst_Match("abcd", "a[x]cd", false);				// fail
		tst_Match("abcd", "a[bcde]cd", true);			// multiple; pass
		tst_Match("abcd", "a[xyz]cd", false);			// multiple; fail
		tst_Match("abcd", "a[^z]cd", true);				// not; pass
		tst_Match("abcd", "a[^b]cd", false);			// not; fail
	}
	@Test public void Escape() {
		tst_Match("a%b", "a|%b", true);					// escape wildcard; pass
		tst_Match("a%bc", "a|%b", false);				// escape wildcard; fail
		tst_Match("a|b", "a|b", false);					// escape char; fail
		tst_Match("a|b", "a||b", true);					// escape char; pass
	}
	@Test public void Escape_diffChar() {
		tst_Match("a%b", "a~%b", '~', true);			// escape wildcard; pass
		tst_Match("a%bc", "a~%b", '~', false);			// escape wildcard; fail
		tst_Match("a|b", "a|b", '~', true);				// no escape needed
		tst_Match("a~b", "a~b", '~', false);			// escape char; fail
		tst_Match("a~b", "a~~b", '~', true);			// escape char; pass
	}
	@Test public void Chars() {						// Escape RegxBldr; ex: LIKE 'a{' -> a\{
		tst_EscapeRegxChar(RegxBldr.Tkn_Escape);		// \
		tst_EscapeRegxChar(RegxBldr.Tkn_GroupBegin);	// [
		tst_EscapeRegxChar(RegxBldr.Tkn_GroupEnd);		// ]
		tst_EscapeRegxChar(RegxBldr.Tkn_LineBegin);		// ^
		tst_EscapeRegxChar(RegxBldr.Tkn_LineEnd);		// $
		tst_EscapeRegxChar(RegxBldr.Tkn_RepBegin);		// {
		tst_EscapeRegxChar(RegxBldr.Tkn_RepEnd);		// }
		tst_EscapeRegxChar(RegxBldr.Tkn_Wild_0or1);		// ?
		tst_EscapeRegxChar(RegxBldr.Tkn_Wild_0Plus);	// *
		tst_EscapeRegxChar(RegxBldr.Tkn_Wild_1Plus);	// +
	}
	void tst_Match(String raw, String regx, boolean expd) {tst_Match(raw, regx, RegxPatn_cls_like.EscapeDefault, expd);}
	void tst_Match(String raw, String regx, char escape, boolean expd) {
		RegxPatn_cls_like like = RegxPatn_cls_like_.parse_(regx, escape);
		boolean actl = like.Matches(raw);
		Tfds.Eq(expd, actl, "raw={0} regx={1} expd={2}", raw, regx, expd);
	}
	void tst_EscapeRegxChar(char regexChar) {
		RegxPatn_cls_like like = RegxPatn_cls_like_.parse_(Object_.XtoStr_OrEmpty(regexChar), '|');
		Tfds.Eq(true, like.Matches(Object_.XtoStr_OrEmpty(regexChar)));
		Tfds.Eq(false, like.Matches("a"));				// catches errors for improper escaping of wildcard
	}
}
