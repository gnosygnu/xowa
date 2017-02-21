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
package gplx.langs.htmls.entitys; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
public class Gfh_entity_trie {	// TS
	public static final    String		// NOTE: top_define; entities needed for <nowiki> escaping
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
	public static final    Btrie_slim_mgr Instance = New(); Gfh_entity_trie() {}
	private static Btrie_slim_mgr New() {// REF.MW: Sanitizer|$wgHtmlEntities; NOTE:added apos
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs();
		Add_name(rv, Bool_.Y,   60, Str__xowa_lt);
		Add_name(rv, Bool_.Y,   91, Str__xowa_brack_bgn);
		Add_name(rv, Bool_.Y,   93, Str__xowa_brack_end);
		Add_name(rv, Bool_.Y,  124, Str__xowa_pipe);
		Add_name(rv, Bool_.Y,   39, Str__xowa_apos);
		Add_name(rv, Bool_.Y,   58, Str__xowa_colon);
		Add_name(rv, Bool_.Y,   95, Str__xowa_underline);
		Add_name(rv, Bool_.Y,   42, Str__xowa_asterisk);
		Add_name(rv, Bool_.Y,   32, Str__xowa_space);
		Add_name(rv, Bool_.Y,   10, Str__xowa_nl);
		Add_name(rv, Bool_.Y,   45, Str__xowa_dash);
		Add_name(rv, Bool_.N,   39, "&apos;");
		Add_name(rv, Bool_.N,  193, "&Aacute;");
		Add_name(rv, Bool_.N,  225, "&aacute;");
		Add_name(rv, Bool_.N,  194, "&Acirc;");
		Add_name(rv, Bool_.N,  226, "&acirc;");
		Add_name(rv, Bool_.N,  180, "&acute;");
		Add_name(rv, Bool_.N,  198, "&AElig;");
		Add_name(rv, Bool_.N,  230, "&aelig;");
		Add_name(rv, Bool_.N,  192, "&Agrave;");
		Add_name(rv, Bool_.N,  224, "&agrave;");
		Add_name(rv, Bool_.N, 8501, "&alefsym;");
		Add_name(rv, Bool_.N,  913, "&Alpha;");
		Add_name(rv, Bool_.N,  945, "&alpha;");
		Add_name(rv, Bool_.N,   38, "&amp;");
		Add_name(rv, Bool_.N, 8743, "&and;");
		Add_name(rv, Bool_.N, 8736, "&ang;");
		Add_name(rv, Bool_.N,  197, "&Aring;");
		Add_name(rv, Bool_.N,  229, "&aring;");
		Add_name(rv, Bool_.N, 8776, "&asymp;");
		Add_name(rv, Bool_.N,  195, "&Atilde;");
		Add_name(rv, Bool_.N,  227, "&atilde;");
		Add_name(rv, Bool_.N,  196, "&Auml;");
		Add_name(rv, Bool_.N,  228, "&auml;");
		Add_name(rv, Bool_.N, 8222, "&bdquo;");
		Add_name(rv, Bool_.N,  914, "&Beta;");
		Add_name(rv, Bool_.N,  946, "&beta;");
		Add_name(rv, Bool_.N,  166, "&brvbar;");
		Add_name(rv, Bool_.N, 8226, "&bull;");
		Add_name(rv, Bool_.N, 8745, "&cap;");
		Add_name(rv, Bool_.N,  199, "&Ccedil;");
		Add_name(rv, Bool_.N,  231, "&ccedil;");
		Add_name(rv, Bool_.N,  184, "&cedil;");
		Add_name(rv, Bool_.N,  162, "&cent;");
		Add_name(rv, Bool_.N,  935, "&Chi;");
		Add_name(rv, Bool_.N,  967, "&chi;");
		Add_name(rv, Bool_.N,  710, "&circ;");
		Add_name(rv, Bool_.N, 9827, "&clubs;");
		Add_name(rv, Bool_.N, 8773, "&cong;");
		Add_name(rv, Bool_.N,  169, "&copy;");
		Add_name(rv, Bool_.N, 8629, "&crarr;");
		Add_name(rv, Bool_.N, 8746, "&cup;");
		Add_name(rv, Bool_.N,  164, "&curren;");
		Add_name(rv, Bool_.N, 8224, "&dagger;");
		Add_name(rv, Bool_.N, 8225, "&Dagger;");
		Add_name(rv, Bool_.N, 8595, "&darr;");
		Add_name(rv, Bool_.N, 8659, "&dArr;");
		Add_name(rv, Bool_.N,  176, "&deg;");
		Add_name(rv, Bool_.N,  916, "&Delta;");
		Add_name(rv, Bool_.N,  948, "&delta;");
		Add_name(rv, Bool_.N, 9830, "&diams;");
		Add_name(rv, Bool_.N,  247, "&divide;");
		Add_name(rv, Bool_.N,  201, "&Eacute;");
		Add_name(rv, Bool_.N,  233, "&eacute;");
		Add_name(rv, Bool_.N,  202, "&Ecirc;");
		Add_name(rv, Bool_.N,  234, "&ecirc;");
		Add_name(rv, Bool_.N,  200, "&Egrave;");
		Add_name(rv, Bool_.N,  232, "&egrave;");
		Add_name(rv, Bool_.N, 8709, "&empty;");
		Add_name(rv, Bool_.N, 8195, "&emsp;");
		Add_name(rv, Bool_.N, 8194, "&ensp;");
		Add_name(rv, Bool_.N,  917, "&Epsilon;");
		Add_name(rv, Bool_.N,  949, "&epsilon;");
		Add_name(rv, Bool_.N, 8801, "&equiv;");
		Add_name(rv, Bool_.N,  919, "&Eta;");
		Add_name(rv, Bool_.N,  951, "&eta;");
		Add_name(rv, Bool_.N,  208, "&ETH;");
		Add_name(rv, Bool_.N,  240, "&eth;");
		Add_name(rv, Bool_.N,  203, "&Euml;");
		Add_name(rv, Bool_.N,  235, "&euml;");
		Add_name(rv, Bool_.N, 8364, "&euro;");
		Add_name(rv, Bool_.N, 8707, "&exist;");
		Add_name(rv, Bool_.N,  402, "&fnof;");
		Add_name(rv, Bool_.N, 8704, "&forall;");
		Add_name(rv, Bool_.N,  189, "&frac12;");
		Add_name(rv, Bool_.N,  188, "&frac14;");
		Add_name(rv, Bool_.N,  190, "&frac34;");
		Add_name(rv, Bool_.N, 8260, "&frasl;");
		Add_name(rv, Bool_.N,  915, "&Gamma;");
		Add_name(rv, Bool_.N,  947, "&gamma;");
		Add_name(rv, Bool_.N, 8805, "&ge;");
		Add_name(rv, Bool_.N,   62, "&gt;");
		Add_name(rv, Bool_.N, 8596, "&harr;");
		Add_name(rv, Bool_.N, 8660, "&hArr;");
		Add_name(rv, Bool_.N, 9829, "&hearts;");
		Add_name(rv, Bool_.N, 8230, "&hellip;");
		Add_name(rv, Bool_.N,  205, "&Iacute;");
		Add_name(rv, Bool_.N,  237, "&iacute;");
		Add_name(rv, Bool_.N,  206, "&Icirc;");
		Add_name(rv, Bool_.N,  238, "&icirc;");
		Add_name(rv, Bool_.N,  161, "&iexcl;");
		Add_name(rv, Bool_.N,  204, "&Igrave;");
		Add_name(rv, Bool_.N,  236, "&igrave;");
		Add_name(rv, Bool_.N, 8465, "&image;");
		Add_name(rv, Bool_.N, 8734, "&infin;");
		Add_name(rv, Bool_.N, 8747, "&int;");
		Add_name(rv, Bool_.N,  921, "&Iota;");
		Add_name(rv, Bool_.N,  953, "&iota;");
		Add_name(rv, Bool_.N,  191, "&iquest;");
		Add_name(rv, Bool_.N, 8712, "&isin;");
		Add_name(rv, Bool_.N,  207, "&Iuml;");
		Add_name(rv, Bool_.N,  239, "&iuml;");
		Add_name(rv, Bool_.N,  922, "&Kappa;");
		Add_name(rv, Bool_.N,  954, "&kappa;");
		Add_name(rv, Bool_.N,  923, "&Lambda;");
		Add_name(rv, Bool_.N,  955, "&lambda;");
		Add_name(rv, Bool_.N, 9001, "&lang;");
		Add_name(rv, Bool_.N,  171, "&laquo;");
		Add_name(rv, Bool_.N, 8592, "&larr;");
		Add_name(rv, Bool_.N, 8656, "&lArr;");
		Add_name(rv, Bool_.N, 8968, "&lceil;");
		Add_name(rv, Bool_.N, 8220, "&ldquo;");
		Add_name(rv, Bool_.N, 8804, "&le;");
		Add_name(rv, Bool_.N, 8970, "&lfloor;");
		Add_name(rv, Bool_.N, 8727, "&lowast;");
		Add_name(rv, Bool_.N, 9674, "&loz;");
		Add_name(rv, Bool_.N, 8206, "&lrm;");
		Add_name(rv, Bool_.N, 8249, "&lsaquo;");
		Add_name(rv, Bool_.N, 8216, "&lsquo;");
		Add_name(rv, Bool_.N,   60, "&lt;");
		Add_name(rv, Bool_.N,  175, "&macr;");
		Add_name(rv, Bool_.N, 8212, "&mdash;");
		Add_name(rv, Bool_.N,  181, "&micro;");
		Add_name(rv, Bool_.N,  183, "&middot;");
		Add_name(rv, Bool_.N, 8722, "&minus;");
		Add_name(rv, Bool_.N,  924, "&Mu;");
		Add_name(rv, Bool_.N,  956, "&mu;");
		Add_name(rv, Bool_.N, 8711, "&nabla;");
		Add_name(rv, Bool_.N,  160, "&nbsp;");
		Add_name(rv, Bool_.N, 8211, "&ndash;");
		Add_name(rv, Bool_.N, 8800, "&ne;");
		Add_name(rv, Bool_.N, 8715, "&ni;");
		Add_name(rv, Bool_.N,  172, "&not;");
		Add_name(rv, Bool_.N, 8713, "&notin;");
		Add_name(rv, Bool_.N, 8836, "&nsub;");
		Add_name(rv, Bool_.N,  209, "&Ntilde;");
		Add_name(rv, Bool_.N,  241, "&ntilde;");
		Add_name(rv, Bool_.N,  925, "&Nu;");
		Add_name(rv, Bool_.N,  957, "&nu;");
		Add_name(rv, Bool_.N,  211, "&Oacute;");
		Add_name(rv, Bool_.N,  243, "&oacute;");
		Add_name(rv, Bool_.N,  212, "&Ocirc;");
		Add_name(rv, Bool_.N,  244, "&ocirc;");
		Add_name(rv, Bool_.N,  338, "&OElig;");
		Add_name(rv, Bool_.N,  339, "&oelig;");
		Add_name(rv, Bool_.N,  210, "&Ograve;");
		Add_name(rv, Bool_.N,  242, "&ograve;");
		Add_name(rv, Bool_.N, 8254, "&oline;");
		Add_name(rv, Bool_.N,  937, "&Omega;");
		Add_name(rv, Bool_.N,  969, "&omega;");
		Add_name(rv, Bool_.N,  927, "&Omicron;");
		Add_name(rv, Bool_.N,  959, "&omicron;");
		Add_name(rv, Bool_.N, 8853, "&oplus;");
		Add_name(rv, Bool_.N, 8744, "&or;");
		Add_name(rv, Bool_.N,  170, "&ordf;");
		Add_name(rv, Bool_.N,  186, "&ordm;");
		Add_name(rv, Bool_.N,  216, "&Oslash;");
		Add_name(rv, Bool_.N,  248, "&oslash;");
		Add_name(rv, Bool_.N,  213, "&Otilde;");
		Add_name(rv, Bool_.N,  245, "&otilde;");
		Add_name(rv, Bool_.N, 8855, "&otimes;");
		Add_name(rv, Bool_.N,  214, "&Ouml;");
		Add_name(rv, Bool_.N,  246, "&ouml;");
		Add_name(rv, Bool_.N,  182, "&para;");
		Add_name(rv, Bool_.N, 8706, "&part;");
		Add_name(rv, Bool_.N, 8240, "&permil;");
		Add_name(rv, Bool_.N, 8869, "&perp;");
		Add_name(rv, Bool_.N,  934, "&Phi;");
		Add_name(rv, Bool_.N,  966, "&phi;");
		Add_name(rv, Bool_.N,  928, "&Pi;");
		Add_name(rv, Bool_.N,  960, "&pi;");
		Add_name(rv, Bool_.N,  982, "&piv;");
		Add_name(rv, Bool_.N,  177, "&plusmn;");
		Add_name(rv, Bool_.N,  163, "&pound;");
		Add_name(rv, Bool_.N, 8242, "&prime;");
		Add_name(rv, Bool_.N, 8243, "&Prime;");
		Add_name(rv, Bool_.N, 8719, "&prod;");
		Add_name(rv, Bool_.N, 8733, "&prop;");
		Add_name(rv, Bool_.N,  936, "&Psi;");
		Add_name(rv, Bool_.N,  968, "&psi;");
		Add_name(rv, Bool_.N,   34, "&quot;");
		Add_name(rv, Bool_.N, 8730, "&radic;");
		Add_name(rv, Bool_.N, 9002, "&rang;");
		Add_name(rv, Bool_.N,  187, "&raquo;");
		Add_name(rv, Bool_.N, 8594, "&rarr;");
		Add_name(rv, Bool_.N, 8658, "&rArr;");
		Add_name(rv, Bool_.N, 8969, "&rceil;");
		Add_name(rv, Bool_.N, 8221, "&rdquo;");
		Add_name(rv, Bool_.N, 8476, "&real;");
		Add_name(rv, Bool_.N,  174, "&reg;");
		Add_name(rv, Bool_.N, 8971, "&rfloor;");
		Add_name(rv, Bool_.N,  929, "&Rho;");
		Add_name(rv, Bool_.N,  961, "&rho;");
		Add_name(rv, Bool_.N, 8207, "&rlm;");
		Add_name(rv, Bool_.N, 8250, "&rsaquo;");
		Add_name(rv, Bool_.N, 8217, "&rsquo;");
		Add_name(rv, Bool_.N, 8218, "&sbquo;");
		Add_name(rv, Bool_.N,  352, "&Scaron;");
		Add_name(rv, Bool_.N,  353, "&scaron;");
		Add_name(rv, Bool_.N, 8901, "&sdot;");
		Add_name(rv, Bool_.N,  167, "&sect;");
		Add_name(rv, Bool_.N,  173, "&shy;");
		Add_name(rv, Bool_.N,  931, "&Sigma;");
		Add_name(rv, Bool_.N,  963, "&sigma;");
		Add_name(rv, Bool_.N,  962, "&sigmaf;");
		Add_name(rv, Bool_.N, 8764, "&sim;");
		Add_name(rv, Bool_.N, 9824, "&spades;");
		Add_name(rv, Bool_.N, 8834, "&sub;");
		Add_name(rv, Bool_.N, 8838, "&sube;");
		Add_name(rv, Bool_.N, 8721, "&sum;");
		Add_name(rv, Bool_.N, 8835, "&sup;");
		Add_name(rv, Bool_.N,  185, "&sup1;");
		Add_name(rv, Bool_.N,  178, "&sup2;");
		Add_name(rv, Bool_.N,  179, "&sup3;");
		Add_name(rv, Bool_.N, 8839, "&supe;");
		Add_name(rv, Bool_.N,  223, "&szlig;");
		Add_name(rv, Bool_.N,  932, "&Tau;");
		Add_name(rv, Bool_.N,  964, "&tau;");
		Add_name(rv, Bool_.N, 8756, "&there4;");
		Add_name(rv, Bool_.N,  920, "&Theta;");
		Add_name(rv, Bool_.N,  952, "&theta;");
		Add_name(rv, Bool_.N,  977, "&thetasym;");
		Add_name(rv, Bool_.N, 8201, "&thinsp;");
		Add_name(rv, Bool_.N,  222, "&THORN;");
		Add_name(rv, Bool_.N,  254, "&thorn;");
		Add_name(rv, Bool_.N,  732, "&tilde;");
		Add_name(rv, Bool_.N,  215, "&times;");
		Add_name(rv, Bool_.N, 8482, "&trade;");
		Add_name(rv, Bool_.N,  218, "&Uacute;");
		Add_name(rv, Bool_.N,  250, "&uacute;");
		Add_name(rv, Bool_.N, 8593, "&uarr;");
		Add_name(rv, Bool_.N, 8657, "&uArr;");
		Add_name(rv, Bool_.N,  219, "&Ucirc;");
		Add_name(rv, Bool_.N,  251, "&ucirc;");
		Add_name(rv, Bool_.N,  217, "&Ugrave;");
		Add_name(rv, Bool_.N,  249, "&ugrave;");
		Add_name(rv, Bool_.N,  168, "&uml;");
		Add_name(rv, Bool_.N,  978, "&upsih;");
		Add_name(rv, Bool_.N,  933, "&Upsilon;");
		Add_name(rv, Bool_.N,  965, "&upsilon;");
		Add_name(rv, Bool_.N,  220, "&Uuml;");
		Add_name(rv, Bool_.N,  252, "&uuml;");
		Add_name(rv, Bool_.N, 8472, "&weierp;");
		Add_name(rv, Bool_.N,  926, "&Xi;");
		Add_name(rv, Bool_.N,  958, "&xi;");
		Add_name(rv, Bool_.N,  221, "&Yacute;");
		Add_name(rv, Bool_.N,  253, "&yacute;");
		Add_name(rv, Bool_.N,  165, "&yen;");
		Add_name(rv, Bool_.N,  376, "&Yuml;");
		Add_name(rv, Bool_.N,  255, "&yuml;");
		Add_name(rv, Bool_.N,  918, "&Zeta;");
		Add_name(rv, Bool_.N,  950, "&zeta;");
		Add_name(rv, Bool_.N, 8205, "&zwj;");
		Add_name(rv, Bool_.N, 8204, "&zwnj;");
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
