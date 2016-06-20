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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_factory {
	public Json_itm			Null()												{return Json_itm_null.Null;}
	public Json_itm			Bool_n()											{return Json_itm_bool.Bool_n;}
	public Json_itm			Bool_y()											{return Json_itm_bool.Bool_y;}
	public Json_itm_int		Int(Json_doc doc, int bgn, int end)					{return new Json_itm_int(doc, bgn, end);}
	public Json_itm_long	Long(Json_doc doc, int bgn, int end)				{return new Json_itm_long(doc, bgn, end);}
	public Json_itm			Decimal(Json_doc doc, int bgn, int end)				{return new Json_itm_decimal(doc, bgn, end);}
	public Json_itm			Str(Json_doc doc, int bgn, int end, boolean exact)		{return new Json_itm_str(doc, bgn, end, exact);}
	public Json_kv			Kv(Json_itm key, Json_itm val)						{return new Json_kv(key, val);}
	public Json_ary			Ary(int bgn, int end)								{return new Json_ary(bgn, end);}
	public Json_nde			Nde(Json_doc doc, int bgn)							{return new Json_nde(doc, bgn);}
}
