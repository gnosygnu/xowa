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
package gplx.ios; import gplx.*;
public class IoErr {
	public static String Namespace				= "gplx.ios.";
	public static String FileIsReadOnly_key		= Namespace + "FileIsReadOnlyError";
	public static String FileNotFound_key		= Namespace + "FileNotFoundError";
	public static Err FileIsReadOnly(Io_url url) {
		return Err_.new_(FileIsReadOnly_key, "file is read-only", "url", url.Xto_api()).Trace_ignore_add_1_();
	}
	public static Err FileNotFound(String op, Io_url url) {
		// file is missing -- op='copy' file='C:\a.txt' copyFile_target='D:\a.txt' 
		return Err_.new_(FileNotFound_key, "file not found", "op", op, "file", url.Xto_api()).Trace_ignore_add_1_();
	}
}