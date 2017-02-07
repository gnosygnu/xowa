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
package gplx.core.ios.drives; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public class Io_drive__jre implements Io_drive {
	public long Get_space_total(Io_url drive) {return new java.io.File(drive.Xto_api()).getTotalSpace();}	
	public long Get_space_free (Io_url drive) {return new java.io.File(drive.Xto_api()).getFreeSpace();}	
}
