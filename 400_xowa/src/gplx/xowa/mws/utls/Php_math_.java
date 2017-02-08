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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Php_math_ {
	public static double Round(double v, int places) {
		if (places < 0) {	// -1 means round to 10; -2 means round to 100; etc..
			int factor = (int)Math_.Pow(10, places * -1);
			return ((int)(Math_.Round(v, 0) / factor)) * factor;	// EX: ((int)Round(123, 0) / 10) * 10: 123 -> 12.3 -> 12 -> 120
		}
		else {
			return Math_.Round(v, places);
		}
	}					
}
