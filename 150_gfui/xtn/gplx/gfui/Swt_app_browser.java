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
package gplx.gfui;
import gplx.*;
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*; import org.eclipse.swt.custom.*; import org.eclipse.swt.events.*; import org.eclipse.swt.graphics.*; import org.eclipse.swt.layout.*; import org.eclipse.swt.widgets.*;
public class Swt_app_browser {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		System.setProperty("org.eclipse.swt.browser.XULRunnerPath", "C:\\xowa\\bin\\windows\\xulrunner");
		Swt_app_browser_mgr mgr = new Swt_app_browser_mgr(shell);
		New_btn(shell, 0, "loa&d", new Swt_app_browser_cmd_load(mgr));
		New_btn(shell, 1, "&free", new Swt_app_browser_cmd_free(mgr));
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		mgr.Free();
		display.dispose();
	}
	private static Button New_btn(Shell shell, int idx, String text, SelectionListener lnr) {
		Button rv = new Button(shell, SWT.BORDER);
		rv.setText(text);
		rv.setBounds(idx * 80, 0, 80, 40);
		rv.addSelectionListener(lnr);
		return rv;
	}
}
class Swt_app_browser_mgr {
	private Shell shell; private Browser browser;
	public Swt_app_browser_mgr(Shell shell) {this.shell = shell;}
	public void Load() {
//		this.Free();
		if (browser == null) {
			browser = new Browser(shell, SWT.MOZILLA);
			Point size = shell.getSize();
			browser.setBounds(0, 40, size.x, size.y - 40);
		}
//		browser.setUrl("about:blank");
        browser.setUrl("file:///C:/temp.html");
//		browser.setText(Io_mgr._.LoadFilStr("C:\\temp.html"));
	}
	public void Free() {
		if (browser != null) {
//			browser.setUrl("about:blank");
			browser.dispose();
		}
		Env_.GarbageCollect();
		browser = null;
	}
}
class Swt_app_browser_cmd_load implements SelectionListener {
	private Swt_app_browser_mgr mgr;
	public Swt_app_browser_cmd_load(Swt_app_browser_mgr mgr) {this.mgr = mgr;}
    public void widgetSelected(SelectionEvent event) {mgr.Load();}
    public void widgetDefaultSelected(SelectionEvent event) {}	
}
class Swt_app_browser_cmd_free implements SelectionListener {
	private Swt_app_browser_mgr mgr;
	public Swt_app_browser_cmd_free(Swt_app_browser_mgr mgr) {this.mgr = mgr;}
    public void widgetSelected(SelectionEvent event) {mgr.Free();}
    public void widgetDefaultSelected(SelectionEvent event) {}	
}
