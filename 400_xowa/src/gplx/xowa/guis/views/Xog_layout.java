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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.xowa.guis.views.*;
public class Xog_layout implements Gfo_invk {
	public Xog_layout() {
		go_bwd_btn.Owner_(this);
		go_fwd_btn.Owner_(this);
		url_box.Owner_(this);
		url_exec_btn.Owner_(this);
		find_close_btn.Owner_(this);
		search_box.Owner_(this);
		search_exec_btn.Owner_(this);
		allpages_box.Owner_(this);
		allpages_exec_btn.Owner_(this);
		html_box.Owner_(this);
		find_box.Owner_(this);
		prog_box.Owner_(this);
		note_box.Owner_(this);
		main_win.Owner_(this);
	}
	public Xog_layout_box Go_fwd_btn() {return go_fwd_btn;} private Xog_layout_box go_fwd_btn = new Xog_layout_box();
	public Xog_layout_box Go_bwd_btn() {return go_bwd_btn;} private Xog_layout_box go_bwd_btn = new Xog_layout_box();
	public Xog_layout_box Url_box() {return url_box;} private Xog_layout_box url_box = new Xog_layout_box();
	public Xog_layout_box Url_exec_btn() {return url_exec_btn;} private Xog_layout_box url_exec_btn = new Xog_layout_box();
	public Xog_layout_box Find_close_btn() {return find_close_btn;} private Xog_layout_box find_close_btn = new Xog_layout_box();
	public Xog_layout_box Search_box() {return search_box;} private Xog_layout_box search_box = new Xog_layout_box();
	public Xog_layout_box Search_exec_btn() {return search_exec_btn;} private Xog_layout_box search_exec_btn = new Xog_layout_box();
	public Xog_layout_box Allpages_box() {return allpages_box;} private Xog_layout_box allpages_box = new Xog_layout_box();
	public Xog_layout_box Allpages_exec_btn() {return allpages_exec_btn;} private Xog_layout_box allpages_exec_btn = new Xog_layout_box();
	public Xog_layout_box Html_box() {return html_box;} private Xog_layout_box html_box = new Xog_layout_box();
	public Xog_layout_box Find_box() {return find_box;} private Xog_layout_box find_box = new Xog_layout_box();
	public Xog_layout_box Find_fwd_btn() {return find_fwd_btn;} private Xog_layout_box find_fwd_btn = new Xog_layout_box();
	public Xog_layout_box Find_bwd_btn() {return find_bwd_btn;} private Xog_layout_box find_bwd_btn = new Xog_layout_box();
	public Xog_layout_box Prog_box() {return prog_box;} private Xog_layout_box prog_box = new Xog_layout_box();
	public Xog_layout_box Note_box() {return note_box;} private Xog_layout_box note_box = new Xog_layout_box();
	public Xog_layout_box Browser_win() {return main_win;} private Xog_layout_box main_win = new Xog_layout_box();
	public void Find_show() {
		Visible_(true, win.Find_box(), win.Find_bwd_btn(), win.Find_fwd_btn(), win.Find_close_btn());
		GfuiTextBox find_box = win.Find_box();
		find_box.Focus();
		int text_len = String_.Len(find_box.Text());
		if (text_len > 0) {	// if text exists, select it; GUI:Firefox
			find_box.SelBgn_set(0);
			find_box.SelLen_set(text_len);
		}
	}
	public void Find_close() {
		Visible_(false, win.Find_box(), win.Find_bwd_btn(), win.Find_fwd_btn(), win.Find_close_btn());
		win.Active_html_box().Focus();
	}
	private void Visible_(boolean v, GfuiElem... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
			ary[i].Visible_set(v);
	}
	public void Init(Xog_win_itm win) {
		this.win = win;
		go_bwd_btn.Adj_text(win.Go_bwd_btn());
		go_fwd_btn.Adj_text(win.Go_fwd_btn());
		url_box.Adj_text(win.Url_box());
		url_exec_btn.Adj_text(win.Url_exec_btn());
		find_close_btn.Adj_text(win.Find_close_btn());
		search_box.Adj_text(win.Search_box());
		search_exec_btn.Adj_text(win.Search_exec_btn());
		allpages_box.Adj_text(win.Allpages_box());
		allpages_exec_btn.Adj_text(win.Allpages_exec_btn());
		find_box.Adj_text(win.Find_box());
		find_fwd_btn.Adj_text(win.Find_fwd_btn());
		find_bwd_btn.Adj_text(win.Find_bwd_btn());
		prog_box.Adj_text(win.Prog_box());
		note_box.Adj_text(win.Info_box());
		win.Tab_mgr().Tab_mgr().TextMgr().Font_(win.Url_box().TextMgr().Font());
		Visible_(false, win.Find_box(), win.Find_bwd_btn(), win.Find_fwd_btn(), win.Find_close_btn());
	}	private Xog_win_itm win;
	public int Box_height_calc(Gfui_kit kit, GfuiElem url_box) {
		if (box_height > 0) return box_height;
		float font_height = kit.Calc_font_height(url_box, "I");
		box_height = (int)(font_height * 2);
		return box_height;
	}	private int box_height = 0;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_go_fwd_btn))				return go_fwd_btn;
		else if	(ctx.Match(k, Invk_go_bwd_btn))				return go_bwd_btn;
		else if	(ctx.Match(k, Invk_url_box))				return url_box;
		else if	(ctx.Match(k, Invk_url_exec_btn))			return url_exec_btn;
		else if	(ctx.Match(k, Invk_find_close_btn))			return find_close_btn;
		else if	(ctx.Match(k, Invk_search_box))				return search_box;
		else if	(ctx.Match(k, Invk_search_exec_btn))		return search_exec_btn;
		else if	(ctx.Match(k, "allpages_box"))				return allpages_box;
		else if	(ctx.Match(k, "allpages_exec_btn"))			return allpages_exec_btn;
		else if	(ctx.Match(k, Invk_html_box))				return html_box;
		else if	(ctx.Match(k, Invk_find_box))				return find_box;
		else if	(ctx.Match(k, Invk_find_fwd_btn))			return find_fwd_btn;
		else if	(ctx.Match(k, Invk_find_bwd_btn))			return find_bwd_btn;
		else if	(ctx.Match(k, Invk_prog_box))				return prog_box;
		else if	(ctx.Match(k, Invk_note_box))				return note_box;
		else if	(ctx.Match(k, Invk_main_win))				return main_win;
		else	return Gfo_invk_.Rv_unhandled;
	}
	static final String Invk_coord_mode_ = "coord_mode_", Invk_go_fwd_btn = "go_fwd_btn", Invk_go_bwd_btn = "go_bwd_btn", Invk_url_box = "url_box", Invk_search_box = "search_box", Invk_html_box = "html_box", Invk_find_box = "find_box", Invk_prog_box = "prog_box", Invk_note_box = "note_box"
		, Invk_main_win = "main_win", Invk_find_fwd_btn = "find_fwd_btn", Invk_find_bwd_btn = "find_bwd_btn", Invk_url_exec_btn = "url_exec_btn", Invk_search_exec_btn = "search_exec_btn", Invk_find_close_btn = "find_close_btn";
}
