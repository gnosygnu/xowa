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
