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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
import gplx.core.encoders.*; import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.htmls.*;
import gplx.xowa.mws.parsers.*;
public class Xomw_sanitizer {
	private final    Mwh_doc_wkr__atr_bldr atr_bldr = new Mwh_doc_wkr__atr_bldr();
	private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	public void Fix_tag_attributes(Bry_bfr bfr, byte[] tag_name, byte[] atrs) {
		atr_bldr.Atrs__clear();
		atr_parser.Parse(atr_bldr, -1, -1, atrs, 0, atrs.length);
		int len = atr_bldr.Atrs__len();

		// PORTED: Sanitizer.php|safeEncodeTagAttributes
		for (int i = 0; i < len; i++) {
			// $encAttribute = htmlspecialchars( $attribute );
			// $encValue = Sanitizer::safeEncodeAttribute( $value );
			// $attribs[] = "$encAttribute=\"$encValue\"";
			Mwh_atr_itm itm = atr_bldr.Atrs__get_at(i);
			bfr.Add_byte_space();	// "return count( $attribs ) ? ' ' . implode( ' ', $attribs ) : '';"
			bfr.Add_bry_escape_html(itm.Key_bry(), itm.Key_bgn(), itm.Key_end());
			bfr.Add_byte_eq().Add_byte_quote();
			bfr.Add(itm.Val_as_bry());	// TODO.XO:Sanitizer::encode
			bfr.Add_byte_quote();
		}
	}
	public void Normalize_char_references(Xomw_parser_bfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		Normalize_char_references(bfr, Bool_.N, src, src_bgn, src_end);
	}
	public byte[] Normalize_char_references(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end) {
		// assert static structs
		if (Normalize__dec == null) {
			synchronized (Xomw_sanitizer.class) {
				html_entities = Html_entities_new();
				Normalize__dec = Bool_ary_bldr.New_u8().Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9).To_ary();
				Normalize__hex = Bool_ary_bldr.New_u8()
					.Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9)
					.Set_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z)
					.Set_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z)
					.To_ary();
				Normalize__ent = Bool_ary_bldr.New_u8()
					.Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9)
					.Set_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z)
					.Set_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z)
					.Set_rng(128, 255)
					.To_ary();
			}
		}

		// XO.BRY_BFR
		boolean dirty = false;
		int cur = src_bgn;
		boolean called_by_bry = bfr == null;

		while (true) {
			// search for "&"
			int find_bgn = Bry_find_.Find_fwd(src, Byte_ascii.Amp, cur);
			if (find_bgn == Bry_find_.Not_found) {	// "&" not found; exit
				if (dirty)
					bfr.Add_mid(src, cur, src_end);
				break;
			}
			int ent_bgn = find_bgn + 1;	// +1 to skip &

			// get regex; (a) dec (&#09;); (b) hex (&#xFF;); (c) entity (&alpha;);
			boolean[] regex = null;
			// check for #;
			if (ent_bgn < src_end && src[ent_bgn] == Byte_ascii.Hash) {
				ent_bgn++;
				if (ent_bgn < src_end) {
					byte nxt = src[ent_bgn];
					// check for x
					if (nxt == Byte_ascii.Ltr_X || nxt == Byte_ascii.Ltr_x) {
						ent_bgn++;
						regex = Normalize__hex;
					}
				}
				if (regex == null)
					regex = Normalize__dec;
			}
			else {
				regex = Normalize__ent;
			}

			// keep looping until invalid regex
			int ent_end = ent_bgn;
			byte b = Byte_ascii.Null;
			for (int i = ent_bgn; i < src_end; i++) {
				b = src[i];
				if (regex[b])
					ent_end++;
				else
					break;
			}

			// mark dirty; can optimize later by checking if "&lt;" already exists
			dirty = true;
			if (bfr == null) bfr = Bry_bfr_.New();
			bfr.Add_mid(src, cur, find_bgn); // add everything before &

			// invalid <- regex ended, but not at semic
			if (b != Byte_ascii.Semic) {
				bfr.Add(Gfh_entity_.Amp_bry);       // transform "&" to "&amp;"
				cur = find_bgn + 1;                 // position after "&"
				continue;
			}

			// do normalization
			byte[] name = Bry_.Mid(src, ent_bgn, ent_end);
			boolean ret = false;
			if      (regex == Normalize__ent) {
				Normalize_entity(bfr, name);
				ret = true;
			}
			else if (regex == Normalize__dec) {
				ret = Dec_char_reference(bfr, name);
			}
			else if (regex == Normalize__hex) {
				ret = Hex_char_reference(bfr, name);
			}
			if (!ret) {
				bfr.Add(Gfh_entity_.Amp_bry);       // transform "&" to "&amp;"
				bfr.Add_bry_escape_html(src, find_bgn + 1, ent_end + 1); // "find_bgn + 1" to start after "&"; "ent_end + 1" to include ";"
			}

			cur = ent_end + 1;	// +1 to position after ";"
		}

		// XO.BRY_BFR
		if (dirty) {
			if (called_by_bry)
				return bfr.To_bry_and_clear();
			else
				return Bry_.Empty;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
			else {
				if (lone_bfr)
					bfr.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}

	// If the named entity is defined in the HTML 4.0/XHTML 1.0 DTD,
	// return the equivalent numeric entity reference (except for the core &lt;
	// &gt; &amp; &quot;). If the entity is a MediaWiki-specific alias, returns
	// the HTML equivalent. Otherwise, returns HTML-escaped text of
	// pseudo-entity source (eg &amp;foo;)
	private void Normalize_entity(Bry_bfr bfr, byte[] name) {
		Object o = html_entities.Get_by_bry(name);
		if (o == null) {
			bfr.Add_str_a7("&amp;").Add(name).Add_byte_semic();
		}
		else {
			Xomw_html_ent entity = (Xomw_html_ent)o;
			bfr.Add(entity.html);
		}
	}

	private boolean Dec_char_reference(Bry_bfr bfr, byte[] codepoint) {
		int point = Bry_.To_int_or(codepoint, -1);
		if (Validate_codepoint(point)) {
			bfr.Add_str_a7("&#").Add_int_variable(point).Add_byte_semic();
			return true;
		}
		return false;
	}

	private boolean Hex_char_reference(Bry_bfr bfr, byte[] codepoint) {
		int point = Hex_utl_.Parse_or(codepoint, -1);
		if (Validate_codepoint(point)) {
			bfr.Add_str_a7("&#x");
			Hex_utl_.Write_bfr(bfr, Bool_.Y, point);	// sprintf( '&#x%x;', $point )
			bfr.Add_byte_semic();
			return true;
		}
		return false;
	}

	private boolean Validate_codepoint(int codepoint) {
		// U+000C is valid in HTML5 but not allowed in XML.
		// U+000D is valid in XML but not allowed in HTML5.
		// U+007F - U+009F are disallowed in HTML5 (control characters).
		return  codepoint == 0x09
			||  codepoint == 0x0a
			|| (codepoint >= 0x20    && codepoint <= 0x7e)
			|| (codepoint >= 0xa0    && codepoint <= 0xd7ff)
			|| (codepoint >= 0xe000  && codepoint <= 0xfffd)
			|| (codepoint >= 0x10000 && codepoint <= 0x10ffff);
	}

	private static boolean[] Normalize__dec, Normalize__hex, Normalize__ent; 
	private static Hash_adp_bry html_entities;
	private static Hash_adp_bry Html_entities_new() {
		Bry_bfr tmp = Bry_bfr_.New();
		Hash_adp_bry rv = Hash_adp_bry.cs();

		Html_entities_set(rv, Xomw_html_ent.Type__alias, -1, "רלמ", "&rlm;");
		Html_entities_set(rv, Xomw_html_ent.Type__alias, -1, "رلم", "&rlm;");

		Html_entities_set(rv, Xomw_html_ent.Type__char, 60, "lt", "&lt;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 62, "gt", "&gt;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 38, "amp", "&amp;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 34, "quot", "&quot;");

		// List of all named character entities defined in HTML 4.01
		// https://www.w3.org/TR/html4/sgml/entities.html
		// As well as &apos; which is only defined starting in XHTML1.
		Html_entities_set(rv, tmp, "Aacute"   , 193);
		Html_entities_set(rv, tmp, "aacute"   , 225);
		Html_entities_set(rv, tmp, "Acirc"    , 194);
		Html_entities_set(rv, tmp, "acirc"    , 226);
		Html_entities_set(rv, tmp, "acute"    , 180);
		Html_entities_set(rv, tmp, "AElig"    , 198);
		Html_entities_set(rv, tmp, "aelig"    , 230);
		Html_entities_set(rv, tmp, "Agrave"   , 192);
		Html_entities_set(rv, tmp, "agrave"   , 224);
		Html_entities_set(rv, tmp, "alefsym"  , 8501);
		Html_entities_set(rv, tmp, "Alpha"    , 913);
		Html_entities_set(rv, tmp, "alpha"    , 945);
		Html_entities_set(rv, tmp, "amp"      , 38);	// XO: identical to Type__char entry; note that Type__char should be evaluated first
		Html_entities_set(rv, tmp, "and"      , 8743);
		Html_entities_set(rv, tmp, "ang"      , 8736);
		Html_entities_set(rv, tmp, "apos"     , 39); // New in XHTML & HTML 5; avoid in output for compatibility with IE.
		Html_entities_set(rv, tmp, "Aring"    , 197);
		Html_entities_set(rv, tmp, "aring"    , 229);
		Html_entities_set(rv, tmp, "asymp"    , 8776);
		Html_entities_set(rv, tmp, "Atilde"   , 195);
		Html_entities_set(rv, tmp, "atilde"   , 227);
		Html_entities_set(rv, tmp, "Auml"     , 196);
		Html_entities_set(rv, tmp, "auml"     , 228);
		Html_entities_set(rv, tmp, "bdquo"    , 8222);
		Html_entities_set(rv, tmp, "Beta"     , 914);
		Html_entities_set(rv, tmp, "beta"     , 946);
		Html_entities_set(rv, tmp, "brvbar"   , 166);
		Html_entities_set(rv, tmp, "bull"     , 8226);
		Html_entities_set(rv, tmp, "cap"      , 8745);
		Html_entities_set(rv, tmp, "Ccedil"   , 199);
		Html_entities_set(rv, tmp, "ccedil"   , 231);
		Html_entities_set(rv, tmp, "cedil"    , 184);
		Html_entities_set(rv, tmp, "cent"     , 162);
		Html_entities_set(rv, tmp, "Chi"      , 935);
		Html_entities_set(rv, tmp, "chi"      , 967);
		Html_entities_set(rv, tmp, "circ"     , 710);
		Html_entities_set(rv, tmp, "clubs"    , 9827);
		Html_entities_set(rv, tmp, "cong"     , 8773);
		Html_entities_set(rv, tmp, "copy"     , 169);
		Html_entities_set(rv, tmp, "crarr"    , 8629);
		Html_entities_set(rv, tmp, "cup"      , 8746);
		Html_entities_set(rv, tmp, "curren"   , 164);
		Html_entities_set(rv, tmp, "dagger"   , 8224);
		Html_entities_set(rv, tmp, "Dagger"   , 8225);
		Html_entities_set(rv, tmp, "darr"     , 8595);
		Html_entities_set(rv, tmp, "dArr"     , 8659);
		Html_entities_set(rv, tmp, "deg"      , 176);
		Html_entities_set(rv, tmp, "Delta"    , 916);
		Html_entities_set(rv, tmp, "delta"    , 948);
		Html_entities_set(rv, tmp, "diams"    , 9830);
		Html_entities_set(rv, tmp, "divide"   , 247);
		Html_entities_set(rv, tmp, "Eacute"   , 201);
		Html_entities_set(rv, tmp, "eacute"   , 233);
		Html_entities_set(rv, tmp, "Ecirc"    , 202);
		Html_entities_set(rv, tmp, "ecirc"    , 234);
		Html_entities_set(rv, tmp, "Egrave"   , 200);
		Html_entities_set(rv, tmp, "egrave"   , 232);
		Html_entities_set(rv, tmp, "empty"    , 8709);
		Html_entities_set(rv, tmp, "emsp"     , 8195);
		Html_entities_set(rv, tmp, "ensp"     , 8194);
		Html_entities_set(rv, tmp, "Epsilon"  , 917);
		Html_entities_set(rv, tmp, "epsilon"  , 949);
		Html_entities_set(rv, tmp, "equiv"    , 8801);
		Html_entities_set(rv, tmp, "Eta"      , 919);
		Html_entities_set(rv, tmp, "eta"      , 951);
		Html_entities_set(rv, tmp, "ETH"      , 208);
		Html_entities_set(rv, tmp, "eth"      , 240);
		Html_entities_set(rv, tmp, "Euml"     , 203);
		Html_entities_set(rv, tmp, "euml"     , 235);
		Html_entities_set(rv, tmp, "euro"     , 8364);
		Html_entities_set(rv, tmp, "exist"    , 8707);
		Html_entities_set(rv, tmp, "fnof"     , 402);
		Html_entities_set(rv, tmp, "forall"   , 8704);
		Html_entities_set(rv, tmp, "frac12"   , 189);
		Html_entities_set(rv, tmp, "frac14"   , 188);
		Html_entities_set(rv, tmp, "frac34"   , 190);
		Html_entities_set(rv, tmp, "frasl"    , 8260);
		Html_entities_set(rv, tmp, "Gamma"    , 915);
		Html_entities_set(rv, tmp, "gamma"    , 947);
		Html_entities_set(rv, tmp, "ge"       , 8805);
		Html_entities_set(rv, tmp, "gt"       , 62);
		Html_entities_set(rv, tmp, "harr"     , 8596);
		Html_entities_set(rv, tmp, "hArr"     , 8660);
		Html_entities_set(rv, tmp, "hearts"   , 9829);
		Html_entities_set(rv, tmp, "hellip"   , 8230);
		Html_entities_set(rv, tmp, "Iacute"   , 205);
		Html_entities_set(rv, tmp, "iacute"   , 237);
		Html_entities_set(rv, tmp, "Icirc"    , 206);
		Html_entities_set(rv, tmp, "icirc"    , 238);
		Html_entities_set(rv, tmp, "iexcl"    , 161);
		Html_entities_set(rv, tmp, "Igrave"   , 204);
		Html_entities_set(rv, tmp, "igrave"   , 236);
		Html_entities_set(rv, tmp, "image"    , 8465);
		Html_entities_set(rv, tmp, "infin"    , 8734);
		Html_entities_set(rv, tmp, "int"      , 8747);
		Html_entities_set(rv, tmp, "Iota"     , 921);
		Html_entities_set(rv, tmp, "iota"     , 953);
		Html_entities_set(rv, tmp, "iquest"   , 191);
		Html_entities_set(rv, tmp, "isin"     , 8712);
		Html_entities_set(rv, tmp, "Iuml"     , 207);
		Html_entities_set(rv, tmp, "iuml"     , 239);
		Html_entities_set(rv, tmp, "Kappa"    , 922);
		Html_entities_set(rv, tmp, "kappa"    , 954);
		Html_entities_set(rv, tmp, "Lambda"   , 923);
		Html_entities_set(rv, tmp, "lambda"   , 955);
		Html_entities_set(rv, tmp, "lang"     , 9001);
		Html_entities_set(rv, tmp, "laquo"    , 171);
		Html_entities_set(rv, tmp, "larr"     , 8592);
		Html_entities_set(rv, tmp, "lArr"     , 8656);
		Html_entities_set(rv, tmp, "lceil"    , 8968);
		Html_entities_set(rv, tmp, "ldquo"    , 8220);
		Html_entities_set(rv, tmp, "le"       , 8804);
		Html_entities_set(rv, tmp, "lfloor"   , 8970);
		Html_entities_set(rv, tmp, "lowast"   , 8727);
		Html_entities_set(rv, tmp, "loz"      , 9674);
		Html_entities_set(rv, tmp, "lrm"      , 8206);
		Html_entities_set(rv, tmp, "lsaquo"   , 8249);
		Html_entities_set(rv, tmp, "lsquo"    , 8216);
		Html_entities_set(rv, tmp, "lt"       , 60);
		Html_entities_set(rv, tmp, "macr"     , 175);
		Html_entities_set(rv, tmp, "mdash"    , 8212);
		Html_entities_set(rv, tmp, "micro"    , 181);
		Html_entities_set(rv, tmp, "middot"   , 183);
		Html_entities_set(rv, tmp, "minus"    , 8722);
		Html_entities_set(rv, tmp, "Mu"       , 924);
		Html_entities_set(rv, tmp, "mu"       , 956);
		Html_entities_set(rv, tmp, "nabla"    , 8711);
		Html_entities_set(rv, tmp, "nbsp"     , 160);
		Html_entities_set(rv, tmp, "ndash"    , 8211);
		Html_entities_set(rv, tmp, "ne"       , 8800);
		Html_entities_set(rv, tmp, "ni"       , 8715);
		Html_entities_set(rv, tmp, "not"      , 172);
		Html_entities_set(rv, tmp, "notin"    , 8713);
		Html_entities_set(rv, tmp, "nsub"     , 8836);
		Html_entities_set(rv, tmp, "Ntilde"   , 209);
		Html_entities_set(rv, tmp, "ntilde"   , 241);
		Html_entities_set(rv, tmp, "Nu"       , 925);
		Html_entities_set(rv, tmp, "nu"       , 957);
		Html_entities_set(rv, tmp, "Oacute"   , 211);
		Html_entities_set(rv, tmp, "oacute"   , 243);
		Html_entities_set(rv, tmp, "Ocirc"    , 212);
		Html_entities_set(rv, tmp, "ocirc"    , 244);
		Html_entities_set(rv, tmp, "OElig"    , 338);
		Html_entities_set(rv, tmp, "oelig"    , 339);
		Html_entities_set(rv, tmp, "Ograve"   , 210);
		Html_entities_set(rv, tmp, "ograve"   , 242);
		Html_entities_set(rv, tmp, "oline"    , 8254);
		Html_entities_set(rv, tmp, "Omega"    , 937);
		Html_entities_set(rv, tmp, "omega"    , 969);
		Html_entities_set(rv, tmp, "Omicron"  , 927);
		Html_entities_set(rv, tmp, "omicron"  , 959);
		Html_entities_set(rv, tmp, "oplus"    , 8853);
		Html_entities_set(rv, tmp, "or"       , 8744);
		Html_entities_set(rv, tmp, "ordf"     , 170);
		Html_entities_set(rv, tmp, "ordm"     , 186);
		Html_entities_set(rv, tmp, "Oslash"   , 216);
		Html_entities_set(rv, tmp, "oslash"   , 248);
		Html_entities_set(rv, tmp, "Otilde"   , 213);
		Html_entities_set(rv, tmp, "otilde"   , 245);
		Html_entities_set(rv, tmp, "otimes"   , 8855);
		Html_entities_set(rv, tmp, "Ouml"     , 214);
		Html_entities_set(rv, tmp, "ouml"     , 246);
		Html_entities_set(rv, tmp, "para"     , 182);
		Html_entities_set(rv, tmp, "part"     , 8706);
		Html_entities_set(rv, tmp, "permil"   , 8240);
		Html_entities_set(rv, tmp, "perp"     , 8869);
		Html_entities_set(rv, tmp, "Phi"      , 934);
		Html_entities_set(rv, tmp, "phi"      , 966);
		Html_entities_set(rv, tmp, "Pi"       , 928);
		Html_entities_set(rv, tmp, "pi"       , 960);
		Html_entities_set(rv, tmp, "piv"      , 982);
		Html_entities_set(rv, tmp, "plusmn"   , 177);
		Html_entities_set(rv, tmp, "pound"    , 163);
		Html_entities_set(rv, tmp, "prime"    , 8242);
		Html_entities_set(rv, tmp, "Prime"    , 8243);
		Html_entities_set(rv, tmp, "prod"     , 8719);
		Html_entities_set(rv, tmp, "prop"     , 8733);
		Html_entities_set(rv, tmp, "Psi"      , 936);
		Html_entities_set(rv, tmp, "psi"      , 968);
		Html_entities_set(rv, tmp, "quot"     , 34);
		Html_entities_set(rv, tmp, "radic"    , 8730);
		Html_entities_set(rv, tmp, "rang"     , 9002);
		Html_entities_set(rv, tmp, "raquo"    , 187);
		Html_entities_set(rv, tmp, "rarr"     , 8594);
		Html_entities_set(rv, tmp, "rArr"     , 8658);
		Html_entities_set(rv, tmp, "rceil"    , 8969);
		Html_entities_set(rv, tmp, "rdquo"    , 8221);
		Html_entities_set(rv, tmp, "real"     , 8476);
		Html_entities_set(rv, tmp, "reg"      , 174);
		Html_entities_set(rv, tmp, "rfloor"   , 8971);
		Html_entities_set(rv, tmp, "Rho"      , 929);
		Html_entities_set(rv, tmp, "rho"      , 961);
		Html_entities_set(rv, tmp, "rlm"      , 8207);
		Html_entities_set(rv, tmp, "rsaquo"   , 8250);
		Html_entities_set(rv, tmp, "rsquo"    , 8217);
		Html_entities_set(rv, tmp, "sbquo"    , 8218);
		Html_entities_set(rv, tmp, "Scaron"   , 352);
		Html_entities_set(rv, tmp, "scaron"   , 353);
		Html_entities_set(rv, tmp, "sdot"     , 8901);
		Html_entities_set(rv, tmp, "sect"     , 167);
		Html_entities_set(rv, tmp, "shy"      , 173);
		Html_entities_set(rv, tmp, "Sigma"    , 931);
		Html_entities_set(rv, tmp, "sigma"    , 963);
		Html_entities_set(rv, tmp, "sigmaf"   , 962);
		Html_entities_set(rv, tmp, "sim"      , 8764);
		Html_entities_set(rv, tmp, "spades"   , 9824);
		Html_entities_set(rv, tmp, "sub"      , 8834);
		Html_entities_set(rv, tmp, "sube"     , 8838);
		Html_entities_set(rv, tmp, "sum"      , 8721);
		Html_entities_set(rv, tmp, "sup"      , 8835);
		Html_entities_set(rv, tmp, "sup1"     , 185);
		Html_entities_set(rv, tmp, "sup2"     , 178);
		Html_entities_set(rv, tmp, "sup3"     , 179);
		Html_entities_set(rv, tmp, "supe"     , 8839);
		Html_entities_set(rv, tmp, "szlig"    , 223);
		Html_entities_set(rv, tmp, "Tau"      , 932);
		Html_entities_set(rv, tmp, "tau"      , 964);
		Html_entities_set(rv, tmp, "there4"   , 8756);
		Html_entities_set(rv, tmp, "Theta"    , 920);
		Html_entities_set(rv, tmp, "theta"    , 952);
		Html_entities_set(rv, tmp, "thetasym" , 977);
		Html_entities_set(rv, tmp, "thinsp"   , 8201);
		Html_entities_set(rv, tmp, "THORN"    , 222);
		Html_entities_set(rv, tmp, "thorn"    , 254);
		Html_entities_set(rv, tmp, "tilde"    , 732);
		Html_entities_set(rv, tmp, "times"    , 215);
		Html_entities_set(rv, tmp, "trade"    , 8482);
		Html_entities_set(rv, tmp, "Uacute"   , 218);
		Html_entities_set(rv, tmp, "uacute"   , 250);
		Html_entities_set(rv, tmp, "uarr"     , 8593);
		Html_entities_set(rv, tmp, "uArr"     , 8657);
		Html_entities_set(rv, tmp, "Ucirc"    , 219);
		Html_entities_set(rv, tmp, "ucirc"    , 251);
		Html_entities_set(rv, tmp, "Ugrave"   , 217);
		Html_entities_set(rv, tmp, "ugrave"   , 249);
		Html_entities_set(rv, tmp, "uml"      , 168);
		Html_entities_set(rv, tmp, "upsih"    , 978);
		Html_entities_set(rv, tmp, "Upsilon"  , 933);
		Html_entities_set(rv, tmp, "upsilon"  , 965);
		Html_entities_set(rv, tmp, "Uuml"     , 220);
		Html_entities_set(rv, tmp, "uuml"     , 252);
		Html_entities_set(rv, tmp, "weierp"   , 8472);
		Html_entities_set(rv, tmp, "Xi"       , 926);
		Html_entities_set(rv, tmp, "xi"       , 958);
		Html_entities_set(rv, tmp, "Yacute"   , 221);
		Html_entities_set(rv, tmp, "yacute"   , 253);
		Html_entities_set(rv, tmp, "yen"      , 165);
		Html_entities_set(rv, tmp, "Yuml"     , 376);
		Html_entities_set(rv, tmp, "yuml"     , 255);
		Html_entities_set(rv, tmp, "Zeta"     , 918);
		Html_entities_set(rv, tmp, "zeta"     , 950);
		Html_entities_set(rv, tmp, "zwj"      , 8205);
		Html_entities_set(rv, tmp, "zwnj"     , 8204);
		return rv;
	}
	private static void Html_entities_set(Hash_adp_bry rv, Bry_bfr tmp, String name_str, int code) {
		byte[] html_bry = tmp.Add_str_a7("&#").Add_int_variable(code).Add_byte_semic().To_bry_and_clear();
		Html_entities_set(rv, Xomw_html_ent.Type__entity, code, name_str, html_bry);
	}
	private static void Html_entities_set(Hash_adp_bry rv, byte type, int code, String name_str, String html_str) {Html_entities_set(rv, type, code, name_str, Bry_.new_u8(html_str));}
	private static void Html_entities_set(Hash_adp_bry rv, byte type, int code, String name_str, byte[] html_bry) {
		byte[] name_bry = Bry_.new_u8(name_str);
		rv.Add_if_dupe_use_1st(name_bry, new Xomw_html_ent(type, code, name_bry, html_bry));	// Add_dupe needed b/c "lt" and co. are added early; ignore subsequent call
	}
}
class Xomw_html_ent {
	public Xomw_html_ent(byte type, int code, byte[] name, byte[] html) {
		this.type = type;
		this.code = code;
		this.name = name;
		this.html = html;
	}
	public final    byte type;
	public final    int code;
	public final    byte[] name;
	public final    byte[] html;
	public static final byte Type__null = 0, Type__alias = 1, Type__char = 2, Type__entity = 3;
}
class Bool_ary_bldr {
	private final    boolean[] ary;
	public Bool_ary_bldr(int len) {
		this.ary = new boolean[len];
	}
	public Bool_ary_bldr Set_many(int... v) {
		int len = v.length;
		for (int i = 0; i < len; i++)
			ary[v[i]] = true;
		return this;
	}
	public Bool_ary_bldr Set_rng(int bgn, int end) {
		for (int i = bgn; i <= end; i++)
			ary[i] = true;
		return this;
	}
	public boolean[] To_ary() {
		return ary;
	}
	public static Bool_ary_bldr New_u8() {return new Bool_ary_bldr(256);}
}
