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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.mediawiki.*;
import gplx.xowa.mediawiki.includes.parsers.preprocessors.*;
// THREAD.UNSAFE: caching for repeated calls
class XomwPreprocessor_DOM extends XomwPreprocessor { 	private final BryWtr tmp_bfr = BryWtr.New();
	private Xomw_prepro_accum__dom accum_dom = new Xomw_prepro_accum__dom("");
	public XomwPreprocessor_DOM(XomwParser parser) {this.parser = parser;}
	@Override public XomwParser Parser() {return parser;} private final XomwParser parser;

	@Override protected XomwPPDPart Factory__part() {return new XomwPPDPart_DOM("");}
	@Override protected XomwPPDStack Factory__stack() {return new XomwPPDStack(Xomw_prepro_accum__dom.Instance);}
	@Override protected XomwPPDStackElement Factory__stack_element(XomwPPDPart part_factory, String open, String close, int count, int start_pos, boolean lineStart) {return new XomwPPDStackElement(part_factory, open, close, count, start_pos, lineStart);}

	@Override public XomwPPFrame newFrame() {
		return null;
	}
	@Override public XomwPPFrame newCustomFrame(XomwPPFrame args) {
		return null;
	}
	@Override protected Xomw_prepro_accum Accum__set(Xomw_prepro_accum accum) {
		this.accum_dom = (Xomw_prepro_accum__dom)accum;
		return accum;
	}

