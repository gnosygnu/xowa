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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public interface Xof_file_itm {
	byte[]				Lnki_ttl();
	byte[]				Lnki_md5();
	Xof_ext				Lnki_ext();
	byte 				Lnki_type();
	int					Lnki_w();
	int					Lnki_h();
	double				Lnki_upright();
	double				Lnki_time();
	int					Lnki_page();
	byte				Orig_repo_id();
	byte[]				Orig_repo_name();
	byte[]				Orig_ttl();
	int					Orig_ext();
	int					Orig_w();
	int					Orig_h();
	byte[]				Orig_redirect();
	boolean				Img_is_thumbable();
	int					File_w();
	int					Html_uid();
	byte				Html_elem_tid();
	int					Html_w();
	int					Html_h();
	int					Gallery_mgr_h();
}
