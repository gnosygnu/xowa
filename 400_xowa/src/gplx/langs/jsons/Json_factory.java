/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