	@Override public String preprocessToDbg(byte[] src, boolean for_inclusion) {return StringUtl.NewU8((byte[])this.preprocessToObj_base(src, for_inclusion));}
	@Override public XomwPPNode preprocessToObj(String text, int flags) {
		return (XomwPPNode)preprocessToObj_base(BryUtl.NewU8(text), gplx.core.bits.Bitmask_.Has_int(flags, XomwParser.PTD_FOR_INCLUSION));
	}
	@Override protected void preprocessToObj_root() {
		accum_dom.Clear();
		accum_dom.Add_str_literal("<root>");
	}
	@Override protected void preprocessToObj_ignore(byte[] src, int bgn, int end) {
		accum_dom.Add_str_literal("<ignore>").Add_str_escaped(src, bgn, end).Add_str_literal("</ignore>");
	}
	@Override protected void preprocessToObj_comment(byte[] src, int bgn, int end) {
		accum_dom.Add_str_literal("<comment>").Add_str_escaped(src, bgn, end).Add_str_literal("</comment>");
	}
	@Override protected void preprocessToObj_literal(byte[] src, int bgn, int end) {
		accum_dom.Add_str_escaped(src, bgn, end);
	}
	@Override protected void preprocessToObj_removeLeadingWhitespaceFromEnd(int ws_len) {
		int accum_dom_len = accum_dom.Len();
		if (	ws_len > 0
			&&	XophpString_.strspn_fwd__space_or_tab(accum_dom.Bfr_bry(), accum_dom_len - ws_len, -1, accum_dom_len) == ws_len) {
			accum_dom.Del_at_end(ws_len);
		}
	}
	@Override protected byte[] preprocessToObj_close_init() {return BryUtl.Empty;}
	@Override protected byte[] preprocessToObj_close_make(byte[] src, int bgn, int end) {
		return tmp_bfr.AddStrA7("<close>").AddBryEscapeHtml(src, bgn, end).AddStrA7("</close>").ToBryAndClear();
	}
	@Override protected void preprocessToObj_ext(byte[] src, byte[] name, int atr_bgn, int atr_end, byte[] inner, byte[] close) {
		accum_dom.Add_str_literal("<ext>");
		// PORTED:
		// if ( $attrEnd <= $attrStart ) {
		//	 $attr = '';
		// } else {
		//   $attr = substr( $text, $attrStart, $attrEnd - $attrStart );
		// }
		accum_dom.Add_str_literal("<name>").Add_bry(name).Add_str_literal("</name>");
		// Note that the attr element contains the whitespace between name and attribute,
		// this is necessary for precise reconstruction during pre-save transform.
		accum_dom.Add_str_literal("<attr>");
		if (atr_end > atr_bgn)
			accum_dom.Add_str_escaped(src, atr_bgn, atr_end);
		accum_dom.Add_str_literal("</attr>");
		if (inner != null) {
			accum_dom.Add_str_literal("<inner>").Add_str_escaped(inner, 0, inner.length).Add_str_literal("</inner>");
		}
		accum_dom.Add_bry(close).Add_str_literal("</ext>");
	}
	@Override protected Xomw_prepro_accum preprocessToObj_heading_init(int count, int heading_index) {
		byte[] rv = tmp_bfr.AddStrA7("<h level=\"").AddIntVariable(count).AddStrA7("\" i=\"").AddIntVariable(heading_index).AddStrA7("\">").AddStrU8(accum_dom.To_str()).AddStrA7("</h>").ToBryAndClear();
		return new Xomw_prepro_accum__dom(StringUtl.NewU8(rv));
	}
	@Override protected void preprocessToObj_heading_end(Xomw_prepro_accum element) {
		accum_dom.Add_bry(((Xomw_prepro_accum__dom)element).To_bry());
	}
	@Override protected Xomw_prepro_accum preprocessToObj_text(XomwPPDStackElement piece, byte[] rule_end, int matching_count) {
		tmp_bfr.AddStrU8((String)piece.breakSyntax(matching_count));
		tmp_bfr.Add(BryUtl.RepeatBry(rule_end, matching_count));
		byte[] rv = tmp_bfr.ToBryAndClear();
		return new Xomw_prepro_accum__dom(StringUtl.NewU8(rv));
	}
	@Override protected Xomw_prepro_accum preprocessToObj_xml(XomwPPDStackElement piece, byte[] name_bry, int max_count, int matching_count) {
		// Note: $parts is already XML, does not need to be encoded further
		XophpArray parts = piece.parts;
		byte[] title = ((XomwPPDPart_DOM)parts.Get_at(0)).To_bry();
		parts.Del_at(0);

		// The invocation is at the start of the line if lineStart is set in
		// the stack, and all opening brackets are used up.
		byte[] attr = null;
		if (max_count == matching_count && piece.lineStart) {	// RELIC:!empty( $piece->lineStart )
			attr = BryUtl.NewA7(" lineStart=\"1\"");
		}
		else {
			attr = BryUtl.Empty;
		}

		tmp_bfr.AddStrA7("<").Add(name_bry).Add(attr).AddStrA7(">");
		tmp_bfr.AddStrA7("<title>").Add(title).AddStrA7("</title>");

		int arg_idx = 1;
		int parts_len = parts.Len();
		for (int j = 0; j < parts_len; j++) {
			XomwPPDPart_DOM part = (XomwPPDPart_DOM)parts.Get_at(j);
			if (part.eqpos != 0) {
				byte[] part_bfr_bry = part.To_bry();
				tmp_bfr.AddStrA7("<part><name>").AddMid(part_bfr_bry, 0, part.eqpos);
				tmp_bfr.AddStrA7("</name>=<value>").AddMid(part_bfr_bry, part.eqpos + 1, part.Len());
				tmp_bfr.AddStrA7("</value></part>");
			}
			else {
				tmp_bfr.AddStrA7("<part><name index=\"").AddIntVariable(arg_idx).AddStrA7("\" /><value>").Add(part.To_bry()).AddStrA7("</value></part>");
				arg_idx++;
			}
		}
		byte[] element = tmp_bfr.AddStrA7("</").Add(name_bry).AddStrA7(">").ToBryAndClear();
		return new Xomw_prepro_accum__dom(StringUtl.NewU8(element));
	}
	@Override protected void preprocessToObj_add_element(Xomw_prepro_accum element) {
		accum_dom.Add_bry(((Xomw_prepro_accum__dom)element).To_bry());
	}
	@Override protected void preprocessToObj_equals(XomwPPDStack stack) {
		stack.getCurrentPart().eqpos = accum_dom.Len();
		accum_dom.Add_bry(AsciiByte.EqBry);
	}
	@Override protected Object preprocessToObj_term(XomwPPDStack stack) {
		BryWtr root_accum = BryWtr.New().AddStrU8(((Xomw_prepro_accum__dom)stack.Get_root_accum()).To_str());
		int stack_len = stack.stack.Len();
		for (int j = 0; j < stack_len; j++) {
			// XomwPPDStackElement_Hash piece = (XomwPPDStackElement_Hash)stack.stack.Get_at(j);
			// root_accum.Add((XophpArray)piece.breakSyntax(tmp_bfr));
			// root_accum.Add((XophpArray)piece.breakSyntax(tmp_bfr, -1));
		}
		root_accum.AddStrA7("</root>");
		return root_accum.ToBryAndClear();
	}

	@Override public XomwPreprocessor Make_new(XomwParser parser) {return new XomwPreprocessor_DOM(parser);}
	public static final XomwPreprocessor Instance = new XomwPreprocessor_DOM(null);
}
