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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
import gplx.xowa.mediawiki.includes.parsers.preprocessors.*;
// THREAD.UNSAFE: caching for repeated calls
class XomwPreprocessor_DOM extends XomwPreprocessor { 	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xomw_prepro_accum__dom accum_dom = new Xomw_prepro_accum__dom("");

	@Override protected XomwPPDStack Factory__stack() {return new XomwPPDStack(Xomw_prepro_accum__dom.Instance);}
	@Override protected XomwPPDPart Factory__part() {return new XomwPPDPart_DOM("");}

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

	@Override public byte[] preprocessToDbg(byte[] src, boolean for_inclusion) {return (byte[])this.preprocessToObj_base(src, for_inclusion);}
	@Override public XomwPPNode preprocessToObj(String text, int flags) {
		return (XomwPPNode)preprocessToObj_base(Bry_.new_u8(text), gplx.core.bits.Bitmask_.Has_int(flags, XomwParser.PTD_FOR_INCLUSION));
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
			&&	XophpString.strspn_fwd__space_or_tab(accum_dom.Bfr_bry(), accum_dom_len - ws_len, -1, accum_dom_len) == ws_len) {
			accum_dom.Del_at_end(ws_len);
		}
	}
	@Override protected byte[] preprocessToObj_close_init() {return Bry_.Empty;}
	@Override protected byte[] preprocessToObj_close_make(byte[] src, int bgn, int end) {
		return tmp_bfr.Add_str_a7("<close>").Add_bry_escape_html(src, bgn, end).Add_str_a7("</close>").To_bry_and_clear();
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
		byte[] rv = tmp_bfr.Add_str_a7("<h level=\"").Add_int_variable(count).Add_str_a7("\" i=\"").Add_int_variable(heading_index).Add_str_a7("\">").Add_str_u8(accum_dom.To_str()).Add_str_a7("</h>").To_bry_and_clear();
		return new Xomw_prepro_accum__dom(String_.new_u8(rv));
	}
	@Override protected void preprocessToObj_heading_end(Xomw_prepro_accum element) {
		accum_dom.Add_bry(((Xomw_prepro_accum__dom)element).To_bry());
	}
	@Override protected Xomw_prepro_accum preprocessToObj_text(Xomw_prepro_accum element_obj, Xomw_prepro_piece piece, byte[] rule_end, int matching_count) {
		Xomw_prepro_accum__dom element = (Xomw_prepro_accum__dom)element_obj;
		tmp_bfr.Add(piece.Break_syntax(tmp_bfr, matching_count));
		if (element != null)
			tmp_bfr.Add(element.To_bry());
		tmp_bfr.Add(Bry_.Repeat_bry(rule_end, matching_count));
		byte[] rv = tmp_bfr.To_bry_and_clear();
		return new Xomw_prepro_accum__dom(String_.new_u8(rv));
	}
	@Override protected Xomw_prepro_accum preprocessToObj_xml(Xomw_prepro_piece piece, byte[] name_bry, int max_count, int matching_count) {
		// Note: $parts is already XML, does not need to be encoded further
		List_adp parts = piece.parts;
		byte[] title = ((XomwPPDPart_DOM)parts.Get_at(0)).To_bry();
		parts.Del_at(0);

		// The invocation is at the start of the line if lineStart is set in
		// the stack, and all opening brackets are used up.
		byte[] attr = null;
		if (max_count == matching_count && piece.line_start) {	// RELIC:!empty( $piece->lineStart )
			attr = Bry_.new_a7(" lineStart=\"1\"");
		}
		else {
			attr = Bry_.Empty;
		}

		tmp_bfr.Add_str_a7("<").Add(name_bry).Add(attr).Add_str_a7(">");
		tmp_bfr.Add_str_a7("<title>").Add(title).Add_str_a7("</title>");

		int arg_idx = 1;
		int parts_len = parts.Len();
		for (int j = 0; j < parts_len; j++) {
			XomwPPDPart_DOM part = (XomwPPDPart_DOM)parts.Get_at(j);
			if (part.eqpos != -1) {
				byte[] part_bfr_bry = part.To_bry();
				tmp_bfr.Add_str_a7("<part><name>").Add_mid(part_bfr_bry, 0, part.eqpos);
				tmp_bfr.Add_str_a7("</name>=<value>").Add_mid(part_bfr_bry, part.eqpos + 1, part.Len());
				tmp_bfr.Add_str_a7("</value></part>");
			}
			else {
				tmp_bfr.Add_str_a7("<part><name index=\"").Add_int_variable(arg_idx).Add_str_a7("\" /><value>").Add(part.To_bry()).Add_str_a7("</value></part>");
				arg_idx++;
			}
		}
		byte[] element = tmp_bfr.Add_str_a7("</").Add(name_bry).Add_str_a7(">").To_bry_and_clear();
		return new Xomw_prepro_accum__dom(String_.new_u8(element));
	}
	@Override protected void preprocessToObj_add_element(Xomw_prepro_accum element) {
		accum_dom.Add_bry(((Xomw_prepro_accum__dom)element).To_bry());
	}
	@Override protected void preprocessToObj_equals(XomwPPDStack stack) {
		stack.Get_current_part().eqpos = accum_dom.Len();
		accum_dom.Add_bry(Byte_ascii.Eq_bry);
	}
	@Override protected Object preprocessToObj_term(XomwPPDStack stack) {
		Bry_bfr root_accum = Bry_bfr_.New().Add_str_u8(((Xomw_prepro_accum__dom)stack.Get_root_accum()).To_str());
		int stack_len = stack.stack.Len();
		for (int j = 0; j < stack_len; j++) {
			Xomw_prepro_piece piece = (Xomw_prepro_piece)stack.stack.Get_at(j);
			root_accum.Add(piece.Break_syntax(tmp_bfr, -1));
		}
		root_accum.Add_str_a7("</root>");
		return root_accum.To_bry_and_clear();
	}

	@Override public XomwPreprocessor Make_new(XomwParser parser) {return new XomwPreprocessor_DOM();}
	public static final    XomwPreprocessor Instance = new XomwPreprocessor_DOM();
}
