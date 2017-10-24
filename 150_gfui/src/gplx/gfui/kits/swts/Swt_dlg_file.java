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
import gplx.gfui.kits.*;
import gplx.gfui.kits.core.Gfui_dlg_file;
import gplx.gfui.kits.core.Gfui_kit_;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
public class Swt_dlg_file implements Gfui_dlg_file {
	private FileDialog under;
	public Swt_dlg_file(byte type, Shell shell)	{
		int file_dialog_type
			= type == Gfui_kit_.File_dlg_type_save
			? SWT.SAVE
			: SWT.OPEN
			;			
		under = new FileDialog(shell, file_dialog_type);
	}
	public Gfui_dlg_file Init_msg_(String v) 			{under.setText(v); return this;}
	public Gfui_dlg_file Init_file_(String v) 			{under.setFileName(v); return this;}
	public Gfui_dlg_file Init_dir_(Io_url v) 			{under.setFilterPath(v.Xto_api()); return this;}
	public Gfui_dlg_file Init_exts_(String... v) 		{under.setFilterExtensions(v); return this;}
	public String Ask() 								{return under.open();}
}
