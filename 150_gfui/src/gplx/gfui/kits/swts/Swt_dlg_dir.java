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
