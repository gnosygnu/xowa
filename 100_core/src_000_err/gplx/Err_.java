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
package gplx;
import gplx.core.strings.*;
public class Err_ {	//_20110415
	public static Err as_(Object obj) {return obj instanceof Err ? (Err)obj : null;}
	@gplx.Internal protected static Err new_key_1(String key, String hdr)			{return Err.hdr_(hdr).Key_(key);}
	public static String Message_lang(Exception e)			{return e.getMessage();} 
	public static String Message_gplx(Exception e)			{return ErrMsgWtr._.Message_gplx(e);}
	public static String Message_gplx_brief(Exception e)		{return ErrMsgWtr._.Message_gplx_brief(e);}
	@gplx.Internal protected static String StackTrace_lang(Exception e) {
				String_bldr sb = String_bldr_.new_();
		StackTraceElement[] stackTraceAry = e.getStackTrace();
		int len = stackTraceAry.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add_char_crlf();
			sb.Add(stackTraceAry[i].toString());
		}
		return sb.XtoStr();
			}
}
