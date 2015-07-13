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
package gplx.gfui; import gplx.*;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
public class ScreenAdp_ {
	public static final ScreenAdp Primary = screen_(0);
	public static ScreenAdp as_(Object obj) {return obj instanceof ScreenAdp ? (ScreenAdp)obj : null;}
	public static ScreenAdp cast_(Object obj) {try {return (ScreenAdp)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, ScreenAdp.class, obj);}}
	public static ScreenAdp parse_(String raw) {	// ex: {screen{1}
		try {
			raw = String_.Replace(raw, "{screen{", "");
			raw = String_.Replace(raw, "}", "");
			return ScreenAdp_.screen_(Int_.parse_(raw));
		}	catch(Exception exc) {throw Exc_.new_parse_exc(exc, ScreenAdp.class, raw);}
	}
	public static ScreenAdp from_point_(PointAdp pos) {// NOTE: not using FromPoint b/c of plat_wce
		if (ScreenAdp_.Count() == 1) return Primary;
		ScreenAdp screen0 = screen_(0), screen1 = screen_(1);
		return pos.X() < screen1.X() ? screen0 : screen1;
	}
	public static ScreenAdp opposite_(int idx) {
		if (ScreenAdp_.Count() == 1) return Primary;
		int opposite = idx == 0 ? 1 : 0;	// will ignore all screens with index > 1
		return screen_(opposite);
	}	
	public static int Count() {
				return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
//		return 1;//Screen.AllScreens.Length;
			}
	public static ScreenAdp screen_(int index) {
				if (index >= ScreenAdp_.Count()) throw Exc_.new_missing_idx(index, ScreenAdp_.Count());
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devs = env.getScreenDevices();
		GraphicsConfiguration conf = devs[index].getDefaultConfiguration();		
		ScreenAdp sd = new ScreenAdp(index, GxwCore_lang.XtoRectAdp(conf.getBounds()));
		return sd;
			}
//#@endif
	static ScreenAdp new_(int index, RectAdp rect) {return new ScreenAdp(index, rect);}
}
