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
package gplx.xowa.guis.views.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import java.awt.*;
import java.awt.event.*;
public class Xog_splash_win implements Rls_able {
		private SplashScreen splash;
	private Graphics2D graphics; private boolean graphics_init = true;
		public Xog_splash_win(boolean app_mode_is_gui) {
		        if (app_mode_is_gui) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "gui.splash.bgn");
	        this.splash = SplashScreen.getSplashScreen();
	        if (splash == null) System.out.println("SplashScreen.getSplashScreen() returned null");
			Gfo_usr_dlg_.Instance.Log_many("", "", "gui.splash.end");
        }
        	}
	public void Write(String msg) {
				if (splash == null) return;
    	if (graphics_init) {
    		graphics_init = false;
	        if (graphics == null) {
	        	graphics = splash.createGraphics();
	        	if (graphics == null) System.out.println("graphics is null");
	        }        
    	}
    	if (graphics == null) return;
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(120,140,200,40);
        graphics.setPaintMode();
        graphics.setColor(Color.BLACK);
        graphics.drawString(msg, 0, 0);
        splash.update();
    		}
	public void Rls() {
				if (splash == null) return;
    	splash.close();
    		}
}
