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
package gplx.xowa.mediawiki.includes.parsers.doubleunders; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_doubleunder_data {
	// XO.MW: MW stores these as mDoubleUnderscores in Parser
	public boolean toc;
	public boolean no_toc;
	public boolean force_toc;

	public boolean no_gallery;
	public boolean force_gallery;

	public boolean no_title_convert;
	public boolean no_content_convert;

	public boolean no_edit_section;
	public boolean new_section_link;

	public boolean static_redirect;

	public boolean hidden_cat;

	public boolean index;
	public boolean no_index;

	// XO.MW: MW stores these as member variables in Parser
	public boolean show_toc;
	public boolean force_toc_position;

	public void Reset() {
		toc = no_toc = force_toc =
		no_gallery = force_gallery = 
		no_title_convert = no_content_convert = 
		no_edit_section = new_section_link = 
		static_redirect = 
		hidden_cat = index = no_index =
			false;

		show_toc = force_toc_position = false;
	}
}
