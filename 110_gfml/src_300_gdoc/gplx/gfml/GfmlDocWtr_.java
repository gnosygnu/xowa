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
package gplx.gfml; import gplx.*;
import gplx.core.strings.*;
public class GfmlDocWtr_ {
	public String Xto_str_and_clear()			{return sb.Xto_str_and_clear();}
	public void BuildAttrib(GfmlAtr atr)	{Build(atr);}
	public void BuildNode(GfmlNde nde)		{Build(nde);}
	void Build(GfmlItm owner) {
		for (int i = 0; i < owner.SubObjs_Count(); i++) {
			GfmlObj subObj = owner.SubObjs_GetAt(i);
			GfmlItm subItm = GfmlItm_.as_(subObj);
			if (subItm == null)
				sb.Add(GfmlTkn_.as_(subObj).Raw());
			else
				Build(subItm);
		}
	}
	String_bldr sb = String_bldr_.new_();
        public static String xtoStr_(GfmlNde nde) {
		GfmlDocWtr_ wtr = new GfmlDocWtr_();
		wtr.BuildNode(nde);
		return wtr.Xto_str_and_clear();
	}
}
