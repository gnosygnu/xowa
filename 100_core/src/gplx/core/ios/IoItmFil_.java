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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoItmFil_ {
	public static IoItmFil as_(Object obj) {return obj instanceof IoItmFil ? (IoItmFil)obj : null;}
	public static final String 
	  Prop_Size			= "size"
	, Prop_Modified		= "modified";
	public static IoItmFil new_(Io_url url, long size, DateAdp created, DateAdp modified) {return new IoItmFil().ctor_IoItmFil(url, size, modified);}	
	public static IoItmFil sub_(String name, long size, DateAdp modified) {
		IoItmFil rv = new IoItmFil();
		rv.ctor_IoItmFil(Io_url_.mem_fil_("mem/" + name), size, modified);
		rv.Name_(name);
		return rv;
	}
}
