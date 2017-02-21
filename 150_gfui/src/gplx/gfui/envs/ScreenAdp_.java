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
package gplx.gfui.envs; import gplx.*; import gplx.gfui.*;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import gplx.gfui.controls.gxws.*;
public class ScreenAdp_ {
	public static final    ScreenAdp Primary = screen_(0);
	public static ScreenAdp as_(Object obj) {return obj instanceof ScreenAdp ? (ScreenAdp)obj : null;}
	public static ScreenAdp cast(Object obj) {try {return (ScreenAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, ScreenAdp.class, obj);}}
	public static ScreenAdp parse(String raw) {	// ex: {screen{1}
		try {
			raw = String_.Replace(raw, "{screen{", "");
			raw = String_.Replace(raw, "}", "");
			return ScreenAdp_.screen_(Int_.parse(raw));
		}	catch(Exception exc) {throw Err_.new_parse_exc(exc, ScreenAdp.class, raw);}
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
				if (index >= ScreenAdp_.Count()) throw Err_.new_missing_idx(index, ScreenAdp_.Count());
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devs = env.getScreenDevices();
		GraphicsConfiguration conf = devs[index].getDefaultConfiguration();		
		ScreenAdp sd = new ScreenAdp(index, GxwCore_lang.XtoRectAdp(conf.getBounds()));
		return sd;
			}
//#@endif
	static ScreenAdp new_(int index, RectAdp rect) {return new ScreenAdp(index, rect);}
}
