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
package gplx.langs.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.errs.ErrUtl;
public class Gfh_tag_ {	// NOTE: not serialized; used by tag_rdr
	public static final int 
	  Id__comment		= -3
	, Id__eos			= -2
	, Id__any			= -1
	, Id__unknown		=  0
	, Id__h1			=  1
	, Id__h2			=  2
	, Id__h3			=  3
	, Id__h4			=  4
	, Id__h5			=  5
	, Id__h6			=  6
	, Id__a				=  7
	, Id__span			=  8
	, Id__div			=  9
	, Id__img			= 10
	, Id__ul			= 11
	, Id__ol			= 12
	, Id__li			= 13
	, Id__dl			= 14
	, Id__dd			= 15
	, Id__dt			= 16
	, Id__p				= 17
	, Id__br			= 18
	, Id__hr			= 19
	, Id__table			= 20
	, Id__tr			= 21
	, Id__td			= 22
	, Id__th			= 23
	, Id__thead			= 24
	, Id__tbody			= 25
	, Id__caption		= 26
	, Id__pre			= 27
	, Id__small			= 28
	, Id__i				= 29
	, Id__b				= 30
	, Id__sup			= 31
	, Id__sub			= 32
	, Id__bdi			= 33
	, Id__font			= 34
	, Id__strong		= 35
	, Id__s				= 36
	, Id__abbr			= 37
	, Id__cite			= 38
	, Id__var			= 39
	, Id__u				= 40
	, Id__big			= 41
	, Id__del			= 42
	, Id__strike		= 43
	, Id__tt			= 44
	, Id__code			= 45
	, Id__wbr			= 46
	, Id__center		= 47	// not HTML5, but used by en.v:Vandalism_in_progress
	, Id__dfn			= 48
	, Id__kbd			= 49
	, Id__samp			= 50
	, Id__ins			= 51
	, Id__em			= 52
	, Id__blockquote	= 53
	, Id__map			= 54
	, Id__bdo			= 55
	, Id__time			= 56
	, Id__ruby			= 57
	, Id__rb			= 58
	, Id__rp			= 59
	, Id__rt			= 60
	, Id__form			= 61
	, Id__mark			= 62
	;
	public static final byte[]
	  Bry__a			= BryUtl.NewA7("a")
	, Bry__ul			= BryUtl.NewA7("ul")
	, Bry__td			= BryUtl.NewA7("td")
	, Bry__th			= BryUtl.NewA7("th")
	, Bry__div			= BryUtl.NewA7("div")
	, Bry__link			= BryUtl.NewA7("link")
	, Bry__style		= BryUtl.NewA7("style")
	, Bry__script		= BryUtl.NewA7("script")
	, Bry__xowa_any		= BryUtl.NewA7("xowa_any")
	, Bry__xowa_comment	= BryUtl.NewA7("xowa_comment")
	, Bry__img			= BryUtl.NewA7("img")
	;

//		private static final Gfh_tag_meta[] Ary = new Gfh_tag_meta[Id__ary_max];
//		private static final Hash_adp_bry tags_by_bry = Hash_adp_bry.ci_a7();
//		public static Gfh_tag_meta New_tag(int id, String key_str) {
//			Gfh_tag_meta rv = new Gfh_tag_meta(id, key_str);
//			Ary[id] = rv;
//			tags_by_bry.Add_bry_int(rv.Key_bry(), id);
//			return rv;
//		}
	public static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_bry_int(Bry__a			, Id__a)
	.Add_str_int("h1"			, Id__h1)
	.Add_str_int("h2"			, Id__h2)
	.Add_str_int("h3"			, Id__h3)
	.Add_str_int("h4"			, Id__h4)
	.Add_str_int("h5"			, Id__h5)
	.Add_str_int("h6"			, Id__h6)
	.Add_str_int("span"			, Id__span)
	.Add_str_int("div"			, Id__div)
	.Add_str_int("img"			, Id__img)
	.Add_str_int("br"			, Id__br)
	.Add_str_int("hr"			, Id__hr)
	.Add_str_int("ul"			, Id__ul)
	.Add_str_int("ol"			, Id__ol)
	.Add_str_int("li"			, Id__li)
	.Add_str_int("dl"			, Id__dl)
	.Add_str_int("dd"			, Id__dd)
	.Add_str_int("dt"			, Id__dt)
	.Add_str_int("table"		, Id__table)
	.Add_str_int("tr"			, Id__tr)
	.Add_str_int("td"			, Id__td)
	.Add_str_int("th"			, Id__th)
	.Add_str_int("thead"		, Id__thead)
	.Add_str_int("tbody"		, Id__tbody)
	.Add_str_int("caption"		, Id__caption)
	.Add_str_int("p"			, Id__p)
	.Add_str_int("pre"			, Id__pre)
	.Add_str_int("small"		, Id__small)
	.Add_str_int("i"			, Id__i)
	.Add_str_int("b"			, Id__b)
	.Add_str_int("sup"			, Id__sup)
	.Add_str_int("sub"			, Id__sub)
	.Add_str_int("bdi"			, Id__bdi)
	.Add_str_int("font"			, Id__font)
	.Add_str_int("strong"		, Id__strong)
	.Add_str_int("s"			, Id__s)
	.Add_str_int("abbr"			, Id__abbr)
	.Add_str_int("cite"			, Id__cite)
	.Add_str_int("var"			, Id__var)
	.Add_str_int("u"			, Id__u)
	.Add_str_int("big"			, Id__big)
	.Add_str_int("del"			, Id__del)
	.Add_str_int("strike"		, Id__strike)
	.Add_str_int("tt"			, Id__tt)
	.Add_str_int("code"			, Id__code)
	.Add_str_int("wbr"			, Id__wbr)
	.Add_str_int("center"		, Id__center)
	.Add_str_int("dfn"			, Id__dfn)
	.Add_str_int("kbd"			, Id__kbd)
	.Add_str_int("samp"			, Id__samp)
	.Add_str_int("ins"			, Id__ins)
	.Add_str_int("em"			, Id__em)
	.Add_str_int("blockquote"	, Id__blockquote)
	.Add_str_int("map"			, Id__map)
	.Add_str_int("bdo"			, Id__bdo)
	.Add_str_int("time"			, Id__time)
	.Add_str_int("ruby"			, Id__ruby)
	.Add_str_int("rb"			, Id__rb)
	.Add_str_int("rp"			, Id__rp)
	.Add_str_int("rt"			, Id__rt)
	.Add_str_int("form"			, Id__form)
	.Add_str_int("mark"			, Id__mark)
	;
	public static String To_str(int tid) {
		switch (tid) {
			case Id__eos:			return "EOS";
			case Id__any:			return "any";
			case Id__unknown:		return "unknown";
			case Id__comment:		return "comment";
			case Id__h1:			return "h1";
			case Id__h2:			return "h2";
			case Id__h3:			return "h2";
			case Id__h4:			return "h2";
			case Id__h5:			return "h2";
			case Id__h6:			return "h2";
			case Id__a:				return "a";
			case Id__span:			return "span";
			case Id__div:			return "div";
			case Id__img:			return "img";
			case Id__p:				return "p";
			case Id__br:			return "br";
			case Id__hr:			return "hr";
			case Id__ul:			return "ul";
			case Id__ol:			return "ol";
			case Id__li:			return "li";
			case Id__dl:			return "dl";
			case Id__dd:			return "dd";
			case Id__dt:			return "dt";
			case Id__table:			return "table";
			case Id__tr:			return "tr";
			case Id__td:			return "td";
			case Id__th:			return "th";
			case Id__thead:			return "thead";
			case Id__tbody:			return "tbody";
			case Id__caption:		return "caption";
			case Id__pre:			return "pre";
			case Id__small:			return "small";
			case Id__i:				return "i";
			case Id__b:				return "b";
			case Id__sup:			return "sup";
			case Id__sub:			return "sub";
			case Id__bdi:			return "bdi";
			case Id__font:			return "font";
			case Id__strong:		return "strong";
			case Id__s:				return "s";
			case Id__abbr:			return "abbr";
			case Id__cite:			return "cite";
			case Id__var:			return "var";
			case Id__u:				return "u";
			case Id__big:			return "big";
			case Id__del:			return "del";
			case Id__strike:		return "strike";
			case Id__tt:			return "tt";
			case Id__code:			return "code";
			case Id__wbr:			return "wbr";
			case Id__center:		return "center";
			case Id__dfn:			return "dfn";
			case Id__kbd:			return "kbd";
			case Id__samp:			return "samp";
			case Id__ins:			return "ins";
			case Id__em:			return "em";
			case Id__blockquote:	return "blockquote";
			case Id__map:			return "map";
			case Id__bdo:			return "bdo";
			case Id__time:			return "time";
			case Id__ruby:			return "ruby";
			case Id__rb:			return "rb";
			case Id__rp:			return "rp";
			case Id__rt:			return "rt";
			case Id__form:			return "form";
			case Id__mark:			return "mark";
			default:				throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static final byte[]
	  Br_inl					= BryUtl.NewA7("<br/>")
	, Br_lhs					= BryUtl.NewA7("<br>")
	, Hr_inl					= BryUtl.NewA7("<hr/>")
	, Body_lhs					= BryUtl.NewA7("<body>")			, Body_rhs					= BryUtl.NewA7("</body>")
	, B_lhs						= BryUtl.NewA7("<b>")			, B_rhs						= BryUtl.NewA7("</b>")
	, I_lhs						= BryUtl.NewA7("<i>")			, I_rhs						= BryUtl.NewA7("</i>")
	, P_lhs						= BryUtl.NewA7("<p>")			, P_rhs						= BryUtl.NewA7("</p>")
	, Pre_lhs					= BryUtl.NewA7("<pre>")			, Pre_rhs					= BryUtl.NewA7("</pre>")
	, Div_lhs					= BryUtl.NewA7("<div>")			, Div_rhs					= BryUtl.NewA7("</div>")		, Div_lhs_bgn				= BryUtl.NewA7("<div")
	, Html_rhs					= BryUtl.NewA7("</html>")
	, Head_lhs_bgn				= BryUtl.NewA7("<head")			, Head_rhs					= BryUtl.NewA7("</head>")
	, Style_lhs_w_type			= BryUtl.NewA7("<style type=\"text/css\">")
	, Style_rhs					= BryUtl.NewA7("</style>")
	, Script_lhs				= BryUtl.NewA7("<script>")		, Script_rhs				= BryUtl.NewA7("</script>")
	, Script_lhs_w_type			= BryUtl.NewA7("<script type='text/javascript'>")
	, Span_lhs					= BryUtl.NewA7("<span")			, Span_rhs					= BryUtl.NewA7("</span>")
	, Strong_lhs				= BryUtl.NewA7("<strong>")		, Strong_rhs				= BryUtl.NewA7("</strong>")
	, Ul_lhs					= BryUtl.NewA7("<ul>")			, Ul_rhs					= BryUtl.NewA7("</ul>")
	, Ol_lhs					= BryUtl.NewA7("<ol>")			, Ol_rhs					= BryUtl.NewA7("</ol>")
	, Dt_lhs					= BryUtl.NewA7("<dt>")			, Dt_rhs					= BryUtl.NewA7("</dt>")
	, Dd_lhs					= BryUtl.NewA7("<dd>")			, Dd_rhs					= BryUtl.NewA7("</dd>")
	, Dl_lhs					= BryUtl.NewA7("<dl>")			, Dl_rhs					= BryUtl.NewA7("</dl>")
	, Li_lhs					= BryUtl.NewA7("<li>")			, Li_rhs					= BryUtl.NewA7("</li>")		, Li_lhs_bgn				= BryUtl.NewA7("<li")
	, Table_lhs					= BryUtl.NewA7("<table>")		, Table_rhs					= BryUtl.NewA7("</table>")	, Table_lhs_bgn				= BryUtl.NewA7("<table")
	, Tr_lhs					= BryUtl.NewA7("<tr>")			, Tr_rhs					= BryUtl.NewA7("</tr>")		, Tr_lhs_bgn				= BryUtl.NewA7("<tr")
	, Td_lhs					= BryUtl.NewA7("<td>")			, Td_rhs					= BryUtl.NewA7("</td>")		, Td_lhs_bgn				= BryUtl.NewA7("<td")
	, Th_lhs					= BryUtl.NewA7("<th>")			, Th_rhs					= BryUtl.NewA7("</th>")		, Th_lhs_bgn				= BryUtl.NewA7("<th")
	, Caption_lhs				= BryUtl.NewA7("<caption>")		, Caption_rhs				= BryUtl.NewA7("</caption>")	, Caption_lhs_bgn			= BryUtl.NewA7("<caption")
	;
	public static final String 
	  Comm_bgn_str				= "<!--"
	, Comm_end_str				= "-->"
	, Anchor_str				= "#"
	;
	public static final byte[]
	  Comm_bgn = BryUtl.NewA7(Comm_bgn_str), Comm_end = BryUtl.NewA7(Comm_end_str)
	;
	public static final int
	  Comm_bgn_len = Comm_bgn.length
	, Comm_end_len = Comm_end.length
	;
	public static final byte[] Rhs_bgn = BryUtl.NewA7("</");
	public static void Bld_lhs_bgn(BryWtr bfr, byte[] tag) {bfr.AddByte(AsciiByte.Lt).Add(tag);}						// <tag
	public static void Bld_lhs_bgn(BryWtr bfr, int tag_id) {bfr.AddByte(AsciiByte.Lt).AddStrA7(To_str(tag_id));}   // <tag
	public static void Bld_lhs_end_nde(BryWtr bfr)			{bfr.AddByte(AsciiByte.Gt);}								// >
	public static void Bld_lhs_end_inl(BryWtr bfr)			{bfr.AddByte(AsciiByte.Slash).AddByte(AsciiByte.Gt);}	// "/>"
	public static void Bld_rhs(BryWtr bfr, byte[] name)	{bfr.Add(Rhs_bgn).Add(name).AddByte(AsciiByte.AngleEnd);}	// EX:"</tag_name>"
}
