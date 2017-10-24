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
package gplx.gfml; import gplx.*;
class GfmlDocLxrs {
	@gplx.Internal protected static void Default_lxr(GfmlLxrRegy regy, GfmlLxr rootLxr) {
		GfmlLxr[] ary = new GfmlLxr[] {	GfmlDocLxrs.Whitespace_lxr()
									,	GfmlDocLxrs.Quote0_Eval0_lxr()
									,	GfmlDocLxrs.Quote1_lxr()
									,	GfmlDocLxrs.QuoteBlock_lxr()
									,	GfmlDocLxrs.QuoteBlock1_lxr()
									,	GfmlDocLxrs.QuoteBlock2_lxr()
									,	GfmlDocLxrs.NdeBodyBgn_lxr()
									,	GfmlDocLxrs.NdeBodyEnd_lxr()
									,	GfmlDocLxrs.NdeHeader_lxr()
									,	GfmlDocLxrs.NdeInline_lxr()
									,	GfmlDocLxrs.ElmKey_lxr()
									,	GfmlDocLxrs.Comment0_lxr()
									,	GfmlDocLxrs.Comment1_lxr()
									,	GfmlDocLxrs.NdePropBgn_lxr()
									,	GfmlDocLxrs.NdePropEnd_lxr()
									};
//			if (Op_sys.Cur().Tid_is_wnt())
//				regy.Add(Comment0a_lxr());
		for (GfmlLxr lxr : ary)
			regy.Add(lxr);
		rootLxr.SubLxr_Add(ary);
	}
	public static GfmlLxr Root_lxr() {
		GfmlTkn txtTkn = GfmlTkn_.cmd_("tkn:text", GfmlBldrCmd_dataTkn_set.Instance);
		return GfmlLxr_.general_("lxr:root", txtTkn);
	}
	public static GfmlLxr Whitespace_lxr() {
		GfmlTkn tkn = GfmlTkn_.cmd_("key:gfml.whitespace_0", GfmlBldrCmd_whitespace.Instance);
		GfmlLxr rv = GfmlLxr_.range_("lxr:gfml.whitespace_0", String_.Ary(" ", String_.Tab, String_.CrLf, String_.Lf), tkn, false);
		return rv;
	}
	public static GfmlLxr Comment0_lxr() {return GfmlLxr_.frame_("gfml.comment_0", GfmlFrame_.comment_(), "//", String_.Lf);}
//		public static GfmlLxr Comment0a_lxr() {return GfmlLxr_.frame_("gfml.comment_0b", GfmlFrame_.comment_(), "//", "\n");}
	public static GfmlLxr Comment1_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.comment_1", GfmlFrame_.comment_(), "/*", "*/");

		GfmlLxr escapeBgn = lxr_escape_("gfml.comment_1_escape_bgn", "/*/*", "/*");
		GfmlLxr escapeEnd = lxr_escape_("gfml.comment_1_escape_end", "*/*/", "*/");
		rv.SubLxr_Add(escapeBgn, escapeEnd, rv);
		return rv;
	}
	public static GfmlLxr Eval0_lxr() {return GfmlLxr_.frame_("gfml.eval_0", GfmlFrame_.eval_(), "<~", ">");}
	public static GfmlLxr Quote0_Eval0_lxr() {
		GfmlLxr rv = Quote0_lxr();
		GfmlLxr eval0 = Eval0_lxr();
		rv.SubLxr_Add(eval0);
		eval0.SubLxr_Add(rv); // recursive!
		return rv;
	}
	public static GfmlLxr Quote0_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_0", GfmlFrame_.quote_(), "'", "'");

		GfmlLxr escape = lxr_escape_("gfml.quote_0_escape",  "''", "'");
		rv.SubLxr_Add(escape);
		return rv;
	}
	public static GfmlLxr Quote1_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_1", GfmlFrame_.quote_(), "\"", "\"");

		GfmlLxr escape = lxr_escape_("gfml.quote_1_escape",  "\"\"", "\"");
		rv.SubLxr_Add(escape);
		return rv;
	}
	public static GfmlLxr QuoteBlock_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_block_0", GfmlFrame_.quote_(), "|'", "'|");

		GfmlLxr escapeBgn = lxr_escape_("gfml.quote_block_0_escape_bgn", "|'|'", "|'");
		GfmlLxr escapeEnd = lxr_escape_("gfml.quote_block_0_escape_end", "'|'|", "'|");
		rv.SubLxr_Add(escapeBgn, escapeEnd, rv);	// NOTE: adding rv makes it recursive
		return rv;
	}
	public static GfmlLxr QuoteBlock1_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_block_1", GfmlFrame_.quote_(), "<:['\n", "\n']:>");
