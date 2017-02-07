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
package gplx.core.progs.rates; import gplx.*; import gplx.core.*; import gplx.core.progs.*;
import gplx.core.lists.rings.*;
public class Gfo_rate_list {
	private final    Ring__long ring;
	public Gfo_rate_list(int size) {
		this.ring = new Ring__long(size * 2);	// *2 to store both data and time
	}
	public void Clear() {ring.Clear(); cur_rate = cur_delta = 0;}
	public double Cur_rate() {return cur_rate;} private double cur_rate = 0;
	public double Cur_delta() {return cur_delta;} private double cur_delta;
	public double Add(long data, long time) {
		ring.Add(data);
		ring.Add(time);		
		double new_rate = Cur_calc(data, time);
		cur_delta = cur_rate == 0 ? new_rate : Math_.Abs_double((new_rate - cur_rate) / cur_rate);
		cur_rate = new_rate;
		return cur_rate;
	}
	private double Cur_calc(long cur_data, long cur_time) {
		int len = ring.Len();
		long data_all = 0;
		long time_all = 0;
		for (int i = 0; i < len; i += 2) {
			data_all += ring.Get_at(i);
			time_all += ring.Get_at(i + 1);
		}
		return data_all / (time_all == 0 ? .001 : time_all);
	}
}
