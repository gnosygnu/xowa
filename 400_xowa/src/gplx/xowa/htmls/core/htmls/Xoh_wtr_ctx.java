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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
public class Xoh_wtr_ctx {
	Xoh_wtr_ctx(int mode) {this.mode = mode;}
	public int Mode() {return mode;} private final int mode;
	public boolean Mode_is_alt()			{return mode == Mode_alt;}
	public boolean Mode_is_display_title() {return mode == Mode_display_title;}
	public boolean Mode_is_popup()			{return mode == Mode_popup;}
	public boolean Mode_is_hdump()			{return mode == Mode_hdump;}
	public static final int Mode_basic = 0, Mode_alt = 1, Mode_display_title = 2, Mode_popup = 3, Mode_hdump = 4;
	public static final Xoh_wtr_ctx
	  Basic				= new Xoh_wtr_ctx(Mode_basic)
	, Alt				= new Xoh_wtr_ctx(Mode_alt)
	, Display_title		= new Xoh_wtr_ctx(Mode_display_title)
	, Popup				= new Xoh_wtr_ctx(Mode_popup)
	, Hdump				= new Xoh_wtr_ctx(Mode_hdump)
	;
}
