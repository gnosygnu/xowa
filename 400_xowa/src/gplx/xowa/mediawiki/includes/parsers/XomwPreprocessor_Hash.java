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
import gplx.xowa.mediawiki.includes.parsers.preprocessors.*;
class XomwPreprocessor_Hash extends XomwPreprocessor { 	private XophpArray accum;
	public XomwPreprocessor_Hash(XomwParser parser) {this.parser = parser;}
	@Override public XomwParser Parser() {return parser;} private final    XomwParser parser;

	@Override public XomwPPFrame newFrame() {
		return null;
	}

	@Override public XomwPPFrame newCustomFrame(XomwPPFrame args) {
		return null;
	}

	@Override protected XomwPPDPart Factory__part() {return new XomwPPDPart_Hash("");}
	@Override protected XomwPPDStack Factory__stack() {return new XomwPPDStack(Xomw_prepro_accum__hash.Instance);}
	@Override protected XomwPPDStackElement Factory__stack_element(XomwPPDPart part_factory, String open, String close, int count, int start_pos, boolean lineStart) {return new XomwPPDStackElement_Hash(part_factory, open, close, count, start_pos, lineStart);}

	@Override protected Xomw_prepro_accum Accum__set(Xomw_prepro_accum accum_obj) {
		this.accum = ((Xomw_prepro_accum__hash)accum_obj).Ary();
		return accum_obj;
	}

	@Override public String preprocessToDbg(byte[] src, boolean for_inclusion) {
		XomwPPNode_Hash_Tree node = (XomwPPNode_Hash_Tree)this.preprocessToObj_base(src, for_inclusion);
		return node.toString();
	}
	@Override public XomwPPNode preprocessToObj(String text, int flags) {
		return (XomwPPNode)preprocessToObj_base(Bry_.new_u8(text), gplx.core.bits.Bitmask_.Has_int(flags, XomwParser.PTD_FOR_INCLUSION));
	}
	@Override protected void preprocessToObj_root() {} // NOTE: deliberately empty;

	@Override protected void preprocessToObj_ignore(byte[] src, int bgn, int end) {
		accum.Add(XophpArray.New("ignore", XophpArray.New(XophpString.substr(src, bgn, end - bgn))));
	}
	@Override protected void preprocessToObj_literal(byte[] src, int bgn, int end) {
		addLiteral(accum, XophpString.substr(src, bgn, end - bgn));
	}
	@Override protected void preprocessToObj_comment(byte[] src, int bgn, int end) {
		accum.Add(XophpArray.New("comment", XophpArray.New(XophpString.substr(src, bgn, end - bgn))));
	}
	@Override protected void preprocessToObj_removeLeadingWhitespaceFromEnd(int ws_len) {
		int endIndex = accum.Len() - 1;
		if (	ws_len > 0
			&&	endIndex >= 0) {
			Object itm_obj = accum.Get_at(endIndex);
			if (XophpTypeUtl.is_string(itm_obj)) {
				byte[] itm = Bry_.new_u8((String)itm_obj);
				if (XophpString.strspn_fwd__space_or_tab(itm, itm.length - ws_len, -1, itm.length) == ws_len) {
					accum.Set(endIndex, XophpString.substr(itm, 0, -ws_len));
				}
			}
		}
	}
	@Override protected byte[] preprocessToObj_close_init() {return null;}
	@Override protected byte[] preprocessToObj_close_make(byte[] src, int bgn, int end) {
		return Bry_.Mid(src, bgn, end);
	}
	@Override protected void preprocessToObj_ext(byte[] src, byte[] name, int atr_bgn, int atr_end, byte[] inner, byte[] close) {
		XophpArray children = XophpArray.New();
		children.Add(XophpArray.New("name", XophpArray.New(name)));
		children.Add(XophpArray.New("attr", XophpArray.New(String_.new_u8(Bry_.Mid(src, atr_bgn, atr_end)))));
		if (inner != null)
			children.Add(XophpArray.New("inner", XophpArray.New(inner)));
		if (close != null)
			children.Add(XophpArray.New("close", XophpArray.New(close)));
		accum.Add(XophpArray.New("ext", children));
	}
	@Override protected Xomw_prepro_accum preprocessToObj_heading_init(int count, int heading_index) {
		Xomw_prepro_accum__hash rv = new Xomw_prepro_accum__hash(XophpArray.New());
		rv.Ary().Add
		(   XophpArray.New
			(   "h",
				XophpArrayUtl.array_merge
				(	XophpArray.New
					( XophpArray.New("@level", XophpArray.New(count))
					, XophpArray.New("@i"    , XophpArray.New(heading_index))
					)
					, accum
				)
			)
		);
		return rv;
	}
	@Override protected void preprocessToObj_heading_end(Xomw_prepro_accum element) {
		XophpArrayUtl.array_splice(accum, accum.Len(), 0, ((Xomw_prepro_accum__hash)element).Ary());
	}

