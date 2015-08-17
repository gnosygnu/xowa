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
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import gplx.core.strings.*;
public class ClipboardAdp_ {
	public static void SetText(String text) {
				StringSelection data = new StringSelection(text);		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(data, data);
			}
	public static boolean IsText() {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		return clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
			}
	public static String GetText() {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String rv = "";
		try {rv = clipboard.getData(DataFlavor.stringFlavor).toString();}
		catch (Exception e) {throw Err_.new_exc(e, "ui", "clipboard get_data failed");}
		if (Op_sys.Cur().Tid_is_wnt()) {	// WORKAROUND:JAVA: On Windows, Clipboard will have \r\n, but Java automatically converts to \n
			String_bldr remake = String_bldr_.new_();
			for (int i = 0; i < String_.Len(rv); i++) {
				char c = String_.CharAt(rv, i);
				if (c == '\n' && i > 0) {
					char prev = String_.CharAt(rv, i - 1);
					if (prev != '\r') {
						remake.Add(String_.CrLf);
						continue;
					}					
				}
				remake.Add(c);
			}				
			rv = remake.To_str();
//			rv = String_.Replace(rv, "\n", Env_.NewLine);
		}
		return rv;
			}
}