//			GfmlLxr escapeBgn = lxr_escape_("gfml.quote_block_1_escape_bgn", "<~{'\n<~{'\n", "<~{'\n");	// doesn't work; causes dangling errors; need to debug later
//			GfmlLxr escapeEnd = lxr_escape_("gfml.quote_block_1_escape_end", "\n'}~>\n'}~>", "\n'}~>");
//			rv.SubLxr_Add(rv);
		return rv;
	}
	public static GfmlLxr QuoteBlock2_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_block_2", GfmlFrame_.quote_(), "<:[\"\n", "\n\"]:>");
		return rv;
	}
	public static GfmlLxr QuoteFold_lxr() {
		GfmlLxr rv = GfmlLxr_.frame_("gfml.quote_fold_0", GfmlFrame_.quote_(), "^'", "'^");

		GfmlTkn tkn = GfmlTkn_.valConst_("key:gfml.quote_fold_0_whitespace", GfmlTkn_.NullVal, GfmlBldrCmd_whitespace.Instance);
		GfmlLxr whitespace = GfmlLxr_.range_("lxr:gfml.quote_fold_0_whitespace", String_.Ary(String_.Tab, String_.CrLf, String_.Lf), tkn, false);
		GfmlLxr escapeBgn = lxr_escape_("gfml.quote_fold_0_escape_bgn", "^'^'", "^'");
		GfmlLxr escapeEnd = lxr_escape_("gfml.quote_fold_0_escape_end", "'^'^", "'^");
		rv.SubLxr_Add(whitespace, escapeBgn, escapeEnd, rv);	// NOTE: adding rv makes it recursive

		rv.SubLxr_Add(Eval0_lxr(),
		Comment0_lxr(),
		Comment1_lxr());
		return rv;
	}
	public static GfmlLxr ElmKey_lxr()			{return lxr_symbol_("gfml.elm_key_0", "=", GfmlBldrCmd_elemKey_set.Instance);}
	public static GfmlLxr NdeHeader_lxr()		{return lxr_symbol_("gfml.node_name_0", ":", GfmlBldrCmd_ndeName_set.Instance);}
	public static GfmlLxr NdeInline_lxr()		{return lxr_symbol_("gfml.node_inline_0", ";", GfmlBldrCmd_ndeInline.Instance);}
	public static GfmlLxr NdeBodyBgn_lxr()		{return lxr_symbol_("gfml.node_body_0_begin", "{", GfmlBldrCmd_ndeBody_bgn.Instance);}
	public static GfmlLxr NdeBodyEnd_lxr()		{return lxr_symbol_("gfml.node_body_0_end", "}", GfmlBldrCmd_frameEnd.nde_(GfmlNdeSymType.BodyEnd));}
	public static GfmlLxr NdePropBgn_lxr()		{return lxr_symbol_("lxr.gfml.node_prop_0_bgn", "[", GfmlBldrCmd_ndeProp_bgn.Instance);}
	public static GfmlLxr NdePropEnd_lxr()		{return lxr_symbol_("lxr.gfml.node_prop_0_end", "]", GfmlBldrCmd_frameEnd.nde_(GfmlNdeSymType.PrpEnd));}
	public static GfmlLxr NdeDot_lxr()			{return lxr_symbol_("gfml.node_drill_0", ".", GfmlBldrCmd_ndeDot.Instance);}
	public static GfmlLxr NdeHdrBgn_lxr()		{return lxr_symbol_("lxr.gfml.node_hdr_0_bgn", "(", GfmlBldrCmd_ndeHdr_bgn.Instance);}
	public static GfmlLxr NdeHdrEnd_lxr()		{return lxr_symbol_("lxr.gfml.node_hdr_0_end", ")", GfmlBldrCmd_ndeHdr_end.Instance);}
	public static GfmlLxr AtrSpr_lxr()			{return lxr_symbol_("lxr.gfml.atrSpr", ",", GfmlBldrCmd_atrSpr.Instance);}
	static GfmlLxr lxr_escape_(String key, String raw, String escape) {return GfmlLxr_.symbol_(key, raw, escape, GfmlBldrCmd_pendingTkns_add.Instance);}
	static GfmlLxr lxr_symbol_(String key, String raw, GfmlBldrCmd cmd) {return GfmlLxr_.symbol_(key, raw, raw, cmd);}
}
