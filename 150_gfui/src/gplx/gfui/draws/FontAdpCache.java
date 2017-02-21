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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import java.awt.Font;
import java.awt.Toolkit;
public class FontAdpCache {
	public Font GetNativeFont(FontAdp fontAdp) {
		String key = fontAdp.toString();
		Font rv = (Font)hash.Get_by(key); if (rv != null) return rv;
				if (screenResolutionInDpi == -1) ScreenResolution_set();
	    int fontSize = XtoJavaDpi(fontAdp.Size());
		rv = new Font(fontAdp.Name(), fontAdp.Style().Val(), fontSize);		
				hash.Add(key, rv);
		return rv;
	}	Hash_adp hash = Hash_adp_.New();
		public static void ScreenResolution_set() {screenResolutionInDpi = Toolkit.getDefaultToolkit().getScreenResolution();}	// usually either 96 or 120
	public static int XtoOsDpi(float v) {return Math.round((v * 72) / screenResolutionInDpi);} // WORKAROUND/JAVA: Java needs 72 dpi screen resolution; wnt uses 96 or 120 dpi
	public static int XtoJavaDpi(float v) {return Math.round((v * screenResolutionInDpi) / 72);}
	static int screenResolutionInDpi = -1;
		public static final    FontAdpCache Instance = new FontAdpCache(); FontAdpCache() {}
}
