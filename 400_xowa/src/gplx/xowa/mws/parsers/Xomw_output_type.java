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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_output_type {
	public static final byte 
	  Tid__html       = 1   // like parse()
	, Tid__wiki       = 2   // like preSaveTransform()
	, Tid__preprocess = 3   // like preprocess()
	, Tid__msg        = 3
	, Tid__plain      = 4   // like extractSections() - portions of the original are returned unchanged.
	;
}
