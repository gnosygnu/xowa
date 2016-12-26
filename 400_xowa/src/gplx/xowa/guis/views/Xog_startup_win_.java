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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.envs.*;
import gplx.gfui.*; import gplx.gfui.envs.*; import gplx.gfui.controls.windows.*;
public class Xog_startup_win_ {
	public static void Startup(Xoa_app app, GfuiWin win) {
		gplx.xowa.addons.apps.cfgs.Xocfg_mgr cfg_mgr = app.Cfg();
		String window_mode = cfg_mgr.Get_str_app_or(Cfg__window_mode, "previous");
		Rect_ref manual_rect = Rect_ref.parse(cfg_mgr.Get_str_app_or(Cfg__manual_rect, "0,0,800,640"));

		// change win_rect per mode: previous; absolute; etc.
		boolean init_is_maximized = false;
		if		(String_.Eq(window_mode, "previous")) {
			if (cfg_mgr.Get_bool_app_or(Cfg__prev_maximized, false)) {
				win.Maximized_(true);
				init_is_maximized = true;
			}
			else {
				Rect_ref previous_rect = null;
				String s = cfg_mgr.Get_str_app_or(Cfg__prev_rect, "");
				if (String_.Eq(s, "")) {
					SizeAdp size = Screen_maximized_calc();
					previous_rect = new Rect_ref(0, 0, size.Width(), size.Height());
				}
				else
					previous_rect = Rect_ref.parse(s);
				win.Rect_set(previous_rect.XtoRectAdp());
			}
		}
		else if (String_.Eq(window_mode, "absolute")) {
			win.Rect_set(manual_rect.XtoRectAdp());
		}
		else if (String_.Eq(window_mode, "maximized")) {
			win.Maximized_(true);
			init_is_maximized = true; 
		}
		else if (String_.Eq(window_mode, "default")) {} // noop
		else if (String_.Eq(window_mode, "relative")) {
			SizeAdp screen_maximized = Screen_maximized_calc();
			Rect_ref win_rect = new Rect_ref(0, 0, screen_maximized.Width(), screen_maximized.Height());
			win.Rect_set(win_rect.XtoRectAdp_add(manual_rect));
		}

		// make sure win_rect is safe
		boolean safe_mode = cfg_mgr.Get_bool_app_or(Cfg__manual_safe, true);
		if (safe_mode && !init_is_maximized) {
			RectAdp rect = win.Rect();
			boolean force = false; int x = rect.X(), y = rect.Y(), w = rect.Width(), h = rect.Height();
			SizeAdp screen_size = Screen_maximized_calc();
			int max_w = screen_size.Width(), max_h = screen_size.Height();
			int Safe_mode_buffer = 20; // allow minor negative positioning (x=-5), off-screen positioning (w=1605)
			if (x < -Safe_mode_buffer || x		> max_w + Safe_mode_buffer)		{force = true; x = 0;}
			if (y < -Safe_mode_buffer || y		> max_h + Safe_mode_buffer)		{force = true; y = 0;}
			if (w <  Safe_mode_buffer || x + w	> max_w + Safe_mode_buffer)		{force = true; w = max_w - x;}
			if (h <  Safe_mode_buffer || y + h	> max_h + Safe_mode_buffer)		{force = true; h = max_h - y;}
			if (force)
				win.Rect_set(RectAdp_.new_(x, y, w, h));
		}
	}
	public static void Shutdown(Xoae_app app, GfuiWin win) {
		gplx.xowa.addons.apps.cfgs.Xocfg_mgr cfg_mgr = app.Cfg();
		if (String_.Eq(cfg_mgr.Get_str_app_or(Cfg__window_mode, "previous"), "previous")) {
			cfg_mgr.Set_str_app(Cfg__prev_rect			, win.Rect().Xto_str());
			cfg_mgr.Set_str_app(Cfg__prev_maximized		, Yn.To_str(win.Maximized()));
		}
		Xog_startup_tabs_.Shutdown(app);

		cfg_mgr.Set_str_app(gplx.xowa.guis.views.Xog_startup_tabs_.Cfg__prev_version, Xoa_app_.Version);
		gplx.xowa.apps.cfgs.Xoa_cfg_mgr cfg_mgr2 = app.Cfg_mgr();
		app.Api_root().Html().Page().Toggle_mgr().Save(cfg_mgr2);
		cfg_mgr2.Db_save_txt();
	}
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
	private static final String 
	  Cfg__window_mode		= "xowa.app.startup.window.mode"
	, Cfg__manual_rect		= "xowa.app.startup.window.manual_rect"
	, Cfg__manual_safe		= "xowa.app.startup.window.manual_safe"
	, Cfg__prev_rect		= "xowa.app.startup.window.previous_rect"
	, Cfg__prev_maximized	= "xowa.app.startup.window.previous_maximized"
	;
}
