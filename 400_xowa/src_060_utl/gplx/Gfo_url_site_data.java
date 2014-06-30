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
package gplx;
public class Gfo_url_site_data {
	public boolean Rel() {return rel;} private boolean rel;
	public int Site_bgn() {return site_bgn;} private int site_bgn;
	public int Site_end() {return site_end;} private int site_end;
	public void Atrs_set(boolean rel, int bgn, int end) {this.rel = rel; this.site_bgn = bgn; this.site_end = end;}
}