	@Override protected Xomw_prepro_accum preprocessToObj_text(XomwPPDStackElement piece, byte[] rule_end, int matching_count) {
		XophpArray array = (XophpArray)((XomwPPDStackElement)piece).breakSyntax(matching_count);
		addLiteral(array, XophpString.str_repeat(String_.new_u8(rule_end), matching_count));
		return new Xomw_prepro_accum__hash(array);
	}
	@Override protected Xomw_prepro_accum preprocessToObj_xml(XomwPPDStackElement piece, byte[] name_bry, int max_count, int matching_count) {
		XophpArray parts = piece.parts;
		Xomw_prepro_accum title = ((XomwPPDPart_Hash)parts.Get_at(0)).Accum();
		parts.Del_at(0);

		XophpArray children = XophpArray.New();

		// The invocation is at the start of the line if lineStart is set in
		// the stack, and all opening brackets are used up.
		if (max_count == matching_count && piece.lineStart) {	// RELIC:!empty( $piece->lineStart )
			children.Add(XophpArray.New("@lineStart", XophpArray.New(1)));
		}
		XophpArray titleNode = XophpArray.New("title", title);
		children.Add(titleNode);

		int argIndex = 1;
		int parts_len = parts.Len();
		for (int j = 0; j < parts_len; j++) {
			XomwPPDPart_Hash part = (XomwPPDPart_Hash)parts.Get_at(j);
			XophpArray part_out = (XophpArray)part.Accum_hash().Ary();
			if (part.eqpos != -1) {
				Object equalsNode = part_out.Get_at(part.eqpos);
				XophpArray nameNode  = XophpArray.New("name" , XophpArrayUtl.array_slice(part_out, 0, part.eqpos));
				XophpArray valueNode = XophpArray.New("value", XophpArrayUtl.array_slice(part_out, part.eqpos + 1));
				XophpArray partNode  = XophpArray.New("part" , XophpArray.New(nameNode, equalsNode, valueNode));
				children.Add(partNode);
			}
			else {
				XophpArray nameNode  = XophpArray.New("name" , XophpArray.New(XophpArray.New("@index", XophpArray.New(argIndex++))));
				XophpArray valueNode = XophpArray.New("value", part_out);
				XophpArray partNode  = XophpArray.New("part" , XophpArray.New(nameNode, valueNode));
				children.Add(partNode);
			}
		}
		XophpArray element = XophpArray.New(XophpArray.New(String_.new_u8(name_bry), children));
		return new Xomw_prepro_accum__hash(element);
	}
	@Override protected void preprocessToObj_add_element(Xomw_prepro_accum element) {
		XophpArrayUtl.array_splice(accum, accum.Len(), 0, ((Xomw_prepro_accum__hash)element).Ary());
	}
	@Override protected void preprocessToObj_equals(XomwPPDStack stack) {
		accum.Add(XophpArray.New("equals", XophpArray.New("=")));
		stack.getCurrentPart().eqpos = accum.Len() - 1;
	}
	@Override protected Object preprocessToObj_term(XomwPPDStack stack) {
		Xomw_prepro_accum__hash stack_accum = (Xomw_prepro_accum__hash)stack.getAccum();
		XophpArray stack_ary = stack_accum.Ary();
		int len = stack_ary.Len();
		for (int i = 0; i < len; i++) {
//				XomwPPDPart_Hash piece = (XomwPPDPart_Hash)(stack_ary.Get_at(i).Val());
//				XophpArrayUtl.array_splice(stack_ary, stack_ary.Len(), 0, piece.breakSyntax());
		}
		//	for ( $stack->stack as $piece ) {
		//		array_splice( $stack->rootAccum, count( $stack->rootAccum ), 0, $piece->breakSyntax() );
		//	}

		//	// Enable top-level headings
		//	for ( $stack->rootAccum as &$node ) {
		//		if ( is_array( $node ) && $node[PPNode_Hash_Tree::NAME] === 'possible-h' ) {
		//			$node[PPNode_Hash_Tree::NAME] = 'h';
		//		}
		//	}


		XophpArray rootStore = XophpArray.New(XophpArray.New("root", stack.Get_root_accum()));
		XomwPPNode_Hash_Tree rootNode = new XomwPPNode_Hash_Tree(rootStore, 0);

		//	// Cache
		//	$tree = json_encode( $rootStore, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE );
		//	if ( $tree !== false ) {
		//		$this->cacheSetTree( $text, $flags, $tree );
		//	}
		//
		return rootNode;
	}

	private static void addLiteral(XophpArray accum, byte[] text) {addLiteral(accum, String_.new_u8(text));}
	private static void addLiteral(XophpArray accum, String text) {
		int n = accum.Len();
		Object itm = accum.Get_at(n - 1);
		if (n > 0 && XophpTypeUtl.is_string(itm)) {
			accum.Set(n - 1, ((String)itm) + text);
		}
		else {
			accum.Add(text);
		}
	}

	@Override public XomwPreprocessor Make_new(XomwParser parser) {return new XomwPreprocessor_Hash(parser);}
	public static final    XomwPreprocessor Instance = new XomwPreprocessor_Hash(null);
}
