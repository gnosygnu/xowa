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
package gplx.core.gfo_regys; import gplx.*; import gplx.core.*;
public class GfoRegyItm {
	public String Key() {return key;} private String key;
	public Object Val() {return val;} Object val;
	public Io_url Url() {return url;} Io_url url;
	public int ValType() {return valType;} int valType;
	public GfoRegyItm(String key, Object val, int valType, Io_url url) {this.key = key; this.val = val; this.valType = valType; this.url = url;}

	public static final int 
		  ValType_Obj = 1
		, ValType_Url = 2
		, ValType_B64 = 3
		;
}
