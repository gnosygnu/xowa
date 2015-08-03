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
package gplx.xowa.html.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_href_ {
	public static final String
	  Str__file				= "file://"
	, Str__site				= "/site/"
	, Str__wiki				= "/wiki/"
	, Str__anch				= "#"
	;
	public static final byte[] 
	  Bry__file				= Bry_.new_a7(Str__file)
	, Bry__site				= Bry_.new_a7(Str__site)
	, Bry__wiki				= Bry_.new_a7(Str__wiki)
	, Bry__anch				= Bry_.new_a7(Str__anch)
	, Bry__https			= Bry_.new_a7("https://")	// NOTE: must be "https:" or wmf api won't work; DATE:2015-06-17
	, Bry__xcmd				= Bry_.new_a7("/xcmd/")
	;
	public static final int
	  Len__file				= Bry__file.length
	, Len__site				= Bry__site.length
	, Len__wiki				= Bry__wiki.length
	, Len__anch				= Bry__anch.length
	;
}
