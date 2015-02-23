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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wmfs.apis.*;
public class Xof_orig_wkr_ {
	public static final Xof_orig_wkr[] Ary_empty = new Xof_orig_wkr[0];
	public static final byte
	  Tid_null				= Byte_.Max_value_127
	, Tid_noop				= 0
	, Tid_missing			= 1
	, Tid_mock				= 2
	, Tid_xowa_db			= 3
	, Tid_xowa_reg			= 4
	, Tid_wmf_api			= 5
	, Tid_dir				= 6
	;
	public static final byte 
	  Status_null			= Byte_.Max_value_127
	, Status_noop			= 0
	, Status_found			= 1
	, Status_missing_orig	= 2
	, Status_missing_qry	= 3
	, Status_missing_bin	= 4
	;
}
