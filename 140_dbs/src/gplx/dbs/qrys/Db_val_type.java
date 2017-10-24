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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
public class Db_val_type {
	public static final byte // not serialized
	  Tid_null		= 0
	, Tid_bool		= 1
	, Tid_byte		= 2
	, Tid_int32		= 3
	, Tid_int64		= 4
	, Tid_date		= 5
	, Tid_decimal	= 6
	, Tid_float		= 7
	, Tid_double	= 8
	, Tid_bry		= 9
	, Tid_varchar	= 10
	, Tid_nvarchar	= 11
	, Tid_rdr		= 12
	, Tid_text		= 13
	;
}
