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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
class Site_skin_itm implements To_str_able {
	public Site_skin_itm(byte[] code, boolean dflt, byte[] name, boolean unusable) {
		this.code = code; this.dflt = dflt; this.name = name; this.unusable = unusable;
	}
	public byte[] Code() {return code;} private final byte[] code;
	public boolean Dflt() {return dflt;} private final boolean dflt;
	public byte[] Name() {return name;} private final byte[] name;
	public boolean Unusable() {return unusable;} private final boolean unusable;
	public String To_str() {return String_.Concat_with_obj("|", code, dflt, name, unusable);}
}
