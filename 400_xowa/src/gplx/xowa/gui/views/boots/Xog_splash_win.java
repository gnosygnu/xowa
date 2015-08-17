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
package gplx.xowa.gui.views.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*; import gplx.xowa.gui.views.*;
import java.awt.*;
import java.awt.event.*;
public class Xog_splash_win implements RlsAble {
		private SplashScreen splash;
	private Graphics2D graphics; private boolean graphics_init = true;
		public Xog_splash_win(boolean app_mode_is_gui) {
		        if (app_mode_is_gui) {
	        this.splash = SplashScreen.getSplashScreen();
	        if (splash == null) System.out.println("SplashScreen.getSplashScreen() returned null");
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
