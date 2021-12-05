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
package gplx.langs.htmls.entitys;
import gplx.Bry_;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.objects.primitives.BoolUtl;
public class Gfh_entity_trie {	// TS
	public static final String		// NOTE: top_define; entities needed for <nowiki> escaping
	  Str__xowa_lt			= "&xowa_lt;"
	, Str__xowa_brack_bgn	= "&xowa_brack_bgn;"
	, Str__xowa_brack_end	= "&xowa_brack_end;"
	, Str__xowa_pipe		= "&xowa_pipe;"
	, Str__xowa_apos		= "&xowa_apos;"
	, Str__xowa_colon		= "&xowa_colon;"
	, Str__xowa_underline	= "&xowa_underline;"
	, Str__xowa_asterisk	= "&xowa_asterisk;"
	, Str__xowa_space		= "&xowa_space;"
	, Str__xowa_nl			= "&xowa_nl;"
	, Str__xowa_dash		= "&xowa_dash;"
	;
	public static final Btrie_slim_mgr Instance = New(); Gfh_entity_trie() {}
	private static Btrie_slim_mgr New() {// REF.MW: Sanitizer|$wgHtmlEntities; NOTE:added apos
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs();
		Add_name(rv, BoolUtl.Y,   60, Str__xowa_lt);
		Add_name(rv, BoolUtl.Y,   91, Str__xowa_brack_bgn);
		Add_name(rv, BoolUtl.Y,   93, Str__xowa_brack_end);
		Add_name(rv, BoolUtl.Y,  124, Str__xowa_pipe);
		Add_name(rv, BoolUtl.Y,   39, Str__xowa_apos);
		Add_name(rv, BoolUtl.Y,   58, Str__xowa_colon);
		Add_name(rv, BoolUtl.Y,   95, Str__xowa_underline);
		Add_name(rv, BoolUtl.Y,   42, Str__xowa_asterisk);
		Add_name(rv, BoolUtl.Y,   32, Str__xowa_space);
		Add_name(rv, BoolUtl.Y,   10, Str__xowa_nl);
		Add_name(rv, BoolUtl.Y,   45, Str__xowa_dash);
		Add_name(rv, BoolUtl.N,   39, "&apos;");
		Add_name(rv, BoolUtl.N,  193, "&Aacute;");
		Add_name(rv, BoolUtl.N,  225, "&aacute;");
		Add_name(rv, BoolUtl.N,  194, "&Acirc;");
		Add_name(rv, BoolUtl.N,  226, "&acirc;");
		Add_name(rv, BoolUtl.N,  180, "&acute;");
		Add_name(rv, BoolUtl.N,  198, "&AElig;");
		Add_name(rv, BoolUtl.N,  230, "&aelig;");
		Add_name(rv, BoolUtl.N,  192, "&Agrave;");
		Add_name(rv, BoolUtl.N,  224, "&agrave;");
		Add_name(rv, BoolUtl.N, 8501, "&alefsym;");
		Add_name(rv, BoolUtl.N,  913, "&Alpha;");
		Add_name(rv, BoolUtl.N,  945, "&alpha;");
		Add_name(rv, BoolUtl.N,   38, "&amp;");
		Add_name(rv, BoolUtl.N, 8743, "&and;");
		Add_name(rv, BoolUtl.N, 8736, "&ang;");
		Add_name(rv, BoolUtl.N,  197, "&Aring;");
		Add_name(rv, BoolUtl.N,  229, "&aring;");
		Add_name(rv, BoolUtl.N, 8776, "&asymp;");
		Add_name(rv, BoolUtl.N,  195, "&Atilde;");
		Add_name(rv, BoolUtl.N,  227, "&atilde;");
		Add_name(rv, BoolUtl.N,  196, "&Auml;");
		Add_name(rv, BoolUtl.N,  228, "&auml;");
		Add_name(rv, BoolUtl.N, 8222, "&bdquo;");
		Add_name(rv, BoolUtl.N,  914, "&Beta;");
		Add_name(rv, BoolUtl.N,  946, "&beta;");
		Add_name(rv, BoolUtl.N,  166, "&brvbar;");
		Add_name(rv, BoolUtl.N, 8226, "&bull;");
		Add_name(rv, BoolUtl.N, 8745, "&cap;");
		Add_name(rv, BoolUtl.N,  199, "&Ccedil;");
		Add_name(rv, BoolUtl.N,  231, "&ccedil;");
		Add_name(rv, BoolUtl.N,  184, "&cedil;");
		Add_name(rv, BoolUtl.N,  162, "&cent;");
		Add_name(rv, BoolUtl.N,  935, "&Chi;");
		Add_name(rv, BoolUtl.N,  967, "&chi;");
		Add_name(rv, BoolUtl.N,  710, "&circ;");
		Add_name(rv, BoolUtl.N, 9827, "&clubs;");
		Add_name(rv, BoolUtl.N, 8773, "&cong;");
		Add_name(rv, BoolUtl.N,  169, "&copy;");
		Add_name(rv, BoolUtl.N, 8629, "&crarr;");
		Add_name(rv, BoolUtl.N, 8746, "&cup;");
		Add_name(rv, BoolUtl.N,  164, "&curren;");
		Add_name(rv, BoolUtl.N, 8224, "&dagger;");
		Add_name(rv, BoolUtl.N, 8225, "&Dagger;");
		Add_name(rv, BoolUtl.N, 8595, "&darr;");
		Add_name(rv, BoolUtl.N, 8659, "&dArr;");
		Add_name(rv, BoolUtl.N,  176, "&deg;");
		Add_name(rv, BoolUtl.N,  916, "&Delta;");
		Add_name(rv, BoolUtl.N,  948, "&delta;");
		Add_name(rv, BoolUtl.N, 9830, "&diams;");
		Add_name(rv, BoolUtl.N,  247, "&divide;");
		Add_name(rv, BoolUtl.N,  201, "&Eacute;");
		Add_name(rv, BoolUtl.N,  233, "&eacute;");
		Add_name(rv, BoolUtl.N,  202, "&Ecirc;");
		Add_name(rv, BoolUtl.N,  234, "&ecirc;");
		Add_name(rv, BoolUtl.N,  200, "&Egrave;");
		Add_name(rv, BoolUtl.N,  232, "&egrave;");
		Add_name(rv, BoolUtl.N, 8709, "&empty;");
		Add_name(rv, BoolUtl.N, 8195, "&emsp;");
		Add_name(rv, BoolUtl.N, 8194, "&ensp;");
		Add_name(rv, BoolUtl.N,  917, "&Epsilon;");
		Add_name(rv, BoolUtl.N,  949, "&epsilon;");
		Add_name(rv, BoolUtl.N, 8801, "&equiv;");
		Add_name(rv, BoolUtl.N,  919, "&Eta;");
		Add_name(rv, BoolUtl.N,  951, "&eta;");
		Add_name(rv, BoolUtl.N,  208, "&ETH;");
		Add_name(rv, BoolUtl.N,  240, "&eth;");
		Add_name(rv, BoolUtl.N,  203, "&Euml;");
		Add_name(rv, BoolUtl.N,  235, "&euml;");
		Add_name(rv, BoolUtl.N, 8364, "&euro;");
		Add_name(rv, BoolUtl.N, 8707, "&exist;");
		Add_name(rv, BoolUtl.N,  402, "&fnof;");
		Add_name(rv, BoolUtl.N, 8704, "&forall;");
		Add_name(rv, BoolUtl.N,  189, "&frac12;");
		Add_name(rv, BoolUtl.N,  188, "&frac14;");
		Add_name(rv, BoolUtl.N,  190, "&frac34;");
		Add_name(rv, BoolUtl.N, 8260, "&frasl;");
		Add_name(rv, BoolUtl.N,  915, "&Gamma;");
		Add_name(rv, BoolUtl.N,  947, "&gamma;");
		Add_name(rv, BoolUtl.N, 8805, "&ge;");
		Add_name(rv, BoolUtl.N,   62, "&gt;");
		Add_name(rv, BoolUtl.N, 8596, "&harr;");
		Add_name(rv, BoolUtl.N, 8660, "&hArr;");
		Add_name(rv, BoolUtl.N, 9829, "&hearts;");
		Add_name(rv, BoolUtl.N, 8230, "&hellip;");
		Add_name(rv, BoolUtl.N,  205, "&Iacute;");
		Add_name(rv, BoolUtl.N,  237, "&iacute;");
		Add_name(rv, BoolUtl.N,  206, "&Icirc;");
		Add_name(rv, BoolUtl.N,  238, "&icirc;");
		Add_name(rv, BoolUtl.N,  161, "&iexcl;");
		Add_name(rv, BoolUtl.N,  204, "&Igrave;");
		Add_name(rv, BoolUtl.N,  236, "&igrave;");
		Add_name(rv, BoolUtl.N, 8465, "&image;");
		Add_name(rv, BoolUtl.N, 8734, "&infin;");
		Add_name(rv, BoolUtl.N, 8747, "&int;");
		Add_name(rv, BoolUtl.N,  921, "&Iota;");
		Add_name(rv, BoolUtl.N,  953, "&iota;");
		Add_name(rv, BoolUtl.N,  191, "&iquest;");
		Add_name(rv, BoolUtl.N, 8712, "&isin;");
		Add_name(rv, BoolUtl.N,  207, "&Iuml;");
		Add_name(rv, BoolUtl.N,  239, "&iuml;");
		Add_name(rv, BoolUtl.N,  922, "&Kappa;");
		Add_name(rv, BoolUtl.N,  954, "&kappa;");
		Add_name(rv, BoolUtl.N,  923, "&Lambda;");
		Add_name(rv, BoolUtl.N,  955, "&lambda;");
		Add_name(rv, BoolUtl.N, 9001, "&lang;");
		Add_name(rv, BoolUtl.N,  171, "&laquo;");
		Add_name(rv, BoolUtl.N, 8592, "&larr;");
		Add_name(rv, BoolUtl.N, 8656, "&lArr;");
		Add_name(rv, BoolUtl.N, 8968, "&lceil;");
		Add_name(rv, BoolUtl.N, 8220, "&ldquo;");
		Add_name(rv, BoolUtl.N, 8804, "&le;");
		Add_name(rv, BoolUtl.N, 8970, "&lfloor;");
		Add_name(rv, BoolUtl.N, 8727, "&lowast;");
		Add_name(rv, BoolUtl.N, 9674, "&loz;");
		Add_name(rv, BoolUtl.N, 8206, "&lrm;");
		Add_name(rv, BoolUtl.N, 8249, "&lsaquo;");
		Add_name(rv, BoolUtl.N, 8216, "&lsquo;");
		Add_name(rv, BoolUtl.N,   60, "&lt;");
		Add_name(rv, BoolUtl.N,  175, "&macr;");
		Add_name(rv, BoolUtl.N, 8212, "&mdash;");
		Add_name(rv, BoolUtl.N,  181, "&micro;");
		Add_name(rv, BoolUtl.N,  183, "&middot;");
		Add_name(rv, BoolUtl.N, 8722, "&minus;");
		Add_name(rv, BoolUtl.N,  924, "&Mu;");
		Add_name(rv, BoolUtl.N,  956, "&mu;");
		Add_name(rv, BoolUtl.N, 8711, "&nabla;");
		Add_name(rv, BoolUtl.N,  160, "&nbsp;");
		Add_name(rv, BoolUtl.N, 8211, "&ndash;");
		Add_name(rv, BoolUtl.N, 8800, "&ne;");
		Add_name(rv, BoolUtl.N, 8715, "&ni;");
		Add_name(rv, BoolUtl.N,  172, "&not;");
		Add_name(rv, BoolUtl.N, 8713, "&notin;");
		Add_name(rv, BoolUtl.N, 8836, "&nsub;");
		Add_name(rv, BoolUtl.N,  209, "&Ntilde;");
		Add_name(rv, BoolUtl.N,  241, "&ntilde;");
		Add_name(rv, BoolUtl.N,  925, "&Nu;");
		Add_name(rv, BoolUtl.N,  957, "&nu;");
		Add_name(rv, BoolUtl.N,  211, "&Oacute;");
		Add_name(rv, BoolUtl.N,  243, "&oacute;");
		Add_name(rv, BoolUtl.N,  212, "&Ocirc;");
		Add_name(rv, BoolUtl.N,  244, "&ocirc;");
		Add_name(rv, BoolUtl.N,  338, "&OElig;");
		Add_name(rv, BoolUtl.N,  339, "&oelig;");
		Add_name(rv, BoolUtl.N,  210, "&Ograve;");
		Add_name(rv, BoolUtl.N,  242, "&ograve;");
		Add_name(rv, BoolUtl.N, 8254, "&oline;");
		Add_name(rv, BoolUtl.N,  937, "&Omega;");
		Add_name(rv, BoolUtl.N,  969, "&omega;");
		Add_name(rv, BoolUtl.N,  927, "&Omicron;");
		Add_name(rv, BoolUtl.N,  959, "&omicron;");
		Add_name(rv, BoolUtl.N, 8853, "&oplus;");
		Add_name(rv, BoolUtl.N, 8744, "&or;");
		Add_name(rv, BoolUtl.N,  170, "&ordf;");
		Add_name(rv, BoolUtl.N,  186, "&ordm;");
		Add_name(rv, BoolUtl.N,  216, "&Oslash;");
		Add_name(rv, BoolUtl.N,  248, "&oslash;");
		Add_name(rv, BoolUtl.N,  213, "&Otilde;");
		Add_name(rv, BoolUtl.N,  245, "&otilde;");
		Add_name(rv, BoolUtl.N, 8855, "&otimes;");
		Add_name(rv, BoolUtl.N,  214, "&Ouml;");
		Add_name(rv, BoolUtl.N,  246, "&ouml;");
		Add_name(rv, BoolUtl.N,  182, "&para;");
		Add_name(rv, BoolUtl.N, 8706, "&part;");
		Add_name(rv, BoolUtl.N, 8240, "&permil;");
		Add_name(rv, BoolUtl.N, 8869, "&perp;");
		Add_name(rv, BoolUtl.N,  934, "&Phi;");
		Add_name(rv, BoolUtl.N,  966, "&phi;");
		Add_name(rv, BoolUtl.N,  928, "&Pi;");
		Add_name(rv, BoolUtl.N,  960, "&pi;");
		Add_name(rv, BoolUtl.N,  982, "&piv;");
		Add_name(rv, BoolUtl.N,  177, "&plusmn;");
		Add_name(rv, BoolUtl.N,  163, "&pound;");
		Add_name(rv, BoolUtl.N, 8242, "&prime;");
		Add_name(rv, BoolUtl.N, 8243, "&Prime;");
		Add_name(rv, BoolUtl.N, 8719, "&prod;");
		Add_name(rv, BoolUtl.N, 8733, "&prop;");
		Add_name(rv, BoolUtl.N,  936, "&Psi;");
		Add_name(rv, BoolUtl.N,  968, "&psi;");
		Add_name(rv, BoolUtl.N,   34, "&quot;");
		Add_name(rv, BoolUtl.N, 8730, "&radic;");
		Add_name(rv, BoolUtl.N, 9002, "&rang;");
		Add_name(rv, BoolUtl.N,  187, "&raquo;");
		Add_name(rv, BoolUtl.N, 8594, "&rarr;");
		Add_name(rv, BoolUtl.N, 8658, "&rArr;");
		Add_name(rv, BoolUtl.N, 8969, "&rceil;");
		Add_name(rv, BoolUtl.N, 8221, "&rdquo;");
		Add_name(rv, BoolUtl.N, 8476, "&real;");
		Add_name(rv, BoolUtl.N,  174, "&reg;");
		Add_name(rv, BoolUtl.N, 8971, "&rfloor;");
		Add_name(rv, BoolUtl.N,  929, "&Rho;");
		Add_name(rv, BoolUtl.N,  961, "&rho;");
		Add_name(rv, BoolUtl.N, 8207, "&rlm;");
		Add_name(rv, BoolUtl.N, 8250, "&rsaquo;");
		Add_name(rv, BoolUtl.N, 8217, "&rsquo;");
		Add_name(rv, BoolUtl.N, 8218, "&sbquo;");
		Add_name(rv, BoolUtl.N,  352, "&Scaron;");
		Add_name(rv, BoolUtl.N,  353, "&scaron;");
		Add_name(rv, BoolUtl.N, 8901, "&sdot;");
		Add_name(rv, BoolUtl.N,  167, "&sect;");
		Add_name(rv, BoolUtl.N,  173, "&shy;");
		Add_name(rv, BoolUtl.N,  931, "&Sigma;");
		Add_name(rv, BoolUtl.N,  963, "&sigma;");
		Add_name(rv, BoolUtl.N,  962, "&sigmaf;");
		Add_name(rv, BoolUtl.N, 8764, "&sim;");
		Add_name(rv, BoolUtl.N, 9824, "&spades;");
		Add_name(rv, BoolUtl.N, 8834, "&sub;");
		Add_name(rv, BoolUtl.N, 8838, "&sube;");
		Add_name(rv, BoolUtl.N, 8721, "&sum;");
		Add_name(rv, BoolUtl.N, 8835, "&sup;");
		Add_name(rv, BoolUtl.N,  185, "&sup1;");
		Add_name(rv, BoolUtl.N,  178, "&sup2;");
		Add_name(rv, BoolUtl.N,  179, "&sup3;");
		Add_name(rv, BoolUtl.N, 8839, "&supe;");
		Add_name(rv, BoolUtl.N,  223, "&szlig;");
		Add_name(rv, BoolUtl.N,  932, "&Tau;");
		Add_name(rv, BoolUtl.N,  964, "&tau;");
		Add_name(rv, BoolUtl.N, 8756, "&there4;");
		Add_name(rv, BoolUtl.N,  920, "&Theta;");
		Add_name(rv, BoolUtl.N,  952, "&theta;");
		Add_name(rv, BoolUtl.N,  977, "&thetasym;");
		Add_name(rv, BoolUtl.N, 8201, "&thinsp;");
		Add_name(rv, BoolUtl.N,  222, "&THORN;");
		Add_name(rv, BoolUtl.N,  254, "&thorn;");
		Add_name(rv, BoolUtl.N,  732, "&tilde;");
		Add_name(rv, BoolUtl.N,  215, "&times;");
		Add_name(rv, BoolUtl.N, 8482, "&trade;");
		Add_name(rv, BoolUtl.N,  218, "&Uacute;");
		Add_name(rv, BoolUtl.N,  250, "&uacute;");
		Add_name(rv, BoolUtl.N, 8593, "&uarr;");
		Add_name(rv, BoolUtl.N, 8657, "&uArr;");
		Add_name(rv, BoolUtl.N,  219, "&Ucirc;");
		Add_name(rv, BoolUtl.N,  251, "&ucirc;");
		Add_name(rv, BoolUtl.N,  217, "&Ugrave;");
		Add_name(rv, BoolUtl.N,  249, "&ugrave;");
		Add_name(rv, BoolUtl.N,  168, "&uml;");
		Add_name(rv, BoolUtl.N,  978, "&upsih;");
		Add_name(rv, BoolUtl.N,  933, "&Upsilon;");
		Add_name(rv, BoolUtl.N,  965, "&upsilon;");
		Add_name(rv, BoolUtl.N,  220, "&Uuml;");
		Add_name(rv, BoolUtl.N,  252, "&uuml;");
		Add_name(rv, BoolUtl.N, 8472, "&weierp;");
		Add_name(rv, BoolUtl.N,  926, "&Xi;");
		Add_name(rv, BoolUtl.N,  958, "&xi;");
		Add_name(rv, BoolUtl.N,  221, "&Yacute;");
		Add_name(rv, BoolUtl.N,  253, "&yacute;");
		Add_name(rv, BoolUtl.N,  165, "&yen;");
		Add_name(rv, BoolUtl.N,  376, "&Yuml;");
		Add_name(rv, BoolUtl.N,  255, "&yuml;");
		Add_name(rv, BoolUtl.N,  918, "&Zeta;");
		Add_name(rv, BoolUtl.N,  950, "&zeta;");
		Add_name(rv, BoolUtl.N, 8205, "&zwj;");
		Add_name(rv, BoolUtl.N, 8204, "&zwnj;");
		Add_prefix(rv, Gfh_entity_itm.Tid_num_hex, "#x");
		Add_prefix(rv, Gfh_entity_itm.Tid_num_hex, "#X");
		Add_prefix(rv, Gfh_entity_itm.Tid_num_dec, "#");
		return rv;
	}
	private static void Add_name(Btrie_slim_mgr trie, boolean tid_is_xowa, int char_int, String xml_name_str) {
		byte itm_tid = tid_is_xowa ? Gfh_entity_itm.Tid_name_xowa : Gfh_entity_itm.Tid_name_std;
		byte[] xml_name_bry = Bry_.new_a7(xml_name_str);
		byte[] key = Bry_.Mid(xml_name_bry, 1, xml_name_bry.length); // ignore & for purpose of trie; EX: "amp;"; NOTE: must keep trailing ";" else "&amp " will be valid;
		trie.Add_obj(key, new Gfh_entity_itm(itm_tid, char_int, xml_name_bry));
	}
	private static void Add_prefix(Btrie_slim_mgr trie, byte prefix_type, String prefix) {
		byte[] prefix_ary = Bry_.new_u8(prefix);
		Gfh_entity_itm itm = new Gfh_entity_itm(prefix_type, Gfh_entity_itm.Char_int_null, prefix_ary);
		trie.Add_obj(prefix_ary, itm);
	}
}
