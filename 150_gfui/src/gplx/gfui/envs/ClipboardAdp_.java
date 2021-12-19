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
package gplx.gfui.envs;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import gplx.core.envs.*;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
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
		catch (Exception e) {throw ErrUtl.NewArgs(e, "clipboard get_data failed");}
		if (Op_sys.Cur().Tid_is_wnt()) {	// WORKAROUND:JAVA: On Windows, Clipboard will have \r\n, but Java automatically converts to \n
			String_bldr remake = String_bldr_.new_();
			for (int i = 0; i < StringUtl.Len(rv); i++) {
				char c = StringUtl.CharAt(rv, i);
				if (c == '\n' && i > 0) {
					char prev = StringUtl.CharAt(rv, i - 1);
					if (prev != '\r') {
						remake.Add(StringUtl.CrLf);
						continue;
					}					
				}
				remake.Add(c);
			}				
			rv = remake.ToStr();
//			rv = String_.Replace(rv, "\n", Env_.NewLine);
		}
		return rv;
			}
}
