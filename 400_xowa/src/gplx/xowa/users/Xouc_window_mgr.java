/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.gfui.*; import gplx.xowa.gui.views.*;
public class Xouc_window_mgr implements GfoInvkAble {
	public Xouc_window_mgr(Xoue_user user) {this.user = user;} private Xoue_user user;
	public byte Mode_tid() {return mode_tid;} private byte mode_tid = Mode_tid_previous;
	public String Mode_str() {return mode_str;} private String mode_str = "previous";
	public Rect_ref Rect() {return rect;} Rect_ref rect = new Rect_ref(0, 0, 800, 640);
	public boolean Safe_mode() {return safe_mode;} private boolean safe_mode = true;
	public Rect_ref Previous_adj() {return previous_adj;} Rect_ref previous_adj = new Rect_ref(0, 0, 0, 0);
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_mode))					return mode_str;
		else if	(ctx.Match(k, Invk_mode_))					{mode_str = m.ReadStr("v"); mode_tid = Xto_mode_tid(mode_str);}
		else if	(ctx.Match(k, Invk_mode_list))				return Options_mode_list;
		else if	(ctx.Match(k, Invk_rect))					return rect;
		else if	(ctx.Match(k, Invk_rect_))					rect = Rect_ref.parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_safe_mode))				return Yn.Xto_str(safe_mode);
		else if	(ctx.Match(k, Invk_safe_mode_))				safe_mode = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_previous_adj))			return previous_adj;
		else if	(ctx.Match(k, Invk_previous_adj_))			previous_adj = Rect_ref.parse(m.ReadStr("v"));
		return this;
	}
	public static final String Invk_mode = "mode", Invk_mode_ = "mode_", Invk_mode_list = "mode_list"
		, Invk_rect = "rect", Invk_rect_ = "rect_", Invk_safe_mode = "safe_mode", Invk_safe_mode_ = "safe_mode_", Invk_previous_adj = "previous_adj", Invk_previous_adj_ = "previous_adj_";
	private static KeyVal[] Options_mode_list = KeyVal_.Ary(KeyVal_.new_("previous"), KeyVal_.new_("maximized"), KeyVal_.new_("absolute"), KeyVal_.new_("relative"), KeyVal_.new_("default")); 
	public void Init_window(GfuiWin win) {
		boolean init_is_maximized = false;
		switch (mode_tid) {
			case Mode_tid_previous:
				Xous_window_mgr session_window = user.Session_mgr().Window_mgr();
				if (session_window.Maximized()) {
					win.Maximized_(true);
					init_is_maximized = true;
				}
				else
					win.Rect_set(session_window.Rect().XtoRectAdp_add(previous_adj));
				break;
			case Mode_tid_absolute:		win.Rect_set(rect.XtoRectAdp()); break;
			case Mode_tid_maximized:	win.Maximized_(true); init_is_maximized = true; break;
			case Mode_tid_default:		break;	// noop
			case Mode_tid_relative:
				SizeAdp screen_maximized = Screen_maximized_calc();
				Rect_ref win_rect = new Rect_ref(0, 0, screen_maximized.Width(), screen_maximized.Height());
				win.Rect_set(win_rect.XtoRectAdp_add(rect));
				break;
		}
		if (safe_mode && !init_is_maximized) {
			RectAdp rect = win.Rect();
			boolean force = false; int x = rect.X(), y = rect.Y(), w = rect.Width(), h = rect.Height();
			SizeAdp screen_size = Screen_maximized_calc();
			int max_w = screen_size.Width(), max_h = screen_size.Height();
			if (x < -Safe_mode_buffer || x		> max_w + Safe_mode_buffer)		{force = true; x = 0;}
			if (y < -Safe_mode_buffer || y		> max_h + Safe_mode_buffer)		{force = true; y = 0;}
			if (w <  Safe_mode_buffer || x + w	> max_w + Safe_mode_buffer)		{force = true; w = max_w - x;}
			if (h <  Safe_mode_buffer || y + h	> max_h + Safe_mode_buffer)		{force = true; h = max_h - y;}
			if (force)
				win.Rect_set(RectAdp_.new_(x, y, w, h));
		}
	}	private static final int Safe_mode_buffer = 20; // allow minor negative positioning (x=-5), off-screen positioning (w=1605)
	public static SizeAdp Screen_maximized_calc() {
		Op_sys os = Op_sys.Cur();
		SizeAdp screen = ScreenAdp_.screen_(0).Size();
		int w = screen.Width();
		int h = screen.Height() - 30;	// -20=application menu bar; -10 for start bar padding
		switch (os.Tid()) {
			case Op_sys.Tid_wnt:
				switch (os.Sub_tid()) {
					case Op_sys.Sub_tid_win_xp:	h += -4; break;	// NOOP; will keep values as above
					default: break;		// for all else, use Windows 7 border (which is thicker); note that Windows 8 is being detected as "Windows NT (unknown)", so need to use default; this may not work with Windows 2000
				}
				break;
			default:
				h += -4;	// default adjustments since version 0.0.0.0; seems to work on XP and LNX
				break;
		}
		return SizeAdp_.new_(w, h);
	}
	private static byte Xto_mode_tid(String s) {
		if		(String_.Eq(s, "previous"))		return Mode_tid_previous;
		else if	(String_.Eq(s, "maximized"))	return Mode_tid_maximized;
		else if	(String_.Eq(s, "absolute"))		return Mode_tid_absolute;
		else if	(String_.Eq(s, "relative"))		return Mode_tid_relative;
		else if	(String_.Eq(s, "default"))		return Mode_tid_default;
		else									return Mode_tid_previous;
	}
	public static final byte Mode_tid_previous = 0, Mode_tid_maximized = 1, Mode_tid_absolute = 2, Mode_tid_relative = 3, Mode_tid_default = 4;
}
