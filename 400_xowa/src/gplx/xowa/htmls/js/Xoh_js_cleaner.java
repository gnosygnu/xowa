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
package gplx.xowa.htmls.js;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.core.btries.*;
public class Xoh_js_cleaner {
	private Xoae_app app; private boolean ctor = true;
	public Xoh_js_cleaner(Xoae_app app) {this.app = app;}
	public void Clean_bfr(Xowe_wiki wiki, Xoa_ttl ttl, BryWtr bfr, int bgn) {
		int end = bfr.Len();
		byte[] cleaned = this.Clean(wiki, bfr.Bry(), bgn, end);
		if (cleaned != null) {
			bfr.DelBy(end - bgn);
			bfr.Add(cleaned);
			app.Usr_dlg().Log_many("", "", "javascript detected: wiki=~{0} ~{1}", wiki.Domain_str(), StringUtl.NewU8(ttl.Full_txt()));
		}
	}
	public byte[] Clean(Xowe_wiki wiki, byte[] src, int bgn, int end) {
		if (ctor) Ctor();
		BryWtr bfr = null;
		boolean dirty = false;
		try {
			bfr = wiki.Utl__bfr_mkr().GetM001();
			int pos = bgn;
			while (pos < end) {
				byte b = src[pos];
				Object o = trie.Match_bgn_w_byte(b, src, pos, end);
				if (o == null) {
					if (dirty)
						bfr.AddByte(b);
					++pos;
				}
				else {					
					byte[] frag = (byte[])o;
					int frag_len = frag.length;
					if (frag[0] == AsciiByte.Lt) {	// jscript node; EX: <script
						if (!dirty) {bfr.AddMid(src, bgn, pos); dirty = true;}
						bfr.Add(HtmlEntityCodes.LtBry);
						bfr.AddMid(frag, 1, frag.length);
						pos += frag_len; 
					}
					else {	// jscript attribue; EX: onmouseover
						int atr_pos = Get_pos_eq(src, pos, end, frag_len);
						if (atr_pos == -1)	// false match; EX: "onSelectNotJs=3"; "regionSelect=2"
							pos += frag_len;
						else {
							if (!dirty) {bfr.AddMid(src, bgn, pos); dirty = true;}
							bfr.Add(frag);
							bfr.Add(HtmlEntityCodes.EqBry);
							pos = atr_pos;
						}
					}
				}
			}
		}	finally {if (bfr != null) bfr.MkrRls();}
		return dirty ? bfr.ToBryAndClear() : null;
	}
	private int Get_pos_eq(byte[] src, int pos, int end, int frag_len) {
		if (	pos > 0								// bounds check
			&& !AsciiByte.IsWs(src[pos - 1])		// previous byte is not whitespace; frag is part of word; EX: "regionSelect=2"; DATE:2014-02-06
			)
			return -1;
		boolean next_byte_is_equal = false; boolean break_loop = false;
		int atr_pos = pos + frag_len;
		for (; atr_pos < end; atr_pos++) {
			byte atr_b = src[atr_pos];
			switch (atr_b) {
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space: break;
				case AsciiByte.Eq:
					next_byte_is_equal = true;
					++atr_pos;
					break_loop = true;
					break;
				default:
					break_loop = true;
					break;
			}
			if (break_loop) break;
		}
		return next_byte_is_equal ? atr_pos : -1;
	}
	private void Ctor() {
		Reg_itm("<script");
		Reg_itm("<iframe");
		Reg_itm("<style");
		Reg_itm("<link");
		Reg_itm("<meta");
		Reg_itm("<Object");
		Reg_itm("<frame");
		Reg_itm("<embed");
		Reg_itm("<body");
		Reg_itm("FSCommand");
		Reg_itm("onAbort");
		Reg_itm("onActivate");
		Reg_itm("onAfterPrint");
		Reg_itm("onAfterUpdate");
		Reg_itm("onBeforeActivate");
		Reg_itm("onBeforeCopy");
		Reg_itm("onBeforeCut");
		Reg_itm("onBeforeDeactivate");
		Reg_itm("onBeforeEditFocus");
		Reg_itm("onBeforePaste");
		Reg_itm("onBeforePrint");
		Reg_itm("onBeforeUnload");
		Reg_itm("onBegin");
		Reg_itm("onBlur");
		Reg_itm("onBounce");
		Reg_itm("onCellChange");
		Reg_itm("onChange");
		Reg_itm("onClick");
		Reg_itm("onContextMenu");
		Reg_itm("onControlSelect");
		Reg_itm("onCopy");
		Reg_itm("onCut");
		Reg_itm("onDataAvailable");
		Reg_itm("onDataSetChanged");
		Reg_itm("onDataSetComplete");
		Reg_itm("onDblClick");
		Reg_itm("onDeactivate");
		Reg_itm("onDrag");
		Reg_itm("onDragEnd");
		Reg_itm("onDragLeave");
		Reg_itm("onDragEnter");
		Reg_itm("onDragOver");
		Reg_itm("onDragDrop");
		Reg_itm("onDrop");
		Reg_itm("onEnd");
		Reg_itm("onError");
		Reg_itm("onErrorUpdate");
		Reg_itm("onFilterChange");
		Reg_itm("onFinish");
		Reg_itm("onFocus");
		Reg_itm("onFocusIn");
		Reg_itm("onFocusOut");
		Reg_itm("onHelp");
		Reg_itm("onKeyDown");
		Reg_itm("onKeyPress");
		Reg_itm("onKeyUp");
		Reg_itm("onLayoutComplete");
		Reg_itm("onLoad");
		Reg_itm("onLoseCapture");
		Reg_itm("onMediaComplete");
		Reg_itm("onMediaError");
		Reg_itm("onMouseDown");
		Reg_itm("onMouseEnter");
		Reg_itm("onMouseLeave");
		Reg_itm("onMouseMove");
		Reg_itm("onMouseOut");
		Reg_itm("onMouseOver");
		Reg_itm("onMouseUp");
		Reg_itm("onMouseWheel");
		Reg_itm("onMove");
		Reg_itm("onMoveEnd");
		Reg_itm("onMoveStart");
		Reg_itm("onOutOfSync");
		Reg_itm("onPaste");
		Reg_itm("onPause");
		Reg_itm("onProgress");
		Reg_itm("onPropertyChange");
		Reg_itm("onReadyStateChange");
		Reg_itm("onRepeat");
		Reg_itm("onReset");
		Reg_itm("onResize");
		Reg_itm("onResizeEnd");
		Reg_itm("onResizeStart");
		Reg_itm("onResume");
		Reg_itm("onReverse");
		Reg_itm("onRowsEnter");
		Reg_itm("onRowExit");
		Reg_itm("onRowDelete");
		Reg_itm("onRowInserted");
		Reg_itm("onScroll");
		Reg_itm("onSeek");
		Reg_itm("onSelect");
		Reg_itm("onSelectionChange");
		Reg_itm("onSelectStart");
		Reg_itm("onStart");
		Reg_itm("onStop");
		Reg_itm("onSyncRestored");
		Reg_itm("onSubmit");
		Reg_itm("onTimeError");
		Reg_itm("onTrackChange");
		Reg_itm("onUnload");
		Reg_itm("onURLFlip");
		Reg_itm("seekSegmentTime");
		ctor = false;
	}
	private void Reg_itm(String s) {trie.Add_bry(BryUtl.NewA7(s));} Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:javascript event name
}
