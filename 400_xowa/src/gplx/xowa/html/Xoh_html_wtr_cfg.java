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
	public boolean Toc_show() {return toc_show;} public Xoh_html_wtr_cfg Toc_show_(boolean v) {toc_show = v; return this;} private boolean toc_show;
	public boolean Lnki_id() {return lnki_id;} public Xoh_html_wtr_cfg Lnki_id_(boolean v) {lnki_id = v; return this;} private boolean lnki_id;
	public boolean Lnki_title() {return lnki_title;} public Xoh_html_wtr_cfg Lnki_title_(boolean v) {lnki_title = v; return this;} private boolean lnki_title;
	public boolean Lnki_visited() {return lnki_visited;} public Xoh_html_wtr_cfg Lnki_visited_(boolean v) {lnki_visited = v; return this;} private boolean lnki_visited;
}
