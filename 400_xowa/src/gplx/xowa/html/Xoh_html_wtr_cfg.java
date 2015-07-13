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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public class Xoh_html_wtr_cfg {
	public boolean Toc__show()			{return toc__show;} public Xoh_html_wtr_cfg Toc__show_(boolean v) {toc__show = v; return this;} private boolean toc__show;
	public boolean Lnki__id()			{return lnki__id;} public Xoh_html_wtr_cfg Lnki__id_y_() {lnki__id = Bool_.Y; return this;} private boolean lnki__id;
	public boolean Lnki__title()		{return lnki__title;} public Xoh_html_wtr_cfg Lnki__title_(boolean v)	{lnki__title = v; return this;} private boolean lnki__title;
	public boolean Lnki__visited()		{return lnki__visited;} public Xoh_html_wtr_cfg Lnki_visited_y_() {lnki__visited = true; return this;} private boolean lnki__visited;
	public boolean Hdr__div_wrapper()	{return hdr__div_wrapper;} public Xoh_html_wtr_cfg Hdr__div_wrapper_(boolean v) {hdr__div_wrapper = v; return this;} private boolean hdr__div_wrapper;
}
