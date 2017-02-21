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
