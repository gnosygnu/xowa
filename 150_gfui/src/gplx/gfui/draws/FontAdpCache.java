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
