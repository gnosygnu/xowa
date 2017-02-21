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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.miscs.*;
import gplx.core.btries.*; 
public class Xop_para_wkr implements Xop_ctx_wkr {
	private boolean para_enabled;
	private byte cur_mode;
	private int para_stack;
	private boolean in_block, block_is_bgn_xnde, block_is_end_xnde, in_blockquote, block_is_bgn_blockquote, block_is_end_blockquote;
	private int prv_nl_pos; private Xop_para_tkn prv_para; private int prv_ws_bgn;
	private final    Btrie_rv trv = new Btrie_rv();
	public boolean Enabled() {return enabled;} public Xop_para_wkr Enabled_(boolean v) {enabled = v; return this;} private boolean enabled = true;
	public Xop_para_wkr Enabled_y_() {enabled = true; return this;} public Xop_para_wkr Enabled_n_() {enabled = false; return this;}				
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {
		this.Clear();
		para_enabled = enabled && ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt;	// only enable for wikitext (not for template)
		if (para_enabled)
			Prv_para_new(ctx, root, -1, 0);	// create <para> at bos
	}
	private void Clear() {
		cur_mode = Mode_none;
		para_stack = Para_stack_none;
		in_block = block_is_bgn_xnde = block_is_end_xnde = false;
		in_blockquote = block_is_bgn_blockquote = block_is_end_blockquote = false;
		prv_nl_pos = -1;
		prv_para = null;
		prv_ws_bgn = 0;
	}
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {
		if (para_enabled) {
			Process_nl(ctx, root, src, src_len, src_len);
			this.Prv_para_end();												// close anything created by Process_nl()
		}
		this.Clear();
	}
	public void Process_block__bgn_y__end_n(Xop_xnde_tag tag)		{Process_block(tag, Bool_.Y, Bool_.N);}	// NOTE: disables para for rest of page; Process_block__bgn_n__end_y must be called; DATE:2014-04-18
	public void Process_block__bgn_n__end_y(Xop_xnde_tag tag)		{Process_block(tag, Bool_.N, Bool_.Y);}
	public void Process_block__xnde(Xop_xnde_tag tag, byte mode) {
		if		(mode == Xop_xnde_tag.Block_bgn)	 Process_block(tag, Bool_.Y, Bool_.N);
		else if (mode == Xop_xnde_tag.Block_end)	 Process_block(tag, Bool_.N, Bool_.Y);
	}
	public void Process_block_lnki_div() {	// bgn_lhs is pos of [[; end_lhs is pos of ]]
		if (prv_ws_bgn > 0)	 // if pre at start of line; ignore it b/c of div; EX: "\n\s[[File:A.png|thumb]]" should not produce thumb; also [[File:A.png|right]]; DATE:2014-02-17
			prv_ws_bgn = 0;
		this.Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__div);
	}
	private void Process_block(Xop_xnde_tag tag, boolean bgn, boolean end) {
		if (prv_ws_bgn > 0) {
			prv_para.Space_bgn_(prv_ws_bgn);
			prv_ws_bgn = 0;
		}
		block_is_bgn_xnde = bgn;
		block_is_end_xnde = end;
		switch (tag.Id()) {
			case Xop_xnde_tag_.Tid__blockquote:
				if (bgn) block_is_bgn_blockquote = true;
				if (end) block_is_end_blockquote = true;
				break;
		}
	}
	public void Process_block__bgn__nl_w_symbol(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int bgn_pos, int cur_pos, Xop_xnde_tag tag) {// handle \n== and \n* \n{|; note that nl is at rng of bgn_pos to bgn_pos + 1 (not cur_pos)
		if (!para_enabled) return;
		Process_nl(ctx, root, src, bgn_pos, bgn_pos + 1);
		Process_block__bgn_y__end_n(tag);
	}
	public void Process_nl(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int bgn_pos, int cur_pos) {// REF.MW:Parser.php|doBlockLevels
		Dd_clear(ctx);
		if (block_is_bgn_xnde || block_is_end_xnde) {
			para_stack = Para_stack_none;													// MW: $paragraphStack = false;
			Prv_para_end();																	// MW: $output .= $this->closeParagraph()
			if (block_is_bgn_blockquote && !block_is_end_blockquote)						// MW: if ( $preOpenMatch and !$preCloseMatch )
				in_blockquote = true;														// MW: $this->mInPre = true;
			else
				in_blockquote = false;														// XO: turn off blockquote else following para / nl won't work; w:Snappy_(software); DATE:2014-04-25
			in_block = !block_is_end_xnde;													// MW: $inBlockElem = !$closematch;
		}
		else if (!in_block && !in_blockquote) {												// MW: elseif ( !$inBlockElem && !$this->mInPre ) {
			boolean line_is_ws = Line_is_ws(src, bgn_pos);
			if (prv_ws_bgn > 0 && (cur_mode == Mode_pre || !line_is_ws)) {					// MW: if ( ' ' == substr( $t, 0, 1 ) and ( $this->mLastSection === 'pre' || trim( $t ) != '' ) ) {
				if (cur_mode != Mode_pre) {													// MW: if ( $this->mLastSection !== 'pre' ) {
					para_stack = Para_stack_none;											// MW: $paragraphStack = false;
					prv_para.Space_bgn_(prv_ws_bgn - 1);									// -1 to ignore 1st "\s" in "\n\s"; note that prv_ws_bgn only includes spaces, so BOS doesn't matter; DATE:2014-04-14
					Prv_para_end(); Prv_para_bgn(Xop_para_tkn.Tid_pre);  					// MW: $output .= $this->closeParagraph() . '<pre>';
					cur_mode = Mode_pre;													// MW: $this->mLastSection = 'pre';
				}
				else {																		// already in pre						
					if (line_is_ws) {														// line is entirely ws
						int next_char_pos = prv_nl_pos + 2;									// "\n\s".length
						if (	next_char_pos < src.length									// bounds check
							&&	src[next_char_pos] == Byte_ascii.Nl					// is "\n\s\n"; i.e.: "\n" only
							) {
							ctx.Subs_add(root, ctx.Tkn_mkr().Bry_raw(bgn_pos, bgn_pos, Byte_ascii.Nl_bry));	// add a "\n" tkn; note that adding a NewLine tkn doesn't work, b/c Xoh_html_wtr has code to remove consecutive \n; PAGE:en.w:Preferred_numbers DATE:2014-06-24
							prv_nl_pos = bgn_pos;
						}
					}
				}
				prv_ws_bgn = 0;																// MW: $t = substr( $t, 1 );
			}
			else {
				if (bgn_pos - prv_nl_pos == 1 || line_is_ws) {								// line is blank ("b" for blank)						MW: if ( trim( $t ) === '' ) {
					if (para_stack != Para_stack_none) {									// "b1"; stack has "<p>" or "</p><p>"; output "<br/>";	MW: if ( $paragraphStack ) {
						Para_stack_end(cur_pos); Add_br(ctx, root, bgn_pos);				//														MW: $output .= $paragraphStack . '<br />';
						para_stack = Para_stack_none;										//														MW: $paragraphStack = false;
						cur_mode = Mode_para;												//														MW: $this->mLastSection = 'p';
					}
					else {																	// stack is empty
						if (cur_mode != Mode_para) {										// "b2"; cur is '' or <pre>								MW: if ( $this->mLastSection !== 'p' ) {
							Prv_para_end();													//														MW: $output .= $this->closeParagraph();
							cur_mode = Mode_none;											//														MW: $this->mLastSection = '';
							para_stack = Para_stack_bgn;									// put <p> on stack										MW: $paragraphStack = '<p>';
						}
						else																// "b3"; cur is p
							para_stack = Para_stack_mid;									// put </p><p> on stack									MW: $paragraphStack = '</p><p>';
					}
				}
				else {																		// line has text ("t" for text); NOTE: tkn already added before \n, so must change prv_para; EX: "a\n" -> this code is called for "\n" but "a" already processed
					if (para_stack != Para_stack_none) {									// "t1"													MW: if ( $paragraphStack ) {
						Para_stack_end(cur_pos);											//														MW: $output .= $paragraphStack;
						para_stack = Para_stack_none;										//														MW: $paragraphStack = false;
						cur_mode = Mode_para;												//														MW: $this->mLastSection = 'p';
					}
					else if (cur_mode != Mode_para) {										// "t2"; cur is '' or <pre>								MW: elseif ( $this->mLastSection !== 'p' ) {
						Prv_para_end(); Prv_para_bgn(Xop_para_tkn.Tid_para);				//														MW: $output .= $this->closeParagraph() . '<p>';
						cur_mode = Mode_para;												//														MW: $this->mLastSection = 'p';
					}
					else {}																	// "t3"
				}
			}
		}
		if (in_blockquote && prv_ws_bgn > 0)	// handle blockquote separate; EX: <blockquote>\n\sa\n</blockquote>; note that "\s" needs to be added literally; MW doesn't have this logic specifically, since it assumes all characters go into $output, whereas XO, sets aside the "\s" in "\n\s" separately
			prv_para.Space_bgn_(prv_ws_bgn);
		prv_ws_bgn = 0;							// nl encountered and processed; always prv_ws_bgn set to 0, else ws from one line will carry over to next
		// in_blockquote = false;
		block_is_bgn_xnde = block_is_end_xnde = false;
		// if ( $preCloseMatch && $this->mInPre )
		//		$this->mInPre = false;
		// prv_ws_bgn = false; 
		Prv_para_new(ctx, root, bgn_pos, cur_pos);											// add a prv_para placeholder
		if (para_stack == Para_stack_none)													// "x1"													MW: if ( $paragraphStack === false ) {
			if (prv_para != null) prv_para.Nl_bgn_y_();										// add nl; note that "$t" has already been processed;	MW: $output .= $t . "\n";
	}
	public int Process_pre(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int txt_pos) {
		Dd_clear(ctx);
		Btrie_slim_mgr tblw_ws_trie = ctx.App().Utl_trie_tblw_ws();
		Object o = tblw_ws_trie.Match_at(trv, src, txt_pos, src_len);
		if (o != null) {	// tblw_ws found
			Xop_tblw_ws_itm ws_itm = (Xop_tblw_ws_itm)o;
			byte tblw_type = ws_itm.Tblw_type();
			switch (tblw_type) {
				case Xop_tblw_ws_itm.Type_nl:	// \n\s
					if (cur_mode == Mode_pre) {	// already in pre; just process "\n\s"
						ctx.Subs_add(root, tkn_mkr.NewLine(bgn_pos, bgn_pos, Xop_nl_tkn.Tid_char, 1));
						prv_nl_pos = bgn_pos;	// NOTE: must update prv_nl_pos; PAGE:en.w:Preferred_number DATE:2014-06-24
						return txt_pos;
					}
					break;
				case Xop_tblw_ws_itm.Type_xnde:
					int nxt_pos = trv.Pos();
					if (nxt_pos < src_len) {	// bounds check
						switch (src[nxt_pos]) {	// check that next char is "end" of xnde name; guard against false matches like "<trk" PAGE:de.v:Via_Jutlandica/Gpx DATE:2014-11-29
							case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab:		// whitespace
							case Byte_ascii.Slash: case Byte_ascii.Gt:									// end node
							case Byte_ascii.Quote: case Byte_ascii.Apos:								// quotes
								if (bgn_pos != Xop_parser_.Doc_bgn_bos)
									ctx.Para().Process_nl(ctx, root, src, bgn_pos, cur_pos);
								return ctx.Xnde().Make_tkn(ctx, tkn_mkr, root, src, src_len, txt_pos, txt_pos + 1);
						}
					}
					break;
				default: {
					int tblw_rv = ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, txt_pos + ws_itm.Hook_len(), false, tblw_type, Xop_tblw_wkr.Called_from_pre, -1, -1);
					if (tblw_rv != -1)	// \n\s| is valid tblw tkn and processed; otherwise process pre-code below; EX:w:Wikipedia:WikiProject_History/CategoryExample; DATE:2014-04-14
						return tblw_rv;
					break;
				}
			}
		}
		// NOTE: pre lxr emulates MW for "\n\s" by (1) calling Process nl for "\n"; (2) anticipating next line by setting prv_ws_bgn
		// EX: "\na\n b\n"; note that "\n " is cur
		if (bgn_pos != Xop_parser_.Doc_bgn_bos)								// if bos, then don't close 1st para
			Process_nl(ctx, root, src, bgn_pos, bgn_pos + 1);				// note that tkn is \n\s; so, bgn_pos -> bgn_pos + 1 is \n ...
		if (cur_mode == Mode_pre)											// in pre_mode
			ctx.Subs_add(root, tkn_mkr.Space(root, cur_pos, txt_pos));		// cur_pos to start after \s; do not capture "\s" in "\n\s"; (not sure why not before \s)
		prv_ws_bgn = txt_pos - cur_pos + 1;
		return txt_pos;
	}
	public void Process_lnki_category(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int pos, int src_len) {	// REF.MW:Parser.php|replaceInternalLinks2|Strip the whitespace Category links produce; 
		if (!para_enabled) return;
		int subs_len = root.Subs_len();
		for (int i = subs_len - 2; i > -1; i--) {	// -2: -1 b/c subs_len is invalid; -1 to skip current lnki
			Xop_tkn_itm sub_tkn = root.Subs_get(i);
			switch (sub_tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_para:		// nl found; note this means that BOL -> [[Category:]] is all ws;
					if (prv_ws_bgn > 0) {		// line begins with ws a
						if (sub_tkn.Src_bgn() != 0)	// do not ignore BOS para; needed b/c it is often <p>; needed for test;
							sub_tkn.Ignore_y_();	// ignore nl (pretty-printing only)
						prv_ws_bgn = 0;			// remove ws
						if (ctx.Stack_has(Xop_tkn_itm_.Tid_list)){	// HACK: if in list, set prv_nl_pos to EOL; only here for one test to pass
							int nl_at_eol = -1;
							for (int j = pos; j < src_len; j++) {	// check if rest of line is ws
								byte b = src[j];
								switch (b) {
									case Byte_ascii.Space: case Byte_ascii.Tab: break;	// ignore space / tab
									case Byte_ascii.Nl:
										nl_at_eol = j;
										j = src_len;
										break;
									default:	// something else besides ws; stop
										j = src_len;
										break;
								}
								if (nl_at_eol != -1)
									prv_nl_pos = nl_at_eol + 1;	// SEE:NOTE_2
							}
						}
					}
					return;
				default:						// exit if anything except para / nl in front of [[Category:]]
					i = -1;
					break;
			}
		}
//			if (para_found)	// BOS exit; just remove prv_ws_bgn
			prv_ws_bgn = 0;
	}
	private void Prv_para_new(Xop_ctx ctx, Xop_root_tkn root, int prv_nl_pos, int para_pos) {
		this.prv_nl_pos = prv_nl_pos;
		prv_para = ctx.Tkn_mkr().Para(para_pos);
		ctx.Subs_add(root, prv_para);
	}
	private void Prv_para_end() {	// MW: closeParagraph();
		// following switch is equivalent to:
		// MW: if ( $this->mLastSection != '' )
		// MW: 		$result = '</' . $this->mLastSection . ">\n";
		switch (cur_mode) {
			case Mode_none:	return;
			case Mode_pre:	prv_para.Para_end_(Xop_para_tkn.Tid_pre); break;
			case Mode_para: prv_para.Para_end_(Xop_para_tkn.Tid_para); break;
		}
		// in_pre = false;		// MW: $this->mInPre = false;
		cur_mode = Mode_none;	// MW: $this->mLastSection = '';
	}
	private void Prv_para_bgn(byte mode) {
		if (prv_para != null) prv_para.Para_bgn_(mode);
	}
	private void Para_stack_end(int cur_pos) {	// MW: $output .= $paragraphStack;
		switch (para_stack) {
			case Para_stack_none:	break;
			case Para_stack_bgn:	prv_para.Para_end_(Xop_para_tkn.Tid_none).Para_bgn_(Xop_para_tkn.Tid_para); break;	// '<p>'
			case Para_stack_mid:	prv_para.Para_end_(Xop_para_tkn.Tid_para).Para_bgn_(Xop_para_tkn.Tid_para); break;	// '</p><p>'
		}
	}
	private void Add_br(Xop_ctx ctx, Xop_root_tkn root, int bgn_pos) {
		ctx.Subs_add(root, ctx.Tkn_mkr().Xnde(bgn_pos, bgn_pos).Tag_(Xop_xnde_tag_.Tag__br)); 
	}
	private boolean Line_is_ws(byte[] src, int pos) {
		if (prv_nl_pos == -1) return false;
		boolean ws = true;
		for (int i = prv_nl_pos + 1; i < pos; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Tab:
				case Byte_ascii.Space:
					break;
				default:
					ws = false;
					i = pos;
					break;
			}
		}
		return ws;
	}
	private void Dd_clear(Xop_ctx ctx) {ctx.List().Dd_chk_(false);}
	private static final int 
	  Para_stack_none = 0	// false
	, Para_stack_bgn  = 1	// <p>
	, Para_stack_mid  = 2	// </p><p>
	;
	private static final byte
	  Mode_none = 0			// ''
	, Mode_para = 1			// p
	, Mode_pre  = 2			// pre
	;
}
/*
NOTE_1:
xowa uses \n as the leading character for multi-character hooks; EX: "\n*","\n{|","\n==",etc..
For this section of code, xowa treats \n separately from the rest of the hook for the purpose of emulating MW code.
EX: a\n==b==
MW:
- split into two lines: "a", "==b=="
- call process_nl on "a"
- call process_nl on "==b=="
XO:
- split into "tkns": "a", "\n==", "b", "=="
- add "a"
- add "\n=="
	- since there is a "\n", call process_nl, which will effectively call it for "a"
- note that page_end will effectively call process_nl on "==b=="

NOTE_2: Category needs to "trim" previous line
EX:
* a
* b
  [[Category:c]]
* d

MW does the following: (REF.MW:Parser.php|replaceInternalLinks2|Strip the whitespace Category links produce;)
- removes the \n after b (REF: $s = rtrim( $s . "\n" ); # bug 87)
- trims all space " " in front of [[ (NOTE: this makes it a non-pre line)
- plucks out the [[Category:c]]
- joins everything after ]] (starting with the \n) to the * b (REF: $s .= trim( $prefix . $trail, "\n" ) == '' ? '': $prefix . $trail;)
This effectively "blanks" out the entire line "\n  [[Category:c]]" -> ""

XOWA tries to emulate this by doing the following
- mark the para_tkn after \b as blank
- disable pre for the line
- keep the [[Category:c]], but *simulate* a blank line by moving the prv_nl_pos to after the ]]

NOTE_3: if (last_section_is_pre)
PURPOSE: if Category trims previous nl, but nl was part of pre, deactivate it
REASON: occurs b/c MW does separate passes for pre and Category while XO does one pass.
EX: "a\n [[Category:c]]"
- pre is activated by \n\s
- [[Category:c]] indicates that \n\s should be trimmed
  so, disable_pre, etc.

*/