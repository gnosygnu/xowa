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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_amp_trie {
	public static final byte[]		// NOTE: top_define
	  Bry_xowa_lt			= Bry_.new_ascii_("&xowa_lt;")
	, Bry_xowa_brack_bgn	= Bry_.new_ascii_("&xowa_brack_bgn;")
	, Bry_xowa_brack_end	= Bry_.new_ascii_("&xowa_brack_end;")
	, Bry_xowa_pipe			= Bry_.new_ascii_("&xowa_pipe;")
	, Bry_xowa_apos			= Bry_.new_ascii_("&xowa_apos;")
	, Bry_xowa_colon		= Bry_.new_ascii_("&xowa_colon;")
	, Bry_xowa_underline	= Bry_.new_ascii_("&xowa_underline;")
	, Bry_xowa_asterisk		= Bry_.new_ascii_("&xowa_asterisk;")
	, Bry_xowa_space		= Bry_.new_ascii_("&xowa_space;")
	, Bry_xowa_nl			= Bry_.new_ascii_("&xowa_nl;")
	;
	public static final Btrie_slim_mgr _ = new_(); Xop_amp_trie() {}
	private static Btrie_slim_mgr new_() {// REF.MW: Sanitizer|$wgHtmlEntities; NOTE:added apos
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs_();
		Reg_name(rv, Bool_.Y,   60, Bry_xowa_lt);
		Reg_name(rv, Bool_.Y,   91, Bry_xowa_brack_bgn);
		Reg_name(rv, Bool_.Y,   93, Bry_xowa_brack_end);
		Reg_name(rv, Bool_.Y,  124, Bry_xowa_pipe);
		Reg_name(rv, Bool_.Y,   39, Bry_xowa_apos);
		Reg_name(rv, Bool_.Y,   58, Bry_xowa_colon);
		Reg_name(rv, Bool_.Y,   95, Bry_xowa_underline);
		Reg_name(rv, Bool_.Y,   42, Bry_xowa_asterisk);
		Reg_name(rv, Bool_.Y,   32, Bry_xowa_space);
		Reg_name(rv, Bool_.Y,   10, Bry_xowa_nl);
		Reg_name(rv, Bool_.N,   39, "&apos;");
		Reg_name(rv, Bool_.N,  193, "&Aacute;");
		Reg_name(rv, Bool_.N,  225, "&aacute;");
		Reg_name(rv, Bool_.N,  194, "&Acirc;");
		Reg_name(rv, Bool_.N,  226, "&acirc;");
		Reg_name(rv, Bool_.N,  180, "&acute;");
		Reg_name(rv, Bool_.N,  198, "&AElig;");
		Reg_name(rv, Bool_.N,  230, "&aelig;");
		Reg_name(rv, Bool_.N,  192, "&Agrave;");
		Reg_name(rv, Bool_.N,  224, "&agrave;");
		Reg_name(rv, Bool_.N, 8501, "&alefsym;");
		Reg_name(rv, Bool_.N,  913, "&Alpha;");
		Reg_name(rv, Bool_.N,  945, "&alpha;");
		Reg_name(rv, Bool_.N,   38, "&amp;");
		Reg_name(rv, Bool_.N, 8743, "&and;");
		Reg_name(rv, Bool_.N, 8736, "&ang;");
		Reg_name(rv, Bool_.N,  197, "&Aring;");
		Reg_name(rv, Bool_.N,  229, "&aring;");
		Reg_name(rv, Bool_.N, 8776, "&asymp;");
		Reg_name(rv, Bool_.N,  195, "&Atilde;");
		Reg_name(rv, Bool_.N,  227, "&atilde;");
		Reg_name(rv, Bool_.N,  196, "&Auml;");
		Reg_name(rv, Bool_.N,  228, "&auml;");
		Reg_name(rv, Bool_.N, 8222, "&bdquo;");
		Reg_name(rv, Bool_.N,  914, "&Beta;");
		Reg_name(rv, Bool_.N,  946, "&beta;");
		Reg_name(rv, Bool_.N,  166, "&brvbar;");
		Reg_name(rv, Bool_.N, 8226, "&bull;");
		Reg_name(rv, Bool_.N, 8745, "&cap;");
		Reg_name(rv, Bool_.N,  199, "&Ccedil;");
		Reg_name(rv, Bool_.N,  231, "&ccedil;");
		Reg_name(rv, Bool_.N,  184, "&cedil;");
		Reg_name(rv, Bool_.N,  162, "&cent;");
		Reg_name(rv, Bool_.N,  935, "&Chi;");
		Reg_name(rv, Bool_.N,  967, "&chi;");
		Reg_name(rv, Bool_.N,  710, "&circ;");
		Reg_name(rv, Bool_.N, 9827, "&clubs;");
		Reg_name(rv, Bool_.N, 8773, "&cong;");
		Reg_name(rv, Bool_.N,  169, "&copy;");
		Reg_name(rv, Bool_.N, 8629, "&crarr;");
		Reg_name(rv, Bool_.N, 8746, "&cup;");
		Reg_name(rv, Bool_.N,  164, "&curren;");
		Reg_name(rv, Bool_.N, 8224, "&dagger;");
		Reg_name(rv, Bool_.N, 8225, "&Dagger;");
		Reg_name(rv, Bool_.N, 8595, "&darr;");
		Reg_name(rv, Bool_.N, 8659, "&dArr;");
		Reg_name(rv, Bool_.N,  176, "&deg;");
		Reg_name(rv, Bool_.N,  916, "&Delta;");
		Reg_name(rv, Bool_.N,  948, "&delta;");
		Reg_name(rv, Bool_.N, 9830, "&diams;");
		Reg_name(rv, Bool_.N,  247, "&divide;");
		Reg_name(rv, Bool_.N,  201, "&Eacute;");
		Reg_name(rv, Bool_.N,  233, "&eacute;");
		Reg_name(rv, Bool_.N,  202, "&Ecirc;");
		Reg_name(rv, Bool_.N,  234, "&ecirc;");
		Reg_name(rv, Bool_.N,  200, "&Egrave;");
		Reg_name(rv, Bool_.N,  232, "&egrave;");
		Reg_name(rv, Bool_.N, 8709, "&empty;");
		Reg_name(rv, Bool_.N, 8195, "&emsp;");
		Reg_name(rv, Bool_.N, 8194, "&ensp;");
		Reg_name(rv, Bool_.N,  917, "&Epsilon;");
		Reg_name(rv, Bool_.N,  949, "&epsilon;");
		Reg_name(rv, Bool_.N, 8801, "&equiv;");
		Reg_name(rv, Bool_.N,  919, "&Eta;");
		Reg_name(rv, Bool_.N,  951, "&eta;");
		Reg_name(rv, Bool_.N,  208, "&ETH;");
		Reg_name(rv, Bool_.N,  240, "&eth;");
		Reg_name(rv, Bool_.N,  203, "&Euml;");
		Reg_name(rv, Bool_.N,  235, "&euml;");
		Reg_name(rv, Bool_.N, 8364, "&euro;");
		Reg_name(rv, Bool_.N, 8707, "&exist;");
		Reg_name(rv, Bool_.N,  402, "&fnof;");
		Reg_name(rv, Bool_.N, 8704, "&forall;");
		Reg_name(rv, Bool_.N,  189, "&frac12;");
		Reg_name(rv, Bool_.N,  188, "&frac14;");
		Reg_name(rv, Bool_.N,  190, "&frac34;");
		Reg_name(rv, Bool_.N, 8260, "&frasl;");
		Reg_name(rv, Bool_.N,  915, "&Gamma;");
		Reg_name(rv, Bool_.N,  947, "&gamma;");
		Reg_name(rv, Bool_.N, 8805, "&ge;");
		Reg_name(rv, Bool_.N,   62, "&gt;");
		Reg_name(rv, Bool_.N, 8596, "&harr;");
		Reg_name(rv, Bool_.N, 8660, "&hArr;");
		Reg_name(rv, Bool_.N, 9829, "&hearts;");
		Reg_name(rv, Bool_.N, 8230, "&hellip;");
		Reg_name(rv, Bool_.N,  205, "&Iacute;");
		Reg_name(rv, Bool_.N,  237, "&iacute;");
		Reg_name(rv, Bool_.N,  206, "&Icirc;");
		Reg_name(rv, Bool_.N,  238, "&icirc;");
		Reg_name(rv, Bool_.N,  161, "&iexcl;");
		Reg_name(rv, Bool_.N,  204, "&Igrave;");
		Reg_name(rv, Bool_.N,  236, "&igrave;");
		Reg_name(rv, Bool_.N, 8465, "&image;");
		Reg_name(rv, Bool_.N, 8734, "&infin;");
		Reg_name(rv, Bool_.N, 8747, "&int;");
		Reg_name(rv, Bool_.N,  921, "&Iota;");
		Reg_name(rv, Bool_.N,  953, "&iota;");
		Reg_name(rv, Bool_.N,  191, "&iquest;");
		Reg_name(rv, Bool_.N, 8712, "&isin;");
		Reg_name(rv, Bool_.N,  207, "&Iuml;");
		Reg_name(rv, Bool_.N,  239, "&iuml;");
		Reg_name(rv, Bool_.N,  922, "&Kappa;");
		Reg_name(rv, Bool_.N,  954, "&kappa;");
		Reg_name(rv, Bool_.N,  923, "&Lambda;");
		Reg_name(rv, Bool_.N,  955, "&lambda;");
		Reg_name(rv, Bool_.N, 9001, "&lang;");
		Reg_name(rv, Bool_.N,  171, "&laquo;");
		Reg_name(rv, Bool_.N, 8592, "&larr;");
		Reg_name(rv, Bool_.N, 8656, "&lArr;");
		Reg_name(rv, Bool_.N, 8968, "&lceil;");
		Reg_name(rv, Bool_.N, 8220, "&ldquo;");
		Reg_name(rv, Bool_.N, 8804, "&le;");
		Reg_name(rv, Bool_.N, 8970, "&lfloor;");
		Reg_name(rv, Bool_.N, 8727, "&lowast;");
		Reg_name(rv, Bool_.N, 9674, "&loz;");
		Reg_name(rv, Bool_.N, 8206, "&lrm;");
		Reg_name(rv, Bool_.N, 8249, "&lsaquo;");
		Reg_name(rv, Bool_.N, 8216, "&lsquo;");
		Reg_name(rv, Bool_.N,   60, "&lt;");
		Reg_name(rv, Bool_.N,  175, "&macr;");
		Reg_name(rv, Bool_.N, 8212, "&mdash;");
		Reg_name(rv, Bool_.N,  181, "&micro;");
		Reg_name(rv, Bool_.N,  183, "&middot;");
		Reg_name(rv, Bool_.N, 8722, "&minus;");
		Reg_name(rv, Bool_.N,  924, "&Mu;");
		Reg_name(rv, Bool_.N,  956, "&mu;");
		Reg_name(rv, Bool_.N, 8711, "&nabla;");
		Reg_name(rv, Bool_.N,  160, "&nbsp;");
		Reg_name(rv, Bool_.N, 8211, "&ndash;");
		Reg_name(rv, Bool_.N, 8800, "&ne;");
		Reg_name(rv, Bool_.N, 8715, "&ni;");
		Reg_name(rv, Bool_.N,  172, "&not;");
		Reg_name(rv, Bool_.N, 8713, "&notin;");
		Reg_name(rv, Bool_.N, 8836, "&nsub;");
		Reg_name(rv, Bool_.N,  209, "&Ntilde;");
		Reg_name(rv, Bool_.N,  241, "&ntilde;");
		Reg_name(rv, Bool_.N,  925, "&Nu;");
		Reg_name(rv, Bool_.N,  957, "&nu;");
		Reg_name(rv, Bool_.N,  211, "&Oacute;");
		Reg_name(rv, Bool_.N,  243, "&oacute;");
		Reg_name(rv, Bool_.N,  212, "&Ocirc;");
		Reg_name(rv, Bool_.N,  244, "&ocirc;");
		Reg_name(rv, Bool_.N,  338, "&OElig;");
		Reg_name(rv, Bool_.N,  339, "&oelig;");
		Reg_name(rv, Bool_.N,  210, "&Ograve;");
		Reg_name(rv, Bool_.N,  242, "&ograve;");
		Reg_name(rv, Bool_.N, 8254, "&oline;");
		Reg_name(rv, Bool_.N,  937, "&Omega;");
		Reg_name(rv, Bool_.N,  969, "&omega;");
		Reg_name(rv, Bool_.N,  927, "&Omicron;");
		Reg_name(rv, Bool_.N,  959, "&omicron;");
		Reg_name(rv, Bool_.N, 8853, "&oplus;");
		Reg_name(rv, Bool_.N, 8744, "&or;");
		Reg_name(rv, Bool_.N,  170, "&ordf;");
		Reg_name(rv, Bool_.N,  186, "&ordm;");
		Reg_name(rv, Bool_.N,  216, "&Oslash;");
		Reg_name(rv, Bool_.N,  248, "&oslash;");
		Reg_name(rv, Bool_.N,  213, "&Otilde;");
		Reg_name(rv, Bool_.N,  245, "&otilde;");
		Reg_name(rv, Bool_.N, 8855, "&otimes;");
		Reg_name(rv, Bool_.N,  214, "&Ouml;");
		Reg_name(rv, Bool_.N,  246, "&ouml;");
		Reg_name(rv, Bool_.N,  182, "&para;");
		Reg_name(rv, Bool_.N, 8706, "&part;");
		Reg_name(rv, Bool_.N, 8240, "&permil;");
		Reg_name(rv, Bool_.N, 8869, "&perp;");
		Reg_name(rv, Bool_.N,  934, "&Phi;");
		Reg_name(rv, Bool_.N,  966, "&phi;");
		Reg_name(rv, Bool_.N,  928, "&Pi;");
		Reg_name(rv, Bool_.N,  960, "&pi;");
		Reg_name(rv, Bool_.N,  982, "&piv;");
		Reg_name(rv, Bool_.N,  177, "&plusmn;");
		Reg_name(rv, Bool_.N,  163, "&pound;");
		Reg_name(rv, Bool_.N, 8242, "&prime;");
		Reg_name(rv, Bool_.N, 8243, "&Prime;");
		Reg_name(rv, Bool_.N, 8719, "&prod;");
		Reg_name(rv, Bool_.N, 8733, "&prop;");
		Reg_name(rv, Bool_.N,  936, "&Psi;");
		Reg_name(rv, Bool_.N,  968, "&psi;");
		Reg_name(rv, Bool_.N,   34, "&quot;");
		Reg_name(rv, Bool_.N, 8730, "&radic;");
		Reg_name(rv, Bool_.N, 9002, "&rang;");
		Reg_name(rv, Bool_.N,  187, "&raquo;");
		Reg_name(rv, Bool_.N, 8594, "&rarr;");
		Reg_name(rv, Bool_.N, 8658, "&rArr;");
		Reg_name(rv, Bool_.N, 8969, "&rceil;");
		Reg_name(rv, Bool_.N, 8221, "&rdquo;");
		Reg_name(rv, Bool_.N, 8476, "&real;");
		Reg_name(rv, Bool_.N,  174, "&reg;");
		Reg_name(rv, Bool_.N, 8971, "&rfloor;");
		Reg_name(rv, Bool_.N,  929, "&Rho;");
		Reg_name(rv, Bool_.N,  961, "&rho;");
		Reg_name(rv, Bool_.N, 8207, "&rlm;");
		Reg_name(rv, Bool_.N, 8250, "&rsaquo;");
		Reg_name(rv, Bool_.N, 8217, "&rsquo;");
		Reg_name(rv, Bool_.N, 8218, "&sbquo;");
		Reg_name(rv, Bool_.N,  352, "&Scaron;");
		Reg_name(rv, Bool_.N,  353, "&scaron;");
		Reg_name(rv, Bool_.N, 8901, "&sdot;");
		Reg_name(rv, Bool_.N,  167, "&sect;");
		Reg_name(rv, Bool_.N,  173, "&shy;");
		Reg_name(rv, Bool_.N,  931, "&Sigma;");
		Reg_name(rv, Bool_.N,  963, "&sigma;");
		Reg_name(rv, Bool_.N,  962, "&sigmaf;");
		Reg_name(rv, Bool_.N, 8764, "&sim;");
		Reg_name(rv, Bool_.N, 9824, "&spades;");
		Reg_name(rv, Bool_.N, 8834, "&sub;");
		Reg_name(rv, Bool_.N, 8838, "&sube;");
		Reg_name(rv, Bool_.N, 8721, "&sum;");
		Reg_name(rv, Bool_.N, 8835, "&sup;");
		Reg_name(rv, Bool_.N,  185, "&sup1;");
		Reg_name(rv, Bool_.N,  178, "&sup2;");
		Reg_name(rv, Bool_.N,  179, "&sup3;");
		Reg_name(rv, Bool_.N, 8839, "&supe;");
		Reg_name(rv, Bool_.N,  223, "&szlig;");
		Reg_name(rv, Bool_.N,  932, "&Tau;");
		Reg_name(rv, Bool_.N,  964, "&tau;");
		Reg_name(rv, Bool_.N, 8756, "&there4;");
		Reg_name(rv, Bool_.N,  920, "&Theta;");
		Reg_name(rv, Bool_.N,  952, "&theta;");
		Reg_name(rv, Bool_.N,  977, "&thetasym;");
		Reg_name(rv, Bool_.N, 8201, "&thinsp;");
		Reg_name(rv, Bool_.N,  222, "&THORN;");
		Reg_name(rv, Bool_.N,  254, "&thorn;");
		Reg_name(rv, Bool_.N,  732, "&tilde;");
		Reg_name(rv, Bool_.N,  215, "&times;");
		Reg_name(rv, Bool_.N, 8482, "&trade;");
		Reg_name(rv, Bool_.N,  218, "&Uacute;");
		Reg_name(rv, Bool_.N,  250, "&uacute;");
		Reg_name(rv, Bool_.N, 8593, "&uarr;");
		Reg_name(rv, Bool_.N, 8657, "&uArr;");
		Reg_name(rv, Bool_.N,  219, "&Ucirc;");
		Reg_name(rv, Bool_.N,  251, "&ucirc;");
		Reg_name(rv, Bool_.N,  217, "&Ugrave;");
		Reg_name(rv, Bool_.N,  249, "&ugrave;");
		Reg_name(rv, Bool_.N,  168, "&uml;");
		Reg_name(rv, Bool_.N,  978, "&upsih;");
		Reg_name(rv, Bool_.N,  933, "&Upsilon;");
		Reg_name(rv, Bool_.N,  965, "&upsilon;");
		Reg_name(rv, Bool_.N,  220, "&Uuml;");
		Reg_name(rv, Bool_.N,  252, "&uuml;");
		Reg_name(rv, Bool_.N, 8472, "&weierp;");
		Reg_name(rv, Bool_.N,  926, "&Xi;");
		Reg_name(rv, Bool_.N,  958, "&xi;");
		Reg_name(rv, Bool_.N,  221, "&Yacute;");
		Reg_name(rv, Bool_.N,  253, "&yacute;");
		Reg_name(rv, Bool_.N,  165, "&yen;");
		Reg_name(rv, Bool_.N,  376, "&Yuml;");
		Reg_name(rv, Bool_.N,  255, "&yuml;");
		Reg_name(rv, Bool_.N,  918, "&Zeta;");
		Reg_name(rv, Bool_.N,  950, "&zeta;");
		Reg_name(rv, Bool_.N, 8205, "&zwj;");
		Reg_name(rv, Bool_.N, 8204, "&zwnj;");
		Reg_prefix(rv, Xop_amp_trie_itm.Tid_num_hex, "#x");
		Reg_prefix(rv, Xop_amp_trie_itm.Tid_num_hex, "#X");
		Reg_prefix(rv, Xop_amp_trie_itm.Tid_num_dec, "#");
		return rv;
	}
	private static void Reg_name(Btrie_slim_mgr trie, boolean tid_is_xowa, int char_int, String xml_name_str) {Reg_name(trie, tid_is_xowa, char_int, Bry_.new_ascii_(xml_name_str));}
	private static void Reg_name(Btrie_slim_mgr trie, boolean tid_is_xowa, int char_int, byte[] xml_name_bry) {
		byte itm_tid = tid_is_xowa ? Xop_amp_trie_itm.Tid_name_xowa : Xop_amp_trie_itm.Tid_name_std;
		Xop_amp_trie_itm itm = new Xop_amp_trie_itm(itm_tid, char_int, xml_name_bry);
		byte[] key = Bry_.Mid(xml_name_bry, 1, xml_name_bry.length); // ignore & for purpose of trie; EX: "amp;"; NOTE: must keep trailing ";" else "&amp " will be valid;
		trie.Add_obj(key, itm);
	}
	private static void Reg_prefix(Btrie_slim_mgr trie, byte prefix_type, String prefix) {
		byte[] prefix_ary = Bry_.new_ascii_(prefix);
		Xop_amp_trie_itm itm = new Xop_amp_trie_itm(prefix_type, Xop_amp_trie_itm.Char_int_null, prefix_ary);
		trie.Add_obj(prefix_ary, itm);
	}
}
