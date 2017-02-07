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
package gplx.gfui.envs; import gplx.*; import gplx.gfui.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import gplx.core.envs.*;
public class TimerAdp implements Rls_able {
	public TimerAdp Interval_(int interval) {
				underTimer.setInitialDelay(interval);
		underTimer.setDelay(interval);
				return this;
	}
	public TimerAdp Enabled_on() {return Enabled_(true);} public TimerAdp Enabled_off() {return Enabled_(false);}
	public TimerAdp Enabled_(boolean val) {
		if (!Env_.Mode_testing()) {
						if (val) underTimer.start();
			else underTimer.stop();
					}
		return this;
	}
	public void Rls() {underTimer.stop();}	

	Timer underTimer;
		public static TimerAdp new_(Gfo_invk invk, String msgKey, int interval, boolean enabled) {
		TimerAdp rv = new TimerAdp();
		rv.underTimer = new Timer(interval, new TimerActionListener(invk, msgKey));
		rv.Interval_(interval).Enabled_(enabled);
		return rv;
	}
	}
class TimerActionListener implements ActionListener {
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		Gfo_invk_.Invk_by_key(invk, key);
	}
	Gfo_invk invk; String key;
	public TimerActionListener(Gfo_invk invk, String key) {
		this.invk = invk; this.key = key;
	}
}
