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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.kits.core.*;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
public class Swt_dlg_dir implements Gfui_dlg_dir {
	private final DirectoryDialog under;
	public Swt_dlg_dir(Shell shell) {
		this.under = new DirectoryDialog(shell);
	}
	public Gfui_dlg_dir Init_text_(String v) 			{under.setText(v); return this;}
	public Gfui_dlg_dir Init_msg_(String v) 				{under.setMessage(v); return this;}
	public Gfui_dlg_dir Init_dir_(Io_url v) 				{under.setFilterPath(v.Xto_api()); return this;}
	public String Ask() 								{return under.open();}
}
