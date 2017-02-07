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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
public interface Xox_mgr extends Gfo_invk {
	byte[]		Xtn_key();
	void		Xtn_ctor_by_app(Xoae_app app);
	void		Xtn_ctor_by_wiki(Xowe_wiki wiki);
	void		Xtn_init_by_app(Xoae_app app);
	void		Xtn_init_by_wiki(Xowe_wiki wiki);
	Xox_mgr		Xtn_clone_new();
}
