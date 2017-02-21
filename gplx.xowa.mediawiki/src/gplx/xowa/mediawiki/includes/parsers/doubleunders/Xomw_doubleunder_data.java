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
