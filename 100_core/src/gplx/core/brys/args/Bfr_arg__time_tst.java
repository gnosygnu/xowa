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
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*;
public class Bfr_arg__time_tst {
	@Test   public void Basic() {
		Time_fmtr_arg_fxt fxt = new Time_fmtr_arg_fxt().Clear();
		fxt.XferAry(      1,             "01s");	// seconds
		fxt.XferAry(     62,         "01m 02s");	// minutes
		fxt.XferAry(   3723,     "01h 02m 03s");	// hours
		fxt.XferAry(  93784, "01d 02h 03m 04s");	// days
		fxt.XferAry(      0,             "00s");	// handle 0 seconds
		fxt.XferAry(   3601,     "01h 00m 01s");	// handle 0 in middle unit
	}
}
class Time_fmtr_arg_fxt {
	public Time_fmtr_arg_fxt Clear() {
		if (arg == null) arg = new Bfr_arg__time();
		return this;
	}	Bfr_arg__time arg;
	public void XferAry(int seconds, String expd) {
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		arg.Seconds_(seconds);
		arg.Bfr_arg__add(bfr);
		Tfds.Eq(expd, bfr.To_str());
	}
}
