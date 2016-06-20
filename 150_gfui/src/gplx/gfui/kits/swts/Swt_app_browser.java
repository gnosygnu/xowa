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
import gplx.*;
import gplx.core.envs.Env_;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*; import org.eclipse.swt.custom.*; import org.eclipse.swt.events.*; import org.eclipse.swt.graphics.*; import org.eclipse.swt.layout.*; import org.eclipse.swt.widgets.*;
public class Swt_app_browser {
    public static void main(String[] args) {
        new Swt_app_browser().start();
    }

    public void start()
    {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gridData.widthHint = SWT.DEFAULT;
        gridData.heightHint = SWT.DEFAULT;
        shell.setLayoutData(gridData);
        shell.setText("Firebug Lite for SWT ;)");

        final Browser browser = new Browser(shell, SWT.NONE);
        GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
        gridData2.widthHint = SWT.DEFAULT;
        gridData2.heightHint = SWT.DEFAULT;
        browser.setLayoutData(gridData2);

        Button button = new Button(shell, SWT.PUSH);
        button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,    false));
        button.setText("Install");
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                browser.setUrl("javascript:(function(F,i,r,e,b,u,g,L,I,T,E){if(F.getElementById(b))return;E=F[i+'NS']&&F.documentElement.namespaceURI;E=E?F[i+'NS'](E,'script'):F[i]('script');E[r]('id',b);E[r]('src',I+g+T);E[r](b,u);(F[e]('head')[0]||F[e]('body')[0]).appendChild(E);E=new%20Image;E[r]('src',I+L);})(document,'createElement','setAttribute','getElementsByTagName','FirebugLite','4','firebug-lite.js','releases/lite/latest/skin/xp/sprite.png','https://getfirebug.com/','#startOpened');");
            }
        });

        browser.setUrl("http://stackoverflow.com/questions/12003602/eclipse-swt-browser-and-firebug-lite");

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
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
//        browser.setUrl("file:///C:/temp.html");
		browser.setText("hello");
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
